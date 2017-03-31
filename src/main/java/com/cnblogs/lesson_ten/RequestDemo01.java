package com.cnblogs.lesson_ten;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/RequestDemo01")
public class RequestDemo01 extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6631436812008184323L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String requestURI = req.getRequestURI();//获取资源标识
		String requestURL = req.getRequestURL().toString();//获取资源定位
		String host = req.getRemoteHost();//获取远程主机名
		String queryString = req.getQueryString();//获取查询参数
		String remoteAddr = req.getRemoteAddr();//获取请求地址
		int port = req.getRemotePort();//获取远程端口
		String localName = req.getLocalName();
		String localAddr = req.getLocalAddr();
		String method = req.getMethod();
		String pathInfo = req.getPathInfo();
		String remoteUser = req.getRemoteUser();
		
		resp.setHeader("content-type", "text/html;charset=utf8");
		PrintWriter out =  resp.getWriter();
		out.write("客户机的信息如下");
		out.write("<hr/>");
		out.write("用户请求的URI："+requestURI);
		out.write("<br/>");
		out.write("用户请求的URL："+requestURL);
		out.write("<br/>");
		out.write("用户请求的主机："+host);
		out.write("<br/>");
		out.write("查询参数："+URLDecoder.decode(queryString, "utf8"));
		out.write("<br/>");
		out.write("用户的地址："+remoteAddr);
		out.write("<br/>");
		out.write("用户请求的端口："+port);
		out.write("<br/>");
		out.write("服务器名："+localName);
		out.write("<br/>");
		out.write("服务器地址："+localAddr);
		out.write("<br/>");
		out.write("请求的方法："+method);
		out.write("<br/>");
		out.write("地址信息："+pathInfo);		
		out.write("<br/>");
		out.write("远程用户："+remoteUser);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
