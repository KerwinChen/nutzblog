package net.javablog.quartz.job;


import net.javablog.bean.tb_book;
import net.javablog.bean.tb_seris;
import net.javablog.bean.tb_singlepage;
import net.javablog.init.Const;
import net.javablog.module.adm.html.CreateHtml;
import net.javablog.module.adm.html.FtpModule;
import net.javablog.service.*;
import net.javablog.util.FTPUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * // 每天执行最近  30 个小时文章的静态化.
 * //另外 e
 * // 图片资源对应上传 ,jsoup搜索 长度32的src.
 */
@IocBean
public class HtmlJob implements Job {

    private static final Log log = Logs.get();

    @Inject
    private FtpModule ftpModule;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        ftpModule.htmlTask();
    }


}