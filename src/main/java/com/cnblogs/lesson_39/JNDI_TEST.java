package com.cnblogs.lesson_39;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/JNDI_TEST")
public class JNDI_TEST extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			conn = JdbcUtils_JNDI.getConn();
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
