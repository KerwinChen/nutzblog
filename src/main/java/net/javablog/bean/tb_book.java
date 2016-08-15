package net.javablog.bean;


import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 图书表
 */
@Table("tb_book")
public class tb_book extends BasePojo implements Serializable {


    @Id
    @Comment("自增")
    private int _id;

    @Column
    @ColDefine(width = 100)
    @Comment("书名")
    private String _booktitle;

    @Column
    @ColDefine(width = 500)
    @Comment("书名-英文")
    private String _booktitleen;

    @Column
    @ColDefine(width = 1000)
    @Comment("书的简介")
    private String _bookintro;


    @Column
    @ColDefine(width = 100)
    @Comment("书的作者")
    private String _bookauthor;

    @ColDefine
    @Column
    @Comment("是否是草稿")
    private boolean _isdraft;


    public String get_booktitleen() {
        return _booktitleen;
    }

    public void set_booktitleen(String _booktitleen) {
        this._booktitleen = _booktitleen;
    }

    public boolean is_isdraft() {
        return _isdraft;
    }

    public void set_isdraft(boolean _isdraft) {
        this._isdraft = _isdraft;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_booktitle() {
        return _booktitle;
    }

    public void set_booktitle(String _booktitle) {
        this._booktitle = _booktitle;
    }

    public String get_bookintro() {
        return _bookintro;
    }

    public void set_bookintro(String _bookintro) {
        this._bookintro = _bookintro;
    }

    public String get_bookauthor() {
        return _bookauthor;
    }

    public void set_bookauthor(String _bookauthor) {
        this._bookauthor = _bookauthor;
    }


}
