package net.javablog.module.adm.html;


import com.alibaba.druid.pool.DruidDataSource;
import net.javablog.util.CurrentUserUtils;
import net.javablog.util.RunES_IndexJob;
import net.javablog.util.Threads;
import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CheckSession;

@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class EsIndexModule {

    private static Logger log = Logger.getLogger("FTP");

    @Inject
    private DruidDataSource ds;

    @At("/esindex")
    @Ok("redirect:/log")
    public void esindex() {
        Threads.run(new Runnable() {
            @Override
            public void run() {
                RunES_IndexJob.repeatRows("tb_singlepage", ds.getUrl(), ds.getUsername(), ds.getPassword());
            }
        });
    }
}
