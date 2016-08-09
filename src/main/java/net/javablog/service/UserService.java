package net.javablog.service;

import net.javablog.bean.User;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.random.R;
import org.nutz.service.IdNameEntityService;

import java.util.Date;

@IocBean(fields = "dao")
public class UserService extends IdNameEntityService<User> {

    public User add(String name, String password) {
        User user = new User();
        user.setName(name.trim());
        user.setSalt(R.UU16());
        user.setPassword(Lang.md5(password + user.getSalt()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return dao().insert(user);
    }

    public int fetch(String username, String password) {
        User user = fetch(username);
        if (user == null) {
            return -1;
        }
        String _pass = Lang.md5(password + user.getSalt());
        if (_pass.equalsIgnoreCase(user.getPassword())) {
            return user.getId();
        }
        return -1;
    }

    public void updatePassword(int userId, String password) {
        User user = fetch(userId);
        if (user == null) {
            return;
        }
        user.setSalt(R.UU16());
        user.setPassword(Lang.md5(password + user.getSalt()));
        user.setUpdateTime(new Date());
        dao().update(user, "^(password|salt|updateTime)$");
    }


}