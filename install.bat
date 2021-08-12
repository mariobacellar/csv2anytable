cls

echo "Rodando Datalake"

set PATH=.;C:\datalake\jre1.8.0_202\bin;%PATH%
set CLASSPATH=.;datalake-1.0.0.jar
SET JAVA_HOME=C:\datalake\jre1.8.0_202


rem Configura o nível de log da aplicação. Os valores possíveis são:
rem OFF (most specific, no logging)
rem FATAL (most specific, little data)
rem ERROR
rem WARN
rem INFO
rem DEBUG
rem TRACE (least specific, a lot of data)
rem ALL (leas
SET LOG_LEVEL=INFO


rem  Se o parametro LOOP=on, esse parametro (INTERVAL) Informa o tempo (em segundos) que a aplicação deve pausar o loop que verifica se tem arquivo CSV para ser enviado para a base de dados.
SET INTERVAL=5

rem LOOP=on, indica que a aplicação fica em loop, verificando se tem novo arquivo CSV gravado na hierarquia de pastas para ser enviado para o banco de dados
rem LOOP=off, indica que a aplicação não executará em loop. A aplicação irá verificar, somente uma vez se existe CSV  para ser enviado para o banco de dados.
SET LOOP=off

rem Informa se a aplicação irá ler o CSV da estrututra de arvore ou de uma unica pasta
rem Se o valor = '1' a aplicação irá monitorar arquivos CSV gravados em 2 níveis a partir da pasta  <datalake.folder.home>
rem Ex: <datalake.folder.home>/<datalake.folder.csv.load.full>/shinobi/atendimento 
rem Se o valor = '0' a aplicação irá monitorar arquivos CSV gravados  <datalake.folder.csv.load.full.new>
rem Nesse caso o arquivo precisa ter a seguinte nomenclarura <origem>_<tipo>_?????????.csv . Ex: shinobi_atendimento_RELATORIO_2021_01_01.csv
rem Onde: 
rem - <origem> = shinobi, facebook, ....
rem - <tipo> = atendimento, venda, ligacao, ...
rem - ????????? = qualquer coisa
rem - .csv = para identificar que o arquivo é do tipo CSV
SET FOLDER_UNICO=0

rem Pasta raíz 
SET FOLDER_HOME=%USERPROFILE%\Downloads

rem Pasta que irá receber os CSV, dentro da FOLDER_HOME
SET FOLDER_FULL_NEW=\arquivos_full 

rem Pasta que irá receber os CSV processados, dentro da FOLDER_HOME
SET FOLDER_FULL_OLD=\arquivos_full_velhos 

rem Pasta que irá receber os CSV, dentro da FOLDER_HOME
SET FOLDER_INCREMENTAL_NEW=\arquivos_inc 

rem Pasta que irá receber os CSV processados, dentro da FOLDER_HOME
SET FOLDER_INCREMENTAL_OLD=\arquivos_inc_old 

rem Tipos dos arquivos. Só deve ser informado quando a FOLDER_UNICO=1. Ex: ORIGENS=shinobi;facebook;macro;googleads;algar;americanet;sky;totalben;ituran;athena;hive;salesforce
SET ORIGENS=NO 

rem Tipos dos arquivos. Só deve ser informado quando a FOLDER_UNICO=1. Ex:  TIPOS=atendimento;ligacao;venda;pedido;campanha;localidade
SET TIPOS=NO 

rem OPTION=0 -> Cria arvore de pastas, se FOLDER_UNICO<>0
rem OPTION=1 -> Carrega CSV para as tabelas de pooling table
rem OPTION=2 -> Carrega CSV para respectiva tabela estruturada na base de dados configurada no application.properties (dentro do jar)
SET OPTION=0


SET RUN="C:\datalake\datalake-1.0.0.jar"
echo "RUN=%RUN%"

java -cp . -DLOG_LEVEL=%LOG_LEVEL% -DINTERVAL=%INTERVAL% -DLOOP=%LOOP% -DFOLDER_UNICO=%FOLDER_UNICO% -DFOLDER_HOME=%FOLDER_HOME% -DFOLDER_FULL_NEW=%FOLDER_FULL_NEW% -DFOLDER_FULL_OLD=%FOLDER_FULL_OLD% -DFOLDER_INCREMENTAL_NEW=%FOLDER_INCREMENTAL_NEW% -DFOLDER_INCREMENTAL_OLD=%FOLDER_INCREMENTAL_OLD% -DORIGENS=%ORIGENS% -DTIPOS=%TIPOS% -DOPTION=%OPTION% -jar %RUN%
