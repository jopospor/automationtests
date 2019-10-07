package com.contaazul.auto.locale;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.TestNGException;

import com.contaazul.auto.data.ExcelDataSheet;

public class SessionLocale {

	protected String locale = "Pt_BR";
	protected List<String> enumLocales = new ArrayList<String>();
	protected ExcelDataSheet translationDataSheet;

	/**
	 * Construtor padrão.
	 * 
	 * @param localeCode
	 *            : String padronizada de locale ex: Pt_BR, Eng_US, deve existir
	 *            como um cabeçalho na planilha de tradução
	 */

	public SessionLocale(String localeCode) {
		if (localeCode != null && !localeCode.isEmpty())
			setLocaleCode( localeCode );
	}

	/**
	 * Ao setar um novo locale para a instância de SessionLocale, ele recarrega
	 * seus dados da planilha de tradução
	 * 
	 * @param localeCode
	 *            String padronizada de locale ex: Pt_BR, Eng_US, deve existir
	 *            como um cabeçalho na planilha de tradução
	 */
	public synchronized void setLocaleCode(String localeCode) {
		if (enumLocales.isEmpty() || enumLocales == null) {
			loadLocaleSpreadsheet( localeCode );
		}
		boolean match = false;
		for (String s : enumLocales) {
			if (s.matches( localeCode )) {
				match = true;
				break;
			}
		}
		if (match)
			locale = localeCode;
		else
			Reporter.log( "Código de local inválido. Utilizando default: "
					+ locale, true );
	}

	private synchronized void loadLocaleSpreadsheet(String localeCode) {

		if (localeCode != null && !localeCode.isEmpty())
			locale = localeCode;
		translationDataSheet = new ExcelDataSheet();
		try {
			translationDataSheet.importSheet(
					System.getProperty( "file.separator" ) + "src"
							+ System.getProperty( "file.separator" ) + "test"
							+ System.getProperty( "file.separator" )
							+ "resources"
							+ System.getProperty( "file.separator" ) + "testdata"
							+ System.getProperty( "file.separator" )
							+ "Locale_data.xls", "Planilha1" );

			// 2DO: acabar com a recursividade de props para poder carregar o
			// caminho pelo getSession()
		} catch (Exception e) {
			throw new TestNGException(
					"Erro ao construir planilha de idiomas (Locale). Certifique-se de manter a planilha excel de Locale no mesmo diretório de input dos dados de teste: \\src\\test\\resources\\testdata\\Locale_data.xls. Verifique também células em branco ou colunas fora do padrão.",
					e );
		}
		for (int i = 1; i < translationDataSheet.getHeaders().length; i++)
			enumLocales.add( translationDataSheet.getHeaders()[i] );
	}

	public String getLocaleCode() {
		return locale;
	}

	public String getLocaleLanguage() {
		return getLocaleStringToArray()[0];
	}

	public String getLocaleCountry() {
		return getLocaleStringToArray()[1];
	}

	private String[] getLocaleStringToArray() {
		String[] a = locale.split( "_" );
		for (int i = 0; i < a.length; i++)
			a[i] = a[i].toLowerCase();
		return a;
	}

	/**
	 * Traduz a palavra passada por parâmetro pelo seu correspondente na tabela
	 * de Locale. Usa o locale para o qual esta instância esteja configurada.
	 * 
	 * @param keyword
	 *            : palavra a traduzir
	 * @return String traduzida para o Locale setado
	 */
	public String translate(String keyword) {
		String initialValue = keyword;
		if (!keyword.isEmpty() && keyword != null) {
			keyword = translationDataSheet.getValueAtRow( this.getLocaleCode(),
					keyword );
			if (keyword == null)
				Assert.fail( "Erro ao traduzir string. keyword: "
						+ initialValue
						+ ". Verifique se esta keyword existe na tabela de tradução (Locale_data.xls)" );
			else
				return keyword;
		}
		return initialValue;
	}

}
