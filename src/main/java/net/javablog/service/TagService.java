package net.javablog.service;


import net.javablog.bean.tb_singlepage;
import net.javablog.bean.tb_tag;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IocBean(fields = "dao")
public class TagService extends BaseService<tb_tag> {

    /**
     * 返回tagstr对应的字符串
     *
     * @param tb
     * @return
     */
    public String getTagStr(tb_singlepage tb) {
        StringBuffer strtag = new StringBuffer();
        if (!Strings.isBlank(tb.get_tags())) {
            String[] arr = tb.get_tags().split(",");
            if (!Lang.isEmpty(arr)) {
                for (int i = 0; i < arr.length; i++) {
                    // 检查是否有图片 .如果有图片.
                    tb_tag t = fetch(Cnd.where("_name", "=", arr[i]));
                    if (t == null) {
                        continue;
                    }

                    String imgstr = "";
                    if (!Strings.isBlank(t.get_img())) {
                        imgstr = " class=\"tag-img\" style=\"background-image: url(/images/" + t.get_img() + ");\"";
                    }

                    if (i == arr.length - 1) {
                        strtag.append(" <a   " + imgstr + "  href=\"/filter/tag/" + getTagIdByName(arr[i]) + "/1.html\">" + arr[i] + "</a>");
                    } else {
                        strtag.append(" <a  " + imgstr + " href=\"/filter/tag/" + getTagIdByName(arr[i]) + "/1.html\">" + arr[i] + "</a> ");
                    }
                }
            }
        }
        return strtag.toString();
    }

    private String getTagStr(String pname, List<tb_tag> tags) {

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < tags.size(); i++) {
            tb_tag tag = tags.get(i);
            if (Strings.isBlank(tag.get_img())) {
                stringBuffer.append("<a    href=\"/filter/tag/" + tag.get_id() + "/1.html\">" + tag.get_name() + "</a>\n");
            } else {
                stringBuffer.append("<a class=\"tag-img\" style=\"background-image: url(/images/" + tag.get_img() + ");\" href=\"/filter/tag/" + tag.get_id() + "/1.html\">" + tag.get_name() + "</a>\n");
            }
        }

        StringBuffer stringBufferOut = new StringBuffer();
        stringBufferOut.append("<dl>\n" +
                "                                <dt>" + pname + "</dt>\n" +
                "                                <dd>\n" +
                "                                    <span class=\"meta-value\">\n" +
                "                                    " + stringBuffer + "                                                       " +
                "                                    </span>\n" +
                "\n" +
                "                                </dd>\n" +
                "                            </dl>");

        return stringBufferOut.toString();
    }

    public int getTagIdByName(String _name) {
        tb_tag tag = fetch(Cnd.where("_name", "=", _name));
        return tag.get_id();
    }

    public String getTagNameById(int _id) {
        tb_tag tag = fetch(_id);
        return tag.get_name();
    }


    public Map getdata() {
        Map map = new HashMap();

        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();

        Sql sql = Sqls.create("SELECT _pname from  tb_tag  GROUP BY _pname");
        sql.setCallback(Sqls.callback.strList());
        dao().execute(sql);
        List<String> pnames = sql.getList(String.class);

        if (!Lang.isEmpty(pnames)) {
            for (int i = 0; i < pnames.size(); i++) {
                String pname = pnames.get(i);
                List<tb_tag> tags = dao().query(tb_tag.class, Cnd.where("_pname", "=", pname));

                //第一个位置
                if (i % 3 == 0) {
                    stringBuffer1.append(getTagStr(pname, tags));
                }

                //第二列
                if (i % 3 == 1) {
                    stringBuffer2.append(getTagStr(pname, tags));
                }

//                第三例
                if (i % 3 == 2) {
                    stringBuffer3.append(getTagStr(pname, tags));
                }
            }

            map.put("s1", stringBuffer1);
            map.put("s2", stringBuffer2);
            map.put("s3", stringBuffer3);
        }

        return map;
    }
}
