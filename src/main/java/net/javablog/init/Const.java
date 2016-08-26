package net.javablog.init;


import org.nutz.lang.Files;

import java.io.File;

public class Const {

    public static final String savepath = System.getProperty("user.home") + File.separatorChar + "images"+ File.separatorChar ;
    static {
        Files.createDirIfNoExists(savepath);
    }

}
