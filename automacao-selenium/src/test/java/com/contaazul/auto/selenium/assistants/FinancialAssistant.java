package com.contaazul.auto.selenium.assistants;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.contaazul.auto.selenium.WebPage;

public class FinancialAssistant extends WebPage {

	public FinancialAssistant(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public String getTypeTransaction(String line, Integer nrLine) {
		return getAssistants().getListingAssistant( driver ).getListingRowAsElement( nrLine )
				.findElement( By.xpath( "td[" + line + "]/span" ) ).getText();
	}

	public String getBalance(Integer nrLine) {
		return getAssistants().getListingAssistant( driver ).getListingRowAsElement( nrLine )
				.findElement( By.xpath( "td[6]" ) ).getText();
	}

	public String getNameTransaction(Integer nrLine) {
		return getAssistants().getListingAssistant( driver ).getListingRowAsElement( nrLine )
				.findElement( By.xpath( "td[4]/div/span" ) ).getText();
	}

	public String getValue(Integer nrLine) {
		return getAssistants().getListingAssistant( driver ).getListingRowAsElement( nrLine )
				.findElement( By.xpath( "td[5]" ) ).getText();
	}

	public void openMenuDropDownFinance() {
		sleep( VERY_LONG );
		driver.findElementByXPath( "//*[@id='addFinance']/button[2]" ).click();
	}

	public void optionMenuFinance(String option) {
		driver.findElementByXPath( "//span[contains(text(), '" + option + "')]" ).click();
	}

	public void navigateMenuFinance(String option) {
		sleep( VERY_LONG );
		driver.findElementByLinkText( option ).click();
		sleep( VERY_LONG );
	}

	public String getMessageTransaction() {
		sleep( VERY_LONG );
		List<WebElement> elements = driver
				.findElementsByXPath( "//h2[contains(text(),'Importar Extrato.')]" );
		Iterator<WebElement> elements2 = elements.iterator();
		while (elements2.hasNext()) {
			WebElement we = (WebElement) elements2.next();
			if (we.isDisplayed())
				return we.getText();
		}
		return null;
	}

	public String getMessageBlankSlate() {
		return driver.findElement( By.xpath( "//*[@id='financeiroListBlankSlateContainer']/div[2]/h2" ) ).getText();
	}
}
