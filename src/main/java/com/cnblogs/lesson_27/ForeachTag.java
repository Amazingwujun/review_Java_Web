package com.cnblogs.lesson_27;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ForeachTag<T> extends SimpleTagSupport {

	private Object target;

	private String var;

	private Collection collection;

	@Override
	public void doTag() throws JspException, IOException {
		JspFragment fragment = this.getJspBody();
		PageContext context = (PageContext) this.getJspContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();

		Iterator<String> it = collection.iterator();
		while (it.hasNext()) {
			Object tar = it.next();
			request.setAttribute(var, tar);
			fragment.invoke(null);
		}
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		if (target instanceof Collection) {
			collection = (Collection) target;
		} else if (target instanceof Map) {
			collection = ((Map) target).entrySet();
		} else if (target.getClass().isArray()) {
			int len = Array.getLength(target);

			collection = new ArrayList<String>(len);

			for (int i = 0; i < len; i++) {
				Object ob =  Array.get(target, i);
				collection.add(ob);
			}
		}

		this.target = target;
	}

	public Object getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

}
