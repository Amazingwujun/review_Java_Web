package com.cnblogs.lesson_41;

import java.sql.SQLException;

import org.junit.Test;

import com.cnblogs.lesson_40.Acount;

public class AcountService {
	
	@Test
	public void transferTest(){
		
		try {
			transfer(1, 2, 100);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @Method: transfer
	 * @Description: 这个方法用来处理两个用户之间的转账业务
	 * @author: 俊
	 * 
	 * @param: source
	 * @param: target
	 * @param: money
	 * 
	 * @throws SQLException
	 */
	public void transfer(int source, int target, float money) throws SQLException {
		try {
			JdbcUtils_thread.startTransaction();
			
			AcountDao adao = new AcountDao();

			Acount src = adao.find(source);
			Acount tar = adao.find(target);

			src.setMoney(src.getMoney() - money);
			tar.setMoney(tar.getMoney() + money);
			
			adao.update(src);
			int i=1/0;
			adao.update(tar);
			
			JdbcUtils_thread.commit();
		} catch (Exception e) {
			e.printStackTrace();
			JdbcUtils_thread.rollback();
		} finally{
			JdbcUtils_thread.close();
		}
	}
}
