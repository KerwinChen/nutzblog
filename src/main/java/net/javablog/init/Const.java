package net.javablog.init;


import org.nutz.lang.Files;

import java.io.File;

public class Const {

    public static final int PAGE_SIZE = 20;
    public static final String HTML_SAVEPATH = System.getProperty("user.home") + File.separatorChar + "site_html" + File.separatorChar;
    public static final String HTML_SAVEPATH_TEMP = System.getProperty("user.home") + File.separatorChar + "site_html_temp" + File.separatorChar;
    public static final String IMG_SAVEPATH = System.getProperty("user.home") + File.separatorChar + "site_html"+File.separator+"images" + File.separatorChar;

    static {
        Files.createDirIfNoExists(IMG_SAVEPATH);
        Files.createDirIfNoExists(HTML_SAVEPATH);
        Files.createDirIfNoExists(HTML_SAVEPATH_TEMP);
    }

    public static String admin = "刀刀";
    public static String admin_email = "daodaovps@qq.com";
    public static String admin_github = "http://github.com/daodaovps";
    public static String admin_photo = "";
    public static String ftp_ip = "";
    public static String ftp_user = "";
    public static String ftp_pwd = "";
    public static String site_aboutme = "";
    public static String site_aboutme_md = "";
    public static String site_createtime = "";
    public static String site_fav = "";
    public static String site_logo = "";
    public static String site_msgboard = "";
    public static String site_name = "";
    public static String site_tj = "";


}
