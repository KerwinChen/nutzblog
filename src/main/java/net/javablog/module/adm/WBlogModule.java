package net.javablog.module.adm;

import net.javablog.bean.tb_singlepage;
import net.javablog.service.BlogService;
import net.javablog.util.CurrentUserUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class WBlogModule {

    @Inject
    private BlogService blogService;

    @At("/adm/wblog")
    @Ok("fm:adm.wblog")
    public NutMap wblog(@Param(value = "_id", df = "0") int id) {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li1");
        out.put("sidebar_activeposition", "#li1li2");

        tb_singlepage tb = new tb_singlepage();
        tb.set_username(CurrentUserUtils.getInstance().getUser().get_username());
        if (id > 0) {
            tb = blogService.fetch(id);
        }
        out.put("item", tb);
        return out;
    }

}
