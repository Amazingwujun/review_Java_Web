package com.cnblogs.lesson_46;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.jndi.toolkit.ctx.AtomicContext;

import sun.misc.BASE64Encoder;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(urlPatterns = { "/*" }, initParams = {
		@WebInitParam(name = "username", value = "password", description = "用户") })
public class LoginFilter implements Filter {
	AtomicReference<Object> ar = new AtomicReference<Object>(new User());
	
	
	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
		ar.set(newValue);
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		req.setCharacterEncoding("utf8");
		
		
		HttpSession session = req.getSession();

		if (session.getAttribute("user") != null) {
			req.getRequestDispatcher("/lesson_46/userPage.jsp").forward(req, resp);
			return;
		}

		Cookie[] cookies = req.getCookies();
		User u = null;
		if ((u = userCheck(cookies)) != null) {
			session.setAttribute("user", u);
			req.getRequestDispatcher("/lesson_46/userPage.jsp").forward(req, resp);
			return;
		}

		chain.doFilter(request, response);
	}

	private User userCheck(Cookie[] cookies) {
		String username = null;
		String password = null;
		UserDao udao = new UserDaoImpl();
		User u = null;

		if (cookies == null) {
			return null;
		}

		for (Cookie c : cookies) {
			System.out.println(c.getName());

			if (c.getName().equals("userLogin")) {
				try {
					username = URLDecoder.decode(c.getValue().split("\\.")[0], "utf8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				password = c.getValue().split("\\.")[1];
			}
		}

		if (username != null && password != null) {
			u = udao.find(username);

			System.out.println(u.getUsername());
			System.out.println(u.getPassword());

			if (password.equals(MD5Encoder(u.getPassword()))) {
				return u;
			}
		}

		return null;
	}

	private String MD5Encoder(String password) {
		String result = null;

		try {
			MessageDigest md = MessageDigest.getInstance("md5");

			byte[] buf = md.digest(password.getBytes());

			BASE64Encoder b64 = new BASE64Encoder();
			result = b64.encode(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
