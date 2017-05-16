package com.cnblogs.lesson_43;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/servlet/GZIPtest")
public class GZIPtest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String s = "fjdlsajflkasjfklklssssssssssssssssssssssssssfjkdslajflkdsajfklajfkldsajflkajflksjaflkjakfjklajflsk";
		resp.getWriter().write(s);
	}
}
