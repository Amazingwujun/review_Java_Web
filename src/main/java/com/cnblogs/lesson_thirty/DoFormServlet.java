package com.cnblogs.lesson_thirty;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/servlet/DoFormServlet")
public class DoFormServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("content-type", "text/html;charset=UTF-8");
		req.setCharacterEncoding("utf8");
		String name = req.getParameter("username");
		HttpSession session = req.getSession();

		String Token = (String) session.getAttribute("TokenNum");
		session.removeAttribute("TokenNum");

		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (Token == null || !Token.equals((String) req.getParameter("Token"))) {
			System.err.println("请勿重复提交请求");
			out.write("数据提交错误");
			return;
		}

		System.out.println(name);
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
