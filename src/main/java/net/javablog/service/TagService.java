package net.javablog.service;


import net.javablog.bean.tb_singlepage;
import net.javablog.bean.tb_tag;
import org.nutz.ioc.loader.annotation.IocBean;

@IocBean(fields = "dao")
public class TagService extends BaseService<tb_tag> {

}
