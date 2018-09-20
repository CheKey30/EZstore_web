

import java.sql.*;

class DBDao {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //服务器版本的数据库名为storeweb
    static final String DB_URL ="jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8";
    static final String USER = "root";
    static final String PASS = "142857";
    static Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Link to database....");
            connection = DriverManager.getConnection(DB_URL,USER,PASS);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("connection failed");
        }
        return connection;
    }
    static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close(); // 关闭数据库连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    }

