package net.javablog.service;


import net.javablog.bean.tb_book;
import net.javablog.bean.tb_singlepage;
import org.nutz.ioc.loader.annotation.IocBean;

@IocBean(fields = "dao")
public class NoteService extends BaseService<tb_book> {
}
