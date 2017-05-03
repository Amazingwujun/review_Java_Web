package com.cnblogs.lesson_39;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils_C3P0 {

	private static ComboPooledDataSource ds = null;

	static {
		ds = new ComboPooledDataSource("mysql");
	}

	public static Connection getConn() throws SQLException {
		return ds.getConnection();
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
	
}
