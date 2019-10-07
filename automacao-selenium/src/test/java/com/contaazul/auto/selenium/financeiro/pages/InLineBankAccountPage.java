package com.contaazul.auto.selenium.financeiro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import com.contaazul.auto.selenium.WebPage;

public class InLineBankAccountPage extends WebPage {

	public WebElement checkbox;
	public WebElement nomeBanco;
	public WebElement nomeConta;
	public WebElement boletoConfigurado;

	public InLineBankAccountPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void setReferenciaNaTela(WebElement contaBancariaInlineElement) {
		checkbox = contaBancariaInlineElement.findElement( By
				.className( "checkbox" ) );
		nomeBanco = contaBancariaInlineElement.findElement( By.xpath( "/td[2]" ) );
		nomeConta = contaBancariaInlineElement.findElement( By.xpath( "/td[3]" ) );
		boletoConfigurado = contaBancariaInlineElement.findElement( By
				.xpath( "/td[4]" ) );
	}

	public void clicaCheckBox() {
		checkbox.click();
	}

	public String getNomeBanco() {
		try {
			return nomeBanco.getText();
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu obter valor do campo. InLineBankAccountPage foi inicializado?" );
			return "";
		}
	}

	public String getNomeConta() {
		try {
			return nomeConta.getText();
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu obter valor do campo. InLineBankAccountPage foi inicializado?" );
			return "";
		}
	}

	public String getBoletoConfigurado() {
		try {
			return boletoConfigurado.getText();
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu obter valor do campo. InLineBankAccountPage foi inicializado?" );
			return "";
		}
	}

	public void clicarContaBancaria() {
		this.nomeBanco.click();
	}

}
