package com.cnblogs.lesson_27;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class WhenTag extends SimpleTagSupport {

	private boolean test;

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	@Override
	public void doTag() throws JspException, IOException {
		ChooseTag superTag = (ChooseTag) this.getParent();
		JspFragment jspFragment = this.getJspBody();
		
		if (test) {
			jspFragment.invoke(null);
			superTag.setExcuted(true);
		}

	}

}
