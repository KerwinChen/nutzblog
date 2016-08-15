package net.javablog.bean;

import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;


@Table("tb_tag")
public class tb_tag  extends BasePojo  implements Serializable {
    @Id
    @Comment("自增id")
    private int _id;

    @Column
    @ColDefine(width = 100)
    @Comment("标签名")
    private String _name;

    @Column
    @ColDefine(width = 100)
    @Comment("所属的分类名")
    private String _pname;


    @Column
    @ColDefine(width = 100)
    @Comment("标签图片,存放在七牛上")
    private String _img;


    @Column
    @ColDefine(width = 1000)
    @Comment("标签简介")
    private String _intro;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_pname() {
        return _pname;
    }

    public void set_pname(String _pname) {
        this._pname = _pname;
    }

    public String get_img() {
        return _img;
    }

    public void set_img(String _img) {
        this._img = _img;
    }

    public String get_intro() {
        return _intro;
    }

    public void set_intro(String _intro) {
        this._intro = _intro;
    }

}
