package com.cnblogs.lesson_41;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.cnblogs.lesson_39.JdbcUtils_C3P0;

public class ResultSetHandleTest {

	@Test
	public void testArrayHandle() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils_C3P0.getDS());
		String sql = "select * from users";
		Object[] results = qr.query(sql, new ArrayHandler());
		System.out.println(Arrays.asList(results));
		List<Object> list = Arrays.asList(results);
		System.out.println(list);
	}

	@Test
	public void testArrayListHandle() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils_C3P0.getDS());
		String sql = "select * from users";
		List<Object[]> list = qr.query(sql, new ArrayListHandler());

		for (Object[] objects : list) {
			System.out.println(Arrays.asList(objects));
		}

		System.out.println(list);
	}

	@Test
	public void testColumnListHandle() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils_C3P0.getDS());
		String sql = "select * from users";

		List<Object> list = qr.query(sql, new ColumnListHandler<>("name"));
		System.out.println(list);
	}

	@Test
	public void testKeyedHandle() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils_C3P0.getDS());
		String sql = "select * from users";

		Map<Integer, Map<String, Object>> map = qr.query(sql, new KeyedHandler<>("id"));
		for (Entry<Integer, Map<String, Object>> en : map.entrySet()) {
			Map<String, Object> innerMap = en.getValue();

			for (Entry<String, Object> ien : innerMap.entrySet()) {
				String columnName = ien.getKey();
				Object columnValue = ien.getValue();

				System.out.println(columnName + "=" + columnValue);
			}
			System.out.println("----------------------------");
		}
	}

	@Test
	public void testMapHandle() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils_C3P0.getDS());
		String sql = "select * from users";

		Map<String, Object> map = qr.query(sql, new MapHandler());
		for (Entry<String, Object> en : map.entrySet()) {
			String columnName = en.getKey();
			Object columnValue = en.getValue();

			System.out.println(columnName + "=" + columnValue);
		}
	}

	@Test
	public void testMapListHandle() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtils_C3P0.getDS());
		String sql = "select * from users";

		List<Map<String, Object>> map = qr.query(sql, new MapListHandler());
		for (Map<String, Object> m : map) {
			for (Entry<String, Object> en : m.entrySet()) {
				String columnName = en.getKey();
				Object columnValue = en.getValue();

				System.out.println(columnName + "=" + columnValue);
			}

			System.out.println("-----------------------");
		}
		
	}
}
