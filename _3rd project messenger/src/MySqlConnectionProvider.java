import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MySqlConnectionProvider {
	private static Properties properties = new Properties();
	
	static {
		System.out.println("프로퍼티 설정 파일을 읽습니다.");
		ClassLoader classLoader = MySqlConnectionProvider.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("3rdSql.properties");
		try (BufferedInputStream br = new BufferedInputStream(inputStream)) {
			properties.load(br);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("MySql JDBC 드라이버를 적재합니다.");
		try {
			Class.forName(properties.getProperty("DRIVER"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(properties.getProperty("URL")
				, properties.getProperty("ID")
				, properties.getProperty("PASSWORD"));
	}
	
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}




