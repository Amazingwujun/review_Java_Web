package com.cnblogs.lesson_37;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cnblogs.lesson_32.JdbcUtils;

public class Test {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtils.getConn();
			String sql = "insert into lesson37(name) values(?)";
			pstmt = conn.prepareStatement(sql, 1);

			pstmt.setString(1, "wujun");

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			while (rs.next()) {
				System.out.println(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}

	}
}
