package com.contaazul.auto.selenium.financeiro.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.contaazul.auto.selenium.WebPage;

public class CreateBankAccountPage extends WebPage {
	@FindBy(id = "saldoInicial")
	protected WebElement campoSaldoInicial;
	@FindBy(id = "dataSaldoInicial")
	protected WebElement campoDataSaldoInicial;
	@FindBy(id = "nomeDoBanco")
	protected WebElement campoNomeDaConta;
	@FindBy(id = "listaBancos")
	protected WebElement selectBancos;
	@FindBy(id = "saveBank")
	protected WebElement botaoSalvar;
	@FindBy(id = "notification")
	protected WebElement caixaDeMensagens;
	@FindBy(xpath = "//*[@id='alertDialog']/div[1]")
	protected WebElement msgaAviso;
	@FindBy(xpath = "//button[contains(text(),'Ok')]")
	protected WebElement btnMsgAvisoOk;
	@FindBy(xpath = "//div[@id='formBanco_']/div[4]/a")
	WebElement voltaTelaInicial;
	@FindBy(xpath = "//tr[@id='rowBanco_row0']/td[3]")
	protected WebElement alterarConta;
	@FindBy(xpath = "//div[contains(@class, 'contaazul-components-dialog Dialog')]")
	private WebElement alertDialogue;
	@FindBy(id = "nomeAgencia")
	protected WebElement numberAgency;
	@FindBy(id = "contaCorrente")
	protected WebElement numberAccount;
	@FindBy(id = "digitoVerificador")
	protected WebElement digit;
	@FindBy(id = "automaticOfxSwitcherOff")
	protected WebElement importAutomaticOff;

	public void withoutAutomaticImport() {
		importAutomaticOff.click();
	}

	public boolean hasItemsImport() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( 2000, TimeUnit.MILLISECONDS )
				.pollingEvery( 500, TimeUnit.MILLISECONDS );
		try {
			wait.until( new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver webDriver) {
					try {
						return webDriver.findElement( By.id( "automaticOfxSwitcherOff" ) ).getText()
								.contains( "Sem importação automática de extrato" );
					} catch (Exception e) {
						return false;
					}
				}
			} );
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	// ATDD
	@FindBy(id = "bankCustomerCpf")
	protected WebElement cpf;
	@FindBy(id = "nomeAgencia")
	protected WebElement bankAgency;
	@FindBy(id = "contaCorrente")
	protected WebElement accountNumber;
	@FindBy(id = "digitoVerificador")
	protected WebElement accountDigit;
	@FindBy(id = "formBanco_bankConfiguration_password")
	protected WebElement accountPassword;
	@FindBy(id = "bankPassConfirm")
	protected WebElement confirmAccountPassword;
	@FindBy(id = "automaticOfxSwitcherOn")
	protected WebElement configureSynch;
	@FindBy(id = "automaticOfxSwitcherOff")
	protected WebElement doNotConfigureSynch;
	@FindBy(id = "saveBank")
	protected WebElement btnsave;
	@FindBy(id = "automaticOfxEditBtn")
	protected WebElement buttonEditConfigureAutomatic;

	public void accountNotImportAutomatic() {
		waitForElementNotPresent( By.id( "bankTitle" ) );
	}

	public void doNotConfigure() {
		doNotConfigureSynch.click();
	}

	public void configureSynchOn() {
		configureSynch.click();
	}

	public void save() {
		btnsave.click();
	}

	public void editConfigureAutomatic() {
		buttonEditConfigureAutomatic.click();
	}

	public void password() {
		accountPassword.click();
	}

	public void confirmPassword() {
		confirmAccountPassword.click();
	}

	public void cpfUser(String numberCpf) {
		cpf.clear();
		cpf.sendKeys( numberCpf );
	}

	public void verificationDigit(String digitoV) {
		digit.sendKeys( digitoV );
	}

	public void numberAccount(String numberB) {
		numberAccount.sendKeys( numberB );
	}

	public void setNumberAgency(String numberA) {
		numberAgency.sendKeys( numberA );
	}

	public void clickOnConfigureSynchButton() {
		configureSynch.click();
	}

	public void inputAccountSynchInformation(String agency, String account, String digit) {
		accountNumber.sendKeys( account );
		bankAgency.sendKeys( agency );
		accountDigit.sendKeys( digit );
	}

	public void setPassword(String pass) {
		for (int i = 0; i < pass.length(); i++)
			new VirtualKeyboardPage( driver ).click( String.valueOf( pass.charAt( i ) ) );
	}

	public void setSaldoInicial(String valor) {
		campoSaldoInicial.clear();
		campoSaldoInicial.sendKeys( valor );
	}

	public void setDataSaldoInicial(String data) {
		campoDataSaldoInicial.clear();
		campoDataSaldoInicial.sendKeys( data );
	}

	public void setBanco(String banco) {
		waitForElementNotStale( By.id( "listaBancos" ) );
		new Select( selectBancos ).selectByVisibleText( banco );
	}

	public void setNomeDaConta(String nome) {
		campoNomeDaConta.clear();
		campoNomeDaConta.sendKeys( nome );
	}

	public void clicarSalvar() {
		botaoSalvar.click();
		sleep( VERY_LONG );
	}

	public String getMensagemValidacao() {
		if (caixaDeMensagens.isDisplayed()) {
			return caixaDeMensagens.getText();
		}
		return null;
	}

	public String getNomeBanco() {
		return new Select( selectBancos ).getFirstSelectedOption().getText();
	}

	public String getNomeConta() {
		return campoNomeDaConta.getAttribute( "value" );
	}

	public String getSaldoInicial() {
		return campoSaldoInicial.getAttribute( "value" );
	}

	public String getDataInicial() {
		return campoDataSaldoInicial.getAttribute( "value" );
	}

	public void clearDataSaldoInicial() {
		campoDataSaldoInicial.clear();
	}

	public String msgAviso() {
		return driver.findElementByXPath( "//*[@id='alertDialog']/div[1]" ).getText();
	}

	public void btnMsgAvisoOk() {
		btnMsgAvisoOk.click();
	}

	public void clearNomeDaConta() {
		campoNomeDaConta.clear();
	}

	public void setSaldoInicial2(String valor) {
		campoDataSaldoInicial.sendKeys( valor );
	}

	public void telaInicial() {
		voltaTelaInicial.click();
	}

	public String notification() {
		return driver.findElementById( "notification" ).getText();
	}

	public void telaAlterar() {
		alterarConta.click();
	}

	public String getMensagemValidacaoPopUp() {
		try {
			return alertDialogue.getText();
		} catch (ElementNotVisibleException e) {
			return "";
		}
	}

	public CreateBankAccountPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

}
