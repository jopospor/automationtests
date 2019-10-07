package com.contaazul.auto.selenium.dashboard.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.DeleteAssistant;
import com.contaazul.auto.selenium.dashboard.pages.DashboardPage;
import com.contaazul.auto.selenium.dashboard.pages.GraphicalDashboardPage;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class GraphicalDrilldownDashboardTest extends SeleniumTest {
	/**
	 * Teste que valida o grafico do dashboard, cria e exclui receitas e
	 * despesas
	 */

	@BeforeClass
	public void deleteRecords() {

		DeleteAssistant deleteAssistant = getAssistants().getDeleteAssistant( getWebDriver() );
		getAssistants().getLoginAssistant( getWebDriver() ).login( "GraphicalDrilldownDashboardTest@contaazul.com",
				"12345" );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Extrato" );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		deleteAssistant.deleteAllFinancialStatements();
		step( "Faz login ,vai para financeiro e exclui todas receitas/despesas" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void graphicalDrilldownDashboardTest(String nomeDespesa, String valor, String contaBanco, String categoria,
			String expirationDateExpense, String nomeReceita, String banco, String expirationDate,
			String expirationDatePassada) {

		DashboardPage dashboardPage = getPaginas().getDashboardPage( getWebDriver() );
		GraphicalDashboardPage graphicalDashboardPage = getPaginas().getGraphicalDashboardPage( getWebDriver() );

		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		financeExtractPage.navigateToNewIncome();
		createRevenue( valor, categoria, nomeReceita, banco, expirationDate );
		financeExtractPage.navigateToNewExpense();
		createExpense( nomeDespesa, valor, contaBanco, categoria, expirationDateExpense );
		step( "Cria receitas/despesas data atual" );

		financeExtractPage.navigateToNewIncome();
		createRevenue( valor, categoria, nomeReceita, banco, expirationDatePassada );
		financeExtractPage.navigateToNewExpense();
		createExpense( nomeDespesa, valor, contaBanco, categoria, expirationDateExpense );
		step( "Cria receitas/despesas no passado" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Visão Geral" );
		graphicalDashboardPage.drillDown( "11" );
		dashboardPage.clickLinkReturnToGraphic();
		step( "Valida o click no drilldown e o link de voltar" );

	}

	@AfterClass
	public void logout() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
	}

	private void createRevenue(String valor, String categoria, String nomeReceita, String banco, String expirationDate) {

		createStatement( valor, categoria, nomeReceita, banco, expirationDate );
		checkPoint( "Não encontrou mensagem de confirmação de receita criada com sucesso",
				"Receita '" + nomeReceita + "', de R$ " + valor + ",",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
	}

	private void createExpense(String nomeDespesa, String valor, String contaBanco, String categoria,
			String expirationDateExpense) {

		createStatement( valor, categoria, nomeDespesa, contaBanco, expirationDateExpense );
		checkPoint( "Não encontrou mensagem de confirmação de despesa criada com sucesso",
				"Despesa '" + nomeDespesa + "', de R$ " + valor + ",",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
	}

	private void createStatement(String valor, String categoria, String nome, String banco, String expirationDate) {
		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		createIncomePage.setIncomeName( nome );
		createIncomePage.setValue( valor );
		createIncomePage.setBankAccount( banco );
		createIncomePage.setExpirationDate( expirationDate );
		createIncomePage.setIncomeCategory( categoria );
		createIncomePage.clickAddIncomeButton();
	}

}
