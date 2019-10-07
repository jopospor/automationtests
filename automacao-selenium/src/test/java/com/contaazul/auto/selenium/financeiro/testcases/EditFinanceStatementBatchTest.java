package com.contaazul.auto.selenium.financeiro.testcases;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public abstract class EditFinanceStatementBatchTest extends SeleniumTest {
	private ListingAssistant listingAssistant;
	private NotificationAssistant notificationAssistant;
	private CreateIncomePage createIncomePage;

	protected abstract String getModuleName();

	protected abstract String getStatementType();

	public void editFinanceStatementBatchTest(String incomeName, String value, String bankAccount,
			String expirationDate,
			String incomeCategory, String done, String repeat, String customer, String competenceDate,
			String costCenter, String observations, String attachment) {

		this.createIncomePage = new CreateIncomePage( getWebDriver() );

		instantiateAssistants();

		navigateToModule();
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );

		step( "Seleciona todos os registros e edita em lote." );
		sleep( QUICKLY );
		listingAssistant.checkAllItems();
		listingAssistant.performAction( "Editar em lote" );

		step( "Preenche formulário de edição" );
		setNewExpense( incomeName, value, bankAccount, expirationDate, incomeCategory, done, repeat, customer,
				competenceDate, costCenter, observations );

		incomeCategory = createIncomePage.getIncomeCategory();

		clickSave();

		checkPoint( "A mensagem de sucesso não foi apresentada.", "Lançamentos alterados com sucesso",
				notificationAssistant.getAlertMessageText(), true );

		step( "Verifica se todos os registros estão alterados corretamente na grid." );
		for (int i = 1; i <= listingAssistant.getTotalRows(); i++) {
			checkPoint( "Data na linha " + i + " não é igual a alterada.", expirationDate,
					listingAssistant.getDateOnRow( i ), true );

			checkPoint( "Descrição do lançamento na linha " + i + " não é igual a alterada.", incomeName,
					listingAssistant.getFinanceStatementOnRow( i ), true );

			checkPoint( "Categoria na linha " + i + " não é igual a alterada.", incomeCategory,
					listingAssistant.getCategoryOnRow( i ), true );

			checkPoint( "Valor do lançamento na linha " + i + " não é igual ao alterado.", value,
					listingAssistant.getValueOnRow( i ), true );

			checkPoint( "Campo Baixado na linha " + i + " não é igual ao alterado.",
					Boolean.parseBoolean( done ), listingAssistant.isReceivedOnRow( i ), true );
		}
	}

	private void setNewExpense(String incomeName, String value, String bankAccount, String expirationDate,
			String incomeCategory, String done, String repeat, String customer, String competenceDate,
			String costCenter, String observations) {
		this.createIncomePage.setIncomeName( incomeName );
		this.createIncomePage.setValue( value );
		this.createIncomePage.setBankAccount( bankAccount );
		this.createIncomePage.setExpirationDate( expirationDate );
		this.createIncomePage.setIncomeCategory( incomeCategory );
		this.createIncomePage.setReceived( Boolean.parseBoolean( done ) );
		this.createIncomePage.setRepeat( Boolean.parseBoolean( repeat ) );
		this.createIncomePage.setCustomer( customer );
		this.createIncomePage.setCompetenceDate( competenceDate );
		this.createIncomePage.setCostCenter( costCenter );
		this.createIncomePage.setObservations( observations );
	}

	private void navigateToModule() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				getModuleName() );
	}

	private void instantiateAssistants() {
		notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );
		listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
	}

	private void clickSave() {
		this.createIncomePage.clickAddIncomeButton();
	}
}
