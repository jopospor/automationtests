package com.contaazul.auto.selenium.componentesweb.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.vendas.pages.NewProductSalePage;

public class AutoCompleteTest extends SeleniumTest {

	private NewProductSalePage salePage;

	@BeforeClass
	public void setupTest() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "AutoCompleteTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Produtos", "Vendas de Produtos" );
		getWebDriver().findElement( By.linkText( "Nova Venda" ) ).click();
		salePage = getPaginas().getNewProductSalePage( getWebDriver() );
		sleep( FOR_A_LONG_TIME );
	}

	/*
	 * Auto complete suggestions
	 * 
	 * div id=clientSugestions
	 * 
	 * várias ULs, cada UL é uma sugestão
	 * 
	 * ul id=clientesugestions-option1
	 * 
	 * dentro de cada ul tem um li li class=dataFieldName e às vezes tem um B
	 * pra atrapalhas e às vezes tem um outro li com class=grey
	 */

	@Test
	public void clickOnEmptyAutoComplete() throws InterruptedException {
		salePage.clickOnClientField();
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		String[] sugestions = getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteSugestionOptions();
		checkPoint( "Lista de clientes sem pesquisar por nada - entrando com clique. Item 1 não confere.",
				"AVILA PRODUTOS NATURAIS LTDA", sugestions[0], true );
		checkPoint(
				"Lista de clientes sem pesquisar por nada - entrando com clique. Botão mostrar mais não foi mostrado.",
				"Mostrar mais...", sugestions[5], true );
		salePage.clickOnSalesmanField();
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		sugestions = getAssistants().getAutoCompleteAssistant( getWebDriver() ).getAutoCompleteSugestionOptions();
		checkPoint( "Lista de vendedores sem pesquisar por nada - entrando com clique. Item 1 não confere.",
				"AutoCompleteTest@contaazul.com", sugestions[0], true );
		checkPoint( "Lista de vendedores sem pesquisar por nada. Botão adicionar novo não foi mostrado.",
				"Adicionar novo", sugestions[1], true );
	}

	@Test
	public void focusAutoCompleteUsingTab() throws InterruptedException {
		salePage.clearClientField();
		salePage.clickOnClientField();
		sleep( QUICKLY );
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.SHIFT, Keys.TAB );
		sleep( QUICKLY );
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.TAB );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		String[] sugestions = getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteSugestionOptions();
		checkPoint( "Lista de clientes sem pesquisar por nada - entrando com tab. Item 1 não confere.",
				"AVILA PRODUTOS NATURAIS LTDA", sugestions[0], true );
		checkPoint(
				"Lista de clientes sem pesquisar por nada - entrando com tab. Botão mostrar mais não foi mostrado.",
				"Mostrar mais...", sugestions[5], true );
	}

	@Test
	public void focusAutoCompleteUsingShiftTab() throws InterruptedException {
		salePage.clearClientField();
		salePage.clickOnClientField();
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.TAB );
		sleep( QUICKLY );
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.SHIFT, Keys.TAB );

		String[] sugestions = getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteSugestionOptions();
		checkPoint( "Lista de clientes sem pesquisar por nada - entrando com Shift-Tab. Item 1 não confere.",
				"AVILA PRODUTOS NATURAIS LTDA", sugestions[0], true );
		checkPoint(
				"Lista de clientes sem pesquisar por nada- entrando com Shift-Tab. Botão mostrar mais não foi mostrado.",
				"Mostrar mais...", sugestions[5], true );
	}

	@Test
	public void typePartialMatch() throws InterruptedException {
		WebElement clientes = salePage.getClientField();
		salePage.clearClientField();
		// Mais de 5 opções
		clientes.sendKeys( "Ca" );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		String[] sugestions = getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteSugestionOptions();
		checkPoint( "Lista de clientes com match parcial - Ca. Item 1 não confere.",
				"CARINE GRAVE SUPLEMENTOS", sugestions[0], true );
		checkPoint( "Lista de clientes com match parcial - Ca. Botão mostrar mais não foi mostrado.",
				"Mostrar mais...", sugestions[5], true );
		// Menos de 5 opções
		clientes.clear();
		clientes.sendKeys( "Cas" );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		sugestions = getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteSugestionOptions();
		checkPoint( "Lista de clientes com match parcial - Cas. Item 1 não confere.",
				"CASA DE PRODUTOS NATURAIS NATURAMEL - FILIAL 2", sugestions[0], true );
		checkPoint( "Lista de clientes com match parcial - Cas. Item 2 não confere.",
				"CASA DE PRODUTOS NATURAIS NATURAMEL LTDA ME", sugestions[1], true );
		checkPoint( "Lista de clientes com match parcial - Cas. Botão Adicionar novo não foi mostrado.",
				"Cas (Adicionar novo)...", sugestions[2], true );
	}

	@Test
	public void clickOnPopulatedAutoComplete() throws InterruptedException {
		salePage.setClient( "Shoping São Francisco" );
		salePage.clickOnDateField();
		salePage.clickOnClientField();
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		String[] sugestions = getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteSugestionOptions();
		checkPoint( "Clique no campo populado - espera que não pesquise.", null, sugestions[0], true );
	}

	@Test
	public void tabIntoPopulatedAutoComplete() throws InterruptedException {
		salePage.setClient( "Shoping São Francisco" );
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.SHIFT, Keys.TAB );
		sleep( QUICKLY );
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.TAB );
		String[] sugestions = getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteSugestionOptions();
		checkPoint( "Entra com Tab no campo populado - espera que não pesquise.", null, sugestions[0], true );
	}

	@Test
	public void shiftTabIntoPopulatedAutoComplete() throws InterruptedException {
		salePage.setClient( "Shoping São Francisco" );
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.TAB );
		sleep( QUICKLY );
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.SHIFT, Keys.TAB );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		String[] sugestions = getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteSugestionOptions();
		checkPoint( "Entra com Shift-Tab no campo populado - espera que não pesquise.", null, sugestions[0], true );
	}

	@Test
	public void typePartialMatchAndClickOnItem() throws InterruptedException {
		WebElement clientes = salePage.getClientField();
		clientes.clear();
		clientes.sendKeys( "Ca" );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).clickOnAutoCompleteSugestionOption( 3 );
		checkPoint( "Match parcial e clica na segunda opção.", "Carine Grave", salePage.getClientFieldValue(), true );
	}

	@Test
	public void typePartialMatchAndClickOnMostrarMais() throws InterruptedException {
		WebElement clientes = salePage.getClientField();
		salePage.clearClientField();
		clientes.sendKeys( "Ca" );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).clickOnShowMoreOptions();
		String[] sugestions = getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteSugestionOptions();
		checkPoint( "Match parcial - Ca. Depois do mostrar mais, item 5 não confere.",
				"IM PRODUTOS NATURAIS LTDA ME",
				sugestions[5], true );
		checkPoint( "Match parcial - Ca. Depois do mostrar mais, botão Adicionar novo não aparece.",
				"Ca (Adicionar novo)...", sugestions[8], true );
	}

	@Test
	public void typeExactMatchAndClickOutside() throws InterruptedException {
		WebElement clientes = salePage.getClientField();
		clientes.clear();
		clientes.sendKeys( "Divino Grão Prod. Naturais LTDA" );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		salePage.clickOnDateField();
		checkPoint( "Match exato - item único selecionado não confere - clicando fora.",
				"Divino Grão Prod. Naturais LTDA", salePage.getClientFieldValue(), true );
	}

	@Test
	public void typeExactMatchAndClickTab() throws InterruptedException {
		WebElement clientes = salePage.getClientField();
		clientes.clear();
		clientes.sendKeys( "Divino Grão Prod. Naturais LTDA" );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.TAB );
		checkPoint( "Match exato - item único selecionado não confere - saindo com Tab.",
				"Divino Grão Prod. Naturais LTDA", salePage.getClientFieldValue(), true );
	}

	@Test
	public void typeExactMatchAndClickShiftTab() throws InterruptedException {
		WebElement clientes = salePage.getClientField();
		clientes.clear();
		clientes.sendKeys( "Divino Grão Prod. Naturais LTDA" );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.SHIFT, Keys.TAB );
		checkPoint( "Match exato - item único selecionado não confere - saindo com Shift - Tab.",
				"Divino Grão Prod. Naturais LTDA", salePage.getClientFieldValue(), true );
	}

	@Test
	public void typeExactMatchAndClickEnter() throws InterruptedException {
		WebElement clientes = salePage.getClientField();
		clientes.clear();
		clientes.sendKeys( "CDivino Grão Prod. Naturais LTDA" );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.ENTER );
		checkPoint( "Match exato - item único selecionado não confere - saindo com Enter.",
				"Divino Grão Prod. Naturais LTDA", salePage.getClientFieldValue(), true );
	}

	@Test
	public void typeExactMatchAndClickOnSugestion() throws InterruptedException {
		WebElement clientes = salePage.getClientField();
		clientes.clear();
		clientes.sendKeys( "Divino Grão Prod. Naturais LTDA" );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).clickOnAutoCompleteSugestionOption( 0 );
		checkPoint( "Match exato - item único selecionado não confere - clicando no item.",
				"Divino Grão Prod. Naturais LTDA",
				salePage.getClientFieldValue(), true );
	}

	@Test
	public void clickOnAddNewButton() throws InterruptedException {
		salePage.clickOnSalesmanField();
		WebElement salesmen = salePage.getSalesmanField();
		salesmen.clear();
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).clickOnAutoCompleteSugestionOption( 1 );
		checkPoint( "Clicar no botão Adicionar novo - mensagem de confirmação não foi exibida",
				"Digite para adicionar um novo", getAssistants().getAutoCompleteAssistant( getWebDriver() )
						.getAutoCompleteNotificationMessage(), true );
	}

	@Test
	public void typeNewValueAndClickAddNew() {
		salePage.expandShippingSection();
		String randomId = "NovaTransportadora" + "_" + Double.toString( Math.random() );
		randomId = randomId.substring( 0, Math.min( 30, randomId.length() - 1 ) );
		WebElement transpAutoComplete = salePage.getTransportersField();
		transpAutoComplete.clear();
		transpAutoComplete.sendKeys( randomId );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).clickOnAutoCompleteSugestionOption( 0 );
		checkPoint( "Digitar valor novo e clique Adicionar novo. Não criou novo item.", "'" + randomId
				+ "' inserido com sucesso", getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteNotificationMessage(), true );
	}

	@Test
	public void typeNewValueAndClickOutside() throws InterruptedException {
		salePage.expandShippingSection();
		String randomId = "NovaTransportadora" + "_" + Double.toString( Math.random() );
		randomId = randomId.substring( 0, Math.min( 30, randomId.length() - 1 ) );
		WebElement transpAutoComplete = salePage.getTransportersField();
		transpAutoComplete.clear();
		transpAutoComplete.sendKeys( randomId );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		salePage.clickOnDateField();
		checkPoint( "Digitar valor novo e clicar fora. Não criou novo item.",
				"'" + randomId + "' inserido com sucesso", getAssistants().getAutoCompleteAssistant( getWebDriver() )
						.getAutoCompleteNotificationMessage(), true );
	}

	@Test
	public void typeNewValueAndClickTab() {
		salePage.expandShippingSection();
		String randomId = "NovaTransportadora" + "_" + Double.toString( Math.random() );
		randomId = randomId.substring( 0, Math.min( 30, randomId.length() - 1 ) );
		WebElement transpAutoComplete = salePage.getTransportersField();
		transpAutoComplete.clear();
		transpAutoComplete.sendKeys( randomId );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.TAB );
		checkPoint( "Digitar valor novo e clique Tab. Não criou novo item.", "'" + randomId + "' inserido com sucesso",
				getAssistants().getAutoCompleteAssistant( getWebDriver() ).getAutoCompleteNotificationMessage(), true );
	}

	@Test
	public void typeNewValueAndClickShiftTab() {
		salePage.expandShippingSection();
		String randomId = "NovaTransportadora" + "_" + Double.toString( Math.random() );
		randomId = randomId.substring( 0, Math.min( 30, randomId.length() - 1 ) );
		WebElement transpAutoComplete = salePage.getTransportersField();
		transpAutoComplete.clear();
		transpAutoComplete.sendKeys( randomId );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.SHIFT, Keys.TAB );
		checkPoint( "Digitar valor novo e clique Shift - Tab. Não criou novo item.", "'" + randomId
				+ "' inserido com sucesso", getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteNotificationMessage(), true );
	}

	@Test
	public void typeNewValueAndClickEnter() {
		salePage.expandShippingSection();
		String randomId = "NovaTransportadora" + "_" + Double.toString( Math.random() );
		randomId = randomId.substring( 0, Math.min( 30, randomId.length() - 1 ) );
		WebElement transpAutoComplete = salePage.getTransportersField();
		transpAutoComplete.clear();
		transpAutoComplete.sendKeys( randomId );
		getAssistants().getAutoCompleteAssistant( getWebDriver() ).waitForAutoCompleteSugestions();
		getWebDriver().findElement( By.tagName( "body" ) ).sendKeys( Keys.ENTER );
		checkPoint( "Digitar valor novo e clique Enter. Não criou novo item.", "'" + randomId
				+ "' inserido com sucesso", getAssistants().getAutoCompleteAssistant( getWebDriver() )
				.getAutoCompleteNotificationMessage(), true );
	}

}
