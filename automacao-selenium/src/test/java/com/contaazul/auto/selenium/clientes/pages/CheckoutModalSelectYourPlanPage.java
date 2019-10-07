package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class CheckoutModalSelectYourPlanPage extends WebPage {

	@FindBy(id = "details-first")
	protected WebElement financialControl;

	@FindBy(id = "details-third")
	protected WebElement salesControl;

	@FindBy(id = "details-second")
	protected WebElement inventoryControl;

	@FindBy(id = "details-fourth")
	protected WebElement reports;

	@FindBy(id = "MEDIUM")
	protected WebElement planMedium;

	@FindBy(id = "SMALL")
	protected WebElement planSmall;

	@FindBy(id = "MICRO")
	protected WebElement planMicro;

	@FindBy(id = "STARTUP")
	protected WebElement planStartup;

	@FindBy(id = "FREELANCER")
	protected WebElement planFreelancer;

	public void financialControl() {
		financialControl.click();
	}

	public void salesControl() {
		salesControl.click();
	}

	public void inventoryControl() {
		inventoryControl.click();
	}

	public void reports() {
		reports.click();
	}

	public void planMedium() {
		planMedium.click();
	}

	public void planSmall() {
		planSmall.click();
	}

	public void planMicro() {
		planMicro.click();
	}

	public void planFreelancer() {
		planFreelancer.click();
	}

	public void planStartup() {
		planStartup.click();
	}

	public CheckoutModalSelectYourPlanPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

}
