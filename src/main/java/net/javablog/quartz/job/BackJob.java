package net.javablog.quartz.job;

import net.javablog.util.BackSite;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *   源码, 图片, 数据库备份
 */

@IocBean
public class BackJob  implements org.quartz.Job  {


    @Inject
    private BackSite backSite;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        backSite.back();
    }



}
