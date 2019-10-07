package com.contaazul.auto.selenium.financeiro.testcases;

import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.contaazul.auto.data.DateFormatUtil;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class DatePeriodSearchAccountsReceivableTest extends SeleniumTest {

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"DatePeriodSearchAccountsReceivableTest@contaazul.com", "12345" );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.FINANCEIRO );
		getAssistants().getListingAssistant( getWebDriver() ).filterByPeriod( "Mostrar todos" );
		getAssistants().getDeleteAssistant( getWebDriver() ).deleteAllFinancialStatements();
		step( "Remove todos os lançamentos, se houver." );

		new FinanceExtractPage( getWebDriver() ).navigateToNewIncome();
		step( "Vai para o formulário Adicionar Receita" );
		CreateIncomePage createIncomePage = new CreateIncomePage( getWebDriver() );
		createIncomePage.setIncomeName( "Teste Receita data hoje" );
		createIncomePage.setValue( "123456" );
		createIncomePage.setBankAccount( "Cartão de Crédito" );
		createIncomePage.setExpirationDate( DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE ) );
		createIncomePage.setIncomeCategory( "Ajuste Caixa" );
		createIncomePage.setReceived( Boolean.parseBoolean( "false" ) );
		createIncomePage.setRepeat( Boolean.parseBoolean( "false" ) );
		step( "Preenche o formulário Adicionar Despesa" );
		createIncomePage.clickAddIncomeButton();
		sleep( FOR_A_LONG_TIME );
		step( "Clica Adicionar Despesa com data de hoje" );

		new FinanceExtractPage( getWebDriver() ).navigateToNewIncome();
		step( "Vai para o formulário Adicionar Receita" );
		createIncomePage = new CreateIncomePage( getWebDriver() );
		createIncomePage.setIncomeName( "Teste Receita mostrar todos" );
		createIncomePage.setValue( "123456" );
		createIncomePage.setBankAccount( "Cartão de Crédito" );
		Calendar c = Calendar.getInstance();
		c.add( Calendar.YEAR, -10 );
		createIncomePage.setExpirationDate( DateFormatUtil.format( c.getTime(), DateFormatUtil.FULLDATE ) );
		createIncomePage.setIncomeCategory( "Ajuste Caixa" );
		createIncomePage.setReceived( Boolean.parseBoolean( "false" ) );
		createIncomePage.setRepeat( Boolean.parseBoolean( "false" ) );
		step( "Preenche o formulário Adicionar Despesa" );
		createIncomePage.clickAddIncomeButton();
		sleep( FOR_A_LONG_TIME );
		step( "Clica Adicionar Despesa com data de 10 anos atrás" );
	}

	@BeforeMethod
	public void goToHomePage() {
		this.getAssistants().getMenuAssistant( getWebDriver() ).navigateToHomePage();
	}

	@Test
	public void searchForIncomeMadeToday() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_A_RECEBER );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.HOJE );
		checkPoint( "Não encontrou receita criada hoje", true,
				accountReceivablePage.hasItemInGrid( "Teste Receita data hoje" ) );
		checkPoint( "Havia mais de uma receita criada hoje", "1",
				Long.toString( accountReceivablePage.getGridItemsCount() ) );
	}

	@Test
	public void searchForTransactionsMadeThisWeek() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_A_RECEBER );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.ESTA_SEMANA );
		checkPoint( "Não encontrou receita criada esta semana", true,
				accountReceivablePage.hasItemInGrid( "Teste Receita data hoje" ) );
		checkPoint( "Não encontrou receita criada esta semana", "1",
				Long.toString( accountReceivablePage.getGridItemsCount() ) );
	}

	@Test
	public void searchForTransactionsMadethisMonth() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_A_RECEBER );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.ESTE_MES );
		checkPoint( "Não encontrou receita criada este mês", true,
				accountReceivablePage.hasItemInGrid( "Teste Receita data hoje" ) );
		checkPoint( "Não encontrou receita criada este mês", "1",
				Long.toString( accountReceivablePage.getGridItemsCount() ) );
	}

	@Test
	public void searchForTransactionsLast30Days() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_A_RECEBER );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.ULTIMOS_30_DIAS );
		checkPoint( "Não encontrou receita criada últimos 30 dias", true,
				accountReceivablePage.hasItemInGrid( "Teste Receita data hoje" ) );
		checkPoint( "Não encontrou receita criada últimos 30 dias", "1",
				Long.toString( accountReceivablePage.getGridItemsCount() ) );
	}

	@Test
	public void searchForAllPostings() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_A_RECEBER );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		checkPoint( "Não encontrou receita criada há mais de 30 dias", true,
				accountReceivablePage.hasItemInGrid( "Teste Receita mostrar todos" ) );
		checkPoint( "Não encontrou receita criada há mais de 30 dias", "2",
				Long.toString( accountReceivablePage.getGridItemsCount() ) );
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

}
