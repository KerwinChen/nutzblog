package net.javablog.module;


import net.javablog.bean.tb_files;
import net.javablog.init.Const;
import net.javablog.service.FileService;
import net.javablog.util.CurrentUserUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;
import org.nutz.mvc.view.HttpServerResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Map;

@IocBean
@Filters(@By(type = CheckSession.class, args = {CurrentUserUtils.CUR_USER, "/adm/login"}))
public class UploadModule {

    @Inject
    private FileService fileService;

    /**
     * 新上传/更新图片文件
     *
     * @param file
     * @param fieldid 如果fileid是0，表示新上传
     * @param req
     * @param resp
     * @return
     */
    @Ok("json")
    @At("/adm/upload")
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
    public Map upload(@Param("f") TempFile file, @Param(value = "fieldid", df = "0") int fieldid, HttpServletRequest req, HttpServerResponse resp) {

        System.out.println(file);

        tb_files t = new tb_files();
        if (fieldid > 0) {
            t = fileService.fetch(fieldid);
            t.set_name(file.getName());
            t.setUpdateTime(new Date());
            fileService.updateIgnoreNull(t);
        } else {
            String uid = R.UU16();
            t.setCreateTime(new Date());
            t.setUpdateTime(new Date());
            t.set_filekey(uid + Files.getSuffix(file.getMeta().getFileExtension()));
            t.set_name(file.getName());
            t.set_downurl("/down/" + t.get_filekey() + "/");
            fileService.insert(t);
        }

        try {
            file.write(Const.savepath + t.get_filekey());
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }


        NutMap map = NutMap.NEW();
        map.setv("imgid", t.get_filekey());
        map.setv("path", t.get_downurl());
        return map;
    }


    /**
     * @param fileid 包括文件后缀
     * @return
     */
    @At("/down/*")
    @Ok("raw:jpg")
    public File down(String fileid) {
        return new File(Const.savepath + fileid);
    }


}
