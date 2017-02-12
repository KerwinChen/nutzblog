package net.javablog.util;

import jodd.datetime.JDateTime;
import net.javablog.init.Const;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 备份工具类
 */
@IocBean
public class BackSite {


    public static String bakpath_base = System.getProperty("user.home") + "/nutzblog_html/all_javablog_bak/";
    public static String bakpath_zipfiles = bakpath_base + "/zipfiles/";

    public static String bakpath_db = bakpath_base + "db_javablog.net/";
    public static String bakpath_img = bakpath_base + "img_javablog.net/";
    public static String bakpath_code = bakpath_base + "code_javablog.net/";


    private static Logger log = LoggerFactory.getLogger(BackSite.class);

    public void backimg() {
        log.info("正在备份本机上的图片");
        Files.createDirIfNoExists(bakpath_img);
        Files.clearDir(new File(bakpath_img));

        try {
            Files.copyDir(new File(Const.IMG_SAVEPATH), new File(bakpath_img));
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("备份图片完毕");
    }


    public void backmysql() {
        log.info("正在备份数据库");
        Files.createDirIfNoExists(bakpath_db);
        Files.clearDir(new File(bakpath_db));

        ShellRun.run("mysqldump -uroot -proot nutzblog>" + bakpath_db + "/nutzblog.sql");
        log.info("备份数据库完毕");
    }


    public void back_code() {
        Files.createDirIfNoExists(bakpath_code);
        Files.clearDir(new File(bakpath_code));

        String codeing = "https://github.com/daodaovps/nutzblog.git";
        ShellRun.run("cd " + bakpath_code +" &&  git clone " + codeing);
        Files.deleteDir(new File(bakpath_code + "/nutzblog/.git"));
    }


    public void back() {
        Files.createDirIfNoExists(bakpath_base);

        backmysql();
        backimg();
        back_code();

        //打包　
        String filezip = bakpath_zipfiles + "/javablog_allbak_" + Times.sD(new Date()) + ".zip";
        if (new File(filezip).exists()) {
            new File(filezip).delete();
        }

        Files.createDirIfNoExists(bakpath_zipfiles);

        //删除之前7天的文件
        File[] fsall = Files.files(new File(bakpath_zipfiles), ".zip");
        if (!Lang.isEmpty(fsall)) {
            for (int i = 0; i < fsall.length; i++) {
                File f = fsall[i];
                String filename = f.getName();
                String date = filename.replace("javablog_allbak_", "").replace(".zip", "");
                //如果是5天前的备份，就删除
                if (Times.parseq("yyyy-MM-dd", date).before(new JDateTime(new Date()).subDay(5).convertToDate())) {
                    f.delete();
                }
            }
        }

        ZipUT.AddFolder(filezip, bakpath_code);
        ZipUT.AddFolder(filezip, bakpath_db);
        ZipUT.AddFolder(filezip, bakpath_img);

    }
}
