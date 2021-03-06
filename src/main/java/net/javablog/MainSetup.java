package net.javablog;

import com.alibaba.druid.pool.DruidDataSource;
import net.javablog.bean.tb_config;
import net.javablog.bean.tb_user;
import net.javablog.init.Const;
import net.javablog.module.adm.html.FtpModule;
import net.javablog.service.ConfigService;
import net.javablog.service.UserService;
import net.javablog.util.FTPUtil;
import net.javablog.util.RunES_IndexJob;
import net.javablog.util.Threads;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.lang.Files;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainSetup implements Setup {


    private static final Log log = Logs.get();

    // 特别留意一下,是init方法,不是destroy方法!!!!!
    public void init(NutConfig conf) {
        Ioc ioc = conf.getIoc();
        Dao dao = ioc.get(Dao.class);
        Daos.createTablesInPackage(dao, tb_user.class, false);

        // 初始化默认根用户
        if (dao.count(tb_user.class) == 0) {
            UserService us = ioc.get(UserService.class);
            us.add("admin", "admin888");
        }

        //  配置表初始化
        {
            List<tb_config> listconfig = new ArrayList<>();
            listconfig.add(new tb_config("site_name", "NutzBlog"));
            listconfig.add(new tb_config("site_logo", "http://i1.buimg.com/1949/0847caefe5536050.png")); //本地图片    uuid.png , 肯定最后在文件夹中查找.
            listconfig.add(new tb_config("site_fav", "http://i1.buimg.com/1949/a688e1b6d48caabb.png"));  //本地图片    uuid.png , 肯定最后在文件夹中查找.
            listconfig.add(new tb_config("site_createtime", "2015-01-01"));
            listconfig.add(new tb_config("site_aboutme", "我是刀刀~~ (daodaovps@qq.com)"));
            listconfig.add(new tb_config("site_aboutme_md", "我是刀刀~~ (daodaovps@qq.com)"));
            listconfig.add(new tb_config("site_tj", ""));
            listconfig.add(new tb_config("site_msgboard", ""));

            listconfig.add(new tb_config("admin", "刀刀"));
            listconfig.add(new tb_config("admin_photo", "http://i4.buimg.com/1949/94c52c5fe19ccecc.jpg"));
            listconfig.add(new tb_config("admin_email", "daodaovps@qq.com"));
            listconfig.add(new tb_config("admin_github", "https://github.com/daodaovps"));

            listconfig.add(new tb_config("ftp_ip", ""));
            listconfig.add(new tb_config("ftp_user", ""));
            listconfig.add(new tb_config("ftp_pwd", ""));
            insertConfig(listconfig, dao);
        }

        // 公共常量
        ConfigService confService = ioc.get(ConfigService.class);
        Const.admin = confService.fetch(Cnd.where("k", "=", "admin")).getV();
        Const.admin_email = confService.fetch(Cnd.where("k", "=", "admin_email")).getV();
        Const.admin_github = confService.fetch(Cnd.where("k", "=", "admin_github")).getV();
        Const.admin_photo = confService.fetch(Cnd.where("k", "=", "admin_photo")).getV();
        Const.ftp_ip = confService.fetch(Cnd.where("k", "=", "ftp_ip")).getV();
        Const.ftp_pwd = confService.fetch(Cnd.where("k", "=", "ftp_pwd")).getV();
        Const.ftp_user = confService.fetch(Cnd.where("k", "=", "ftp_user")).getV();
        Const.site_aboutme = confService.fetch(Cnd.where("k", "=", "site_aboutme")).getV();
        Const.site_aboutme_md = confService.fetch(Cnd.where("k", "=", "site_aboutme_md")).getV();
        Const.site_createtime = confService.fetch(Cnd.where("k", "=", "site_createtime")).getV();
        Const.site_fav = confService.fetch(Cnd.where("k", "=", "site_fav")).getV();
        Const.site_logo = confService.fetch(Cnd.where("k", "=", "site_logo")).getV();
        Const.site_msgboard = confService.fetch(Cnd.where("k", "=", "site_msgboard")).getV();
        Const.site_name = confService.fetch(Cnd.where("k", "=", "site_name")).getV();
        Const.site_tj = confService.fetch(Cnd.where("k", "=", "site_tj")).getV();

        //测试初始化Quartz
        //因为NutIoc中的Bean是完全懒加载模式的,不获取就不生成,不初始化,所以,为了触发计划任务的加载,需要获取一次
        ioc.get(NutQuartzCronJobFactory.class);


        //拷贝static文件夹
        Threads.run(new Runnable() {
            @Override
            public void run() {
                try {
                    File target_static = new File(Const.HTML_SAVEPATH + "/static");
                    Files.deleteDir(target_static);
                    Files.copyDir(new File(conf.getServletContext().getRealPath("/") + "/static"), target_static);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




        //ES 创建索引
        Threads.run(new Runnable() {
            @Override
            public void run() {
                DataSource ds = ((NutIoc) ioc).get(DataSource.class);
                DruidDataSource ds_ = (DruidDataSource) ds;
                String url = ds_.getUrl();
                String user = ds_.getUsername();
                String pwd = ds_.getPassword();
                RunES_IndexJob.repeatRows("tb_singlepage", url, user, pwd);
            }
        });

        //异步处理,最新的10篇博客
//        FtpModule ftpModule = ((NutIoc) ioc).get(FtpModule.class);
//        ftpModule.htmlTask();


//        upload_static_images(Const.ftp_ip, Const.ftp_user, Const.ftp_pwd);


        //测试一次成功后，就不再运行

        // 测试发送邮件
//            try {
//                HtmlEmail email = ioc.get(HtmlEmail.class);
//                email.setSubject("测试NutzBook");
//                email.setMsg("This is a test mail ... :-)" + System.currentTimeMillis());
//                email.addTo("zhushaolong321@qq.com");//请务必改成您自己的邮箱啊!!!
//                email.buildMimeMessage();
//                email.sendMimeMessage();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


//          redis测试代码
//        JedisPool jedisPool = ioc.get(JedisPool.class);
//        try (Jedis jedis = jedisPool.getResource()) { // Java7的语法
//            String re = jedis.set("_nutzbook_test_key", "http://javablog.net");
//            log.debug("redis say : " + re);
//            re = jedis.get("_nutzbook_test_key");
//            log.debug("redis say : " + re);
//        } finally {
//        }

//         AOP方式测试redis
//        RedisService redis = ioc.get(RedisService.class);
//        redis.set("hi", "刀刀");
//        log.debug("redis say again : " + redis.get("hi"));


    }

    private void upload_static_images(String ftp_ip, String ftp_user, String ftp_pwd) {


        String remoteDir1 = "/images";
        String remoteDir2 = "/static";
        String localDir1 = Const.HTML_SAVEPATH + "images";
        String localDir2 = Const.HTML_SAVEPATH + "static";


        FTPUtil.uploadDirectory(ftp_ip, ftp_user, ftp_pwd, localDir1, remoteDir1);
        FTPUtil.uploadDirectory(ftp_ip, ftp_user, ftp_pwd, localDir2, remoteDir2);


    }

    private void insertConfig(List<tb_config> list, Dao dao) {

        for (int i = 0; i < list.size(); i++) {
            tb_config c = list.get(i);
            if (dao.count(tb_config.class, Cnd.where("k", "=", c.getK())) == 0) {
                dao.insert(c);
            }
        }

    }

    public void destroy(NutConfig conf) {
    }

}