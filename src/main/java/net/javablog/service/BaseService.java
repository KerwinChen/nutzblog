package net.javablog.service;

import net.javablog.util.PagerUT;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.Lang;
import org.nutz.service.IdEntityService;

import java.util.List;

public class BaseService<T> extends IdEntityService<T> {

    protected final static int DEFAULT_PAGE_SIZE = 20;

    public BaseService() {
        super();
    }


    public T fetch(String id) {
        return dao().fetch(getEntityClass(), id);
    }

    public List getObjListByPage(Sql listsql, int pageno) {
        return getObjListByPage(listsql, pageno, DEFAULT_PAGE_SIZE);
    }

    public List getObjListByPage(Sql listsql, int pageno, int pagesize) {
        Pager pager = new Pager();
        pager.setPageSize(pagesize);
        pager.setPageNumber(pageno);

        listsql.setCallback(Sqls.callback.records());
        listsql.setPager(pager);
        dao().execute(listsql);
        List<Record> list = listsql.getList(Record.class);
        return list;
    }


    public String getPageHtmlByPage(Sql countsql, int pageno) {
        return getPageHtmlByPage(countsql, "", pageno, DEFAULT_PAGE_SIZE);
    }

    public String getPageHtmlByPage(Sql countsql, int pageno, int pagesize) {
        return getPageHtmlByPage(countsql, "", pageno, pagesize);
    }

    public String getPageHtmlByPage(Sql countsql, String args, int pageno) {
        return getPageHtmlByPage(countsql, args, pageno, DEFAULT_PAGE_SIZE);
    }

    public String getPageHtmlByPage(Sql countsql, String args, int pageno, int pagesize) {
        countsql.setPager(null);
        countsql.setCallback(Sqls.callback.longValue());
        dao().execute(countsql);
        Object allcount_obj = countsql.getResult();
        long allcount = 0;
        if (!Lang.isEmpty(allcount_obj)) {
            allcount = Long.valueOf(allcount_obj.toString());
        }
        return PagerUT.pages(pagesize, allcount, pageno, "javascript:page(%s,'" + args + "');", 3);
    }


}