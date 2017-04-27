package com.cnblogs.lesson_27;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HTMLTranslateTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		StringWriter sWriter = new StringWriter();

		this.getJspBody().invoke(sWriter);

		String content = sWriter.getBuffer().toString();

		content = stringFilter(content);

		this.getJspContext().getOut().write(content);
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
