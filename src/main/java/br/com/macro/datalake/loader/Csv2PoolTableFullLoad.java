package br.com.macro.datalake.loader;

import java.io.File;

import org.apache.log4j.Logger;

import br.com.macro.datalake.common.Util;
import br.com.macro.datalake.dao.pooltable.PoolCtrlDAO;
import br.com.macro.datalake.dao.pooltable.PoolHeaderDAO;
import br.com.macro.datalake.dao.pooltable.PoolTableDAO;

public class Csv2PoolTableFullLoad extends Loader {

	private final static Logger logger = Logger.getLogger(Csv2PoolTableFullLoad.class);

	private PoolCtrlDAO  	ctrlDAO;
	private PoolHeaderDAO   headerDAO;
	private PoolTableDAO	tableDAO;
	
	public Csv2PoolTableFullLoad() throws Exception {
		super();
		
		this.ctrlDAO   = new PoolCtrlDAO();
		this.ctrlDAO.ckTable();
		
		this.headerDAO = new PoolHeaderDAO();
		this.headerDAO.ckTable();
		
		this.tableDAO  = new PoolTableDAO();
		this.tableDAO.ckTable();
	}
	

	@Override
	protected void wanderSubfolders(File[] ltFolders) throws Exception {
		logger.info("START WANDER SUBFOLFERS");
		for (File folder : ltFolders) {
			
			String folderNivel1 = folder.getAbsolutePath();
			logger.info("fPasta Nivel 1 ["+folderNivel1+"]");
		
			File[] subfolders = Util.getSubfolders(folderNivel1);
			for (File folderNivel2 : subfolders) {
				
				logger.info("Pasta Nivel 2 ["+folderNivel2+"]");
				for (File fileCSV : Util.getFiles( folderNivel2.getAbsolutePath() ) ) {

					logger.debug("Arquivo CSV ["+fileCSV.getAbsolutePath()+"]");
					
					String[] 	lines 			= Util.doReadLines(fileCSV);
					String[] 	parts 			= Util.filePartsName(fileCSV);					
					String 		partFileName 	= parts[parts.length-1];
					String		partFileType 	= parts[parts.length-2];
					String		partFileSource 	= parts[parts.length-3];
					String 		type			= partFileSource + "." + partFileType; 
					
					logger.info("START - Origem=["+partFileSource+"] Tipo=["+partFileType+"] Nome=["+partFileName+"]");
				 
					ctrlDAO  .insert2PoolCtrl  (type, "Dados em atualização");
					headerDAO.insert2PoolHeader(type, lines);
					tableDAO .insert2PoolTable (type, lines);
					
					Util.moveFile(fileCSV);
					
					ctrlDAO.insert2PoolCtrl(type, "Dados atualizadas");
					logger.info("FINISHED - Origem=["+partFileSource+"] Tipo=["+partFileType+"] Nome=["+partFileName+"]");
				}
			}				
		}
		logger.info("STOPPED WANDER SUBFOLFERS");
	}

	@Override
	protected void wanderStraightFolder(File[] lfFiles) throws Exception {
		
		logger.info("START WANDER STRAIGHT FOLDER");	
		for (File fileCSV : lfFiles ) {

			logger.debug("Arquivo CSV ["+fileCSV.getAbsolutePath()+"]");
			
			String[] 	lines 	= Util.doReadLines(fileCSV);
			String[] 	parts	= Util.getOrigenTypeFile(fileCSV) ;
			
			String		partFileSource 	= parts[0];
			String		partFileType 	= parts[1];
			String 		partFileName 	= parts[2];
			String 		type			= partFileSource + "." + partFileType; 
			
			logger.info("START - Origem=["+partFileSource+"] Tipo=["+partFileType+"] Nome=["+partFileName+"] - Informações em atualização");

			ctrlDAO  .insert2PoolCtrl  (type, "Dados em atualização");
			headerDAO.insert2PoolHeader(type, lines);
			tableDAO .insert2PoolTable (type, lines);
			
			Util.moveFile(fileCSV);
			
			ctrlDAO.insert2PoolCtrl(type, "Dados atualizadas");
			logger.info("FINISHED - Origem=["+partFileSource+"] Tipo=["+partFileType+"] Nome=["+partFileName+"]");

		}
		logger.info("STOPPED WANDER STRAIGHT FOLDER");	
	}
}
