package net.javablog.module.adm;

import net.javablog.init.Const;
import net.javablog.util.CurrentUserUtils;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CheckSession;

import java.util.Map;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class AdmIndexModule {

    @At("/adm/index")
    @Ok("fm:adm.index") // 模板文件的路径为  /WEB-INF/adm/login
    public Map index() {
        NutMap map=NutMap.NEW();
        map.setv("save_html", Const.HTML_SAVEPATH);
        return map;
    }




}
