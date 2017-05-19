package com.cnblogs.lesson_46;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

//@WebServlet("/servlet/loginServlet_46")
public class LoginServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf8");
		resp.setCharacterEncoding("utf8");
		
		UserDao udao = new UserDaoImpl();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		System.out.println(username+"---"+password);
		
		if (username == null || password == null || "".equals(username.trim()) || "".equals(password.trim())) {
			resp.sendRedirect(req.getContextPath()+"/lesson_46/index.jsp");
			return;
		}

		User u = new User();
		u.setPassword(password);
		u.setUsername(username);

		System.out.println(u);
		if (udao.find(u) != null) {
			Cookie cookie = new Cookie("userLogin",
					URLEncoder.encode(u.getUsername(), "utf8") + "." + MD5Encoder(u.getPassword()));
			cookie.setPath(req.getContextPath());
			resp.addCookie(cookie);

			System.out.println(u);
			req.getSession().setAttribute("user", u);
			req.getRequestDispatcher("/lesson_46/userPage.jsp").forward(req, resp);
			return;
		}

		resp.sendRedirect(req.getContextPath() + "/lesson_46/index.jsp");
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

}
