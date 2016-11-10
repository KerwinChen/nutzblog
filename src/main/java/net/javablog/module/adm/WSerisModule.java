package net.javablog.module.adm;

import net.javablog.bean.tb_seris;
import net.javablog.bean.tb_singlepage;
import net.javablog.bean.tb_tag;
import net.javablog.bean.tb_user;
import net.javablog.service.BlogService;
import net.javablog.service.SerisService;
import net.javablog.service.TagService;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.RunES_IndexJob;
import net.javablog.util.Translates;
import net.javablog.util.Util;
import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
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
public class WSerisModule {

    @Inject
    private SerisService serisService;

    @Inject
    private BlogService blogService;

    @Inject
    private TagService tagService;

    @At("/adm/wseris")
    @Ok("fm:adm.seris.wseris")
    public NutMap wseris(@Param(value = "_id", df = "0") int id) {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li2");
        out.put("sidebar_activeposition", "#li2li2");
        tb_seris tb = new tb_seris();
        if (id > 0) {
            tb = serisService.fetch(id);
        }
        out.put("item", tb);
        return out;
    }

    @At("/adm/seris_mgr/doaddup")
    @Ok("json")
    public String doaddup(@Param("..") tb_seris tb) {
        tb.setUt(new Date());
        tb.set_seristitleen(Translates.trans(tb.get_seristitle()));
        if (tb.get_id() > 0) {
            serisService.update(tb);
        } else {
            tb.setCt(new Date());
            serisService.insert(tb);
        }
        return tb.get_id() + "";
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


    @At("/adm/seris_mgr/doaddup_inlist")
    @Ok("json")
    public Map doaddup_inlist(@Param("..") final tb_singlepage tbin) {

        NutMap map = NutMap.NEW();
        tbin.setUt(new Date());
        tbin.set_content_html(Util.processH2(tbin.get_content_html()));
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
                    t.setUt(new Date());
                    t.setCt(new Date());
                    tagService.insert(t);
                }
            }
        }


        tb_singlepage count = blogService.fetch(Cnd.where("_serisid", "=", tbin.get_serisid()).and("_id", "!=", tbin.get_id()).orderBy("_index_inseris", "desc"));
        if (count == null) {
            tbin.set_index_inseris(1);
        } else {
            tbin.set_index_inseris(count.get_index_inseris() + 1);
        }
        
        if (tbin.get_id() > 0) {
            tbin.setUt(new Date());
            blogService.update(tbin);
        } else {
            tbin.setCt(new Date());
            blogService.insert(tbin);
        }

        map.put("status", "ok");
        map.put("item", tbin);

        Map rec = Lang.obj2map(tbin);
        Record record = new Record();
        record.putAll(rec);

        RunES_IndexJob.createIndex(record);
        return map;

    }

}
