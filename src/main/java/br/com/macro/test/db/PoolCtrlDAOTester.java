package br.com.macro.test.db;

import br.com.macro.datalake.common.ConfigProperties;
import br.com.macro.datalake.dao.pooltable.PoolCtrlDAO;

public class PoolCtrlDAOTester {

	public PoolCtrlDAOTester() throws Exception {
		ConfigProperties.getInstance();
	}

	public void testPoolCtrlDAO() {
		try {
			String type   = "mario2.test";
			String status = "testando";
			
			PoolCtrlDAO dao = new PoolCtrlDAO();
			
			dao.ckTable();
			
			dao.insert2PoolCtrl(type, status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		try {

			PoolCtrlDAOTester 
			tester = new PoolCtrlDAOTester();
			tester.testPoolCtrlDAO();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
