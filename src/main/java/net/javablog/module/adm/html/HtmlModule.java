package net.javablog.module.adm.html;


import net.javablog.bean.tb_singlepage;
import net.javablog.init.Const;
import net.javablog.service.FtpConfigService;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.FTPUtil;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;


@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class HtmlModule {


    @Inject
    private NutDao dao;

    @Inject
    private CreateHtml createHtml;

    @Inject
    private FtpConfigService ftpConfigService;


    /**
     * 生成html
     * <p>
     * ？1     single,seris,book
     * ?  2    _id
     *
     * @param type
     * @param pageid
     * @return
     */
    @At("/html/?/?")
    @Ok("redirect:/log")
    public void html(String type, int pageid) {

        String ip = ftpConfigService.getIP();
        String user = ftpConfigService.getUser();
        String pwd = ftpConfigService.getPwd();

        if (type.equals("single")) {
            final tb_singlepage tbin = dao.fetch(tb_singlepage.class, pageid);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    String tofile = "/page/" + tbin.get_id() + "/" + tbin.get_titleen() + ".html";
                    String fromfile = Const.HTML_SAVEPATH + tofile;

                    createHtml.createhtml_page(tbin.get_id(), "page.ftl", fromfile);
                    FTPUtil.uploadSingleFile(ip, user, pwd, fromfile, tofile);
                }
            }).start();
        }
    }

//
//    /**
//     * 生成seris的html
//     *
//     * @return
//     */
//    @AuthPassport
//    @RequestMapping("/html_seris/{serisid}")
//    public String html_seris(final @PathVariable("serisid") int serisid) {
//        String direct = "redirect:/show_getlog";
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                log.info("html_sris {}", serisid);
//                if (serisid > 0) {
//                    List<tb_singlepage> pages = dao.query(tb_singlepage.class, Cnd.where("_serisid", "=", serisid));
//
//                    log.info("html_sris  pages {}", pages.size());
//
//                    if (!Lang.isEmpty(pages)) {
//                        for (int i = 0; i < pages.size(); i++) {
//                            final tb_singlepage tbin = pages.get(i);
//                            //生成html页面
//                            bizTutorial.createhtml_seriespage(tbin.get_id(), "tutorial_seriespage.ftl", Const.filepath + "/pages/" + tbin.get_id() + "/", tbin.get_titleen() + ".html");
//                            //上传文件  /tutorial/page/1.html
//                            ftp.upload(Const.filepath + "/pages/" + tbin.get_id() + "/", tbin.get_titleen() + ".html", "/pages/" + tbin.get_id() + "/");
//                        }
//                    }
//                    //如果是从book过来的，就不用再生成serisid
//                    tb_seris tbSeris = dao.fetch(tb_seris.class, serisid);
//                    if (tbSeris.get_bookid() == 0) {
//                        createhtml.createhtml("show_menu_seris.ftl", bizTutorial.getseris(tbSeris.get_id()).getModelMap(), Const.filepath + "seris/" + tbSeris.get_id(), tbSeris.get_seristitleen() + ".html");
//                        ftp.upload(Const.filepath + "/seris/" + tbSeris.get_id() + "/", tbSeris.get_seristitleen() + ".html", "/seris/" + tbSeris.get_id() + "/");
//                    }
//
//                }
//            }
//        }).start();
//        return direct;
//    }
//
//
//    /**
//     * 生成book的html
//     *
//     * @return
//     */
//    @AuthPassport
//    @RequestMapping("/html_book/{bookid}")
//    public String html_book(final @PathVariable("bookid") int bookid) {
//        String direct = "redirect:/show_getlog";
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<tb_seris> serises = dao.query(tb_seris.class, Cnd.where("_bookid", "=", bookid));
//                if (!Lang.isEmpty(serises)) {
//                    for (int i = 0; i < serises.size(); i++) {
//                        html_seris(serises.get(i).get_id());
//                    }
//                }
//
//
//                tb_book tbBook = dao.fetch(tb_book.class, bookid);
//                createhtml.createhtml("show_menu_book.ftl", bizTutorial.getbook(tbBook.get_id()).getModelMap(), Const.filepath + "book/" + tbBook.get_id(), tbBook.get_booktitleen() + ".html");
//                ftp.upload(Const.filepath + "/book/" + tbBook.get_id() + "/", tbBook.get_booktitleen() + ".html", "/book/" + tbBook.get_id() + "/");
//
//            }
//        }).start();
//
//        return direct;
//
//    }


}
