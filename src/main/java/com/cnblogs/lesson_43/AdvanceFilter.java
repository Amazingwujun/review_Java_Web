package com.cnblogs.lesson_43;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

@WebFilter(
		urlPatterns = ("/*"),
		initParams = {
				@WebInitParam(name = "charset", value = "utf8"),
				@WebInitParam(name = "dirtyWords", value = "d:/敏感词库.txt") 
		}
)
public class AdvanceFilter implements Filter {
	private FilterConfig config;
	private static final String defaultCharset = "utf8";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String charset = config.getInitParameter("charset");
		if (charset == null) {
			charset = defaultCharset;
		}

		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		response.setHeader("content-type", "text/html;charset=utf8");

		HttpServletRequestWrapper requestWrapper = new IntegrateRequestFilter(request);
		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * @Description: 使用装饰器模式强化request功能，主要为过滤敏感词和HTML标签符号的功能
	 * 
	 */
	class IntegrateRequestFilter extends HttpServletRequestWrapper {
		private HttpServletRequest request;

		public IntegrateRequestFilter(HttpServletRequest request) {
			super(request);
			this.request = request;
		}

		@Override
		public String getParameter(String name) {
			String message = request.getParameter(name);
			String methodName = request.getMethod();

			// 参数为空，直接返回
			if (message == null) {
				return null;
			}

			// 方法为GET
			if ("get".equalsIgnoreCase(methodName)) {
				//tomcat改版后，get方法传的参数用的编码也是UTF8
				//message = new String(message.getBytes("ISO8859-1"), "utf8");
				System.out.println(message);
				message = htmlFilter(message);
				message = dirtyFilter(message);
			} else {
				message = htmlFilter(message);
				message = dirtyFilter(message);
			}

			return message;
		}

		/**
		 * @className: dirtyFilter
		 * @Description: 过滤用户指定文本中的敏感词 ,默认d:/敏感词库.txt
		 * 
		 * @param: message
		 *             待过滤信息
		 * @throws IOException
		 */
		private String dirtyFilter(String message) {
			try {
				List<String> list = getDirtyList();

				for (String str : list) {
					if (message.contains(str)) {
						System.out.println("敏感词\"" + str + "\"将被替换为*");
						message = message.replaceAll(str, "*");
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			return message;
		}

		private List<String> getDirtyList() throws IOException {
			List<String> list = new ArrayList<>();
			String path = config.getInitParameter("dirtyWords");

			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);

			while (br.ready()) {
				String word = br.readLine();
				if ("".equals(word.trim())) {
					continue;
				}
				String dirtyWord = word.substring(0, word.indexOf("="));

				list.add(word.substring(0, word.indexOf("=")));
			}

			return list;
		}

		private String htmlFilter(String message) {
			char[] cBuf = message.toCharArray();
			StringBuffer sb = new StringBuffer(cBuf.length);

			for (char c : cBuf) {
				switch (c) {
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				case '"':
					sb.append("&quot;");
					break;

				default:
					sb.append(c);
					break;
				}

			}

			return sb.toString();
		}

	}

}
