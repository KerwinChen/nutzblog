package net.javablog.bean;


import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

@Table("tb_config")
public class tb_config extends BasePojo {

    @Id
    private int id;

    @Column
    @ColDefine(width = 200)
    private String k;

    @Column
    @ColDefine(width = 1000)
    private String v;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }


    public tb_config() {
    }

    public tb_config(String k, String v) {
        this.v = v;
        this.k = k;
        this.setCt(new Date());
        this.setUt(new Date());
    }


}
