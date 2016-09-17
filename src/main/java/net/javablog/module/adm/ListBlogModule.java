package net.javablog.module.adm;

import net.javablog.bean.tb_singlepage;
import net.javablog.bean.tb_tag;
import net.javablog.bean.tb_user;
import net.javablog.service.BlogService;
import net.javablog.service.TagService;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.Translates;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import java.util.Date;
import java.util.List;
import java.util.Map;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class ListBlogModule {


    @Inject
    private BlogService blogService;

    @Inject
    private TagService tagService;

    @At("/adm/listblog")
    @Ok("fm:adm.blog.listblog")
    public NutMap listblog(@Param(value = "_serisid", df = "0") int serisid, @Param(value = "_bookid", df = "0") int book_id) {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li1");
        out.put("sidebar_activeposition", "#li1li1");

        if (book_id > 0) {
            out.put("sidebar_openposition", "#li3");
            out.put("sidebar_activeposition", "#li3li1");
        }

        return out;
    }


    @At("/adm/single_mgr/del")
    @Ok("json")
    public String del(@Param("id") int id) {
        blogService.delete(id);
        return "ok";
    }


    @At("/adm/single_mgr/doshowlist")
    @Ok("json")
    public NutMap doshowlist(@Param("pageno") int pageno, @Param("txt_q") String txt_q) {

        NutMap out = new NutMap();
        if (pageno <= 0) {
            pageno = 1;
        }

        Cnd cnd = Cnd.where("_serisid", "<=", 0);
        if (!Strings.isBlank(txt_q)) {
            cnd.and("_title", "like", "%" + txt_q + "%");
        }
        cnd.desc("_id");

        Sql sql = Sqls.create("select  _id,_title,_titleen, _isdraft , ut from tb_singlepage  $condition").setCondition(cnd);
        List<Record> tbs = blogService.getObjListByPage(sql, pageno);
        out.put("datas", tbs);

        Sql sqlcount = Sqls.create("select count(_id) from tb_singlepage $condition").setCondition(cnd);
        out.put("pages", blogService.getPageHtmlByPage(sqlcount, pageno));

        return out;
    }


}
