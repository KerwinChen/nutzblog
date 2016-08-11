package net.javablog.bean;


import org.nutz.dao.entity.annotation.*;

@Table("tb_user")
public class tb_user extends BasePojo {


    @Name
    @ColDefine(width = 100)
    private String _username;

    @Column
    @ColDefine(width = 100)
    private String _password;

    @Column
    @ColDefine(width = 100)
    private String _nickname;

    @Column
    @ColDefine(width = 400)
    private String _photo;


    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_nickname() {
        return _nickname;
    }

    public void set_nickname(String _nickname) {
        this._nickname = _nickname;
    }

    public String get_photo() {
        return _photo;
    }

    public void set_photo(String _photo) {
        this._photo = _photo;
    }


}
