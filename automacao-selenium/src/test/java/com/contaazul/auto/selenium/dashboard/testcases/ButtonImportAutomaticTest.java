package com.contaazul.auto.selenium.dashboard.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.dashboard.pages.DashboardPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateBankAccountPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;

public class ButtonImportAutomaticTest extends SeleniumTest {

	@Test
	public void buttonImportAutomaticTest() {

		CreateBankAccountPage createBankAccountPage = getPaginas().getPaginaAdicionarContaBancaria( getWebDriver() );
		ImportBankExtractPage importBankExtractPage = getPaginas().getImportBankExtractPage( getWebDriver() );
		DashboardPage dashboardPage = getPaginas().getDashboardPage( getWebDriver() );
		NotificationAssistant notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"ButtonImportAutomaticTest@contaazul.com", "12345" );

		dashboardPage.importExtractManual();
		importBankExtractPage.importExtractOfx( "OFX_ContaBancaria.ofx" );
		checkPoint( "Mensagem verificado com sucesso ",
				"Extrato bancário importado com sucesso!",
				getAssistants().getNotificationAssistant( getWebDriver()
						).getAlertMessageText() );
		importBankExtractPage.closeRegister();
		notificationAssistant.waitMessageDismiss();
		step( "clica no botão de importar manualmente e faz a importação" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		dashboardPage.btnConfigureFooter();
		createBankAccountPage.doNotConfigure();
		createBankAccountPage.save();
		checkPoint( "Mensagem verificado com sucesso ", "Registro alterado com sucesso!",
				notificationAssistant.getAlertMessageText() );
		notificationAssistant.waitMessageDismiss();
		step( "clica em configurar extrato no rodapé e cria conta sem extrato automatico " );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Movimentações", "Importar Extrato" );
		listingAssistant.checkAllItems();
		importBankExtractPage.selectAction( "Excluir" );
		checkPoint( "Mensagem verificado com sucesso ", "Lançamento(s) removido(s) com sucesso",
				notificationAssistant.getAlertMessageText() );
		notificationAssistant.waitMessageDismiss();
		step( "Exclui os registros " );

		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
		step( "Faz logout" );

	}
}
