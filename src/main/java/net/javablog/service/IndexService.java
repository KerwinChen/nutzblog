package net.javablog.service;

import net.javablog.bean.tb_book;
import net.javablog.bean.tb_seris;
import net.javablog.bean.tb_singlepage;
import net.javablog.init.Const;
import net.javablog.util.FrontPagerUT;
import org.nutz.castor.Castors;
import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Times;

import java.util.*;


@IocBean
public class IndexService {

    private int pagesize = Const.PAGE_SIZE;

    @Inject
    private NutDao dao;

    @Inject
    private TagService tagService;

    @Inject
    private UserService userService;

    /**
     * 获取首页中的页码数量，不足一页算一页
     * 第一页创建的位置比较特殊，需要创建两个文件   index.html ,index.htm
     *
     * @return
     */
    public int getIndxePageCount() {
        int allcount = dao.count(tb_singlepage.class, Cnd.where("_isdraft", "=", 0));

        if (allcount == 0) {
            return 1;
        }

        if (allcount % pagesize == 0) {
            return allcount / pagesize;
        } else {
            return allcount / pagesize + 1;
        }
    }

    public Map getIndexMapdata(int pageno) {

        Map out = new HashMap();
        Pager pager = new Pager();
        if (pageno <= 0) {
            pageno = 1;
        }

        pager.setPageNumber(pageno);
        pager.setPageSize(pagesize);


        Cnd cnd = Cnd.where("_isdraft", "=", 0);
        List<tb_singlepage> tbs = dao.query(tb_singlepage.class, cnd.orderBy("_id", "desc"), pager);
        int allcount = dao.count(tb_singlepage.class, cnd);

        out.put("pagescount", allcount);

        out.put("pages", FrontPagerUT.pages(pager.getPageSize(), allcount, pageno, "/index/%s.html", 3));
        out.put("pages_min", FrontPagerUT.pages(pager.getPageSize(), allcount, pageno, "/index/%s.html", 0));

        List<Record> records = new ArrayList<Record>();
        for (int i = 0; i < tbs.size(); i++) {

            String tagstr = tagService.getTagStr(tbs.get(i));
            Record rec = Castors.me().castTo(tbs.get(i), Record.class);
            rec.put("tagstr", tagstr);
            rec.put("username", Const.admin);
            rec.put("photo", Const.admin_photo);
            //=0 就是单文章
            if (rec.getInt("_serisid") <= 0) {
                rec.put("_seris_show", "display:none;");
                rec.put("_seris_tag", "");
                rec.put("_serieshref", "/page/" + rec.get("_id") + "/" + rec.get("_titleen") + ".html");
            } else {
                rec.put("_seris_show", "");
                rec.put("_seris_tag", "Series " + getSerisIndex(tbs.get(i)) + "");
                rec.put("_serieshref", "/pages/" + rec.get("_id") + "/" + rec.get("_titleen") + ".html");
            }
            records.add(rec);

        }
        out.put("datas", records);
        return out;
    }

    /**
     * /filter/page/{single,seris,book}/1.html
     * /filter/tag/1/1.html   url中用tagid
     * /filter/month/201501/1.html
     *
     * @param type
     * @param typevalue
     * @param pageno
     * @return
     */
    public Map getIndexMapdata_filter(String type, String typevalue, int pageno) {

        Map out = new HashMap();
        Pager pager = new Pager();
        if (pageno <= 0) {
            pageno = 1;
        }

        pager.setPageNumber(pageno);
        pager.setPageSize(pagesize);
        int allcount = 0;
        Criteria criteria = Cnd.cri();
        criteria.where().and("_isdraft", "=", 0);
        if (type.equals("page")) {
            if (typevalue.equals("single")) {
                criteria.where().and("_serisid", "=", 0);
                out.put("typevalue", "经验");
            }
            if (typevalue.equals("seris")) {
                criteria.where().and("_serisid", ">", 0).and("_bookid", "=", 0);
                out.put("typevalue", "教程");
            }

            if (typevalue.equals("book")) {
                criteria.where().and("_serisid", ">", 0).and("_bookid", ">", 0);
                out.put("typevalue", "图书");
            }


            int allcount1 = dao.count(tb_singlepage.class, Cnd.where("_serisid", "=", 0).and("_isdraft", "=", 0));
            out.put("pagescount1", allcount1);
            int allcount2 = dao.count(tb_singlepage.class, Cnd.where("_serisid", ">", 0).and("_bookid", "=", 0).and("_isdraft", "=", 0));
            out.put("pagescount2", allcount2);
            int allcount3 = dao.count(tb_singlepage.class, Cnd.where("_serisid", ">", 0).and("_bookid", ">", 0).and("_isdraft", "=", 0));
            out.put("pagescount3", allcount3);

            allcount = dao.count(tb_singlepage.class, criteria);
            out.put("pagescount", allcount);
            out.put("type", "文章");


        } else if (type.equals("tag")) {
            typevalue = tagService.getTagNameById(Integer.valueOf(typevalue));
            SqlExpressionGroup g = Cnd.exps("_tags", "like", "%," + typevalue + "").or("_tags", "like", "%," + typevalue + ",%").or("_tags", "like", "" + typevalue + ",%").or("_tags", "=", typevalue);
            criteria.where().and(g);

            out.put("type", "标签");
            out.put("typevalue", typevalue);

            allcount = dao.count(tb_singlepage.class, criteria);
            out.put("pagescount", allcount);

        } else if (type.equals("month")) {
            Date[] d = getBetw(typevalue);
            criteria.where().and("ut", ">=", d[0]).and("ut", "<=", d[1]);
            out.put("type", "归档");
            out.put("typevalue", Times.format("yyyy年MM月", d[0]));

            allcount = dao.count(tb_singlepage.class, criteria);
            out.put("pagescount", allcount);
        }


        criteria.getOrderBy().desc("_id");
        List<tb_singlepage> tbs = dao.query(tb_singlepage.class, criteria, pager);


        out.put("pages", FrontPagerUT.pages(pager.getPageSize(), allcount, pageno, "/filter/" + type + "/" + typevalue + "/%s.html", 3));
        out.put("pages_min", FrontPagerUT.pages(pager.getPageSize(), allcount, pageno, "/filter/" + type + "/" + typevalue + "/%s.html", 0));

        List<Record> records = new ArrayList<Record>();
        for (int i = 0; i < tbs.size(); i++) {

            String tagstr = tagService.getTagStr(tbs.get(i));
            Record rec = Castors.me().castTo(tbs.get(i), Record.class);
            rec.put("tagstr", tagstr);
            rec.put("username", Const.admin);
            rec.put("photo", Const.admin_photo);

            //=0 就是单文章
            if (rec.getInt("_serisid") <= 0) {
                rec.put("_seris_show", "display:none;");
                rec.put("_seris_tag", "");
                rec.put("_serieshref", "/page/" + rec.get("_id") + "/" + rec.get("_titleen") + ".html");
            } else {
                rec.put("_seris_show", "");
                rec.put("_seris_tag", "Series " + getSerisIndex(tbs.get(i)) + "");
                rec.put("_serieshref", "/pages/" + rec.get("_id") + "/" + rec.get("_titleen") + ".html");
            }
            records.add(rec);

        }
        out.put("datas", records);
        return out;
    }


