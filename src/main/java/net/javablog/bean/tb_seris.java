package net.javablog.bean;


import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 系列文章的标题
 */
@Table("tb_seris")
@Comment("根据字段字段  _bookid   如果<=0表示单独的系列，否则表示是某本书中的一个章节")
public class tb_seris extends BasePojo implements Serializable {

    @Id
    @Comment("自增")
    private int _id;


    @Column
    @ColDefine(width = 100)
    @Comment("系列的标题")
    private String _seristitle;

    @Column
    @ColDefine(width = 100)
    @Comment("系列的标题 英文")
    private String _seristitleen;


    @Column
    @ColDefine(width = 1000)
    @Comment("系列的简介")
    private String _serisintro;

    @Column
    @Comment("系列的点站次数")
    private int _startcount;


    @Column
    @Comment("如果是<=0,就表示是单独的系列，否则就是属于某本书")
    private int _bookid;


    @Column
    @Comment("所在book的索引位置")
    @Default("0")
    private int _index_inbook;


    @ColDefine
    @Column
    @Comment("是否是草稿")
    private boolean _isdraft;


    public tb_seris() {
    }


    public String get_seristitleen() {
        return _seristitleen;
    }

    public void set_seristitleen(String _seristitleen) {
        this._seristitleen = _seristitleen;
    }

    public boolean is_isdraft() {
        return _isdraft;
    }

    public void set_isdraft(boolean _isdraft) {
        this._isdraft = _isdraft;
    }


    public int get_index_inbook() {
        return _index_inbook;
    }

    public void set_index_inbook(int _index_inbook) {
        this._index_inbook = _index_inbook;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_seristitle() {
        return _seristitle;
    }

    public void set_seristitle(String _seristitle) {
        this._seristitle = _seristitle;
    }

    public String get_serisintro() {
        return _serisintro;
    }

    public void set_serisintro(String _serisintro) {
        this._serisintro = _serisintro;
    }

    public int get_startcount() {
        return _startcount;
    }

    public void set_startcount(int _startcount) {
        this._startcount = _startcount;
    }


    public int get_bookid() {
        return _bookid;
    }

    public void set_bookid(int _bookid) {
        this._bookid = _bookid;
    }

    public void setDefault() {
        set_startcount(0);
    }
}
