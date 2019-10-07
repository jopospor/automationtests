package com.contaazul.auto.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.testng.Reporter;
import org.testng.TestNGException;

import com.contaazul.auto.TestSession;

public abstract class DataSheet {

	protected String filePath;
	protected String fileName;
	protected String[] headers = new String[990];
	protected int currentRow = 0;
	protected List<HashMap<String, String>> testData = new LinkedList<HashMap<String, String>>();

	public abstract void importSheet(String filePathRelative,
			String sourceSheetName) throws Exception;

	public abstract void exportSheet(String filePathRelative);

	protected DataSheet() {
	}

	/**
	 * Construtor
	 * 
	 * @param filePathRelative
	 *            : caminho relativo do arquivo de dados
	 * @param fileName
	 *            ; nome do arquivo de dados
	 */
	public DataSheet(String filePathRelative, String fileName) {
		this.filePath = filePathRelative;
		this.fileName = fileName;
	}

	/**
	 * Retorna os títulos (primeira linha) de cada coluna de dados carregados
	 * 
	 * @return nomes dos cabeçalhos das colunas
	 */
	public String[] getHeaders() {
		return headers;
	}

	/*
	 * Funções de acesso direto aos dados
	 */
	public int getRowCount() {
		return testData.size();
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int i) {
		if (i >= 0 && i < getRowCount()) {
			currentRow = i;
		}
	}

	public boolean nextRow() {
		if (currentRow < getRowCount() - 1) {
			currentRow++;
			return true;
		} else {
			return false;
		}
	}

	protected void addRow(HashMap<String, String> hash) {
		try {
			cleanUpParams( hash );
		} catch (Exception e) {
			if (hash != null)
				Reporter.log( "AVISO: DataSheet falhou ao tentar limpar dados da linha:(" + hash.toString()
						+ "). A linha não foi adicionada aos dados de teste.", true );
			else
				Reporter.log(
						"AVISO: DataSheet falhou ao tentar limpar dados. A linha não foi adicionada aos dados de teste",
						true );
		}
		testData.add( hash );
	}

	public void setValue(String columnName, String value) {
		if (columnName != null && !columnName.isEmpty())
			try {
				testData.get( currentRow ).put( columnName, value );
			} catch (Exception e) {
				Reporter.log( "AVISO: DataSheet falhou ao setar valor. Coluna:" + columnName + " terá o valor nulo.",
						true );
			}
		else
			Reporter.log(
					"AVISO: DataSheet falhou ao setar valor. Nome da Coluna não pode ser em branco nem nulo.",
					true );
	}

	public String getValue(String columnName) {
		return testData.get( currentRow ).get( columnName );
	}

	public String getValueAtRow(String columnName, String rowName) {
		HashMap<String, String> hm;
		for (int i = 0; i < getRowCount(); i++) {
			hm = testData.get( i );
			try {
				if (hm.get( "#ID" ).matches( "#*" + rowName ))
					return hm.get( columnName );
			} catch (Exception e) {
				throw new TestNGException( "Não conseguiu retornar um valor para a coluna: " + columnName
						+ " e linha: " + rowName + ". Mensagem: " + e.getMessage(), e );
			}
		}
		return null;
	}

	/**
	 * Retorna os dados já carregados na DataSheet no formato exigido pelo
	 * TestNG
	 * 
	 * @return Array de array de Strings
	 */
	public String[][] getTestData() {
		String[][] data = new String[getRowCount()][headers.length];
		for (int i = 0; i < getRowCount(); i++)
			for (int j = 0; j < headers.length; j++)
				data[i][j] = testData.get( i ).get( headers[j] );
		return data;
	}

	/**
	 * Limpa os dados sendo importados do arquivo. Elimina ou trata caracteres
	 * inválidos, traduz os dados (que começarem com "#") para o locale da
	 * sessão de testes, converte as palavras chaves "BLANK" e "null" para o
	 * espaço em branco e valor nulo, substitui os dados envoltos em
	 * "< nome de um cabeçalho >" para o valor da coluna correspondente.
	 * 
	 * @param hash
	 *            Representa uma linha de dados
	 * @throws Exception
	 */
	private void cleanUpParams(HashMap<String, String> hash) {
		String value = null;
		String keyWord;
		for (String hashKey : hash.keySet()) {
			value = (hash.get( hashKey ) == null) ? "" : hash.get( hashKey );
			value = value.replaceFirst( "^\\*", "" ).trim();
			value = (value.toLowerCase().matches( "^blank$" )) ? "" : value;
			if (value.matches( "<{1}HOJE((\\+|-)[0-9]+)?>{1}" ))
				value = DateFormatUtil.format( value, DateFormatUtil.FULLDATE );
			while (value.matches( ".*<{1}[$?\\d,\\w,\\s]+>{1}.*" )) {
				keyWord = value.substring( value.indexOf( '<' ) + 1, value.indexOf( '>' ) );
				if (hash.containsKey( keyWord ))
					value = value.replaceFirst( "<{1}[$?\\d,\\w,\\s]+>{1}", hash.get( keyWord ) );
				else
					value = value.replaceFirst( "<{1}[$?\\d,\\w,\\s]+>{1}", "<Não existe a coluna '" + keyWord + "'>" );
			}
			if (!hashKey.startsWith( "#" ) && value.contains( "#" ))
				value = translate( value );
			value = (value.matches( "null" )) ? null : value;
			hash.put( hashKey, value );
		}
	}

	private String translate(String value) {
		int inicioCte = value.indexOf( "#" );
		if (value != null && inicioCte >= 0) {
			String pattern = "#{1}[A-Z_0-9]+";
			String auxSeparator = "--separatekeyword--";
			String rep = value.replaceFirst( pattern, auxSeparator );
			if (rep.matches( auxSeparator )) {
				return TestSession.getSession().getLocale().translate( value );
			} else if (!rep.contains( auxSeparator )) {
				Reporter.log( "AVISO, não pôde traduzir o valor: " + value, true );
				return value;
			} else {
				int firstKeyAt = rep.indexOf( auxSeparator );
				String prefix = value.substring( 0, firstKeyAt );
				String translated = "";
				if (firstKeyAt > 0)
					translated += prefix;
				int suffixStarts = firstKeyAt + auxSeparator.length();
				String suffix = rep.substring( suffixStarts );
				int keyLen = value.length() - prefix.length() - suffix.length();
				String kw = value.substring( firstKeyAt, firstKeyAt + keyLen );
				String getTranslation = TestSession.getSession().getLocale().translate( kw );
				translated += getTranslation + suffix;
				return translate( translated );
			}
		}
		return value;
	}
}
