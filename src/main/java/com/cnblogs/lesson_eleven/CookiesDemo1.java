package com.cnblogs.lesson_eleven;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/CookiesDemo1")
public class CookiesDemo1 extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("gbk");
		
		PrintWriter out = response.getWriter();
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies==null){			
			out.write("你是第一次登录本站！");
		}else{
			for(Cookie c:cookies){
				if("firstLogin".equals(c.getName())){
					long time = Long.parseLong(c.getValue());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					out.write("<h1>"+
					"上一次登录的时间为"+
					sdf.format(new Date(time))+
					"</h1>");
				}
			}
		}
		
		

		Cookie cookie = new Cookie("firstLogin", System.currentTimeMillis()+"");
		cookie.setMaxAge(60*60*24*7);
		response.addCookie(cookie);
		
	}
	
	
}
