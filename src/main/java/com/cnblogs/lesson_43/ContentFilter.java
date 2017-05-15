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

/**
* @ClassName: GzipFilter
* @Description: 压缩过滤器，将web应用中的文本都经过压缩后再输出到浏览器
* @author: 孤傲苍狼
* @date: 2014-9-7 上午10:52:42
*
*/ 
//@WebFilter("/*")
public class ContentFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletResponse response = (HttpServletResponse) resp;
        
        BufferResponse myresponse = new BufferResponse(response);
        chain.doFilter(req, myresponse);
        //拿出缓存中的数据，压缩后再打给浏览器
        byte out[] = myresponse.getBuffer();
        System.out.println("原始大小:" + out.length);
        
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        //压缩输出流中的数据
        GZIPOutputStream gout = new GZIPOutputStream(bout);
        gout.write(out);
        gout.close();
        
        byte gzip[] = bout.toByteArray();
        System.out.println("压缩后的大小:" + gzip.length);
        
        response.setHeader("content-encoding", "gzip");
        response.setContentLength(gzip.length);
        response.getOutputStream().write(gzip);
    }
    
    public void destroy() {
        
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        
    }
}

class BufferResponse extends HttpServletResponseWrapper{

    private ByteArrayOutputStream bout = new ByteArrayOutputStream();
    private PrintWriter pw;
    private HttpServletResponse response;
    public BufferResponse(HttpServletResponse response) {
        super(response);
        this.response = response;
    }
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new MyServletOutputStream(bout);
    }
    @Override
    public PrintWriter getWriter() throws IOException {
        pw = new PrintWriter(new OutputStreamWriter(bout,this.response.getCharacterEncoding()));
        return pw;
    }
    
    public byte[] getBuffer(){
        try{
            if(pw!=null){
                pw.flush();
            }
            if(bout!=null){
                bout.flush();
                return bout.toByteArray();
            }
            
            
            return null;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class MyServletOutputStream extends ServletOutputStream{

    private ByteArrayOutputStream bout;
    public MyServletOutputStream(ByteArrayOutputStream bout){
        this.bout = bout;
    }
    
    @Override
    public void write(int b) throws IOException {
        this.bout.write(b);
    }

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWriteListener(WriteListener paramWriteListener) {
		// TODO Auto-generated method stub
		
	}
}