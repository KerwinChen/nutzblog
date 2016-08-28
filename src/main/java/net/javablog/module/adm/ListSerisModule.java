package net.javablog.module.adm;

import net.javablog.service.BlogService;
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

import java.util.List;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class ListSerisModule {

    @Inject
    private SerisService serisService;
    @Inject
    private BlogService blogService;

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
    public String del(@Param("id") int id) {
        int blogs = blogService.count(Cnd.where("_serisid", "=", id));
        if (blogs > 0) {
            return "系列教程下还有内容没删除，不能删除当前系列。";
        }
        blogService.delete(id);
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


}
