package com.cnblogs.lesson_43;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpRequestParamsTransform extends HttpServletRequestWrapper {
	private HttpServletRequest request;

	public HttpRequestParamsTransform(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	public String getParameter(String name) {
		String methodName = request.getMethod();
		String value = request.getParameter(name);

		if (value == null) {
			return null;
		}

		if ("get".equalsIgnoreCase(methodName)) {
			try {
				return new String(value.getBytes("utf8"), "utf8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return value;
	}

}
