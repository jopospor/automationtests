package com.contaazul.auto.selenium.financeiro.testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.RegisterRevenueAndExpenseAttachedPage;

public class RegisterRevenueWithAttached extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void registerExpenseWithAttachedTest(String incomeName, String value, String bankAccount,
			String expirationDate, String incomeCategory,
			String clientProvider, String competenceDate, String costCenter, String fileUpload, String rowNumber,
			String received, String dateToday)
			throws IOException {

		CreateIncomePage createIncome = getPaginas().getCreateIncomePage(
				getWebDriver() );
		FinanceFlowPage financeFlow = getPaginas().getFinanceFlowPage(
				getWebDriver() );
		RegisterRevenueAndExpenseAttachedPage registerExpense = getPaginas().getregisterExpenseAttachedPage(
				getWebDriver() );
		getPaginas().getFinancialTransferPage( getWebDriver() );
		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );
		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );

		step( "Executa o login e navega para a tela de adicionar receita." );
		loginAndNavigate();

		step( "Entra na tela de adicionar receita, e preenche os campos adicionando tres arquivos ao anexo." );
		setDateFields( incomeName, value, bankAccount, expirationDate, incomeCategory, clientProvider, competenceDate,
				costCenter, fileUpload, createIncome, financeFlow, registerExpense );

		step( "Verifica se os valores cadastrados estão corretos, e se a receita possui um anexo." );
		checkPoint( "Teste", "Receita 'Receita com anexo', de R$ 666,00, no dia " + dateToday + "", getAssistants()
				.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		checkPoint( "Arquivo nao anexado", true, registerExpense.isPaperclipDisplayed() );

		step( "Clica na linha para remover o anexo, e deleta o primeiro anexo" );
		removeAttachment( rowNumber, registerExpense );

		step( "Verifica se o anexo foi removido com sucesso e verifica se ainda possui dois anexos." );
		checkPoint( "Arquivo anexado não foi deletado com um sucesso", "Anexo removido com sucesso.",
				registerExpense.getMessageVerification() );
		checkPoint( "Açao esperada nao acontenceu, falta 2 anexos", true, registerExpense.isAttachmentDisplayed1() );
		checkPoint( "Açao esperada nao acontenceu, falta 2 anexos", true, registerExpense.isAttachmentDisplayed2() );

		step( "Vai para o menu de receita deleta as receitas e em sequencia faz logout." );
		createIncome.clickAddIncomeButton();
		listingAssistant.checkAllItems();
		importBankExtract.selectAction( "Excluir" );
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void removeAttachment(String rowNumber, RegisterRevenueAndExpenseAttachedPage registerExpense) {
		registerExpense.clickRemoveAttachment( Integer.parseInt( rowNumber ) );
		registerExpense.removeAttachment();
	}

	private void setDateFields(String incomeName, String value, String bankAccount, String expirationDate,
			String incomeCategory, String clientProvider, String competenceDate, String costCenter, String fileUpload,
			CreateIncomePage createIncome, FinanceFlowPage financeFlow,
			RegisterRevenueAndExpenseAttachedPage registerExpense)
			throws IOException {
		financeFlow.clickAddIncome();
		createIncome.setIncomeName( incomeName );
		createIncome.setValue( value );
		createIncome.setBankAccount( bankAccount );
		createIncome.setExpirationDate( expirationDate );
		createIncome.setIncomeCategory( incomeCategory );
		createIncome.openMoreOptions();
		registerExpense.setClientProvider( clientProvider );
		createIncome.setCompetenceDate( competenceDate );
		createIncome.setCostCenter( costCenter );
		registerExpense.revenueUpload( fileUpload );
		registerExpense.revenueUpload( fileUpload );
		registerExpense.revenueUpload( fileUpload );
		createIncome.clickAddIncomeButton();
	}

	private void loginAndNavigate() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "RegisterRevenueWithAttached@contaazul.com",
				"12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );
	}

}
