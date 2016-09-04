package net.javablog.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcUtil {
//    private static String url = "sc.cyueuynavgb6.rds.cn-north-1.amazonaws.com.cn";
//    private static String dbname = "sc";
//    private static String dbuser = "reader";
//    private static String dbpwd = "reader";
    private static String driverName = "com.mysql.jdbc.Driver";


    static {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String url,  String dbuser, String dbpwd) throws SQLException {
        return DriverManager.getConnection(url, dbuser, dbpwd);
    }

    public static void close(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
