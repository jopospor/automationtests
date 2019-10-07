package com.contaazul.auto.selenium.assistants;

import java.util.Iterator;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.WebPage;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

@Log4j
public class AutoCompleteAssistant extends WebPage {

	public AutoCompleteAssistant(RemoteWebDriver driver) {
		super( driver );
	}

	/**
	 * Envia um valor ao campo AutoComplete e seleciona a opção de criar novo
	 * elemento. Não funcionará em AutoCompletes somente leitura.
	 * 
	 * @param autoCompleteField
	 * @param keysToSend
	 */
	public void sendKeysAndCreateNew(WebElement autoCompleteField, String keysToSend) {
		String autoCompleteFieldId = extractAutoCompleteId( autoCompleteField );
		autoCompleteField = waitForAutoCompleteNotStale( autoCompleteFieldId );
		clearAutoCompleteField( autoCompleteField );
		autoCompleteField.sendKeys( keysToSend );
		sleep( QUICKLY );
		waitForAddNewButton();
		pickFirstAutoCompleteOptionBeingDisplayedAndWaitConfirmation( keysToSend );
		blurAutoCompleteAndWaitForMessagesToClose( autoCompleteFieldId );
	}

	private void waitForAddNewButton() {
		waitForPresenceOfSuggestions();
		waitForPresenceOfAddNewButton();
	}

	private void waitForPresenceOfSuggestions() {
		waitForElementPresent( By.cssSelector( "li.dataFieldName" ) );
	}

	private void waitForPresenceOfAddNewButton() {
		waitForElementPresent( By.xpath( "//li[contains(text(),'(Adicionar novo)...')]" ) );
	}

	/**
	 * Envia um valor ao campo AutoComplete e seleciona a opção exibida que dê
	 * match exato no valor passado.
	 * 
	 * @param autoCompleteField
	 * @param keysToSend
	 */
	public void sendKeysAndSelectExisting(WebElement autoCompleteField,
			final String keysToSend) {
		String autoCompleteFieldId = extractAutoCompleteId( autoCompleteField );
		autoCompleteField = waitForAutoCompleteNotStale( autoCompleteFieldId );
		clearAutoCompleteField( autoCompleteField );
		autoCompleteField.sendKeys( keysToSend );
		sleep( QUICKLY );
		waitForElementPresent( By.className( "contaazul-components-autocomplete-sugestions" ) );
		sleep( QUICKLY );
		final WebElement suggestionsBox = driver.findElement( By
				.className( "contaazul-components-autocomplete-sugestions" ) );
		waitUntilOptionAppearsOnSuggestionBox( keysToSend, suggestionsBox );
		clickOnOptionIfItAppearsOnSuggestionBox( keysToSend, suggestionsBox );
		sleep( QUICKLY );
		waitForAutoCompleteText( autoCompleteField, keysToSend );
		waitForElementNotPresent( "contaazul-components-autocomplete-sugestions" );
	}

	private void clickOnOptionIfItAppearsOnSuggestionBox(final String keysToSend, final WebElement suggestionsBox) {
		List<WebElement> suggestions = suggestionsBox.findElements( By.className( "dataFieldName" ) );
		Iterator<WebElement> it = suggestions.iterator();
		WebElement sugestedOption;
		while (it.hasNext()) {
			sugestedOption = it.next();
			if (sugestedOption.getText().startsWith( keysToSend )
					&& sugestedOption.getText().contains( keysToSend )) {
				sugestedOption.click();
				break;
			}
		}
	}

