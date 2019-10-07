package com.contaazul.auto.selenium.financeiro.testcases;

import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.contaazul.auto.data.DateFormatUtil;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage.AdvancedSearchOptions;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class IncomeAdvancedSearchTest extends SeleniumTest {

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "IncomeAdvancedSearchTest@contaazul.com", "12345" );
	}

	@Test
	public void advancedSearch() {

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_A_RECEBER );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterBankAccount( "Bradesco" );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		editDateActual();

		accountReceivablePage.advancedSearch( new AdvancedSearchOptions( "Ajuste Caixa", "Centro 002",
				"Conta Azul Ltda", "Categoria para pesquisa" ) );

		checkPoint( "Procurando item sendo filtrado", true,
				accountReceivablePage.hasItemInGrid( "Categoria para pesquisa" ) );
		checkPoint( "Procurando item sendo filtrado", "1", Long.toString( accountReceivablePage.getGridItemsCount() ) );
	}

	private void editDateActual() {
		getAssistants().getListingAssistant( getWebDriver() ).searchByKeyword( "Categoria para pesquisa" );
		String dataInicial = DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE );
		getAssistants().getListingAssistant( getWebDriver() ).getListingRowAsElement( 1 ).click();
		CreateIncomePage editIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		editIncomePage.setExpirationDate( dataInicial );
		editIncomePage.clickAddIncomeButton();
	}

	@BeforeMethod
	public void goToHomePage() {
		this.getAssistants().getMenuAssistant( getWebDriver() ).navigateToHomePage();
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

}
