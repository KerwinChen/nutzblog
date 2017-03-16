package net.javablog.module.adm;

import net.javablog.bean.tb_singlepage;
import net.javablog.bean.tb_tag;
import net.javablog.bean.tb_user;
import net.javablog.module.adm.html.HtmlModule;
import net.javablog.service.BlogService;
import net.javablog.service.TagService;
import net.javablog.util.*;
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
public class WBlogModule {

    @Inject
    private BlogService blogService;

    @Inject
    private TagService tagService;

    @Inject
    private HtmlModule htmlModule;

    @At("/adm/single_mgr/doaddup")
    @Ok("json")
    public Map doaddup(@Param("..") tb_singlepage tbin, @Param("auto") final boolean auto) {

        HashMap map = new HashMap();

        tbin.set_content_html(Util.processH2(tbin.get_content_html()));
        tbin.set_titleen(Translates.trans(tbin.get_title()));
        tb_user user = CurrentUserUtils.getInstance().getUser();
        tbin.set_username(user.get_username());
        tbin.set_tags(trimstr(tbin.get_tags()));
        tbin.set_content_html(add_ablank(tbin.get_content_html()));


        // 初始页面不是 草稿.   自动保存会被变成草稿.
        if (tbin.get_id() == 0) {
            tbin.setCt(new Date());
            tbin.setUt(new Date());
            tbin = blogService.insert(tbin);
        } else {
            tb_singlepage tbexist = blogService.fetch(tbin.get_id());
            tbin.setUt(tbexist.getUt());
            if (!auto) {
                tbin.setUt(new Date());
            } else {
                tbin.setUt(tbexist.getUt());
            }
            tbin.setCt(tbexist.getCt());
            blogService.update(tbin);
        }

        //同步新增加的tag到tag表中
        if (!Strings.isBlank(tbin.get_tags())) {
            String[] arr = tbin.get_tags().split(",");
            Set<String> arr2 = new HashSet<String>(Arrays.asList(arr));

            for (String item : arr2) {
                String tag1 = item.trim();
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

        map.put("status", "ok");
        map.put("item", tbin);
        Map m = Lang.obj2map(tbin);
        Record record = new Record();
        record.putAll(m);

        RunES_IndexJob.createIndex(record);

        htmlModule.html("single", tbin.get_id());

        return map;

    }

    private String trimstr(String tags) {
        String[] arr = tags.split(",");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Strings.trim(arr[i]);
        }
        return String.join(",", arr);
    }

    private String add_ablank(String _content_html) {
        if (!Strings.isBlank(_content_html)) {
            List<String> lista = JsoupBiz.getList_OutHtml(_content_html, "a");
            if (!Lang.isEmpty(lista)) {
                for (int i = 0; i < lista.size(); i++) {
                    String ahtml = lista.get(i);
                    String href = JsoupBiz.getOne_Attr(ahtml, "a", "href");
                    String txt = JsoupBiz.getOne_Text(ahtml, "a");
                    String newhtml = "<a href='" + href + "' target='_blank'>" + txt + "</a>";
                    _content_html = _content_html.replace(ahtml, newhtml);
                }
            }
        }
        return _content_html;
    }

    @At("/adm/wblog")
    @Ok("fm:adm.blog.wblog")
    public NutMap wblog(@Param(value = "_id", df = "0") int id) {
        NutMap out = new NutMap();
        out.put("sidebar_openposition", "#li1");
        out.put("sidebar_activeposition", "#li1li2");

        tb_singlepage tb = new tb_singlepage();
        tb.set_username(CurrentUserUtils.getInstance().getUser().get_username());
        if (id > 0) {
            tb = blogService.fetch(id);
        }
        out.put("item", tb);
        tb.set_isdraft(false);

        List<tb_tag> tags = tagService.query();
        out.put("_tags", tags);

        return out;
    }


}
