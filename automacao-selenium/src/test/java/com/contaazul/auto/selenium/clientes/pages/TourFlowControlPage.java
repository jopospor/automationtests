package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class TourFlowControlPage extends WebPage {

	@FindBy(xpath = "//div[@class='jTour_content']/h1")
	protected WebElement titleInitialCashFlow;
	@FindBy(xpath = "//button[contains(text(), 'Contas Bancarias »')]")
	protected WebElement goToContasBancarias;

	public TourFlowControlPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public String getTitleInitialCashFlow() {
		return titleInitialCashFlow.getText();
	}

}
