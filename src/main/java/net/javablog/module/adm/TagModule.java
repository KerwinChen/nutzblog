package net.javablog.module.adm;


import net.javablog.bean.tb_tag;
import net.javablog.service.TagService;
import net.javablog.util.CurrentUserUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import java.util.*;

@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class TagModule {

    @Inject
    private TagService tagService;


    @At("/adm/listtag")
    @Ok("fm:adm.tag.listtag")
    public NutMap listtag() {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li4");
        return out;
    }

    @At("/adm/tag_mgr/doshowlist_tag")
    @Ok("json")
    public Map doshowlist(@Param("pageno") int pageno, @Param("txt_q") String txt_q) {

        NutMap out = NutMap.NEW();
        Pager pager = new Pager();
        if (pageno <= 0) {
            pageno = 1;
        }
        pager.setPageNumber(pageno);

        Cnd cnd = Cnd.NEW();
        if (!Strings.isBlank(txt_q)) {
            cnd.and("_name", "like", "%" + txt_q.trim() + "%");
            cnd.or("_pname", "like", "%" + txt_q.trim() + "%");
        }
        cnd.orderBy("_pname", "asc");

        Sql sqllist = Sqls.create("select * from tb_tag $condition").setCondition(cnd);
        Sql sqlcount = Sqls.create("select count(*) from tb_tag $condition").setCondition(cnd);

        out.setv("pages", tagService.getPageHtmlByPage(sqlcount, pageno));
        out.setv("datas", tagService.getObjListByPage(sqllist, pageno));
        return out;
    }


    @Ok("json")
    @At("/adm/tag_mgr/del")
    public String del_tag(@Param("_id") int _id) {
        tagService.delete(_id);
        return "ok";
    }


    @At("/adm/tag_mgr/showaddup")
    @Ok("fm:adm.tag.wtag")
    public Map showaddup(@Param(value = "_id", df = "0") int _id) {
        NutMap out = NutMap.NEW();
        out.put("sidebar_openposition", "#li4");
        tb_tag tag = new tb_tag();
        if (_id > 0) {
            tag = tagService.fetch(_id);
        }
        out.setv("item", tag);

        List<tb_tag> all = tagService.query();
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> all_ = new HashSet<>();


        if (!Lang.isEmpty(all)) {
            for (int i = 0; i < all.size(); i++) {
                all_.add(all.get(i).get_pname());
            }
            for (String pname : all_) {
                stringBuilder.append("\"" + pname + "\",");
            }
        }
        out.setv("_pnames", "[" + stringBuilder.toString() + "]");
        return out;
    }

    @At("/adm/tag_mgr/doaddup")
    @Ok("json")
    public Map doaddup(@Param("..") tb_tag tag) {

        NutMap map = NutMap.NEW();
        //_name 需要是唯一的
        tb_tag ori = tagService.fetch(Cnd.where("_name", "=", tag.get_name()));

        if (ori != null) {
            if (ori.get_id() != tag.get_id()) {
                map.put("status", "标签名已经存在");
                return map;
            }
        }

        tag.setUt(new Date());
        if (tag.get_id() > 0) {
            tagService.update(tag);
        } else {
            tag.setCt(new Date());
            tagService.insert(tag);
        }
        map.put("item", tag);
        map.put("status", "ok");
        return map;
    }


}
