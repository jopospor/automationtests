package com.contaazul.auto.selenium.financeiro.testcases;

import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage.RolloverMenuOptions;

public abstract class EditFinanceStatementInlineTest extends SeleniumTest {

	private FinanceFlowPage financeFlowPage;
	private NotificationAssistant notificationAssistant;
	private ListingAssistant listingAssistant;
	String statementTypeTranslated;

	protected abstract String getStatementType();

	protected abstract String getModuleName();

	public void editFinanceStatementInlineTest() {
		this.statementTypeTranslated = SeleniumSession.getSession().getLocale().translate( getStatementType() );

		instatiatePages();
		instantiateAssistants();

		navigateToModule();

		sleep( FOR_A_LONG_TIME );

		step( "Edição de " + this.statementTypeTranslated + " marcando o lançamento como estornado." );

		undoPayThroughRolloverMenu( 0 );

		sleep( FOR_A_LONG_TIME );

		checkUndoPaySucess();

		checkRecordInGrid();

		defineLikePaidThroughRolloverMenu( 0 );
	}

	private void instatiatePages() {
		financeFlowPage = new FinanceFlowPage( getWebDriver() );
	}

	private void checkRecordInGrid() {
		checkPoint( "Registro na grid não está como estornado.", false, listingAssistant.isReceivedOnRow( 1 ), true );
	}

	private void checkUndoPaySucess() {
		checkPoint( "Não foi apresentada a mensagem de Baixa desfeita com sucesso.",
				"Baixa(s) desfeita(s) com sucesso", notificationAssistant.getAlertMessageText(), true );
	}

	private void navigateToModule() {
		getAssistants().getMenuAssistant( getWebDriver() )
				.navigateMenu( "Financeiro", "Movimentações", getModuleName() );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
	}

	private void instantiateAssistants() {
		notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );
		listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
	}

	public void defineLikePaidThroughRolloverMenu(int rowNumber) {
		financeFlowPage.showRolloverMenu( rowNumber );
		financeFlowPage.chooseOptionMenuRollover( RolloverMenuOptions.PAY );
		financeFlowPage.closeRolloverMenuOpened();
	}

	public void undoPayThroughRolloverMenu(int rowNumber) {
		financeFlowPage.showRolloverMenu( rowNumber );
		financeFlowPage.chooseOptionMenuRollover( RolloverMenuOptions.UNDO_PAY );
		financeFlowPage.closeRolloverMenuOpened();
	}

}
