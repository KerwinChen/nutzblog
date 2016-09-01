package net.javablog.module;


import net.javablog.service.TagService;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import java.util.Map;

@IocBean
public class TagsModule {

    @Inject
    private TagService tagService;

    @At("/tags")
    @Ok("fm:tags")
    @Aop({"addrs"})
    public Map tags() {
        return tagService.getdata();
    }

}
