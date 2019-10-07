package com.contaazul.auto.selenium.dashboard.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.DeleteAssistant;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.dashboard.pages.DashboardPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.NewImportExtractPage;

public class AccountsBanksPaidReceivedTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void accountsBanksPaidReceivedTest(String valor, String expirationDateExpense,
			String expirationDate, String nomeDespesa, String contaBanco, String categoria, String nomeReceita,
			String banco, String arquivo) {

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"AccountsBanksPaidReceivedTest@contaazul.com", "12345" );

		DeleteAssistant deleteAssistant = getAssistants().getDeleteAssistant( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
		DashboardPage dashboardPage = getPaginas().getDashboardPage( getWebDriver() );
		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		ImportBankExtractPage importBankExtractPage = getPaginas().getImportBankExtractPage( getWebDriver() );
		NewImportExtractPage newImportExtractPage = getPaginas().getNewImportExtractPage( getWebDriver() );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Extrato" );
		deleteAssistant.deleteAllFinancialStatements();
		step( "exclui lançamentos cadastrados " );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		dashboardPage.buttonAddExpense();
		createIncomePage.setIncomeName( nomeDespesa );
		createIncomePage.setValue( valor );
		createIncomePage.setBankAccount( contaBanco );
		createIncomePage.setIncomeCategory( categoria );
		createIncomePage.setExpirationDate( expirationDateExpense );
		createIncomePage.clickAddIncomeButton();
		checkPoint( "Não encontrou mensagem de confirmação de despesa criada com sucesso",
				"Despesa 'Teste Despesa', de R$ " + valor + ",",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		step( "Adicionar uma despesa pelo dashboard" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		dashboardPage.buttonAddRevenue();
		createIncomePage.setIncomeName( nomeReceita );
		createIncomePage.setValue( valor );
		createIncomePage.setBankAccount( banco );
		createIncomePage.setExpirationDate( expirationDate );
		createIncomePage.setIncomeCategory( categoria );
		createIncomePage.clickAddIncomeButton();
		checkPoint( "Não encontrou mensagem de confirmação de receita criada com sucesso",
				"Receita 'Teste Receita', de R$ " + valor + ",",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		step( "Volta para o dashboard e adiciona uma receita " );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		dashboardPage.clickImportExtract();
		newImportExtractPage.selectFileImport( arquivo );
		newImportExtractPage.clickImportExtract();
		checkPoint( "Não encontrou mensagem de confirmação de extrato bancário importado com sucesso",
				"Extrato bancário importado com sucesso!",
				getAssistants().getNotificationAssistant( getWebDriver()
						).getAlertMessageText() );
		importBankExtractPage.closeRegister();
		step( "importar extrato" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		dashboardPage.openDropDownImportExtract();
		dashboardPage.clickSeeTranfer();
		listingAssistant.hasItemInGrid( nomeDespesa );
		listingAssistant.hasItemInGrid( nomeReceita );
		step( "Volta para o dashboard vai para os lançamentos " );

		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );

	}
}
