package com.cnblogs.lesson_45;

import java.io.Serializable;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

public class JavaBeanListener2 implements HttpSessionActivationListener, Serializable {
	private String name;

	public JavaBeanListener2(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void sessionWillPassivate(HttpSessionEvent se) {
		System.out.println(name + "和session一起被序列化到硬盘了,session id: " + se.getSession().getId());
	}

	@Override
	public void sessionDidActivate(HttpSessionEvent se) {
		System.out.println(name + "和session一起被反序列化到内纯了,session id: " + se.getSession().getId());
	}

}
