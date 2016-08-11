package net.javablog.util;


import org.nutz.mvc.Mvcs;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 返回数据
 */
public class Rs {

    public enum Type {
        success, warn, error;
    }

    private Type status;
    private String desc;
    private Map datas; //  list接口返回数据使用

    public Rs() {
    }

    public Rs(Rs.Type status, String desc, Map datas) {
        this.status = status;
        this.desc = desc;
        this.datas = datas;
    }


    public static Rs succcess(String desc, Map datas) {
        Rs rs = new Rs();
        rs.status = Type.success;
        rs.desc = desc;
        rs.datas = datas;
        return rs;
    }


    public static Rs succcess() {
        return succcess("操作成功");
    }

    public static Rs succcess(String desc) {
        Rs rs = new Rs();
        rs.status = Type.success;
        rs.desc = desc;
        return rs;
    }


    public static Rs warn(String desc, Map datas) {
        Rs rs = new Rs();
        rs.status = Type.warn;
        rs.desc = desc;
        rs.datas = datas;
        return rs;
    }

    public static Rs warn(String desc) {
        Rs rs = new Rs();
        rs.status = Type.warn;
        rs.desc = desc;
        return rs;
    }


    public static Rs error() {
        Rs rs = new Rs();
        rs.status = Type.error;
        rs.desc = "操作失败";
        return rs;
    }

    public static Rs error(String desc) {
        Rs rs = new Rs();
        rs.status = Type.error;
        rs.desc = desc;
        return rs;
    }


    public static Rs error(String desc, Map datas) {
        Rs rs = new Rs();
        rs.status = Type.error;
        rs.desc = desc;
        rs.datas = datas;
        return rs;
    }


}