	private void waitUntilOptionAppearsOnSuggestionBox(final String keysToSend, final WebElement suggestionsBox) {
		new WebDriverWait( driver, getSessionDefaultTimeout() ).until(
				new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						List<WebElement> suggestions = suggestionsBox.findElements( By.className( "dataFieldName" ) );
						Iterator<WebElement> it = suggestions.iterator();
						WebElement sugestedOption;
						while (it.hasNext()) {
							sugestedOption = it.next();
							if (sugestedOption.getText().startsWith( keysToSend )
									&& sugestedOption.getText().contains( keysToSend )) {
								return true;
							}
						}
						return false;
					}
				}
				);
	}

	public void waitForAutoCompleteText(final WebElement autoCompleteField, final String keysToSend) {
		try {
			int timeExpire = Integer.parseInt( SeleniumSession.getSession().getProperties()
					.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) );

			(new WebDriverWait( driver, timeExpire )).until( new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					try {
						return retrieveValueByJavaScript( autoCompleteField ).contains( keysToSend );
					} catch (Exception e) {
						return false;
					}
				}
			} );
		} catch (Exception e) {
			log.info( e );
			Reporter.log( e.getMessage(), true );
		}
	}

	/**
	 * Retorna o valor atual do campo AutoComplete - método especializado que
	 * aguarda o valor ser setado propriamente no AutoComplete.
	 * 
	 * @param autoCompleteField
	 * @return
	 */
	public String getText(WebElement autoCompleteField) {
		String currentValue = retrieveValueByJavaScript( autoCompleteField );
		if (currentValue == "") {
			// pode não ter carregado ainda os valores, eles são às vezes
			// atualizados por postback
			sleep( FOR_A_LONG_TIME );
			currentValue = retrieveValueByJavaScript( autoCompleteField );
		}
		return currentValue;
	}

	private String retrieveValueByJavaScript(WebElement autoCompleteField) {
		String id = extractAutoCompleteId( autoCompleteField );
		String script = "return document.getElementById('" + id + "').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

	public String extractAutoCompleteId(WebElement autoCompleteField) {
		String autoCompleteFieldId = autoCompleteField.getAttribute( "id" );
		if (autoCompleteFieldId == null)
			Assert.fail( "Campo autocomplete não possuía atributo ID. É obrigatório que o autocomplete tenha ID para utilizar o assistente pois é usado getElementById com JavaScript." );
		return autoCompleteFieldId;
	}

	public WebElement waitForAutoCompleteNotStale(final String autoCompleteFieldId) {
		waitForElementNotStale( By.id( autoCompleteFieldId ) );
		return driver.findElement( By.id( autoCompleteFieldId ) );
	}

	private void clearAutoCompleteField(WebElement autoCompleteField) {
		autoCompleteField.clear();
		waitForAutoCompleteText( autoCompleteField, "" );
	}

	private void pickFirstAutoCompleteOptionBeingDisplayedAndWaitConfirmation(String keysToSend) {
		driver.findElement( By.cssSelector( "li.dataFieldName" ) ).click();
		waitForText( By.id( "fieldNotificationMessageContainer" ), "'" + keysToSend + "' inserido com sucesso" );
	}

	private void blurAutoCompleteAndWaitForMessagesToClose(String autoCompleteFieldId) {
		String script = "document.getElementById('" + autoCompleteFieldId + "').blur()";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		try {
			j.executeScript( script );
		} catch (Exception e) {
			driver.findElement( By.id( autoCompleteFieldId ) ).click();
		}
		waitForElementNotPresent( "fieldNotificationMessageContainer",
				Integer.valueOf( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	/**
	 * Método auxiliar para clicar em uma opção que já esteja sendo exibida pelo
	 * AutoComplete.
	 * 
	 * @param optionIndex
	 *            Linha a ser clicada - base 1.
	 */
	public void clickOnAutoCompleteSugestionOption(int optionIndex) {
		waitForAutoCompleteSugestions();
		try {
			List<WebElement> suggestions = driver.findElements( By.className( "dataFieldName" ) );
			Iterator<WebElement> suggestionListIterator = suggestions.iterator();
			int currentOption = 0;
			WebElement ele = null;
			while (suggestionListIterator.hasNext() && currentOption <= optionIndex) {
				ele = ((WebElement) suggestionListIterator.next());
				currentOption++;
			}
			ele.click();
		} catch (ElementNotFoundException e) {
			String message = "AVISO: Opções do AutoComplete não estavam presentes. Exceção:" + e.getMessage();
			log.info( message );
			Reporter.log( message, true );
		}
	}

	/**
	 * @return Array contendo todos os valores das sugestões já sendo exibidas
	 *         pelo AutoComplete no momento.
	 */
	public String[] getAutoCompleteSugestionOptions() {
		try {
			waitForAutoCompleteSugestions();
			List<WebElement> sugestoes = driver.findElements( By.className( "dataFieldName" ) );
			Iterator<WebElement> i = sugestoes.iterator();
			int j = 0;
			String[] retorno = new String[300];
			while (i.hasNext()) {
				WebElement ele = ((WebElement) i.next());
				retorno[j] = ele.getText();
				j++;
			}
			return retorno;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Verifica se opção está sendo exibida no momento nas sugestões.
	 * 
	 * @param autocomplete
	 * @param optionName
	 * @return
	 */
	public boolean containsOption(WebElement autocomplete, String optionName) {
		clearAutoCompleteField( autocomplete );
		autocomplete.sendKeys( optionName );
		for (String autoCompleteSugestionOption : getAutoCompleteSugestionOptions())
			if (autoCompleteSugestionOption.equals( optionName ))
				return true;
		return false;
	}

	/**
	 * Método auxiliar de sinconização, aguarda a caixa de sugestões estar
	 * presente.
	 */
	public void waitForAutoCompleteSugestions() {
		waitForElementPresent( By.id( "autocompleteSugestions" ) );
	}

	/**
	 * Método auxiliar, clica na opção 'Mostar mais' quando ela já estiver sendo
	 * exibida.
	 */
	public void clickOnShowMoreOptions() {
		driver.findElement( By.xpath( "//li[contains(text(), 'Mostrar mais...')]" ) ).click();
	}

	/**
	 * @return a mensagem que estiver contida na caixa de notificações no
	 *         momento, por exemplo, a mensagem de confirmação de criação de um
	 *         novo item.
	 */
	public String getAutoCompleteNotificationMessage() {
		waitForElementEnabled( By.id( "fieldNotificationMessageContainer" ) );
		waitForNotificationMessageSet();
		return driver.findElement( By.id( "fieldNotificationMessageContainer" ) ).getText();
	}

	private void waitForNotificationMessageSet() {
		try {
			(new WebDriverWait( driver, Integer.parseInt( SeleniumSession.getSession().getProperties()
					.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) )).until( new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					try {
						return driver.findElement( By.id( "fieldNotificationMessageContainer" ) ).getText() != "";
					} catch (NoSuchElementException e) {
						return false;
					} catch (StaleElementReferenceException ex) {
						return false;
					}
				}
			} );
		} catch (Exception e) {
			log.info( e );
			Reporter.log( e.getMessage(), true );
		}
	}

	/**
	 * Método para o cadastrar um novo item pelo AutoComplete 2.0
	 * 
	 * Obs: é provisório pois a automação do novo autocomplete não está
	 * finalizada
	 */
	public void newSendKeysAndCreateNew(WebElement autoCompleteField, String keysToSend, String type, Integer index) {
		String autoCompleteFieldId = getAssistants().getAutoCompleteAssistant( driver ).extractAutoCompleteId(
				autoCompleteField );
		autoCompleteField = getAssistants().getAutoCompleteAssistant( driver ).waitForAutoCompleteNotStale(
				autoCompleteFieldId );
		getAssistants().getAutoCompleteAssistant( driver ).clearAutoCompleteField( autoCompleteField );

		sleep( VERY_LONG );
		autoCompleteField.sendKeys( keysToSend );

		sleep( VERY_LONG );
		driver.findElement( By.className( "addItem" ) ).click();
		Select opcoes = new Select( driver.findElement( By.id( "itemType" ) ) );
		opcoes.selectByVisibleText( type );

		sleep( VERY_LONG );
		List<WebElement> list = driver.findElements( By.id( "saveItem" ) );
		list.get( index ).click();
	}

	/**
	 * Método para selecionar um item pelo AutoComplete 2.0
	 * 
	 * Obs: é provisório pois a automação do novo autocomplete não está
	 * finalizada
	 */
	public void newSendKeysAndSelectExisting(WebElement autoCompleteField,
			String keysToSend) {
		String autoCompleteFieldId = getAssistants().getAutoCompleteAssistant( driver ).extractAutoCompleteId(
				autoCompleteField );
		autoCompleteField = getAssistants().getAutoCompleteAssistant( driver ).waitForAutoCompleteNotStale(
				autoCompleteFieldId );
		getAssistants().getAutoCompleteAssistant( driver ).clearAutoCompleteField( autoCompleteField );
		autoCompleteField.sendKeys( keysToSend );
		sleep( VERY_LONG );
		List<WebElement> temp = driver.findElements( By.className( "sugestions-option" ) );
		Iterator<WebElement> it = temp.iterator();
		WebElement suggestedOption;
		while (it.hasNext()) {
			suggestedOption = it.next();
			if (suggestedOption.getText().startsWith( keysToSend ) && suggestedOption.getText().contains( keysToSend )) {
				suggestedOption.click();
				break;
			}
		}
		sleep( VERY_LONG );
		getAssistants().getAutoCompleteAssistant( driver ).waitForAutoCompleteText( autoCompleteField, keysToSend );
		waitForElementNotPresent( "contaazul-components-autocomplete-sugestions" );
	}

}
