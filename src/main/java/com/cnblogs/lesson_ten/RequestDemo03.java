package com.cnblogs.lesson_ten;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/RequestDemo03")
public class RequestDemo03 extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf8");

		PrintWriter out = resp.getWriter();
		// Enumeration<String> params = req.getParameterNames();
		Map<String, String[]> params = req.getParameterMap();

		for (Entry<String, String[]> entry : params.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			System.out.print(name + ":");

			for (int i = 0; i < values.length; i++) {
				if (i == values.length - 1) {
					System.out.print(values[i]);
					break;
				}
				System.out.print(values[i] + ",");
			}

			System.out.println();
		}
		/*
		 * while(params.hasMoreElements()){ String name = params.nextElement();
		 * String[] value = req.getParameterValues(name);
		 * 
		 * 
		 * System.out.println(MessageFormat.format("{0}:  {1}", name,value)); }
		 */

	}

}
