package com.contaazul.auto.selenium.fiscal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.contaazul.auto.selenium.WebPage;

public class InvoicePage extends WebPage {

	@FindBy(id = "dsCfop0")
	public WebElement cfopField;

	@FindBy(css = "li.dataFieldName")
	public WebElement autoCompletePopUp;

	public InvoicePage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void typeOnCFOPField(String text) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( cfopField, text );
	}

	public void clearCFOPField() {
		cfopField.clear();
		try {
			waitForElementNotPresent( "autoCompleteSugestions", 3 );
		} catch (Exception e) {
			Reporter.log( "InvoicePage clearCFOPField autoCompleteSugestions estava presente.", true );
		}
	}

	public void clickOutOfCFOPInput() {
		driver.findElement( By.xpath( "//th[contains(text(), 'CFOP')]" ) ).click();
	}

	public String getPopUpResult(final String input) {
		try {
			// permitir tempo para as sugestões se renovarem
			(new WebDriverWait( driver, 10 )).until( new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.findElement( By.cssSelector( "li.dataFieldName" ) ).getText().contains( input );
				}
			} );
		} catch (Exception e) {
			// falha em silêncio pois o teste pode esperar null
			return null;
		}
		return autoCompletePopUp.getText();
	}
}
