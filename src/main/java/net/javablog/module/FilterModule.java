package net.javablog.module;


import net.javablog.service.IndexService;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.Map;

@IocBean
public class FilterModule {

    @Inject
    private IndexService indexService;

    /**
     * /filter/page/{single,seris,book}/1.html
     * <p/>
     * 首页直接返回html
     *
     * @return
     */
    @At("/filter/page/single/?")
    @Ok("fm:filter_page_single")
    @Aop({"addrs"})
    public Map filter_page_single(@Param(value = "pageno", df = "1") int pageno) {
        if (pageno <= 0) {
            pageno = 1;
        }
        Map out = indexService.getIndexMapdata_filter_single_seris_book("single", pageno);
        return out;
    }

    @At("/filter/page/seris/?")
    @Ok("fm:filter_page_seris")
    @Aop({"addrs"})
    public Map filter_page_seris(@Param(value = "pageno", df = "1") int pageno) {
        if (pageno <= 0) {
            pageno = 1;
        }
        Map out = indexService.getIndexMapdata_filter_single_seris_book("seris", pageno);
        return out;
    }

    @At("/filter/page/book/?")
    @Ok("fm:filter_page_book")
    @Aop({"addrs"})
    public Map filter_page_book(@Param(value = "pageno", df = "1") int pageno) {
        if (pageno <= 0) {
            pageno = 1;
        }
        Map out = indexService.getIndexMapdata_filter_single_seris_book("book", pageno);
        return out;
    }

    /**
     * /filter/tag/java/1.html
     * <p/>
     * 首页直接返回html
     *
     * @return
     */
    @At("/filter/tag/?/?")
    @Ok("fm:filter_tag")
    @Aop({"addrs"})
    public Map filter_tag(@Param("typevalue") String typevalue, @Param(value = "pageno", df = "1") int pageno) {
        if (pageno <= 0) {
            pageno = 1;
        }
        Map out = indexService.getIndexMapdata_filter("tag", typevalue, pageno);
        return out;
    }


    /**
     * /filter/month/201501/1.html
     * <p/>
     * 首页直接返回html
     *
     * @return
     */
    @At("/filter/month/?/?")
    @Ok("fm:filter_month")
    @Aop({"addrs"})
    public Map filter_month(@Param("typevalue") String typevalue, @Param(value = "pageno", df = "1") int pageno) {
        if (pageno <= 0) {
            pageno = 1;
        }
        Map out = indexService.getIndexMapdata_filter("month", typevalue, pageno);
        return out;
    }


}
