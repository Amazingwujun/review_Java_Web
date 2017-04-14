package com.cnblogs.lesson_thirty;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import sun.misc.BASE64Encoder;

public class TokenMaker {
	
	
	public static String make() {
		Random rd = new Random();
		String msg = ""+System.currentTimeMillis()+rd.nextInt(99999999);
		BASE64Encoder encoder = new BASE64Encoder();
		
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			
			msg = encoder.encode(md5.digest(msg.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return msg;
	}

}
