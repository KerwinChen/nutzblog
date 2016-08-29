package net.javablog.module.adm;

import net.javablog.util.CurrentUserUtils;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CheckSession;


@At("/adm")
@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class SettingsModule {


    @At
    @Ok("fm:adm.settings")
    public NutMap settings() {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li5");
//        out.put("sidebar_activeposition", "#li4li1");
        return out;
    }

}
