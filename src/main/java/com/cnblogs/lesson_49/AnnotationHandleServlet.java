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

@WebServlet(value = "*.do", loadOnStartup = 0, initParams = { @WebInitParam(name = "package", value = "") })
public class AnnotationHandleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Test
	public void test() {
		String s = null;
		for (String str : s.split(",")) {
			System.out.println(str);
		}
	}

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
		 * 《3》获得注解的URI，将该URI与clazz放入map.
		 */
		for (Class<?> clazz : set) {

			Annotation clazzAnno = clazz.getDeclaredAnnotation(Controller.class);

			if (clazzAnno != null) {
				Method[] mts = clazz.getDeclaredMethods();

				for (Method mt : mts) {

					Annotation mtAnno = mt.getAnnotation(RequestMapping.class);
					if (mtAnno != null) {
						String uri = ((RequestMapping) mtAnno).value();

						if (!"".equals(uri.trim())) {
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

		Map<String, Class<?>> map = (Map<String, Class<?>>) req.getServletContext().getAttribute("requestMapping");

		String uri = parseUri(req);

		Class<?> clazz = map.get(uri);
		Object obj = BeanUtils.newInstance(clazz);

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
			Object ob =  target.invoke(obj);
			
			
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
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
