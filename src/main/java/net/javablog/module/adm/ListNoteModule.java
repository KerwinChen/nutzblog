package net.javablog.module.adm;

import net.javablog.bean.tb_seris;
import net.javablog.service.NoteService;
import net.javablog.service.SerisService;
import net.javablog.util.CurrentUserUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class ListNoteModule {

    @Inject
    private SerisService serisService;
    @Inject
    private NoteService noteService;

    @At("/adm/listnote")
    @Ok("fm:adm.note.listnote")
    public NutMap listnote() {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li3");
        out.put("sidebar_activeposition", "#li3li1");
        return out;
    }

    /**
     * 显示书中的章节list
     *
     * @param book_id
     * @return
     */
    @Ok("fm:adm.note.listseris")
    @At("/adm/books_mgr/showlist_in")
    public Map showlist_in(@Param("_id") int book_id, @Param(value = "isdraft", df = "0") int isdraft) {
        NutMap out = NutMap.NEW();
        out.put("sidebar_openposition", "#li3");
        out.put("sidebar_activeposition", "#li3li1");
        out.setv("book", noteService.fetch(book_id));
        out.setv("isdraft", isdraft);
        return out;
    }


    @At("/adm/book_mgr/del")
    @Ok("json")
    public String del(@Param("_id") int id) {
        int seris = serisService.count(Cnd.where("_bookid", "=", id));
        if (seris > 0) {
            return "当前笔记下还有内容没删除，不能删除该笔记。";
        }
        noteService.delete(id);
        return "ok";
    }


    @At("/adm/books_mgr/doshowlist")
    @Ok("json")
    public NutMap doshowlist(@Param("pageno") int pageno, @Param("txt_q") String txt_q, @Param("isdraft") int isdraft) {

        NutMap out = new NutMap();
        if (pageno <= 0) {
            pageno = 1;
        }

        SimpleCriteria cnd = Cnd.cri();
        cnd.desc("_id");
        if (!Strings.isBlank(txt_q)) {
            cnd.where().andLike("_booktitle", "%" + txt_q + "%");
        }

        Sql sql = Sqls.create("select  * from tb_book  $condition").setCondition(cnd);
        List<Record> tbs = noteService.getObjListByPage(sql, pageno);
        out.put("datas", tbs);

        Sql sqlcount = Sqls.create("select count(*) from tb_book $condition").setCondition(cnd);
        out.put("pages", noteService.getPageHtmlByPage(sqlcount, pageno));
        return out;

    }


    /**
     * 添加或修改   某本书中的章节
     *
     * @param book_id
     * @param seris_id
     * @return
     */
    @Ok("fm:adm.note.wseris")
    @At("/adm/books_mgr/showaddup_inlist")
    public Map showaddup_inlist(@Param("book_id") int book_id, @Param(value = "seris_id", df = "0") int seris_id) {
        NutMap out = NutMap.NEW();
        out.setv("book_id", book_id);
        out.setv("book", noteService.fetch(book_id));
        out.setv("seris_id", seris_id);
        tb_seris tb_seris = new tb_seris();
        if (seris_id > 0) {
            tb_seris = serisService.fetch(seris_id);
        }
        out.setv("item", tb_seris);
        return out;
    }


    /**
     * 查询出来   某本书中的所有章节
     *
     * @param book_id
     * @return
     */
    @At("/adm/books_mgr/doshowlist_in")
    @Ok("json")
    public Map doshowlist_in(@Param("_bookid") int book_id) {
        NutMap out = NutMap.NEW();
        List<tb_seris> tbs = serisService.query(Cnd.where("_bookid", "=", book_id).orderBy("_index_inbook", "asc"), null);
        out.setv("datas", tbs);
        return out;
    }


}
