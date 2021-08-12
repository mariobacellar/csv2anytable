package br.com.macro.datalake.dao;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import br.com.macro.datalake.common.DBConnector;

public class TableDAO {
	

	private final static Logger logger = Logger.getLogger(TableDAO.class);
	
	
	protected PreparedStatement pstmt;
	protected Statement stmt;
	protected DBConnector dbc;
	
	public String tableName;
	public String createTable;
	public String inserRecord;
	public String inserStmt;
	public String selectAll;
	public String dropTable;
	public String deleteTypes;
	public String[] insertValues;
				
	
	public TableDAO() throws Exception{
		this.dbc = DBConnector.getInstance();
	}
	
	public void doDropTable() throws Exception{
		dbc.getConnection().createStatement().executeUpdate(dropTable);
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();	
	}

	public void doCreateTable() throws Exception{
		dbc.getConnection().createStatement().executeUpdate(createTable);
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();	
	}

	public void doInsertRecord() throws Exception{
		dbc.getConnection().createStatement().executeUpdate(inserRecord);
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();	
	}
 
	public ResultSet doSelectAll() throws Exception{
		return dbc.getConnection().createStatement().executeQuery(selectAll);
	}

	public void ckTable() throws Exception {
		DatabaseMetaData dbm = dbc.getConnection().getMetaData();
		ResultSet tables = dbm.getTables(null, null, this.tableName, null);
		if (tables.next()) {
			logger.info("Table ["+this.tableName+"] alredy exists");
		}else{
			logger.info("Table ["+this.tableName+"] dosen't alredy exists...creating table now");
			doCreateTable() ;
			logger.info("Table ["+this.tableName+"] created");
		}
	}
}
