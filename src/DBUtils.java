import java.sql.*;
import java.util.ResourceBundle;

/*
 * JDBC工具类，简化JDBC编程
 * */
public class DBUtils {

    /*工具类中的构造方法都是私有的，以为工具类当中的方法都是静态的
      不需要new对象，直接采用类名调用。*/

    private static ResourceBundle bundle=ResourceBundle.getBundle("jdbc");
    private static String driver = bundle.getString("driver");
    private static String url = bundle.getString("url");
    private static String user = bundle.getString("user");
    private static String password = bundle.getString("password");

    private DBUtils(){}
    //静态代码块在类加载时执行，并且只执行一次。
    static {
        try {
            //1.注册驱动
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException{
        //获取数据库连接对象，返回连接对象

        return DriverManager.getConnection(url, user, password);
//        return DriverManager.getConnection("jdbc:mysql://localhost:3306/MySql?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT"
//                ,"root","131138");

    }

    //关闭资源，conn：连接对象，ps：数据库操作对象 rs：结果集
    public static void close(Connection conn, Statement ps, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(ps!=null){
            try {
                ps.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
