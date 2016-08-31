package net.javablog.module;


import net.javablog.service.MenuService;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.Map;

@IocBean
public class MenuModule {

    @Inject
    private MenuService menuService;

    @At("/seris/?/?")
    @Ok("fm:menu_seris")
    @Aop({"addrs"})
    public Map show_menu_seris(@Param("serisid") int serisid) {
        return menuService.getseris(serisid);
    }

    @At("/book/?/?")
    @Ok("fm:menu_note")
    @Aop({"addrs"})
    public Map show_menu_book(@Param("bookid") int bookid) {
        return menuService.getbook(bookid);
    }


}
