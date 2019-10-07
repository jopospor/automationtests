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

public class CreateTransferTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void createTransfer(String accountBankOrigin, String accountBankDestiny
			, String descriptionP, String valueTransferP, String transferDateE, String valueTransferPW) {

		FinanceExtractPage financeExtract = getPaginas().getFinanceExtractPage( getWebDriver() );
		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );
		NotificationAssistant notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );
		FinancialAssistant financialAssistant = getAssistants().getFinancialAssistant( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login( "CreateTransferTest@contaazul.com",
				"12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );

		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		String accountBankOriginR = accountBankOrigin + "_" + Double.toString( Math.random() );
		String accountBankDestinyR = accountBankDestiny + "_" + Double.toString( Math.random() );
		step( "Verifica se é possivel lançar uma transferência com o campo do valor em branco." );
		financeExtract.btnOpenMenuExtract( "Nova Transferência" );
		importBankExtract.createBankOrigin( accountBankOriginR );
		importBankExtract.createBankDestiny( accountBankDestinyR );
		importBankExtract.setValueTransfer( valueTransferPW );
		importBankExtract.setDescriptionTransfer( descriptionP );
		importBankExtract.saveTransfer();
		checkPoint( "Todos os campos não estão preenchidos", "O valor informado deve ser maior que zero.",
				notificationAssistant.getAlertMessageText() );
		notificationAssistant.waitMessageDismiss();
		importBankExtract.closeTransfer();

		step( "Verifica se é possivel fazer uma transferência com o campo da destino em branco" );
		financeExtract.btnOpenMenuExtract( "Nova Transferência" );
		importBankExtract.selectBankOrigin( accountBankOriginR );
		importBankExtract.setDescriptionTransfer( descriptionP );
		importBankExtract.setValueTransfer( valueTransferP );
		importBankExtract.saveTransfer();
		checkPoint( "Todos os campos não estão preenchidos.", "Favor preencher os campos obrigatórios.",
				notificationAssistant.getAlertMessageText() );
		importBankExtract.closeTransfer();

		step( "Verifica se é possivel fazer uma transferência com o campo da origem em branco" );
		financeExtract.btnOpenMenuExtract( "Nova Transferência" );
		importBankExtract.selectBankDestiny( accountBankDestinyR );
		importBankExtract.setDescriptionTransfer( descriptionP );
		importBankExtract.setValueTransfer( valueTransferP );
		importBankExtract.saveTransfer();
		checkPoint( "Todos os campos não estão preenchidos.", "Favor preencher os campos obrigatórios.",
				notificationAssistant.getAlertMessageText() );
		importBankExtract.closeTransfer();

		step( "Verifica se é possivel fazer uma transferência no futuro." );
		financeExtract.btnOpenMenuExtract( "Nova Transferência" );
		importBankExtract.selectBankOrigin( accountBankOriginR );
		importBankExtract.selectBankDestiny( accountBankDestinyR );
		importBankExtract.setDescriptionTransfer( descriptionP );
		importBankExtract.setValueTransfer( valueTransferP );
		importBankExtract.setDate( transferDateE );
		importBankExtract.saveTransfer();
		checkPoint( "Todos os campos não estão preenchidos", "Não é possível realizar uma transferência no futuro.",
				notificationAssistant.getAlertMessageText() );
		notificationAssistant.waitMessageDismiss();
		importBankExtract.closeTransfer();

		step( "Faz o lançamento de uma transferência e verifica se foi realizada com sucesso." );
		financeExtract.btnOpenMenuExtract( "Nova Transferência" );
		importBankExtract.selectBankOrigin( accountBankOriginR );
		importBankExtract.selectBankDestiny( accountBankDestinyR );
		importBankExtract.setDescriptionTransfer( descriptionP );
		importBankExtract.setValueTransfer( valueTransferP );
		importBankExtract.saveTransfer();
		checkPoint( "Todos os campos não estão preenchidos.", "Transferência realizada com sucesso!",
				notificationAssistant.getAlertMessageText() );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
		step( "Verifica se os valores que estão impresso são iguais aos digitados" );
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
		listingAssistant.checkAllItems();
		importBankExtract.selectAction( "Excluir" );

		step( "Navega para a tela de contas bancárias e deleta as contas criada anteriormente, fazendo logout em sequência" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_BANCARIAS );
		BankAccountsPage bank = getPaginas().getPaginaContasBancarias(
				getWebDriver() );
		bank.clickCheckbox( accountBankOriginR );
		bank.excluir();
		notificationAssistant.waitMessageDismiss();
		bank.clickCheckbox( accountBankDestinyR );
		bank.excluir();
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
