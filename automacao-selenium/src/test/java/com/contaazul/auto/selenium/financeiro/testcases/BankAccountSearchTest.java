package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.IncomePage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class BankAccountSearchTest extends SeleniumTest {

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "BankAccountSearchTest@contaazul.com", "12345" );
	}

	@BeforeMethod
	public void goToHomePage() {
		this.getAssistants().getMenuAssistant( getWebDriver() ).navigateToHomePage();
	}

	@Test
	public void searchBankAccount() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_A_RECEBER );

		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		IncomePage<AccountReceivablePage> editIncomePage = accountReceivablePage.navigateToEditIncomePage( 0 );

		editIncomePage.setBankAccount( "Banco do Brasil" );
		editIncomePage.save();
		sleep( FOR_A_LONG_TIME );

		accountReceivablePage.filterBankAccount( "Banco do Brasil" );

		checkPoint( "Procurando item sendo filtrado", true,
				accountReceivablePage.hasItemInGrid( "Teste de receita nao paga no passado" ) );
		checkPoint( "Procurando item sendo filtrado", "1", Long.toString( accountReceivablePage.getGridItemsCount() ) );
	}

	@Test
	public void searchAllBankAccounts() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_A_RECEBER );

		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		accountReceivablePage.filterBankAccount( "Todos" );

		checkPoint( "Procurando item sendo filtrado", true,
				accountReceivablePage.hasItemInGrid( "Teste de receita nao paga no passado" ) );
		checkPoint( "Procurando item sendo filtrado", "1", Long.toString( accountReceivablePage.getGridItemsCount() ) );
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

}
