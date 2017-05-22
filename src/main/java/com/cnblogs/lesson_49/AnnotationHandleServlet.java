package com.cnblogs.lesson_49;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.cnblogs.lesson_48.Common_utils;

@WebServlet(value = "*.do", // 拦截后缀为.do的请求
		loadOnStartup = 0, // 应用启动即加载
		initParams = {
				// controller所在包
				@WebInitParam(name = "package", value = "com.cnblogs.lesson_49") })
public class AnnotationHandleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 初始化时，将给定包下的符合条件的类加载并添加到 Map<String,Class<?>>
	 * 
	 */
	public void init() throws ServletException {

		Map<String, Class<?>> map = new HashMap<>();
		ServletContext servletContext = getServletContext();
		ServletConfig servletConfig = getServletConfig();

		String packs[] = servletConfig.getInitParameter("package").split(";");

		// 包为空则跳过
		for (int i = 0; packs != null && i < packs.length; i++) {
			addClass2Map(packs[i], map);
		}

		servletContext.setAttribute("requestMapping", map);
		System.out.println("---注解处理器初始化完成---");
	}

	private void addClass2Map(String pack, Map<String, Class<?>> map) {
		// 获得指定包下所有的类
		Set<Class<?>> set = Common_utils.classScanner(pack);

		/**
		 * 《1》遍历查询被@Controller注解过的类， 《2》获得该类被@RequestMapping注解过的方法，
		 * 《3》获得注解的URI，将该URI与clazz放入map。
		 */
		for (Class<?> clazz : set) {

			Annotation clazzAnno = clazz.getDeclaredAnnotation(Controller.class);

			if (clazzAnno != null) {
				Method[] mts = clazz.getDeclaredMethods();

				for (Method mt : mts) {

					Annotation mtAnno = mt.getAnnotation(RequestMapping.class);
					if (mtAnno != null) {
						String uri = ((RequestMapping) mtAnno).value();

						if (map.containsKey(uri)) {
							throw new RuntimeException("uri:\"" + uri + "\",映射地址uri必须唯一");
						}

						if (!"".equals(uri.trim())) {
							System.out.println("key:" + uri + "   value: " + clazz.getName());

							map.put(uri, clazz);
						}

					}

				}
			}

		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		excute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		excute(req, resp);
	}

	@SuppressWarnings("unchecked")
	private void excute(HttpServletRequest req, HttpServletResponse resp) {
		// 将请求和响应对象存到对应线程
		WebContext.requestHolder.set(req);
		WebContext.responseHolder.set(resp);

		// 获取方法URL与对应类的映射,如果Map为空，do nothing
		Map<String, Class<?>> map = (Map<String, Class<?>>) req.getServletContext().getAttribute("requestMapping");
		if (map == null)
			return;

		// 用户请求URL
		String uri = parseUri(req);
		// 反射获取请求方法的类对象
		Class<?> clazz = map.get(uri);
		Object obj = BeanUtils.newInstance(clazz);
		// 遍历类方法，
		Method[] mts = clazz.getDeclaredMethods();
		Method target = null;

		for (Method mt : mts) {
			RequestMapping anno = mt.getAnnotation(RequestMapping.class);

			String value = anno.value();
			if (uri.equals(value)) {
				target = mt;
			}
		}

		try {

			if (target == null) {
				return;
			}

			/*
			 * View view = new View("/lesson_50/fileUpload.jsp");
			 * view.setDispatcherAction(View.DISPATCHER_FORWARD);
			 */
			// invoke this method
			Object ob = target.invoke(obj);

			if (ob != null) {

				View v = (View) ob;
				if (v.getDispatcherAction().equals(View.DISPATCHER_FORWARD)) {
					req.getRequestDispatcher(v.getUri()).forward(req, resp);
				} else if (v.getDispatcherAction().equals(View.DISPATCHER_REDIRECT)) {
					resp.sendRedirect(req.getContextPath() + v.getUri());
				}
			}

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ServletException
				| IOException e) {
			e.printStackTrace();

		}

	}

	private String parseUri(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String midUri = uri.replace(contextPath, "");
		String lastUri = midUri.substring(0, midUri.lastIndexOf("."));
		System.out.println("解析后的URI:" + lastUri);

		return lastUri;
	}

}
