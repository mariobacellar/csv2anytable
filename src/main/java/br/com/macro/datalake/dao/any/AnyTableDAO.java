package br.com.macro.datalake.dao.any;

import org.apache.log4j.Logger;

import br.com.macro.datalake.common.Util;
import br.com.macro.datalake.dao.TableDAO;

public class AnyTableDAO extends TableDAO {
	
	private final static Logger logger = Logger.getLogger(AnyTableDAO.class);
	
	public AnyTableDAO(String type, String header) throws Exception {
		
		header = Util.fixFieldName(header);
		logger.debug("header=["+header+"]");
		
		this.tableName = Util.fixTableName(type);
		logger.debug("tableName=["+tableName+"]");
		
		// CREATE TABLE
		this.createTable = Util.getCreateTabeleSQLFromCSVHeader(this.tableName , header);

		// INSERT INTO VALUES values
		this.inserRecord = null;

		// INSERT INTO VALUES ???
		this.inserStmt = Util.getInsertStatementaSQL(this.tableName , header);

		this.selectAll = "SELECT type,created,status FROM "+ tableName;

		this.deleteTypes = "DELETE FROM "+ tableName;

		this.dropTable = "DROP TABLE IF EXISTS "+ tableName;
		
		ckTable();
	}
	
	public void insertOneRecord(String header, String[] values) throws Exception {
		logger.info("Inserindo na tabela ["+this.tableName+"] - header=["+header+"]");
		this.insertValues=values;
		this.inserRecord = Util.getInsertStatementaSQLTest(tableName, header, insertValues);
		pstmt = dbc.getConnection().prepareStatement( this.inserRecord);
		pstmt.execute();
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();
		pstmt.close();
	}

	public void insertBatch(String type, String[] lines) throws Exception {
 		logger.info("Apagando tabela ["+this.tableName+"]");
		pstmt = dbc.getConnection().prepareStatement( this.deleteTypes);
		pstmt.execute();
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();
		
		logger.info("STARTING Inserinfo LOTE -> tabela ["+this.tableName+"]");
		logger.info("inserStmt ["+this.inserStmt+"]");
		pstmt = dbc.getConnection().prepareStatement( this.inserStmt);
		for (String line : lines)  {
			logger.trace("line ["+line+"]");
			String[] fields = line.split(";");
			for (int j = 0; j < fields.length; j++) {
				String field = fields[j];
				int index = j+1;
				logger.trace("index("+index+") - field["+j+"]=["+field+"]");
				pstmt.setString(index,field);				
			}
			pstmt.addBatch();
		}
		int[] updateCounts = pstmt.executeBatch();
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();

		int updateCountsErro= Util.count(updateCounts,0);
		int updateCountsOk  = Util.count(updateCounts,1);
		logger.info("Resultados retornados = ["+updateCounts.length+"] - Ok=["+updateCountsOk+"] - Erro=["+updateCountsErro+"]");
		
		pstmt.close();
		logger.info("FINISHED  Inserinfo LOTE  -> type=["+type+"]");
	}
	
}
