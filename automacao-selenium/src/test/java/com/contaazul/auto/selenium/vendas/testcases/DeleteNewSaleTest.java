package com.contaazul.auto.selenium.vendas.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.Period;
import com.contaazul.auto.selenium.vendas.pages.NewSalePage;

public class DeleteNewSaleTest extends SeleniumTest {
	CreateNewSaleTest newSale;

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "DeleteNewSaleTest@contaazul.com", "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void deleteNewSaleTest(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String qtd, String discountType, String discountValue, String freightValue,
			String bankAccount, String received, String typeItem, String itemDesc, String itemQuantity,
			String itemPrice, String validateResume) {

		NewSalePage salePage = getPaginas().getNewSalePage( getWebDriver() );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );

		newSale = new CreateNewSaleTest();
		newSale.setDriver( getWebDriver() );

		newSale.CreateNew( client, saleNumber, saleDate, paymentType, paymentMaturityDate, qtd, typeItem,
				itemDesc, itemQuantity, itemPrice, discountType, discountValue, freightValue, validateResume );
		salePage.goBackToSaleList();
		newSale.CreateNew( client, saleNumber, saleDate, paymentType, paymentMaturityDate, qtd, typeItem,
				itemDesc, itemQuantity, itemPrice, discountType, discountValue, freightValue, validateResume );
		salePage.goBackToSaleList();
		newSale.CreateNew( client, saleNumber, saleDate, paymentType, paymentMaturityDate, qtd, typeItem,
				itemDesc, itemQuantity, itemPrice, discountType, discountValue, freightValue, validateResume );
		salePage.goBackToSaleList();
		step( "Cria 3 vendas" );

		salePage.deleteLastSale( 0 );
		checkPoint( "Venda não paga não foi excluida", "Venda selecionada excluída com sucesso.", getAssistants()
				.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		step( "Seleciona a primeira  venda e deleta" );

		salePage.deleteAllSales();
		checkPoint( "Venda não paga não foi excluidas", "Vendas selecionadas foram excluídas com sucesso.",
				getAssistants()
						.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		step( "Seleciona todas e deleta." );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		checkPoint( "Vendas não foram excluidas", "0", Long.toString( accountReceivablePage.getGridItemsCount() ) );
		step( "Verifica na grid do financeiro se elas  foram excluídas" );
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
	}
}
