package net.javablog.service;


import net.javablog.bean.tb_singlepage;
import net.javablog.bean.tb_tag;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;

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
                        imgstr = " class=\"tag-img\" style=\"background-image: url(" + t.get_img() + "?v=" + R.UU16() + ");\"";
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

    public int getTagIdByName(String _name) {
        tb_tag tag = fetch(Cnd.where("_name", "=", _name));
        return tag.get_id();
    }

    public String getTagNameById(int _id) {
        tb_tag tag = fetch(_id);
        return tag.get_name();
    }

}
