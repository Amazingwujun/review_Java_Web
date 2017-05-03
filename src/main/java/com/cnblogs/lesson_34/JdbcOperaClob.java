package com.cnblogs.lesson_34;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;

import com.cnblogs.lesson_32.JdbcUtils;

public class JdbcOperaClob {

	@Test
	public void insert() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		Reader reader = null;

		try {
			File file = new File("d:/大道争锋.txt");
			reader = new FileReader(file);

			conn = JdbcUtils.getConn();
			sql = "insert into testclob(resume) values(?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setCharacterStream(1, reader, file.length());

			int num = pstmt.executeUpdate();
			if (num > 0) {
				System.out.println("数据插入成功");
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
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
		String sql = "";
		Reader reader = null;
		Clob clob = null;

		try {
			File file = new File("d:/放开那个女巫.txt");
			FileWriter fWriter = new FileWriter(file);

			conn = JdbcUtils.getConn();
			sql = "select resume from testclob where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				clob = rs.getClob("resume");
				reader = clob.getCharacterStream();

				char[] cbuf = new char[1024];
				int len = 0;
				while ((len = reader.read(cbuf)) > 0) {
					fWriter.write(cbuf, 0, len);
				}
			}
			
			fWriter.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}

	}

}
