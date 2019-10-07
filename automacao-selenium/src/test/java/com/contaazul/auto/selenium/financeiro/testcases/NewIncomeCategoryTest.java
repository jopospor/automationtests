package com.contaazul.auto.selenium.financeiro.testcases;

import java.util.UUID;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.CreateCategoryPage;
import com.contaazul.auto.selenium.financeiro.pages.EditCategoryPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.IncomePage;

public class NewIncomeCategoryTest extends SeleniumTest {

	String generatedName = "Categoria " + UUID.randomUUID();
	String difference = UUID.randomUUID().toString();

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "newIncomeCategoryTest@contaazul.com", "12345" );
	}

	@BeforeMethod
	public void goToHomePage() {
		this.getAssistants().getMenuAssistant( getWebDriver() ).navigateToHomePage();
	}

	@Test
	public void createNewIncomeCategoryByFinanceMenu() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		CreateCategoryPage createCategoryPage = getPaginas().getCreateCategoryPage( getWebDriver() );
		EditCategoryPage editCategoryPage = getPaginas().getEditCategoryPage( getWebDriver() );
		financeExtractPage.clickEditCategoriesButton();
		editCategoryPage.clickAddCategoryButton();
		createCategoryPage.setCategoryText( "" );
		createCategoryPage.save();
		step( "Adiciona uma categoria em branco esperando mensagem de ERRO" );
		checkPoint( "Nao deve salvar categoria", "É necessário informar uma categoria.",
				createCategoryPage.getValidationMessage() );

		createCategoryPage.setCategoryText( "Cat_Rec_teste_" + this.difference );
		createCategoryPage.save();
		String alertMessage = getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText();

		checkPoint( "Salvamento da categoria", "Categoria salva com sucesso", alertMessage );

		checkPoint( "Adicionado categoria na listagem de edicao de categoria", true,
				editCategoryPage.containsIncomeItem( "Cat_Rec_teste_" + this.difference ) );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Extrato" );
		IncomePage<FinanceExtractPage> newIncome = financeExtractPage.navigateToNewIncome();

		checkPoint( "Nova categoria esta presente na tela de criacao de receita", true,
				newIncome.containsCategoryName( "Cat_Rec_teste_" + this.difference ) );
		newIncome.close();
	}

	@Test
	public void createNewIncomeCategoryByAutoComplete() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Extrato" );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		IncomePage<FinanceExtractPage> newIncome = financeExtractPage.navigateToNewIncome();
		newIncome.createCategoryName( this.generatedName );
		newIncome.close();
		EditCategoryPage editCategoryPage = financeExtractPage.navigateToEditCategory();

		checkPoint( "Criando categoria pelo autocomplete", true, editCategoryPage.containsIncomeItem( generatedName ) );
	}

	@Test
	public void removeIncomeCategoryTest() {

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );
		FinanceExtractPage financeExtractPage = getPaginas().getFinanceExtractPage( getWebDriver() );
		EditCategoryPage editCategoryPage = getPaginas().getEditCategoryPage( getWebDriver() );

		financeExtractPage.clickEditCategoriesButton();

		editCategoryPage.removeIncomeCategory( this.generatedName );

		String alertMessageText = getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText();
		checkPoint( "Não conseguiu encontrar mensagem de remoção de categoria com sucesso",
				"Categoria removida com sucesso", alertMessageText );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();

		editCategoryPage.removeIncomeCategory( "Cat_Rec_teste_" + this.difference );
		checkPoint( "Não conseguiu encontrar mensagem de remoção de categoria com sucesso",
				"Categoria removida com sucesso", alertMessageText );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
