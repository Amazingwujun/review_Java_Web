package com.cnblogs.lesson_40;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {
	private static String username;
	private static String password;
	private static String url;
	private static String driver;
	
	private ThreadLocal<Connection> treadConnection = new ThreadLocal<>();

	static {
		Properties prop = new Properties();
		InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");

		try {
			prop.load(in);

			username = prop.getProperty("username");
			password = prop.getProperty("password");
			url = prop.getProperty("url");
			driver = prop.getProperty("driver");

			Class.forName(driver);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public static void releaseRS(Connection conn, ResultSet rs, Statement stmt) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void update(String sql, Object[] params) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			st = conn.prepareStatement(sql);

			for (int i = 0; params != null && i < params.length; i++) {
				st.setObject(i + 1, params[i]);
			}
			
			int num = st.executeUpdate();
			if (num>0) {
				System.out.println("数据库更新成功");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, st);
		}

	}
	
	public static Object query(String sql,Object[] params,ResultSetHandle rsh){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			st = conn.prepareStatement(sql);

			for (int i = 0; params != null && i < params.length; i++) {
				st.setObject(i + 1, params[i]);
			}
			
			rs = st.executeQuery();
			
			return rsh.handle(rs);
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			JdbcUtils.releaseRS(conn, rs, st);
		}		
	}
	
}
