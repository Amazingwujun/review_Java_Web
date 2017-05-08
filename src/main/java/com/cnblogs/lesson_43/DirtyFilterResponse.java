package com.cnblogs.lesson_43;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class DirtyFilterResponse extends HttpServletResponseWrapper {
	private HttpServletResponse response;
	
	public DirtyFilterResponse(HttpServletResponse response) {
		super(response);
		this.response = response;
	}
	
	
}
