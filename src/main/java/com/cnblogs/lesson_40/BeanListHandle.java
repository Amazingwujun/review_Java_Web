package com.cnblogs.lesson_40;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class BeanListHandle<T> implements ResultSetHandle {
	private Class<T> clazz;
	private Object result;

	public BeanListHandle(Class<T> clazz) {
		this.clazz = clazz;
	}

	
	public Object getResult() {
		return result;
	}

	@Override
	public Object handle(ResultSet rs) {
		List<T> list = new ArrayList<>();

		try {
			while (rs.next()) {
				ResultSetMetaData rm = rs.getMetaData();
				T bean = clazz.newInstance();
				int columnCount = rm.getColumnCount();
				
				for (int i = 0; i < columnCount; i++) {
					String columnName = rm.getColumnName(i + 1);
					Object columnValue = rs.getObject(i + 1);
					
					Field f = clazz.getDeclaredField(columnName);
					f.setAccessible(true);
					f.set(bean, columnValue);
				} 
				
				list.add(bean);
			}
			
			this.result = list;
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

}
