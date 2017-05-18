package com.cnblogs.lesson_43;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*", initParams = { @WebInitParam(value = "d:/dirtyWords.txt", name = "dirtyWords") })
public class DirtyFilter implements Filter {
	private FilterConfig config;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;

		HttpServletRequestWrapper requestWrapper = new DirtyFilterRequest(request);
		arg2.doFilter(requestWrapper, response);

	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private List<String> getDirtyWords() {

		List<String> list = null;

		try {
			list = new ArrayList<>();
			String path = config.getInitParameter("dirtyWords");
			System.out.println(path);
			FileReader reader = new FileReader(path);
			
			StringWriter sw = new StringWriter();

			char[] cbuf = new char[1024];
			int len = 0;
			while ((len = reader.read(cbuf)) > 0) {
				sw.write(cbuf, 0, len);
			}

			String[] words = sw.toString().split(";");
			for (String str : words) {
				System.out.println(str);
				list.add(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return list;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.config = arg0;
	}

	class DirtyFilterRequest extends HttpServletRequestWrapper {
		private HttpServletRequest request;

		public DirtyFilterRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}

		@Override
		public String getParameter(String name) {
			String value = request.getParameter(name);

			if (value == null) {
				return null;
			}

			for (String str : getDirtyWords()) {
				if (value.contains(str)) {
					System.out.println("敏感词" + str + "将被替换为****");
					value = value.replaceAll(str, "****");
				}
			}

			return value;
		}

	}
}
