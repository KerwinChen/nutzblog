package net.javablog.util;

import org.nutz.lang.random.R;

import java.util.List;

public class Util {


    /**
     * 替换所有的 h2标签的id为  uuid
     * <h2 id="h2-中文不好使...">
     *
     * @param content_html
     * @return
     */
    public static String processH2(String content_html) {
        List<String> allh2 = JsoupBiz.getList_OutHtml(content_html, "h2");
        //肯定有h2标签的
        for (int i = 0; i < allh2.size(); i++) {
            String h2 = "<h2 id=\"h2-" + R.UU16() + "\">" + JsoupBiz.getOne_Text(allh2.get(i), "h2") + "</h2>";
            content_html = content_html.replace(allh2.get(i), h2);
        }
        return content_html;
    }


}
