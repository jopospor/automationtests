package com.contaazul.auto.selenium.financeiro.testcases;

import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public abstract class EditFinanceStatementTest extends SeleniumTest {

	private ListingAssistant listingAssistant;
	private NotificationAssistant notificationAssistant;
	private String statementTypeTranslated;
	private CreateIncomePage createIncomePage;

	protected abstract String getStatementType();

	protected abstract String getModuleName();

	public void editFinanceStatementTest(String incomeName, String value, String bankAccount, String expirationDate,
			String incomeCategory, String received, String repeat, String customer, String competenceDate,
			String costCenter, String observations, String attachment) {

		this.statementTypeTranslated = SeleniumSession.getSession().getLocale().translate( getStatementType() );
		instantiatePages();
		instantiateAssistants();

		navigateToModule();
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );

		listingAssistant.getListingRowAsElement( 1 ).click();

		createIncomePage.setIncomeName( incomeName );

		createIncomePage.setValue( value );

		createIncomePage.setExpirationDate( expirationDate );

		createIncomePage.setIncomeName( incomeName );

		createIncomePage.setIncomeCategory( incomeCategory );

		createIncomePage.setReceived( Boolean.parseBoolean( received ) );

		createIncomePage.clickAddIncomeButton();

		checkPoint( "Não foi apresentada a mensagem de sucesso",
				getSucessMessageExpected( incomeName, value, expirationDate, Boolean.parseBoolean( received ) ),
				this.notificationAssistant.getAlertMessageText(), true );

		this.notificationAssistant.waitAlertMessageDismiss();
	}

	private void instantiatePages() {
		createIncomePage = new CreateIncomePage( getWebDriver() );
	}

	private void navigateToModule() {
		getAssistants().getMenuAssistant( getWebDriver() )
				.navigateMenu( "Financeiro", "Movimentações", getModuleName() );
	}

	private void instantiateAssistants() {
		this.listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
		this.notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );
	}

	private String getSucessMessageExpected(String incomeName, String value, String expirationDate, boolean received) {
		if (!received)
			return this.statementTypeTranslated + " '" + incomeName + "', de R$ " + value + ", agendado para o dia "
					+ expirationDate.trim();
		else
			return this.statementTypeTranslated + " '" + incomeName + "', de R$ " + value + ", no dia "
					+ expirationDate;
	}
}
