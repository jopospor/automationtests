package com.contaazul.auto.selenium.vendas.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage.RolloverMenuOptions;
import com.contaazul.auto.selenium.financeiro.pages.Period;
import com.contaazul.auto.selenium.vendas.pages.NewSalePage;

public class DeleteNewSalePayedPlotsTest extends SeleniumTest {

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() )
				.login( "deleteNewSalePayedPlotsTest@contaazul.com", "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void deleteNewSalePLayedPlotsTest(String client, String saleDate, String paymentType,
			String paymentMaturityDate, String quantityItens, String discountType, String discountValue,
			String freightValue, String bankAccount, String received) {

		NewSalePage salePage = getPaginas().getNewSalePage( getWebDriver() );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.addNewSale();
		salePage.setSaleClient( client );
		salePage.setSaleDate( saleDate );
		salePage.setSalePaymentType( paymentType );
		salePage.setPaymentMaturityDate( paymentMaturityDate );
		salePage.fillRandomItemsList( quantityItens, discountType, discountValue, freightValue );
		salePage.saveNewSale();
		step( "Cria uma nova venda preenchendo os campos obrigatórios e salvando" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		financeExtractPage.search( client );
		getAssistants().getListingAssistant( getWebDriver() ).getListingRowAsElement( 1 ).click();
		createIncomePage.setBankAccount( bankAccount );
		createIncomePage.setReceived( Boolean.parseBoolean( received ) );
		createIncomePage.getValue();
		createIncomePage.clickAddIncomeButton();
		step( "Vai para o financeiro busca pelo nome do cliente a marca a primeria receita como paga" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.deleteAllSales();
		checkPoint( "Mensagem de aviso não aparece ou está incorreto", salePage.msgDeleteOneFirstLine(),
				salePage.getWarningDeleteFirstLine() );
		checkPoint( "Mensagem de aviso não aparece ou está incorreto", salePage.msgDeleteSecondLine(),
				salePage.getWarningDeleteMoreThanOneSecondLine() );
		salePage.clickBtnWarningDelete();
		step( "Volta para listagem da nova venda e tenta deletar a venda criada validando as mensagens" );

		FinanceFlowPage financeFlowPage = getPaginas().getFinanceFlowPage( getWebDriver() );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
		financeExtractPage.search( client );
		financeFlowPage.showRolloverMenu( 0 );
		financeFlowPage.chooseOptionMenuRollover( RolloverMenuOptions.UNDO_PAY );
		financeFlowPage.closeRolloverMenuOpened();
		step( "Volta para o financeiro marca como não paga a receita" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage.deleteAllSales();
		step( "Deleta as nova venda criada" );
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
	}
}
