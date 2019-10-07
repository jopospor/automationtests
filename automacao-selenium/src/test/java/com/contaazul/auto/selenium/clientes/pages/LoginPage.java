package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class LoginPage extends WebPage {

	@FindBy(id = "username_login")
	protected WebElement campoUsuario;
	@FindBy(id = "password_login")
	protected WebElement campoSenha;
	@FindBy(xpath = "//button[@type='submit']")
	protected WebElement botaoSubmit;
	@FindBy(id = "feedback")
	protected WebElement mensagemDeValidacao;
	@FindBy(xpath = "//a[@href='#modalCadastro']")
	protected WebElement cadastrese;
	@FindBy(linkText = "Cadastre-se gratuitamente agora!")
	protected WebElement clicaCadastro;

	public LoginPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void preencherCampoUsuario(String userName) {
		campoUsuario.sendKeys( userName );
	}

	public void preencherCampoSenha(String password) {
		campoSenha.sendKeys( password );
	}

	public void submit() {
		botaoSubmit.click();
	}

	public String getMensagemDeErroLogin() {
		return mensagemDeValidacao.getText();
	}

	public boolean estaNaPaginaDeLogin() {
		try {
			waitForElementPresent( By.id( "username_login" ) );
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void clickCadastro() {
		clicaCadastro.click();
	}

	public void clicaNovoCadastro() {
		cadastrese.click();
	}

}
