package com.contaazul.auto.selenium.financeiro.testcases;

import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public abstract class CreateFinanceStatementTest extends SeleniumTest {

	protected CreateIncomePage createIncomePage;
	protected ListingAssistant listingAssistant;
	protected FinanceFlowPage financeFlowPage;
	private String messageRequiredFields;
	private String sucessMessage;
	private String messageFutureIncomes;
	private String statementTypeTranslated;

	protected abstract String getStatementType();

	protected abstract String getModuleName();

	protected abstract void clickAddFinanceStatement();

	public void createFinanceStatementTest(String statementName, String value, String bankAccount,
			String expirationDate,
			String incomeCategory, String done, String repeat, String customer, String competenceDate,
			String costCenter, String observations, String attachment, String $result, String transaction) {

		this.statementTypeTranslated = SeleniumSession.getSession().getLocale().translate( getStatementType() );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		ImportBankExtractPage importBankExtractPage = getPaginas().getImportBankExtractPage( getWebDriver() );
		instatiatePages();
		setMessagesIdentifiers();

		navigateToModuleName();
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		clickAddFinanceStatement();
		step( "Preenche o formulário Adicionar " + this.statementTypeTranslated );
		createIncomePage.setIncomeName( statementName );
		createIncomePage.setValue( value );
		createIncomePage.setBankAccount( bankAccount );
		createIncomePage.setExpirationDate( expirationDate );
		createIncomePage.setIncomeCategory( incomeCategory );
		createIncomePage.setReceived( Boolean.parseBoolean( done ) );
		createIncomePage.setRepeat( Boolean.parseBoolean( repeat ) );
		createIncomePage.setCustomer( customer );
		createIncomePage.setCompetenceDate( competenceDate );
		createIncomePage.setCostCenter( costCenter );
		createIncomePage.setObservations( observations );

		createIncomePage.clickAddIncomeButton();

		if ($result.equals( messageRequiredFields )) {
			step( "Testa campos obrigatórios." );

			checkPoint( "Não foi apresentada a mensagem de erro dos campos obrigatórios", messageRequiredFields,
					createIncomePage.getMessageTopModal(), true );
			createIncomePage.waitTopMessageNotPresent();
			createIncomePage.closePage();
		} else if ($result.equals( messageFutureIncomes )) {
			step( "Testa validação de lançamentos futuros." );

			checkPoint( "Não foi apresentada a mensagem de erro de lançamentos futuros", messageFutureIncomes,
					createIncomePage.getMessageTopModal(), true );
			createIncomePage.waitTopMessageNotPresent();
			createIncomePage.setReceived( !Boolean.parseBoolean( done ) );
			createIncomePage.clickAddIncomeButton();
			testCreateSucess( statementName, expirationDate, incomeCategory, value, "false", transaction );
		} else if ($result.equals( sucessMessage )) {
			step( "Testa sucesso da inclusão da " + this.statementTypeTranslated );
			testCreateSucess( statementName, expirationDate, incomeCategory, value, done, transaction );
			getAssistants().getListingAssistant( getWebDriver() ).checkAllItems();
			importBankExtractPage.selectAction( "Excluir" );
		}

	}

	private void setMessagesIdentifiers() {
		this.messageRequiredFields = SeleniumSession.getSession().getLocale().translate( "#ERROR_REQUIRED_FIELDS" );
		this.sucessMessage = SeleniumSession.getSession().getLocale().translate( "#SUCCESS" );
		this.messageFutureIncomes = SeleniumSession.getSession().getLocale()
				.translate( "#ERROR_FINANCIAL_STATEMENTS_FUTURE" );
	}

	private void navigateToModuleName() {
		getAssistants().getMenuAssistant( getWebDriver() )
				.navigateMenu( "Financeiro", "Movimentações", getModuleName() );
	}

	private void instatiatePages() {
		createIncomePage = new CreateIncomePage( getWebDriver() );
		financeFlowPage = new FinanceFlowPage( getWebDriver() );
		listingAssistant = getAssistants().getListingAssistant( getWebDriver() );

	}

	private void testCreateSucess(String incomeName, String expirationDate, String incomeCategory, String value,
			String received, String transaction) {
		checkPoint( "Não foi apresentada a mensagem de sucesso", createIncomePage.getSucessMessageExpected( incomeName,
				value, expirationDate, Boolean.parseBoolean( received ), Boolean.parseBoolean( transaction ) ),
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );

		checkPoint( "Não foi encontrada a Receita na grid", true,
				listingAssistant.hasItemInGrid( incomeName, Boolean.parseBoolean( received ) ), true );

		createIncomePage.waitTopNotificationNotPresent();
	}
}
