package com.contaazul.auto.selenium.vendas.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class ListSalesOrderFormPage extends WebPage {

	public ListSalesOrderFormPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	@FindBy(linkText = "Novo Pedido")
	private WebElement buttonNewSalesOrder;

	@FindBy(id = "search_pedidoVendaSearch")
	private WebElement search;

	@FindBy(id = "searchButton_pedidoVendaSearch")
	private WebElement buttonSearch;

	public WebElement getButtonNewSalesOrder() {
		return buttonNewSalesOrder;
	}

	public void searchByClient(String clientName) {
		search.sendKeys( clientName );
		buttonSearch.click();
	}

	public void clickOnSalesOrder(String clientName) {
		waitForElementNotStale( By.xpath( "//td[contains(text(), '" + clientName + "')]" ) );
		driver.findElement( By.xpath( "//td[contains(text(), '" + clientName + "')]" ) ).click();
	}

}
