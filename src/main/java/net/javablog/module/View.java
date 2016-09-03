package net.javablog.module;

import net.javablog.init.Const;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Streams;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

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
        return new File(Const.IMG_SAVEPATH + fileid);
    }

    /**
     * 这里是服务器端代码，务必传递的参数最后有 / 结尾
     *
     * @param fileid
     * @param resp
     */
    @At("/images/?")
    @Ok("void")
    public void view(String fileid, HttpServletResponse resp) {
        File f = new File(Const.IMG_SAVEPATH + fileid);
        try {
            Streams.writeAndClose(resp.getOutputStream(), new FileInputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
