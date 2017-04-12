package com.cnblogs.lesson_twelve;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListenerDemo1 implements HttpSessionListener {

	public static final ConcurrentLinkedQueue<HttpSession> list = new ConcurrentLinkedQueue<HttpSession>();

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		ServletContext context = se.getSession().getServletContext();

		list.add(session);
		context.setAttribute("list", list);
		System.out.println("session被创建了 ID：" + session.getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

	}

}
