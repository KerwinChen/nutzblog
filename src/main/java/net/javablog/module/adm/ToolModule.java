package net.javablog.module.adm;


import net.javablog.bean.tb_singlepage;
import net.javablog.util.CurrentUserUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.mvc.view.ViewWrapper;

@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class ToolModule {

    @Inject
    private NutDao dao;

    @At("/adm/lastblog")
    public View LastBlog() {

//        /adm/wblog/?_id=178   blog
//        /adm/seris_mgr/showaddup_inlist?book_id=0&seris_id=76&single_id=143
//        /adm/seris_mgr/showaddup_inlist?book_id=1&seris_id=1&single_id=1
        tb_singlepage tb = dao.fetch(tb_singlepage.class, Cnd.NEW().orderBy("ut", "desc"));
        if (tb == null) {
            //还没写文章呢 ....
            return new ViewWrapper(new ServerRedirectView("/"), null);
        }

        String out = "";
        if (tb.get_bookid() == 0 && tb.get_serisid() == 0) {
            out = "/adm/wblog/?_id=" + tb.get_id();
        }
        if (tb.get_bookid() == 0 && tb.get_serisid() > 0) {
            out = "/adm/seris_mgr/showaddup_inlist?book_id=0&seris_id=" + tb.get_serisid() + "&single_id=" + tb.get_id();
        }
        if (tb.get_bookid() > 0 && tb.get_serisid() > 0) {
            out = "/adm/seris_mgr/showaddup_inlist?book_id=" + tb.get_bookid() + "&seris_id=" + tb.get_serisid() + "&single_id=" + tb.get_id();
        }
        return new ViewWrapper(new ServerRedirectView(out), null);
    }
}
