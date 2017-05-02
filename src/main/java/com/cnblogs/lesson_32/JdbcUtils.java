package com.cnblogs.lesson_32;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {
	private static String username;
	private static String password;
	private static String url;
	private static String driver;
	
	private volatile static Connection conn;

	static {
		InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("JDBCParameters.properties");

		Properties prop = new Properties();
		try {				
			prop.load(in);
			
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			url = prop.getProperty("url");
			driver = prop.getProperty("driver");
			
			Class.forName(driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConn(){
		if(conn==null){
			synchronized (JdbcUtils.class) {
				if(conn==null){
					try {
						return DriverManager.getConnection(url, username, password);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return conn;
	}
	
	public static void releaseRS(Connection conn,ResultSet rs,Statement stmt){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}
}
