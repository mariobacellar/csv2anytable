package br.com.macro.test.db;

import br.com.macro.datalake.common.ConfigProperties;
import br.com.macro.datalake.dao.pooltable.PoolTableDAO;

public class PoolTableDAOTester {

	public PoolTableDAOTester() throws Exception {
		ConfigProperties.getInstance();
	}

	public void testPoolTableDAO() {
		try {
		
			String type = "mario2.test";
			
			String[] 
			lines = new String[5];
			lines[0]="nome;telefone;celular;endereco;";
			lines[1]="mario1;99999999;;av7;";
			lines[2]="mario2;99999999;;av7;";
			lines[3]="mario3;99999999;;av7;";
			lines[4]="mario4;99999999;;av7;";

			PoolTableDAO dao = new PoolTableDAO();
			
			dao.ckTable();
			
			dao.insert2PoolTable(type,lines);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		try {

			PoolTableDAOTester 
			tester = new PoolTableDAOTester();
			tester.testPoolTableDAO();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
