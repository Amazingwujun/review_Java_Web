package com.cnblogs.lesson_twelve;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/servlet/IndexServlet")
public class IndexServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf8");
		resp.setHeader("content-type", "text/html;charset=utf8");
		String url = "";
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();

		HashMap<String, Book> map = DB.getMap();

		out.write("<h5>书籍列表</h5><hr>");
		for (Entry<String, Book> en : map.entrySet()) {
			url = "http://localhost/review_Java_Web/servlet/ListCartServlet";
			url = resp.encodeURL(url)+"?id="+en.getKey();
			System.out.println(url);
			
			out.write(en.getValue().getName() + ": "
					+ "<a href='"+url
					+ "'>购买</a></br>");
		}
	}

}

class Book {
	private String id;
	private String name;

	public Book(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

class DB {
	private static final HashMap<String, Book> map = new HashMap<String, Book>();

	public static HashMap<String, Book> getMap() {
		return map;
	}

	static {
		map.put("1", new Book("1", "傲慢与偏见"));
		map.put("2", new Book("2", "成为简奥斯丁"));
		map.put("3", new Book("3", "理智与情感"));
		map.put("4", new Book("4", "简爱"));
		map.put("5", new Book("5", "雾都孤儿"));
	}
}
