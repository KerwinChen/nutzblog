package net.javablog.util;

import org.nutz.lang.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class StreamGobbler extends Thread {


    private static final Logger log = LoggerFactory.getLogger(StreamGobbler.class);

    InputStream is;
    String type;  //输出流的类型ERROR或OUTPUT 

    StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public String out_common = "";
    public String out_error = "";


    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is, Lang.isWin() ? "GBK" : "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {

                log.info(type + ">" + line);

                if (type.equals("ERROR")) {
                    out_error = out_error + line + ";";
                    log.info(out_common);
                } else {
                    out_common = out_common + line + ";";
                    log.info(out_common);
                }
                System.out.flush();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public String run_return() {
        String out = "";
        try {
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                log.info(type + ">" + line);

                if (type.equals("ERROR")) {
                    out_error = out_error + line + ";";
                } else {
                    out_common = out_common + line + ";";
                }
                System.out.flush();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if (type.equals("ERROR")) {
            return out_error;
        } else {
            return out_common;
        }
    }
}