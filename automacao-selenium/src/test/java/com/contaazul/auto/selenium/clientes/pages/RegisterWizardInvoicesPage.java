package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class RegisterWizardInvoicesPage extends WebPage {

	@FindBy(className = "name")
	protected WebElement greetings;
	@FindBy(name = "configuracaoConta.usoProdutoServico")
	protected WebElement produtosServicosRadio;

	// "Comercializa apenas produtos."
	@FindBy(xpath = "//*[contains(text(), 'De produto (NF-e) e serviço (NFS-e)')]")
	protected WebElement produtosEServicosRadio;
	@FindBy(xpath = "//a[contains(@class, 'btn-primary')]")
	protected WebElement proximoButton;

	public RegisterWizardInvoicesPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void selectProductsAndServices() {
		produtosEServicosRadio.click();
	}

	public void clickProximoButton() {
		proximoButton.click();
		sleep( VERY_LONG );
	}

	public void waitForVideo() {
		waitForElementPresent( By.id( "introductionVideo" ), 40 );
	}

}
