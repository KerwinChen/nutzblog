package net.javablog.module;

import net.javablog.bean.User;
import net.javablog.service.UserService;
import org.nutz.aop.interceptor.ioc.TransAop;
import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;


@IocBean
@At("/user")
@Ok("json:{locked:'password|salt',ignoreNull:true}")
@Fail("http:500")

@Filters(@By(type = CheckSession.class, args = {"me", "/"}))
public class UserModule extends BaseModule {

    @Inject
    protected UserService userService;


    @At
//    @Filters()
    public int test500() {
        int i = 1 / 0;
        return i;
    }


    @At
//    @Filters()
    public int count() {
        Log log = Logs.get();
        log.info("test count");
        return dao.count(User.class);
    }


    //index.jsp 就是登陆页面
    //登陆成功后会重定向到 /user，就显示了  jsp.user.list 页面了
    @At("/")
    @Ok("jsp:jsp.user.list") // 真实路径是 /WEB-INF/jsp/user/list.jsp
    public void index() {
    }


//      使用shiro
//    @At
//    @Filters // 覆盖UserModule类的@Filter设置,因为登陆可不能要求是个已经登陆的Session
//    @POST
//    public Object login(@Param("username") String username,
//                        @Param("password") String password,
//                        @Param("captcha") String captcha,
//                        @Attr(scope = Scope.SESSION, value = "nutz_captcha") String _captcha,
//                        HttpSession session) {
//        NutMap re = new NutMap();
//        if (!Toolkit.checkCaptcha(_captcha, captcha)) {
//            return re.setv("ok", false).setv("msg", "验证码错误");
//        }
//        int userId = userService.fetch(username, password);
//        if (userId < 0) {
//            return re.setv("ok", false).setv("msg", "用户名或密码错误");
//        } else {
//            session.setAttribute("me", userId);
//            return re.setv("ok", true);
//        }
//    }
//
//    @At
//    @Ok(">>:/")
//    public void logout(HttpSession session) {
//        session.invalidate();
//    }

    @At
    public Object add(@Param("..") User user) { // 两个点号是按对象属性一一设置
        NutMap re = new NutMap();
        String msg = checkUser(user, true);
        if (msg != null) {
            return re.setv("ok", false).setv("msg", msg);
        }
        user = userService.add(user.getName(), user.getPassword());
        return re.setv("ok", true).setv("data", user);
    }

    @At
    public Object update(@Param("password") String password, @Attr("me") int me) {
        if (Strings.isBlank(password) || password.length() < 6)
            return new NutMap().setv("ok", false).setv("msg", "密码不符合要求");
        userService.updatePassword(me, password);
        return new NutMap().setv("ok", true);
    }


    @At
    @Aop(TransAop.READ_COMMITTED)
    public Object delete(@Param("id") int id, @Attr("me") int me) {
        if (me == id) {
            return new NutMap().setv("ok", false).setv("msg", "不能删除当前用户!!");
        }
        dao.delete(User.class, id); // 再严谨一些的话,需要判断是否为>0
        return new NutMap().setv("ok", true);
    }


//    直接查询
//    http://127.0.0.1:8080/nutzbook/user/query
//    带条件查询
//    http://127.0.0.1:8080/nutzbook/user/query?name=ad
//    带分页查询
//    http://127.0.0.1:8080/nutzbook/user/query?pageNumber=1&pageSize=2

    @At
    public Object query(@Param("name") String name, @Param("..") Pager pager) {
        Cnd cnd = Strings.isBlank(name) ? null : Cnd.where("name", "like", "%" + name + "%");
        QueryResult qr = new QueryResult();
        qr.setList(dao.query(User.class, cnd, pager));
        pager.setRecordCount(dao.count(User.class, cnd));
        qr.setPager(pager);
        return qr; //默认分页是第1页,每页20条
    }

    protected String checkUser(User user, boolean create) {
        if (user == null) {
            return "空对象";
        }
        if (create) {
            if (Strings.isBlank(user.getName()) || Strings.isBlank(user.getPassword()))
                return "用户名/密码不能为空";
        } else {
            if (Strings.isBlank(user.getPassword()))
                return "密码不能为空";
        }
        String passwd = user.getPassword().trim();
        if (6 > passwd.length() || passwd.length() > 12) {
            return "密码长度错误";
        }
        user.setPassword(passwd);
        if (create) {
            int count = dao.count(User.class, Cnd.where("name", "=", user.getName()));
            if (count != 0) {
                return "用户名已经存在";
            }
        } else {
            if (user.getId() < 1) {
                return "用户Id非法";
            }
        }
        if (user.getName() != null)
            user.setName(user.getName().trim());
        return null;
    }


    @GET
    @At("/login")
    @Ok("jsp:jsp.user.login")
    public void loginPage() {
    }


}