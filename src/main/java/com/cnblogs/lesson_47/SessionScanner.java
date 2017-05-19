package com.cnblogs.lesson_47;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//@WebListener
public class SessionScanner implements HttpSessionListener, ServletContextListener {

	// 用于存储session的list
	// private volatile List<HttpSession> list = new ArrayList<>();
	private ConcurrentHashMap<String, HttpSession> map = new ConcurrentHashMap<>();
	private AtomicLong count = new AtomicLong(1);
	// private CopyOnWriteArrayList<HttpSession> list = new
	// CopyOnWriteArrayList<>();
	// 对象锁

	@Override
	public void sessionCreated(HttpSessionEvent se) {

		HttpSession session = se.getSession();
		String sessionID = session.getId();
		// 加锁
		map.putIfAbsent(sessionID, session);
		System.out.println("sessionID:" + sessionID);
	}

	class MyTask extends TimerTask {

		private ConcurrentHashMap<String, HttpSession> map;

		public MyTask(Map<String, HttpSession> map) {
			this.map = (ConcurrentHashMap<String, HttpSession>) map;
		}

		@Override
		public void run() {
			System.out.println("定时器执行了：" + Thread.currentThread().getName());
			System.out.println("map是否为空："+map.isEmpty());
			
			for (Entry<String, HttpSession> en : map.entrySet()) {
				HttpSession session = en.getValue();

				System.out.println("--------------------");
				System.out.println("等待的Session: " + en.getKey());
				System.out.println("--------------------");

				if (System.currentTimeMillis() - session.getLastAccessedTime() > 5 * 1000) {
					session.invalidate();
					map.remove(en.getKey());
					System.out.println("sessionID:" + session.getId() + "被销毁");
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
		System.out.println("初始化");
		Timer timer = new Timer();
		timer.schedule(new MyTask(map), 0, 5 * 1000);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
