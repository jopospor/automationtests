package com.contaazul.auto.selenium.financeiro.testcases;

import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.data.DateFormatUtil;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.DeleteIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage.RolloverMenuOptions;

public class DeleteRevenueTest extends DeleteFinanceStatementTest {

	@Test
	public void deleteRevenueByRolloverMenuTest() {

		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		DeleteIncomePage deleteIncomePage = new DeleteIncomePage( getWebDriver() );
		FinanceFlowPage financeFlowPage = new FinanceFlowPage( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login( "DeleteRevenueTest@contaazul.com",
				"12345" );
		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );

		financeFlowPage.navigateToNewIncome();
		step( "Vai para o formulário Adicionar Despesa" );

		createIncomePage.setIncomeName( "Lançamento de testes 1" );
		createIncomePage.setBankAccount( "Cartão de Crédito" );
		createIncomePage.setExpirationDate( DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE ) );
		createIncomePage.setIncomeCategory( "Ajuste Caixa" );
		createIncomePage.setReceived( Boolean.parseBoolean( "true" ) );
		createIncomePage.setRepeat( Boolean.parseBoolean( "false" ) );
		createIncomePage.setCustomer( "" );
		createIncomePage.setCompetenceDate( "" );
		createIncomePage.setCostCenter( "" );
		createIncomePage.setValue( "123456" );
		step( "Preenche o formulário Adicionar Despesa" );

		createIncomePage.clickAddIncomeButton();
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		step( "Clica Adicionar Despesa" );

		financeFlowPage.showRolloverMenu( 0 );
		financeFlowPage.chooseOptionMenuRollover( RolloverMenuOptions.DELETE );
		deleteIncomePage.clickDeleteNowButton();
		step( "Exclui Despesa pelo RolloverMenu." );

		checkPoint( "Não foi mostrada mensagem de sucesso",
				"Lançamento(s) removido(s) com sucesso",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	@Test
	public void deleteOneRevenueTest() {

		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		DeleteIncomePage deleteIncomePage = new DeleteIncomePage( getWebDriver() );
		FinanceFlowPage financeFlowPage = new FinanceFlowPage( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login( "DeleteRevenueTest@contaazul.com",
				"12345" );
		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );

		financeFlowPage.navigateToNewIncome();
		step( "Vai para o formulário Adicionar Despesa" );

		createIncomePage.setIncomeName( "Lançamento de testes 2" );
		createIncomePage.setBankAccount( "Cartão de Crédito" );
		createIncomePage.setExpirationDate( DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE ) );
		createIncomePage.setIncomeCategory( "Ajuste Caixa" );
		createIncomePage.setReceived( Boolean.parseBoolean( "true" ) );
		createIncomePage.setRepeat( Boolean.parseBoolean( "false" ) );
		createIncomePage.setCustomer( "" );
		createIncomePage.setCompetenceDate( "" );
		createIncomePage.setCostCenter( "" );
		createIncomePage.setValue( "123456" );
		step( "Preenche o formulário Adicionar Despesa" );

		createIncomePage.clickAddIncomeButton();
		getAssistants().getNotificationAssistant( getWebDriver() ).waitAlertMessageDismiss();
		step( "Clica Adicionar Despesa" );

		listingAssistant.checkRow( 1 );

		listingAssistant.clickActionsButton();
		listingAssistant.clickDeleteIncomeButton();
		deleteIncomePage.clickDeleteNowButton();
		step( "Seleciona a despesa e clica em Excluir" );

		checkPoint( "Não foi mostrada mensagem de sucesso",
				"Lançamento(s) removido(s) com sucesso",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );

		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	@Test
	public void deleteSeveralExpensesTest() {

		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		DeleteIncomePage deleteIncomePage = new DeleteIncomePage( getWebDriver() );
		FinanceFlowPage financeFlowPage = new FinanceFlowPage( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login( "DeleteRevenueTest@contaazul.com",
				"12345" );
		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );

		financeFlowPage.navigateToNewIncome();
		step( "Vai para o formulário Adicionar Despesa" );

		createIncomePage.setIncomeName( "Lançamento de testes 3" );
		createIncomePage.setBankAccount( "Cartão de Crédito" );
		createIncomePage.setExpirationDate( DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE ) );
		createIncomePage.setIncomeCategory( "Ajuste Caixa" );
		createIncomePage.setReceived( Boolean.parseBoolean( "true" ) );
		createIncomePage.setRepeat( Boolean.parseBoolean( "false" ) );
		createIncomePage.setCustomer( "" );
		createIncomePage.setCompetenceDate( "" );
		createIncomePage.setCostCenter( "" );
		createIncomePage.setValue( "123456" );
		step( "Preenche o formulário Adicionar Despesa" );

		createIncomePage.clickAddIncomeButton();
		sleep( FOR_A_LONG_TIME );
		step( "Clica Adicionar Despesa" );

		financeFlowPage.navigateToNewIncome();
		step( "Vai para o formulário Adicionar Despesa" );

		createIncomePage.setIncomeName( "Lançamento de testes 4" );
		createIncomePage.setBankAccount( "Cartão de Crédito" );
		createIncomePage.setExpirationDate( DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE ) );
		createIncomePage.setIncomeCategory( "Ajuste Caixa" );
		createIncomePage.setReceived( Boolean.parseBoolean( "true" ) );
		createIncomePage.setRepeat( Boolean.parseBoolean( "false" ) );
		createIncomePage.setCustomer( "" );
		createIncomePage.setCompetenceDate( "" );
		createIncomePage.setCostCenter( "" );
		createIncomePage.setValue( "123456" );
		step( "Preenche o formulário Adicionar Despesa" );

