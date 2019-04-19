import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    //使用默认配置
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    //使用连接池返回前一个连接对象

    //返回连接池对象
    public static ComboPooledDataSource getDataSource() {
        return dataSource;
    }

    // 获取数据库连接对象
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 关闭数据库连接
    public static void close(Connection conn) {
        try {
            if (conn != null && conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
