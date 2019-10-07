package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage.RolloverMenuOptions;
import com.contaazul.auto.selenium.financeiro.pages.FinanceSpreadsheetPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;

public class PrintBankSlipTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void printBankSlipTest(String incomeName, String value, String bankAccount, String expirationDate,
			String incomeCategory, String received
			, String customer, String date) {

		loginAndNavigate();

		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
		FinanceFlowPage financeFlow = getPaginas().getFinanceFlowPage( getWebDriver() );
		CreateIncomePage createIncome = getPaginas().getCreateIncomePage( getWebDriver() );
		FinanceSpreadsheetPage financeSpread = getPaginas().getPaginaImportacaoPlanilhaExtrato( getWebDriver() );
		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );

		step( "Cria uma nova receita e verifica os valores cadastrados" );
		financeFlow.clickAddIncome();
		setNewRevenue( incomeName, value, bankAccount, expirationDate, incomeCategory, received, customer, createIncome );
		checkPoint( "Erro ao cadastrar a receita",
				"Receita 'Teste Imprimir Boleto', de R$ 666,00, agendado para o dia " + date + ""
				, getAssistants().getNotificationAssistant( getWebDriver() )
						.getAlertMessageText() );

		step( "Clica para baixar o boleto" );
		printSlipRevenue( financeFlow, financeSpread );

		step( "Verifica se o boleto foi baixado" );
		checkPoint( "Boleto não foi baixado", true, createIncome.isPdfDownloaded( customer ) );

		step( "Deleta a receita criada" );
		listingAssistant.checkAllItems();
		importBankExtract.selectAction( "Excluir" );

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void printSlipRevenue(FinanceFlowPage financeFlow, FinanceSpreadsheetPage financeSpread) {
		financeFlow.showRolloverMenu( 0 );
		financeFlow.chooseOptionMenuRollover( RolloverMenuOptions.PRINT_SLIP );
		financeFlow.closeRolloverMenuOpened();
		getAssistants().getNotificationAssistant( getWebDriver() ).waitLoadingDismiss();
		financeSpread.downloadSpreadsheetDialogWindow();
	}

	private void setNewRevenue(String incomeName, String value, String bankAccount, String expirationDate,
			String incomeCategory, String received, String customer, CreateIncomePage createIncome) {
		createIncome.setIncomeName( incomeName );
		createIncome.setValue( value );
		createIncome.setBankAccount( bankAccount );
		createIncome.setExpirationDate( expirationDate );
		createIncome.setIncomeCategory( incomeCategory );
		createIncome.setReceived( Boolean.parseBoolean( received ) );
		createIncome.openMoreOptions();
		createIncome.setCustomer( customer );
		createIncome.clickAddIncomeButton();
	}

	private void loginAndNavigate() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "PrintBankSlipTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );
	}
}
