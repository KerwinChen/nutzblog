package net.javablog.module.adm.html;


import net.javablog.bean.tb_book;
import net.javablog.bean.tb_seris;
import net.javablog.bean.tb_singlepage;
import net.javablog.init.Const;
import net.javablog.service.*;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.FTPUtil;
import net.javablog.util.Threads;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class HtmlModule {

    private static final Logger log = LoggerFactory.getLogger("FTP");

    @Inject
    private NutDao dao;

    @Inject
    private CreateHtml createHtml;

    @Inject
    private ConfigService configService;

    @Inject
    private LogService logService;

    @Inject
    private MenuService menuService;

    @Inject
    private IndexService indexService;

    @Inject
    private TagService tagService;

    @Inject
    private ArchiveService archiveService;

    private String ip;
    private String user;
    private String pwd;

    /**
     * 生成html
     * <p/>
     * ？1     single,seris,book
     * ?  2    _id
     *
     * @param type
     * @param id
     * @return
     */
    @At("/html/?/?")
    @Ok("redirect:/log")
    public void html(String type, int id) {

        ip = configService.getIP();
        user = configService.getUser();
        pwd = configService.getPwd();

        html_single(type, id, ip, user, pwd);

        html_seris(type, id, ip, user, pwd);

        html_book(type, id, ip, user, pwd);


    }

    private void html_book(String type, final int id, final String ip, final String user, final String pwd) {
        if (type.equals("note")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<tb_seris> serises = dao.query(tb_seris.class, Cnd.where("_bookid", "=", id));
                    if (!Lang.isEmpty(serises)) {
                        for (int i = 0; i < serises.size(); i++) {
                            html_seris("seris", serises.get(i).get_id(), ip, user, pwd);
                        }
                    }
                    tb_book tbBook = dao.fetch(tb_book.class, id);
                    String tofile = "/book/" + tbBook.get_id() + "/" + tbBook.get_booktitleen() + ".html";
                    String fromfile = Const.HTML_SAVEPATH + tofile;
                    createHtml.createhtml_menu_note(tbBook.get_id(), "menu_note.ftl", fromfile);
                    FTPUtil.uploadSingleFile(ip, user, pwd, fromfile, tofile);
                    upload_index_first();
                }
            }).start();
        }
    }

    private void html_single(String type, int id, final String ip, final String user, final String pwd) {
        if (type.equals("single")) {
            final tb_singlepage tbin = dao.fetch(tb_singlepage.class, id);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    String tofile = "/page/" + tbin.get_id() + "/" + tbin.get_titleen() + ".html";
                    String fromfile = Const.HTML_SAVEPATH + tofile;

                    createHtml.createhtml_page(tbin.get_id(), "page.ftl", fromfile);
                    FTPUtil.uploadSingleFile(ip, user, pwd, fromfile, tofile);
                    upload_images_bySinglePages(new int[]{tbin.get_id()});

                    upload_index_first();
                }
            }).start();
        }
    }


    public void upload_images_bySinglePages(int[] pageids) {
        if (Lang.isEmpty(pageids)) {
            return;
        }
        List<tb_singlepage> listpages = dao.query(tb_singlepage.class, Cnd.where("_id", "in", pageids));
        if (Lang.isEmpty(listpages)) {
            return;
        }
        List<String> images = new ArrayList<>();

        for (int i = 0; i < listpages.size(); i++) {
            String html = listpages.get(i).get_content_html();
            createHtml.findImgByHtml(images, html);
        }

        for (int i = 0; i < images.size(); i++) {
            String img = Const.HTML_SAVEPATH + "/images/" + images.get(i);
            FTPUtil.uploadSingleFile(ip, user, pwd, img, "/images/" + images.get(i));
        }
    }

    private void upload_index_first() {
        createHtml.createhtml("index.ftl", indexService.getIndexMapdata(1), Const.HTML_SAVEPATH + "index.html");
        try {
            Files.copyFile(new File(Const.HTML_SAVEPATH + "index.html"), new File(Const.HTML_SAVEPATH + "index/" + "1.html"));
            Files.copyFile(new File(Const.HTML_SAVEPATH + "index.html"), new File(Const.HTML_SAVEPATH + "index.htm"));
        } catch (IOException e) {
        }
        FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "index.html", "/index.html");
        FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "index.html", "/index.htm");
        FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "index.html", "/index/1.html");
    }

    private void html_seris(String type, final int id, final String ip, final String user, final String pwd) {
        if (type.equals("seris")) {
            final tb_singlepage tbin = dao.fetch(tb_singlepage.class, id);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (id > 0) {
                        List<tb_singlepage> pages = dao.query(tb_singlepage.class, Cnd.where("_serisid", "=", id));

                        log.info("html_sris  pages {}", pages.size());
                        if (!Lang.isEmpty(pages)) {
                            for (int i = 0; i < pages.size(); i++) {
                                final tb_singlepage tbin = pages.get(i);
                                //生成html页面
                                String tofile = "/pages/" + tbin.get_id() + "/" + tbin.get_titleen() + ".html";
                                String fromfile = Const.HTML_SAVEPATH + tofile;
                                createHtml.createhtml_seriespage(tbin.get_id(), "pages.ftl", fromfile);
                                //上传文件  /tutorial/page/1.html
                                FTPUtil.uploadSingleFile(ip, user, pwd, fromfile, tofile);
                                upload_images_bySinglePages(new int[]{tbin.get_id()});
                            }
                        }

                        //如果是从book过来的，就不用再生成serisid
                        tb_seris tbSeris = dao.fetch(tb_seris.class, id);
                        if (tbSeris.get_bookid() == 0) {

                            String tofile = "/seris/" + tbSeris.get_id() + "/" + tbSeris.get_seristitleen() + ".html";
                            String fromfile = Const.HTML_SAVEPATH + tofile;
                            createHtml.createhtml_menu_seris(tbSeris.get_id(), "menu_seris.ftl", fromfile);
                            FTPUtil.uploadSingleFile(ip, user, pwd, fromfile, tofile);
                        }

                        upload_index_first();

                    }
                }
            }).start();
        }
    }


    private static boolean runing = false;

    /**
     * 整站生成html
     *
     * @return
     */
    @At("/createhtml_site")
    @Ok("redirect:/log")
    public void createhtml() {
        //拷贝static文件夹
        try {
            File target_static = new File(Const.HTML_SAVEPATH + "/static");
            Files.deleteDir(target_static);
            Files.copyDir(new File(Mvcs.getActionContext().getServletContext().getRealPath("/") + "/static"), target_static);

            if (runing) {

            } else {

                Threads.run(new Runnable() {
                    @Override
                    public void run() {
                        cleanHtml();
                        runing = true;
                        logService.setProcess0();

                        //首页上的总页数
                        final int count_index_pages = indexService.getIndxePageCount();

                        //按照经验分页，多少页？
                        int count_filter_pagesingle = createHtml.getCount_1();

                        //按照教程分页，多少页？
                        int count_filter_pageseris = createHtml.getCount_2();

                        //按照图书分页，多少页？
                        int count_filter_pagebook = createHtml.getCount_3();

                        //归档页面  /archive.html
                        int count_archive = 1;
                        //归档页面  所有的月份*各自拥有的分页   /filter/month/201507/1.html
                        int count_archive_pages = createHtml.getCount_4();


                        //标签页  /tags.html
                        int count_tag = 1;
                        //每个标签对应的分页的数量之和
                        int count_tag_pages = createHtml.getCount_5();

                        //关于我
                        int count_me = 1;
                        //项目列表总共多少页
//                    int count_proj_pages = createHtml.getCount_6();

                        //404页面
                        int count_404 = 1;

                        //生成每个单页 [所有的文章页，还有所有的项目页]
                        final List<tb_singlepage> listpages = dao.query(tb_singlepage.class, Cnd.where("_isdraft", "=", 0));
                        int listpages_ = listpages == null ? 0 : listpages.size();

                        //多少个系列
                        List<tb_seris> seris_ = dao.query(tb_seris.class, Cnd.where("_isdraft", "=", 0).and("_bookid", "=", 0));
                        int seris_count = seris_ == null ? 0 : seris_.size();
                        //多少本书
                        List<tb_book> books_ = dao.query(tb_book.class, Cnd.where("_isdraft", "=", 0));
                        int books_count = books_ == null ? 0 : books_.size();

                        final int all = count_index_pages + count_filter_pagesingle + count_filter_pageseris + count_filter_pagebook + count_archive +
                                count_archive_pages + count_tag + count_tag_pages + count_me + listpages_ + seris_count + books_count + count_404;


                        log.debug("all:" + all);

                        //首页及其分页链接
                        for (int pageno = 1; pageno <= count_index_pages; pageno++) {
                            logService.setProcess(all);
                            Files.createDirIfNoExists(Const.HTML_SAVEPATH + "index/");
                            if (pageno == 1) {
                                createHtml.createhtml("index.ftl", indexService.getIndexMapdata(pageno), Const.HTML_SAVEPATH + "index.html");
                                try {
                                    Files.copyFile(new File(Const.HTML_SAVEPATH + "index.html"), new File(Const.HTML_SAVEPATH + "index/" + "1.html"));
                                    Files.copyFile(new File(Const.HTML_SAVEPATH + "index.html"), new File(Const.HTML_SAVEPATH + "index.htm"));
                                } catch (IOException e) {
                                }
                            } else {
                                createHtml.createhtml("index.ftl", indexService.getIndexMapdata(pageno), Const.HTML_SAVEPATH + "/index/" + pageno + ".html");
                            }
                            log.info("正在生成首页: " + pageno + "/" + count_index_pages);
                        }
                        log.info("生成首页over");

                        //按照经验分页
                        for (int i = 1; i <= count_filter_pagesingle; i++) {
                            logService.setProcess(all);
                            createHtml.createhtml("filter_page_single.ftl", indexService.getIndexMapdata_filter_single_seris_book("single", i), Const.HTML_SAVEPATH + "filter/page/single/" + i + ".html");
                        }
                        //按照教程分页
                        for (int i = 1; i <= count_filter_pageseris; i++) {
                            logService.setProcess(all);
                            createHtml.createhtml("filter_page_seris.ftl", indexService.getIndexMapdata_filter_single_seris_book("seris", i), Const.HTML_SAVEPATH + "filter/page/seris/" + i + ".html");
                        }
                        //每个教程的menu
                        for (int i = 0; i < seris_.size(); i++) {
                            logService.setProcess(all);
                            tb_seris tb = seris_.get(i);
                            createHtml.createhtml("menu_seris.ftl", menuService.getseris(tb.get_id()), Const.HTML_SAVEPATH + "seris/" + tb.get_id() + "/" + tb.get_seristitleen() + ".html");
                        }

                        //按照图书分页
                        for (int i = 1; i <= count_filter_pagebook; i++) {
                            logService.setProcess(all);
                            createHtml.createhtml("filter_page_book.ftl", indexService.getIndexMapdata_filter_single_seris_book("book", i), Const.HTML_SAVEPATH + "filter/page/book/" + i + ".html");
                        }
                        //每本书的menu
                        for (int i = 0; i < books_.size(); i++) {
                            logService.setProcess(all);
                            tb_book tb = books_.get(i);
                            createHtml.createhtml("menu_note.ftl", menuService.getbook(tb.get_id()), Const.HTML_SAVEPATH + "book/" + tb.get_id() + "/" + tb.get_booktitleen() + ".html");
                        }

                        //归档页面
                        logService.setProcess(all);
                        Map data = archiveService.data();
                        createHtml.createhtml("archive.ftl", data, Const.HTML_SAVEPATH + "archive.html");

                        //归档页面中的所有分页    count_archive_pages
                        Files.createDirIfNoExists(Const.HTML_SAVEPATH + "filter/month/");
                        Map<Integer, Integer> archives = createHtml.getCount_4_();
                        Iterator it = archives.keySet().iterator();
                        while (it.hasNext()) {
                            int month = (Integer) it.next();
                            int pagecount = archives.get(month);
                            for (int i = 1; i <= pagecount; i++) {
                                logService.setProcess(all);
                                Files.createDirIfNoExists(Const.HTML_SAVEPATH + "filter/month/" + String.valueOf(month) + "/");
                                createHtml.createhtml("filter_month.ftl", indexService.getIndexMapdata_filter("month", String.valueOf(month), i), Const.HTML_SAVEPATH + "filter/month/" + String.valueOf(month) + "/" + i + ".html");
                            }
                        }


                        //标签页面
                        logService.setProcess(all);
                        Map data_tag = tagService.getdata();
                        createHtml.createhtml("tags.ftl", data_tag, Const.HTML_SAVEPATH + "tags.html");

                        //每个标签对应的分页
                        if (count_tag_pages > 0) {
                            Map<String, Integer> tags = createHtml.getCount_5_();
                            Iterator it2 = tags.keySet().iterator();
                            while (it2.hasNext()) {
                                String tag_id = (String) it2.next();
                                int pagecount = tags.get(tag_id);
                                for (int i = 1; i <= pagecount; i++) {
                                    logService.setProcess(all);
                                    Files.createDirIfNoExists(Const.HTML_SAVEPATH + "filter/tag/" + tag_id + "/");
                                    createHtml.createhtml("filter_tag.ftl", indexService.getIndexMapdata_filter("tag", tag_id, i), Const.HTML_SAVEPATH + "filter/tag/" + tag_id + "/" + i + ".html");
                                }
                            }
                        }


                        //我
                        logService.setProcess(all);
                        createHtml.createhtml("me.ftl", NutMap.NEW().setv("rs", configService.get_site_aboutme()), Const.HTML_SAVEPATH + "me.html");

                        //404
                        logService.setProcess(all);
                        createHtml.createhtml("404.ftl", new HashMap(), Const.HTML_SAVEPATH + "404.html");


                        //每一篇详情页 [page  serispage]
                        if (listpages_ > 0) {
                            for (int i = 0; i < listpages.size(); i++) {

                                logService.setProcess(all);

                                log.info((i + 1) + "/" + listpages.size());
                                final tb_singlepage tbin = listpages.get(i);
                                if (tbin.get_serisid() == 0) {
                                    createHtml.createhtml_page(tbin.get_id(), "page.ftl", Const.HTML_SAVEPATH + "/page/" + tbin.get_id() + "/" + tbin.get_titleen() + ".html");
                                } else {
                                    createHtml.createhtml_seriespage(tbin.get_id(), "pages.ftl", Const.HTML_SAVEPATH + "/pages/" + tbin.get_id() + "/" + tbin.get_titleen() + ".html");
                                }
                            }

                            log.info("生成详情页over,数量" + listpages.size());
                            log.info("生成全部页面 over,数量" + all);

                            log.info("一键生成静态网站 over (保存位置 " + Const.HTML_SAVEPATH + ")");
                            runing = false;
                        }
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            runing = false;
        }
    }

    private void cleanHtml() {

        Files.deleteDir(new File(Const.HTML_SAVEPATH + "book"));
        Files.deleteDir(new File(Const.HTML_SAVEPATH + "page"));
        Files.deleteDir(new File(Const.HTML_SAVEPATH + "pages"));
        Files.deleteDir(new File(Const.HTML_SAVEPATH + "seris"));

    }


}
