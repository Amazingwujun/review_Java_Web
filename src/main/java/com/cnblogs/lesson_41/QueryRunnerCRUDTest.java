package com.cnblogs.lesson_41;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import com.cnblogs.lesson_39.JdbcUtils_C3P0;
import com.cnblogs.lesson_40.Users;

public class QueryRunnerCRUDTest {

	@Test
	public void add() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils_C3P0.getDS());
		String sql = "insert into users(id,name,password,email,birthday) values(?,?,?,?,?)";
		Object[] params = { 6, "老衲还年轻", "416471", "85998282@qq.com", "1989-12-23" };
		qr.update(sql, params);
	}

	@Test
	public void delete() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils_C3P0.getDS());
		String sql = "delete from users where id=?";
		Object[] params = { 6 };
		qr.update(sql, params);
	}

	@Test
	public void update() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils_C3P0.getDS());
		String sql = "update acount set money=money+? where name=?";
		Object[] params = { 666, "a" };
		qr.update(sql, params);
	}

	@Test
	public void find() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils_C3P0.getDS());
		String sql = "select * from users where id=10";
		// BeanListHandler<Users> rsh = new BeanListHandler<>(Users.class);
		BeanHandler<Users> rsh = new BeanHandler<>(Users.class);
		Users user = qr.query(sql, rsh);
		
		System.out.println(user);
		/*for (Users u : list) {
			System.out.println(u);
		}*/
	}
}
