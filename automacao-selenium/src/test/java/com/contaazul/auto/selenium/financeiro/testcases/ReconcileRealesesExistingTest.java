package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.FinancialAssistant;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage.RolloverMenuOptions;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class ReconcileRealesesExistingTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void reconcileRealesesExisting(String conta, String arquivo, String nrLine) {

		FinancialAssistant financialTransfer = getPaginas().getFinancialTransferPage( getWebDriver() );
		FinanceFlowPage financeFlow = getPaginas().getFinanceFlowPage( getWebDriver() );
		ImportBankExtractPage bankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );

		step( "Executa o login e navega para a tela de importar extrato" );
		getAssistants().getLoginAssistant( getWebDriver() ).login( "ReconcileRealesesExisting@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Pagar" );

		String contaId = conta + "_" + Double.toString( Math.random() );

		step( "Altera a conta bancaria para a criada anteriormente" );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		if (listingAssistant.isReceivedOnRow( 1 ) == true) {
			financeFlow.showRolloverMenu( 0 );
			financeFlow.chooseOptionMenuRollover( RolloverMenuOptions.UNDO_PAY );
		}
		listingAssistant.getListingRowAsElement( 1 ).click();
		sleep( VERY_LONG );
		createIncomePage.setBankAccount( contaId );
		createIncomePage.clickAddIncomeButton();

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );

		step( "Altera a conta bancaria para a criada anteriormente" );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		if (listingAssistant.isReceivedOnRow( 1 ) == true) {
			financeFlow.showRolloverMenu( 0 );
			financeFlow.chooseOptionMenuRollover( RolloverMenuOptions.UNDO_PAY );
		}
		listingAssistant.getListingRowAsElement( 1 ).click();
		sleep( VERY_LONG );
		createIncomePage.setBankAccount( contaId );
		createIncomePage.clickAddIncomeButton();

		step( "Clica em importar novo extrato seleciona uma conta existe e faz upload do arquivo OFX" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );
		getAssistants().getDeleteAssistant( getWebDriver() ).deleteAllFinancialStatements();
		uploadOfx( contaId, arquivo, financialTransfer, bankExtract );
		step( "Concilia a despesa e faz verificação de validação" );
		String msgAlert = concileExpense( nrLine, financeFlow );
		checkPoint( "Lançamentos de conciliação não foram executados", "Lançamento conciliado com sucesso!", msgAlert );

		step( "Concilia a receita e faz verificação de validação, e espera a mensagem no topo da tela sumir" );
		concileRevenue( nrLine, financeFlow );
		checkPoint( "Lançamentos de conciliação não foram executados", "Lançamento conciliado com sucesso!", msgAlert );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();

		step( "Verifica se os lançamentos foram removidos" );
		checkPoint( "Lançamentos não foram excluidos", "Importar meu extrato bancário",
				getAssistants().getNotificationAssistant( getWebDriver() ).msgConcileValidation() );

		step( "Navega para a tela de extrato volta a despesa e receita para não paga." );
		undoPay( financeFlow, accountReceivablePage );

		BankAccountsPage bank = getPaginas().getPaginaContasBancarias( getWebDriver() );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_BANCARIAS );
		bank.clickCheckbox( contaId );
		bank.excluir();

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void undoPay(FinanceFlowPage financeFlow, AccountReceivablePage accountReceivablePage) {
		financeFlow.clickExtract();
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		financeFlow.showRolloverMenu( 0 );
		financeFlow.chooseOptionMenuRollover( RolloverMenuOptions.UNDO_PAY );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		financeFlow.showRolloverMenu( 1 );
		financeFlow.chooseOptionMenuRollover( RolloverMenuOptions.UNDO_PAY );
	}

	private void concileRevenue(String nrLine, FinanceFlowPage financeFlow) {
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		financeFlow.launchConcile( nrLine );
	}

	private String concileExpense(String nrLine, FinanceFlowPage financeFlow) {
		financeFlow.launchConcile( nrLine );
		String msgAlert = getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText();
		return msgAlert;
	}

	private void uploadOfx(String contaId, String arquivo, FinancialAssistant financialTransfer,
			ImportBankExtractPage bankExtract) {
		bankExtract.btnNewExtractOfx();
		bankExtract.selectBankAccount( contaId );
		sleep( 6000 );
		bankExtract.importExtractOfx( arquivo );
		bankExtract.clickLaunchConcileButton();
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
	}
}
