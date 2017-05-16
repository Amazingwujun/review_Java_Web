package com.cnblogs.lesson_45;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class JavaBeanDemo1 implements HttpSessionBindingListener {
	private String name;
		
	public JavaBeanDemo1(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println(name+"被session: "+event.getSession().getId()+" 绑定了！");
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println(name+"被session: "+event.getSession().getId()+" 解绑了！");
	}

}
