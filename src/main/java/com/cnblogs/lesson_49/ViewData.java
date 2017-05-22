package com.cnblogs.lesson_49;

import javax.servlet.http.HttpServletRequest;

public class ViewData {

	private HttpServletRequest request;

	public ViewData() {
		init();
	}

	private void init() {
		request = WebContext.requestHolder.get();
	}

	public void put(String arg, Object obj) {
		request.setAttribute(arg, obj);
	}

}
