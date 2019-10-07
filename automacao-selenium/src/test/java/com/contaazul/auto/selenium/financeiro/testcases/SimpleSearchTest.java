package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class SimpleSearchTest extends SeleniumTest {

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "SimpleSearchTest@contaazul.com", "12345" );
	}

	@BeforeMethod
	public void goToHomePage() {
		this.getAssistants().getMenuAssistant( getWebDriver() ).navigateToHomePage();
	}

	@Test
	public void simpleSearch() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_A_RECEBER );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		accountReceivablePage.search( "lote" );

		checkPoint( "Procurando item sendo filtrado", true, accountReceivablePage.hasItemInGrid( "Venda lote 1" ) );
		checkPoint( "Procurando item sendo filtrado", "1", Long.toString( accountReceivablePage.getGridItemsCount() ) );

		accountReceivablePage.search( "" );
		checkPoint( "Procurando item sendo filtrado", "1", Long.toString( accountReceivablePage.getGridItemsCount() ) );
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

}