		createIncomePage.clickAddIncomeButton();
		getAssistants().getNotificationAssistant( getWebDriver() ).waitAlertMessageDismiss();
		step( "Clica Adicionar Despesa" );

		listingAssistant.checkRow( 1 );
		listingAssistant.checkRow( 2 );

		listingAssistant.clickActionsButton();
		listingAssistant.clickDeleteIncomeButton();
		deleteIncomePage.clickDeleteNowButton();
		step( "Seleciona 2 despesas e clica em Excluir" );

		checkPoint( "Não foi mostrada mensagem de sucesso",
				"Lançamento(s) removido(s) com sucesso",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );

		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	@Test
	public void deleteAllRevenueTest() {
		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		DeleteIncomePage deleteIncomePage = new DeleteIncomePage( getWebDriver() );
		FinanceFlowPage financeFlowPage = new FinanceFlowPage( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login( "DeleteRevenueTest@contaazul.com",
				"12345" );
		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );

		financeFlowPage.navigateToNewIncome();
		step( "Vai para o formulário Adicionar Despesa" );

		createIncomePage.setIncomeName( "Lançamento de testes 5" );
		createIncomePage.setBankAccount( "Cartão de Crédito" );
		createIncomePage.setExpirationDate( DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE ) );
		createIncomePage.setIncomeCategory( "Ajuste Caixa" );
		createIncomePage.setReceived( Boolean.parseBoolean( "true" ) );
		createIncomePage.setRepeat( Boolean.parseBoolean( "false" ) );
		createIncomePage.setCustomer( "" );
		createIncomePage.setCompetenceDate( "" );
		createIncomePage.setCostCenter( "" );
		createIncomePage.setValue( "123456" );
		step( "Preenche o formulário Adicionar Despesa" );

		createIncomePage.clickAddIncomeButton();
		sleep( FOR_A_LONG_TIME );
		step( "Clica Adicionar Despesa" );

		financeFlowPage.navigateToNewIncome();
		step( "Vai para o formulário Adicionar Despesa" );

		createIncomePage.setIncomeName( "Lançamento de testes 6" );
		createIncomePage.setBankAccount( "Cartão de Crédito" );
		createIncomePage.setExpirationDate( DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE ) );
		createIncomePage.setIncomeCategory( "Ajuste Caixa" );
		createIncomePage.setReceived( Boolean.parseBoolean( "true" ) );
		createIncomePage.setRepeat( Boolean.parseBoolean( "false" ) );
		createIncomePage.setCustomer( "" );
		createIncomePage.setCompetenceDate( "" );
		createIncomePage.setCostCenter( "" );
		createIncomePage.setValue( "123456" );
		step( "Preenche o formulário Adicionar Despesa" );

		createIncomePage.clickAddIncomeButton();
		sleep( FOR_A_LONG_TIME );
		step( "Clica Adicionar Despesa" );

		listingAssistant.checkAllItems();
		listingAssistant.clickActionsButton();
		listingAssistant.clickDeleteIncomeButton();
		deleteIncomePage.clickDeleteNowButton();
		step( "Seleciona todas despesas e clica em Excluir" );

		checkPoint( "Não foi mostrada mensagem de sucesso", "Lançamento(s) removido(s) com sucesso",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText(), true );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
