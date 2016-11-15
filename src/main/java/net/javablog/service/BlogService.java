package net.javablog.service;


import net.javablog.bean.*;
import net.javablog.init.Const;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.JsoupBiz;
import org.nutz.castor.Castors;
import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IocBean(fields = "dao")
public class BlogService extends BaseService<tb_singlepage> {


    @Inject
    private TagService tagService;

    @Inject
    private SerisService serisService;

    @Inject
    private UserService userService;

    @Inject
    private NoteService noteService;

    /**
     * 根据page的id，查询所属的系列的，以及书（如果有的话）
     *
     * @param id
     * @return
     */
    public Map getserispage(int id) {
        Map out = new HashMap<>();

        tb_singlepage tb = fetch(id);
        out.put("item", tb);

        //如果当前登录用户是admin，就显示出来，编辑和删除按钮。
        out.put("showadm", "");
        try {
            tb_user user = CurrentUserUtils.getInstance().getUser();
            if (user != null && user.get_username().equals("admin")) {
                out.put("showadm", "true");
            }
        } catch (Exception e) {
        }


        List<String> allh2 = JsoupBiz.getList_OutHtml(tb.get_content_html(), "h2");
        String h2_nav = toHtml(allh2);
        out.put("rightnav", h2_nav);

        //系列的标题和简介
        int sid = tb.get_serisid();
        tb_seris tbs = serisService.fetch(sid);
        out.put("s", tbs);


        //系列下的所有内容
        List<tb_singlepage> singlelist = query(Cnd.where("_serisid", "=", sid).and("_isdraft", "=", 0).orderBy("_index_inseris", "asc"));
        List<Record> res = toRecs(singlelist);

        out.put("start", geststart(tb, singlelist));
        out.put("end", singlelist.size());

        //追加user属性
        for (int i = 0; i < res.size(); i++) {
            res.get(i).put("username", Const.admin);
            res.get(i).put("userphoto", Const.admin_photo);
        }
        out.put("singlelist", res);

        //检查是否属于某一本书中，如果是的话，需要显示左侧的目录菜单
        tb_book tb_book = new tb_book();
        if (tbs.get_id() > 0) {
            int _bookid = tbs.get_bookid();
            if (_bookid > 0) {
                tb_book = noteService.fetch(_bookid);
            }
        }
        out.put("book", tb_book);

        //章节， Pages
        StringBuffer stringBuffer = new StringBuffer();
        Map<tb_seris, List<tb_singlepage>> menu = new HashMap<tb_seris, List<tb_singlepage>>();
        if (tb_book.get_id() > 0) {
            List<tb_seris> serises = serisService.query(Cnd.where("_bookid", "=", tb_book.get_id()).and("_isdraft", "=", 0).orderBy("_index_inbook", "asc"));
            if (!Lang.isEmpty(serises)) {
                for (int i = 0; i < serises.size(); i++) {
                    int index = i;
                    index++;
                    tb_seris s = serises.get(i);
                    List<tb_singlepage> pages = query(Cnd.where("_serisid", "=", s.get_id()).and("_isdraft", "=", 0).orderBy("_index_inseris", "asc"));
//                    if (!Lang.isEmpty(pages))
                    {
                        menu.put(s, pages);

                        if (i % 3 == 0 || i == 0) {

                            stringBuffer.append("                     <div class=\"row\">\n" +
                                    "                                <div class=\"col-md-4\">\n" +
                                    "                                    <dl>\n" +
                                    "                                        <dt>第" + index + "章    " + s.get_seristitle() + "</dt>\n" +
                                    getStrPage(pages, i, id) +
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
                                            getStrPage(pages, i, id) +
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
                                            getStrPage(pages, i, id) +
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

        String strtag = getTagStr(tb);
        out.put("tags", strtag);
        out.put("username", Const.admin);
        out.put("userphoto", Const.admin_photo);

        return out;
    }


    /**
     * 返回tagstr对应的字符串
     *
     * @param tb
     * @return
     */
    public String getTagStr(tb_singlepage tb) {
        StringBuffer strtag = new StringBuffer();
        if (!Strings.isBlank(tb.get_tags())) {
            String[] arr = tb.get_tags().split(",");
            if (!Lang.isEmpty(arr)) {
                for (int i = 0; i < arr.length; i++) {

                    // 检查是否有图片 .如果有图片.
                    tb_tag t = tagService.fetch(Cnd.where("_name", "=", arr[i]));
                    if (t == null) {
                        continue;
                    }
                    String imgstr = "";
                    if (t != null && !Strings.isBlank(t.get_img())) {
                        imgstr = " class=\"tag-img\" style=\"background-image: url(/images/" + t.get_img() + ");\"";
                    }

                    if (i == arr.length - 1) {
                        strtag.append(" <a   " + imgstr + "  href=\"/filter/tag/" + t.get_id() + "/1.html\">" + arr[i] + "</a>");
                    } else {
                        strtag.append(" <a  " + imgstr + " href=\"/filter/tag/" + t.get_id() + "/1.html\">" + arr[i] + "</a> ");
                    }

                }
//                mod.addObject("tags", strtag);
            }
        }

        return strtag.toString();
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

    private int geststart(tb_singlepage tb, List<tb_singlepage> singlelist) {
        for (int i = 1; i <= singlelist.size(); i++) {
            if (tb.get_id() == singlelist.get(i - 1).get_id()) {
                return i;
            }
        }
        return 1;
    }

    public Map getpage(int id) {

        Map out = new NutMap();
        tb_singlepage tb = fetch(id);
        out.put("item", tb);
        //如果当前登录用户是admin，就显示出来，编辑和删除按钮。
        //因为如果是createhtml线程访问此处，找不到user，所以报错。所以没有这个变量，这样就是想要的效果
        out.put("showadm", "");
        try {
            tb_user user = CurrentUserUtils.getInstance().getUser();
            if (user != null && user.get_username().equals("admin")) {
                out.put("showadm", "true");
            }
        } catch (Exception e) {
        }
        List<String> allh2 = JsoupBiz.getList_OutHtml(tb.get_content_html(), "h2");
        String h2_nav = toHtml(allh2);
        out.put("rightnav", h2_nav);

        String strtag = tagService.getTagStr(tb);
        out.put("tags", strtag);
        out.put("username", Const.admin);
        out.put("userphoto", Const.admin_photo);

        return out;
    }


    private String toHtml(List<String> allh2) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < allh2.size(); i++) {
            String temp = allh2.get(i);
            String id = JsoupBiz.getOne_Attr(temp, "h2", "id");
            String text = JsoupBiz.getOne_Text(temp, "h2");
            stringBuffer.append("<li class=\"tocify-item\" style=\"cursor: pointer;\"><a href=\"#" + id + "\">" + text + "</a></li>");
        }
        return stringBuffer.toString();

    }

    public static List<Record> toRecs(List<tb_singlepage> ins) {
        if (Lang.isEmpty(ins)) {
            return new ArrayList<Record>();
        } else {
            List<Record> out = new ArrayList<Record>();
            for (int i = 0; i < ins.size(); i++) {
                tb_singlepage tb = ins.get(i);
                Record rec = Castors.me().castTo(tb, Record.class);
                out.add(rec);
            }
            return out;
        }
    }

}