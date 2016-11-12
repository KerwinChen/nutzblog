package net.javablog.module;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import java.util.Map;


@IocBean
public class Test111Module {


    @At("/test111")
    @Ok("fm:test111")
    public Map tags() {
        return NutMap.NEW();
    }


}
