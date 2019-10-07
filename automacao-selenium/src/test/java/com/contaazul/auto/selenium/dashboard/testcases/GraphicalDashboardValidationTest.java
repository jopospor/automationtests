package com.contaazul.auto.selenium.dashboard.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.DeleteAssistant;
import com.contaazul.auto.selenium.dashboard.pages.GraphicalDashboardPage;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class GraphicalDashboardValidationTest extends SeleniumTest {

	@BeforeClass
	public void loginAndDeleteRecords() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "GraphicalDashboardValidationTest@contaazul.com",
				"12345" );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Movimentações",
				"Extrato" );

		DeleteAssistant deleteAssistant = getAssistants().getDeleteAssistant(
				getWebDriver() );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );

		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		deleteAssistant.deleteAllFinancialStatements();
		step( "Faz login ,vai para financeiro e exclui todas receitas/despesas" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void graphicalValidationTest(String nome, String valor, String banco, String expirationDate,
			String categoria, String expirationFuturo) {
		GraphicalDashboardPage graphicalDashboardPage = getPaginas().getGraphicalDashboardPage( getWebDriver() );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );

		financeExtractPage.navigateToNewIncome();
		createIncome( nome, valor, banco, expirationFuturo, categoria );
		financeExtractPage.navigateToNewExpense();
		createIncome( nome, valor, banco, expirationFuturo, categoria );
		step( "cria receita e despesa no futuro" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		graphicalDashboardPage.getGraphicalText();
		step( "valida que os lançamentos futuros não irão aparecer no grafico" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Movimentações", "Extrato" );
		financeExtractPage.navigateToNewIncome();
		createIncome( nome, valor, banco, expirationDate, categoria );
		financeExtractPage.navigateToNewExpense();
		createIncome( nome, valor, banco, expirationDate, categoria );
		step( "cria receita e despesa na data atual" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Visão Geral" );
		graphicalDashboardPage.tooltip( "11" );
		checkPoint( "tooltip não encontrado", true, graphicalDashboardPage.isTooltipTextDisplayed( "R$ 5.000,50" ) );
		step( "validar o tooltip" );

	}

	private void createIncome(String nome, String valor, String banco, String expirationDate, String categoria) {
		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		createIncomePage.setIncomeName( nome );
		createIncomePage.setValue( valor );
		createIncomePage.setBankAccount( banco );
		createIncomePage.setExpirationDate( expirationDate );
		createIncomePage.setIncomeCategory( categoria );
		createIncomePage.clickAddIncomeButton();
	}

	@AfterClass
	public void logout() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
	}

}
