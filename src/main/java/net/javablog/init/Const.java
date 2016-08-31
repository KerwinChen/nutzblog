package net.javablog.init;


import org.nutz.lang.Files;

import java.io.File;

public class Const {

    public static final int PAGE_SIZE = 20;

    public static final String savepath = System.getProperty("user.home") + File.separatorChar + "images" + File.separatorChar;

    static {
        Files.createDirIfNoExists(savepath);
    }


    public static String admin="刀刀";
    public static String admin_email="daodaovps@qq.com";
    public static String admin_github="http://github.com/daodaovps";
    public static String admin_photo="";
    public static String ftp_ip="";
    public static String ftp_user="";
    public static String ftp_pwd="";
    public static String site_aboutme="";
    public static String site_aboutme_md="";
    public static String site_createtime="";
    public static String site_fav="";
    public static String site_logo="";
    public static String site_msgboard="";
    public static String site_name="";
    public static String site_tj="";



}
