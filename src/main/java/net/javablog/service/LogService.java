package net.javablog.service;


import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.mvc.Mvcs;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@IocBean
public class LogService {


    private Logger log = Logger.getLogger("FTP");

    private int indexpage = 0;
    private int process = 0;


    public void setProcess0() {
        indexpage = 0;
        process = 0;
    }


    /**
     * 每调用一次就+1.  离allCount就进1次。。
     * 获取执行的进度，int是100中的比例，比如60，比如100，最小值是0
     *
     * @return
     */
    public void setProcess(int allcount) {
        indexpage++;
        if (indexpage == allcount) {
            process = 100;
        }
        float f = (float) indexpage / (float) allcount;
        DecimalFormat format = new DecimalFormat(".00");
        String s = format.format(f); //转换成字符串
        float process_ = (Float.valueOf(s)) * 100;
        s = String.valueOf(process_).split("\\.")[0];
        process = Integer.valueOf(s);

        log.info("当前的完成的数量：" + indexpage);
//        System.out.println("当前的完成的数量：" + indexpage);

    }

    public int getProcess() {
        return process;
    }


    /**
     * 获取 log/spring.log 最后100行
     *
     * @return
     */
    public String getLog() {


        //webapp 的根目录
        String filepath = Mvcs.getServletContext().getRealPath("/") + File.separator + "ftp.log";


        File f = new File(filepath);
        if (!f.exists()) {
            filepath = getRoot() + File.separator + "ftp.log";
            if (!(new File(filepath).exists())) {
                log.info("没有找到日志文件");
                return "没有找到日志文件";
            }
        }

        List<String> lines = Files.readLines(new File(filepath));

        if (Lang.isEmpty(lines)) {
//            log.info("暂时没有日志");
            return "";
        }

        StringBuffer strb = new StringBuffer();

        int begin = 0;
        if (lines.size() > 8) {
            begin = lines.size() - 8;
        }

        for (int i = begin; i < lines.size(); i++) {
            strb.append(lines.get(i) + "<br>");
        }

        return strb.toString();
    }

    public String getRoot() {
        String root = null;
        try {
            root = new File("./").getCanonicalPath() + File.separatorChar;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
