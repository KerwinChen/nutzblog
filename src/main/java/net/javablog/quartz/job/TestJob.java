package net.javablog.quartz.job;


import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@IocBean
public class TestJob implements Job {

    private static final Log log = Logs.get();

    @Inject
    protected Dao dao;

    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.debugf("test %s ", "123");

    }

}