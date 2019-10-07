package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.FinancialAssistant;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class ExtractSearchTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void extractFindAndListReleased(String dtAtual, String pesquisar, String nomeReceita, String valor, String
			nomeBanco, String categoria, String cliente, String dtEmissao, String centroDeCusto, String
			observacao, String dtVencimento, String categorias, String CentroDeCusto, String ClientesFornecedores,
			String Descricao) {

		FinancialAssistant transfer = getPaginas().getFinancialTransferPage( getWebDriver() );
		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );
		FinanceExtractPage fin = getPaginas().getFinanceExtractPage( getWebDriver() );
		CreateIncomePage createIncome = getPaginas().getCreateIncomePage( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"ExtractSearchTest@contaazul.com", "12345" );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Movimentações", "Extrato" );

		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );

		step( "Filtra por vencidos, pagos e recebidos CA-4242" );
		filterStatus( fin );

		step( "Edita a data e clica em salvar." );
		fin.btnEdit();
		CreateIncomePage createIncomePage = new CreateIncomePage( getWebDriver() );
		createIncomePage.setExpirationDate( dtAtual );

		step( "Cria um novo lançamento preenchendo todos os campos." );
		createNewRelease( nomeReceita, valor, nomeBanco, categoria, cliente, dtEmissao, centroDeCusto, observacao,
				dtVencimento, transfer, fin, createIncome );

		step( "Faz filtro para mostrar lançamentos por datas." );
		filterDates( fin );

		step( "Faz a busca pelo campo de pesquisa e após a busca limpa ele." );
		fin.search( pesquisar );
		fin.searchClean();

		step( "Filtra pelo status, banco e data." );
		filterBankDate( fin );

		step( "Campo de Pesquisa Avançada." );
		fin.advancedSearch( categorias, CentroDeCusto, ClientesFornecedores, Descricao );

		step( "Seleciona a Receita cadatrada, exclui e faz logout" );
		fin.selectFirstLineCheckbox();
		importBankExtract.selectAction( "Excluir" );

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void filterStatus(FinanceExtractPage fin) {
		fin.filterStatus( "Vencidos" );
		fin.filterStatus( "Pagos / Recebidos" );
		fin.filterStatus( "Contrato de Venda" );
		fin.filterStatus( "Todos" );
		fin.filterStatus( "Contas a Pagar / Receber" );
		fin.filterStatus( "Todos" );
	}

	private void filterBankDate(FinanceExtractPage fin) {
		fin.filterStatus( "Recebido" );
		fin.filterStatus( "Todos" );
		fin.filterStatusBank( "Bradesco" );
		fin.selectDropdownOptionDate( "Hoje" );
		fin.selectDropdownOptionToday( "Hoje" );
	}

	private void filterDates(FinanceExtractPage fin) {
		fin.selectDropdownOptionDate( "Hoje" );
		fin.selectDropdownOptionToday( "Hoje" );
		fin.selectDropdownOptionDate( "Esta semana" );
		fin.selectDropdownOptionWeek( "Esta semana" );
		fin.selectDropdownOptionDate( "Este mês" );
		fin.selectDropdownOptionMonth( "Este mês" );
		fin.selectDropdownOptionDate( "Todos" );
		fin.selectDropdownOptionAll( "Todos" );
	}

	private void createNewRelease(String nomeReceita, String valor, String nomeBanco, String categoria, String cliente,
			String dtEmissao, String centroDeCusto, String observacao, String dtVencimento,
			FinancialAssistant transfer, FinanceExtractPage fin, CreateIncomePage createIncomePage) {
		fin.btnAddRevenue();
		fin.newRevenue();
		createIncomePage.setIncomeName( nomeReceita );
		createIncomePage.setValue( valor );
		createIncomePage.setBankAccount( nomeBanco );
		createIncomePage.setExpirationDate( dtVencimento );
		createIncomePage.setIncomeCategory( categoria );
		createIncomePage.openMoreOptions();
		createIncomePage.setCustomer( cliente );
		createIncomePage.setCompetenceDate( dtEmissao );
		createIncomePage.setCostCenter( centroDeCusto );
		createIncomePage.setObservations( observacao );
		createIncomePage.clickAddIncomeButton();
	}
}
