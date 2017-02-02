package net.javablog.module.adm.html;


import net.javablog.bean.tb_book;
import net.javablog.bean.tb_seris;
import net.javablog.bean.tb_singlepage;
import net.javablog.init.Const;
import net.javablog.service.*;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.FTPUtil;
import net.javablog.util.Threads;
import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CheckSession;

import java.io.File;
import java.io.IOException;
import java.util.*;

@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class FtpModule {

    private static Logger log = Logger.getLogger("FTP");


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

    /**
     * 上传一次就可以了
     */
    @At("/ftp_upload_static")
    @Ok("redirect:/log")
    public void upload_static() {
        String ip = configService.getIP();
        String user = configService.getUser();
        String pwd = configService.getPwd();
        Threads.run(new Runnable() {
            @Override
            public void run() {
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/static", "/static");

                log.info("上传static文件夹 over");
            }
        });
    }

    /**
     * 基本上传一次就可以，后续的图片会跟随html的上传一并上传的。
     */
    @At("/ftp_upload_images_all")
    @Ok("redirect:/log")
    public void upload_images_all() {
        String ip = configService.getIP();
        String user = configService.getUser();
        String pwd = configService.getPwd();
        Threads.run(new Runnable() {
            @Override
            public void run() {
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/images", "/images");
                log.info("上传images文件夹 over");
            }
        });
    }

    @At("/ftp_upload_html_all")
    @Ok("redirect:/log")
    public void ftp_upload_html_all() {
        String ip = configService.getIP();
        String user = configService.getUser();
        String pwd = configService.getPwd();
        Threads.run(new Runnable() {
            @Override
            public void run() {

                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/index.html", "/index.html");
                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/index.htm", "/index.htm");
                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/archive.html", "/archive.html");
                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/tags.html", "/tags.html");
                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/me.html", "/me.html");
                FTPUtil.uploadSingleFile(ip, user, pwd, Const.HTML_SAVEPATH + "/404.html", "/404.html");

                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/book", "/book");
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/filter", "/filter");
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/index", "/index");
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/pages", "/pages");
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/page", "/page");
                FTPUtil.uploadDirectory(ip, user, pwd, Const.HTML_SAVEPATH + "/seris", "/seris");
                log.info("上传html文件 over");
            }
        });
    }

    @At("/ftp_cleanall")
    @Ok("redirect:/log")
    public void ftp_cleanall() {
        String ip = configService.getIP();
        String user = configService.getUser();
        String pwd = configService.getPwd();
        Threads.run(new Runnable() {
            @Override
            public void run() {
                FTPUtil.removeDirectory(ip, user, pwd, "/");
            }
        });
    }


    @At("/ftp_upload_recent")
    @Ok("redirect:/log")
    public void htmlTask() {

        Threads.run(new Runnable() {
            @Override
            public void run() {
//                List<tb_singlepage> list = configService.dao().query(tb_singlepage.class, Cnd.NEW().and("ut", ">", new Date(Times.now().getTime() - Times.T_1D * 10)));

                //定时任务, 每天处理最新的10篇文章, 一天不可能写10篇博客吧?
                Pager p = new Pager();
                p.setPageSize(10);
                p.setPageNumber(1);
                List<tb_singlepage> list = configService.dao().query(tb_singlepage.class, Cnd.NEW().orderBy("ut", "desc"), p);
                if (Lang.isEmpty(list)) {
                    return;
                }

                //single  首页
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
                int count_archive_pages = createHtml.getCount_4_byMonth();


                //标签页  /tags.html
                int count_tag = 1;
                //每个文章里多个标签  对应的分页的数量之和
                int count_tag_pages = createHtml.getCount_5_byPages(list);

                //关于我
                int count_me = 1;
                //项目列表总共多少页

                //404页面
                int count_404 = 1;

                //生成每个单页
                final List<tb_singlepage> listpages = list;
                int listpages_ = listpages == null ? 0 : listpages.size();
                

                //多少个系列
                List serislist = new ArrayList<>();
                List booklist = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    serislist.add(list.get(i).get_serisid());
                    booklist.add(list.get(i).get_bookid());
                }

                List<tb_seris> seris_ = configService.dao().query(tb_seris.class, Cnd.where("_isdraft", "=", 0).and("_bookid", "=", 0).and("_id", "in", serislist));
                int seris_count = seris_ == null ? 0 : seris_.size();

                //多少本书
                List<tb_book> books_ = configService.dao().query(tb_book.class, Cnd.where("_isdraft", "=", 0).and("_id", "in", booklist));
                int books_count = books_ == null ? 0 : books_.size();

                final int all = count_index_pages + count_filter_pagesingle + count_filter_pageseris + count_filter_pagebook + count_archive +
                        count_archive_pages + count_tag + count_tag_pages + count_me + listpages_ + seris_count + count_404 + books_count;

                //config表， tag表，single表(_toppic, _content_html)
                List<String> images = createHtml.getCount_6_allcount(list);
                final int all_ftp = all + images.size();
                log.debug("all:" + all);
                log.debug("all ftp:" + all_ftp);


                //首页及其分页链接
                for (int pageno = 1; pageno <= count_index_pages; pageno++) {
                    logService.setProcess(all);
                    Files.createDirIfNoExists(Const.HTML_SAVEPATH_TEMP + "index/");
                    if (pageno == 1) {
                        createHtml.createhtml("index.ftl", indexService.getIndexMapdata(pageno), Const.HTML_SAVEPATH_TEMP + "index.html");
                        try {
                            Files.copyFile(new File(Const.HTML_SAVEPATH_TEMP + "index.html"), new File(Const.HTML_SAVEPATH_TEMP + "index/" + "1.html"));
                            Files.copyFile(new File(Const.HTML_SAVEPATH_TEMP + "index.html"), new File(Const.HTML_SAVEPATH_TEMP + "index.htm"));
                        } catch (IOException e) {
                        }
                    } else {
                        createHtml.createhtml("index.ftl", indexService.getIndexMapdata(pageno), Const.HTML_SAVEPATH_TEMP + "/index/" + pageno + ".html");
                    }
                    log.info("正在生成首页: " + pageno + "/" + count_index_pages);
                }
                log.info("生成首页over");

                //按照经验分页
                for (int i = 1; i <= count_filter_pagesingle; i++) {
                    logService.setProcess(all);
                    createHtml.createhtml("filter_page_single.ftl", indexService.getIndexMapdata_filter_single_seris_book("single", i), Const.HTML_SAVEPATH_TEMP + "filter/page/single/" + i + ".html");
                }
                //按照教程分页
                for (int i = 1; i <= count_filter_pageseris; i++) {
                    logService.setProcess(all);
                    createHtml.createhtml("filter_page_seris.ftl", indexService.getIndexMapdata_filter_single_seris_book("seris", i), Const.HTML_SAVEPATH_TEMP + "filter/page/seris/" + i + ".html");
                }
                //每个教程的menu
                for (int i = 0; i < seris_.size(); i++) {
                    logService.setProcess(all);
                    tb_seris tb = seris_.get(i);
                    createHtml.createhtml("menu_seris.ftl", menuService.getseris(tb.get_id()), Const.HTML_SAVEPATH_TEMP + "seris/" + tb.get_id()+"/" + tb.get_seristitleen() + ".html");
                }

                //按照图书分页
                for (int i = 1; i <= count_filter_pagebook; i++) {
                    logService.setProcess(all);
                    createHtml.createhtml("filter_page_book.ftl", indexService.getIndexMapdata_filter_single_seris_book("book", i), Const.HTML_SAVEPATH_TEMP + "filter/page/book/" + i + ".html");
                }
                //每本书的menu
                for (int i = 0; i < books_.size(); i++) {
                    logService.setProcess(all);
                    tb_book tb = books_.get(i);
                    createHtml.createhtml("menu_note.ftl", menuService.getbook(tb.get_id()), Const.HTML_SAVEPATH_TEMP + "book/" + tb.get_id() + "/" + tb.get_booktitleen() + ".html");
                }

                //归档页面
                logService.setProcess(all);
                Map data = archiveService.data();
                createHtml.createhtml("archive.ftl", data, Const.HTML_SAVEPATH_TEMP + "archive.html");

                //归档页面中的所有分页    count_archive_pages
                Files.createDirIfNoExists(Const.HTML_SAVEPATH_TEMP + "filter/month/");
                for (int i = 1; i <= count_archive_pages; i++) {
                    logService.setProcess(all);
                    Files.createDirIfNoExists(Const.HTML_SAVEPATH_TEMP + "filter/month/" + Times.format("yyyyMM", new Date()) + "/");
                    createHtml.createhtml("filter_month.ftl", indexService.getIndexMapdata_filter("month", Times.format("yyyyMM", new Date()), i), Const.HTML_SAVEPATH_TEMP + "filter/month/" + Times.format("yyyyMM", new Date()) + "/" + i + ".html");
                }


                //标签页面
                logService.setProcess(all);
                Map data_tag = tagService.getdata();
                createHtml.createhtml("tags.ftl", data_tag, Const.HTML_SAVEPATH_TEMP + "tags.html");

                //每个标签对应的分页
                if (count_tag_pages > 0) {
                    Map<String, Integer> tags = createHtml.getCount_5_byPages_(list);
                    Iterator it2 = tags.keySet().iterator();
                    while (it2.hasNext()) {
                        String tagid = (String) it2.next();
                        int pagecount = tags.get(tagid);
                        for (int i = 1; i <= pagecount; i++) {
                            logService.setProcess(all);
                            Files.createDirIfNoExists(Const.HTML_SAVEPATH_TEMP + "filter/tag/" + tagid + "/");
                            createHtml.createhtml("filter_tag.ftl", indexService.getIndexMapdata_filter("tag", tagid, i), Const.HTML_SAVEPATH_TEMP + "filter/tag/" + tagid + "/" + i + ".html");
                        }
                    }
                }


                //我
                logService.setProcess(all);
                createHtml.createhtml("me.ftl", NutMap.NEW().setv("rs", configService.get_site_aboutme()), Const.HTML_SAVEPATH_TEMP + "me.html");


                //404
                logService.setProcess(all);
                createHtml.createhtml("404.ftl", new HashMap(), Const.HTML_SAVEPATH_TEMP + "404.html");


                //每一篇详情页 [page  serispage]
                if (listpages_ > 0) {
                    for (int i = 0; i < listpages.size(); i++) {
                        logService.setProcess(all);
                        log.info((i + 1) + "/" + listpages.size());
                        final tb_singlepage tbin = listpages.get(i);
                        if (tbin.get_serisid() == 0) {
                            createHtml.createhtml_page(tbin.get_id(), "page.ftl", Const.HTML_SAVEPATH_TEMP + "/page/" + tbin.get_id() + "/" + tbin.get_titleen() + ".html");
                        } else {
                            createHtml.createhtml_seriespage(tbin.get_id(), "pages.ftl", Const.HTML_SAVEPATH_TEMP + "/pages/" + tbin.get_id() + "/" + tbin.get_titleen() + ".html");
                        }
                    }
                }

                log.info("生成详情页over,数量" + listpages.size());
                log.info("生成全部页面 over,数量" + all);
                log.info("生成相关文件 over (保存位置 " + Const.HTML_SAVEPATH_TEMP + ")");

                //拷贝图片
                for (int i = 0; i < images.size(); i++) {
                    String from = Const.HTML_SAVEPATH + "/images/" + images.get(i);
                    String to = Const.HTML_SAVEPATH_TEMP + "/images/" + images.get(i);
                    Files.copy(new File(from), new File(to));
                }

                String ftp = configService.getIP();
                String user = configService.getUser();
                String pwd = configService.getPwd();
                if (FTPUtil.testconn(ftp, user, pwd)) {
                    FTPUtil.uploadDirectory(ftp, user, pwd, Const.HTML_SAVEPATH_TEMP, "/");
                }
                try {
                    Files.copyDir(new File(Const.HTML_SAVEPATH_TEMP), new File(Const.HTML_SAVEPATH));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Files.deleteDir(new File(Const.HTML_SAVEPATH_TEMP));

            }
        });
    }


}
