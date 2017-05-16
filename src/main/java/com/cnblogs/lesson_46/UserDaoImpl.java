package com.cnblogs.lesson_46;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cnblogs.lesson_39.JdbcUtils_C3P0;

public class UserDaoImpl implements UserDao {

	@Override
	public User find(String username) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User u = new User();

		try {
			conn = JdbcUtils_C3P0.getConn();
			String sql = "select * from users where name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				u.setUsername(rs.getString("name"));
				u.setPassword(rs.getString("password"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils_C3P0.releaseRS(conn, rs, pstmt);
		}

		return u;
	}

	@Override
	public User find(User u) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;

		try {
			conn = JdbcUtils_C3P0.getConn();
			String sql = "select * from users where name=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				String uname = rs.getString("name");
				String uword = rs.getString("password");
				
				if (uname == null || uword == null) {
					return null;
				}
				
				user = u;
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils_C3P0.releaseRS(conn, rs, pstmt);
		}

		return user;
	}

}
