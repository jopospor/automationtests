package com.contaazul.auto.selenium.financeiro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class BankAccountsPage extends WebPage {

	@FindBy(id = "btnNewBankAccount")
	protected WebElement botaoNovaConta;
	@FindBy(id = "search_banco")
	protected WebElement campoPesquisa;
	@FindBy(id = "searchButton_banco")
	protected WebElement botaoPesquisar;
	@FindBy(id = "openBankSlipPopup")
	protected WebElement configureBankslip;
	@FindBy(linkText = "Excluir")
	protected WebElement btnExcluir;
	@FindBy(className = "container-message")
	protected WebElement popUp;

	public BankAccountsPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void clicarNovaConta() {
		sleep( VERY_QUICKLY );
		botaoNovaConta.click();
	}

	public void pesquisar(String nome) {
		sleep( FOR_A_LONG_TIME );
		waitForElementEnabled( By.id( "search_banco" ) );
		campoPesquisa = driver.findElement( By.id( "search_banco" ) );
		campoPesquisa.clear();
		campoPesquisa.sendKeys( nome );
		botaoPesquisar = driver.findElement( By.id( "searchButton_banco" ) );
		botaoPesquisar.click();
	}

	public WebElement getContaInlineElement(String accountName) {
		return driver.findElement( By.xpath( "//tr[td[contains(text(), '"
				+ accountName + "')]]" ) );
	}

	public void clickCheckbox(String accountName) {
		sleep( VERY_LONG );
		waitForElementPresent(
				By.xpath( "//tr[td[contains(text(), '" + accountName
						+ "')]]/td[1]/span" ), 30 );
		driver.findElement(
				By.xpath( "//tr[td[contains(text(), '" + accountName
						+ "')]]/td[1]/span" ) ).click();
	}

	public void clicConfigureBankslip() {
		configureBankslip.click();
		waitForElementPresent( By
				.xpath( "//h3[contains(text(), 'Configure conta bancária para emitir boleto')]" ) );
	}

	public String getNomeBanco(String accountName) {
		By by = By.xpath( "//tr[td[contains(text(), '" + accountName + "')]]/td[2]" );
		return waitForElementNotStale( by ).getText();
	}

	public String getBankslipConfigured(String accountName) {
		return driver.findElement(
				By.xpath( "//tr[td[contains(text(), '" + accountName
						+ "')]]/td[4]" ) ).getText();
	}

	public void clickBankAccount(String accountName) {
		driver.findElement(
				By.xpath( "//td[contains(text(), '" + accountName + "')]" ) )
				.click();
	}

	public void excluir() {
		btnExcluir.click();
		sleep( VERY_QUICKLY );
		driver.switchTo().alert().accept();
	}

	public boolean isAccountListed(String accountName) {
		try {
			driver.findElement( By.xpath( "//td[contains(text(), '" + accountName + "')]" ) );
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void selecAllCheckBoxAccounts() {
		driver.findElementByXPath( "//table[@id='rowBanco']/thead/tr/th/span" ).click();
	}

	public boolean hasItemsListed() {
		return getAssistants().getDeleteAssistant( driver ).hasBanksListed();
	}
}
