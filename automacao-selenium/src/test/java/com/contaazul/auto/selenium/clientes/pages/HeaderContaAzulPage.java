package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class HeaderContaAzulPage extends WebPage {

	@FindBy(id = "accountUserLogged")
	WebElement nomeDoUsuario;
	@FindBy(id = "logoutApp")
	WebElement botaoSair;

	public HeaderContaAzulPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public String getNomeUsuarioLogado() {
		javascript( "$('liPerfil').click()" );
		return nomeDoUsuario.getText();
	}

	public void logout() {
		botaoSair.click();
	}

	public void logoutNewMenu() {
		javascript( "$('liPerfil').click()" );
		botaoSair.click();
	}

}
