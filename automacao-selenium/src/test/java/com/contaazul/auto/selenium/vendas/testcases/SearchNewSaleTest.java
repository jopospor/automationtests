package com.contaazul.auto.selenium.vendas.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.Period;
import com.contaazul.auto.selenium.vendas.pages.NewSalePage;

public class SearchNewSaleTest extends SeleniumTest {

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "SearchNewSaleTest@contaazul.com", "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void searchNewSaleTest(String client, String saleDate, String paymentType, String paymentMaturityDate,
			String quantityItens, String discountType, String discountValue, String freightValue,
			String invalidSendKeys, String clientInvalid) {

		NewSalePage salePage = getPaginas().getNewSalePage( getWebDriver() );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.addNewSale();
		salePage.setSaleClient( client );
		salePage.setSaleDate( saleDate );
		salePage.setSalePaymentType( paymentType );
		salePage.setPaymentMaturityDate( paymentMaturityDate );
		salePage.fillRandomItemsList( quantityItens, discountType, discountValue, freightValue );
		salePage.saveNewSale();
		salePage.goBackToSaleList();
		step( "Cria uma nova venda preenchendo os campos obrigatórios e salvando" );
		salePage.addNewSale();
		salePage.setSaleClient( clientInvalid );
		salePage.setSaleDate( saleDate );
		salePage.setSalePaymentType( paymentType );
		salePage.setPaymentMaturityDate( paymentMaturityDate );
		salePage.fillRandomItemsList( quantityItens, discountType, discountValue, freightValue );
		salePage.saveNewSale();
		step( "Cria uma nova venda preenchendo os campos obrigatórios e salvando" );

		salePage.goBackToSaleList();
		salePage.setSearchField( client );
		salePage.clickBtnSearch();
		checkPoint( "Resultado esperado aparecer 1 item na grid", "1", Long.toString( salePage.getNumberRowGridList() ) );

		salePage.setSearchField( invalidSendKeys );
		salePage.clickBtnSearch();
		checkPoint( "Resultado esperado aparecer 1 item na grid", "0", Long.toString( salePage.getNumberRowGridList() ) );
		salePage.setSearchField( " " );
		salePage.clickBtnSearch();

		salePage.deleteAllSales();
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
	}
}
