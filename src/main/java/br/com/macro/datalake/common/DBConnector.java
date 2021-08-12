package br.com.macro.datalake.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class DBConnector {

	private final static Logger logger = Logger.getLogger(DBConnector.class);

	private static DBConnector instance = null;
	private Connection conn;
	private DataSource dataSource;

	
	public static DBConnector getInstance() throws Exception {
		if (instance==null)  instance = new DBConnector();
		return instance;
	}
	
	private DBConnector() throws Exception {
		dataSource = newDataSource();
		conn = dataSource.getConnection();
		conn.setAutoCommit(false);
	}

	
	public Connection getConnection() {
		return conn;
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	
	 public static DataSource newDataSource() {
		 
		 String driverName = System.getProperty("datalake.datasource.driver.class.name") ;
		 logger.info("driverName=["+driverName+"]");
		 
		 String url = System.getProperty("datalake.datasource.url");
		 logger.info("url=["+url+"]");
		 
		 String username = System.getProperty("datalake.datasource.username");
		 logger.info("username=["+username+"]");

		 String password = System.getProperty("datalake.datasource.password");
		 logger.info("password=[*****]");

		 BasicDataSource 
		 ds = new BasicDataSource();
		 ds.setDriverClassName( driverName);
		 ds.setUrl( System.getProperty("datalake.datasource.url") );
		 ds.setUsername( username  );
		 ds.setPassword( password);
		 return ds;
	 }
	 
	 public static void printStatus(DataSource ds) {
		 BasicDataSource bds = (BasicDataSource) ds;
		 logger.info("NumActive...:[" + bds.getNumActive()+"]");
		 logger.info("NumIdle.....:[" + bds.getNumIdle()  +"]");
	}
		 
	 public static void close(DataSource ds) throws SQLException {
		 BasicDataSource bds = (BasicDataSource) ds;
		 bds.close();
	 }
	 
	public Statement getStatement() throws Exception {
		return getConnection().createStatement();
	}


	public void closeConnection() throws Exception {
		conn.close();
	}
	
	public void closeDataSource() throws Exception {
		BasicDataSource bds = (BasicDataSource) dataSource;
		 bds.close();
	}
	public void closeAll() throws Exception {
		closeConnection();
		closeDataSource();
	}
}
