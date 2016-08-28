package net.javablog.module.adm;

import net.javablog.bean.tb_seris;
import net.javablog.service.SerisService;
import net.javablog.util.CurrentUserUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import java.util.Map;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class WSerisModule {

    @Inject
    private SerisService serisService;

    @At("/adm/wseris")
    @Ok("fm:adm.seris.wseris")
    public NutMap wseris(@Param(value = "id", df = "0") int id) {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li2");
        out.put("sidebar_activeposition", "#li2li2");
        tb_seris tb = new tb_seris();
        if (id > 0) {
            tb = serisService.fetch(id);
        }
        out.put("item", tb);
        return out;
    }

}
