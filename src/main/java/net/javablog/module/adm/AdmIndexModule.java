package net.javablog.module.adm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CheckSession;


@IocBean
@Filters(@By(type = CheckSession.class, args = {"me", "/adm/login"}))
public class AdmIndexModule {

    @At("/adm/index")
    @Ok("fm:adm.index") // 模板文件的路径为  /WEB-INF/adm/login
    public void index() {
    }

}
