package net.javablog.module;


import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import java.util.Map;

@IocBean
public class TestModule {

    @At("/testpage")
    @Ok("fm:testpage") // 模板文件的路径为  /WEB-INF/testpage.ftl
    public Map testpage() {
        return NutMap.NEW();
    }




}
