package net.javablog.module;

import net.javablog.init.Const;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Streams;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.view.HttpServerResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@IocBean
public class View {

    /**
     * @param fileid 包括文件后缀
     * @return
     */
    @At("/down/*")
    @Ok("raw:jpg")
    public File down(String fileid) {
        return new File(Const.savepath + fileid);
    }

    @At("/view/*")
    @Ok("void")
    public void view(String fileid, HttpServletResponse resp) {
        File f = new File(Const.savepath + fileid);
        try {
            Streams.writeAndClose(resp.getOutputStream(), new FileInputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
