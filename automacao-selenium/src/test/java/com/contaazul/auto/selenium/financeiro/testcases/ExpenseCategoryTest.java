package com.contaazul.auto.selenium.financeiro.testcases;

import java.util.UUID;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.CreateCategoryPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.EditCategoryPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;

public class ExpenseCategoryTest extends SeleniumTest {

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "ExpenseCategoryTest@contaazul.com",
				"12345" );
	}

	@BeforeMethod
	public void goToHomePage() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Extrato" );
	}

	@Test
	public void createNewIncomeCategoryByFinanceMenuNegative() {

		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		EditCategoryPage editCategoryPage = getPaginas().getEditCategoryPage( getWebDriver() );
		CreateCategoryPage createCategoryPage = getPaginas().getCreateCategoryPage( getWebDriver() );

		financeExtractPage.clickEditCategoriesButton();
		editCategoryPage.clickAddExpenseCategoryButton();
		createCategoryPage.setCategoryText( "" );
		createCategoryPage.save();
		step( "Adiciona uma categoria em branco esperando mensagem de ERRO" );
		checkPoint( "Nao deve salvar categoria", "É necessário informar uma categoria.",
				createCategoryPage.getValidationMessage() );
		createCategoryPage.cancel();
	}

	@Test
	public void createNewIncomeCategoryByFinanceMenu() {

		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		EditCategoryPage editCategoryPage = getPaginas().getEditCategoryPage( getWebDriver() );
		CreateCategoryPage createCategoryPage = getPaginas().getCreateCategoryPage( getWebDriver() );
		CreateIncomePage createExpensePage = getPaginas().getCreateIncomePage( getWebDriver() );

		financeExtractPage.clickEditCategoriesButton();
		editCategoryPage.clickAddExpenseCategoryButton();
		createCategoryPage.setCategoryText( "Nova categoria de teste" );
		createCategoryPage.save();
		String alertMessage = getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText();
		checkPoint( "Salvamento da categoria", "Categoria salva com sucesso", alertMessage );
		checkPoint( "Adicionado categoria na listagem de edicao de categoria", true,
				editCategoryPage.isExpenseCategoryListed( "Nova categoria de teste" ) );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Extrato" );
		sleep( VERY_LONG );
		financeExtractPage.navigateToNewExpense();
		try {
			createExpensePage.setIncomeCategory( "Nova categoria de teste" );
		} catch (Exception e) {
			fail( "Não conseguiu identificar a nova categoria de despesa 'Nova categoria de teste' no autocomplete de categorias." );
		}
		createExpensePage.clickCancelButton();
	}

	@Test
	public void createNewIncomeCategoryByAutoComplete() {

		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		CreateIncomePage createExpensePage = getPaginas().getCreateIncomePage( getWebDriver() );
		EditCategoryPage editCategoryPage = getPaginas().getEditCategoryPage( getWebDriver() );

		financeExtractPage.navigateToNewExpense();

		String generatedName = "Categoria " + UUID.randomUUID();
		createExpensePage.createCategoryName( generatedName );
		createExpensePage.clickCancelButton();
		step( "Cria uma categoria atraves do menu de autocomplete, da tela de adicionar despesas" );

		financeExtractPage.clickEditCategoriesButton();
		checkPoint( "Não conseguiu achar categoria criada pelo autocomplete", true,
				editCategoryPage.isExpenseCategoryListed( generatedName ) );
	}

	@Test
	public void removeExpenseCategoryTest() {

		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		EditCategoryPage editCategoryPage = getPaginas().getEditCategoryPage( getWebDriver() );
		step( "Remove uma categoria de despesa e verifica a mensagem de retorno." );
		financeExtractPage.clickEditCategoriesButton();
		editCategoryPage.removeExpenseCategory( "Nova categoria de teste" );
		String alertMessageText = getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText();
		checkPoint( "Não conseguiu encontrar mensagem de remoção de categoria com sucesso",
				"Categoria removida com sucesso", alertMessageText );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
