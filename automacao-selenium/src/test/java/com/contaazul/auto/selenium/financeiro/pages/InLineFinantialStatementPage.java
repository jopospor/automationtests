package com.contaazul.auto.selenium.financeiro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import com.contaazul.auto.selenium.WebPage;

public class InLineFinantialStatementPage extends WebPage {

	public WebElement checkbox;
	public WebElement dataVencimento;
	public WebElement recorrencia;
	public WebElement categoria;
	public WebElement nome;
	public WebElement cliente;
	public WebElement valor;
	public WebElement pago;

	public InLineFinantialStatementPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void setReferenciaNaTela(WebElement lancamentoInlineElement) {
		checkbox = lancamentoInlineElement.findElement( By
				.xpath( "//span[contains(@class, 'mark')]" ) );// checkbox-colunm"));//mark"));
		dataVencimento = lancamentoInlineElement.findElement( By
				.xpath( "//td[@data-original-title='Data de vencimento']" ) );
		categoria = lancamentoInlineElement.findElement( By
				.xpath( "//td[contains(@class, 'statement')]/span" ) );// ]label
																		// limit-text
																		// span-label-description
																		// act-label-category
																		// new_tooltip"));
		recorrencia = lancamentoInlineElement.findElement( By
				.className( "recurrence" ) );
		nome = lancamentoInlineElement.findElement( By
				.xpath( "//div[@class='statement-table']/span" ) );
		cliente = lancamentoInlineElement.findElement( By
				.xpath( "//div[@class='statement-table']/span[1]" ) );
		valor = lancamentoInlineElement.findElement( By
				.xpath( "//td[contains(@class, 'right value-column')]" ) );
		pago = valor.findElement( By.tagName( "i" ) );
	}

	public String getNome() {
		try {
			return nome.getText();
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu obter valor do campo. InLineFinantialStatementPage foi inicializado?" );
			return "";
		}
	}

	public String getValor() {
		try {
			return valor.getText();
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu obter valor do campo. InLineFinantialStatementPage foi inicializado?" );
			return "";
		}
	}

	public String getDataDeVencimento() {
		try {
			return dataVencimento.getText();
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu obter valor do campo. InLineFinantialStatementPage foi inicializado?" );
			return "";
		}
	}

	public String getRecorrencia() {
		try {
			return recorrencia.getText();
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu obter valor do campo. InLineFinantialStatementPage foi inicializado?" );
			return "";
		}
	}

	public String getCliente() {
		try {
			return cliente.getText();
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu obter valor do campo. InLineFinantialStatementPage foi inicializado?" );
			return "";
		}
	}

	public String getSituacao() {
		try {
			return pago.isDisplayed() ? "pago" : "não pago";
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu obter valor do campo. InLineFinantialStatementPage foi inicializado?" );
			return "";
		}
	}

	public String getCategoria() {
		try {
			return categoria.getText();
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu obter valor do campo. InLineFinantialStatementPage foi inicializado?" );
			return "";
		}
	}

	public void clicarLancamento() {
		this.nome.click();
	}

}
