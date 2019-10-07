package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class BillingFormPage extends WebPage {

	@FindBy(id = "CNPJ")
	protected WebElement CNPJ;
	@FindBy(id = "CPF")
	protected WebElement CPF;
	@FindBy(id = "documentNumber")
	protected WebElement documentNumber;
	@FindBy(id = "name")
	protected WebElement name;
	@FindBy(id = "cep")
	protected WebElement cep;
	@FindBy(id = "address")
	protected WebElement address;
	@FindBy(id = "number")
	protected WebElement number;
	@FindBy(id = "complement")
	protected WebElement complement;
	@FindBy(id = "neighborhood")
	protected WebElement neighborhood;
	@FindBy(id = "nameCity")
	protected WebElement nameCity;
	@FindBy(id = "phoneNumber")
	protected WebElement phoneNumber;
	@FindBy(id = "email")
	protected WebElement email;
	@FindBy(xpath = "//button[contains(text(), 'Enviar Dados')]")
	protected WebElement enviarDados;
	@FindBy(className = "container-message")
	protected WebElement mensagens;

	public BillingFormPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void setTipoDocumento(String tipoDocumento) {
		if (tipoDocumento.toLowerCase().matches( "cnpj" ))
			CNPJ.click();
		else if (tipoDocumento.toLowerCase().matches( "cpf" ))
			CPF.click();
	}

	public void setDocumentNumber(String number) {
		String script = "document.getElementById('documentNumber').value=''";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( script );
		documentNumber.clear();
		documentNumber.sendKeys( number );
	}

	public void setName(String name) {
		String script = "document.getElementById('name').value=''";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( script );
		this.name.clear();
		this.name.sendKeys( name );
	}

	public void setCEP(String CEP) {
		String script = "document.getElementById('cep').value=''";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( script );
		this.cep.clear();
		this.cep.sendKeys( CEP );
	}

	public void setAddress(String address) {
		String script = "document.getElementById('address').value=''";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( script );
		this.address.clear();
		this.address.sendKeys( address );
	}

	public void setNumber(String number) {
		String script = "document.getElementById('number').value=''";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( script );
		this.number.clear();
		this.number.sendKeys( number );
	}

	public void setComplement(String complement) {
		String script = "document.getElementById('complement').value=''";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( script );
		this.complement.clear();
		this.complement.sendKeys( complement );
	}

	public void setNeighborhood(String neighborhood) {
		String script = "document.getElementById('neighborhood').value=''";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( script );
		this.neighborhood.clear();
		this.neighborhood.sendKeys( neighborhood );
	}

	public void setNameCity(String nameCity) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( this.nameCity, nameCity );
	}

	public void setPhoneNumber(String phoneNumber) {
		String script = "document.getElementById('phoneNumber').value=''";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( script );
		this.phoneNumber.clear();
		this.phoneNumber.sendKeys( phoneNumber );
	}

	public void setEmail(String email) {
		String script = "document.getElementById('email').value=''";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( script );
		waitForElementPresent( By.id( "email" ) );
		this.email.clear();
		this.email.sendKeys( email );
	}

	public void clicarEnviarDados() {
		waitForElementPresent( By.xpath( "//button[contains(text(), 'Enviar Dados')]" ) );
		enviarDados.click();
	}

	public String getMensagemValidacao() {
		return this.mensagens.getText();
	}

	public String getTipoDocumento() {
		return CPF.isSelected() ? "CPF" : CNPJ.isSelected() ? "CNPJ" : "";
	}

	public String getDocumentNumber() {
		String script = "return document.getElementById('documentNumber').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

	public String getName() {
		String script = "return document.getElementById('name').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

	public String getCEP() {
		String script = "return document.getElementById('cep').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

	public String getAddress() {
		String script = "return document.getElementById('address').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

	public String getNumber() {
		String script = "return document.getElementById('number').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

	public String getComplement() {
		String script = "return document.getElementById('complement').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

	public String getNeighborhood() {
		String script = "return document.getElementById('neighborhood').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

	public String getNameCity() {
		String script = "return document.getElementById('nameCity').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

	public String getPhoneNumber() {
		String script = "return document.getElementById('phoneNumber').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

	public String getEmail() {
		String script = "return document.getElementById('email').value";
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( script );
	}

}
