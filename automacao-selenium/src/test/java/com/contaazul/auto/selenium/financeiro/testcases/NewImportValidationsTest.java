package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.financeiro.pages.FinanceImportExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.NewImportExtractPage;

public class NewImportValidationsTest extends SeleniumTest {

	/*
	 * Teste de valição da nova importação, validando importações e link.Cria
	 * dados e exclui todos.
	 */

	@BeforeClass
	public void preparForTesting() {
		FinanceImportExtractPage financeImportExtractPage = getPaginas().getPaginaImportacaoExtrato( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login( "newImporValidationsTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );
		step( "faz login e navega pelo menu financeiro " );

		if (getNumberOfRegistrys( financeImportExtractPage ) > 0) {
			ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
			listingAssistant.checkAllItems();
			financeImportExtractPage.selecionarAcao( "Excluir" );
		}
		step( "Método para excluir importações, caso possua alguma" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void newImportValidationsTest(String arquivo, String result) {

		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );
		NewImportExtractPage newImportExtractPage = getPaginas().getNewImportExtractPage( getWebDriver() );
		NotificationAssistant notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );

		configureImport( arquivo, importBankExtract, newImportExtractPage );
		checkPoint( "mensagem de erro nao apareceu como o esperado",
				result,
				notificationAssistant.getAlertMessageText() );

	}

	@AfterClass
	public void logout() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();

	}

	private void configureImport(String arquivo, ImportBankExtractPage importBankExtract,
			NewImportExtractPage newImportExtractPage) {
		sleep( VERY_LONG );
		importBankExtract.btnNewExtractOfx();
		newImportExtractPage.selectFileImport( arquivo );
	}

	private int getNumberOfRegistrys(FinanceImportExtractPage financeImportExtractPage) {
		try {
			return new Integer( financeImportExtractPage.getNumberReleases() );
		} catch (Exception e) {
			return 0;
		}
	}

}
