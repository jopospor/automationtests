package com.contaazul.auto.selenium.dashboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class DashboardPage extends WebPage {

	@FindBy(xpath = "//div[@id='boxBankAccountActionBtns_0']/button")
	protected WebElement dropDownImportExtract;
	@FindBy(id = "boxBankAccountEditAccountLink_0")
	protected WebElement editAccount;
	@FindBy(id = "boxBankAccountVerifyStatementsLink_0")
	protected WebElement seeTransfer;
	@FindBy(id = "boxBankAccountImportStatementBtn_0")
	protected WebElement importExtract;
	@FindBy(id = "financeDashboardAddRevenue")
	protected WebElement btnAddRevenue;
	@FindBy(id = "financeDashboardAddExpense")
	protected WebElement btnAddExpense;
	@FindBy(id = "boxBankAccountAddAccountBtn")
	protected WebElement btnAddBankAccount;
	@FindBy(id = "boxBankAccountAutomateImportStatementSubFooterBtn_0")
	protected WebElement btnConfigureExtractFooter;
	@FindBy(id = "boxBankAccountAutomateImportStatementBlankslateBtn_0")
	protected WebElement btnConfigureExtractAutomaticDash;
	@FindBy(id = "boxBankAccountAddAccountBtn")
	protected WebElement btnAddAccountBankDashboard;
	@FindBy(id = "boxBankAccountImportStatementManually_1")
	protected WebElement importManualDropDownDash;
	@FindBy(id = "boxBankAccountImportStatementBtn_0")
	protected WebElement atualizarExtract;
	@FindBy(id = "pendingTransactionLink_0")
	protected WebElement btnConciliateDashboard;
	@FindBy(id = "boxBankAccountManualImportStatementBlankslateLink_0")
	protected WebElement importManual;
	@FindBy(id = "back_to_main_inoutcomes")
	protected WebElement returnGraphic;

	public void clickLinkReturnToGraphic() {
		returnGraphic.click();
	}

	public void btnConciliate() {
		btnConciliateDashboard.click();
		sleep( FOR_A_LONG_TIME );
	}

	public void atualizarExtract() {
		atualizarExtract.click();
		sleep( VERY_QUICKLY );
	}

	public void importExtractManualDrop() {
		importManualDropDownDash.click();
	}

	public void btnAddAccount() {
		btnAddAccountBankDashboard.click();
	}

	public void btnConfigureExtractAutomatic() {
		btnConfigureExtractAutomaticDash.click();
		sleep( VERY_LONG );
	}

	public void btnConfigureFooter() {
		btnConfigureExtractFooter.click();
		sleep( VERY_LONG );
	}

	public void importExtractManual() {
		importManual.click();
	}

	public void buttonAddBankAccount() {
		btnAddBankAccount.click();

	}

	public void buttonAddRevenue() {
		btnAddRevenue.click();
		sleep( VERY_LONG );
	}

	public void buttonAddExpense() {
		btnAddExpense.click();
		sleep( VERY_LONG );
	}

	public void clickImportExtract() {
		sleep( VERY_LONG );
		if (isElementPresent( By.id( "boxBankAccountImportStatementBlankslateBtn_0" ) ))
			driver.findElementById( "boxBankAccountImportStatementBlankslateBtn_0" ).click();
		else
			importExtract.click();
	}

	public void clickSeeTranfer() {
		seeTransfer.click();
	}

	public void clickEditAccount() {
		editAccount.click();
	}

	public void openDropDownImportExtract() {
		dropDownImportExtract.click();
	}

	public String msgErrorImportExtract() {
		return driver.findElementById( "boxBankAccountNotificationText_1" ).getText();
	}

	public String msgSuscessCreateAccount() {
		return driver.findElementByXPath( "//*[@id='continuousActionsTitle']" ).getText();
	}

	public DashboardPage(WebDriver driver) {
		super( driver );
	}

}
