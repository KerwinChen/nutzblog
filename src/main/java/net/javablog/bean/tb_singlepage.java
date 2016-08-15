package net.javablog.bean;

import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 单文章表
 */

//所有的字段都以下划线开头    _
@Table("tb_singlepage")
@Comment("根据_serisid字段判断，如果是>0就表示属于系列文章，否则就是单独的文章")
public class tb_singlepage extends BasePojo  implements Serializable {

    @Id
    @Comment("tb_singlepage  自增id")
    private int _id;

    @Column
    @ColDefine(width = 100)
    @Comment("标题")
    private String _title;

    @Column
    @ColDefine(width = 100)
    @Comment("在logo图上的简短的名字")
    private String _titleinlogo;


    @Column
    @ColDefine(width = 300)
    @Comment("title 英文翻译，为了放在url中")
    private String _titleen;


    @Column
    @ColDefine(width = 1000)
    @Comment("简介")
    private String _showintro;


    @Column
    @ColDefine(width = 100)
    @Comment("置顶大图")
    private String _toppic;

    @Column
    @ColDefine(type = ColType.TEXT)
    @Comment("文章正文内容 html")
    private String _content_html;


    @Column
    @ColDefine(type = ColType.TEXT)
    @Comment("文章正文内容  markdown ")
    private String _content_markdown;


    @Column
    @Comment("点赞次数")
    private int _startcount;

    @Column
    @Comment("评论关联id,32位.因为是对应多条评论,所以用多余的字段而不是自增的字段")
    private int _discusskey;

    @Column
    @Comment("是否显示")
    private boolean _showornot;


    @Column
    @ColDefine(width = 500)
    @Comment("逗号分隔")
    private String _tags;


    @Column
    @Comment("用户关联字段")
    private int _userid;


    @Default("0")
    @Column
    @ColDefine
    @Comment("系列关联字段-如果是单独文章就没有记录")
    private int _serisid;


    @Default("0")
    @Column
    @ColDefine
    @Comment("图书关联字段-如果是单独文章就没有记录")
    private int _bookid;

    @Column
    @Default("0")
    @Comment("所在seris的索引位置")
    private int _index_inseris;


    @Column
    @Comment("是否是草稿")
    private boolean _isdraft;



    private int copy_id;

    public int getCopy_id() {
        return copy_id;
    }

    public void setCopy_id(int copy_id) {
        this.copy_id = copy_id;
    }

    public String get_titleen() {
        return _titleen;
    }

    public void set_titleen(String _titleen) {
        this._titleen = _titleen;
    }

    public String get_titleinlogo() {
        return _titleinlogo;
    }

    public void set_titleinlogo(String _titleinlogo) {
        this._titleinlogo = _titleinlogo;
    }

    public int get_bookid() {
        return _bookid;
    }

    public void set_bookid(int _bookid) {
        this._bookid = _bookid;
    }

    public boolean is_isdraft() {
        return _isdraft;
    }

    public void set_isdraft(boolean _isdraft) {
        this._isdraft = _isdraft;
    }


    public int get_index_inseris() {
        return _index_inseris;
    }

    public void set_index_inseris(int _index_inseris) {
        this._index_inseris = _index_inseris;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_showintro() {
        return _showintro;
    }

    public void set_showintro(String _showintro) {
        this._showintro = _showintro;
    }

    public String get_toppic() {
        return _toppic;
    }

    public void set_toppic(String _toppic) {
        this._toppic = _toppic;
    }


    public String get_content_html() {
        return _content_html;
    }

    public void set_content_html(String _content_html) {
        this._content_html = _content_html;
    }

    public String get_content_markdown() {
        return _content_markdown;
    }

    public void set_content_markdown(String _content_markdown) {
        this._content_markdown = _content_markdown;
    }

    public int get_startcount() {
        return _startcount;
    }

    public void set_startcount(int _startcount) {
        this._startcount = _startcount;
    }

    public int get_discusskey() {
        return _discusskey;
    }

    public void set_discusskey(int _discusskey) {
        this._discusskey = _discusskey;
    }

    public boolean is_showornot() {
        return _showornot;
    }

    public void set_showornot(boolean _showornot) {
        this._showornot = _showornot;
    }

    public String get_tags() {
        return _tags;
    }

    public void set_tags(String _tags) {
        this._tags = _tags;
    }

    public int get_userid() {
        return _userid;
    }

    public void set_userid(int _userid) {
        this._userid = _userid;
    }


    public int get_serisid() {
        return _serisid;
    }

    public void set_serisid(int _serisid) {
        this._serisid = _serisid;
    }


}
