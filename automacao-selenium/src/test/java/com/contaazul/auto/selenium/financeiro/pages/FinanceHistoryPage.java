package com.contaazul.auto.selenium.financeiro.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class FinanceHistoryPage extends WebPage {

	public FinanceHistoryPage(WebDriver driver) {
		super( driver );
	}

	@FindBy(id = "textSearch")
	protected WebElement searchList;
	@FindBy(xpath = "//div[@id='newPopupManagerReplacement']/div[4]/div/div/button")
	protected WebElement saveRevenue;
	@FindBy(id = "valor")
	protected WebElement comparvalue;
	@FindBy(id = "newIdConta")
	protected WebElement comparAccount;
	@FindBy(id = "notification")
	protected WebElement msgValidation;
	@FindBy(id = "dtVencimento")
	protected WebElement comparDate;
	@FindBy(id = " bankAccountNameOrigin")
	protected WebElement accountBankOrigin;
	@FindBy(id = "bankAccountNameDestiny")
	protected WebElement accountBankDestiny;
	@FindBy(id = "transferedValue")
	protected WebElement valueTranfer;
	@FindBy(id = "financeiro_memo")
	protected WebElement description;
	@FindBy(id = "transferedDate")
	protected WebElement tranferDate;
	@FindBy(xpath = "//*[@id='conteudo']/div/div[2]/div[1]/a[6]/span/span")
	protected WebElement btnHistoric;
	@FindBy(xpath = "//a[contains(text(),'Ver receita')]")
	protected WebElement seeRevenue;
	@FindBy(xpath = "//input[@id='financeiro_memo']")
	protected WebElement editName;
	@FindBy(xpath = "//a[contains(text(),'Ver despesa')]")
	protected WebElement seeExpence;
	@FindBy(xpath = "//a[contains(text(),'Ver transferência')]")
	protected WebElement seeTranfer;
	@FindBy(id = "idCategoria")
	protected WebElement category;
	@FindBy(xpath = "//*[@id='formTransfer']/ul/li[1]/button")
	protected WebElement saveTransfer;

	public void btnSaveTransfer() {
		saveTransfer.click();
	}

	public void searchList(String transferencia) {
		searchList.sendKeys( transferencia );
		searchList.sendKeys( Keys.RETURN );
		sleep( VERY_LONG );
	}

	public void save() {
		saveRevenue.click();
		sleep( FOR_A_LONG_TIME );
	}

	public String getCompareValue() {
		return (String) javascript( "return document.getElementById('valor').value" );
	}

	public String getCompareAccount() {
		return (String) javascript( "return document.getElementById('newIdConta').value" );
	}

	public String getCompareDate() {
		return (String) javascript( "return document.getElementById('dtVencimento').value" );
	}

	public String getAccountBankOrigin() {
		return (String) javascript( "return document.getElementById('bankAccountNameOrigin').value" );
	}

	public String getAccountBankDestiny() {
		return (String) javascript( "return document.getElementById('bankAccountNameDestiny').value" );
	}

	public String getValeuTransfer() {
		return (String) javascript( "return document.getElementById('financeiro_memo').value" );
	}

	public String getTransferedDate() {
		return (String) javascript( "return document.getElementById('transferedDate').value" );
	}

	public String getCategory() {
		return (String) javascript( "return document.getElementById('idCategoria').value" );
	}

	public String getDescriptionTransfer() {
		return (String) javascript( "return document.getElementById('financeiro_memo').value" );
	}

	public String getValueTransfer() {
		sleep( VERY_LONG );
		return (String) javascript( "return document.getElementById('transferedValue').value" );
	}

	public void btnHistoric() {
		btnHistoric.click();
	}

	public void seeRevenue() {
		sleep( VERY_LONG );
		seeRevenue.click();
		sleep( VERY_LONG );
	}

	public String getName() {
		return (String) javascript( "return document.getElementById('financeiro_memo').value" );
	}

	public void cleanSearch() {
		searchList.clear();
		searchList.sendKeys( Keys.ENTER );
	}

	public void seeExpense() {
		seeExpence.click();
		sleep( VERY_LONG );
	}

	public void seeTransfer() {
		seeTranfer.click();
		sleep( VERY_LONG );
	}
}
