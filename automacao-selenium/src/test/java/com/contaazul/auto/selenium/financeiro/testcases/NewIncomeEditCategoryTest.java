package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.CreateCategoryPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.EditCategoryPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;

public class NewIncomeEditCategoryTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void editCategory(String category, String getCategory) {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "NewIncomeEditCategoryTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		CreateIncomePage createExpensePage = getPaginas().getCreateIncomePage( getWebDriver() );
		CreateCategoryPage createCategoryPage = getPaginas().getCreateCategoryPage( getWebDriver() );
		EditCategoryPage editCategoryPage = financeExtractPage.navigateToEditCategory();

		editCategoryPage.editIncomeCategory( getCategory );
		sleep( FOR_A_LONG_TIME );
		createCategoryPage.setCategoryText( category );
		createCategoryPage.save();
		String alertMessage = getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText();
		checkPoint( "Atualizando categoria", "Categoria salva com sucesso", alertMessage );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		checkPoint( "Atualizado categoria na listagem de edicao de categoria", true,
				editCategoryPage.containsIncomeItem( category ) );

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

}
