package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.FinancialAssistant;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class CreateTransferFromOfxFileTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void importTransfer(String conta, String arquivo,
			String accountBank) {

		NotificationAssistant notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );
		FinancialAssistant financialAssistant = getAssistants().getFinancialAssistant( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );
		FinanceExtractPage financeExtract = getPaginas().getFinanceExtractPage( getWebDriver() );

		step( "Faz o login e navega para a tela de importar extrato" );
		getAssistants().getLoginAssistant( getWebDriver() ).login( "CreateTransferFromOfxFileTest.teste@contaazul.com",
				"12345" );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );
		getAssistants().getDeleteAssistant( getWebDriver() ).deleteAllFinancialStatements();

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );
		step( "Clica no botão importar extrato faz upload do arquivo escolhe a conta bancaria gerando um nome random." );
		importBankExtract.btnNewExtractOfx();
		String contaId = conta + "_" + Double.toString( Math.random() );
		importBankExtract.selectBankAccount( contaId );
		importBankExtract.importExtractOfx( arquivo );

		step( "Salva o step anterior verificando a mensagem de sucesso e esperando ela sair da tela." );
		checkPoint( "Mensagem verificado com sucesso ", "Extrato bancário importado com sucesso!",
				notificationAssistant.getAlertMessageText() );
		notificationAssistant.waitMessageDismiss();
		importBankExtract.clickLaunchConcileButton();
		notificationAssistant.waitMessageDismiss();

		step( "Seleciona o primeiro checkbox e envia ele para transferencia " );
		listingAssistant.checkRow( 2 );
		importBankExtract.selectAction( "Adicionar como transferências" );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		step( "Preenche o banco de origem e salva a transferencia verificando se foi lançado com sucesso." );
		importBankExtract.selectBankOrigin( accountBank );
		importBankExtract.saveTransfer();
		checkPoint( "Mensagem verificada com sucesso", "Lançamento conciliado com sucesso",
				notificationAssistant.getAlertMessageText() );
		notificationAssistant.waitMessageDismiss();
		listingAssistant.uncheckRow( 2 );

		step( "Seleciona o primeiro checkbox" );
		listingAssistant.checkRow( 2 );
		importBankExtract.selectAction( "Adicionar como transferências" );

		step( "Preenche os dados do banco de destino e envia para lançamento, verificando se foi enviado." );
		importBankExtract.selectBankDestiny( accountBank );
		importBankExtract.saveTransfer();
		checkPoint( "Mensagem verificado com sucesso ", "Lançamento conciliado com sucesso",
				notificationAssistant.getAlertMessageText() );

		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );

		step( "Navega para a tela de Extrato e verifica se os dados do da grid batem com os dados inseridos" );
		financialAssistant.navigateMenuFinance( "Extrato" );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		checkPoint( "Os campos estão com valores incorretos", "Transferência de Saída",
				financialAssistant.getTypeTransaction( "4", 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "CRÉDITO - CONTA CORRENTE",
				financialAssistant.getNameTransaction( 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "-3.000,50", financialAssistant.getValue( 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "-3.000,50", financialAssistant.getBalance( 1 ) );
		checkPoint( "Os campos estão com valores incorretos", "Transferência de Entrada",
				financialAssistant.getTypeTransaction( "4", 2 ) );
		checkPoint( "Os campos estão com valores incorretos", "CRÉDITO - CONTA CORRENTE",
				financialAssistant.getNameTransaction( 2 ) );
		checkPoint( "Os campos estão com valores incorretos", "3.000,50", financialAssistant.getValue( 2 ) );
		checkPoint( "Os campos estão com valores incorretos", "0,00", financialAssistant.getBalance( 2 ) );
		checkPoint( "Os campos estão com valores incorretos", "Transferência de Saída",
				financialAssistant.getTypeTransaction( "4", 3 ) );
		checkPoint( "Os campos estão com valores incorretos", "DÉBITO - CONTA CORRENTE",
				financialAssistant.getNameTransaction( 3 ) );
		checkPoint( "Os campos estão com valores incorretos", "-1.000,50", financialAssistant.getValue( 3 ) );
		checkPoint( "Os campos estão com valores incorretos", "-1.000,50", financialAssistant.getBalance( 3 ) );
		checkPoint( "Os campos estão com valores incorretos", "Transferência de Entrada",
				financialAssistant.getTypeTransaction( "4", 4 ) );
		checkPoint( "Os campos estão com valores incorretos", "DÉBITO - CONTA CORRENTE",
				financialAssistant.getNameTransaction( 4 ) );
		checkPoint( "Os campos estão com valores incorretos", "1.000,50", financialAssistant.getValue( 4 ) );
		checkPoint( "Os campos estão com valores incorretos", "0,00", financialAssistant.getBalance( 4 ) );
		checkPoint( "Os campos estão com valores incorretos", "4.001,00", financeExtract.getTotalValueRevenue() );
		checkPoint( "Os campos estão com valores incorretos", "-4.001,00", financeExtract.getTotalValueExpense() );
		checkPoint( "Os campos estão com valores incorretos", "4", financeExtract.getTotalNumberTransaction() );

		step( "Exclui todas as transferencias realizadas e exclui tambem as contas bancarias" );
		listingAssistant.checkAllItems();
		importBankExtract.selectAction( "Excluir" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_BANCARIAS );
		BankAccountsPage bank = getPaginas().getPaginaContasBancarias( getWebDriver() );
		bank.clickCheckbox( contaId );
		bank.excluir();

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
