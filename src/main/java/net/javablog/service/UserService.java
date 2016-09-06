package net.javablog.service;

import net.javablog.bean.tb_user;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import java.util.Date;

@IocBean(fields = "dao")
public class UserService extends BaseService<tb_user> {


    public tb_user add(String name, String password) {
        tb_user user = new tb_user();
        user.set_username(name);
        user.set_password(Lang.md5(password));
        user.setCt(new Date());
        user.setUt(new Date());
        return dao().insert(user);
    }

    public tb_user fetch(String username, String password) {
        tb_user user = fetch(username);
        if (user == null) {
            return null;
        }
        String _pass = Lang.md5(password);
        if (_pass.equalsIgnoreCase(user.get_password())) {
            return user;
        }
        return null;
    }


    public void updatePassword(int userId, String password) {
        tb_user user = fetch(userId);
        if (user == null) {
            return;
        }
        user.set_password(Lang.md5(password));
        user.setUt(new Date());
        dao().update(user, "^(password|updateTime)$");
    }


}