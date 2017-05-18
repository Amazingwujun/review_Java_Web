package com.cnblogs.lesson_50;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileCleaningTracker;

@WebServlet("/servlet/FileUploadHandle")
public class FileUploadHandle extends HttpServlet {
	private ServletContext servletContext;
	// 上传文件临时存放位置
	private File tempDirectory;
	// 上传文件大小限制
	private int maxRequestSize;
	//
	private String rootDir = "d:/rootDir";

	public static DiskFileItemFactory newDiskFileItemFactory(ServletContext context, File repository) {
		FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(context);
		DiskFileItemFactory factory = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository);
		factory.setFileCleaningTracker(fileCleaningTracker);
		return factory;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		this.servletContext = getServletContext();
		try {
			tempDirectory = new File(servletContext.getInitParameter("tempDirectory"));
			maxRequestSize = Integer.parseInt(servletContext.getInitParameter("maxRequestSize"));
		} catch (Exception e) {
			log("参数不存在或解析异常，请检查web.xml配置文件是否正确配置！");
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = newDiskFileItemFactory(servletContext, tempDirectory);

		// Set factory constraints
		// factory.setSizeThreshold(yourMaxMemorySize);
		factory.setRepository(tempDirectory);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(1024 * 1024 * 1024);
		// 监听下载进度
		ProgressListener progressListener = new ProgressListener() {
			private long megaBytes = -1;

			public void update(long pBytesRead, long pContentLength, int pItems) {
				long mBytes = pBytesRead / 1000000;
				if (megaBytes == mBytes) {
					return;
				}
				megaBytes = mBytes;

				System.out.println("We are currently reading item " + pItems);
				if (pContentLength == -1) {
					System.out.println("So far, " + pBytesRead + " bytes have been read.");
				} else {
					System.out.println("So far, " + pBytesRead / 1024 + " of " + pContentLength / 1024
							+ " Kbytes have been read.");
				}
			}
		};

		upload.setProgressListener(progressListener);

		// 是否为文件上传请求
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (!isMultipart) {
			return;
		}

		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(req);

			for (FileItem item : items) {

				String name = item.getFieldName();
				InputStream stream = item.getInputStream();

				if (item.isFormField()) {// 如果是表单域
					String value = item.getString();

					System.out.println("Form field " + name + " with value " + Streams.asString(stream) + " detected.");

				} else {
					String fileName = item.getName();
					System.out.println("File field " + name + " with file name " + fileName + " detected.");

					// 建立缓冲流
					BufferedInputStream bis = new BufferedInputStream(stream);
					// 获取将要下载的文件流
					File filePath = dirDispather(rootDir, fileName);
					BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(filePath));

					byte[] buf = new byte[1024];
					int len = 0;
					while ((len = bis.read(buf)) != -1) {
						bout.write(buf, 0, len);
					}

					bis.close();
					bout.close();
					System.out.println("文件上传成功");
				}
			}

		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@org.junit.Test
	public void fda() {
		dirDispather(rootDir, "hah.txt");
	}

	private File dirDispather(String rootDir, String fileName) {
		// 利用hashcode来定位下载文件的分属文件夹
		int dir = fileName.hashCode() & 0x0f;

		File tarDir = new File(rootDir + "/" + dir);
		if (!tarDir.exists()) {
			tarDir.mkdirs();
		}

		String prefix = UUID.randomUUID().toString();
		String filePath = rootDir + "/" + dir + "/" + prefix + "_" + fileName;

		System.out.println(filePath);
		return new File(filePath);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
