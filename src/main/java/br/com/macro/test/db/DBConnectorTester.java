package br.com.macro.test.db;

import java.sql.ResultSet;
import java.sql.Statement;

import br.com.macro.datalake.common.ConfigProperties;
import br.com.macro.datalake.common.DBConnector;
import br.com.macro.datalake.dao.pooltable.PoolCtrlDAO;
import br.com.macro.datalake.dao.pooltable.PoolHeaderDAO;

/**
 * https://git-wip-us.apache.org/repos/asf?p=commons-dbcp.git;a=tree;f=doc;hb=HEAD
 * @author mario
 *
 */
public class DBConnectorTester {

	public DBConnectorTester() throws Exception {
		ConfigProperties.getInstance();
	}
	
	public void testDBConnection() {
		try {
			
			DBConnector dbc1 = DBConnector.getInstance();
			DBConnector dbc2 = DBConnector.getInstance();
			
//			Connection conn1 = dbc2.getDataSource().getConnection();
//			Connection conn2 = dbc2.getDataSource().getConnection();
	    	
			PoolHeaderDAO headerDAO = new PoolHeaderDAO();
			headerDAO.ckTable();
			
			Statement	stmt1 = dbc1.getConnection().createStatement();
			ResultSet	rset1 = stmt1.executeQuery("SELECT * FROM public.poolheader");
	    	while (rset1.next()) 
	            System.out.println( "Read from DB => - " + rset1.getString("TYPE") + " - " + rset1.getTimestamp("CREATED") + " - " + rset1.getString("LINE"));
	    	//dbc1.getConnection().close();
	    	//dbc1.close();
	    	
	    	PoolCtrlDAO ctrlDAO = new PoolCtrlDAO();
			ctrlDAO.ckTable();
			
			Statement	stmt2 = dbc2.getConnection().createStatement();
			ResultSet	rset2 = stmt2.executeQuery("SELECT * FROM public.poolctrl");
	    	while (rset2.next()) 
	            System.out.println( "Read from DB => - " + rset2.getString("TYPE") + " - " + rset2.getTimestamp("CREATED") + " - " + rset2.getString("STATUS"));
	    	dbc2.getConnection().close();
	    	DBConnector.printStatus(dbc2.getDataSource());
	    	
	    	DBConnector.printStatus(dbc2.getDataSource());
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			
			DBConnectorTester 
			tester = new DBConnectorTester();
			tester.testDBConnection();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
