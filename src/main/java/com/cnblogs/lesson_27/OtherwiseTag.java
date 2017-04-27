package com.cnblogs.lesson_27;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class OtherwiseTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		ChooseTag superTag = (ChooseTag) this.getParent();
		JspFragment jspFragment = this.getJspBody();
		
		boolean excute = superTag.getExcuted();

		if (excute == false) {
			jspFragment.invoke(null);
			superTag.setExcuted(true);
		}
	}
}
