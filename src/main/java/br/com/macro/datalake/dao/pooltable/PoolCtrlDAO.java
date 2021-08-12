package br.com.macro.datalake.dao.pooltable;

import org.apache.log4j.Logger;

import br.com.macro.datalake.dao.TableDAO;

public class PoolCtrlDAO extends TableDAO {
	
	private final static Logger logger = Logger.getLogger(PoolCtrlDAO.class);
	
	public PoolCtrlDAO() throws Exception {
		
		this.tableName = "poolctrl";

		this.createTable = 
		"CREATE TABLE IF NOT EXISTS "+tableName+" ("
		+ "  type varchar (400) " 
		+ "	,created timestamp WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP "
		+ "	,status varchar (255) " 
		+ ")";
		

		//this.inserRecord = "INSERT INTO " +tableName +" (type,created,status) VALUES ('shinobi.atendimento', '2021-02-26 17:20:51.641', 'carregado' )";
		this.inserRecord = "INSERT INTO " +tableName +" (type,status) VALUES ('shinobi.atendimento', 'carregado' )";

		this.inserStmt = "INSERT INTO " +tableName +" (type,status) VALUES (?,?)";
		
		this.selectAll = "SELECT type,created,status FROM "+ tableName;

		this.deleteTypes = "DELETE FROM "+ tableName + " where type=?";

		this.dropTable = "DROP TABLE IF EXISTS "+ tableName;
	}
	
	public void insert2PoolCtrl(String type, String status) throws Exception {

		logger.info("Apagando PoolCtrl -> type=["+type+"]");
		pstmt = dbc.getConnection().prepareStatement( this.deleteTypes);
		pstmt.setString(1,type);
		pstmt.execute();
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();

		
		logger.info("Inserindo PoolCtrl -> type=["+type+"] - Status=["+status+"]");
		pstmt = dbc.getConnection().prepareStatement( this.inserStmt);
		pstmt.setString(1,type);
		pstmt.setString(2,status);
		pstmt.execute();
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();

		pstmt.close();
	}
		
}
