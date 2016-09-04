package net.javablog.module.adm.html;


import net.javablog.init.Const;
import net.javablog.service.ConfigService;
import net.javablog.util.FTPUtil;
import net.javablog.util.Threads;
import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@IocBean
public class FtpModule {

    private static Logger log = Logger.getLogger("FTP");

    @Inject
    private ConfigService configService;


    /**
     * 上传一次就可以了
     */
    @At("/ftp_upload_static")
    @Ok("redirect:/log")
    public void upload_static() {
        String ip = configService.getIP();
        String user = configService.getUser();
        String pwd = configService.getPwd();
        Threads.run(new Runnable() {
            @Override
            public void run() {
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/static", "/static");

                log.info("上传static文件夹 over");
            }
        });
    }

    /**
     * 基本上传一次就可以，后续的图片会跟随html的上传一并上传的。
     */
    @At("/ftp_upload_images_all")
    @Ok("redirect:/log")
    public void upload_images_all() {
        String ip = configService.getIP();
        String user = configService.getUser();
        String pwd = configService.getPwd();
        Threads.run(new Runnable() {
            @Override
            public void run() {
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/images", "/images");
                log.info("上传images文件夹 over");
            }
        });
    }

    @At("/ftp_upload_html_all")
    @Ok("redirect:/log")
    public void ftp_upload_html_all() {
        String ip = configService.getIP();
        String user = configService.getUser();
        String pwd = configService.getPwd();
        Threads.run(new Runnable() {
            @Override
            public void run() {

                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/index.html", "/index.html");
                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/index.htm", "/index.htm");
                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/archive.html", "/archive.html");
                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/tags.html", "/tags.html");
                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/me.html", "/me.html");
                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/404.html", "/404.html");

                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/book", "/book");
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/filter", "/filter");
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/index", "/index");
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/pages", "/pages");
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/page", "/page");
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/seris", "/seris");
                log.info("上传html文件 over");
            }
        });
    }

    @At("/ftp_cleanall")
    @Ok("redirect:/log")
    public void ftp_cleanall() {
        String ip = configService.getIP();
        String user = configService.getUser();
        String pwd = configService.getPwd();
        Threads.run(new Runnable() {
            @Override
            public void run() {
                FTPUtil.removeDirectory(ip, user, pwd, "/");
            }
        });
    }


}
