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


    @At("/adm/seris_mgr/del")
    @Ok("json")
    public String del(@Param("_id") int id) {
        int blogs = blogService.count(Cnd.where("_serisid", "=", id));
        if (blogs > 0) {
            return "系列教程下还有内容没删除，不能删除当前系列。";
        }
        serisService.delete(id);
        return "ok";
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

        out.setv("s", serisService.fetch(seris_id));
        out.setv("b", noteService.fetch(book_id) == null ? new tb_book() : noteService.fetch(book_id));
        out.setv("isdraft", isdraft);

        return out;
    }


    //列出指定serisid 下面的文章列表页面
    @At("/adm/seris_mgr/doshowlist_in")
    @Ok("json")
    public Map doshowlist_in(@Param("isdraft") int isdraft, @Param("seris_id") int seris_id, @Param("pageno") int pageno) {
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

        out.put("pages", serisService.getObjListByPage(sqllist, pageno));
        out.put("datas", serisService.getPageHtmlByPage(sqlcount, pageno));

        return out;
    }

    @At("/adm/seris_mgr/showaddup_inlist")
    @Ok("fm:adm.seris.wblog")
    public Map showaddup_inlist(@Param("seris_id") int seris_id, @Param("book_id") int book_id, @Param(value = "single_id", df = "0") int single_id) {

        NutMap out = NutMap.NEW();
        out.put("sidebar_openposition", "#li2");
        out.put("sidebar_activeposition", "#li2li1");

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


    @At("/adm/seris_mgr/doaddup_inlist")
    @Ok("json")
    public Map doaddup_inlist(@Param("..") final tb_singlepage tbin) {

        NutMap map = NutMap.NEW();

        tbin.set_isdraft(false);
        tbin.setUpdateTime(new Date());
        tbin.set_titleen(Translates.trans(tbin.get_title()));

        tb_user user = CurrentUserUtils.getInstance().getUser();
        tbin.set_username(user.get_username());

        //同步新增加的tag到tag表中
        if (!Strings.isBlank(tbin.get_tags())) {
            String[] arr = tbin.get_tags().split(",");
            Set<String> arr2 = new HashSet<String>(Arrays.asList(arr));
            for (String item : arr2) {
                String tag1 = item;
                if (tagService.count(Cnd.where("_name", "=", tag1.trim())) == 0) {
                    tb_tag t = new tb_tag();
                    t.set_img("");
                    t.set_intro("");
                    t.set_name(tag1);
                    t.set_pname("未分组");
                    t.setUpdateTime(new Date());
                    t.setCreateTime(new Date());
                    tagService.insert(t);
                }
            }
        }


        if (tbin.get_id() > 0) {
            blogService.update(tbin);
        } else {
            tb_singlepage count = blogService.fetch(Cnd.where("_serisid", "=", tbin.get_serisid()).and("_id", "!=", tbin.get_id()).orderBy("_index_inseris", "desc"));
            if (count == null) {
                tbin.set_index_inseris(1);
            } else {
                tbin.set_index_inseris(count.get_index_inseris() + 1);
            }
            blogService.insert(tbin);
        }

        map.put("status", "ok");
        map.put("item", tbin);


        return map;


    }


}
