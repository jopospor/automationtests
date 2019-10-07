package com.contaazul.auto.selenium.vendas.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class ClientListPage extends WebPage {

	@FindBy(linkText = "Excluir")
	private WebElement btnDeleteClient;

	public ClientListPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void clickImportClients() {
		driver.findElement( By.linkText( "Importar" ) ).click();
	}

	public void clickUploadFile() {
		driver.findElementById( "importFileButton" ).click();
	}

	public void selectAllCheckBox() {
		driver.findElementByXPath( "//table[@id='row']/thead/tr/th/span" ).click();
	}

	public void clickDeleteClient() {
		btnDeleteClient.click();
	}

	public void clickAlert() {
		driver.switchTo().alert().accept();
	}
}
