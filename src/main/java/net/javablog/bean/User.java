package net.javablog.bean;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;
import java.util.List;

@Table("t_user")
public class User extends BasePojo {

    @Id
    protected int id;
    @Name
    @Column
    protected String name;
    @Column("password")
    @ColDefine(width = 128)
    protected String password;
    @Column
    protected String salt;
    @Column
    private boolean locked;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

}