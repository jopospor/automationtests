package com.contaazul.auto.selenium.financeiro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.WebPage;

public class ConfigureBankslipPage extends WebPage {

	@FindBy(id = "bankAccountName")
	protected WebElement bankAccountName;
	@FindBy(id = "listaBancos")
	protected WebElement listaBancos; // Select
	@FindBy(id = "nrAgencia")
	protected WebElement nrAgencia;
	@FindBy(id = "nrConta")
	protected WebElement nrConta;
	@FindBy(id = "carteiras")
	protected WebElement carteiras; // Select
	@FindBy(id = "varCarteira")
	protected WebElement varCarteira;
	@FindBy(id = "dsInstrucao1")
	protected WebElement dsInstrucao1;
	@FindBy(id = "dsInstrucao2")
	protected WebElement dsInstrucao2;
	@FindBy(id = "dsInstrucao3")
	protected WebElement dsInstrucao3;
	@FindBy(id = "dsInstrucao4")
	protected WebElement dsInstrucao4;
	@FindBy(id = "dsLocalPagamento1")
	protected WebElement dsLocalPagamento1;
	@FindBy(id = "dsLocalPagamento2")
	protected WebElement dsLocalPagamento2;
	@FindBy(className = "jquery-checkbox")
	protected WebElement autorizacaoGerente;
	@FindBy(xpath = "//button[contains(text(), 'Salvar')]")
	protected WebElement save;
	@FindBy(id = "vlBoleto")
	protected WebElement valorDoBoleto;
	@FindBy(id = "nrConvenio")
	protected WebElement numeroConvenio;
	@FindBy(id = "codCliente")
	protected WebElement codCliente;
	@FindBy(id = "nrModalidade")
	protected WebElement modalidade;
	@FindBy(id = "dsCategory")
	protected WebElement categoria;
	@FindBy(id = "popupNotify")
	protected WebElement mensagemValidacao;
	@FindBy(id = "nrCarteira")
	protected WebElement nrCarteira;
	@FindBy(xpath = "//*[@id='newPopupManagerReplacement']/div[4]/div/button")
	protected WebElement btnCancelConfigure;

	public ConfigureBankslipPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void clickCancelConfigure() {
		btnCancelConfigure.click();
	}

	public void setNameBanco(String bankAccountName) {
		if (this.bankAccountName.isEnabled()) {
			this.bankAccountName.clear();
			this.bankAccountName.sendKeys( bankAccountName );
		}
	}

	public String getNameBanco() {
		Select lis = new Select( this.listaBancos );
		return lis.getFirstSelectedOption().getText();
	}

	public void setListaBancos(String listaBancos) {
		Select lis = new Select( this.listaBancos );
		lis.selectByVisibleText( listaBancos );
	}

