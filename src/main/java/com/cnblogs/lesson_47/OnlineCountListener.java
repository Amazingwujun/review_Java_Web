package com.cnblogs.lesson_47;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class OnlineCountListener implements HttpSessionListener {
	private static final AtomicLong count = new AtomicLong(0);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		if (count.get() == 0) {
			se.getSession().getServletContext().setAttribute("OnlineCount", count);
		}
		System.out.println("当前用户数量为：" + count.incrementAndGet());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("用户减少了：" + count.decrementAndGet());
	}

}
