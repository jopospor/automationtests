package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;

public class EditTransferTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void editTransfer(String accountOriginCredit, String accountOriginDestiny, String valueTransferP,
			String descriptionP, String descriptionEdit, String valueTransferEdit) {

		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"EditTransferTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Extrato" );

		step( "Cria uma nova Transferência." );
		financeExtractPage.btnOpenMenuExtract( "Nova Transferência" );
		importBankExtract.selectBankOrigin( accountOriginCredit );
		importBankExtract.selectBankDestiny( accountOriginDestiny );
		importBankExtract.setValueTransfer( valueTransferP );
		importBankExtract.setDescriptionTransfer( descriptionP );
		importBankExtract.saveTransfer();
		checkPoint( "Transferência não foi realizada", "Transferência realizada com sucesso!", getAssistants()
				.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );

		step( "Edita a Transferência." );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Extrato" );
		ListingAssistant listingAssisting = getAssistants().getListingAssistant( getWebDriver() );
		listingAssisting.getListingRowAsElement( 1 ).click();
		importBankExtract.selectBankOrigin( accountOriginDestiny );
		importBankExtract.selectBankDestiny( accountOriginCredit );
		importBankExtract.setValueTransfer( valueTransferP );
		importBankExtract.setDescriptionTransfer( descriptionP );
		importBankExtract.saveTransfer();
		checkPoint( "Transferência Alterada com sucesso", "Transferência realizada com sucesso!", getAssistants()
				.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );

		step( "Deleta Transferência criada." );
		listingAssisting.checkAllItems();
		importBankExtract.selectAction( "Excluir" );

		getAssistants().getLoginAssistant( getWebDriver() ).logout();

	}
}
