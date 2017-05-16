package com.cnblogs.lesson_48;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedHashSet;
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
		
		String url = Common_utils.class.getClassLoader().getResource(path).getPath();

		File file = new File(url);

		addClassToSet(file, pack, set);

		return set;
	}
	
	/**
	 * 递归添加提供包下所有的class文件
	 * 
	 * @param: file 待添加的目录
	 * @param: pack class文件对应的包名
	 * @param: set 存储结果的文件
	 * 
	 */
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
				
				String dirPack = pack+f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\"));
					
				addClassToSet(f, dirPack.replace("\\", "."), set);
			}
			
			if (f.getName().endsWith(".class")) {
				try {
					String className = pack + "." + f.getName().substring(0, f.getName().lastIndexOf("."));
					System.out.println("即将要加载类的类限定名："+className);

					/**
					 * Class<?> clazz = Class.forName(className)
					 * 这种加载方式会初始化类，执行被加载类的静态部分。 
					 * 而loadClass(className)不会初始化
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
