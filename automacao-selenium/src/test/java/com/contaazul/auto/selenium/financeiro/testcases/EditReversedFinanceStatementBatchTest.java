package com.contaazul.auto.selenium.financeiro.testcases;

import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public abstract class EditReversedFinanceStatementBatchTest extends SeleniumTest {

	private ListingAssistant listingAssistant;
	private NotificationAssistant notificationAssistant;
	private String statementTypeTranslated;

	protected abstract String getStatementType();

	protected abstract String getModuleName();

	public void editReversedFinanceStatementBatchTest() {

		instatiateAssistants();
		this.statementTypeTranslated = SeleniumSession.getSession().getLocale().translate( getStatementType() );

		getAssistants().getMenuAssistant( getWebDriver() )
				.navigateMenu( "Financeiro", "Movimentações", getModuleName() );

		step( "Edita " + this.statementTypeTranslated + "s em Lote marcando os lançamentos como estornados." );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		undoPayAll();

		checkPoint( "Não foi apresentada a mensagem de baixa desfeita com sucesso.",
				"Baixa(s) desfeita(s) com sucesso", notificationAssistant.getAlertMessageText(), true );

		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();

		for (int i = 1; i <= listingAssistant.getTotalRows(); i++)
			checkPoint( "Registro na linha " + i + " da grid não está como estornado.", false,
					listingAssistant.isReceivedOnRow( i ), true );

		step( "Edita " + this.statementTypeTranslated + "s em lote marcando os lançamentos como pagos." );
		defineLikePaidAll();

		checkPoint( "Não foi apresentada a mensagem de lançamento efetivado com sucesso.",
				"Lançamento(s) efetivado(s) com sucesso", notificationAssistant.getAlertMessageText(), true );

		for (int i = 1; i <= listingAssistant.getTotalRows(); i++)
			checkPoint( "Registro na linha " + i + " da grid não está como baixado.", true,
					listingAssistant.isReceivedOnRow( i ), true );
	}

	private void instatiateAssistants() {
		listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
		notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );
	}

	protected void undoPayAll() {
		listingAssistant.checkAllItems();
		listingAssistant.performAction( "Definir como não pago" );
	}

	protected void defineLikePaidAll() {
		listingAssistant.uncheckAllItems();
		listingAssistant.checkAllItems();
		listingAssistant.performAction( "Definir como pago" );
	}

}
