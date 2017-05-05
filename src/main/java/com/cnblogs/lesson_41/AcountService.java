package com.cnblogs.lesson_41;

import java.sql.SQLException;

import org.junit.Test;

import com.cnblogs.lesson_40.Acount;

public class AcountService {

	@Test
	public void testTransfer() {
		try {
			new AcountService().Transfer(1, 1, 300);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Transfer(int source, int target, float money) throws SQLException {
		if (source == target) {
			throw new RuntimeException("不支持同账户转账");
		}

		AcountDao adao = new AcountDao();

		// 查找指定用户并转账
		Acount scr = adao.find(source);
		Acount tar = adao.find(target);
		scr.setMoney(scr.getMoney() - money);
		tar.setMoney(tar.getMoney() + money);

		// 开始转账
		adao.update(scr);
		// 模拟异常
		// int i = 1 / 0;
		adao.update(tar);
	}

}
