package com.cnblogs.lesson_48;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

//@WebFilter(value = "/*", initParams = { @WebInitParam(name = "package", value = "com.cnblogs.lesson_48") })
public class WebServletHandleFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Map<String, Class<?>> map = new HashMap<>();
		// 用户定义需要扫描的包
		String[] packs = filterConfig.getInitParameter("package").split(";");

		int len = packs.length;
		if (len > 1) {
			// 循环扫描多个包
			for (String pack : packs) {
				addServletClassToServletContext(pack, map);
			}
		} else if (len == 1) {
			addServletClassToServletContext(packs[0], map);
		}
		// 将储存路由映射的HASHMAP放到全局域
		filterConfig.getServletContext().setAttribute("servletMap", map);

		System.out.println("过滤器初始化完成");
	}

	private void addServletClassToServletContext(String pack, Map<String, Class<?>> map) {
		List<Class<?>> list = Common_utils.classScanner(pack);

		for (Class<?> clazz : list) {
			// 获取用户标注的注解
			Annotation anno = clazz.getAnnotation(WebServlet.class);
			if (anno != null) {
				// 获得servlet访问路由
				String url = ((WebServlet) anno).value();
				System.out.println("add:" + url);

				map.put(url, clazz);
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String methodName = req.getMethod();

		HashMap<String, Class<?>> map = (HashMap<String, Class<?>>) req.getServletContext().getAttribute("servletMap");

		// 获取请求的URI，并进行字符处理
		String uri = req.getRequestURI();
		String servletUri = uri.substring(uri.indexOf("/", 2));
		System.out.println(servletUri);

		if (map.containsKey(servletUri)) {

			try {
				// 获取URI映射的servlet
				Class<?> clazz = map.get(servletUri);
				Object ob = clazz.newInstance();

				if ("get".equalsIgnoreCase(methodName)) {
					Method mt = clazz.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
					
					mt.invoke(ob, request, response);

					chain.doFilter(request, response);
					return;
				} else if ("post".equalsIgnoreCase("POST")) {
					Method mt = clazz.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
					mt.invoke(ob, request, response);

					chain.doFilter(request, response);
					return;
				}

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
