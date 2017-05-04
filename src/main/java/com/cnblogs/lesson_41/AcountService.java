package com.cnblogs.lesson_41;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.cnblogs.lesson_40.Acount;
import com.cnblogs.lesson_40.JdbcUtils;

public class AcountService {
	
	@Test
	public void testTransfer(){
		
		new AcountService().Transfer(1, 2, 100);
		
	}

	public void Transfer(int source, int target, float money) {
		Connection conn = null;

		try {
			conn = JdbcUtils.getConn();
			conn.setAutoCommit(false);

			AcountDao adao = new AcountDao(conn);

			Acount scr = adao.find(source);
			Acount tar = adao.find(target);
			scr.setMoney(scr.getMoney() - money);
			tar.setMoney(tar.getMoney() + money);

			adao.update(scr);
			int i= 1/0;
			adao.update(tar);

			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JdbcUtils.releaseRS(conn, null, null);
		}

	}

}