	public String getNrAgencia() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('nrAgencia').value" );
	}

	public void setNrAgencia(String nrAgencia) {
		this.nrAgencia.clear();
		this.nrAgencia.sendKeys( nrAgencia );
	}

	public String getNrConta() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('nrConta').value" );
	}

	public void setNrConta(String nrConta) {
		this.nrConta.clear();
		this.nrConta.sendKeys( nrConta );
	}

	public String getCarteiras() {
		if (this.carteiras.isDisplayed()) {
			Select car = new Select( carteiras );
			return car.getFirstSelectedOption().getText();
		} else {
			String field = (String) javascript( "return document.getElementById('nrCarteira').value" );
			String select = (String) javascript( "return document.getElementById('carteiras').value" );
			return (field.isEmpty()) ? select : field;
		}
	}

	public void setCarteiras(String carteiras) {
		if (!carteiras.isEmpty()) {
			if (this.nrCarteira.isDisplayed()) {
				this.nrCarteira.clear();
				this.nrCarteira.sendKeys( carteiras );
			} else
				javascript( "document.getElementById('nrCarteira').value='" + carteiras + "'" );
		}
	}

	public void selectCarteiras(String carteiras) {
		javascript( "document.getElementById('divNrCarteiraSelect').setAttribute('hidden', 'false')" );
		if (!carteiras.isEmpty()) {
			if (this.carteiras.isDisplayed()) {
				Select car = new Select( this.carteiras );
				car.selectByVisibleText( carteiras );
			} else
				javascript( "document.getElementById('carteiras').value='" + carteiras + "'" );
		}
	}

	public String getVarCarteira() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('varCarteira').value" );
	}

	public void setVarCarteira(String varCarteira) {
		if (!varCarteira.isEmpty()) {
			if (this.varCarteira.isDisplayed()) {
				this.varCarteira.clear();
				this.varCarteira.sendKeys( varCarteira );
			} else {
				JavascriptExecutor j = (JavascriptExecutor) driver;
				j.executeScript( "document.getElementById('varCarteira').value='" + varCarteira + "'" );
			}
		}
	}

	public String getDsInstrucao1() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('dsInstrucao1').value" );
	}

	public void setDsInstrucao1(String dsInstrucao1) {
		this.dsInstrucao1.clear();
		this.dsInstrucao1.sendKeys( dsInstrucao1 );
	}

	public String getDsInstrucao2() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('dsInstrucao2').value" );
	}

	public void setDsInstrucao2(String dsInstrucao2) {
		this.dsInstrucao2.clear();
		this.dsInstrucao2.sendKeys( dsInstrucao2 );
	}

	public String getDsInstrucao3() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('dsInstrucao3').value" );
	}

	public void setDsInstrucao3(String dsInstrucao3) {
		this.dsInstrucao3.clear();
		this.dsInstrucao3.sendKeys( dsInstrucao3 );
	}

	public String getDsInstrucao4() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('dsInstrucao4').value" );
	}

	public void setDsInstrucao4(String dsInstrucao4) {
		this.dsInstrucao4.clear();
		this.dsInstrucao4.sendKeys( dsInstrucao4 );
	}

	public String getDsLocalPagamento1() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('dsLocalPagamento1').value" );
	}

	public void setDsLocalPagamento1(String dsLocalPagamento1) {
		this.dsLocalPagamento1.clear();
		this.dsLocalPagamento1.sendKeys( dsLocalPagamento1 );
	}

	public String getCheckboxGerente() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('dsLocalPagamento2').value" );
	}

	public void submitForm() {

		(new WebDriverWait( driver, Integer.parseInt( SeleniumSession.getSession().getProperties()
				.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) ))
				.until( new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						return driver.findElement( By.xpath( "//button[contains(text(), 'Salvar')]" ) ).isDisplayed();
					}
				} );

		save.click();
	}

	public void setValorDoBoleto(String valorDoBoleto) {
		this.valorDoBoleto.clear();
		this.valorDoBoleto.sendKeys( valorDoBoleto );
	}

	public void setDsLocalPagamento2(String dsLocalPagamento2) {
		this.dsLocalPagamento2.clear();
		this.dsLocalPagamento2.sendKeys( dsLocalPagamento2 );

	}

	public void setAutorizacaoGerente(boolean isSelected) {
		if (this.autorizacaoGerente.isSelected() != isSelected)
			this.autorizacaoGerente.click();
	}

	public void setNumeroConvenio(String nrConvenio) {
		if (!nrConvenio.isEmpty()) {
			if (this.numeroConvenio.isDisplayed()) {
				this.numeroConvenio.clear();
				this.numeroConvenio.sendKeys( nrConvenio );
			} else {
				JavascriptExecutor j = (JavascriptExecutor) driver;
				j.executeScript( "document.getElementById('nrConvenio').value='" + nrConvenio + "'" );
			}
		}
	}

	public void setCodCliente(String codCliente) {
		if (!codCliente.isEmpty()) {
			if (this.codCliente.isDisplayed()) {
				this.codCliente.clear();
				this.codCliente.sendKeys( codCliente );
			} else {
				JavascriptExecutor j = (JavascriptExecutor) driver;
				j.executeScript( "document.getElementById('codCliente').value='" + codCliente + "'" );
			}
		}
	}

	public void setModalidade(String modalidade) {
		if (!modalidade.isEmpty()) {
			if (this.modalidade.isDisplayed()) {
				this.modalidade.clear();
				this.modalidade.sendKeys( modalidade );
			} else {
				JavascriptExecutor j = (JavascriptExecutor) driver;
				j.executeScript( "document.getElementById('nrModalidade').value='" + modalidade + "'" );
			}
		}
	}

	public void setCategoria(String categoria) {
		if (!categoria.isEmpty()) {
			this.categoria = driver.findElement( By.id( "dsCategory" ) );
			this.categoria.clear();
			this.categoria.sendKeys( categoria );
		}
	}

	public String getValorDoBoleto() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('vlBoleto').value" );
	}

	public String getDsLocalPagamento2() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('dsLocalPagamento2').value" );
	}

	public boolean getAutorizacaoGerente() {
		return autorizacaoGerente.isSelected();
	}

	public String getNumeroConvenio() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('nrConvenio').value" );
	}

	public String getCodCliente() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('codCliente').value" );
	}

	public String getModalidade() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('nrModalidade').value" );
	}

	public String getCategoria() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('dsCategory').value" );
	}

	public String getMensagemDeValidacao() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		return (String) j.executeScript( "return document.getElementById('popupNotify').value" );
	}

	public void btnCancelConfigureBank() {
		driver.findElementByXPath( "//*[@id='newPopupManagerReplacement']/div[4]/div/button" ).click();
	}

}
