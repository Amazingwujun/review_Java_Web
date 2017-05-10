package com.cnblogs.lesson_43;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@WebFilter("/*")
public class WebResouceCacheFilter implements Filter {
	private static final ConcurrentHashMap<String, byte[]> cacheMap = new ConcurrentHashMap<>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		byte[] cache = cacheMap.get(uri);

		if (cache != null) {
			String message = new String(cache, req.getCharacterEncoding());
			System.out.println(message);
			resp.getOutputStream().write(cache);
			return;
		}

		BufferResponse br = new BufferResponse(resp);
		chain.doFilter(req, br);
		
		byte[] resource = br.getBuff();
		cacheMap.put(uri, resource);
		response.getOutputStream().write(resource);
		System.out.println(cacheMap.size());
	}

	class BufferResponse extends HttpServletResponseWrapper {
		private HttpServletResponse response;
		private ByteArrayOutputStream bout = new ByteArrayOutputStream();
		private PrintWriter pw;

		public BufferResponse(HttpServletResponse response) {
			super(response);
			this.response = response;
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			pw = new PrintWriter(new OutputStreamWriter(bout, response.getCharacterEncoding()));
			return pw;
		}
		
		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return new ServletBout(bout);
		}
		
		public byte[] getBuff(){
			if(pw!=null){
				pw.close();
			}
			
			return bout.toByteArray();
		}		

		class ServletBout extends ServletOutputStream{
			private ByteArrayOutputStream bout;
			
			public ServletBout(ByteArrayOutputStream bout) {
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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
