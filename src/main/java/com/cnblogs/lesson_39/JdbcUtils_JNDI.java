package com.cnblogs.lesson_39;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcUtils_JNDI {
	private static DataSource ds;
	
	static{
		
		try {		
			Context initCtx = new InitialContext();
			Context evnCtx = (Context) initCtx.lookup("java:comp/env");
			
			ds = (DataSource) evnCtx.lookup("jdbc/datasource");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static Connection getConn() throws SQLException{
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
