package com.cnblogs.lesson_49;

import java.lang.reflect.Method;

public class BeanUtils<T> {

	public static <T> T newInstance(Class<T> clazz) {
		if(!clazz.isInterface()){
			try {
				return clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return null;
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... classes) {

		Method mt = null;

		try {
			mt = clazz.getDeclaredMethod(methodName, classes);
			mt.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		return mt;
	}

}
