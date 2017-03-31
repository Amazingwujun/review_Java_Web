package com.cnblogs.lesson_eight;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ResponseDemo03")
public class ResponseDemo03 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("content-type", "image/jpeg");
		resp.setDateHeader("expires", -1);
		resp.setHeader("Cache-control", "no-cache");
		resp.setHeader("pragma", "no-Cache");
		//BufferedImage img = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\3.jpg"));
		//在内存中建立一个图片
		BufferedImage img = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);
		//获取图片
		
		Graphics2D g2d = img.createGraphics();
		g2d.rotate(45*Math.PI/180,10,10);
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, 80, 20);
		//设置图片上的字体
		g2d.setColor(getRandomColor());
		g2d.setFont(new Font(null, Font.BOLD, 20));
		g2d.drawString(getRandomNumber(), 10, 17);
		
		
		
		File image = new File("d:/checkNum.jpg");
		ImageIO.write(img, "jpg", resp.getOutputStream());
		
			
	}

	private Color getRandomColor() {
		Color[] colors = {Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN};
		Random rd = new Random();
		int index= rd.nextInt(4);
		
		
		return colors[index];
	}

	private String getRandomNumber() {
		String num = "";
		Random rd = new Random();
		num = rd.nextInt(999999)+"";
		StringBuffer sb = new StringBuffer();
		
		for(int i=0;i<6-num.length();i++){
			sb.append(0);
		}
		
		return sb.toString()+num;
	}

}
