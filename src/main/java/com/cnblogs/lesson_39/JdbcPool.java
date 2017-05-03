package com.cnblogs.lesson_39;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class JdbcPool implements DataSource {
	private static ConcurrentLinkedQueue<Connection> list = new ConcurrentLinkedQueue<>();
	private static String username;
	private static String password;
	private static String url;
	private static String driver;
	private static int initSize;

	static {
		Properties prop = new Properties();
		InputStream in = JdbcPool.class.getClassLoader().getResourceAsStream("db.properties");

		try {
			prop.load(in);

			username = prop.getProperty("username");
			password = prop.getProperty("password");
			url = prop.getProperty("url");
			driver = prop.getProperty("driver");
			initSize = Integer.parseInt(prop.getProperty("jdbcPoolInitSize"));

			Class.forName(driver);

			for (int i = 0; i < initSize; i++) {
				Connection conn = DriverManager.getConnection(url, username, password);
				list.add(conn);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (!list.isEmpty()) {
			Connection conn = list.remove();

			return (Connection) Proxy.newProxyInstance(this.getClass().getClassLoader(),
					new Class[]{Connection.class}, new InvocationHandler() {

						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if ("close".equals(method.getName())) {
								list.add(conn);
								System.out.println("连接重新回到连接池中,剩余可用连接数："+list.size());
								return null;
							}

							return method.invoke(conn, args);
						}
					});
		}else{
			throw new RuntimeException("对不起，服务器忙");
		}

	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
