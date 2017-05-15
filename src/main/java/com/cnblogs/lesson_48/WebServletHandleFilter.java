package com.cnblogs.lesson_48;

import java.io.IOException;
import java.lang.annotation.Annotation;
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

//@WebFilter(initParams = { @WebInitParam(name = "package", value = "") })
public class WebServletHandleFilter implements Filter {
	/**
	 * 用于保存URI与servlet映射的hashmap
	 * 
	 */
	private Map<String, Class<?>> map = new HashMap<>();

	@Test
	public void test() {
		String s = "com.cnblogs.lesson";
		System.out.println(s.replace(".", "/"));

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
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

		try {
			for (Class<?> clazz : list) {
				// 获取用户标注的注解
				Annotation anno = clazz.getAnnotation(WebServlet.class);
				if (anno != null) {
					// 获取servlet实例
					HttpServlet servlet = (HttpServlet) clazz.newInstance();
					// 获得servlet访问路由
					String url = ((WebServlet) anno).value();

					map.put(url, clazz);
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HashMap<String,Class<?>> map = (HashMap<String, Class<?>>) req.getServletContext().getAttribute("servletMap");
		
		String uri = req.getRequestURI();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
