package com.cnblogs.lesson_48;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*@WebFilter(
		value = "/*",
		initParams = {
				@javax.servlet.annotation.WebInitParam(name = "package", value = "com.cnblogs.lesson_48") 
				})*/
public class WebServletHandleFilter implements Filter {
	/**
	 * 用于保存URI与servlet映射的hashmap
	 * 
	 */
	private Map<String, Class<?>> map = new HashMap<>();

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
		Set<Class<?>> set = Common_utils.classScanner(pack);

		for (Class<?> clazz : set) {
			// 获取用户标注的注解
			Annotation anno = clazz.getAnnotation(WebServlet.class);
			if (anno != null) {
				// 获得servlet访问路由
				String url = ((WebServlet) anno).value();

				map.put(url, clazz);
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("--进入注解过滤器--");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String reqMethodName = req.getMethod();

		HashMap<String, Class<?>> map = (HashMap<String, Class<?>>) req.getServletContext().getAttribute("servletMap");

		// 截取请求字符
		String reqUri = req.getRequestURI();

		if (reqUri.indexOf("!") != -1) { // 指定访问方法

			/**
			 * 字符串截取 : "abc!method?name=java" -> method
			 */
			String methodName = reqUri.substring(reqUri.indexOf("!") + 1,
					reqUri.indexOf("?") == -1 ? reqUri.length() : reqUri.indexOf("?"));
			System.out.println(methodName);
			//获取用户访问的URI
			String uri = reqUri.substring(reqUri.indexOf("/", 2), reqUri.indexOf("!"));

			Class<?> clazz = map.get(uri);
			Object obj = null;
			Method tarMt = null;
			try {
				obj = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				tarMt= clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			} catch (NoSuchMethodException | SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// 获取clazz所声明的所有方法
			Method[] mts = clazz.getDeclaredMethods();
			// 获得clazz的注解
			Annotation anno = clazz.getDeclaredAnnotation(WebServlet.class);
			// 获取初始化参数数组,并将键值对存储到Map
			com.cnblogs.lesson_48.WebInitParam[] initParams = ((WebServlet) anno).initParam();
			Map<String, String> initMap = new HashMap<>();

			if (initParams != null && initParams.length > 0) {
				for (com.cnblogs.lesson_48.WebInitParam param : initParams) {
					String value = param.value();
					String name = param.name();
					initMap.put(name, value);
				}
			}

			// 遍历方法，以便查询该类在调用前是否需要进行初始化
			for (Method mt : mts) {
				System.out.println("所有方法的名称:  "+mt.getName());

				if ("init".equals(mt.getName())) {
					try {
						mt.invoke(obj, initMap);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			
			try {
				tarMt.invoke(obj, req, resp);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else { // 如果用户没有指定方法，则根据请求方法调用doGet或是doPost
			String uri = reqUri.substring(reqUri.indexOf("/", 2));
			
			Class<?> clazz = map.get(uri);
			Object obj = null;
			try {
				obj = clazz.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			Method mt = null;
			if ("get".equalsIgnoreCase(reqMethodName)) {
				try {
					mt = clazz.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
				} catch (NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if ("post".equalsIgnoreCase(reqMethodName)) {
				try {
					mt = clazz.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
				} catch (NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				mt.invoke(obj, req, resp);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
