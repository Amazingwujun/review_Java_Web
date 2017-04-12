package com.cnblogs.lesson_twelve;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/servlet/ListCartServlet")
public class ListCartServlet extends HttpServlet {
	private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf8");
		resp.setHeader("content-type", "text/html;charset=utf8");

		HttpSession session = req.getSession();

		Set<String> list = (Set<String>) session.getAttribute("Cart");
		if (list == null) {
			list = new TreeSet<String>();
		}

		PrintWriter out = resp.getWriter();

		String id = req.getParameter("id");

		if (id != null) {
			list.add(DB.getMap().get(id).getName());
			session.setAttribute("Cart", list);
		}

		if (list.isEmpty()) {
			out.write("您尚未购买书籍");
			return;
		}

		out.write("您已经购买了如下书籍</br><hr>");
		for (String s : list) {
			out.write(s + "<br>");
		}
	}

}
