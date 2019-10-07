package com.contaazul.auto.selenium.dashboard.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.DeleteAssistant;
import com.contaazul.auto.selenium.dashboard.pages.DashboardBankAccountPage;
import com.contaazul.auto.selenium.dashboard.pages.DashboardPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.NewImportExtractPage;

public class CreateAccountImportExtractDashboardTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void createAccountImportExtractDashboardTest(String nomeBanco, String arquivo, String accountName,
			String dataSaldo, String editNameAccount, String nrAgencia, String nrAccount, String digit) {

		DashboardBankAccountPage dashboardBankAccountPage = getPaginas().getDashboardBankAccountPage( getWebDriver() );
		DashboardPage dashboardPage = getPaginas().getDashboardPage( getWebDriver() );
		ImportBankExtractPage importBankExtractPage = getPaginas().getImportBankExtractPage( getWebDriver() );
		NewImportExtractPage newImportExtractPage = getPaginas().getNewImportExtractPage( getWebDriver() );
		DeleteAssistant deleteAssistant = getAssistants().getDeleteAssistant( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"CreateAccountImportExtractDashboardTest@contaazul.com",
				"12345" );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Movimentações",
				"Importar Extrato" );
		deleteAssistant.deleteAllFinancialStatements();
		step( "exclui lançamentos cadastrados " );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Organizar", "Contas bancárias" );
		deleteAssistant.deleteAllBankAccounts( editNameAccount );
		step( "exlcui contas bancárias cadatradas" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Visão Geral" );
		sleep( VERY_LONG );
		dashboardPage.buttonAddBankAccount();
		dashboardBankAccountPage.selectBank( nomeBanco );
		dashboardBankAccountPage.setNameBankAccountDash( accountName );
		dashboardBankAccountPage.btnSave();
		step( "Cria conta bancária" );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		dashboardPage.clickImportExtract();
		newImportExtractPage.selectFileImport( arquivo );
		newImportExtractPage.clickImportExtract();
		checkPoint( "Mensagem verificado com sucesso ",
				"Extrato bancário importado com sucesso!",
				getAssistants().getNotificationAssistant( getWebDriver()
						).getAlertMessageText() );
		importBankExtractPage.closeRegister();
		step( "importar extrato" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Visão Geral" );
		dashboardPage.openDropDownImportExtract();
		dashboardPage.clickEditAccount();
		dashboardBankAccountPage.setNameBankAccountDash( editNameAccount );
		dashboardBankAccountPage.setNameAgency( nrAgencia );
		dashboardBankAccountPage.setBankAccount( nrAccount );
		dashboardBankAccountPage.setDigit( digit );
		dashboardBankAccountPage.setOpeningBalance( "100,25" );
		dashboardBankAccountPage.setDate( dataSaldo );
		dashboardBankAccountPage.btnSave();
		step( "Volta para o dashboard e edita conta" );

		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
		step( "Faz logout" );

	}

}
