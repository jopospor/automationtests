package com.contaazul.auto.selenium.vendas.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class NewProductSalePage extends WebPage {

	@FindBy(id = "cliente")
	private WebElement client;
	@FindBy(id = "dtEmissao")
	private WebElement dtEmissao;
	@FindBy(linkText = "Informar frete")
	private WebElement expandShippingSection;
	@FindBy(id = "Transportadora")
	private WebElement transporter;
	@FindBy(id = "Vendedor")
	private WebElement salesman;
	@FindBy(linkText = "Informações para comissão de vendedores")
	private WebElement expandSalesmanSection;
	@FindBy(linkText = "Informar frete")
	private WebElement expandTransporterSection;

	public NewProductSalePage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void setClient(String clientName) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting(
				driver.findElement( By.id( "cliente" ) ), clientName );
	}

	public void clickOnClientField() {
		// Evita StaleElement
		sleep( 1000 );
		client = driver.findElement( By.id( "cliente" ) );
		client.click();
	}

	public void clearClientField() {
		client.clear();
	}

	public String getClientFieldValue() {
		return getAssistants().getAutoCompleteAssistant( driver ).getText( client );
	}

	public WebElement getClientField() {
		return driver.findElement( By.id( "cliente" ) );
	}

	public void setSalesman(String salesmanName) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting(
				driver.findElement( By.id( "Vendedor" ) ), salesmanName );
	}

	public void clickOnSalesmanField() {
		sleep( 1000 );
		expandSalesmanSection.click();
		try {
			salesman.click();
		} catch (ElementNotVisibleException e) {
			expandSalesmanSection.click();
			salesman.click();
		}
	}

	public WebElement getSalesmanField() {
		if (!salesman.isDisplayed())
			expandSalesmanSection.click();
		return salesman;
	}

	public void expandShippingSection() {
		expandShippingSection.click();
	}

	public void clickOnDateField() {
		dtEmissao.click();
	}

	public WebElement getTransportersField() {
		if (!transporter.isDisplayed())
			expandTransporterSection.click();
		return transporter;
	}

}
