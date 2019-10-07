package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class NewUserStepsPage extends WebPage {

	@FindBy(className = "act-see-video")
	protected WebElement seeVideoLink;

	@FindBy(linkText = "Cadastre suas despesas")
	protected WebElement insertExpenses;

	@FindBy(linkText = "Cadastre seus clientes")
	protected WebElement insertClients;

	@FindBy(linkText = "Cadastre seus produtos")
	protected WebElement insertProducts;

	@FindBy(id = "closeVideo")
	protected WebElement fecharPopupdeVideo;

	public NewUserStepsPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void clicarVerVideo() {
		seeVideoLink.click();
	}

	public void clicarFecharPopupVideo() {
		fecharPopupdeVideo.click();
	}

	public void clicarCadastroDeDesepesas() {
		insertExpenses.click();
	}

	public void clicarCadastroDeClientes() {
		insertClients.click();
	}

	public void clicarCadastroDeProdutos() {
		insertProducts.click();
	}

}
