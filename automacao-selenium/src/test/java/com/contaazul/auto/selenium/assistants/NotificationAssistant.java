package com.contaazul.auto.selenium.assistants;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.contaazul.auto.selenium.WebPage;

/**
 * Assistente para validar mensagens de notificação do sistema
 */

public class NotificationAssistant extends WebPage {

	public NotificationAssistant(WebDriver driver) {
		super( driver );
	}

	/**
	 * 
	 * Pega o texto da mensagem de alerta que aparece no todo do conta azul
	 * 
	 * @return
	 */

	public String getAlertMessageText() {
		waitForElementPresent( By.cssSelector( ".container .container-message" ) );
		return this.driver.findElementByCssSelector( ".container .container-message" ).getText();
	}

	/**
	 * 
	 * Pega o texto da mensagem de notificação de um campo
	 * 
	 * @return
	 */

	public String getFieldMessageText() {
		waitForElementPresent( By.xpath( "//div[@id='fieldNotificationMessageContainer']" ) );
		return this.driver.findElementByXPath( "//div[@id='fieldNotificationMessageContainer']" ).getText();
	}

	public boolean hasMessage() {
		sleep( QUICKLY );
		return this.driver.findElementByCssSelector( ".container .container-message" ).isDisplayed();
	}

	/**
	 * Espera que a mensagem de 'Carregando...' seja mostrada
	 */

	public void waitLoadingAppear() {
		waitForElementPresent( By.id( "loading" ) );
	}

	/**
	 * Espera que a mensagem de 'Carregando...' desapareça
	 */

	public void waitLoadingDismiss() {
		waitForElementNotPresent( By.id( "loading" ) );
	}

	/**
	 * Espera que a mensagem de 'Carregando...' seja mostrada e desapareça
	 */

	public void waitLoading() {
		waitLoadingAppear();
		waitLoadingDismiss();
	}

	public void waitAlertMessageDismiss() {
		waitForElementNotPresent( By.cssSelector( ".container .container-message" ) );
	}

	public void waitMessageDismiss() {
		waitForElementNotPresent( By.className( "container-message" ) );
	}

	public void waitMessageAppear() {
		waitForElementPresent( By.className( "container-message" ) );
	}

	public String msgConcileValidation() {
		sleep( FOR_A_LONG_TIME );
		return driver.findElementByXPath( "//*[@id='importBlankslate']/h2" ).getText();
	}

}
