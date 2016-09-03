package net.javablog.module.adm.html;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.javablog.service.BlogService;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

@IocBean
public class CreateHtml {

    @Inject
    private BlogService blogService;

    @Inject
    private Configuration cf;

    @Inject
    private NutDao dao;

    private static final Logger log = LoggerFactory.getLogger("FTP");

    public void createhtml_page(int pageid, String template_name, String htmlfile) {
        createhtml(template_name, blogService.getpage(pageid), htmlfile );
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


}
