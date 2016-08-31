package net.javablog.module;


import net.javablog.bean.A;
import net.javablog.service.*;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.Map;


@IocBean
public class IndexModule {

    @Inject
    private BlogService blogService;

    @Inject
    private TagService tagService;

    @Inject
    private SerisService serisService;

    @Inject
    private UserService userService;
    @Inject
    private IndexService indexService;


    @At("/test")
    @Ok("json")
    @Aop({"addrs"})
    public Map test() {
        return NutMap.NEW();
    }

    @At("/")
    @Ok("fm:index") // 模板文件的路径为  /WEB-INF/index.ftl
    @Aop({"addrs"})
    public Map index() {
        return indexhtml(1);
    }

    @Ok("fm:index")
    @At("/index/?")
    @Aop({"addrs"})
    public Map indexhtml(@Param(value = "pageno", df = "1") int pageno) {
        return indexService.getIndexMapdata(pageno);
    }


    @At("/json")
    @Ok("json")
    public String test(@Param("..") A a) {

        return "abc";
    }


}
