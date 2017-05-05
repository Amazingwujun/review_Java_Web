package com.cnblogs.lesson_43;

import java.io.IOException;
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
		value = { "/*" }, 
		initParams = { 
				@WebInitParam(name = "charset", value = "utf8") 
		}
)
public class CharacterEncodingFilter implements Filter {
	private FilterConfig config;
	public static final String defaultCharset = "utf8";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
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
		response.setHeader("content-type", "text/html;charset=" + charset);

		HttpServletRequestWrapper requestWrapper = new HttpRequestParamsTransform(request);
		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
