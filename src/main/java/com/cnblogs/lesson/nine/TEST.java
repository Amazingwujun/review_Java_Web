package com.cnblogs.lesson.nine;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class TEST {
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String s = "%D6%D8%C9%FA%D6%AE%B3%F6%C8%CB%CD%B7%B5%D8";
		System.out.println(URLDecoder.decode(s, "gbk"));
		
		String num = "nnDvQFOd3nn5VVaKLV6O/Q==";
		System.out.println(num.length());
	}
}
