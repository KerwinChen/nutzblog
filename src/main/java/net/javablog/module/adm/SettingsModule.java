package net.javablog.module.adm;

import net.javablog.bean.tb_config;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.FTPUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import java.util.List;
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

        List<tb_config> configs = dao.query(tb_config.class, null);
        if (!Lang.isEmpty(configs)) {
            out.setv("ftp_ip", getV("ftp_ip", configs));
            out.setv("ftp_user", getV("ftp_user", configs));
            out.setv("ftp_pwd", getV("ftp_pwd", configs));

            out.setv("admin", getV("admin", configs));
            out.setv("admin_photo", getV("admin_photo", configs));
            out.setv("admin_email", getV("admin_email", configs));
            out.setv("admin_github", getV("admin_github", configs));

            out.setv("site_name", getV("site_name", configs));
            out.setv("site_logo", getV("site_logo", configs));
            out.setv("site_fav", getV("site_fav", configs));
            out.setv("site_createtime", getV("site_createtime", configs));
            out.setv("site_aboutme", getV("site_aboutme", configs));
            out.setv("site_aboutme_md", getV("site_aboutme_md", configs));
            out.setv("site_tj", getV("site_tj", configs));
            out.setv("site_msgboard", getV("site_msgboard", configs));
        }
        return out;
    }

    private String getV(String k_input, List<tb_config> configs) {
        for (int i = 0; i < configs.size(); i++) {
            String k = configs.get(i).getK();
            if (k.equals(k_input)) {
                return configs.get(i).getV();
            }
        }
        return "";
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


    @At("/adm/save_admin")
    @Ok("json")
    public Map save_ftp(@Param("admin") String admin, @Param("admin_photo") String admin_photo, @Param("admin_email") String admin_email, @Param("admin_github") String admin_github) {
        NutMap out = NutMap.NEW();

        dao.clear(tb_config.class, Cnd.where("k", "like", "admin%"));
        tb_config c1 = new tb_config("admin", admin);
        tb_config c2 = new tb_config("admin_email", admin_email);
        tb_config c3 = new tb_config("admin_github", admin_github);
        tb_config c4 = new tb_config("admin_photo", admin_photo);
        dao.insert(c1);
        dao.insert(c2);
        dao.insert(c3);
        dao.insert(c4);
        out.setv("status", "ok");
        return out;
    }

    @At("/adm/save_site")
    @Ok("json")
    public Map save_site(@Param("site_name") String site_name, @Param("site_logo") String site_logo,
                         @Param("site_fav") String site_fav, @Param("site_createtime") String site_createtime,
                         @Param("site_aboutme") String site_aboutme,@Param("site_aboutme_md") String site_aboutme_md, @Param("site_tj") String site_tj,
                         @Param("site_msgboard") String site_msgboard) {
        NutMap out = NutMap.NEW();

        dao.clear(tb_config.class, Cnd.where("k", "like", "admin%"));
        tb_config c1 = new tb_config("admin", site_logo);
        tb_config c2 = new tb_config("admin_email", site_fav);
        tb_config c3 = new tb_config("admin_github", site_createtime);
        tb_config c4 = new tb_config("site_aboutme", site_aboutme);
        tb_config c40 = new tb_config("site_aboutme_md", site_aboutme_md);
        tb_config c5 = new tb_config("site_tj", site_tj);
        tb_config c6 = new tb_config("site_msgboard", site_msgboard);
        dao.insert(c1);
        dao.insert(c2);
        dao.insert(c3);
        dao.insert(c4);
        dao.insert(c40);
        dao.insert(c5);
        dao.insert(c6);
        out.setv("status", "ok");
        return out;
    }


}
