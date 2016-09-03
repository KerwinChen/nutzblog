package test01;

import org.nutz.lang.Files;

import java.io.File;

public class Test01 {


    public static void main(String[] args) {

        String path = "C:\\Users\\Administrator\\site_html\\images";

        File[] fs = Files.lsAll(new File(path), null);

        for (int i = 0; i < fs.length; i++) {
            String filename = fs[i].getAbsolutePath();
            if (filename.endsWith("png")) {
                filename = filename.replace(".png", "");
            } else if (filename.endsWith("jpg")) {
                filename = filename.replace(".jpg", "");
            } else {
                continue;
            }
            fs[i].renameTo(new File(filename));

        }
    }


}
