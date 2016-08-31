package net.javablog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.ArrayList;
import java.util.List;

public class JsoupBiz {

    private static Log log = Logs.get();

    /**
     * 获取指定path的指定属性的值【返回第一个】
     *
     * @param content
     * @param path
     * @return
     */
    public static String getOne_Attr(String content, String path, String attr) {
        String out = "";
        try {
            out = Jsoup.parse(content).select(path).get(0).attr(attr);
        } catch (Exception e) {
            log.info("没有值,{},{}"+ path+","+ attr);
        }
        return out;
    }

    public static String getTextFromTHML(String htmlStr) {
        Document doc = Jsoup.parse(htmlStr);
        String text = doc.text();
        // remove extra white space
        StringBuilder builder = new StringBuilder(text);
        int index = 0;
        while(builder.length()>index){
            char tmp = builder.charAt(index);
            if(Character.isSpaceChar(tmp) || Character.isWhitespace(tmp)){
                builder.setCharAt(index, ' ');
            }
            index++;
        }
        text = builder.toString().replaceAll(" +", " ").trim();
        return text;
    }


    /**
     * 取得path对应节点的上一个兄弟节点
     *
     * @param content
     * @param path
     * @param attr
     */
    public static String getPrevOne_attr(String content, String path, String attr) {
        String out = "";
        try {
            out = Jsoup.parse(content).select(path).get(0).previousElementSibling().attr(attr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }


    /**
     * 获取指定path的text的值【返回第一个】
     *
     * @param content
     * @param path
     * @return
     */
    public static String getOne_Text(String content, String path) {

        Elements es = Jsoup.parse(content).select(path);
        if (Lang.isEmpty(es)) {
            return "";
        }
        return es.get(0).text();
    }

    /**
     * 获取指定片段内容的Document对象
     *
     * @param content
     * @return
     */
    public static Document getDoc(String content) {
        Document doc = Jsoup.parse(content);
        if (Lang.isEmpty(doc)) {
            return null;
        }
        return doc;
    }


    /**
     * 获取指定path的html的值【返回第一个】
     *
     * @param content
     * @param path
     * @return
     */
    public static String getOne_Html(String content, String path) {
        String out = "";
        try {
            out = Jsoup.parse(content).select(path).get(0).html();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;

    }

    /**
     * 获取指定path的Outhtml的值【返回第一个】
     *
     * @param content
     * @param path
     * @return
     */
    public static String getOne_Outhtml(String content, String path) {
        try {
            return Jsoup.parse(content).select(path).get(0).outerHtml();
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取指定path的指定属性的值【返回所有】
     *
     * @param content
     * @param path
     * @return
     */
    public static List<String> getList_Attr(String content, String path, String attr) {
        List<String> out = new ArrayList<String>();
        Elements es = Jsoup.parse(content).select(path);
        if (Lang.isEmpty(es)) {
            return new ArrayList();
        }

        for (Element e : es) {
            out.add(e.attr(attr));
        }
        return out;
    }

    /**
     * 取得指定path中list集合里，每个元素的text，组成list返回
     *
     * @param content
     * @param path
     * @return
     */
    public static List<String> getList_Text(String content, String path) {
        List<String> out = new ArrayList<String>();
        Elements es = Jsoup.parse(content).select(path);
        if (Lang.isEmpty(es)) {
            return new ArrayList();
        }

        for (Element e : es) {
            out.add(e.text());
        }
        return out;
    }

    /**
     * 取得指定path中list集合里，每个元素的html，组成list返回
     *
     * @param content
     * @param path
     * @return
     */
    public static List<String> getList_Html(String content, String path) {
        List<String> out = new ArrayList<String>();
        Elements es = Jsoup.parse(content).select(path);
        if (Lang.isEmpty(es)) {
            return new ArrayList();
        }

        for (Element e : es) {
            out.add(e.html());
        }
        return out;
    }


    /**
     * 取得指定path中list集合里，每个元素的text，组成list返回
     *
     * @param content
     * @param path
     * @return
     */
    public static List<String> getList_OutHtml(String content, String path) {
        List<String> out = new ArrayList<String>();
        Elements es = Jsoup.parse(content).select(path);
        if (Lang.isEmpty(es)) {
            return new ArrayList();
        }

        for (Element e : es) {
            out.add(e.outerHtml());
        }
        return out;
    }

    /**
     * 返回获取的元素的数量
     *
     * @param content
     * @return
     */
    public static int getCount(String content, String path) {
        Elements es = Jsoup.parse(content).select(path);
        if (Lang.isEmpty(es)) {
            return 0;
        }
        return es.size();
    }


}
