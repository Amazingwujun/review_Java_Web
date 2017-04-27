package com.cnblogs.lesson_27;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ChooseTag extends SimpleTagSupport {
	
	private boolean excuted;
	
	public boolean getExcuted() {
		return excuted;
	}

	public void setExcuted(boolean excuted) {
		this.excuted = excuted;
	}

	@Override
	public void doTag() throws JspException, IOException {
		this.getJspBody().invoke(null);
	}
}
