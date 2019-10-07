package com.contaazul.auto.selenium.vendas.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.AutoCompleteAssistant;
import com.contaazul.auto.selenium.vendas.pages.ListSalesOrderFormPage;
import com.contaazul.auto.selenium.vendas.pages.SalesOrderFormPage;

public class CreateSalesOrderTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void createSalesOrder(String itemQuantity0) throws InterruptedException {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "CreateSalesOrderTest@contaazul.com", "12345" );
		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Produtos", "Pedidos de Venda" );
		step( "Navega para Pedidos de Venda" );
		ListSalesOrderFormPage listPage = getPaginas().getListSalesOrderPage( getWebDriver() );
		listPage.getButtonNewSalesOrder().click();
		step( "Novo Pedido de Venda" );
		SalesOrderFormPage page = getPaginas().getSalesOrderPage( getWebDriver() );
		AutoCompleteAssistant autoComplete = new AutoCompleteAssistant( getWebDriver() );
		String valueClient = String.valueOf( System.currentTimeMillis() );
		autoComplete.sendKeysAndCreateNew( page.getClient(), valueClient );
		String valueItem = String.valueOf( System.currentTimeMillis() );
		autoComplete.sendKeysAndCreateNew( page.getItem0(), valueItem );
		page.setItemQuantity0( itemQuantity0 );
		page.setItem1Value( "900,00" );
		step( "Preenche formulário de Pedido de Venda" );
		page.getButtonSave().click();
		listPage.searchByClient( valueClient );
		listPage.clickOnSalesOrder( valueClient );
		Thread.sleep( 3000 );
		checkPoint( "Cliente nao confere com o valor esperado", valueClient, autoComplete.getText( page.getClient() ),
				true );
		checkPoint( "Item nao confere com o valor esperado", valueItem, autoComplete.getText( page.getItem0() ), true );
	}
}
