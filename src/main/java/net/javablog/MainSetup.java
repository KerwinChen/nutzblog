package net.javablog;

import net.javablog.bean.User;
import net.javablog.service.UserService;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MainSetup implements Setup {


    private static final Log log = Logs.get();

    // 特别留意一下,是init方法,不是destroy方法!!!!!
    public void init(NutConfig conf) {
        Ioc ioc = conf.getIoc();
        Dao dao = ioc.get(Dao.class);

        Daos.createTablesInPackage(dao, "net.javablog", false);
        Daos.migration(dao, User.class, true, false);


        // 初始化默认根用户
        // 初始化默认根用户
        if (dao.count(User.class) == 0) {
            UserService us = ioc.get(UserService.class);
            us.add("admin", "123456");
        }


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

    public void destroy(NutConfig conf) {
    }

}