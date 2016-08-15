package net.javablog.module.adm;

import net.javablog.util.CurrentUserUtils;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CheckSession;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class WBlogModule {

    @At("/adm/wblog")
    @Ok("fm:adm.wblog")
    public NutMap wblog() {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li1");
        out.put("sidebar_activeposition", "#li1li2");
        return out;
    }
    
}
