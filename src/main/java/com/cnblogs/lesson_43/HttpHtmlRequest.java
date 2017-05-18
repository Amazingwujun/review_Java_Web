package com.cnblogs.lesson_43;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpHtmlRequest extends HttpServletRequestWrapper {
	private HttpServletRequest request;
	
	public HttpHtmlRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	
	@Override
	public String getParameter(String name) {
		String htmlMsg = request.getParameter(name);
		
		if(htmlMsg==null){
			return null;
		}
		
		return filter(htmlMsg);
	}

	private String filter(String htmlMsg) {
		char[] buff = htmlMsg.toCharArray();
		StringBuffer str = new StringBuffer();
		
		for(char c:buff){
			switch (c) {
			case '<':
				str.append("&lt;");
				break;
			case '>':
				str.append("&gt;");
				break;
			case '&':
				str.append("&amp;");
				break;
			case '"':
				str.append("&quot;");
				break;
			default:
				str.append(c);
				break;
			}
		}
		
		return str.toString();
		
	}
	

	
}
