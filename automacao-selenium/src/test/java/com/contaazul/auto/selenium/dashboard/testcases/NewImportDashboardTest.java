package com.contaazul.auto.selenium.dashboard.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.dashboard.pages.DashboardPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceImportExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.NewImportExtractPage;

public class NewImportDashboardTest extends SeleniumTest {

	/**
	 * Teste de importação testando a nova importação pelo dashboard. Obs:Teste
	 * com conta bancária ja cadastrada.
	 */

	@Test(dataProvider = DATA_PROVIDER)
	public void newImportDashboardTest(String arquivo, String nomeBanco) {

		FinanceImportExtractPage financeImportExtractPage = getPaginas().getPaginaImportacaoExtrato( getWebDriver() );
		NewImportExtractPage newImportExtractPage = getPaginas().getNewImportExtractPage( getWebDriver() );
		DashboardPage dashboardPage = getPaginas().getDashboardPage( getWebDriver() );
		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );
		step( "faz login e " );

		getAssistants().getLoginAssistant( getWebDriver() ).login( "newImportDashboardTest@contaazul.com", "12345" );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );

		if (getNumberOfRegistrys( financeImportExtractPage ) > 0) {
			ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
			listingAssistant.checkAllItems();
			financeImportExtractPage.selecionarAcao( "Excluir" );
		}
		step( "Exclui importações caso tenha alguma" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		dashboardPage.clickImportExtract();
		step( "vai para o dashboard e clica em importar extrato manualmente" );

		newImportExtractPage.setNameAccountExtract( nomeBanco );
		newImportExtractPage.selectFileImport( arquivo );
		newImportExtractPage.clickImportExtract();
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		importBankExtract.closeRegister();
		step( "selecionar ofx e importar" );

		checkPoint( "nenhum arquivo encontrado", "2",
				financeImportExtractPage.getNumberReleases() );
		step( "importa e vai para os arquivos importados e validar" );

		importBankExtract.clickAllCheckBox();
		financeImportExtractPage.selecionarAcao( "Excluir" );
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
		step( "excluir arquivos e faze logout" );

	}

	private int getNumberOfRegistrys(FinanceImportExtractPage financeImportExtractPage) {
		try {
			return new Integer( financeImportExtractPage.getNumberReleases() );
		} catch (Exception e) {
			return 0;
		}
	}

}
