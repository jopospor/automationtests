package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.FinancialAssistant;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class SearchListTransferTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void searchListTransferTest(String accountOriginCredit, String accountOriginDestiny
			, String descriptionP, String valueTransferP) {

		FinancialAssistant transfer = getPaginas().getFinancialTransferPage( getWebDriver() );

		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		ImportBankExtractPage importBankExtractPage = getPaginas().getImportBankExtractPage( getWebDriver() );

		NotificationAssistant notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login( "SearchListTransferTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );

		step( "Cria uma nova transferencia." );
		String accountDestinyCredit1 = accountOriginDestiny + "_" + Double.toString( Math.random() );
		String accountOriginCredit1 = accountOriginCredit + "_" + Double.toString( Math.random() );

		createNewTransfer( accountOriginCredit,
				accountOriginDestiny,
				descriptionP, valueTransferP, financeExtractPage, importBankExtractPage );
		checkPoint( "Todos os campos não estão preenchidos", "Transferência realizada com sucesso",
				notificationAssistant.getAlertMessageText() );

		step( "Deixa apenas um filtro selecionado, 'Pagos/Recebidos'" );
		typeFilter1( financeExtractPage );

		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );

		step( "Verifica os valores dos campos." );
		checkPoint( "Os campos estão com valores incorretos", "Transferência de Saída",
				transfer.getTypeTransaction( "4", 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "CRÉDITO - CONTA CORRENTE",
				transfer.getNameTransaction( 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "-3.000,50", transfer.getValue( 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "-3.000,50", transfer.getBalance( 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "Transferência de Entrada",
				transfer.getTypeTransaction( "4", 2 ) );
		checkPoint( "Os campos estão com valores incorretos", "CRÉDITO - CONTA CORRENTE",
				transfer.getNameTransaction( 2 ) );
		checkPoint( "Os campos estão com valores incorretos", "3.000,50", transfer.getValue( 2 ) );
		checkPoint( "Os campos estão com valores incorretos", "0,00", transfer.getBalance( 2 ) );

		step( "Faz a verificacao de tela com o tipo de filtro 'Vencidos'" );
		typeFilterOverdue( financeExtractPage );
		checkPoint( "Resultado da tela diferente", "Extrato", transfer.getMessageBlankSlate() );

		step( "Faz a verificacao de tela com o tipo de filtro 'Pagos/Recebidos'" );
		typeFilter1( financeExtractPage );

		step( "Faz a verificacao de tela com o tipo de filtro 'Contrato de venda'" );
		typeFilterContractSale( financeExtractPage );
		checkPoint( "Resultado da tela diferente",
				"Nenhum lançamento encontrado!", transfer.getMessageTransaction() );

		step( "Volta para o filtro padrao" );
		typeFilterNormal( financeExtractPage );

		step( "Seleciona um novo filtro do tipo banco, verificando o banco de origem e faz a verificacao dos valores." );
		typeFilterBankDestiny( financeExtractPage, accountOriginCredit1 );
		checkPoint( "Os campos estão com valores incorretos", "Transferência de Saída",
				transfer.getTypeTransaction( "4", 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "CRÉDITO - CONTA CORRENTE",
				transfer.getNameTransaction( 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "-3.000,50", transfer.getValue( 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "-3.000,50", transfer.getBalance( 1 ) );

		typeFilterBankNormal( transfer, financeExtractPage );

		step( "Seleciona um novo filtro do tipo banco, verificando o banco de destino e faz a verificacao dos valores." );
		typeFilterBankOrigin( financeExtractPage, accountDestinyCredit1 );
		checkPoint( "Os campos estão com valores incorretos", "Transferência de Entrada",
				transfer.getTypeTransaction( "4", 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "CRÉDITO - CONTA CORRENTE",
				transfer.getNameTransaction( 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "3.000,50", transfer.getValue( 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "3.000,50", transfer.getBalance( 1 ) );

		step( "Remove os filtros e volta para o padrao." );
		typeFilterBankNormal( transfer, financeExtractPage );

		step( "Deleta as transferencias selecionadas." );
		getAssistants().getListingAssistant( getWebDriver() ).checkAllItems();
		importBankExtractPage.selectAction( "Excluir" );

		step( "Navega para a tela de contas bancárias e deleta as contas criada anteriormente, fazendo logout em sequência" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Organizar", "Contas bancárias" );
		BankAccountsPage bank = getPaginas().getPaginaContasBancarias(
				getWebDriver() );
		step( "Seleciona os bancos criados e os deleta, fazendo o logout em seguida" );
		deleteBankAccount( accountOriginCredit1, accountDestinyCredit1, transfer, bank,
				notificationAssistant );
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void deleteBankAccount(String accountOriginCredit, String accountDestinyCredit,
			FinancialAssistant transfer, BankAccountsPage bank, NotificationAssistant notificationAssistant) {
		bank.clickCheckbox( accountOriginCredit );
		bank.excluir();
		notificationAssistant.waitMessageDismiss();
		bank.clickCheckbox( accountDestinyCredit );
		bank.excluir();
	}

	private void typeFilterBankNormal(FinancialAssistant transfer, FinanceExtractPage financeExtractPage) {
		sleep( VERY_LONG );
		financeExtractPage.openDropdownOptionsAccountBank( "bank-filter" );
		financeExtractPage.selectDropdownOption( "bank-filter", "Todos" );
		financeExtractPage.applyFilterBank( "type-filter" );
	}

	private void typeFilterBankOrigin(FinanceExtractPage financeExctractPage, String accountDestinyCredit1) {
		financeExctractPage.filterStatusBank( accountDestinyCredit1 );
	}

	private void typeFilterBankDestiny(FinanceExtractPage financeExctractPage, String accountOriginCredit1) {
		financeExctractPage.filterStatusBank( accountOriginCredit1 );
	}

	private void typeFilterNormal(FinanceExtractPage financeExtractPage) {
		financeExtractPage.filterStatus( "Todos" );
	}

	private void typeFilterContractSale(FinanceExtractPage financeExtractPage) {
		financeExtractPage.filterStatus( "Contrato de Venda" );
	}

	private void typeFilterOverdue(FinanceExtractPage financeExtractPage) {
		financeExtractPage.filterStatus( "Vencidos" );
	}

	private void typeFilter1(FinanceExtractPage financeExtractPage) {
		financeExtractPage.filterStatus( "Pagos / Recebidos" );
	}

	private void createNewTransfer(String accountOriginCredit, String accountOriginDestiny, String descriptionP,
			String valueTransferP, FinanceExtractPage financeExtractPage, ImportBankExtractPage importBankExtractPage) {
		financeExtractPage.btnOpenMenuExtract( "Nova Transferência" );
		importBankExtractPage.selectBankOrigin( accountOriginCredit );
		importBankExtractPage.selectBankDestiny( accountOriginDestiny );
		importBankExtractPage.addDescriptionTransfer( descriptionP );
		importBankExtractPage.addValueTransfer( valueTransferP );
		importBankExtractPage.saveTransfer();
	}
}
