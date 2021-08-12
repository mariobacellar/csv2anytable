package br.com.macro.datalake.dao.pooltable;

import org.apache.log4j.Logger;

import br.com.macro.datalake.common.Util;
import br.com.macro.datalake.dao.TableDAO;

public class PoolTableDAO extends TableDAO {

	private final static Logger logger = Logger.getLogger(PoolTableDAO.class);
	
	public PoolTableDAO() throws Exception {
		
		this.tableName = "pooltable";

		this.createTable = 
		"CREATE TABLE IF NOT EXISTS "+tableName+" ("
		+ "  type varchar (400) " 
		+ "	,created timestamp WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP "
		+ "	,line varchar (4000) " 
		+ ")";

		//"INSERT INTO " +tableName +" (type,created,line) VALUES " + "('shinobi.atendimento', '2021-02-26 17:20:51.641', '3854724;ROBO FLUXO ONLINE 1;MARCELO PEREIRA DOS SANTOS;;;;;;;SUMARE;SP;;VENDA;VENDA;MANUAL;MANUAL;22/02/2021;23:53:00; ;NAO;;;\n' )";
		this.inserRecord = 
		"INSERT INTO " +tableName +" (type,line) VALUES " + 
		"('shinobi.atendimento', '3854724;ROBO FLUXO ONLINE 1;MARCELO PEREIRA DOS SANTOS;;;;;;;SUMARE;SP;;VENDA;VENDA;MANUAL;MANUAL;22/02/2021;23:53:00; ;NAO;;;\n' )";

		this.inserStmt = "INSERT INTO " +tableName +" (type,line) VALUES (?,?)";
		
		this.deleteTypes = "DELETE FROM "+ tableName + " where type=?";

		this.selectAll = "SELECT type,created,line FROM "+ tableName;

		this.dropTable = "DROP TABLE IF EXISTS "+ tableName;
		
	}
	
	public void insert2PoolTable(String type, String[] lines) throws Exception {
 		logger.info("Apagando PoolTable -> type=["+type+"]");
		pstmt = dbc.getConnection().prepareStatement( this.deleteTypes);
		pstmt.setString(1,type);
		pstmt.execute();
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();
		
		logger.info("STARTING insert -> type=["+type+"]");
		int i=-1;
		pstmt = dbc.getConnection().prepareStatement( this.inserStmt);
		for (String line : lines)  {
			i++;
			if (i==0) continue;
			pstmt.setString(1,type);
			pstmt.setString(2,line);
			pstmt.addBatch();
		}
		
		int[] updateCounts  = pstmt.executeBatch();
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();
		
		int updateCountsErro= Util.count(updateCounts,0);
		int updateCountsOk  = Util.count(updateCounts,1);
		logger.info("Resultados retornados = ["+updateCounts.length+"] - Ok=["+updateCountsOk+"] - Erro=["+updateCountsErro+"]");
		
		pstmt.close();
		logger.debug("FINISHED insert -> type=["+type+"]");
	}
	
}
