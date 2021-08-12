package br.com.macro.datalake.common;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigProperties {

	private final static Logger logger = Logger.getLogger(ConfigProperties.class);
	
	private static ConfigProperties instance;
	
	private ConfigProperties() throws Exception {
		 init();
	}
	
	public static ConfigProperties getInstance() throws Exception {
		if (instance==null) instance = new ConfigProperties();
		return instance;
	}
	
	private void init() throws Exception {
		
		Properties 
		appProps = new Properties(System.getProperties());
		appProps.load(  Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties") );

		substPropertiesByEnvironmentVariables(appProps);

		Properties sysprops = System.getProperties();
		Enumeration<Object> keys = appProps.keys();
		while (keys.hasMoreElements()) {
		    String key   = (String)keys.nextElement();
		    String value = (String)appProps.get(key);
		    sysprops.setProperty(key, value);
		}
		System.setProperties(sysprops);
		
		printSystemProps();
	}

	public void substPropertiesByEnvironmentVariables(Properties appProps)  throws Exception {
		
		
		//////////////////////////////////////////////////////////////////////////////
		// -DLOG_LEVEL = [INFO, DEBUG,...]
		//////////////////////////////////////////////////////////////////////////////
		String logLevel = System.getProperty("LOG_LEVEL");
		String logLevelCompl = ", stdout, file"; //DEBUG, stdout, file; 
		
		ArrayList<String> 
		ltLlogLevels = new ArrayList<String>();
		ltLlogLevels.add("ALL"); ltLlogLevels.add("DEBUG"); ltLlogLevels.add("INFO");
		ltLlogLevels.add("WARN"); ltLlogLevels.add("ERROR"); ltLlogLevels.add("FATAL");
		ltLlogLevels.add("OFF"); ltLlogLevels.add("TRACE");
		
		if (logLevel!=null && !"".equals(logLevel.trim()) ) {
			logLevel = logLevel.trim().toUpperCase();
			if (!ltLlogLevels.contains(logLevel)) {
				System.out.println("Parametro LOG_LEVEL inválido!. Informe: ALL, DEBUG, INFO, WARN, ERROR, FATAL, OFF ou TRACE. Ex: -DLOG_LEVEL=INFO");
				logLevel="INFO";
			}
			appProps.setProperty("log4j.rootLogger", logLevel+logLevelCompl);
		}
		
		//////////////////////////////////////////////////////////////////////////////
		// Intervalo para pausa
		//////////////////////////////////////////////////////////////////////////////
		String intervalSegundos = System.getProperty("INTERVAL");
		if (intervalSegundos!=null && !"".equals(intervalSegundos.trim())) {
			try {
				int segundos = Integer.parseInt(intervalSegundos); 
				appProps.setProperty("datalake.probe.interval.segundos",  ""+segundos);
			} catch (Exception e) {
				throw new Exception ("Parametro INTERVAL (segundos) inválido!. Informe um número. Ex: -DINTERVAL=5");
			}
		}

		//////////////////////////////////////////////////////////////////////////////
		// Inicia a aplicação com ou sem loop no monitoramento dos arquivos
		//////////////////////////////////////////////////////////////////////////////
		String loop = System.getProperty("LOOP");
		if (loop!=null && !"".equals(loop.trim())) {

			if ("on".equals(loop.trim()) || "off".equals(loop.trim()))
				appProps.setProperty("datalake.probe.status",  loop);
			else
				throw new Exception ("Parametro LOOP inválido!. Informe: 'on' ou 'off'. Ex: -DLOOP=off");
		}

		//////////////////////////////////////////////////////////////////////////////
		// Informa se deseja utilizar a estrututra de pastas ou gravar o csv em um unico folder
		//////////////////////////////////////////////////////////////////////////////
		String subnivel = System.getProperty("FOLDER_UNICO");
		if (subnivel!=null && !"".equals(subnivel)) {
			try {
				int nivel = Integer.parseInt(subnivel); 
				appProps.setProperty("datalake.probe.folder.subnivel",  ""+nivel);
			} catch (Exception e) {
				throw new Exception ("Parametro FOLDER_UNICO (0=sim) inválido!. Informe um número. Ex: -DFOLDER_UNICO=0");
			}
		}
		
		//////////////////////////////////////////////////////////////////////////////
		// Informa a pasta origem
		//////////////////////////////////////////////////////////////////////////////
		String home = System.getProperty("FOLDER_HOME");
		if (home!=null && !"".equals(home)) 
			appProps.setProperty("datalake.folder.home",  home);
		
		//////////////////////////////////////////////////////////////////////////////
		// Informa se deseja utilizar a estrututra de pastas ou gravar o csv em um unico folder
		//////////////////////////////////////////////////////////////////////////////
		String fullNew = System.getProperty("FOLDER_FULL_NEW");
		if (fullNew!=null && !"".equals(fullNew)) 
			appProps.setProperty("datalake.folder.csv.load.full.new",  fullNew);

		String fullOld = System.getProperty("FOLDER_FULL_OLD");
		if (fullOld!=null && !"".equals(fullOld)) 
			appProps.setProperty("datalake.folder.csv.load.full.old",  fullOld);

		String incNew = System.getProperty("FOLDER_INCREMENTAL_NEW");
		if (incNew!=null && !"".equals(incNew)) 
			appProps.setProperty("datalake.folder.csv.load.incremental.new",  incNew);

		String incOld = System.getProperty("FOLDER_INCREMENTAL_OLD");
		if (incOld!=null && !"".equals(incOld)) 
			appProps.setProperty("datalake.folder.csv.load.incremental.old",  incOld);
		
		String origens = System.getProperty("ORIGENS");
		if (origens!=null && "NO".equals(origens)) 
			appProps.setProperty("datalake.folder.origens",  "");
		
		String types = System.getProperty("TIPOS");
		if (types!=null && "NO".equals(types)) 
			appProps.setProperty("datalake.folder.types",  "");
	}
	

	public void reload() throws Exception {
		init();
	}
 
	private void printSystemProps() {
		Properties p = System.getProperties();
		Enumeration<Object> keys = p.keys();
		logger.trace("*** Printing System Properties ***");
		while (keys.hasMoreElements()) {
		    String key   = (String)keys.nextElement();
		    String value = (String)p.get(key);
		    logger.trace(key + ": " + value);
		}
		logger.trace("*** **** ***");
	}
}
