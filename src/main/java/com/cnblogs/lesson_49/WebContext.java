package com.cnblogs.lesson_49;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

/**
 * 将当前用户的request和response保存到当前线程ThreadLocal,
 * 方便使用。
 * 
 */
public class WebContext {
	public static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
	public static ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<>();

	public HttpServletRequest getRequest() {
		return requestHolder.get();
	}

	public HttpServletResponse getResponse() {
		return responseHolder.get();
	}

	public ServletContext getServletContext() {
		return requestHolder.get().getServletContext();
	}

	public HttpSession getSession() {
		return requestHolder.get().getSession();
	}

	public void removeRequest() {
		requestHolder.remove();
	}

	public void removeResponse() {
		responseHolder.remove();
	}

	@Test
	public void test() {
		System.out.println(new WebContext().getRequest());
	}
}
