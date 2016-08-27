package net.javablog.module;


import net.javablog.bean.tb_files;
import net.javablog.init.Const;
import net.javablog.service.FileService;
import net.javablog.util.CurrentUserUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;
import org.nutz.mvc.view.HttpServerResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
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

        tb_files t = getTb_files(file, fieldid);


        NutMap map = NutMap.NEW();
        map.setv("imgid", t.get_filekey());
        map.setv("path", t.get_downurl());
        map.setv("imgname", t.get_name());

        //for  editor.md
        map.setv("success", 1);
        map.setv("url", t.get_downurl());

        return map;
    }

    @Ok("json")
    @At("/adm/upload_editor")
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
    public Map upload_editor(@Param("editormd-image-file") TempFile file, @Param(value = "fieldid", df = "0") int fieldid, HttpServletRequest req, HttpServerResponse resp) {

        System.out.println(file);

        tb_files t = getTb_files(file, fieldid);


        NutMap map = NutMap.NEW();
//        map.setv("imgid", t.get_filekey());
//        map.setv("path", t.get_downurl());
//        map.setv("imgname", t.get_name());

        //for  editor.md
        map.setv("success", 1);
        map.setv("url", t.get_downurl());

        return map;
    }

    private tb_files getTb_files(@Param("editormd-image-file") TempFile file, @Param(value = "fieldid", df = "0") int fieldid) {
        tb_files t = new tb_files();
        if (fieldid > 0) {
            t = fileService.fetch(fieldid);
            t.set_name(file.getMeta().getFileLocalName());
            t.setUpdateTime(new Date());
            fileService.updateIgnoreNull(t);
        } else {
            String uid = R.UU16();
            t.setCreateTime(new Date());
            t.setUpdateTime(new Date());
            t.set_filekey(uid + Files.getSuffix(file.getMeta().getFileExtension()));
            t.set_name(file.getMeta().getFileLocalName());
            t.set_downurl("/down/" + t.get_filekey() + "/");
            fileService.insert(t);
        }

        try {
            file.write(Const.savepath + t.get_filekey());
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    @Ok("fm:adm.selectimg")
    @At("/adm/upload/selectimg")
    public String selectimg() {
        return "adm/selectimg";
    }

    //选择图片的 分页查询请求
    //doshowlist_selectimg
    @Ok("json")
    @At("/adm/upload/doshowlist_selectimg")
    public Map query_selectimg(@Param("pageno") int pageno, @Param(value = "args", df = "") String args) {
        NutMap map = NutMap.NEW();

        if (!Strings.isEmpty(args)) {
            try {
                args = URLDecoder.decode(args, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            args = Strings.trim(args);
        }

        SimpleCriteria criteria = Cnd.cri();
        if (!Strings.isEmpty(args)) {
            criteria.where().and("_name", "like", "%" + args + "%");
        }


        Sql sqllist = Sqls.create("select * from tb_files $condition").setCondition(criteria);
        Sql sqlcount = Sqls.create("select count(*) from tb_files $condition").setCondition(criteria);

        List datas = fileService.getObjListByPage(sqllist, pageno,15);
        String page = fileService.getPageHtmlByPage(sqlcount, args, pageno,15);

        map.setv("datas", datas);
        map.setv("pages", page);

        return map;
    }


}
