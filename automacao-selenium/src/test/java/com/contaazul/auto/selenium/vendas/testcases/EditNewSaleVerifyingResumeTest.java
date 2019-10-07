package com.contaazul.auto.selenium.vendas.testcases;

import java.util.Random;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.vendas.pages.NewSalePage;

public class EditNewSaleVerifyingResumeTest extends SeleniumTest {

	private double calc = 0.00;

	@Test(dataProvider = DATA_PROVIDER)
	public void editNewSaleVerifyingResumeTest(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String quantityItens, String discountType, String discountValue,
			String freightValue, String editItem, String editItemDesc, String editItemQuantity, String editItemPrice,
			String editDiscountType, String editDiscountValue, String editFreightValue, String editClient,
			String editSaleNumber, String editSaleDate, String editPaymentType,
			String editPaymentMaturityDate, String editQuantityItens) {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "EditNewSaleVerifyingResumeTest@contaazul.com",
				"12345" );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Nova Venda" );
		NewSalePage salePage = getPaginas().getNewSalePage( getWebDriver() );

		saleNumber = createSale( client, saleNumber, saleDate, paymentType, paymentMaturityDate, quantityItens,
				salePage );
		salePage.fillRandomItemsList( quantityItens, discountType, discountValue, freightValue );
		step( "Cria uma venda" );

		salePage.saveNewSale();
		salePage.goBackToSaleList();
		salePage.clickOnRow( "0" );
		step( "Volta para listagem e clica na primeira linha" );

		sleep( VERY_LONG );
		salePage.clickBtnEditUpper();
		Random rn = new Random();
		String editSaleNumberRandom = editSaleNumber + rn.nextInt( 1000 );
		step( "Clica no botão de editar a cima" );

		saleNumber = createSale( editClient, editSaleNumberRandom, editSaleDate, editPaymentType,
				editPaymentMaturityDate, editQuantityItens,
				salePage );
		salePage.addOneItemOnList( editItem, editItemDesc, editItemQuantity, editItemPrice, editDiscountType,
				editDiscountValue, editFreightValue );
		salePage.setTypeItem( 0, NewSalePage.TYPE_PRODUCT );
		salePage.saveNewSale();
		step( "Edita todos os campos da venda" );

		verifySaleResumeValues( "1", editDiscountType, salePage );
		checkPoint( "Nome do cliente não foi alterado", editClient, salePage.getClient() );
		checkPoint( "Data da venda não foi alterada", editSaleDate, salePage.getSaleDate() );
		checkPoint( "Data do vencimento não foi alterado", editPaymentMaturityDate, salePage.getSaleFirstDueDate() );
		checkPoint( "Nr da venda", editSaleNumberRandom, salePage.getHeaderSaleNumber() );
		checkPoint( "Condicoes do pagamento não foi alterada", "À vista", salePage.getSaleInstallment() );
		verifySaleResumeValues( quantityItens, discountType, salePage );
		step( "Verifica os valores da grid" );

		salePage.goBackToSaleList();
		checkPoint( "Numero da  venda não foi atualizado", editSaleNumberRandom,
				salePage.getValuesGrid( editSaleNumberRandom ) );
		checkPoint( "Nome do cliente não foi alterado", editClient, salePage.getValuesGrid( editClient ) );
		checkPoint( "Data da venda não foi atualizada", editSaleDate,
				salePage.getValuesGrid( editSaleDate ) );
		checkPoint( "Valor não foi atualizado", "380,00",
				salePage.getValueLiquid() );
		step( "Verifica os valores da grid" );

		salePage.deleteAllSales();
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
		step( "Deleta a venda." );
	}

	private String createSale(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String quantityItens, NewSalePage salePage) {
		salePage.setSaleClient( client );
		salePage.setSaleNumber( saleNumber );
		salePage.setSaleDate( saleDate );
		salePage.setSalePaymentType( paymentType );
		salePage.setPaymentMaturityDate( paymentMaturityDate );
		step( "Informa os dados cliente, numero da venda, data, modo de pagamento..." );

		saleNumber = salePage.getSaleNumber();
		return saleNumber;
	}

	private void verifySaleResumeValues(String qtdeItens, String discountType, NewSalePage salePage) {
		for (int i = 0; i != Integer.parseInt( qtdeItens ); i++) {
			calc = salePage.getResumePriceItem( i ) * salePage.getResumeQttyItem( i );

			checkPoint( "[Resumo da venda] Validação de cálculos de 'Subtotal' da lista de itens",
					String.valueOf( calc ),
					String.valueOf( salePage.getResumeSubTotal( i ) ) );
		}

		checkPoint( "[Resumo da venda] Validação do 'Valor total' da Nova Venda",
				String.valueOf( calc ),
				String.valueOf( salePage.getSomaVlrTotal() ) );

		calc = salePage.getSomaVlrTotal() - salePage.getResumeDiscountValue() + salePage.getResumeFreightValue();

		checkPoint( "[Resumo da venda] Validação do 'Total líquido' da Nova Venda",
				String.valueOf( calc ),
				String.valueOf( salePage.getSaleTotalLiquid() ) );

		checkPoint( "[Resumo da venda] Validação do 'Total líquido do cabeçalho' da Nova Venda",
				String.valueOf( calc ),
				String.valueOf( salePage.getHeaderSaleTotalLiquid() ) );
	}
}
