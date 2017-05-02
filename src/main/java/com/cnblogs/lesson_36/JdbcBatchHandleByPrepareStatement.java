package com.cnblogs.lesson_36;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.cnblogs.lesson_32.JdbcUtils;

public class JdbcBatchHandleByPrepareStatement {

	@Test
	public void insert() {
		long start = System.currentTimeMillis();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtils.getConn();
			String sql = "insert into testbatch(id,name) values(?,?)";
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < 10008; i++) {
				pstmt.setInt(1, i);
				pstmt.setString(2, "aa"+i);
				
				pstmt.addBatch();
				if(i%1000==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}
			
			pstmt.executeBatch();
			pstmt.clearBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseRS(conn, rs, pstmt);
		}

		long end = System.currentTimeMillis();
		System.out.println("程序耗时："+(end-start)/1000+"s");
	}

}
