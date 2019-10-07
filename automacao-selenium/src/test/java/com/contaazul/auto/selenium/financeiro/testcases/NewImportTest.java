package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.financeiro.pages.FinanceImportExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.NewImportExtractPage;

public class NewImportTest extends SeleniumTest {

	/**
	 * Teste de importação testando a nova importação pelo menu financeiro.
	 * Obs:Teste com conta bancária ja cadastrada.
	 */

	@Test(dataProvider = DATA_PROVIDER)
	public void newImportTest(String arquivo, String nomeBanco) {

		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );
		FinanceImportExtractPage financeImportExtractPage = getPaginas().getPaginaImportacaoExtrato( getWebDriver() );
		NewImportExtractPage newImportExtractPage = getPaginas().getNewImportExtractPage( getWebDriver() );
		step( "faz login, navega pelo menu financeiro e clica em importar extrato" );

		getAssistants().getLoginAssistant( getWebDriver() ).login( "newImportTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );

		if (getNumberOfRegistrys( financeImportExtractPage ) > 0) {
			ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
			listingAssistant.checkAllItems();
			financeImportExtractPage.selecionarAcao( "Excluir" );
		}
		step( "Método para excluir importações, caso possua alguma" );

		importBankExtract.btnNewExtractOfx();
		newImportExtractPage.setNameAccountExtract( nomeBanco );
		newImportExtractPage.selectFileImport( arquivo );
		newImportExtractPage.clickImportExtract();
		importBankExtract.closeRegister();
		step( "selecionar ofx e importar" );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );
		step( "ir para os arquivos importados" );

		checkPoint( "nenhum arquivo encontrado", "2",
				financeImportExtractPage.getNumberReleases() );
		step( "Valida as importações" );

		importBankExtract.clickAllCheckBox();
		financeImportExtractPage.selecionarAcao( "Excluir" );
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
		step( "exlui arquivos e faz logout" );

	}

	private int getNumberOfRegistrys(FinanceImportExtractPage financeImportExtractPage) {
		try {
			return new Integer( financeImportExtractPage.getNumberReleases() );
		} catch (Exception e) {
			return 0;
		}
	}
}
