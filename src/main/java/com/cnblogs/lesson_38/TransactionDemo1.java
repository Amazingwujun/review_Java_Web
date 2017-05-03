package com.cnblogs.lesson_38;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.junit.Test;

import com.cnblogs.lesson_32.JdbcUtils;

public class TransactionDemo1 {

	@Test
	public void TestTransaction1() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtils.getConn();
			// 开启事务
			conn.setAutoCommit(false);

			String sql1 = "update acount set money=money-100 where name='a'";
			pstmt = conn.prepareStatement(sql1);
			pstmt.executeUpdate();

			String sql2 = "update acount set money=money+100 where name='b'";
			pstmt = conn.prepareStatement(sql2);
			pstmt.executeUpdate();

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.getMessage();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}

	}

	@Test
	public void TestTransaction2() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtils.getConn();
			// 开启事务
			conn.setAutoCommit(false);

			String sql1 = "update acount set money=money-100 where name='a'";
			pstmt = conn.prepareStatement(sql1);
			pstmt.executeUpdate();

			int i = 1 / 0;
			String sql2 = "update acount set money=money+100 where name='b'";
			pstmt = conn.prepareStatement(sql2);
			pstmt.executeUpdate();

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				conn.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}

	}
	
	@Test
	public void TestTransaction3() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Savepoint sp = null;
		
		try {
			conn = JdbcUtils.getConn();
			conn.setAutoCommit(false);// 开启事务

			String sql1 = "update acount set money=money-100 where name='a'";
			pstmt = conn.prepareStatement(sql1);
			pstmt.executeUpdate();
			
			sp = conn.setSavepoint();
			
			String sql2 = "update acount set money=money+100 where name='b'";
			pstmt = conn.prepareStatement(sql2);
			pstmt.executeUpdate();
			
			int i = 1 / 0;//模拟异常

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback(sp);
				conn.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}

	}

}
