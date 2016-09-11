package net.javablog.util;

import org.nutz.dao.entity.Record;
import org.nutz.dao.util.Daos;
import org.nutz.json.Json;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RunES_IndexJob {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("FTP");

    /**
     * 创建索引
     *
     * @param singlepage
     */
    public static void createIndex(final Record singlepage) {
        Threads.run(new Runnable() {
            @Override
            public void run() {
                String id = singlepage.getString("_id");
                singlepage.put("copy_id", id);
                singlepage.remove("_id");//内部字段，需要删除
                String json = Json.toJson(singlepage);
                EsUT.createIndex(json, id);
            }
        });
    }

    public static void delIndex(final int copyid) {
        Threads.run(new Runnable() {
            @Override
            public void run() {
                EsUT.delIndex(copyid);
            }
        });
    }

    public static void repeatRows(String tbname, String url, String dbuser, String dbpwd) {
        log.info(tbname);

        //查询多少条记录，查询多少页。
        log.info(" dao.count({}),可能时间较长。"+tbname);
        long all = Curd.count(tbname, url, dbuser, dbpwd);

        if (all == 0) {
            log.info("  表 {} , 没有数据,return."+tbname);
            return;
        }

        try {
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, dbuser, dbpwd); //链接本地MYSQL
            PreparedStatement stmt;

            //查询数据 limit m,n    m从0开始，n是多少条记录的意思
            String selectSql = "SELECT * FROM " + tbname;
            stmt = con.prepareStatement(selectSql);
            stmt.setFetchSize(Integer.MIN_VALUE);
            ResultSet rec = stmt.executeQuery();

            int begin = 0;//从0开始，表示是第几页
            int i = 0;//从0开始
            while (rec.next()) { //循环输出结果集
                log.info("第{}个表，第{} pagesize，第{}条记录" + tbname +";"+ begin+";" +i);
                i++;
                Record record = Record.create(rec);
                createIndex(record);
            }
            Daos.safeClose(rec);
        } catch (Exception e) {
            log.info("MYSQL ERROR:" + e.getMessage());
        } finally {
        }
    }


    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/nutzblog";
        String user = "root";
        String pwd = "root";
        RunES_IndexJob.repeatRows("tb_singlepage", url,user,pwd);

    }


}
