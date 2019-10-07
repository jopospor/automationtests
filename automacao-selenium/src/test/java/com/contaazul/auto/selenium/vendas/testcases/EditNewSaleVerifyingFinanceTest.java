package com.contaazul.auto.selenium.vendas.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage.RolloverMenuOptions;
import com.contaazul.auto.selenium.vendas.pages.NewSalePage;

public class EditNewSaleVerifyingFinanceTest extends SeleniumTest {

	private double calc = 0.00;

	@Test(dataProvider = DATA_PROVIDER)
	public void editNewSaleVerifyingFinance(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String quantityItens, String discountType,
			String discountValue, String freightValue, String editQuantityItens, String editItem, String editItemDesc,
			String editItemQuantity,
			String editItemPrice, String editDiscountType, String editDiscountValue, String editFreightValue,
			String observation, String bankAccount, String received) {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "EditNewSaleVerifyingFinanceTest@contaazul.com",
				"12345" );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Nova Venda" );
		NewSalePage salePage = getPaginas().getNewSalePage( getWebDriver() );

		saleNumber = createSale( client, saleNumber, saleDate, paymentType, paymentMaturityDate, quantityItens,
				discountType, discountValue, freightValue, salePage );
		checkPoint( "Validação para a mensagem de venda salva com sucesso.", "A venda nº " + saleNumber
				+ " foi criada com sucesso.", getAssistants().getNotificationAssistant( getWebDriver() )
				.getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		step( "Salva a venda valida a mensagem de sucesso, " );

		salePage.clickBtnEditBottom();
		salePage.addOneItemOnList( editItem, editItemDesc, editItemQuantity, editItemPrice, editDiscountType,
				editDiscountValue, freightValue );

		salePage.setObservationSale( observation );
		salePage.saveNewSale();
		step( "Na tela de visualização clica no botão de editar na parte inferior e altera o valor de todos os campos" );

		checkPoint( "Validação para a mensagem de venda salva com sucesso.", "A venda nº " + saleNumber
				+ " foi editada com sucesso.", getAssistants().getNotificationAssistant( getWebDriver() )
				.getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		getTotalValueSale( quantityItens, salePage );
		searchSaleFinance( saleNumber );
		step( "Valida a mensagem de sucesso e pega o valor da venda e o total" );

		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		sleep( VERY_LONG );
		String valueFinanceconvert = salePage.convertValues( createIncomePage.getValue() );
		checkPoint( "Verifica se o valor é o mesmo", String.valueOf( this.calc ), valueFinanceconvert );
		checkPoint( "Verifica se a categoria é igual Venda", "Venda", createIncomePage.getIncomeCategory() );
		checkPoint( "Verifica a data do vencimento", paymentMaturityDate, createIncomePage.getExpirationDate() );
		checkPoint( "Verifica se o cliente é o mesmo", client, createIncomePage.getCustomer() );
		checkPoint( "Verifica se a data da venda é a mesma", saleDate, createIncomePage.getCompetenceDate() );
		checkPoint( "Verifica se o campo de observações é a mesma", observation, createIncomePage.getObservations() );
		step( "Valida se os valores dentro da receita foram alterados" );

		createIncomePage.setBankAccount( bankAccount );
		createIncomePage.setReceived( Boolean.parseBoolean( received ) );
		createIncomePage.clickAddIncomeButton();
		step( "Seleciona uma conta bancária e marca como pago e salva" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.setSearchField( saleNumber );
		salePage.clickOnRow( saleNumber );
		sleep( VERY_LONG );
		salePage.clickBtnEditUpper();
		String msgWaitedToolType = "Não é possível editar/excluir a venda selecionada pois existem um ou mais recebimentos "
				+ "associados. <br/>Por favor, desfaça o recebimento no financeiro para poder editar/excluir esta venda.";
		checkPoint( "Verifica se a mensagem do tooltype está sendo mostrada e qual a mensagem dela", msgWaitedToolType,
				salePage.getTooltypeMessage() );
		step( "Navega para a Listagem da nova venda, busca pelo numero da venda clica em editar e verifica se  otooltype esta abilitado" );

		salePage.goBackToSaleList();
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
		FinanceFlowPage financeFlowPage = getPaginas().getFinanceFlowPage( getWebDriver() );
		sleep( VERY_LONG );
		financeFlowPage.showRolloverMenu( 0 );
		financeFlowPage.chooseOptionMenuRollover( RolloverMenuOptions.UNDO_PAY );
		financeFlowPage.closeRolloverMenuOpened();
		step( "navega para o financeiro desmarca como paga" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.deleteAllSales();
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
		step( "Deleta a venda." );
	}

	private void searchSaleFinance(String saleNumber) {
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		FinanceExtractPage fin = getPaginas().getFinanceExtractPage( getWebDriver() );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		fin.search( saleNumber );
		getAssistants().getListingAssistant( getWebDriver() ).getListingRowAsElement( 1 ).click();
	}

	private String createSale(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String quantityItens, String discountType, String discountValue,
			String freightValue, NewSalePage salePage) {
		salePage.setSaleClient( client );
		salePage.setSaleNumber( saleNumber );
		salePage.setSaleDate( saleDate );
		salePage.setSalePaymentType( paymentType );
		salePage.setPaymentMaturityDate( paymentMaturityDate );
		step( "Informa os dados cliente, numero da venda, data, modo de pagamento..." );

		salePage.fillRandomItemsList( quantityItens, discountType, discountValue, freightValue );
		saleNumber = salePage.getSaleNumber();
		salePage.saveNewSale();
		return saleNumber;
	}

	private void getTotalValueSale(String quantityItens, NewSalePage salePage) {
		for (int i = 0; i != Integer.parseInt( quantityItens ); i++) {
			calc = salePage.getSomaVlrTotal() - salePage.getResumeDiscountValue() + salePage.getResumeFreightValue();
		}
	}
}
