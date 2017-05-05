package com.cnblogs.lesson_41;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/AcountServlet")
public class AcountServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int srcId = Integer.parseInt(req.getParameter("srcId"));
		int tarId = Integer.parseInt(req.getParameter("tarId"));
		int money = Integer.parseInt(req.getParameter("money"));
		
		try {
			new AcountService().Transfer(srcId, tarId, money);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
