package com.cnblogs.lesson_39;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class PoolTest {

	@Test
	public void find() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			conn = JdbcUtils_C3P0.getConn();
			String sql = "select * from acount";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				System.out.println("id=" + rs.getString(1));
				System.out.println("name=" + rs.getString(2));
				System.out.println("money=" + rs.getString(3));
			}
			
			conn.getCatalog();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			JdbcUtil.releaseRS(conn, rs, pstmt);
		}

	}

}
