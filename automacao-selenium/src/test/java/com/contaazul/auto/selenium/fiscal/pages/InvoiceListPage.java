package com.contaazul.auto.selenium.fiscal.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class InvoiceListPage extends WebPage {
	@FindBy(className = "titleGrid2")
	protected WebElement firstInvoice;

	public InvoiceListPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void clickOnFirstInvoice() {
		firstInvoice.click();
	}
}
