package net.javablog.module.adm;

import net.javablog.bean.tb_config;
import net.javablog.util.CurrentUserUtils;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;


@At("/adm")
@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class SettingsModule {

    @Inject
    private NutDao dao;

    @At
    @Ok("fm:adm.settings")
    public NutMap settings() {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li5");

        dao.query(tb_config.class,null);
        return out;
    }


}
