package com.contaazul.auto.selenium.vendas.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.Period;
import com.contaazul.auto.selenium.vendas.pages.NewSalePage;

public class EditNewSaleIntoPlotsTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void editNewSaleIntoPlotsTest(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String quantityItens, String qtd, String discountType, String discountValue,
			String freightValue, String typeItem, String editPaymentType) {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "EditNewSaleIntoPlotsTest@contaazul.com", "12345" );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Nova Venda" );
		NewSalePage salePage = getPaginas().getNewSalePage( getWebDriver() );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		step( "Faz o login navega para a Nova venda e instancia as classes" );

		createSale( client, saleNumber, saleDate, paymentType, paymentMaturityDate, salePage );
		salePage.fillRandomItemsList( qtd, discountType, discountValue, freightValue );
		salePage.saveNewSale();
		step( "Cria uma nova venda" );

		salePage.clickBtnEditBottom();
		createSale( client, saleNumber, saleDate, paymentType, paymentMaturityDate, salePage );
		salePage.setTypeItem( 0, Integer.parseInt( typeItem ) );
		salePage.setSalePaymentType( editPaymentType );
		step( "Editar a venda passando por parametro o tipo do pagamento e se o item é p/s" );

		if (Integer.parseInt( typeItem ) == 0)
			checkPoint( "Campo de frete não está sendo mostrado", true, salePage.isDisplayerFreight() );
		salePage.saveNewSale();
		step( "Verifica se for produto e está mostrando o campo de frete e salva." );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		checkPoint( "Não foi divida em parcelas", "4", String.valueOf( accountReceivablePage.getGridItemsCount() ) );
		step( "Navega para a tela de Extrato e verifica quantas parcelas estão na grid" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.deleteAllSales();
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
	}

	private String createSale(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, NewSalePage salePage) {
		salePage.setSaleClient( client );
		salePage.setSaleNumber( saleNumber );
		salePage.setSaleDate( saleDate );
		salePage.setSalePaymentType( paymentType );
		salePage.setPaymentMaturityDate( paymentMaturityDate );
		step( "Informa os dados cliente, numero da venda, data, modo de pagamento..." );

		saleNumber = salePage.getSaleNumber();
		return saleNumber;
	}
}
