package com.cnblogs.lesson_50;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

@WebServlet("/servlet/DownloadFileList")
public class DownloadFileList extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("content-type", "text/html;charset=utf8");
		ServletContext servletContext = req.getServletContext();
		PrintWriter out = resp.getWriter();
		
		String srcDir = servletContext.getInitParameter("rootDir");
		File root = new File(srcDir);

		List<File> fList = new ArrayList<>();
		findAllDownloadFiles(root, fList);

		for (File file : fList) {
			String fileName = parseFile(file);

			out.write(fileName + "<a href='" + req.getContextPath() + "/servlet/FileDownload?fn=" + URLEncoder.encode(fileName, "utf8")
					+ "'>下载</a></br>");
		}

		req.getSession().setAttribute("fileList", fList);
	}

	private String parseFile(File file) {
		String name = file.getName();
		String fileName = name.substring(name.indexOf("_") + 1);

		return fileName;
	}

	private List<File> findAllDownloadFiles(File src, List<File> list) {

		if (!src.exists()) {
			return null;
		}

		if (src.isDirectory()) {
			File[] files = src.listFiles();

			for (File file : files) {
				if (!file.isDirectory()) {
					list.add(file);
				} else {
					findAllDownloadFiles(file, list);
				}
			}
		} else {
			list.add(src);
		}

		return list;
	}

	@Test
	public void Filetet() {
		Integer i1 = 50;
		Integer i2 = Integer.valueOf(50);
		System.out.println(i1==i2);
	}

}
