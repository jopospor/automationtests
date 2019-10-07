package com.contaazul.auto.selenium.financeiro.testcases;

import java.util.Date;

import org.testng.Assert;

import com.contaazul.auto.data.DateFormatUtil;
import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.DeleteIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage.RolloverMenuOptions;

public class DeleteFinanceStatementTest extends SeleniumTest {

	private DeleteIncomePage deleteIncomePage;
	private FinanceFlowPage financeFlowPage;
	private ListingAssistant listingAssistant;

	public void deleteFinanceStatementTest(String statementType) {
		deleteIncomePage = new DeleteIncomePage( getWebDriver() );
		financeFlowPage = new FinanceFlowPage( getWebDriver() );
		listingAssistant = getAssistants().getListingAssistant( getWebDriver() );

		//createPrerequisiteFinantialStatements( statementType );

		String moduleName = statementType.equals( "#EXPENSE" ) ? "Contas a Pagar" : "Contas a Receber";
		String statementTypeTranslated = SeleniumSession.getSession().getLocale().translate( statementType );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", moduleName );

		sleep( FOR_A_LONG_TIME );

		step( "Exclui " + statementTypeTranslated + " pelo RolloverMenu." );
		deleteThroughOfRolloverMenu( 0 );
		checkPoint( "Não foi mostrada mensagem de sucesso",
				"Lançamento(s) removido(s) com sucesso",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );

		checkPoint( "Mensagem de sucesso foi mostrada, mas não era esperada", true,
				deleteIncomePage.isSuccessMessageDisplayed(), true );

		step( "Exclui uma " + statementTypeTranslated + "." );
		deleteOneIncome();
		checkPoint( "Não foi mostrada mensagem de sucesso", "Lançamento(s) removido(s) com sucesso",
				deleteIncomePage.getSucessMessage(), true );

		checkPoint( "Mensagem de sucesso foi mostrada, mas não era esperada", true,
				deleteIncomePage.isSuccessMessageDisplayed(), true );

		step( "Exclui várias " + statementTypeTranslated + "s." );
		deleteSeveralIncomes( 2 );
		checkPoint( "Não foi mostrada mensagem de sucesso", "Lançamento(s) removido(s) com sucesso",
				deleteIncomePage.getSucessMessage(), true );

		checkPoint( "Mensagem de sucesso foi mostrada, mas não era esperada", true,
				deleteIncomePage.isSuccessMessageDisplayed(), true );

		step( "Exclui todas as " + statementTypeTranslated + "s." );
		deleteAllIncomes();
		checkPoint( "Não foi mostrada mensagem de sucesso", "Lançamento(s) removido(s) com sucesso",
				deleteIncomePage.getSucessMessage(), true );
	}

	private void createPrerequisiteFinantialStatements(String statementType) {

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		String dataInicial = DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE );

		for (int i = 0; i < 8; i++) {
			if (statementType.equals( "#EXPENSE" ))
				financeExtractPage.navigateToNewExpense();
			else
				financeExtractPage.navigateToNewIncome();

			if (statementType.equals( "#EXPENSE" ))
				financeExtractPage.clickAdicionarDespesas();
			else
				financeExtractPage.clickAdicionarReceita();

			CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
			step( "Preenche o formulário Adicionar " + statementType );
			createIncomePage.setIncomeName( "Lançamento de testes" );
			createIncomePage.setValue( "123456" );
			createIncomePage.setBankAccount( "Cartão de Crédito" );
			createIncomePage.setExpirationDate( "18/02/2014" );
			createIncomePage.setIncomeCategory( "Ajuste Caixa" );
			createIncomePage.setReceived( Boolean.parseBoolean( "true" ) );
			createIncomePage.setRepeat( Boolean.parseBoolean( "false" ) );
			createIncomePage.setCustomer( "" );
			createIncomePage.setCompetenceDate( "" );
			createIncomePage.setCostCenter( "" );
			createIncomePage.setObservations( i + " of 8" );

			createIncomePage.clickAddIncomeButton();
		}

	}

	private void deleteOneIncome() {
		listingAssistant.checkRow( 1 );

		deleteThroughOfActionsButton();
	}

	private void deleteSeveralIncomes(Integer numRows) {
		for (int i = 1; i <= numRows; i++) {
			listingAssistant.checkRow( i );
		}

		deleteThroughOfActionsButton();
	}

	private void deleteAllIncomes() {
		listingAssistant.checkAllItems();

		deleteThroughOfActionsButton();
	}

	private void deleteThroughOfActionsButton() {
		listingAssistant.clickActionsButton();
		listingAssistant.clickDeleteIncomeButton();
		verifyNumberOfRecordsWillBeDeleted();
		deleteIncomePage.clickDeleteNowButton();
	}

	private void deleteThroughOfRolloverMenu(int rowNumber) {
		financeFlowPage.showRolloverMenu( rowNumber );
		financeFlowPage.chooseOptionMenuRollover( RolloverMenuOptions.DELETE );
		verifyNumberOfRecordsWillBeDeleted();
		deleteIncomePage.clickDeleteNowButton();
	}

	private void verifyNumberOfRecordsWillBeDeleted() {
		Assert.assertEquals( listingAssistant.getTotalSelectedRows(),
				deleteIncomePage.getNumberOfRecordsWillBeDeleted(),
				"Número de registros na mensagem de exclusão não confere com o número de registros selecionados." );
	}

}
