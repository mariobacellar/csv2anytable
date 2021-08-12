package br.com.macro.datalake;

import org.apache.log4j.Logger;

import br.com.macro.datalake.common.ConfigProperties;
import br.com.macro.datalake.common.Util;
import br.com.macro.datalake.loader.Csv2AnyTableFullLoad;
import br.com.macro.datalake.loader.Csv2PoolTableFullLoad;

public class App implements AppInterface{


	private final static Logger logger = Logger.getLogger(App.class);
	
	public App() {
	
	}

	
	public void perform(String[] args) {
		try {
			
			// Carrega os parâmetros da aplicação para a memoria
			ConfigProperties.getInstance();

			String strOption = System.getProperty("OPTION");
			strOption = (strOption==null || "".equals(strOption.trim()) ? "0" : strOption);
			
			int option = Integer.parseInt( strOption );
			
			switch (option) {
			
				case MENU_INIT_APP:
					logger.info("Performing MENU_INIT_APP - Criando pastas da aplicacao");

					// Cria as patas se não existirem
					try {
						Util.buildAppFolderTree();
						logger.info("Pastas criadas com sucesso");
					} catch (Exception e) {
						throw new Exception("Erro tentando criar pastas: MENU_INIT_APP");
					} 	
				break;
			
				case MENU_CSV_TO_POOLINGTABLES:
					logger.info("Performing MENU_CSV_TO_POOLINGTABLES - Carregando CSV para Pooling Tables");
					// Cria as patas se não existirem
					try {
						Util.buildAppFolderTree();
						logger.info("Pastas criadas com sucesso");
					} catch (Exception e) {
						throw new Exception("Erro tentando criar pastas: MENU_INIT_APP");
					} 	
					Csv2PoolTableFullLoad loaderPool = new Csv2PoolTableFullLoad();
					loaderPool.performLoop();
					logger.info("Performing MENU_CSV_TO_POOLINGTABLES - Pooling Tables carregadas com sucesso");
				break;
				
				case MENU_CSV_TO_ANYTABLES:
					logger.info("Performing MENU_CSV_TO_ANYTABLES - Carregando CSV para tabelas ESTRUTURADAS");	
					// Cria as patas se não existirem
					try {
						Util.buildAppFolderTree();
						logger.info("Pastas criadas com sucesso");
					} catch (Exception e) {
						throw new Exception("Erro tentando criar pastas: MENU_INIT_APP");
					} 	
					Csv2AnyTableFullLoad loaderAny = new Csv2AnyTableFullLoad();
					loaderAny.performLoop();
					logger.info("Performing MENU_CSV_TO_ANYTABLES - Tabelas ESTRUTURADAS carregadas com sucesso");
				break;
	
				case MENU_POOLINGTABLES_TO_ANYTABLES:
					logger.info("NOT IMPLEMENTED MENU_POOLINGTABLES_TO_ANYTABLES");
				break;
				
				case MENU_POOLINGTABLES_TO_CSV:
					logger.info("NOT IMPLEMENTED MENU_POOLINGTABLES_TO_CSV");
				break;
				
				case MENU_ANYTABLES_TO_CSV:
					logger.info("NOT IMPLEMENTED MENU_ANYTABLES_TO_CSV");
				break;
				
				default:
					logger.info("-DOPTION=0 (ou não inform esse parâmetro) para criar as pastas da aplicacao");
					logger.info("-DOPTION=1 para carregar CSV para as tabelas de POOLINGTABLE");
					logger.info("-DOPTION=2 para carregar CSV para as respectivas tabelas ESTRUTURADAS");
				break;
			}
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		try {
			App app = new App();
			app.perform(args);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
