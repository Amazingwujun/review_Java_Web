package com.cnblogs.lesson_ten;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/RequestDemo02")
public class RequestDemo02 extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("content-type", "text/html;charset=utf8");
		PrintWriter out = resp.getWriter();
		
		Enumeration<String> headers =  req.getHeaderNames();
		
		while(headers.hasMoreElements()){
			String headerName = headers.nextElement();
			String headerValue = req.getHeader(headerName);
			out.write(headerName+":  "+headerValue);
			out.write("<br/>");
		}
		
		
	}
	
	
}
