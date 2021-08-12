package br.com.macro.test.db;

import br.com.macro.datalake.common.ConfigProperties;
import br.com.macro.datalake.dao.pooltable.PoolHeaderDAO;

public class PoolHeaderDAOTester {

	public PoolHeaderDAOTester() throws Exception {
		ConfigProperties.getInstance();
	}

	public void testPoolHeaderDAO() {
		try {
			String   type = "mario2.test";
			String[] 
			lines = new String[2];
			lines[0]="nome;telefone;celular;endereco;";
			lines[1]="mario;99999999;;av7;";
			
			PoolHeaderDAO dao = new PoolHeaderDAO();
			
			dao.ckTable();
			
			dao.insert2PoolHeader(type, lines);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		try {

			PoolHeaderDAOTester 
			tester = new PoolHeaderDAOTester();
			tester.testPoolHeaderDAO();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
