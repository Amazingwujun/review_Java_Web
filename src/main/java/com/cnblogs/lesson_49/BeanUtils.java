package com.cnblogs.lesson_49;

import java.lang.reflect.Method;

/**
 * 包装reflect的一些基本功能
 * 
 */
public class BeanUtils<T> {

	/**
	 * 获得所提供类的实例
	 * 
	 * @return
	 * 
	 */
	public static Object newInstance(Class<?> clazz) {
		if (!clazz.isInterface()) {
			try {
				return clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 获取当前类定义方法的实例，包含实现和继承的方法
	 */
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
