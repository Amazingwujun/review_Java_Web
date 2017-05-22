package com.cnblogs.lesson_50;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/FileDownload")
public class FileDownload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<File> list = (List<File>) req.getSession().getAttribute("fileList");

		String tar = req.getParameter("fn");

		System.out.println(tar);
		File tarFile = null;
		for (File file : list) {
			String name = file.getName();
			String fileName = name.substring(name.indexOf("_") + 1);

			if (tar.equals(fileName)) {
				tarFile = file;
			}
		}

		if (tarFile == null) {
			throw new RuntimeException("没有找到用户需要的文件");
		}

		resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(tar, "utf8"));

		FileInputStream fin = new FileInputStream(tarFile);
		BufferedInputStream bin = new BufferedInputStream(fin);

		BufferedOutputStream bout = new BufferedOutputStream(resp.getOutputStream());

		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = bin.read(buf)) != -1) {
			bout.write(buf, 0, len);
		}

		bin.close();
		bout.close();
	}

}
