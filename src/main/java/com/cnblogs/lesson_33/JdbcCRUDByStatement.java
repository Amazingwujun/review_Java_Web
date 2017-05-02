package com.cnblogs.lesson_33;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.cnblogs.lesson_32.JdbcUtils;

public class JdbcCRUDByStatement {

	@Test
	public void insert() {
		Connection conn = JdbcUtils.getConn();
		
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			String sql = "insert into users(id,name,password,email,birthday) values('5','伍俊','416471','85998282@qq.com','1989-12-23')";

			int num = stmt.executeUpdate(sql);

			if (num > 0) {
				System.out.println("插入数据成功");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, null, stmt);
		}

	}

	@Test
	public void delete() {
		Connection conn = JdbcUtils.getConn();
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			String sql = "delete from users where id='5'";

			int num = stmt.executeUpdate(sql);
			if (num > 0) {
				System.out.println("删除数据成功");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, null, stmt);
		}
	}

	@Test
	public void update() {
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;

		try {
			conn = JdbcUtils.getConn();
			stmt = conn.createStatement();
			String sql = "update users set name='心悦君兮' , email='83860885@qq.com' where id='4'";

			int num = stmt.executeUpdate(sql);
			if (num > 0) {
				System.out.println("数据更新成功");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, stmt);
		}

	}

	@Test
	public void find() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtils.getConn();
			stmt = conn.createStatement();

			String sql = "select * from users where id=4";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println("id=" + rs.getObject("id"));
				System.out.println("name=" + rs.getObject("name"));
				System.out.println("password=" + rs.getObject("password"));
				System.out.println("email=" + rs.getObject("email"));
				System.out.println("birthday=" + rs.getObject("birthday"));
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, stmt);
		}

	}

}
