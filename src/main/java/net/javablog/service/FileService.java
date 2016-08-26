package net.javablog.service;


import net.javablog.bean.tb_book;
import net.javablog.bean.tb_files;
import org.nutz.ioc.loader.annotation.IocBean;

@IocBean(fields = "dao")
public class FileService extends BaseService<tb_files> {
}
