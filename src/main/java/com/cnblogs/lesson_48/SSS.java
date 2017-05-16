package com.cnblogs.lesson_48;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

@WebServlet("/servlet/SSS")
public class SSS extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Test
	public void tset() throws Exception {
		Class<SSS> clazz = SSS.class;

		Object tar = clazz.newInstance();
		
		Method mt = clazz.getDeclaredMethod("test");
		
		mt.setAccessible(false);
		
		mt.invoke(tar,null);
	}

	protected void test() {
		System.out.println("name");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("config.zip");
		System.out.println();

		while (urls.hasMoreElements()) {
			System.out.println(urls.nextElement());
		}

	}

}
