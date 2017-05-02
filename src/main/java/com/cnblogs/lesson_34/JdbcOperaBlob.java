package com.cnblogs.lesson_34;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.cnblogs.lesson_32.JdbcUtils;

public class JdbcOperaBlob {

	@Test
	public void insert() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtils.getConn();
			File file = new File("C:/Users/Administrator/Desktop/01.jpg");
			FileInputStream fin = new FileInputStream(file);
			String sql = "insert into testblob(img) values(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setBlob(1, fin);

			int num = pstmt.executeUpdate();
			if (num > 0) {
				System.out.println("图片插入成功");
			}

		} catch (SQLException e) {
			// TODO: handle exception
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}
	}

	@Test
	public void find() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			File file = new File("C:/Users/Administrator/Desktop/02.jpg");
			FileOutputStream fout = new FileOutputStream(file);

			conn = JdbcUtils.getConn();
			String sql = "select img from testblob where id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, 1);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				Blob blob = rs.getBlob("img");
				InputStream in = blob.getBinaryStream();

				byte[] buff = new byte[1024];
				int len;

				while ((len = in.read(buff)) > 0) {
					fout.write(buff, 0, len);
				}
			}
			
			fout.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
