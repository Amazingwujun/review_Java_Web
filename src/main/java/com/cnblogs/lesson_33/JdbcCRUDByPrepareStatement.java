package com.cnblogs.lesson_33;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;

import com.cnblogs.lesson_32.JdbcUtils;

public class JdbcCRUDByPrepareStatement {

	@Test
	public void insert() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			conn = JdbcUtils.getConn();
			sql = "insert into users(id,name,password,email,birthday) values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "6");
			pstmt.setObject(2, "山有木兮");
			pstmt.setObject(3, "Wujun1223");
			pstmt.setObject(4, "83860885@qq.com");
			pstmt.setObject(5, new java.sql.Date(new Date().getTime()));

			int num = pstmt.executeUpdate();
			if (num > 0) {
				System.out.println("数据插入成功");
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}

	}

	@Test
	public void delete() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			conn = JdbcUtils.getConn();
			sql = "delete from users where id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "6");
			int num = pstmt.executeUpdate();
			if (num > 0) {
				System.out.println("数据删除成功");
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}

	}

	@Test
	public void update() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			conn = JdbcUtils.getConn();
			sql = "update users set name=?,id=? where id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "山有木兮");
			pstmt.setString(2, "10");
			pstmt.setString(3, "4");

			int num = pstmt.executeUpdate();
			if (num > 0) {
				System.out.println("数据更新成功");
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}
	}

	@Test
	public void find() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			conn = JdbcUtils.getConn();
			sql = "select id,name from users where id>?";
			pstmt = conn.prepareStatement(sql);

			//	pstmt.setInt(1, 3);
			pstmt.setString(1, "3");
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.print("id="+rs.getObject("id"));
				System.out.println("  name=" + rs.getObject("name"));
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}

	}

}
