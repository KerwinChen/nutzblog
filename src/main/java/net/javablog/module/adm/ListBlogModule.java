package net.javablog.module.adm;

import net.javablog.bean.tb_singlepage;
import net.javablog.bean.tb_tag;
import net.javablog.bean.tb_user;
import net.javablog.service.BlogService;
import net.javablog.service.TagService;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.PagerUT;
import net.javablog.util.Translates;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import java.util.Date;
import java.util.HashMap;
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
    @Ok("fm:adm.listblog")
    public NutMap listblog() {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li1");
        out.put("sidebar_activeposition", "#li1li1");
        return out;
    }


    @At("/adm/single_mgr/del")
    @Ok("json")
    public String del(@Param("id") int id) {
        blogService.delete(id);
        return "ok";
    }


    @At("/adm/single_mgr/doaddup")
    @Ok("json")
    public NutMap doaddup(@Param("::") final tb_singlepage tbin) {
        NutMap map = new NutMap();


        tbin.set_isdraft(false);
        tbin.set_titleen(Translates.trans(tbin.get_title()));

        tb_user user = CurrentUserUtils.getInstance().getUser();
//        tbin.set_userid(user.get);

        tbin.setCreateTime(new Date());
        tbin.setUpdateTime(new Date());
        if (tbin.get_id() <= 0) {
            blogService.insert(tbin);
        } else {
            blogService.update(tbin);
        }

        //同步新增加的tag到tag表中
        if (!Strings.isBlank(tbin.get_tags())) {
            String[] arr = tbin.get_tags().split(",");
            for (int i = 0; i < arr.length; i++) {
                String tag1 = arr[i];
                if (tagService.count(Cnd.where("_name", "=", tag1)) == 0) {
                    tb_tag t = new tb_tag();
                    t.set_img("");
                    t.set_intro("");
                    t.set_name(tag1);
                    t.set_pname("未分组");
                    tagService.insert(t);
                }
            }
        }

        map.put("status", "ok");
        map.put("item", tbin);
        Map m = Lang.obj2map(tbin);
        Record record = new Record();
        record.putAll(m);

        // RunES_IndexJob.createIndex(record);
        return map;
    }


    @At("/adm/single_mgr/doaddup_draft")
    @Ok("json")
    public NutMap doaddup_draft(@Param("::") final tb_singlepage tbin) {
        NutMap map = new NutMap();
        tbin.set_username(CurrentUserUtils.getInstance().getUser().get_username());
        tbin.set_titleen(Translates.trans(tbin.get_title()));
        //如果原来是正文就不要设置为草稿了
        tb_singlepage temp = blogService.fetch(tbin.get_id());
        if (temp != null) {
            boolean isdraft = blogService.fetch(tbin.get_id()).is_isdraft();
            tbin.set_isdraft(isdraft);
        } else {
            tbin.set_isdraft(true);
        }
        tbin.setCreateTime(new Date());
        if (tbin.get_id() <= 0) {
            blogService.insert(tbin);
        } else {
            tbin.setUpdateTime(new Date());
            blogService.update(tbin);
        }
        map.put("status", "ok");
        map.put("item", tbin);
        return map;
    }


    @At("/adm/single_mgr/doshowlist")
    @Ok("json")
    public NutMap doshowlist(@Param("pageno") int pageno, @Param("txt_q") String txt_q, @Param("isdraft") int isdraft) {

        NutMap out = new NutMap();
        Pager pager = new Pager();

        if (pageno <= 0) {
            pageno = 1;
        }

        Cnd cnd = Cnd.where("_serisid", "<=", 0).and("_isdraft", "=", isdraft);
        if (!Strings.isBlank(txt_q)) {
            cnd.and("_title", "like", "%" + txt_q + "%");
        }
        cnd.desc("_id");

        Sql sql = Sqls.create("select  * from tb_singlepage  $condition").setCondition(cnd);
        List<Record> tbs = blogService.getObjListByPage(sql, pageno);
        out.put("datas", tbs);


        Sql sqlcount = Sqls.create("select count(*) from tb_singlepage $condition").setCondition(cnd);
        out.put("pages", blogService.getPageHtmlByPage(sqlcount, pageno));

        return out;
    }


}
