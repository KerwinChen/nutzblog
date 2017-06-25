package net.javablog.module;


import net.javablog.util.EsUT;
import net.javablog.util.FrontPagerUT;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.Map;

@IocBean
public class SearchModule {

    @At("/search")
    @Ok("fm:search")
    @Aop({"addrs"})
    public Map search(@Param("q") String q, @Param(value = "pageno", df = "1") int pageno) {

        NutMap out = NutMap.NEW();
        int pagesize = 10;
        Map result = get(q, pagesize, pageno);

        int allcount = Integer.valueOf(result.get("allcount").toString());

        out.setv("pages", FrontPagerUT.pages(pagesize, allcount, pageno, "/search?q=" + q + "&pageno=%s", 3));
        out.setv("pages_min", FrontPagerUT.pages(pagesize, allcount, pageno, "/search?q=" + q + "&pageno=%s", 0));
        out.setv("datas", result.get("datas"));
        out.setv("allcount", result.get("allcount"));
        out.setv("q", q);
        return out;
    }


    public Map get(String q, int pagesize, int pageno) {
        return EsUT.search(q, pagesize, pageno);
    }


}