    /**
     * /filter/page/{single,seris,book}/1.html
     *
     * @param typevalue
     * @param pageno
     * @return
     */
    public Map getIndexMapdata_filter_single_seris_book(String typevalue, int pageno) {

        Map out = new HashMap();
        Pager pager = new Pager();
        if (pageno <= 0) {
            pageno = 1;
        }

        pager.setPageNumber(pageno);
        pager.setPageSize(pagesize);
        int allcount = 0;
        Criteria criteria1 = Cnd.cri();
        criteria1.where().and("_isdraft", "=", 0);
        criteria1.where().and("_serisid", "=", 0);
        criteria1.getOrderBy().desc("_id");

        Criteria criteria2 = Cnd.cri();
        criteria2.where().and("_isdraft", "=", 0);
        criteria2.where().and("_bookid", "=", 0);
        criteria2.getOrderBy().desc("_id");

        Criteria criteria3 = Cnd.cri();
        criteria3.where().and("_isdraft", "=", 0);
        criteria3.getOrderBy().desc("_id");

        //分别获取数据
        if (typevalue.equals("single")) {
            out.put("typevalue", "经验");

            List<tb_singlepage> tbs = dao.query(tb_singlepage.class, criteria1, pager);
            List<Record> records = new ArrayList<Record>();
            for (int i = 0; i < tbs.size(); i++) {
                String tagstr = tagService.getTagStr(tbs.get(i));
                Record rec = Castors.me().castTo(tbs.get(i), Record.class);
                rec.put("tagstr", tagstr);
                rec.put("username", Const.admin);
                rec.put("photo", Const.admin_photo);

                //=0 就是单文章
                rec.put("_seris_show", "display:none;");
                rec.put("_seris_tag", "");
                rec.put("_serieshref", "/page/" + rec.get("_id") + "/" + rec.get("_titleen") + ".html");
                records.add(rec);
            }
            out.put("datas", records);
        }


        if (typevalue.equals("seris")) {
            out.put("typevalue", "教程");
            List<tb_seris> tbs = dao.query(tb_seris.class, criteria2, pager);
            out.put("datas", tbs);
        }

        if (typevalue.equals("book")) {
            out.put("typevalue", "图书");
            List<tb_book> tbs = dao.query(tb_book.class, criteria3, pager);
            out.put("datas", tbs);
        }


        //共有的3个数量
        int allcount1 = dao.count(tb_singlepage.class, criteria1);
        out.put("pagescount1", allcount1);
        int allcount2 = dao.count(tb_seris.class, criteria2);
        out.put("pagescount2", allcount2);
        int allcount3 = dao.count(tb_book.class, criteria3);
        out.put("pagescount3", allcount3);


        out.put("pages", FrontPagerUT.pages(pager.getPageSize(), allcount, pageno, "/filter/page/" + typevalue + "/%s.html", 3));
        out.put("pages_min", FrontPagerUT.pages(pager.getPageSize(), allcount, pageno, "/filter/page/" + typevalue + "/%s.html", 0));


        return out;
    }

    /**
     * 查询某月的范围
     *
     * @param typevalue 格式  yyyyMM
     * @return
     */
    private Date[] getBetw(String typevalue) {

        final int[] _MDs = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        Date d1 = Times.parseq("yyyyMM", typevalue);

        int month = d1.getMonth();
        int days = _MDs[month];
        String days_ = String.format("%02d", days);

        Date d2 = Times.parseq("yyyyMMdd", typevalue + days_);
        d2 = new Date(d2.getTime() + Times.T_1D - 1);

        Date[] ds = new Date[2];
        ds[0] = d1;
        ds[1] = d2;
        return ds;
    }


    private String getSerisIndex(tb_singlepage singlepage) {

        List<tb_singlepage> ps = dao.query(tb_singlepage.class, Cnd.where("_serisid", "=", singlepage.get_serisid()).and("_isdraft", "=", 0));
        int count = ps.size();

        int pindex = 1;
        for (int i = 0; i < ps.size(); i++) {
            if (singlepage.get_id() == ps.get(i).get_id()) {
                pindex = i + 1;
                break;
            }
        }
        return pindex + "/" + count;
    }

}
