package com.cnblogs.lesson.nine;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class TEST {
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String s = new String("伍俊".getBytes(),"gbk");
		System.out.println(s);
		
		System.out.println(new String(s.getBytes("gbk"),"utf-8"));
	}
}
