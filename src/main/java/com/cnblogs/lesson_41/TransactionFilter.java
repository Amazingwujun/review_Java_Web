package com.cnblogs.lesson_41;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cnblogs.lesson_39.JdbcUtils_C3P0;

@WebFilter({ "/*" })
public class TransactionFilter implements Filter {
	private static AtomicInteger counter = new AtomicInteger(0);
	private static ConcurrentHashMap<String, Thread> threadMap = new ConcurrentHashMap<>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Connection conn = null;

		if (!threadMap.contains(Thread.currentThread())) {
			threadMap.put(Thread.currentThread().getName(), Thread.currentThread());
		} else {
			System.err.println("当前线程重复，当前线程重复，当前线程重复");
		}

		System.out.println("TransactionFilter执行了: " + counter.incrementAndGet() + "次！~");
		System.out.println("当前的线程名：" + Thread.currentThread().getName());
		try {
			// 获取连接
			conn = JdbcUtils_C3P0.getConn();
			//conn = JdbcUtils.getConn();
			if (conn == null) {
				System.out.println("未获取到连接");
			}
			// 绑定至当前线程
			ContextConn.getInstance().bind(conn);
			// 开启事务
			System.out.println("连接是否为空：" + (conn == null));
			System.out.println(conn);
			conn.setAutoCommit(false);

			chain.doFilter(request, response);

			// 提交事务
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(req.getContextPath() + "/error.jsp");

			throw new RuntimeException(e);
		} finally {

			ContextConn.getInstance().remove();

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("连接解绑");
			System.out.println("----------------------------------");
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}