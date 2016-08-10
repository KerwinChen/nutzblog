package net.javablog.module.adm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;


@IocBean
//@Filters(@By(type = CheckSession.class, args = {"me", "/"}))
public class LoginModule {

    @At("/adm/login")
    @Ok("fm:adm.login") // 模板文件的路径为  /WEB-INF/adm/login
    public void login() {
    }

}
