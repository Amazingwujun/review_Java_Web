package com.cnblogs.lesson_48;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Common_utils {

	@Test
	public void test() {
		classScanner("com.cnblogs.lesson_48");
	}

	public static List<Class<?>> classScanner(String pack) {
		String path = pack.replace(".", "/");
		List<Class<?>> list = new ArrayList<>();

		System.out.println(Thread.currentThread().getContextClassLoader().getResource(path));
		
		
		String url = Common_utils.class.getClassLoader().getResource(path).getPath();

		File file = new File(url);

		File[] fs = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".class");
			}
		});

		for (File f : fs) {
			try {
				String className = pack+"."+f.getName().substring(0, f.getName().lastIndexOf("."));
				System.out.println(className);
				
				Class<?> clazz = Class.forName(className, false, Thread.currentThread().getContextClassLoader());
				list.add(clazz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
}
