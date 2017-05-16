package com.cnblogs.lesson_48;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class Common_utils {

	@Test
	public void test() {
		classScanner("com.cnblogs.lesson_48");
	}

	public static Set<Class<?>> classScanner(String pack) {
		String path = pack.replace(".", "/");
		Set<Class<?>> set = new LinkedHashSet<>();

		System.out.println(Thread.currentThread().getContextClassLoader().getResource(path));

		String url = Common_utils.class.getClassLoader().getResource(path).getPath();

		File file = new File(url);

		addClassToSet(file, pack, set);

		return set;
	}

	private static void addClassToSet(File file, String pack, Set<Class<?>> set) {

		if (file == null || !file.exists() || pack == null) {
			return;
		}

		File[] fs = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory() || pathname.isFile();
			}
		});

		for (File f : fs) {

			if (f.isDirectory()) {
				pack += f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("/"));
			
				addClassToSet(f, pack.replace("/", "."), set);
			}

			if (f.getName().endsWith(".class")) {
				try {
					String className = pack + "." + f.getName().substring(0, f.getName().lastIndexOf("."));
					System.out.println(className);

					/**
					 * Class<?> clazz = Class.forName(className)
					 * 
					 * 类的加载分三段：load,link,init
					 * 
					 * 
					 */
					Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
					set.add(clazz);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}

		}

	}
}
