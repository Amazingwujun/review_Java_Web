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

import sun.misc.BASE64Decoder;

@WebServlet("/servlet/DownloadFileList")
public class DownloadFileList extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Test
	public void fuhao() throws IOException{
		BASE64Decoder decoder = new BASE64Decoder();
		
		String emailSubject = "=?UTF-8?B?5qyi6L+O5oKo6L+b5YWl5paw54mI5pCc54uQ6Zeq55S16YKu566x77yB?=";
		String msg = "=?GBK?B?08q8/rLiytQ=?=";
		String emailContent = "PCFET0NUWVBFIGh0bWwgUFVCTElDICItLy9XM0MvL0RURCBYSFRNTCAxLjAgVHJhbnNpdGlvbmFs"+
"Ly9FTiIgImh0dHA6Ly93d3cudzMub3JnL1RSL3hodG1sMS9EVEQveGh0bWwxLXRyYW5zaXRpb25h"+
"bC5kdGQiPgo8aHRtbCB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94aHRtbCI+CjxoZWFk"+
"Pgo8bWV0YSBodHRwLWVxdWl2PSJDb250ZW50LVR5cGUiIGNvbnRlbnQ9InRleHQvaHRtbDsgY2hh"+
"cnNldD1nYjIzMTIiIC8+Cjx0aXRsZT7mkJzni5Dpl6rnlLXpgq7nrrHmrKLov47mgqg8L3RpdGxl"+
"Pgo8L2hlYWQ+Cgo8Ym9keSBzdHlsZT0ibWFyZ2luOjAiPgo8aW1nIHNyYz0iIiB3aWR0aD0xIGhl"+
"aWdodD0xPgo8dGFibGUgd2lkdGg9IjYxNiIgYm9yZGVyPSIwIiBhbGlnbj0iY2VudGVyIiBjZWxs"+
"cGFkZGluZz0iMCIgY2VsbHNwYWNpbmc9IjMiIGJnY29sb3I9IiNlNGYyZmEiPgogIDx0cj4KICAg"+
"IDx0ZCA+PHRhYmxlIHdpZHRoPSI2MTAiIGJvcmRlcj0iMCIgYWxpZ249ImNlbnRlciIgY2VsbHBh";
		System.out.println(new String(decoder.decodeBuffer(emailSubject),"UTF-8"));
		
	}
	
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
