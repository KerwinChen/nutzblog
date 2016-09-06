package net.javablog.service;

import net.javablog.bean.tb_config;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

@IocBean(fields = "dao")
public class ConfigService extends BaseService<tb_config> {


    public Object get_byName(String k) {
        return fetch(Cnd.where("k", "=", k)).getV();
    }

    public String getIP() {
        return fetch(Cnd.where("k", "=", "ftp_ip")).getV();
    }

    public String getUser() {
        return fetch(Cnd.where("k", "=", "ftp_user")).getV();
    }

    public String getPwd() {
        return fetch(Cnd.where("k", "=", "ftp_pwd")).getV();
    }


    public String get_site_aboutme() {
        return fetch(Cnd.where("k", "=", "site_aboutme")).getV();
    }

    /**
     * 返回的是字符串  类似 2015-01-01
     *
     * @return
     */
    public String getCreateTime() {
        String date = fetch(Cnd.where("k", "=", "site_createtime")).getV();
        if (Strings.isBlank(date)) {
            date = "2015-01-01";
        }
        return date;

    }


}
