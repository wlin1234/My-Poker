import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
	static Connection conn = null;
	
	private DBConnection() {
		
	}
	
	public static Connection getDBConnection() {
		try {
			if(conn == null) {
				//Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokeraccts?verifyServerCertificate=false&useSSL=true", "root", "root");		
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	public static void closeConnection() {
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
