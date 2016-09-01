package net.javablog.module;


import net.javablog.bean.tb_config;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.HashMap;
import java.util.Map;

@IocBean
public class MeModule {

    @Inject
    private NutDao dao;

    @At("/me")
    @Ok("fm:me")
    @Aop({"addrs"})
    public Map me() {
        tb_config c = dao.fetch(tb_config.class, Cnd.where("k", "=", "site_aboutme"));
        Map out = new HashMap<>();
        out.put("rs", c.getV());
        return out;
    }


}
