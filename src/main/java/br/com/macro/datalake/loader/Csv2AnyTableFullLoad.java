package br.com.macro.datalake.loader;

import java.io.File;

import org.apache.log4j.Logger;

import br.com.macro.datalake.common.Util;
import br.com.macro.datalake.dao.any.AnyTableDAO;

public class Csv2AnyTableFullLoad extends Loader {

	private final static Logger logger = Logger.getLogger(Csv2AnyTableFullLoad.class);

	private AnyTableDAO anyTableDAO;

	public Csv2AnyTableFullLoad() throws Exception {
		super();
	}
	

	@Override
	protected void wanderSubfolders(File[] ltFolders) throws Exception {

		logger.info("START WANDER SUBFOLFERS");
		for (File folder : ltFolders) {
			
			String folderNivel1 = folder.getAbsolutePath();
			logger.info("Pasta Nivel 1=["+folderNivel1+"]");
		
			File[] subfolders = Util.getSubfolders(folderNivel1);
			for (File folderNivel2 : subfolders) {
				
				logger.info("Pasta Nivel 2=["+folderNivel2+"]");
				for (File fileCSV : Util.getFiles( folderNivel2.getAbsolutePath() ) ) {

					logger.info("Arquivo CSV ["+fileCSV.getAbsolutePath()+"]");
					
					String[] 	lines 			= Util.doReadLines(fileCSV);
					String 		header			= lines[0];
					String[] 	parts 			= Util.filePartsName(fileCSV);					
					String 		partFileName 	= parts[parts.length-1];
					String		partFileType 	= parts[parts.length-2];
					String		partFileSource 	= parts[parts.length-3];
					String 		type			= partFileSource + "." + partFileType; 
					
					logger.info("START - Origem=["+partFileSource+"] Tipo=["+partFileType+"] Nome=["+partFileName+"]");

					this.anyTableDAO   = new AnyTableDAO(type, header);
					
					//Apaga todos os registros e insere as linhas do CSV
					this.anyTableDAO.insertBatch(type,lines);
					
					Util.moveFile(fileCSV);
					logger.info("FINISHED - Origem=["+partFileSource+"] Tipo=["+partFileType+"] Nome=["+partFileName+"]");
				}
			}				
		}
		logger.info("STOPPED WANDER SUBFOLFERS");
	}
	
	@Override
	protected void wanderStraightFolder(File[] lfFiles) throws Exception {
		
		if (lfFiles==null) return;
		
		logger.info("START WANDER STRAIGHT FOLDER - lfFiles=["+lfFiles+"]");	
		for (File fileCSV : lfFiles ) {

			logger.debug("Arquivo CSV ["+fileCSV.getAbsolutePath()+"]");
			
			String[] 	lines 			= Util.doReadLines(fileCSV);
			String 		header			= lines[0];
			String[] 	parts 			= Util.getOrigenTypeFile(fileCSV) ;
			String		partFileSource 	= parts[0];
			String		partFileType 	= parts[1];
			String 		partFileName 	= parts[2];
			String 		type			= partFileSource + "." + partFileType; 
			
			logger.info("START - Origem=["+partFileSource+"] Tipo=["+partFileType+"] Nome=["+partFileName+"]");

			this.anyTableDAO   = new AnyTableDAO(type, header);
			
			//Apaga todos os registros e insere as linhas do CSV
			this.anyTableDAO.insertBatch(type,lines);
			
			Util.moveFile(fileCSV);
			logger.info("FINISHED - Origem=["+partFileSource+"] Tipo=["+partFileType+"] Nome=["+partFileName+"]");
		}
		logger.info("STOPPED WANDER STRAIGHT FOLDER");	
	}
}
