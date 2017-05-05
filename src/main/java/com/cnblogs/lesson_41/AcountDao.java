package com.cnblogs.lesson_41;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import com.cnblogs.lesson_40.Acount;
import com.cnblogs.lesson_40.BeanHandle;
import com.cnblogs.lesson_40.JdbcUtils;

public class AcountDao {
	
	public void update(Acount acount) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "update Acount set money=? where id=?";

		Object[] args = { acount.getMoney(), acount.getId() };
		qr.update(JdbcUtils_thread.getConn(), sql, args);
	}

	public Acount find(int id) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "select * from Acount where id=?";

		Object[] args = { id };
		return qr.query(JdbcUtils_thread.getConn(), sql, new BeanHandler<Acount>(Acount.class), args);
	}

	@Deprecated
	public void transfer(String source, String target, float money) throws SQLException {
		Connection conn = null;

		try {
			conn = JdbcUtils.getConn();
			conn.setAutoCommit(false);

			QueryRunner qr = new QueryRunner();
			String sqlSrc = "update Acount set money=money-? where name=?";
			String sqlTar = "update Acount set money=money+? where name=?";

			Object[] src = { money, source };
			Object[] tar = { money, target };

			qr.update(conn, sqlSrc, src);
			int i = 1 / 0;
			qr.update(conn, sqlTar, tar);

			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
