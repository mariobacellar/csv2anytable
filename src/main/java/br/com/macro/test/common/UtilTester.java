package br.com.macro.test.common;

import br.com.macro.datalake.common.ConfigProperties;
import br.com.macro.datalake.common.Util;

public class UtilTester {


	public static void main(String[] args) {
		try {

			ConfigProperties.getInstance();
			
//			String tableName = "pablo";
//			String header = "CODIGO;OPERADOR;NOME;CPF/CNPJ;TELEFONE 1;TELEFONE 2;TELEFONE 3;TELEFONE 4; TELEFONE 5;CIDADE;UF;REPRESENTANTE LEGAL;TIPO DE ATENDIMENTO;MOTIVO DE ATENDIMENTO;MAILING;CAMPANHA;DATA DE CADASTRO;HORA DE CADASTRO;DATA_CADASTRO_LEAD;EVIDENCIA;OBSERVACAO;TIPO_DE_AGENDAMENTO;DATA_DE_RETORNO";
			
//			String sqlCreate = Util.getCreateTabeleSQLFromCSVHeader(tableName,header);
//			System.out.println("sqlCreate = ["+sqlCreate+"]");
			
//			String sqlDropCreate = Util.getDropCreateTabeleSQL(tableName,header);
//			System.out.println("sqlDropCreate = ["+sqlDropCreate+"]");
			
//			String sqlInsert = Util.getInsertStatementaSQL(tableName, header);
//			System.out.println("sqlInsert = ["+sqlInsert+"]");

//			File fileCSV = new File("/home/mario/Downloads/tableau/arquivos/csv/carga/full/tvglobo.RELATORIO_ATENDIMENTOS_11_03_2021_11_21_13.csv");
//			Util.moveFile(fileCSV);

//			File fileCSV = new File("/home/mario/Downloads/tableau/csv/carga/full/shinobi/atendimento/RELATORIO_ATENDIMENTOS_11_03_2021_11_21_13.csv");
//			String[] lines = Util.doReadLines(fileCSV);
//			for (String line : lines) {
//				System.out.println("lines = ["+line+"]");
//			}
			
//			 Util.buildAppFolderTree(); 
			
//			String   filename = "/home/mario/Downloads/tableau/arquivos/csv/carga/full/shinobi.atendimento.RELATORIO_ATENDIMENTOS_11_03_2021_11_21_13";
//			String[] fileParts = Util.getOrigenTypeFile(filename) ;
//			for (String part : fileParts) {
//				System.out.println("part = ["+part+"]");
//			}

//			int rst[] = new int[] {1,1,1,177777,1,0}; 
//			int countErro= Util.count(rst,0);
//			int countOk  = Util.count(rst,1);
//			System.out.println("countErro = ["+countErro+"]");
//			System.out.println("countOk   = ["+countOk+"]");
			
			
			
//			String header1 = "COD. DE IGO;OPERADOR;NOME;(CPF/CNPJ);TELEFONE 1;TELEFONE 2;TELEFONE 3;TELEFONE 4; TELEFONE 5;CIDADE;UF;REPRESENTANTE LEGAL;TIPO DE ATENDIMENTO;MOTIVO DE ATENDIMENTO;MAILING;CAMPANHA;DATA DE CADASTRO;HORA DE CADASTRO;DAT. CAD. LEAD;EVIDENCIA;OBSERVACAO;TIPO_DE_AGENDAMENTO;DATA_DE_RETORNO";
//			System.out.println("header1= ["+header1+"]");
//
//			String header2 = Util.fixFieldName(header1);
//			System.out.println("header2= ["+header2+"]");
//
//			String tableName = Util.fixTableName("shinobi.atendimento");
//			System.out.println("tableName= ["+tableName+"]");
			
//			ArrayList<String> 
//			keys = new ArrayList<String>();
//			keys.add("log4j.rootLogger");
//			keys.add("datalake.probe.interval.segundos");
//			keys.add("datalake.probe.status");
//			keys.add("datalake.probe.folder.subnivel");
//			keys.add("datalake.folder.home");
//			keys.add("datalake.folder.csv.load.full.new");
//			keys.add("datalake.folder.csv.load.full.old");
//			keys.add("datalake.folder.csv.load.incremental.new");
//			keys.add("datalake.folder.csv.load.incremental.old");
//			
//			for (String val : keys)
//				System.getProperty("key=["+val+"]");
			
			
			String   filename = "C:\\Users\\Aluga\\Downloads\\csv\\carga\\full\\shinobi.atendimento.RELATORIO_ATENDIMENTOS_11_03_2021_11_21_13";
			String[] fileParts = Util.getOrigenTypeFile(filename) ;
			for (String part : fileParts) {
				System.out.println("part = ["+part+"]");
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}

