package br.com.macro.datalake.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Util {

	private final static Logger logger = Logger.getLogger(Util.class);
	
	public static int count(int[] array, int filter) {
	    int count = 0;
	    for(int elm: array) if(elm == filter) count++; 
	    return count;
	}

	
	public static String[] filePartsName(File fileCSV) {
		
		if (fileCSV==null) {
			String [] ret = new String[0]; ret[0]="";
			return ret;
		}
		logger.debug("fileCSV	= ["+fileCSV.toString()+"]");
		
		String fileSeparator = getFileSeparator();
		logger.debug("fileSeparator=["+fileSeparator+"]");
		
		String  fileAbsolutePath = fileCSV.getAbsolutePath();
		logger.debug("fileAbsolutePath	= ["+fileAbsolutePath+"]");
				
		String[]	fileAbsolutePathParts	= null;
		
		if ( "\\".equals(fileSeparator)) {
			logger.debug("Spliting string para Windows - fileSeparator=["+fileSeparator+"]");
			
			fileAbsolutePath= fileAbsolutePath.replace('\\', '#');
					
			 fileAbsolutePathParts	= fileAbsolutePath.split("#");
		 }else{
			 fileAbsolutePathParts	= fileAbsolutePath.split(fileSeparator);
		 }
		
		return fileAbsolutePathParts;
	}	
	public static File[] getSubfolders(String folderRoot) {
		File directory = new File(folderRoot);
		FileFilter directoryFileFilter = new FileFilter() {
			public boolean accept(File file) {
				boolean ret=file.isDirectory();
				return ret;
			}
		};
		File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
		return directoryListAsFile ;
	}	
	
	public static File[] getFiles(String folder) {
		File fileInFolder = new File(folder);
		FileFilter fileFileFilter = new FileFilter() {
			public boolean accept(File file) {
				return !file.isDirectory();
			}
		};
		File[] fileListAsFile = fileInFolder.listFiles(fileFileFilter);
		return fileListAsFile ;	
	}

	public static String fixTableName(String field) {
		String  ret = field.trim().toUpperCase();
		ret = ret.replaceAll("\\.", "_") ;
		ret= fixFieldName(ret);
		return ret;
	}

	public static String fixFieldName(String field) {
		String  ret = field.trim().toUpperCase();
		ret = ret.replaceAll("\\.", "") ;
		ret = ret.replaceAll("\\(", "" );
		ret = ret.replaceAll("\\)", "" );
		ret = ret.replaceAll(" ", "_");
		ret = ret.replaceAll("-", "_");
		ret = ret.replaceAll("/", "_");
		return ret;
	}
	
	
	public static String getCreateTabeleSQLFromCSVHeader(String tableName, String header) {
		
		String[] fields = header.split(";");
		
		StringBuffer  ret=  new StringBuffer("CREATE TABLE IF NOT EXISTS ").append( tableName ).append(" ( " );
		
		int countCol=0;
	
		for (String field : fields) {
		
			countCol++;

			field = fixFieldName(field);
					
			if (countCol==1)
				ret.append(field).append(" text");
			else
				ret.append(", ").append(field).append(" text");
		}
		ret.append(");");
		return ret.toString();
	}
	
	public static String getDropTabeleSQL(String tableName) {
		return "DROP TABLE IF EXISTS " + tableName + ";";
	}
	
	public static String getDropCreateTabeleSQL(String tableName, String header) {
		String drop  = getDropTabeleSQL(tableName);
		String create= getCreateTabeleSQLFromCSVHeader(tableName, header);
		return drop + "\n"+ create;
	}

	public static String getInsertStatementaSQL(String tableName, String header) {
		
		String[] fields = header.split(";");
		
		StringBuffer ret=  new StringBuffer("INSERT INTO ").append( tableName ).append(" (" );
		
		int countCol=0;
		
		for (String field : fields) {
			
			countCol++;

			field = fixFieldName(field);
			
			if (countCol==1)
				ret.append(field);
			else
				ret.append(", ").append(field);
		}
		ret.append(") VALUES (");
		countCol=0;
		for (String field : fields) {
			countCol++;
			field = "?";
			if (countCol==1)
				ret.append(field);
			else
				ret.append(", ").append(field);
		}
		ret.append(");");
		
		return ret.toString();
	}
	
	public static String getInsertStatementaSQLTest(String tableName, String header, String[] values) throws Exception {
		
		String[] fields = header.split(";");
		
		StringBuffer ret =  new StringBuffer("INSERT INTO ").append( tableName ).append(" (" );
		
		int countCol=0;
		
		for (String field : fields) {
			
			countCol++;
			
			field = fixFieldName(field);			

			if (countCol==1)
				ret.append(field);
			else
				ret.append(", ").append(field);
		}
		ret.append(") VALUES (");
		countCol=0;
		for (String value : values) {
			countCol++;
			if (countCol==1)
				ret.append("\'").append(value).append("\'");
			else
				ret.append(", ").append("\'").append(value).append("\'");
		}
		ret.append(");");
		return ret.toString();
	}
	
	public static String getFileSeparator() {
		String	ret = System.getProperty("path.separator");
		if (":".equals(ret))
			ret = "/"; 
		else	
		if ("Linux".equals(System.getProperty("os.name")))
			ret = "/"; 
		else
		if ( System.getProperty("os.name").indexOf("Windows")>-1) 	
			ret = "\\";		

		logger.debug("getFileSeparator() -> ["+ret+"]");
		return ret; 
	}
	
	public static void moveFile(File fileCSV) throws IOException {
		
		String	 fileSeparator = getFileSeparator();
		
		String[] fileAbsolutePathParts = filePartsName(fileCSV);
		String partFileName 	= fileAbsolutePathParts[fileAbsolutePathParts.length-1];
		String partFileType 	= fileAbsolutePathParts[fileAbsolutePathParts.length-2];
		String partFileSource 	= fileAbsolutePathParts[fileAbsolutePathParts.length-3];

		String	folderProcessed =  System.getProperty("datalake.folder.home");
		if ( fileCSV.getAbsolutePath().indexOf("full")>-1) 
			folderProcessed = folderProcessed + System.getProperty("datalake.folder.csv.load.full.old");
		else
			folderProcessed = folderProcessed + System.getProperty("datalake.folder.csv.load.incremental.old");
		
		String folderProcessedThisFile = folderProcessed + fileSeparator + partFileSource + fileSeparator + partFileType;

		boolean isStraightFolder = ( "0".equals(System.getProperty("datalake.probe.folder.subnivel") ) );
		if (isStraightFolder)
			folderProcessedThisFile = System.getProperty("datalake.folder.home") + System.getProperty("datalake.folder.csv.load.full.old");
		
		File   fileFolderProcessedThisFile = new File(folderProcessedThisFile);
		Path   pathProcessed = Paths.get(fileFolderProcessedThisFile.getAbsolutePath());

		
		if (!fileFolderProcessedThisFile.exists()) {
			Files.createDirectories(  pathProcessed); 
		}
				
		String fromFileName = fileCSV.getAbsolutePath();
		String toFileName   = folderProcessedThisFile + fileSeparator + partFileName;
		
		Path source = Paths.get(fromFileName);
	    Path target = Paths.get(toFileName);

	    try {
	    	Files.move(source, target,StandardCopyOption.REPLACE_EXISTING);
	    }catch(java.nio.file.FileAlreadyExistsException e) {
	    	logger.error("Arquivo ["+fromFileName+"] ja existe!",e);
	    }
	}
	
	public static String[] doReadLines(File fileCSV) throws Exception {
		ArrayList<String> lt = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileCSV))) {
			String line = br.readLine();
			while (line != null) {
				lt.add(line);
				line = br.readLine();
			}
		}
		String[] ret = lt.stream().toArray(String[]::new);
		return ret;
	}
	
	public static void buildAppFolder(String rootFolder, String nivel1, String nivel2) throws Exception {
		String	fileSeparator	= getFileSeparator();
		String folderName		= rootFolder + fileSeparator + nivel1 + fileSeparator + nivel2;
		File   folderFile		= new File(folderName);
		Path   folderPath		= Paths.get(folderFile.getAbsolutePath());
		if (!folderFile.exists())  Files.createDirectories(folderPath); 
		logger.info("Pasta criada ["+folderPath+"]");
	}
	
	public static void buildAppFolder(String rootFolder) throws Exception {
		String folderName		= rootFolder;
		File   folderFile		= new File(folderName);
		Path   folderPath		= Paths.get(folderFile.getAbsolutePath());
		if (!folderFile.exists())  Files.createDirectories(folderPath); 
		logger.info("Pasta criada ["+rootFolder+"]");
	}

	
	public static void buildAppFolderTree() throws Exception {
		String fileSeparator		= getFileSeparator();
		String rootFolders 			= System.getProperty("datalake.folder.home");
		String rootFoldersFull		= rootFolders + fileSeparator + System.getProperty("datalake.folder.csv.load.full.new");
		String rootFoldersFullProc	= rootFolders + fileSeparator + System.getProperty("datalake.folder.csv.load.full.old");
		String rootFoldersIncr		= rootFolders + fileSeparator + System.getProperty("datalake.folder.csv.load.incremental.new");
		String rootFoldersIncrProc	= rootFolders + fileSeparator + System.getProperty("datalake.folder.csv.load.incremental.old");

		buildAppFolder(rootFoldersFull);		
		buildAppFolder(rootFoldersFullProc);		
		
		buildAppFolder(rootFoldersIncr);		
		buildAppFolder(rootFoldersIncrProc);		

		if (!"0".equals(System.getProperty("datalake.probe.folder.subnivel")) ) {

			String[] origens = System.getProperty("datalake.folder.origens").split(";");
			String[] tipos   = System.getProperty("datalake.folder.types").split(";");

			for (String origen : origens) 
				for (String tipo : tipos) 
					buildAppFolder(rootFoldersFull, origen, tipo);

			for (String origen : origens) 
				for (String tipo : tipos) 
					buildAppFolder(rootFoldersIncr, origen, tipo);
		}

	}

	public static String[] getOrigenTypeFile(String filename) {
		return getOrigenTypeFile( new File(filename));
	}
	
	public static String[] getOrigenTypeFile(File file) {
		String[] partsFull			= filePartsName(file); 
		String   filename 			= partsFull[partsFull.length-1];
		String[] partsFileFullName = filename.split("\\.");
		return partsFileFullName ;
	}
	
}
