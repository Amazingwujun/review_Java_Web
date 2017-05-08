package com.cnblogs.lesson_43;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

public class Test {

	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("d:/dirtyWords.txt");

		StringWriter sw = new StringWriter();
		
		char[] cbuf = new char[1024];
		int len;
		while ((len = fr.read(cbuf)) > 0) {
			sw.write(cbuf, 0, len);
		}
		
		System.out.println(sw.toString());
	}
}
