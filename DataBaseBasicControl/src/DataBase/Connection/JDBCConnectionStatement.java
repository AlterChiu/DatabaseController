package DataBase.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.mysql.jdbc.Driver;

public class JDBCConnectionStatement {
	private String url;
	private String user;
	private String pass;
	private String driver;
	public static String mySqlDriver = "com.mysql.jdbc.Driver";
	public static String msServerDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	
	public JDBCConnectionStatement(String url, String user, String pass, String driver) {
		this.url = url;
		this.user = user;
		this.pass = pass;
		this.driver = driver;
	}

	public JDBCConnectionStatement() {
	}

	public void setIP(String url) {
		this.url = url;
	}
	public void setPass(String pass){
		this.pass = pass;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public Connection getConnection() throws ClassNotFoundException {
		Connection con = null;
		Class.forName(driver);
		try {
			con = DriverManager.getConnection(this.url, this.user, this.pass);
			System.out.println("Connection success when " + url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Connection error when " + url);
			e.printStackTrace();
		}
		return con;
	}

}
