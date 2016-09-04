package net.javablog.util;

import org.nutz.lang.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Curd {

    private static final Logger log = LoggerFactory.getLogger(Curd.class);

    private static void add() throws SQLException {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = JdbcUtil.getConnection();
//            String sql = "insert into [user](name,password,email,age,birthday,money) values(?,?,?,?,?,?)";
//            ps = conn.prepareStatement(sql);
//			ps.setString(1, "psName");
//			ps.setString(2, "psPassword");
//			ps.setString(3, "jkjs@126.com");
//			ps.setInt(4, 23);
//			ps.setDate(5, new java.sql.Date(new java.util.Date().getDate()));
//			ps.setFloat(6, 2344);
//            ps.executeUpdate();
//        } finally {
//            JdbcUtil.close(rs, ps, conn);
//        }

    }

    private static void get() throws SQLException {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = JdbcUtil.getConnection();
//            String sql = "select id,name,password,email,birthday,money from [user]";
//            ps = conn.prepareStatement(sql);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                String pass = rs.getString("password");
//                String email = rs.getString("email");
//                Date birthday = rs.getDate("birthday");
//                float money = rs.getFloat("money");
//                System.out.println("id是：" + id + "姓名是： " + name + " 密码是：" + pass + "邮箱是："
//                        + email + "生日是： " + birthday + "工资是" + money);
//            }
//
//        } finally {
//            JdbcUtil.close(rs, ps, conn);
//        }

    }

    private static void update() throws SQLException {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = JdbcUtil.getConnection();
//            String sql = "update [user] set name='lucy',password='123',money=5000 where id=1";
//            ps = conn.prepareStatement(sql);
//            ps.executeUpdate();
//
//        } finally {
//            JdbcUtil.close(rs, ps, conn);
//        }

    }

    private static void delete() throws SQLException {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = JdbcUtil.getConnection(url,dbname,dbuser,dbpwd);
//            String sql = "delete from [user] where id=1";
//            ps = conn.prepareStatement(sql);
//            ps.executeUpdate();
//        } finally {
//            JdbcUtil.close(rs, ps, conn);
//        }
    }


    /**
     * 查询某张表里的总数量
     *
     * @param tbname
     * @return
     * @throws SQLException
     */
    public static long count(String tbname, String url, String dbuser, String dbpwd) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long out = 0;
        try {
            conn = JdbcUtil.getConnection(url, dbuser, dbpwd);
            String sql = "SELECT count(1) from  " + tbname;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                out = rs.getLong(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("", e);
        } finally {
            JdbcUtil.close(rs, ps, conn);
        }
        return out;
    }


    /**
     * 删除掉某张表
     *
     * @param tbname
     * @return
     * @throws SQLException
     */
    public static void drop(String tbname, String url, String dbuser, String dbpwd) {

        if (Lang.isWin()) {
            log.info("win认为是测试环境,暂不删除表 {} ", tbname);
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection(url, dbuser, dbpwd);
            String sql = "drop TABLE  " + tbname;
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("", e);
        } finally {
            log.info("表{}备份完了,已被删除 ", tbname);
            JdbcUtil.close(rs, ps, conn);
        }


    }


    public static List<String> getTbList(String url, String dbname, String dbuser, String dbpwd) {
        List<String> out = new ArrayList<String>();
        String sql = "SELECT table_name tb_names from INFORMATION_SCHEMA.TABLES Where table_schema = '" + dbname + "' ";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection(url, dbuser, dbpwd);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String tbname = rs.getString(1);
                out.add(tbname);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("", e);
        } finally {
            JdbcUtil.close(rs, ps, conn);
        }


        return out;
    }


}
