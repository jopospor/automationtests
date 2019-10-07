package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.CreateCategoryPage;
import com.contaazul.auto.selenium.financeiro.pages.EditCategoryPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;

public class RemoveIncomeCategoryTest extends SeleniumTest {

	@Test
	public void removeIncomeCategoryTest() {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "RemoveIncomeCategoryTest@contaazul.com", "12345" );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );

		CreateCategoryPage createCategoryPage = getPaginas().getCreateCategoryPage( getWebDriver() );
		EditCategoryPage editCategoryPage = getPaginas().getEditCategoryPage( getWebDriver() );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		financeExtractPage.clickEditCategoriesButton();
		editCategoryPage.clickAddCategoryButton();
		createCategoryPage.setCategoryText( "Nova categoria de teste" );
		createCategoryPage.save();

		editCategoryPage.removeIncomeCategory( "Nova categoria de teste" );
		String alertMessageText = getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText();

		checkPoint( "Removendo categoria", "Categoria removida com sucesso", alertMessageText );

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
