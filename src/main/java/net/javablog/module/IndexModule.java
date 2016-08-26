package net.javablog.module;


import net.javablog.bean.A;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;


@IocBean
//@Filters(@By(type = CheckSession.class, args = {"me", "/"}))
public class IndexModule {

    @At("/")
    @Ok("fm:index") // 模板文件的路径为  /WEB-INF/index.ftl
    public void index() {
    }


    @At("/json")
    @Ok("json")
    public String test(@Param("..") A a) {

        return "abc";
    }


}
