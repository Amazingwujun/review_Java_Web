package com.cnblogs.lesson_27;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class OutTag extends SimpleTagSupport {

	private boolean escapeHtml;

	private String content;

	@Override
	public void doTag() throws JspException, IOException {

		if (escapeHtml) {

			content = stringFilter(content);

			this.getJspContext().getOut().write(content);
		} else {
			this.getJspContext().getOut().write(content);
		}

	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isEscapeHtml() {
		return escapeHtml;
	}

	public void setEscapeHtml(boolean escapeHtml) {
		this.escapeHtml = escapeHtml;
	}

	private String stringFilter(String content) {
		StringBuffer sbuff = new StringBuffer();

		char[] target = content.toCharArray();

		for (char c : target) {
			switch (c) {
			case '<':
				sbuff.append("&lt;");
				break;
			case '>':
				sbuff.append("&gt;");
				break;
			case '"':
				sbuff.append("&quot;");
				break;
			case '&':
				sbuff.append("&amp;");
				break;
			default:
				sbuff.append(c);
			}

		}

		return sbuff.toString();
	}
}
