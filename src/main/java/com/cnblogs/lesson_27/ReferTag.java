package com.cnblogs.lesson_27;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ReferTag extends SimpleTagSupport {

	private String site;

	private String page;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public void doTag() throws JspException, IOException {
		//
		PageContext pageContext = (PageContext) this.getJspContext();
		//
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

		String refer = request.getHeader("referer");
		
		if (refer == null || !refer.startsWith(site)) {
			String webRoot = request.getContextPath();

			if (page.startsWith(webRoot)) {
				response.sendRedirect(page);
			} else {
				response.sendRedirect(webRoot + page);
			}

			throw new SkipPageException();
		}

	}

}
