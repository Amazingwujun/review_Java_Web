package com.cnblogs.lesson_46;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TEST {
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(80);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("d:/1.txt"));

		while (true) {
			Socket connection = socket.accept();
			byte[] buf = new byte[1024];
			System.out.println(connection.getInputStream().toString());

			BufferedInputStream bin = new BufferedInputStream(connection.getInputStream());
			int len = 0;
			while ((len = bin.read(buf)) != -1) {
				bos.write(buf, 0, len);
				bos.flush();
			}
		}

	}
}
