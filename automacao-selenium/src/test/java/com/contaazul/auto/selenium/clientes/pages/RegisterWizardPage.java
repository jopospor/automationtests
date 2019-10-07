package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class RegisterWizardPage extends WebPage {

	@FindBy(className = "name")
	protected WebElement greetings;
	@FindBy(name = "configuracaoConta.usoProdutoServico")
	protected WebElement produtosServicosRadio;

	// "Comercializa apenas produtos."
	@FindBy(xpath = "//*[contains(text(), 'Comercializa apenas produtos..')]")
	protected WebElement apenasProdutosRadio;
	@FindBy(xpath = "//*[contains(text(), 'Presta apenas serviços.')]")
	// configuracaoConta.usoProdutoServico")
	protected WebElement apenasServicosRadio;
	@FindBy(xpath = "//*[contains(text(), 'Comercializa produtos e também presta serviços.')]")
	protected WebElement servicosProdutosRadio;
	@FindBy(xpath = "//*[contains(text(), 'Pretende usar apenas o controle financeiro, dispensando o de produtos e serviços.')]")
	protected WebElement semServicosNemProdutos;
	@FindBy(xpath = "//a[contains(@class, 'btn-primary')]")
	protected WebElement proximoButton;

	public RegisterWizardPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void selectOnlyProducts() {
		apenasProdutosRadio.click();
	}

	public void selectOnlyServices() {
		apenasServicosRadio.click();
	}

	public void selectServicesAndProducts() {
		servicosProdutosRadio.click();
	}

	public void selectNoServicesNoProducts() {
		semServicosNemProdutos.click();
	}

	public void clickProximoButton() {
		proximoButton.click();
	}

}
