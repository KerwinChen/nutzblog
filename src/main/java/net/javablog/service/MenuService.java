package net.javablog.service;

import net.javablog.bean.tb_book;
import net.javablog.bean.tb_seris;
import net.javablog.bean.tb_singlepage;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IocBean
public class MenuService {
    @Inject
    private BlogService blogService;

    @Inject
    private SerisService serisService;

    @Inject
    private NoteService noteService;

    public Map getseris(int serisid) {
        Map out = new HashMap<>();
        tb_seris tb = serisService.fetch(serisid);
        out.put("s", tb);
        List<tb_singlepage> pages = blogService.query(Cnd.where("_isdraft", "=", 0).and("_serisid", "=", serisid).orderBy("_index_inseris", "asc"));
        out.put("singlelist", pages);
        return out;
    }

    /**
     * 根据book的id，查询书目录
     *
     * @param bookid
     * @return
     */
    public Map getbook(int bookid) {
        Map out = new HashMap<>();
        //检查是否属于某一本书中，如果是的话，需要显示左侧的目录菜单
        tb_book tb_book = noteService.fetch(bookid);
        out.put("book", tb_book);

        //获取章节， Pages
        StringBuffer stringBuffer = new StringBuffer();
        Map<tb_seris, List<tb_singlepage>> menu = new HashMap<tb_seris, List<tb_singlepage>>();
        if (tb_book.get_id() > 0) {
            List<tb_seris> serises = serisService.query(Cnd.where("_bookid", "=", tb_book.get_id()).and("_isdraft", "=", 0).orderBy("_index_inbook", "asc"));
            if (!Lang.isEmpty(serises)) {
                for (int i = 0; i < serises.size(); i++) {
                    int index = i;
                    index++;
                    tb_seris s = serises.get(i);
                    List<tb_singlepage> pages = blogService.query(Cnd.where("_serisid", "=", s.get_id()).and("_isdraft", "=", 0).orderBy("_index_inseris", "asc"));
//                    if (!Lang.isEmpty(pages))
                    {
                        menu.put(s, pages);

                        if (i % 3 == 0 || i == 0) {

                            stringBuffer.append("                     <div class=\"row\">\n" +
                                    "                                <div class=\"col-md-4\">\n" +
                                    "                                    <dl>\n" +
                                    "                                        <dt>第" + index + "章    " + s.get_seristitle() + "</dt>\n" +
                                    getStrPage(pages, i, 0) +
                                    "                                    </dl>\n" +
                                    "                                </div>");


                            //如果是最后一个,那么后面多加2个 空的col-md-4
                            if (i == serises.size() - 1) {
                                stringBuffer.append(
                                        "    <div class=\"col-md-8\"></div></div>\n");
                            }

                        }
                        if (i % 3 == 1 || i == 1) {
                            stringBuffer.append(
                                    "                                <div class=\"col-md-4\">\n" +
                                            "                                    <dl>\n" +
                                            "                                        <dt>第" + index + "章    " + s.get_seristitle() + "</dt>\n" +
                                            getStrPage(pages, i, 0) +
                                            "                                    </dl>\n" +
                                            "                                </div>");


                            //如果是最后一个,那么后面多加1个 空的col-md-4
                            if (i == serises.size() - 1) {
                                stringBuffer.append(
                                        "                                <div class=\"col-md-4\"></div></div>\n");
                            }

                        }
                        if (i % 3 == 2 || i == 2) {
                            stringBuffer.append(
                                    "                                <div class=\"col-md-4\">\n" +
                                            "                                    <dl>\n" +
                                            "                                        <dt>第" + index + "章    " + s.get_seristitle() + "</dt>\n" +
                                            getStrPage(pages, i, 0) +
                                            "                                    </dl>\n" +
                                            "                                </div>\n" +
                                            "                                </div>");

                        }
                    }
                }
            }

            //给<div class="row"> 结尾
//            stringBuffer.append("</div>");
        }

        out.put("menu", stringBuffer.toString());
        return out;
    }

    /**
     * @param pages
     * @param s_index
     * @param id      设置为0，就可以不显示current
     * @return
     */
    private String getStrPage(List<tb_singlepage> pages, int s_index, int id) {
        s_index++;
        StringBuffer stringBuffer = new StringBuffer();
        if (!Lang.isEmpty(pages)) {
            for (int i = 0; i < pages.size(); i++) {
                int index = i;
                index++;
                int pageid = pages.get(i).get_id();
                String title = pages.get(i).get_title();
                String titleen = pages.get(i).get_titleen();

                if (pageid == id) {
                    stringBuffer.append("<dd><a  class='current_s'  href='/pages/" + pageid + "/" + titleen + ".html'>" + s_index + "." + (index) + " " + title + "</a></dd>");
                } else {
                    stringBuffer.append("<dd><a  href='/pages/" + pageid + "/" + titleen + ".html'>" + s_index + "." + (index) + " " + title + "</a></dd>");
                }
            }
        }
        return stringBuffer.toString();
    }

}
