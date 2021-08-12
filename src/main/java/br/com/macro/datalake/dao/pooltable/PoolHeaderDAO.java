package br.com.macro.datalake.dao.pooltable;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.macro.datalake.dao.TableDAO;

public class PoolHeaderDAO extends TableDAO {

	private final static Logger logger = Logger.getLogger(PoolHeaderDAO.class);
	
	public PoolHeaderDAO() throws Exception {
		
		this.tableName = "poolheader";

		this.createTable = 
		"CREATE TABLE IF NOT EXISTS "+tableName+" ("
		+ "  type varchar (400) " 
		+ "	,created timestamp WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP "
		+ "	,line varchar (4000) " 
		+ ")";

		//"INSERT INTO " +tableName +" (type,created,line) VALUES " + "('shinobi.atendimento', '2021-02-26 17:20:51.641', 'CODIGO;OPERADOR;NOME;CPF/CNPJ;TELEFONE 1;TELEFONE 2;TELEFONE 3;TELEFONE 4; TELEFONE 5;CIDADE;UF;REPRESENTANTE LEGAL;TIPO DE ATENDIMENTO;MOTIVO DE ATENDIMENTO;MAILING;CAMPANHA;DATA DE CADASTRO;HORA DE CADASTRO;DATA_CADASTRO_LEAD;EVIDENCIA;OBSERVACAO;TIPO_DE_AGENDAMENTO;DATA_DE_RETORNO\n' )";
		this.inserRecord = 
		"INSERT INTO " +tableName +" (type,line) VALUES " + "('shinobi.atendimento', 'CODIGO;OPERADOR;NOME;CPF/CNPJ;TELEFONE 1;TELEFONE 2;TELEFONE 3;TELEFONE 4; TELEFONE 5;CIDADE;UF;REPRESENTANTE LEGAL;TIPO DE ATENDIMENTO;MOTIVO DE ATENDIMENTO;MAILING;CAMPANHA;DATA DE CADASTRO;HORA DE CADASTRO;DATA_CADASTRO_LEAD;EVIDENCIA;OBSERVACAO;TIPO_DE_AGENDAMENTO;DATA_DE_RETORNO\n' )";
		
		this.inserStmt = "INSERT INTO " +tableName +" (type,line) VALUES (?,?)";
		
		this.deleteTypes = "DELETE FROM "+ tableName + " where type=?";

		this.selectAll = "SELECT type,created,line FROM "+ tableName;

		this.dropTable = "DROP TABLE IF EXISTS "+ tableName;
		
	}
	
	public void insert2PoolHeader(String type, String[] lines) throws IOException, SQLException {
		String headerLine = lines[0];

		logger.info("Apagando PoolHeader -> type=["+type+"]");
		pstmt = dbc.getConnection().prepareStatement( this.deleteTypes);
		pstmt.setString(1,type);
		pstmt.execute();
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();

		logger.info("Inserindo PoolHeader -> type=["+type+"] - Line=["+headerLine+"]");
		pstmt = dbc.getConnection().prepareStatement( this.inserStmt);
		pstmt.setString(1,type);
		pstmt.setString(2,headerLine);
		pstmt.execute();
		if (!dbc.getConnection().getAutoCommit()) dbc.getConnection().commit();

		pstmt.close();
	}	
	
}
