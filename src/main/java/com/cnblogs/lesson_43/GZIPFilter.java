package com.cnblogs.lesson_43;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


//@WebFilter("/*")
public class GZIPFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("GZIPFilter start");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		MyResponse myResponse = new MyResponse(resp);

		chain.doFilter(request, myResponse);

		ByteArrayOutputStream bos = myResponse.getBout();
		byte[] buff = bos.toByteArray();
		System.out.println("压缩前的大小：" + buff.length);

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(bout);
		gout.write(buff);
		gout.close();

		resp.setHeader("content-encoding", "gzip");

		byte results[] = bout.toByteArray();
		System.out.println("压缩后的大小：" + results.length);
		response.getOutputStream().write(results);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	class MyResponse extends HttpServletResponseWrapper {
		private HttpServletResponse response;
		private PrintWriter pw;
		private ByteArrayOutputStream bout = new ByteArrayOutputStream();

		public MyResponse(HttpServletResponse response) {
			super(response);
			this.response = response;
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			// TODO Auto-generated method stub
			return new MyServletOutputStream(bout);
		}

		public ByteArrayOutputStream getBout() {
			if (pw != null) {
				//pw.close();
				pw.flush();
			}

			return bout;
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			pw = new PrintWriter(new OutputStreamWriter(bout, this.response.getCharacterEncoding()));
			return pw;
		}

		class MyServletOutputStream extends ServletOutputStream {
			private ByteArrayOutputStream bout;

			public MyServletOutputStream(ByteArrayOutputStream bout) {
				this.bout = bout;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setWriteListener(WriteListener writeListener) {
				// TODO Auto-generated method stub

			}

			@Override
			public void write(int b) throws IOException {
				this.bout.write(b);
			}

		}
	}
}
