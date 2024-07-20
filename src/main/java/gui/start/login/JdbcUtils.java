package gui.start.login;

import java.sql.*;

public class JdbcUtils {
    private  String driver="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://localhost:3306/mysql";
    private static String username="root";
    private static String password="root";



    //获取连接
    public  static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }

    //释放连接
    public static void release(Connection cnn, Statement stmt, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(cnn!=null){
            try {
                cnn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
