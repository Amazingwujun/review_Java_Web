package com.cnblogs.lesson_36;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.cnblogs.lesson_32.JdbcUtils;

public class JdbcBatchHandleByStatement {

	@Test
	public void batch(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtils.getConn();
			String sql1 = "insert into testbatch(id,name) values(1,'aa')";
			String sql2 = "insert into testbatch(id,name) values(2,'bb')";
			String sql3 = "insert into testbatch(id,name) values(3,'cc')";
			String sql4 = "update  testbatch set name='dd' where id=3";
			stmt = conn.createStatement();
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.addBatch(sql3);
			stmt.addBatch(sql4);
			
			stmt.executeBatch();
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			JdbcUtils.releaseRS(conn, rs, stmt);
		}
	}
	
}
