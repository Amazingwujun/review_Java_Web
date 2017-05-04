package com.cnblogs.lesson_40;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class BeanHandle<T> implements ResultSetHandle {
	private Class<T> clazz;

	public BeanHandle(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public Object handle(ResultSet rs) {
		try {
			if (!rs.next()) {
				return null;
			}

			ResultSetMetaData rm = rs.getMetaData();

			T bean = clazz.newInstance();
			int columnCount = rm.getColumnCount();

			for (int i = 0; i < columnCount; i++) {
				String columnName = rm.getColumnName(i+1);
				Object columnValue = rs.getObject(i+1);
				
				Field f = clazz.getDeclaredField(columnName);
				f.setAccessible(true);
				f.set(bean, columnValue);
			}
			
			return bean;
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

}
