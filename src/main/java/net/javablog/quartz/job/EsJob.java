package net.javablog.quartz.job;


import com.alibaba.druid.pool.DruidDataSource;
import net.javablog.util.RunES_IndexJob;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@IocBean
public class EsJob implements org.quartz.Job {

    private static final Log log = Logs.get();

    @Inject
    protected Dao dao;
    @Inject
    private DruidDataSource ds;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        RunES_IndexJob.repeatRows("tb_singlepage", ds.getUrl(), ds.getUsername(), ds.getPassword());
    }


}