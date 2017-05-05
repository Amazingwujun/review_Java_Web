package com.cnblogs.lesson_41;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils_thread {
	private static DataSource ds;
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

	static {
		ds = new ComboPooledDataSource();
	}

	public static Connection getConn() throws SQLException {
		Connection conn = threadLocal.get();

		if (conn == null) {
			conn = ds.getConnection();
			threadLocal.set(conn);
		}

		return conn;
	}

	public static void startTransaction() {
		try {
			Connection conn = threadLocal.get();

			if (conn == null) {
				conn = ds.getConnection();
				threadLocal.set(conn);
			}

			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public static void commit() {
		try {
			Connection conn = threadLocal.get();

			if (conn != null) {
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public static void rollback() {
		try {
			Connection conn = threadLocal.get();

			if (conn != null) {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void close() {
		try {
			Connection conn = threadLocal.get();

			if (conn != null) {
				conn.close();
				threadLocal.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
