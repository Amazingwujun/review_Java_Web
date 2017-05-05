package com.cnblogs.lesson_41;

import java.sql.Connection;

public class ContextConn {
	private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	private static ContextConn instance = new ContextConn();

	private ContextConn() {
	};

	public static ContextConn getInstance() {
		return instance;
	}

	public void bind(Connection conn) {
		threadLocal.set(conn);
	}

	public Connection get() {
		return threadLocal.get();
	}

	public void remove() {
		threadLocal.remove();
	}
}
