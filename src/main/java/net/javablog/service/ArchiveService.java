package net.javablog.service;


import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;

import java.util.*;

@IocBean
public class ArchiveService {


    @Inject
    private BlogService blogService;

    @Inject
    private ConfigService configService;

    public Map data() {
        Map out = new HashMap();

        String build_begindate = configService.getCreateTime();
        long build_long = 0;

        long a = Times.parseq("yyyy-MM-dd", build_begindate).getTime();
        long b = new Date().getTime();
        build_long = (b - a) / Times.T_1D;


        int allcount = blogService.count( Cnd.where("_isdraft", "=", "0"));

        Sql sql = Sqls.create("SELECT  YEAR(ct) as y, MONTH(ct) as m ,count(*) as c from tb_singlepage  where _isdraft=0  and ct is not null GROUP BY y,m ORDER BY y asc   ");
        sql.setCallback(Sqls.callback.records());
        blogService.dao().execute(sql);
        List<Record> records = sql.getList(Record.class);

//        y       m  c
//        2015	6	1
//        2015	7	2
//        2016	2	4

        Map<String, List<String>> datas = new HashMap<String, List<String>>();
        for (int i = 0; i < records.size(); i++) {
            String y = records.get(i).getString("y");
            int m = records.get(i).getInt("m");
            int c = records.get(i).getInt("c");

            List<String> mons = new ArrayList<String>();
            if (!Lang.isEmpty(datas.get(y))) {
                mons = datas.get(y);
            }
            mons.add("" + y + "年" + m + "月 (" + c + "篇文章)" + "#" + y + "" + String.format("%02d", m));
            datas.put(y, mons);
        }

        //通过年份排序
        datas = order(datas);


        out.put("build_begindate", build_begindate);
        out.put("build_long", build_long);
        out.put("allcount", allcount);
        out.put("datas", toString_(datas));

        return out;

    }

    private String toString_(Map<String, List<String>> datas) {

        if (Lang.isEmpty(datas)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> it = datas.keySet().iterator();

        int idx = 3;
        while (it.hasNext()) {
            String key = it.next();
            List<String> v = datas.get(key);
            idx++;

            StringBuffer str = new StringBuffer();
            for (int i = 0; i < v.size(); i++) {
                // mons.add("" + y + "年" + m + "月 (" + c + "篇文章)"+"#y="+y+"&m="+m+"");
                String temp = v.get(i);
                String showtemp = temp.split("#")[0];
                String hreftemp = temp.split("#")[1];
                str.append("<dd><a href=\"/filter/month/" + hreftemp + "/1.html\">" + showtemp + "</a></dd>");
            }

            if (idx % 3 == 1) {
                stringBuffer.append(" <div class=\"row\">");
            }

            if (idx % 3 == 1 || idx % 3 == 2 || idx % 3 == 0) {

                stringBuffer.append("<div class=\"col-md-4\">\n" +
                        "                            <dl>\n" +
                        "                                <dt>" + key + "</dt>\n" +
                        "                                  " + str + "   \n" +
                        "                            </dl>\n" +
                        "                        </div>");
            }


            if (idx % 3 == 0) {
                stringBuffer.append(" </div>");
                continue;
            }

            //如果是最后一个元素,且补上当前行的最后一个 ,需要补上 </div>
            if ((idx - 3 - 1) == (datas.size() - 1)) {
                stringBuffer.append(" </div>");
            }


        }
        return stringBuffer.toString();

    }

    private Map<String, List<String>> order(Map<String, List<String>> datas) {

        Map<String, List<String>> out = new LinkedHashMap<String, List<String>>();

        List<Map.Entry<String, List<String>>> infoIds = new ArrayList<Map.Entry<String, List<String>>>(datas.entrySet());

        // 对HashMap中的key 进行排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, List<String>>>() {
            public int compare(Map.Entry<String, List<String>> o1,
                               Map.Entry<String, List<String>> o2) {

                Integer a = Integer.valueOf(o1.getKey());
                Integer b = Integer.valueOf(o2.getKey());
                return a < b ? 1 : -1;

            }
        });

        Collections.reverse(infoIds);

        // 对HashMap中的key 进行排序后  显示排序结果
        for (int i = 0; i < infoIds.size(); i++) {
            String id = infoIds.get(i).getKey();
            out.put(id, (List<String>) datas.get(id));
        }
        return out;
    }
}
