package net.javablog.module.adm;

import net.javablog.bean.tb_config;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.FTPUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import java.util.Map;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class SettingsModule {

    @Inject
    private NutDao dao;

    @At("/adm/settings")
    @Ok("fm:adm.settings")
    public NutMap settings() {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li5");

        dao.query(tb_config.class, null);
        return out;
    }

    @At("/adm/save_ftp")
    @Ok("json")
    public Map save_ftp(@Param("ftp_ip") String ftpip, @Param("ftp_user") String ftp_user, @Param("ftp_pwd") String ftp_pwd) {
        NutMap out = NutMap.NEW();
        if (!FTPUtil.testconn(ftpip, ftp_user, ftp_pwd)) {
            out.setv("status", "连接失败");
            return out;
        }
        dao.clear(tb_config.class, Cnd.where("k", "like", "ftp%"));
        tb_config c1 = new tb_config("ftp_ip", ftpip);
        tb_config c2 = new tb_config("ftp_user", ftp_user);
        tb_config c3 = new tb_config("ftp_pwd", ftp_pwd);
        dao.insert(c1);
        dao.insert(c2);
        dao.insert(c3);

        out.setv("status", "ok");
        return out;
    }


}
