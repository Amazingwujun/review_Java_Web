package com.cnblogs.lesson_40;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.junit.Test;

import com.cnblogs.lesson_32.JdbcUtils;

public class DataBaseMetaData {
	
	@Test
	public void testDataBaseMetaData() throws SQLException{
		Connection conn = JdbcUtils.getConn();
		DatabaseMetaData meta = conn.getMetaData();;
		
		System.out.println(meta.getURL());
		
		System.out.println(meta.getUserName());
		
		System.out.println(meta.getDatabaseProductName());
		
		System.out.println(meta.getDatabaseProductVersion());
		
		System.out.println(meta.getDriverName());
		
		System.out.println(meta.getDriverVersion());
		
		System.out.println(meta.isReadOnly());
		JdbcUtils.releaseRS(conn, null, null);
	}
	
	@Test
	public void testParameterMetaData() throws SQLException{
		Connection conn = JdbcUtils.getConn();
		String sql = "select * from users where name=?,password=?";
		
		PreparedStatement st = conn.prepareStatement(sql);
		ParameterMetaData pm =  st.getParameterMetaData();
		
		System.out.println(pm.getParameterCount());
		
		//System.out.println(pm.getParameterType(1));
		JdbcUtils.releaseRS(conn, null, st);
	}
	
	@Test
	public void testResultSetMetaData() throws SQLException{
		Connection conn = JdbcUtils.getConn();
		String sql = "select * from acount";
		PreparedStatement st = conn.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		ResultSetMetaData rm = rs.getMetaData();
		
		System.out.println(rm.getColumnCount());
		
		System.out.println(rm.getColumnName(3));
		
		System.out.println(rm.getColumnTypeName(3));
		JdbcUtils.releaseRS(conn, rs, st);
	}
}
