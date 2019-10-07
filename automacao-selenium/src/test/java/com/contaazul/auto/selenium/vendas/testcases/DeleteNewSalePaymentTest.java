package com.contaazul.auto.selenium.vendas.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage.RolloverMenuOptions;
import com.contaazul.auto.selenium.vendas.pages.NewSalePage;

public class DeleteNewSalePaymentTest extends SeleniumTest {
	CreateNewSaleTest newSale;

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "DeleteNewSalePaymentTest@contaazul.com", "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void deleteNewSalePaymentTest(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String qtd, String discountType, String discountValue, String freightValue,
			String bankAccount, String received, String typeItem, String itemDesc, String itemQuantity,
			String itemPrice, String validateResume) {

		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		FinanceFlowPage financeFlowPage = getPaginas().getFinanceFlowPage( getWebDriver() );
		NewSalePage salePage = getPaginas().getNewSalePage( getWebDriver() );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		newSale = new CreateNewSaleTest();
		newSale.setDriver( getWebDriver() );
		newSale.CreateNew( client, saleNumber, saleDate, paymentType, paymentMaturityDate, qtd, typeItem,
				itemDesc, itemQuantity, itemPrice, discountType, discountValue, freightValue, validateResume );
		step( "Navega para listagem da nova venda e cria uma nova venda." );

		markLikePayed( client, bankAccount, received, createIncomePage, financeExtractPage );
		step( "Vai para o financeiro busca essa venda e marca como paga" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.deleteLastSale( 0 );
		checkPoint( "Mensagem de aviso não aparece ou está incorreto", salePage.msgDeleteOneFirstLine(),
				salePage.getWarningDeleteFirstLine() );
		checkPoint( "Mensagem de aviso não aparece ou está incorreto", salePage.msgDeleteSecondLine(),
				salePage.getWarningDeleteMoreThanOneSecondLine() );
		salePage.clickBtnWarningDelete();
		step( "Volta para a lsitagem da nova venda e tenta deletar validando a mensagem de erro" );

		undoPay( client, financeFlowPage, financeExtractPage );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.deleteAllSales();
		step( "Navega para o financeiro marca como não paga, volta para listagem e deleta a venda" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void deleteTwoNewSale(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String qtd, String discountType, String discountValue, String freightValue,
			String bankAccount, String received, String typeItem, String itemDesc, String itemQuantity,
			String itemPrice, String validateResume) {

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );

		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		FinanceFlowPage financeFlowPage = getPaginas().getFinanceFlowPage( getWebDriver() );
		NewSalePage salePage = getPaginas().getNewSalePage( getWebDriver() );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );

		newSale = new CreateNewSaleTest();
		newSale.setDriver( getWebDriver() );
		newSale.CreateNew( client, saleNumber, saleDate, paymentType, paymentMaturityDate, qtd, typeItem,
				itemDesc, itemQuantity, itemPrice, discountType, discountValue, freightValue, validateResume );
		salePage.goBackToSaleList();
		newSale.CreateNew( client, saleNumber, saleDate, paymentType, paymentMaturityDate, qtd, typeItem,
				itemDesc, itemQuantity, itemPrice, discountType, discountValue, freightValue, validateResume );
		step( "Cria duas novas vendas" );

		markLikePayed( client, bankAccount, received, createIncomePage, financeExtractPage );
		step( "Marca uma como paga" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.deleteAllSales();
		checkPoint( "Mensagem de aviso não aparece ou está incorreto", salePage.msgDeleteMoreThanOneFirstLine(),
				salePage.getWarningDeleteMoreThanOneFirstLine() );
		checkPoint( "Mensagem de aviso não aparece ou está incorreto", salePage.msgDeleteMoreThanOneSecondtLine(),
				salePage.getWarningDeleteSecondLine() );
		salePage.clickBtnWarningDelete();
		step( "Volta para listagem da nova venda seleciona todas e deleta, valida a mensagem de erro" );

		undoPay( client, financeFlowPage, financeExtractPage );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.deleteAllSales();
		step( "Volta para o financeiro e marca como não paga, voltando para a listagem e deletando as duas vendas." );

		checkPoint( "Venda não paga não foi excluida", "Vendas selecionadas foram excluídas com sucesso.",
				getAssistants()
						.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		step( "Verifica a mensagem de exlucussão com sucesso" );

	}

	private void markLikePayed(String client, String bankAccount, String received, CreateIncomePage createIncomePage,
			FinanceExtractPage financeExtractPage) {
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
		financeExtractPage.search( client );
		getAssistants().getListingAssistant( getWebDriver() ).getListingRowAsElement( 1 ).click();
		createIncomePage.setBankAccount( bankAccount );
		createIncomePage.setReceived( Boolean.parseBoolean( received ) );
		createIncomePage.getValue();
		createIncomePage.clickAddIncomeButton();
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
	}

	private void undoPay(String client, FinanceFlowPage financeFlowPage, FinanceExtractPage financeExtractPage) {
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
		financeExtractPage.search( client );
		financeFlowPage.showRolloverMenu( 0 );
		financeFlowPage.chooseOptionMenuRollover( RolloverMenuOptions.UNDO_PAY );
		financeFlowPage.closeRolloverMenuOpened();
	}

}
