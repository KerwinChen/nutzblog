package test01;


import net.javablog.util.JsoupBiz;
import org.nutz.lang.Files;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Test_jsoup {

    private static final String[] users = new String[]{"shuo.liu.7374", "lynn.zhu.3", "luxuan.wang.7", "livia.yang.10", "rui.sun.581", "cai.ren.988", "xuxuan.ren", "ashin.jin", "chong.liu.161", "chloe.cui.5", "cui.shijun", "yiying.zhao.9", "hiyesterday", "kelia.lu", "betty.chen.944023", "yang.xueqing", "jing.lu.737", "yingying.zhao.9", "happyingrid0423", "b.en.969", "yhuyou.handbyether", "lu.you.9", "grace.lam.3323", "yitong.lin.37", "SSSSSSilent", "ponfywang", "hennie.chin", "amy.tang.148116", "inseok.jang.3990", "linkinpark.wewe", "iamkisa", "dedenljhooker", "sue.zhou.777", "tommy.huang.1428", "mariarosa.spinaiaconis", "gouz.dimz", "john98887", "wstar168", "nkem.okoye.319", "xxwoon", "gai.declan", "poter.guan", "nanthawat.oonkanta", "suiym", "dannydynomite5000", "claudio.mefisto", "edmandl"};

    public static void main(String[] args) {

//        test01();
        Set<String> users_2 = new HashSet<String>();
        for (int i = 0; i < users.length; i++) {
            users_2.add(users[i]);
        }

        Iterator it = users_2.iterator();
        while (it.hasNext()) {
            String u = it.next().toString();
            System.out.print("\"" + u + "\"" + ",");
        }



    }

    private static void test01() {
        String html = Files.read("/home/zhu/Desktop/123.html");
        List<String> users = JsoupBiz.getList_Attr(html, ".fsl.fwb.fcb a", "href");

        // https://www.facebook.com/wstar168?fref=pb&hc_location=friends_tab

        for (int i = 0; i < users.size(); i++) {
            String user = users.get(i);
            if (!user.contains("profile.php")) {
                user = user.replace("https://www.facebook.com/", "");
                user = user.replace("?fref=pb&hc_location=friends_tab", "");
                System.out.print("\"" + user + "\"" + ",");
            }
        }

        System.out.println();
    }
}
