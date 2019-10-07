package com.contaazul.auto.selenium.dashboard.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class DashboardBankAccountPage extends WebPage {

	@FindBy(id = "nomeDoBanco")
	protected WebElement nameAccountBankDash;
	@FindBy(id = "nomeAgencia")
	protected WebElement agency;
	@FindBy(id = "contaCorrente")
	protected WebElement bankAccount;
	@FindBy(id = "digitoVerificador")
	protected WebElement digit;
	@FindBy(id = "saldoInicial")
	protected WebElement openingBalance;
	@FindBy(id = "dataSaldoInicial")
	protected WebElement date;
	@FindBy(id = "saveBank")
	protected WebElement save;
	@FindBy(id = "listaBancos")
	protected WebElement selectBank;
	@FindBy(id = "bankAccountNumber_0")
	private WebElement accountNumberField;

	public void selectBank(String banco) {
		sleep( FOR_A_LONG_TIME );
		selectBank.sendKeys( banco );
	}

	public void btnSave() {
		save.click();
	}

	public void setDate(String data) {
		date.clear();
		date.sendKeys( data );
	}

	public void setOpeningBalance(String saldoInicial) {
		openingBalance.clear();
		openingBalance.sendKeys( saldoInicial );
		sleep( FOR_A_LONG_TIME );
	}

	public void setDigit(String digito) {
		digit.clear();
		digit.sendKeys( digito );
	}

	public void setBankAccount(String account) {
		bankAccount.clear();
		bankAccount.sendKeys( account );
	}

	public void setNameAgency(String nameAgency) {
		agency.clear();
		agency.sendKeys( nameAgency );
	}

	public void setNameBankAccountDash(String name) {
		nameAccountBankDash.clear();
		nameAccountBankDash.sendKeys( name );
	}

	public DashboardBankAccountPage(WebDriver driver) {
		super( driver );

	}

	public boolean isAccountNumberDisplayed(String accountNumber) {
		return accountNumberField.getText().contains( accountNumber );

	}

	public String getAccountNumber() {
		return accountNumberField.getText();
	}

}
