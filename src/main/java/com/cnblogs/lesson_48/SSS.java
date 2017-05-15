package com.cnblogs.lesson_48;

@WebServlet("/*")
public class SSS {
	
	public static void main(String[] args) {
		System.out.println(SSS.class.getAnnotation(WebInitParam.class));;
	}

	static{
		System.out.println("类SSS被初始化");
	}
}
