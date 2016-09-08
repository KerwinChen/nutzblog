package net.javablog.module.adm.html;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.javablog.bean.tb_book;
import net.javablog.bean.tb_seris;
import net.javablog.bean.tb_singlepage;
import net.javablog.bean.tb_tag;
import net.javablog.init.Const;
import net.javablog.service.BlogService;
import net.javablog.service.ConfigService;
import net.javablog.service.MenuService;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.JsoupBiz;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.filter.CheckSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class CreateHtml {

    @Inject
    private BlogService blogService;

    @Inject
    private MenuService menuService;

    @Inject
    private ConfigService configService;

    @Inject
    private Configuration cf;

    @Inject
    private NutDao dao;

    private static final Logger log = LoggerFactory.getLogger("FTP");

    public void createhtml_page(int pageid, String template_name, String htmlfile) {
        createhtml(template_name, blogService.getpage(pageid), htmlfile);
    }

    public void createhtml_seriespage(int id, String templagefullname, String htmlfile) {
        createhtml(templagefullname, blogService.getserispage(id), htmlfile);
    }

    public void createhtml_menu_seris(int id, String templagefullname, String htmlfile) {
        createhtml(templagefullname, menuService.getseris(id), htmlfile);
    }

    public void createhtml_menu_note(int id, String templagefullname, String htmlfile) {
        createhtml(templagefullname, menuService.getbook(id), htmlfile);
    }

    /**
     * 生成静态文件.
     *
     * @param templateFileName 模板文件名
     * @param propMap          用于处理模板的属性Object映射
     * @param htmlFile         静态文件，本地要保存的文件名
     */


    public boolean createhtml(String templateFileName, Map propMap, String htmlFile) {
        try {

            propMap.put("admin", dao.fetch("tb_config", Cnd.where("k", "=", "admin")).getString("v"));
            propMap.put("admin_email", dao.fetch("tb_config", Cnd.where("k", "=", "admin_email")).getString("v"));
            propMap.put("admin_github", dao.fetch("tb_config", Cnd.where("k", "=", "admin_github")).getString("v"));
            propMap.put("admin_photo", dao.fetch("tb_config", Cnd.where("k", "=", "admin_photo")).getString("v"));
            propMap.put("site_name", dao.fetch("tb_config", Cnd.where("k", "=", "site_name")).getString("v"));
            propMap.put("site_logo", dao.fetch("tb_config", Cnd.where("k", "=", "site_logo")).getString("v"));
            propMap.put("site_fav", dao.fetch("tb_config", Cnd.where("k", "=", "site_fav")).getString("v"));
            propMap.put("site_createtime", dao.fetch("tb_config", Cnd.where("k", "=", "site_createtime")).getString("v"));
            propMap.put("site_aboutme", dao.fetch("tb_config", Cnd.where("k", "=", "site_aboutme")).getString("v"));
            propMap.put("site_aboutme_md", dao.fetch("tb_config", Cnd.where("k", "=", "site_aboutme_md")).getString("v"));
            propMap.put("site_tj", dao.fetch("tb_config", Cnd.where("k", "=", "site_tj")).getString("v"));
            propMap.put("site_msgboard", dao.fetch("tb_config", Cnd.where("k", "=", "site_msgboard")).getString("v"));
            propMap.put("version", R.random(10, 99));

            Template t = cf.getTemplate(templateFileName);

            File afile = new File(htmlFile);
            Files.createDirIfNoExists(afile.getParentFile());//生成目标文件的所在文件夹

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile)));
            NutMap datas = NutMap.NEW();
            datas.put("obj", propMap);
            t.process(datas, out);
            log.info("Freemarker生成文件：" + afile.getCanonicalPath());
            out.flush();
            out.close();

            afile.setWritable(true);
            afile.setReadable(true);
            afile.setExecutable(true);

        } catch (TemplateException e) {
            log.error("Error while processing FreeMarker template " + templateFileName, e);
            return false;
        } catch (IOException e) {
            log.error("Error while generate Static Html File " + htmlFile, e);
            return false;
        }
        return true;
    }


    /**
     * 获取所有标签对应的分页  页数之和
     *
     * @return
     */
    public int getCount_5() {

        int out = 0;
        List<tb_tag> tags = dao.query(tb_tag.class, null);
        if (Lang.isEmpty(tags)) {
            return 0;
        }

        for (int i = 0; i < tags.size(); i++) {

            String typevalue = tags.get(i).get_name();
            Criteria criteria = Cnd.cri();
            SqlExpressionGroup g = Cnd.exps("_tags", "like", "%," + typevalue + "").or("_tags", "like", "%," + typevalue + ",%").or("_tags", "like", "" + typevalue + ",%").or("_tags", "=", typevalue);
            criteria.where().and(g);

            int count_intag = dao.count(tb_singlepage.class, criteria);

            int page = 0;
            if (count_intag == 0) {
                page = 1;
            } else if (count_intag % Const.PAGE_SIZE == 0) {
                page = count_intag / Const.PAGE_SIZE;
            } else {
                page = count_intag / Const.PAGE_SIZE + 1;
            }
            out = out + page;
        }
        return out;
    }

    /**
     * 每个标签对应的分页数量
     *
     * @return
     */
    public Map<String, Integer> getCount_5_() {

        Map<String, Integer> out_map = new HashMap<String, Integer>();

        int out = 0;
        List<tb_tag> tags = dao.query(tb_tag.class, null);
        if (Lang.isEmpty(tags)) {
            return out_map;
        }

        for (int i = 0; i < tags.size(); i++) {

            String key = String.valueOf(tags.get(i).get_id());
            String typevalue = tags.get(i).get_name();
            Criteria criteria = Cnd.cri();
            SqlExpressionGroup g = Cnd.exps("_tags", "like", "%," + typevalue + "").or("_tags", "like", "%," + typevalue + ",%").or("_tags", "like", "" + typevalue + ",%").or("_tags", "=", typevalue);
            criteria.where().and(g);

            int count_intag = dao.count(tb_singlepage.class, criteria);

            int page = 0;
            if (count_intag == 0) {
                page = 1;
            } else if (count_intag % Const.PAGE_SIZE == 0) {
                page = count_intag / Const.PAGE_SIZE;
            } else {
                page = count_intag / Const.PAGE_SIZE + 1;
            }
            out_map.put(key, page);
        }
        return out_map;

    }


    //返回  每个月份对应的分页数量之和
    public int getCount_4() {

        int out = 0;

        Sql sql = Sqls.create("SELECT  YEAR(ut) as y, MONTH(ut) as m ,count(*) as c from tb_singlepage  GROUP BY y,m ORDER BY y asc   ");
        sql.setCallback(Sqls.callback.records());
        dao.execute(sql);
        List<Record> records = sql.getList(Record.class);

//        y       m  c
//        2015	6	1
//        2015	7	2
//        2016	2	4


        for (int i = 0; i < records.size(); i++) {
            String y = records.get(i).getString("y");
            int m = records.get(i).getInt("m");
            int c = records.get(i).getInt("c");
//            Integer integer = Integer.valueOf(y + "" + String.format("%02d", m));
//            list.add(integer);

            if (c % Const.PAGE_SIZE == 0) {
                c = c / Const.PAGE_SIZE;
            } else {
                c = c / Const.PAGE_SIZE + 1;
            }
            out = out + c; //每一个月份里的分页数量
        }
        return out;

    }

    /**
     * 获取当前月份的分页数量
     *
     * @return
     */
    public int getCount_4_byMonth() {

        String month = Times.format("yyyyMM", new Date());
        String y = month.substring(0, 4);
        String m = month.substring(4, 6);
        if (m.startsWith("0")) {
            m = month.substring(4, 5);
        }
        String sql = "SELECT   count(*) as c    from tb_singlepage  \n" +
                "where  YEAR(ut)=" + y + " and  MONTH(ut)=" + m + "  and _isdraft=0";

        Sql s = Sqls.create(sql).setCallback(Sqls.callback.integer());
        dao.execute(s);
        int allcount = s.getInt();

        if (allcount == 0) {
            return 1;
        }
        if (allcount % Const.PAGE_SIZE == 0) {
            return allcount / Const.PAGE_SIZE;
        } else {
            return allcount / Const.PAGE_SIZE + 1;
        }


    }


    /**
     * 每个月份里面的分页数量
     *
     * @return
     */
    public Map<Integer, Integer> getCount_4_() {

        Map<Integer, Integer> outlist = new HashMap<Integer, Integer>();
        int out = 0;

        Sql sql = Sqls.create("SELECT  YEAR(ut) as y, MONTH(ut) as m ,count(*) as c from tb_singlepage  GROUP BY y,m ORDER BY y asc   ");
        sql.setCallback(Sqls.callback.records());
        dao.execute(sql);
        List<Record> records = sql.getList(Record.class);

//        y       m  c
//        2015	6	1
//        2015	7	2
//        2016	2	4


        for (int i = 0; i < records.size(); i++) {
            String y = records.get(i).getString("y");
            int m = records.get(i).getInt("m");
            int c = records.get(i).getInt("c");
            Integer key = Integer.valueOf(y + "" + String.format("%02d", m));
//            list.add(integer);

            if (c % Const.PAGE_SIZE == 0) {
                c = c / Const.PAGE_SIZE;
            } else {
                c = c / Const.PAGE_SIZE + 1;
            }
//            out = out + c; //每一个月份里的分页数量
            outlist.put(key, c);
        }
        return outlist;

    }


    /**
     * 按照经验分页，多少页？
     * /filter/page/single/1.html
     *
     * @return
     */
    public int getCount_1() {

        int allcount1 = dao.count(tb_singlepage.class, Cnd.where("_serisid", "=", 0).and("_isdraft", "=", 0));
        if (allcount1 == 0) {
            return 1;
        }
        if (allcount1 % Const.PAGE_SIZE == 0) {
            return allcount1 / Const.PAGE_SIZE;
        } else {
            return allcount1 / Const.PAGE_SIZE + 1;
        }

    }

    /**
     * 按照教程分页，多少页？
     * /filter/page/seris/1.html
     *
     * @return
     */
    public int getCount_2() {

        int allcount1 = dao.count(tb_seris.class, Cnd.where("_bookid", "=", 0).and("_isdraft", "=", 0));
        if (allcount1 == 0) {
            return 1;
        }
        if (allcount1 % Const.PAGE_SIZE == 0) {
            return allcount1 / Const.PAGE_SIZE;
        } else {
            return allcount1 / Const.PAGE_SIZE + 1;
        }

    }


    /**
     * 按照图书分页，多少页？
     * /filter/page/book/1.html
     *
     * @return
     */
    public int getCount_3() {

        int allcount1 = dao.count(tb_book.class);
        if (allcount1 == 0) {
            return 1;
        }
        if (allcount1 % Const.PAGE_SIZE == 0) {
            return allcount1 / Const.PAGE_SIZE;
        } else {
            return allcount1 / Const.PAGE_SIZE + 1;
        }

    }


    /**
     * @param list
     * @return
     */
    public int getCount_5_byPages(List<tb_singlepage> list) {
        if (Lang.isEmpty(list)) {
            return 0;
        }

        Set<String> tags = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            tb_singlepage page = list.get(i);
            String ts = page.get_tags();
            if (Strings.isBlank(ts)) {
                continue;
            }
            String[] tag_arr = ts.split(",", -1);
            tags.addAll(Arrays.asList(tag_arr));
        }

        int out = 0;
        //每个tag 对应的分页的和
        for (String t : tags) {
            int c = getCount_5_byTagName(t);
            out = out + c;
        }

        return out;

    }


    /**
     * 根据指定文章， 获取所涉及到的tag，每个tag对应的分页数
     *
     * @param list
     * @return
     */
    public Map<String, Integer> getCount_5_byPages_(List<tb_singlepage> list) {
        if (Lang.isEmpty(list)) {
            return new HashMap<>();
        }

        Map out = new HashMap<>();
        Set<String> tags = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            tb_singlepage page = list.get(i);
            String ts = page.get_tags();
            if (Strings.isBlank(ts)) {
                continue;
            }
            String[] tag_arr = ts.split(",", -1);
            tags.addAll(Arrays.asList(tag_arr));
        }

        //每个tag 对应的分页的和
        for (String t : tags) {

            tb_tag tag = dao.fetch(tb_tag.class, Cnd.where("_name", "=", t));
            if (tag != null) {
                String k = String.valueOf(tag.get_id());
                int c = getCount_5_byTagName(t);
                out.put(k, c);
            }
        }

        return out;

    }

    private int getCount_5_byTagName(String t) {

        SqlExpressionGroup g = Cnd.exps("_tags", "like", "%," + t + "").or("_tags", "like", "%," + t + ",%").or("_tags", "like", "" + t + ",%").or("_tags", "=", t);
        int allcount = dao.count(tb_singlepage.class, Cnd.NEW().and(g));
        if (allcount == 0) {
            return 1;
        }
        if (allcount % Const.PAGE_SIZE == 0) {
            return allcount / Const.PAGE_SIZE;
        } else {
            return allcount / Const.PAGE_SIZE + 1;
        }

    }

    /**
     * 获取所有文章所关联的所有图片id
     * config表，tag表，single表 _toppic  _content_html
     *
     * @param list
     * @return
     */
    public List<String> getCount_6_allcount(List<tb_singlepage> list) {

        List<String> out = new ArrayList<>();
        out.add(configService.get_byName("admin_photo").toString());
        out.add(configService.get_byName("site_logo").toString());
        out.add(configService.get_byName("site_fav").toString());

        String html_me = configService.get_byName("site_aboutme").toString();
        findImgByHtml(out, html_me);

        List<tb_tag> tags = dao.query(tb_tag.class, Cnd.where("_img", "!=", ""));
        if (!Lang.isEmpty(tags)) {
            for (int i = 0; i < tags.size(); i++) {
                out.add(tags.get(i).get_img());
            }
        }

        if (!Lang.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                out.add(list.get(i).get_toppic());
                findImgByHtml(out, list.get(i).get_content_html());
            }
        }
        return out;
    }

    private void findImgByHtml(List<String> out, String html_me) {
        List<String> imgs = JsoupBiz.getList_Attr(html_me, "img", "src");
        if (!Lang.isEmpty(imgs)) {
            for (int i = 0; i < imgs.size(); i++) {
                String img = imgs.get(i);

                if (img.length() > 6) {
                    img = img.substring(8);//   /images/
                    if (img.length() == 32 && !img.startsWith("http") && !img.contains(".")) {
                        out.add(img);
                    }
                }
            }
        }
    }
}
