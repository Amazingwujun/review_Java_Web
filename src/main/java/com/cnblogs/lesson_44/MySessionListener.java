package com.cnblogs.lesson_44;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

//@WebListener
public class MySessionListener implements ServletRequestListener{

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		HttpServletRequest request =  (HttpServletRequest) sre.getServletRequest();
		System.out.println(request+"请求销毁");
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		HttpServletRequest request =  (HttpServletRequest) sre.getServletRequest();
		System.out.println(request+"请求开始");
	}

}
