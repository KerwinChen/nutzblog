package net.javablog.mvc;

import net.javablog.init.Const;
import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.random.R;

import java.util.Map;

@IocBean //声明为一个Ioc的bean,名字为logInterceptor
public class Addrs implements MethodInterceptor {


    public void filter(InterceptorChain chain) throws Throwable {
//        System.out.println("方法即将执行 -->" + chain.getCallingMethod());
        chain.doChain();// 继续执行其他拦截器
        Object out = chain.getReturn();
        if (out instanceof Map) {
            Map out2 = (Map) out;
            out2.put("admin", Const.admin);
            out2.put("admin_email", Const.admin_email);
            out2.put("admin_github", Const.admin_github);
            out2.put("admin_photo", Const.admin_photo);
//            out2.setv("ftp_ip", Const.ftp_ip);
//            out2.setv("ftp_pwd", Const.ftp_pwd);
//            out2.setv("ftp_user", Const.ftp_user);
//            out2.setv("site_aboutme", Const.site_aboutme);
//            out2.setv("site_aboutme_md", Const.site_aboutme_md);
//            out2.setv("site_createtime", Const.site_createtime);
            out2.put("site_fav", Const.site_fav);
            out2.put("site_logo", Const.site_logo);
            out2.put("site_msgboard", Const.site_msgboard);
            out2.put("site_tj", Const.site_tj);
            out2.put("site_name", Const.site_name);
            out2.put("version", R.random(10, 99));//静态文件缓存
            chain.setReturnValue(out2);
        }
//        System.out.println("方法执行完毕 -->" + chain.getCallingMethod());
    }
}