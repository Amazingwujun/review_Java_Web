package com.cnblogs.lesson_41;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


public class TheadLocalBonding implements Filter{
	private static ThreadLocal<Object> threadLocal = new ThreadLocal<>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/*String threadName = Thread.currentThread().getName();
		String s = "wujun";
		threadLocal.set(s);
		chain.doFilter(request, response);
		System.out.println(s.hashCode()+"==="+threadName);
		threadLocal.remove();
		*/
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
}
