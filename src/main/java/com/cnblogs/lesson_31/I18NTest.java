package com.cnblogs.lesson_31;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18NTest {
	
	public static void main(String[] args) {
		Locale zh = Locale.CHINA;
		Locale en = Locale.ENGLISH;
		
		ResourceBundle myResourceEn = ResourceBundle.getBundle("myproperties", en);
		ResourceBundle myResourceZh = ResourceBundle.getBundle("myproperties", zh);
		
		System.out.println(myResourceEn.getString("username"));
		System.out.println(myResourceEn.getString("password"));
		
		System.out.println(myResourceZh.getString("username"));
		System.out.println(myResourceZh.getString("password"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		sdf.setLenient(false);

		try {
			sdf.parse("1989-12-233");
		} catch (ParseException e) {
			System.out.println("parse false");
		}
		
		System.out.println(sdf.format(new Date()));
	}
	
}
