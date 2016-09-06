package net.javablog.mvc;

import net.javablog.bean.tb_config;
import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.random.R;

import java.util.Map;

@IocBean //声明为一个Ioc的bean,名字为logInterceptor
public class Addrs implements MethodInterceptor {

    @Inject
    private NutDao dao;

    public void filter(InterceptorChain chain) throws Throwable {
//        System.out.println("方法即将执行 -->" + chain.getCallingMethod());
        chain.doChain();// 继续执行其他拦截器
        Object out = chain.getReturn();
        if (out instanceof Map) {
            Map out2 = (Map) out;
            out2.put("admin", get("admin"));
            out2.put("admin_email", get("admin_email"));
            out2.put("admin_github", get("admin_github"));
            out2.put("admin_photo", get("admin_photo"));

            out2.put("site_fav", get("site_fav"));
            out2.put("site_logo", get("site_logo"));
            out2.put("site_msgboard", get("site_msgboard"));
            out2.put("site_tj", get("site_tj"));
            out2.put("site_name", get("site_name"));
            out2.put("version", R.random(10, 99));//静态文件缓存
            chain.setReturnValue(out2);
        }
//        System.out.println("方法执行完毕 -->" + chain.getCallingMethod());
    }

    private Object get(String k) {
        return dao.fetch(tb_config.class, Cnd.where("k", "=", k)).getV();
    }
}