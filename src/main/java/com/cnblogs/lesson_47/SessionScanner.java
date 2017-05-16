package com.cnblogs.lesson_47;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionScanner implements HttpSessionListener, ServletContextListener {

	// 用于存储session的list
	private volatile List<HttpSession> list = new ArrayList<>();
	// private CopyOnWriteArrayList<HttpSession> list = new
	// CopyOnWriteArrayList<>();
	// 对象锁
	private Object lock = new Object();

	@Override
	public void sessionCreated(HttpSessionEvent se) {

		HttpSession session = se.getSession();

		// 加锁
		synchronized (lock) {
			list.add(session);
		}

	}

	class MyTask extends TimerTask {

		private List<HttpSession> list;

		public MyTask(List<HttpSession> list) {
			this.list = list;
		}

		@Override
		public void run() {
			// 持有相同的锁

			if (list != null && list.size() > 0) {
				synchronized (lock) {
					/*
					 * for (HttpSession session : SessionScanner.this.list) { //
					 * 用户最后一次发送session到现在的时间 long time =
					 * System.currentTimeMillis() -
					 * session.getLastAccessedTime();
					 * 
					 * // 超过五分钟，则移除 if (time > 5 * 1000) { session.invalidate();
					 * list.remove(session); } }
					 */

					Iterator<HttpSession> itor = list.iterator();

					while (itor.hasNext()) {
						HttpSession session = itor.next();
						// 用户最后一次发送session到现在的时间
						long time = System.currentTimeMillis() - session.getLastAccessedTime();

						// 超过五分钟，则移除
						if (time > 5 * 1000) {
							session.invalidate();
							itor.remove();
						}
					}
				}
			}

		}

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
		timer.schedule(new MyTask(list), 0, 30 * 1000);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
