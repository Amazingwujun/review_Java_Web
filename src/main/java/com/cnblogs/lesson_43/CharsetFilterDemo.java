package com.cnblogs.lesson_43;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/CharsetFilterDemo")
public class CharsetFilterDemo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String methodName = req.getMethod();
		String param = req.getParameter("username");

		PrintWriter out = resp.getWriter();
		out.write("请求的方式：" + methodName + "<br/>");
		out.write("请求的参数：" + param);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String methodName = req.getMethod();
		String param = req.getParameter("username");

		PrintWriter out = resp.getWriter();
		out.write("请求的方式：" + methodName + "<br/>");
		out.write("请求的参数：" + param);
	}
}
