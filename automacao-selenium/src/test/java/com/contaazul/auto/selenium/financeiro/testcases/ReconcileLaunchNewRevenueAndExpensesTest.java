package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceImportExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class ReconcileLaunchNewRevenueAndExpensesTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void reconcileLaunchNewRevenueAndExpensesTest(String incomeName, String value, String bankAccount,
			String expirationDate, String incomeCategory, String received, String conta, String arquivo) {

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"ReconcileLaunchNewRevenueAndExpensesTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Extrato" );
		CreateIncomePage createIncomePage = getPaginas().getCreateIncomePage( getWebDriver() );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		ImportBankExtractPage importBankExtractPage = getPaginas().getImportBankExtractPage( getWebDriver() );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );

		listingAssistant.filterByPeriod( "Mostrar todos" );
		getAssistants().getDeleteAssistant( getWebDriver() ).deleteAllFinancialStatements();
		step( "Verifica se existe uma receita ou despesa ja criada, se existir a deleta." );

		financeExtractPage.btnOpenMenuExtract( "Novo Pagamento" );
		createIncomePage.setBankAccount( bankAccount );
		createIncomePage.setExpirationDate( expirationDate );
		createIncomePage.setIncomeCategory( incomeCategory );
		createIncomePage.setReceived( Boolean.parseBoolean( received ) );
		sleep( 2000 );
		createIncomePage.setValue( value );
		sleep( 2000 );
		createIncomePage.setIncomeName( incomeName );
		sleep( 2000 );
		createIncomePage.clickAddIncomeButton();
		step( "Cadastra uma nova despesa." );

		financeExtractPage.btnOpenMenuExtract( "Novo Recebimento" );
		createIncomePage.setBankAccount( bankAccount );
		createIncomePage.setExpirationDate( expirationDate );
		createIncomePage.setIncomeCategory( incomeCategory );
		createIncomePage.setReceived( Boolean.parseBoolean( received ) );
		sleep( 2000 );
		createIncomePage.setIncomeName( incomeName );
		sleep( 2000 );
		createIncomePage.setValue( value );
		sleep( 2000 );
		createIncomePage.clickAddIncomeButton();
		step( "Cadastra uma nova receita." );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.IMPORTAR_EXTRATO );
		FinanceImportExtractPage financeImportExtractPage = getPaginas().getPaginaImportacaoExtrato( getWebDriver() );

		financeImportExtractPage.clickImportarExtrato();
		String contaId = conta + "_" + Double.toString( Math.random() );
		financeImportExtractPage.informarContaBancariaExistente( contaId );
		financeImportExtractPage.selecionarArquivoOFX( arquivo );
		financeImportExtractPage.clickContinuar();
		financeImportExtractPage.clickFecharTelaConciliacao();
		step( "Seleciona arquivo OFX e importa." );

		importBankExtractPage.rolloverAndClickMenuConciliation();
		getPaginas().getAccountReceivablePage( getWebDriver() );
		sleep( VERY_QUICKLY );
		financeImportExtractPage.selectDateConciliation( Period.MOSTRAR_TODOS );
		financeImportExtractPage.selectBankAccountConciliation();
		sleep( VERY_QUICKLY );
		financeExtractPage.cleanDropdownCheckboxes( "Todos" );
		financeImportExtractPage.selectOptionBank( "Banco do Brasil" );
		step( "Prestes a checar o radio" );
		financeImportExtractPage.selectCheckBoxSuggestionsConciliation();
		financeImportExtractPage.clickReconcileThisRelease();
		step( "Mensagem de sucesso popup" );
		checkPoint( "Sincronização não conseguiu clicar na proxima receita para conciliar.",
				"Lançamento conciliado com sucesso!",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );

		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		step( "Faz a conciliação do arquivo" );

		importBankExtractPage.rolloverAndClickMenuConciliation();
		getPaginas().getAccountReceivablePage( getWebDriver() );
		sleep( VERY_QUICKLY );
		financeImportExtractPage.selectDateConciliation( Period.MOSTRAR_TODOS );
		financeImportExtractPage.selectBankAccountConciliation();
		sleep( VERY_QUICKLY );
		financeExtractPage.cleanDropdownCheckboxes( "Todos" );
		financeImportExtractPage.selectOptionBank( "Banco do Brasil" );
		financeImportExtractPage.selectCheckBoxSuggestionsConciliation();
		financeImportExtractPage.clickReconcileThisRelease();
		step( "Faz a conciliacao atraves do menu rollover, selecionando o periodo e o tipo de conta." );

		step( "Valida mensagem sendo exibida" );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageAppear();
		checkPoint( "Sincronização não conseguiu acessar o menu de Extrato", "Lançamento conciliado com sucesso!",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		getAssistants().getFinancialAssistant( getWebDriver() ).navigateMenuFinance( "Extrato" );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );

		checkPoint( "Os valores não correspondem", "2.000,00", financeImportExtractPage.getTotalPeriod() );

		getAssistants().getListingAssistant( getWebDriver() ).checkAllItems();
		importBankExtractPage.selectAction( "Excluir" );
		BankAccountsPage bank = getPaginas().getPaginaContasBancarias( getWebDriver() );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_BANCARIAS );
		bank.clickCheckbox( contaId );
		bank.excluir();

		getAssistants().getLoginAssistant( getWebDriver() ).logout();

	}
}
