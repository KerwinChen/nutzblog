package net.javablog.module.adm;

import net.javablog.bean.tb_user;
import net.javablog.service.tb_userService;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.Rs;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;


@IocBean
@At("/adm")
//@Filters(@By(type = CheckSession.class, args = {"me", "/"}))
public class LoginModule {

    @Inject
    private tb_userService tb_userService;

    @At
    @Ok("fm:adm.login") // 模板文件的路径为  /WEB-INF/adm/login
    public void login() {
    }


    @At
    @Ok("json")
    public Rs dologin(@Param("username") String username, @Param("password") String password) {

        tb_user exist = tb_userService.fetch(username);
        if (exist == null) {
            return Rs.error("用户名不存在");
        }
        tb_user user = tb_userService.fetch(username, password);
        if (user != null) {
            CurrentUserUtils.getInstance().setUser(user);
            return Rs.succcess();
        } else {
            return Rs.error("密码错误");
        }

    }


    @At
    @Ok("json")
    @Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
    public Rs dologout() {
        CurrentUserUtils.getInstance().setUser(null);
        return Rs.succcess();
    }

}
