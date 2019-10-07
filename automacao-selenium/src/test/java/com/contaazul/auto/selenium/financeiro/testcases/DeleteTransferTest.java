package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.FinancialAssistant;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;

public class DeleteTransferTest extends SeleniumTest {
	@Test(dataProvider = DATA_PROVIDER)
	public void deleteTransferTest(String accountOriginCredit, String accountDestinyCredit, String descriptionP,
			String valueTransferP) {

		NotificationAssistant notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );
		FinancialAssistant financialAssistant = getAssistants().getFinancialAssistant( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
		ImportBankExtractPage transfer = getPaginas().getImportBankExtractPage( getWebDriver() );
		FinanceExtractPage financeExtract = getPaginas().getFinanceExtractPage( getWebDriver() );

		step( "Faz login e navega no finaceiro" );
		menuLogin();

		step( "Abre janela de Tranferencia e adiciona os dados." );
		financeExtract.btnOpenMenuExtract( "Nova Transferência" );
		transfer.selectBankOrigin( accountOriginCredit );
		transfer.selectBankDestiny( accountDestinyCredit );
		transfer.addDescriptionTransfer( descriptionP );
		transfer.addValueTransfer( valueTransferP );
		transfer.saveTransfer();

		checkPoint( "Erro ao realizar a transferencia", "Transferência realizada com sucesso!",
				notificationAssistant.getAlertMessageText() );
		notificationAssistant.waitMessageDismiss();

		step( "Seleciona uma transferencia e a deleta, verificando se a outra foi deletado junto." );
		financialAssistant.navigateMenuFinance( "Extrato" );
		listingAssistant.checkAllItems();
		transfer.selectAction( "Excluir" );
		checkPoint( "Resultado da tela diferente", "Lançamento(s) removido(s) com sucesso",
				notificationAssistant.getAlertMessageText() );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		checkPoint( "Resultado da tela diferente", "Extrato",
				financialAssistant.getMessageBlankSlate() );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_BANCARIAS );

		BankAccountsPage bank = getPaginas().getPaginaContasBancarias(
				getWebDriver() );

		step( "Seleciona todas as contas bancarias e exclui fazendo o logout." );
		bank.clickCheckbox( accountOriginCredit );
		bank.excluir();
		notificationAssistant.waitMessageDismiss();
		bank.clickCheckbox( accountDestinyCredit );
		bank.excluir();

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void menuLogin() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "DeleteTransferTest@contaazul.com",
				"12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
	}

}
