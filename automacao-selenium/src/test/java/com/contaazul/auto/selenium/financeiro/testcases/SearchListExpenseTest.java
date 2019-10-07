package com.contaazul.auto.selenium.financeiro.testcases;

import java.util.Date;

import org.testng.annotations.Test;

import com.contaazul.auto.data.DateFormatUtil;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage.AdvancedSearchOptions;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateBankAccountPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.IncomePage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class SearchListExpenseTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void SearhListExpenseTest(String Pesquisa1, String Pesquisa2) {

		CreateIncomePage createIncome = getPaginas().getCreateIncomePage( getWebDriver() );
		BankAccountsPage bankAccountPage = getPaginas().getPaginaContasBancarias( getWebDriver() );
		FinanceExtractPage fin = getPaginas().getFinanceExtractPage( getWebDriver() );
		CreateBankAccountPage createBankAccountPage = getPaginas().getPaginaAdicionarContaBancaria( getWebDriver() );
		IncomePage<AccountReceivablePage> editIncomePage = getPaginas().getIncomePage( getWebDriver() );
		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );

		step( "Faz login no sistema, pesquisa contas e edita a data" );
		AccountReceivablePage accountReceivablePage = loginAndSearchEditDate(
				Pesquisa1, Pesquisa2 );

		step( "Selecina filtro de periodo e por status e mostra o resultado" );

		fin.filterStatus( "Vencidos" );

		step( "Selecina filtro de periodo e por status e mostra o resultado" );

		fin.filterStatus( "Pago" );

		step( "Selecina filtro de periodo e por status e mostra o resultado" );

		fin.filterStatus( "Contas a Pagar" );

		step( "Selecina filtro de periodo e por status e mostra o resultado" );

		fin.filterStatus( "Todos" );

		step( "Navega pra o menu de Contas Bancárias e cria uma nova conta" );
		createNewBank( bankAccountPage, createBankAccountPage );

		step( "Pesquisa uma conta criada, edita e busca pelo filtro." );
		searchAccountEditFilter( createBankAccountPage, accountReceivablePage, editIncomePage );

		step( "Faz filtro de lançamentos" );
		filterLaunch( accountReceivablePage );

		step( "Cria uma nova despesa, abre 'Mostrar mais opções' e preenche os campos" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Movimentações",
				"Contas a Pagar" );
		fillNewExpense( createIncome );

		step( "Pesquisa Avançada" );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		step( "Data selecionada" );
		accountReceivablePage.advancedSearch( new AdvancedSearchOptions( "Aluguel", "Centro 002",
				"Conta Azul Ltda", "Teste Despesa" ) );

		step( "Faz a exclusão da despesa criada" );
		fin.selectFirstLineCheckbox();
		importBankExtract.selectAction( "Excluir" );

		step( "Exclui Banco cadastrado" );
		deleteBank( bankAccountPage );

		getAssistants().getLoginAssistant( getWebDriver() ).logout();

	}

	private AccountReceivablePage loginAndSearchEditDate(String Pesquisa1, String Pesquisa2) {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "SearchListExpenseTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Pagar" );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		editDate( Pesquisa1 );
		editDate( Pesquisa2 );
		return accountReceivablePage;
	}

	private void deleteBank(BankAccountsPage bankAccountPage) {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_BANCARIAS );
		sleep( 4000 );
		bankAccountPage.clickCheckbox( "Santander" );
		bankAccountPage.excluir();
	}

	private void fillNewExpense(CreateIncomePage createIncome) {
		String dataInicial = DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE );
		createIncome.newExpense();
		createIncome.setIncomeName( "Teste Despesa" );
		createIncome.setValue( "100,00" );
		createIncome.setBankAccount( "Santander" );
		createIncome.setExpirationDate( dataInicial );
		createIncome.setIncomeCategory( "Aluguel" );
		createIncome.openMoreOptions();
		createIncome.setCustomer( "Conta Azul Ltda" );
		createIncome.setCostCenter( "Centro 002" );
		createIncome.setObservations( "Teste Despesa" );
		createIncome.setCompetenceDate( dataInicial );
		createIncome.clickAddIncomeButton();
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
	}

	private void filterLaunch(AccountReceivablePage accountReceivablePage) {
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		accountReceivablePage.filterBankAccount( "Todos" );
		accountReceivablePage.filterPeriod( Period.HOJE );
		accountReceivablePage.filterPeriod( Period.ESTA_SEMANA );
		accountReceivablePage.filterPeriod( Period.ESTE_MES );
		accountReceivablePage.filterPeriod( Period.HOJE );
		accountReceivablePage.filterPeriod( Period.ULTIMOS_30_DIAS );
	}

	private void searchAccountEditFilter(CreateBankAccountPage createBankAccountPage,
			AccountReceivablePage accountReceivablePage,
			IncomePage<AccountReceivablePage> editIncomePage) {

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Pagar" );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		accountReceivablePage.search( "Despesa vencimento no passado e definida como não paga" );
		getAssistants().getListingAssistant( getWebDriver() ).getListingRowAsElement( 1 ).click();

		CreateIncomePage createIncome = getPaginas().getCreateIncomePage( getWebDriver() );
		createIncome.clearBankAccount();

		createIncome.editBank( "Santander" );

		accountReceivablePage.filterBankAccount( "Santander" );
		accountReceivablePage.search( "" );
	}

	private void createNewBank(BankAccountsPage bankAccountPage, CreateBankAccountPage createBankAccountPage) {
		sleep( VERY_LONG );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_BANCARIAS );
		bankAccountPage.clicarNovaConta();
		createBankAccountPage.setNomeDaConta( "Santander" );
		createBankAccountPage.setBanco( "Santander" );
		createBankAccountPage.setSaldoInicial( "1.000,00" );
		createBankAccountPage.clicarSalvar();
	}

	private void editDate(String Pesquisa) {
		getAssistants().getListingAssistant( getWebDriver() ).searchByKeyword( Pesquisa );
		String dataInicial = DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE );
		sleep( VERY_LONG );
		getAssistants().getListingAssistant( getWebDriver() ).getListingRowAsElement( 1 ).click();
		CreateIncomePage editPage = getPaginas().getCreateIncomePage( getWebDriver() );
		sleep( 4000 );
		editPage.setExpirationDate( dataInicial );
		sleep( VERY_LONG );
		editPage.clickAddIncomeButton();
		getAssistants().getListingAssistant( getWebDriver() ).searchByKeyword( "" );
	}
}
