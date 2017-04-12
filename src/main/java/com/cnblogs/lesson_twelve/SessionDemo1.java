package com.cnblogs.lesson_twelve;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/servlet/SessionDemo1")
public class SessionDemo1 extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static  ConcurrentHashMap<String, String> con;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-type", "text/html;charset=utf8");
		resp.setCharacterEncoding("utf8");
		PrintWriter out = resp.getWriter();
		
		
		
		HttpSession session = req.getSession();
		String sessionID = session.getId();
		
		if(session.isNew()){
			out.write("session 创建成功 ID:"+sessionID);
		}else{
			out.write("server 已经存在session ID:"+sessionID);
		}
		
		
		String url = "http://localhost/review_Java_Web/servlet/SessionDemo1";
		url = resp.encodeURL(url);
		System.out.println(url);
		out.write("<a href='"+url+"'>"+"dingwei"+"</a>");
		
		if(req.getServletContext().getAttribute("list")==null){
			return;
		}
		
		ConcurrentLinkedQueue<HttpSession> list = (ConcurrentLinkedQueue<HttpSession>) req.getServletContext().getAttribute("list");
		Iterator<HttpSession> iter = list.iterator();
		
		while(iter.hasNext()){
			System.out.println("sessionID:"+iter.next().getId());
		}
	}
}
