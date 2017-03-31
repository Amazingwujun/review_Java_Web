package com.cnblogs.lesson.nine;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/check")
public class ServletForCheck extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf8");
		String ck = req.getParameter("checknum");
		System.out.println("伍俊".equals(ck));
		String checknum = (String) req.getSession().getAttribute("checkNum");
		
		if(!checknum.equals(ck)){
			System.out.println("验证码输入错误，请重新输入");
			/*req.setAttribute("message", "验证码输入错误，请重新输入");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);*/
		}else{
			System.out.println("验证码成功");
			/*req.setAttribute("message", "验证码成功");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);*/
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	
}
