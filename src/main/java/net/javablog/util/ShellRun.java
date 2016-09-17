package net.javablog.util;

import org.nutz.lang.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;


public class ShellRun {

    private static final Logger log = LoggerFactory.getLogger(ShellRun.class);

    public static void run(String command) {


        try {
            String[] cmd = new String[3];

            if (iswin()) {
                cmd[0] = "cmd";
                cmd[1] = "/c";
                cmd[2] = command;
            } else {
                cmd[0] = "/bin/bash";
                cmd[1] = "-c";
                cmd[2] = command;
            }

            Runtime rt = Runtime.getRuntime();
            log.info("Executing " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
            Process proc = rt.exec(cmd);
            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");
            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start(); //run　阻塞
            outputGobbler.start();//run　阻塞

            // any error???
            int exitVal = proc.waitFor();
            log.info("ExitValue: " + exitVal);

        } catch (Throwable t) {
            log.error("error", t);
        }
    }


    public static boolean iswin() {
        String osName = System.getProperty("os.name");
        if (Pattern.matches("Linux.*", osName)) {
            return false;
        } else if (Pattern.matches("Windows.*", osName)) {
            return true;

        } else if (Pattern.matches("Mac.*", osName)) {
            return false;
        }
        return false;
    }


    public static void main(String args[]) {
//        run("mysqldump -uroot -proot db_javacore>db.sql");

        String codeing = "git@git.coding.net:javacore/javacore.cn.git";
        ShellRun.run("cd " + System.getProperty("user.home") + ";git clone " + codeing);


    }


}



