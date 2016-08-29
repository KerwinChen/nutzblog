package net.javablog;

import net.javablog.bean.tb_config;
import net.javablog.bean.tb_user;
import net.javablog.service.UserService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

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

        List<tb_config> listconfig = new ArrayList<>();
        listconfig.add(new tb_config("site_name", "NutzBlog"));
        listconfig.add(new tb_config("site_logo", "http://i1.buimg.com/1949/0847caefe5536050.png")); //本地图片    uuid.png , 肯定最后在文件夹中查找.
        listconfig.add(new tb_config("site_fav", "http://i1.buimg.com/1949/a688e1b6d48caabb.png"));  //本地图片    uuid.png , 肯定最后在文件夹中查找.
        listconfig.add(new tb_config("site_createtime", "2015-01-01"));
        listconfig.add(new tb_config("site_aboutme", "请叫我刀刀~~ (daodaovps@qq.com)"));
        listconfig.add(new tb_config("site_tj", ""));
        listconfig.add(new tb_config("site_msgstatus", "false"));
        listconfig.add(new tb_config("site_msgboard", ""));

        listconfig.add(new tb_config("admin_website", "http://daodaovps.com"));
        listconfig.add(new tb_config("admin_icon", "http://i4.buimg.com/1949/94c52c5fe19ccecc.jpg"));
        listconfig.add(new tb_config("admin_email", "daodaovps@qq.com"));
        listconfig.add(new tb_config("admin_github", "https://github.com/daodaovps"));
        listconfig.add(new tb_config("admin_weibo", "http://weibo.com/jiaowodaodao"));
        listconfig.add(new tb_config("admin_qq", "daodaovps@qq.com"));

        listconfig.add(new tb_config("ftp_ip", ""));
        listconfig.add(new tb_config("ftp_username", ""));
        listconfig.add(new tb_config("ftp_pwd", ""));
        insertConfig(listconfig, dao);

        //测试初始化Quartz
        //因为NutIoc中的Bean是完全懒加载模式的,不获取就不生成,不初始化,所以,为了触发计划任务的加载,需要获取一次
        ioc.get(NutQuartzCronJobFactory.class);

        //测试一次成功后，就不再运行
        if (false) {

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

        }


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