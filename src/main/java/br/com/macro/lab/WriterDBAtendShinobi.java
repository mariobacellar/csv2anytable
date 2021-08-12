package br.com.macro.lab;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.macro.datalake.common.DBConnector;
import br.com.macro.datalake.common.Util;
import br.com.macro.datalake.dao.pooltable.PoolCtrlDAO;
import br.com.macro.datalake.dao.pooltable.PoolHeaderDAO;
import br.com.macro.datalake.dao.pooltable.PoolTableDAO;

public class WriterDBAtendShinobi {

	private final static Logger logger = Logger.getLogger(WriterDBAtendShinobi.class);
	
	private DBConnector 		dc;
	private PreparedStatement	pstmt; 
	private PoolTableDAO 		pooltable;
	private PoolCtrlDAO 		poolctrl;
	private PoolHeaderDAO 		poolheader;
	
	private String				tipoCarga; // 'incremental' ou 'full'
	
	public WriterDBAtendShinobi(String tipoCarga) throws Exception {
		this. pooltable = new PoolTableDAO();
		this. poolctrl	= new PoolCtrlDAO();
		this. poolheader= new PoolHeaderDAO();
		this.dc = DBConnector.getInstance();
		this.tipoCarga=tipoCarga;
	}

	
	public void insert2PoolCtrl(String type, String status) throws IOException, SQLException {

		logger.debug("Apagando PoolCtrl -> type=["+type+"]");
		pstmt = dc.getConnection().prepareStatement( poolctrl.deleteTypes);
		pstmt.setString(1,type);
		pstmt.execute();
		dc.getConnection().commit();

		logger.debug("Inserindo PoolCtrl -> type=["+type+"] - Status=["+status+"]");
		pstmt = dc.getConnection().prepareStatement( poolctrl.inserStmt);
		pstmt.setString(1,type);
		pstmt.setString(2,status);
		pstmt.execute();
		dc.getConnection().commit();
		pstmt.close();
		
	}

	public void insert2PoolHeader(String type, String[] lines) throws IOException, SQLException {
		String headerLine = lines[0];

		logger.debug("Apagando PoolHeader -> type=["+type+"]");
		pstmt = dc.getConnection().prepareStatement( poolheader.deleteTypes);
		pstmt.setString(1,type);
		pstmt.execute();
		dc.getConnection().commit();

		logger.debug("Inserindo PoolHeader -> type=["+type+"] - Line=["+headerLine+"]");
		pstmt = dc.getConnection().prepareStatement( poolheader.inserStmt);
		pstmt.setString(1,type);
		pstmt.setString(2,headerLine);
		pstmt.execute();
		dc.getConnection().commit();
		pstmt.close();
	}
	
	public void insert2PoolTable(File fileCSV, String[] lines, String type) throws IOException, SQLException {
 		logger.debug("Apagando PoolTable -> type=["+type+"]");
		pstmt = dc.getConnection().prepareStatement( pooltable.deleteTypes);
		pstmt.setString(1,type);
		pstmt.execute();
		dc.getConnection().commit();
		
		logger.debug("STARTING insert -> type=["+type+"]");
		pstmt = dc.getConnection().prepareStatement( pooltable.inserStmt);
		for (String line : lines)  {
			//String type = partFileSource+"."+partFileType;
			pstmt.setString(1,type);
			pstmt.setString(2,line);
			pstmt.addBatch();//pstmt.execute();
			//logger.debug("Inserteing -> type=["+type+"] - line=["+line+"]");
		}
		int[] updateCounts = pstmt.executeBatch();
		logger.debug("executeBatch=["+updateCounts+"]");
		
		dc.getConnection().commit();
		pstmt.close();
		logger.debug("FINISHED insert -> type=["+type+"]");

		moveFile(fileCSV);
	}
	
	
	private void moveFile(File fileCSV) throws IOException {
		
		String	fileSeparator = System.getProperty("path.separator");
		if (":".equals(fileSeparator)) 								fileSeparator = "/"; else	
		if ("Linux".equals(System.getProperty("os.name")))  		fileSeparator = "/"; else
		if ( System.getProperty("os.name").indexOf("Windows")>-1) 	fileSeparator = "\\";
		
		String[] 				  fileAbsolutePathParts = Util.filePartsName(fileCSV);
		String partFileName 	= fileAbsolutePathParts[fileAbsolutePathParts.length-1];
		String partFileType 	= fileAbsolutePathParts[fileAbsolutePathParts.length-2];
		String partFileSource 	= fileAbsolutePathParts[fileAbsolutePathParts.length-3];

		String	folderProcessed =  System.getProperty("datalake.folder.home");
		if ("full".contentEquals(tipoCarga) )
			folderProcessed = folderProcessed + System.getProperty("datalake.folder.csv.load.full.old");
		else
			folderProcessed = folderProcessed + System.getProperty("datalake.folder.csv.load.incremental.old");

		String folderProcessedThisFile = folderProcessed + fileSeparator + partFileSource + fileSeparator + partFileType;
		
		File fileFolderProcessedThisFile = new File(folderProcessedThisFile);
		
		Path pathProcessed = Paths.get(fileFolderProcessedThisFile.getAbsolutePath());
		
		if (!fileFolderProcessedThisFile.exists()) {
			logger.debug("Criando pasta para arquivo processado=["+fileFolderProcessedThisFile+"]");
			Files.createDirectories(  pathProcessed); 
		}
				
		String fromFileName = fileCSV.getAbsolutePath();
		String toFileName   = folderProcessedThisFile + fileSeparator + partFileName;
		
		Path source = Paths.get(fromFileName);
	    Path target = Paths.get(toFileName);

	    logger.debug("Movendo ["+source+"] para ["+target+"]");
	    Files.move(source, target);
	}


	public void closeConnection() throws Exception {
		dc.closeConnection();
	}
}
