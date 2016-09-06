package net.javablog.bean;


import org.nutz.dao.entity.annotation.*;

import java.util.Date;


/**
 * 文件存储 主要用到  _filekey _suffix 两个字段
 * 实际文件是  _filekey  作为文件名
 * <p>
 * _suffix 只是备用信息
 */

@Table("tb_files")
/**
 *   记录上传的文件，文章删除的时候，tag删除的时候，要同步删除这些引用的文件。
 */
@TableIndexes(
        {@Index(name = "idx_filekey", fields = {"_filekey"}, unique = false),
                @Index(name = "idx_name", fields = {"_name"}, unique = false),
                @Index(name = "idx_downurl", fields = {"_downurl"}, unique = false)
        }
)
public class tb_files extends BasePojo {

    @Id
    @Column
    @Comment("自动递增")
    private int _id;

    @ColDefine(width = 50)
    @Column
    @Comment("存储的文件名，上传的文件的原始名称")
    private String _name;

    @ColDefine(width = 50)
    @Column
    @Comment("存储的文件名,不包括后缀名。")
    private String _filekey;


    @ColDefine(width = 50)
    @Column
    @Comment("存储的文件名,包含点。")
    private String _suffix;

    @ColDefine(width = 100)
    @Column
    @Comment("_downurl  ,  第三方文件存储上完整的下载地址,比如七牛 ")
    private String _downurl;

    public String get_downurl() {
        return _downurl;
    }

    public void set_downurl(String _downurl) {
        this._downurl = _downurl;
    }


    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_filekey() {
        return _filekey;
    }

    public void set_filekey(String _filekey) {
        this._filekey = _filekey;
    }

    public String get_suffix() {
        return _suffix;
    }

    public void set_suffix(String _suffix) {
        this._suffix = _suffix;
    }

    public static tb_files me(String originname, String key, String suffix) {
        tb_files t = new tb_files();
        t.set_name(originname);
        t.set_filekey(key);
        t.set_suffix(suffix);
//        t.set_downurl(Qiniu.getdownload(key));
        t.setCt(new Date());
        t.setUt(new Date());
        return t;
    }


}




