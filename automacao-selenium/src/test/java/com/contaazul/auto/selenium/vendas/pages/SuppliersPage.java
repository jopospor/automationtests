package com.contaazul.auto.selenium.vendas.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class SuppliersPage extends WebPage {

	@FindBy(linkText = "Importar")
	private WebElement importButton;

	public SuppliersPage(RemoteWebDriver driver) {
		super( driver );
	}

	public void clickImport() {
		importButton.click();
	}

}
