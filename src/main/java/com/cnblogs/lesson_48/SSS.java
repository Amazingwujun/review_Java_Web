package com.cnblogs.lesson_48;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/servlet/hha",initParam=@WebInitParam(name="name",value="wujun")	) 
public class SSS {
	
	/**
	 * 格式如下
	 */
	protected void init(Map<String, String> map){
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println("key: "+entry.getKey()+"---"+"value: "+entry.getValue());
		}
		
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf8");
		resp.addHeader("content-type", "text/html;charset=utf8");
		resp.getWriter().write("SSS的DOget方法被条用了");
	}
	
	private void say(HttpServletRequest req, HttpServletResponse resp){
		System.out.println("say() do");
	}
	
	public void say(){
		System.out.println("say");
	}
	
	public static void main(String[] args) throws Exception, SecurityException {
		Method mt = SSS.class.getDeclaredMethod("say", HttpServletRequest.class,HttpServletResponse.class);
		mt.setAccessible(true);
		mt.invoke(new SSS(),null,null);
		
		
		/*Method[] mts = SSS.class.getDeclaredMethods();
		
		
		for (Method mt : mts) {
			System.out.println(mt.getName());
		}*/
	}
	
}

class AAA extends SSS{
	public void nani(){};
}
