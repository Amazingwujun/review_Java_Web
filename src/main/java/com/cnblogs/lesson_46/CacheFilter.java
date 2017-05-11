package com.cnblogs.lesson_46;

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
import javax.servlet.http.HttpServletResponse;

/*@WebFilter(filterName = "CacheFilter", description = "缓存常用资源，避免频繁请求增加服务器的负担",

		urlPatterns = { "*.jpg", "*.css", "*.png", "*.js" }, initParams = { @WebInitParam(name = "jpg", value = "1"),
				@WebInitParam(name = "css", value = "2"), @WebInitParam(name = "png", value = "3"),
				@WebInitParam(name = "js", value = "4"), })*/
public class CacheFilter implements Filter {
	private FilterConfig cfg;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.cfg = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String reqUri = req.getRequestURI();

		String ext = reqUri.substring(reqUri.indexOf(".") + 1);

		Integer time = Integer.parseInt(cfg.getInitParameter(ext));

		if (time > 0) {
			long t = time * 3600 * 1000;
			resp.setDateHeader("expires", System.currentTimeMillis() + t);
		}

		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
