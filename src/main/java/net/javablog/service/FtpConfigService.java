package net.javablog.service;

import net.javablog.bean.tb_config;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

@IocBean(fields = "dao")
public class FtpConfigService extends BaseService<tb_config> {


    public String getIP() {
        return fetch(Cnd.where("k", "=", "ftp_ip")).getV();
    }

    public String getUser() {
        return fetch(Cnd.where("k", "=", "ftp_user")).getV();
    }
    public String getPwd() {
        return fetch(Cnd.where("k", "=", "ftp_pwd")).getV();
    }
}
