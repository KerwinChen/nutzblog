package net.javablog.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translates {


    public static void main(String[] args) {
        String out = trans("Hadoop 1.2.1 简单集群配置Hadoop 1.2.1 简单集群配");
        System.out.println(out);
    }


    /**
     * 百度翻译
     *
     * @param key
     * @return
     */
    public static String trans(String key) {

        Map param = new HashMap<String, String>();
        param.put("from", "zh");
        param.put("to", "en");
        param.put("query", key);
        param.put("transtype", "realtime");
        param.put("simple_means_flag", "3");
        String out = "";
        try {

            Response response = Http.post2("http://fanyi.baidu.com/v2transapi", param, 30000);

            out = response.getContent();
            if (response.getStatus() == 200) {
                Map<String, Map> m = Json.fromJsonAsMap(Map.class, out);

                Map<String, Object> m1 = m.get("trans_result");

                List m2 = (List) m1.get("data");
                Map m3 = (Map) m2.get(0);
                out = m3.get("dst").toString();
                System.out.println(m);
            } else {
                out = "";
            }
        } catch (Exception e) {
            out = "";
        }
        if (Strings.isBlank(out)) {
            return R.UU16();
        }

        out = out.replaceAll("[\\p{Punct}‘]", "");
        out = out.replace("  ", "");
        out = out.replace(" ", "-");

        if (out.startsWith("-")) {
            out = out.substring(1);
        }
        if (out.endsWith("-")) {
            out = out.substring(0, out.length() - 1);
        }
        return out.toLowerCase();
    }

    /**
     * 中文翻译成url中可用的英文
     *
     * @return
     */
    public static String trans_bygoogle(String key) {

        String requesturl = null;
        try {
            requesturl = "http://translate.google.cn/translate_a/single?client=t&sl=zh-CN&tl=en&hl=zh-CN&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&dt=at&ie=UTF-8&oe=UTF-8&source=btn&ssel=6&tsel=3&kc=0&tk=971547|576743&q=" + URLEncoder.encode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String outjson = Http.get(requesturl).getContent();
        System.out.println(outjson);
        String a = Translates.search(2, outjson);
        a = "[" + a + "]";

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String[][] array_temp = gson.fromJson(a, String[][].class);

        StringBuffer o = new StringBuffer();
        for (int i = 0; i < array_temp.length; i++) {
            if (!Strings.isBlank(array_temp[i][0])) {
                o.append(array_temp[i][0] + " ");
            }
        }
        String sout = o.toString().trim();

        System.out.println("替换前：" + sout);
        //去掉所有标点
        sout = sout.replaceAll("[\\p{Punct}‘]", "");
//        o=replaceSpecialtyStr(o,pattern,"");
        sout = sout.replace("  ", "");
        sout = sout.replace(" ", "-");

        System.out.println("替换后：" + sout.toLowerCase());

        if (Strings.isBlank(sout.toLowerCase())) {
            return R.UU16();
        }
        if (sout.startsWith("-")) {
            sout = sout.substring(1);
        }
        if (sout.endsWith("-")) {
            sout = sout.substring(0, sout.length() - 1);
        }
        return sout.toLowerCase();
    }


    /**
     * 查找第一个括号内的值[第3个括号内的值，Gson才能正常识别]
     *
     * @param N
     * @param s
     */
    public static String search(int N, String s) {
        int i = 0, k = -1, w = -1;
        do {
            k = s.indexOf("[", k + 1);
            i++;
        } while (i < N && k != -1);

        if (k > 0) {
            int first = k + 1;
            System.out.println("查找字符串\"" + s + "\"的第" + N + "个左括号内的内容，匹配起始位于字符串的第:" + (first) + "位");
            w = s.indexOf("]", k + 1);
            w = a(w, k, s);
            int end = w;
            System.out.println("匹配结束位于字符串的第:" + (end + 1) + "位");
            System.out.println("输出为：" + s.substring(first, end));
            return s.substring(first, end);

        } else {
            System.out.println("不存在']'");
            return "";
        }


    }


    private static int a(int w, int k, String s) {
        if (w > 0) {
            int m = 1, n = 1;
            boolean f = false;
            do {
                if (s.indexOf("[", k + 1) > 0 && s.indexOf("[", k + 1) < w) {
                    k = s.indexOf("[", k + 1);
                    m += 1;
                }
                while (m > n) {
                    while (s.indexOf("[", k + 1) > 0 && s.indexOf("[", k + 1) < w) {
                        m++;
                        k = s.indexOf("[", k + 1);

                    }

                    for (int j = 0; j <= m - n; j++) {
                        n++;
                        w = s.indexOf("]", w + 1);
                    }
                }

                if (s.indexOf("[", k + 1) > 0 && s.indexOf("[", k + 1) < w)
                    f = true;
                else f = false;
            } while (f);
        } else {
            System.out.println("不存在'['");
        }
        return w;
    }

}