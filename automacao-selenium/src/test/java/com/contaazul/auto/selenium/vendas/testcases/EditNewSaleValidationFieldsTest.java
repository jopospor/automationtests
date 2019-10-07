package com.contaazul.auto.selenium.vendas.testcases;

import java.util.Random;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.vendas.pages.NewSalePage;

public class EditNewSaleValidationFieldsTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void editNewSaleValidationFields(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String quantityItens, String discountType, String discountValue,
			String freightValue, String nrSale, String valueItem, String observation) {
		NewSalePage salePage = getPaginas().getNewSalePage( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login( "EditNewSaleValidationFieldsTest@contaazul.com",
				"12345" );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Nova Venda" );
		Random rn = new Random();
		String randomNrSale = saleNumber + rn.nextInt( 1000 );
		createSale( client, randomNrSale, saleDate, paymentType, paymentMaturityDate, quantityItens, discountType,
				discountValue, freightValue, observation, salePage );

		salePage.clickBtnEditBottom();
		String msgValidation = "Preencha corretamente o(s) campo(s) obrigatório(s) em destaque";

		salePage.clearClient();
		sleep( VERY_LONG );
		salePage.saveNewSale();
		checkPoint( "Permitiu salvar a venda ou aconteceu algum erro inesperado.", msgValidation, getAssistants()
				.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		salePage.setClient( client );
		step( "Apaga o nome do cliente e salva e preenche novamente." );

		salePage.clearNrSale();
		salePage.saveNewSale();
		checkPoint( "Permitiu salvar a  venda ou aconteceu algum erro inesperado", msgValidation, getAssistants()
				.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		randomNrSale = nrSale + rn.nextInt( 1000 );
		salePage.setNrSale( randomNrSale );
		step( "Apaga o numero da venda e salva e preenche novamente." );

		salePage.clearDateSale();
		salePage.saveNewSale();
		checkPoint( "Permitiu salvar a venda ou acontenceu algum erro inesperado", msgValidation, getAssistants()
				.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		salePage.setDateSale( saleDate );
		step( "Apaga a data venda e salva e preenche novamente." );

		salePage.clearFirstDueDate();
		salePage.saveNewSale();
		checkPoint( "Permitiu salvar a venda ou aconteceu algum erro inesperado", msgValidation, getAssistants()
				.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		salePage.setDateFirstDueDate( paymentMaturityDate );
		step( "Apaga a data venda e salva e preenche novamente." );

		String itemRandom = "item" + rn.nextInt( 1000 );
		checkPoint( "Permitiu salvar a  venda ou acontenceu algum eror inesperado", msgValidation,
				salePage.ClearItemAndSave( 0, itemRandom, NewSalePage.TYPE_SERVICE ) );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		salePage.createItemByQuickAdd( driver.findElementById( "negotiationItem" + 0 ), "Item" + rn.nextInt( 10000 ),
				NewSalePage.TYPE_PRODUCT, 0 );
		step( "Apaga o item da primeira linha venda e salva e preenche novamente." );

		salePage.ClearValueItemAndSave( 0, valueItem );
		checkPoint( "Permitiu salvar a  venda ou acontenceu algum eror inesperado", msgValidation,
				salePage.ClearValueItemAndSave( 0, valueItem ) );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		step( "Apaga o valor do item da primeira linha venda e salva e preenche novamente." );

		salePage.ClearQtdAndSave( 0, quantityItens );
		checkPoint( "Permitiu salvar a  venda ou acontenceu algum eror inesperado", msgValidation,
				salePage.ClearQtdAndSave( 0, quantityItens ) );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		step( "Apaga a quantidade do item da primeira linha venda e salva e preenche novamente." );

		salePage.removeLineWithDates( "1" );
		salePage.clearObservation();
		salePage.saveNewSale();
		step( "Remove a segunda linha e limpa o campo de observações." );

		checkPoint( "Linha não foi removida", "1", Long.toString( salePage.getNumberRowResume() ) );

		salePage.goBackToSaleList();
		salePage.deleteAllSales();
		step( "Volta para listagem e deleta a venda." );
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
	}

	private String createSale(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String quantityItens, String discountType, String discountValue,
			String freightValue, String observation, NewSalePage salePage) {
		salePage.setSaleClient( client );
		salePage.setSaleNumber( saleNumber );
		salePage.setSaleDate( saleDate );
		salePage.setSalePaymentType( paymentType );
		salePage.setPaymentMaturityDate( paymentMaturityDate );
		salePage.setObservationSale( observation );
		step( "Informa os dados cliente, numero da venda, data, modo de pagamento..." );

		salePage.fillRandomItemsList( quantityItens, discountType, discountValue, freightValue );
		saleNumber = salePage.getSaleNumber();
		salePage.saveNewSale();
		return saleNumber;
	}
}
