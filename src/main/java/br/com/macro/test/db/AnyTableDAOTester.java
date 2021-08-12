package br.com.macro.test.db;

import br.com.macro.datalake.common.ConfigProperties;
import br.com.macro.datalake.dao.any.AnyTableDAO;

public class AnyTableDAOTester {

	public AnyTableDAOTester() throws Exception {
		ConfigProperties.getInstance();
	}

	public void testAnyTableDAO() {
		try {
			String type   = "mario2.test";
			
			String[] 
			lines = new String[5];
			lines[0]="nome;telefone;celular;endereco;";
			lines[1]="mario21;99999999;we;av7";
			lines[2]="mario22;99999999;we;av7";
			lines[3]="mario23;99999999;we;av7";
			lines[4]="mario24;99999999;we;av7";
			
			String header = lines[0];
			
			AnyTableDAO dao = new AnyTableDAO(type, header);
			
			dao.insertOneRecord(header, lines[1].split(";"));

			dao.insertBatch(type,lines);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {

			AnyTableDAOTester 
			tester = new AnyTableDAOTester();
			tester.testAnyTableDAO();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
