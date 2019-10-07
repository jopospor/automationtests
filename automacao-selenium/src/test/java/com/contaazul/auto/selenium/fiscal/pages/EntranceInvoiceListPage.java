package com.contaazul.auto.selenium.fiscal.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class EntranceInvoiceListPage extends WebPage {

	@FindBy(className = "titleGrid")
	protected WebElement firstInvoice;

	public EntranceInvoiceListPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void clickOnFirstInvoice() {
		firstInvoice.click();
	}

}
