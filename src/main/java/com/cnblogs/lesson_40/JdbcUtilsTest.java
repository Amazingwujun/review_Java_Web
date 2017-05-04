package com.cnblogs.lesson_40;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class JdbcUtilsTest {

	@Test
	public void testUpdate() {
		String sql = "update users set name=? where id=?";
		Object[] params = {"心悦君兮",1};
		
		JdbcUtils.update(sql, params);		
	}

	@Test
	public void testQuery() {
		String sql = "select * from users";
		BeanListHandle<Users> blh = new BeanListHandle<>(Users.class);
		
		JdbcUtils.query(sql, null, blh);
		
		List<Users> list = (List<Users>) blh.getResult();
		
		for (Users user : list) {
			System.out.println(user);
		}
	}

}
