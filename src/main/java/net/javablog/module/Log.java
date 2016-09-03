package net.javablog.module;


import net.javablog.service.LogService;
import net.javablog.util.CurrentUserUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CheckSession;

@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class Log {

    @Inject
    private LogService logService;

    @At("/log")
    @Ok("fm:log")
    public void log() {
    }


    @At("/getlog")
    @Ok("json")
    public String getlog() {
        String out = logService.getLog();
        return out;
    }


}
