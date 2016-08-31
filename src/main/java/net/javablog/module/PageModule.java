package net.javablog.module;


import net.javablog.service.BlogService;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.Map;

@IocBean
public class PageModule {

    @Inject
    private BlogService blogService;

    /**
     * 单页page， 数据直接传过去
     *
     * @param id
     * @return
     */
    @At("/page/?/?")
    @Ok("fm:page")
    @Aop({"addrs"})
    public Map page(@Param("id") int id) {
        return blogService.getpage(id);
    }

    /**
     * series中的某个单页page， 数据直接传过去
     *
     * @param id
     * @return
     */
    @At("/pages/?/?")
    @Ok("fm:pages")
    @Aop({"addrs"})
    public Map pages(@Param("id") int id) {
        return blogService.getserispage(id);
    }


}
