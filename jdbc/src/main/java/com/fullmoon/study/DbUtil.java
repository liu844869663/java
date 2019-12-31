package com.fullmoon.study;


import java.sql.*;

public class DbUtil {
    private static final String url = "jdbc:mysql://192.250.110.158:3306/gsfy_user?useUnicode=true&characterEncoding=utf8&useSSL=false";
    private static final String user = "dev";
    private static final String password = "1qaz@WSX";
    private static Connection conn;

    private void init(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    public void query() {
        ResultSet rs = null;
        Statement sm = null;
        try {
            sm = conn.createStatement();
            String sql = "select * from user";
            rs = sm.executeQuery(sql);
            while (rs.next()) { // 每条记录对应一个对象
                // 推荐使用列名
                System.out.println("id:" + rs.getInt(1) + ",name:" + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close(rs, sm, null, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void prepareQuery() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String sql = " SELECT * FROM user WHERE id=? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,1);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("name:" + rs.getString("name") + ",age:" + rs.getString("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close(rs, null, ps, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(ResultSet rs, Statement sm, PreparedStatement ps, Connection conn) throws SQLException{
        if (rs != null) {
            rs.close();
        }
        if (sm != null) {
            sm.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public static void main(String[] args) {
        DbUtil dbUtil = new DbUtil();
        dbUtil.init();
        // dbUtil.query();
        dbUtil.prepareQuery();
    }
}
