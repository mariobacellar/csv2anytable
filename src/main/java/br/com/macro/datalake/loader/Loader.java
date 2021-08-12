package br.com.macro.datalake.loader;

import java.io.File;

import org.apache.log4j.Logger;

import br.com.macro.datalake.common.ConfigProperties;
import br.com.macro.datalake.common.DBConnector;
import br.com.macro.datalake.common.Util;

public abstract class Loader {


	private final static Logger logger = Logger.getLogger(Loader.class);

	protected  String datalakeFolderHome;
	protected String datalakeFolderCsvLoadFullNew;
	protected String datalakeFolderCsvLoadFullOld;
	protected boolean datalakeProbeFolderSubnivel;
	
	public Loader() throws Exception {
		
		ConfigProperties.getInstance().reload();
		
		this.datalakeFolderHome 			=                      System.getProperty("datalake.folder.home");
		this.datalakeFolderCsvLoadFullNew	= datalakeFolderHome + System.getProperty("datalake.folder.csv.load.full.new");
		this.datalakeFolderCsvLoadFullOld	= datalakeFolderHome + System.getProperty("datalake.folder.csv.load.full.old");
		this.datalakeProbeFolderSubnivel	=           "1".equals(System.getProperty("datalake.probe.folder.subnivel"));
		logger.info(" datalakeProbeFolderSubnivel.........= [" + datalakeProbeFolderSubnivel 	+ "]");
		logger.info(" datalakeFolderHome..................= [" + datalakeFolderHome 			+ "]");
		logger.info(" datalakeFolderCsvLoadFullNew........= [" + datalakeFolderCsvLoadFullNew 	+ "]");		
		logger.info(" datalakeFolderCsvLoadFullOld........= [" + datalakeFolderCsvLoadFullOld 	+ "]");
		logger.info(" user.home...........................= [" + System.getProperty("user.home") + "]");
		logger.info(" user.dir............................= [" + System.getProperty("user.dir")  + "]");
		logger.info(" user.name...........................= [" + System.getProperty("user.name") + "]");
	}
	

	public void performLoop() throws Exception {
		try {
			
			if (datalakeProbeFolderSubnivel) {
				logger.info("Aplicacao configurada (application.properties/datalake.probe.folder.subnivel) para ler CSV de sub pastas");
				turnOnBySubfolder();
			}else{
				logger.info("Aplicacao configurada (application.properties/datalake.probe.folder.subnivel) para ler CSV de uma unica pasta");
				turnOnByStraightfolder();
			}
			
			turnOff(0);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			turnOff(-1);
		}
	}

	
	private void turnOff(int  cause) throws Exception {
		if (cause==0) {
			logger.info("O Monitoramento foi interrompido normalmente");
			System.setProperty("datalake.probe.status", "off");
		}else {
			logger.info("O Monitoramento foi interrompido com erro");
			System.setProperty("datalake.probe.status", "off - desligado com erro");
			cause=-1;
		}
		DBConnector.getInstance().closeAll();
		System.exit(cause);
	}
	
	private boolean probe() {
		return "on".equals(System.getProperty("datalake.probe.status"));
	}


	private void turnOnBySubfolder() throws Exception {

		// Ignora o valor configurado no applicatin.properties somente na primeira vez que a aplicação roda
		// Para deixar a aplicação em loop, configura o parametro "datalake.probe.status=on" no arquivo applicatin.properties  
		System.setProperty("datalake.probe.status","on");
		
		while (probe()) {

			// Lista as pastas que receberão os CSVappHome
			File[] ltFolders = Util.getSubfolders(datalakeFolderCsvLoadFullNew);
			
			// Entra dentro de cada pasta para processar os CSV
			if (ltFolders != null && ltFolders.length > 0)
				wanderSubfolders(ltFolders);
			else {
				logger.warn("Não foram identificadas subpastas para carregar. Verifique o parâmetro 'datalake.probe.folder.subnivel' nos arquivo application.properties");
			}

			// Recarrega application.properties
			ConfigProperties.getInstance().reload();
			
			// Interrompe momentaneamente a aplicação
			int segundos = Integer.parseInt(System.getProperty("datalake.probe.interval.segundos"));
			logger.info("Aplicação pausada por ["+segundos+"] segundos");
			Thread.sleep(1000 * segundos);
		}
	}
	
	private void turnOnByStraightfolder() throws Exception {

		// Ignora o valor configurado no applicatin.properties somente na primeira vez que a aplicação roda
		// Para deixar a aplicação em loop, configura o parametro "datalake.probe.status=on" no arquivo applicatin.properties  
		System.setProperty("datalake.probe.status","on");
		
		while (probe()) {

			// Lista as pastas que receberão os CSVappHome
			logger.debug("datalakeFolderCsvLoadFullNew=["+datalakeFolderCsvLoadFullNew+"]");
			
			File[] lfFiles = Util.getFiles( datalakeFolderCsvLoadFullNew);
			
			wanderStraightFolder(lfFiles);

			// Recarrega application.properties
			ConfigProperties.getInstance().reload();
			
			// Interrompe momentaneamente a aplicação
			int segundos = Integer.parseInt(System.getProperty("datalake.probe.interval.segundos"));
			logger.debug("datalake.probe.interval.segundos=["+segundos+"]");
			Thread.sleep(1000 * segundos);
		}
	}

	protected abstract void wanderSubfolders(File[] ltFolders) throws Exception;
	
	protected abstract void wanderStraightFolder(File[] lfFiles) throws Exception;
 
}
