package com.contaazul.auto.selenium.vendas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class PurchaseListPage extends WebPage {

	@FindBy(id = "purchase")
	protected WebElement issuePurchaseInvoice;

	public PurchaseListPage(WebDriver driver) {
		super( (RemoteWebDriver) (driver) );
	}

	public void clickOnIssuePurchaseInvoice() {
		issuePurchaseInvoice.click();
	}

}
