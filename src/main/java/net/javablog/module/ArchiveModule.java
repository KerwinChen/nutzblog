package net.javablog.module;


import net.javablog.service.ArchiveService;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import java.util.Map;

@IocBean
public class ArchiveModule {

    @Inject
    private ArchiveService archiveService;


        @At("/archive")
        @Ok("fm:archive")
        @Aop({"addrs"})
        public Map showarchive() {
            return archiveService.data();
    }


}
