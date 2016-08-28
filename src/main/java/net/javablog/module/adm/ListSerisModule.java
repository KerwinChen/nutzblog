package net.javablog.module.adm;

import net.javablog.bean.*;
import net.javablog.service.BlogService;
import net.javablog.service.NoteService;
import net.javablog.service.SerisService;
import net.javablog.service.TagService;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.Translates;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import java.util.*;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class ListSerisModule {

    @Inject
    private SerisService serisService;
    @Inject
    private BlogService blogService;
    @Inject
    private NoteService noteService;
    @Inject
    private TagService tagService;

    @At("/adm/listseris")
    @Ok("fm:adm.seris.listseris")
    public NutMap listseris() {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li2");
        out.put("sidebar_activeposition", "#li2li1");
        return out;
    }


    @At("/adm/seris_mgr/doshowlist")
    @Ok("json")
    public NutMap doshowlist(@Param("pageno") int pageno, @Param("txt_q") String txt_q, @Param("isdraft") int isdraft) {

        NutMap out = new NutMap();
        if (pageno <= 0) {
            pageno = 1;
        }

        SimpleCriteria cnd = Cnd.cri();
        cnd.where().andLTE("_bookid", 0);
        cnd.desc("_id");
        if (!Strings.isBlank(txt_q)) {
            cnd.where().andLike("_seristitle", "%" + txt_q + "%");
        }

        Sql sql = Sqls.create("select  * from tb_seris  $condition").setCondition(cnd);
        List<Record> tbs = blogService.getObjListByPage(sql, pageno);
        out.put("datas", tbs);

        Sql sqlcount = Sqls.create("select count(*) from tb_seris $condition").setCondition(cnd);
        out.put("pages", blogService.getPageHtmlByPage(sqlcount, pageno));
        return out;

    }

    @At("/adm/seris_mgr/showlist_in")
    @Ok("fm:adm.seris.listblog")
    public Map showlist_in(@Param(value = "isdraft", df = "0") int isdraft, @Param("_id") int seris_id, @Param(value = "book_id", df = "0") int book_id) {
        NutMap out = NutMap.NEW();
        out.put("sidebar_openposition", "#li2");
        out.put("sidebar_activeposition", "#li2li1");
        if (book_id > 0) {
            out.put("sidebar_openposition", "#li3");
            out.put("sidebar_activeposition", "#li3li1");
        }

        out.setv("s", serisService.fetch(seris_id));
        out.setv("b", noteService.fetch(book_id) == null ? new tb_book() : noteService.fetch(book_id));
        out.setv("isdraft", isdraft);

        return out;
    }


    //列出指定serisid 下面的文章列表页面
    @At("/adm/seris_mgr/doshowlist_in")
    @Ok("json")
    public Map doshowlist_in(@Param("isdraft") int isdraft, @Param("_serisid") int seris_id, @Param("pageno") int pageno) {
        Map out = new HashMap();
        Pager pager = new Pager();

        if (pageno <= 0) {
            pageno = 1;
        }
        pager.setPageNumber(pageno);

        Cnd cnd = Cnd.where("_serisid", "=", seris_id).and("_isdraft", "=", isdraft);
        cnd.asc("_index_inseris");

        Sql sqllist = Sqls.create("select * from  tb_singlepage $condition").setCondition(cnd);
        Sql sqlcount = Sqls.create("select count(*) from  tb_singlepage $condition").setCondition(cnd);

        out.put("datas", serisService.getObjListByPage(sqllist, pageno));
        out.put("pages", serisService.getPageHtmlByPage(sqlcount, pageno));

        return out;
    }

    @At("/adm/seris_mgr/showaddup_inlist")
    @Ok("fm:adm.seris.wblog")
    public Map showaddup_inlist(@Param("seris_id") int seris_id, @Param("book_id") int book_id, @Param(value = "single_id", df = "0") int single_id) {

        NutMap out = NutMap.NEW();
        out.put("sidebar_openposition", "#li2");
        out.put("sidebar_activeposition", "#li2li1");

        if (book_id > 0) {
            out.put("sidebar_openposition", "#li3");
            out.put("sidebar_activeposition", "#li3li1");
        }

        out.setv("b", noteService.fetch(book_id) == null ? new tb_book() : noteService.fetch(book_id));
        out.setv("s", serisService.fetch(seris_id));
        out.setv("single_id", single_id);

        tb_singlepage singlepage = new tb_singlepage();
        if (single_id > 0) {
            singlepage = blogService.fetch(single_id);
        }
        out.setv("single", singlepage);
        return out;
    }




}
