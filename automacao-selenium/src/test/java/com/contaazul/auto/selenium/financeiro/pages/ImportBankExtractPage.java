package com.contaazul.auto.selenium.financeiro.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.contaazul.auto.selenium.WebPage;

public class ImportBankExtractPage extends WebPage {

	public ImportBankExtractPage(WebDriver driver) {
		super( driver );
	}

	@FindBy(id = "bankAccountNameOrigin")
	private WebElement bankOrigin;
	@FindBy(id = "bankAccountNameDestiny")
	private WebElement bankDestiny;
	@FindBy(xpath = "//button[contains(text(),'Continuar')]")
	private WebElement btnContinued;
	@FindBy(id = "file")
	private WebElement upFile;
	@FindBy(xpath = "//div[@id='addFinance']/button ")
	private WebElement btnExtratc;
	@FindBy(id = "newIdConta")
	private WebElement typeAccount;
	@FindBy(xpath = "//*[@id='importSelector']/span/span/img")
	private WebElement selectAllCheckBoxElement;
	@FindBy(xpath = "//div[@id='newPopupManagerReplacement']/div[2]/div[2]/div/button")
	private WebElement launchConcileButton;
	@FindBy(xpath = "//button[contains(text(),'Ações')]")
	private WebElement btnAction;
	@FindBy(id = "addAsTransfer")
	private WebElement btnTransf;
	@FindBy(linkText = "Adicionar como transferências")
	private WebElement btnAddTransfer;
	@FindBy(linkText = "Excluir Agora")
	protected WebElement btnDeleteNow;
	@FindBy(id = "financeiro_memo")
	private WebElement description;
	@FindBy(id = "transferedValue")
	private WebElement valueTransfer;
	@FindBy(className = "close")
	private WebElement btnCancel;
	@FindBy(className = "close-button")
	private WebElement btnCloseTransfer;
	@FindBy(xpath = "//*[@id='formTransfer']/ul/li[1]/button")
	private WebElement btnSaveTransfer;
	@FindBy(id = "transferedDate")
	private WebElement dateTransfer;
	@FindBy(xpath = "//a[contains(@class,'small-glyphicons statement-icon-action  glyphicons search')]")
	private WebElement clickConciliate;
	@FindBy(xpath = "//*[@id='statement-list-container']/table[3]/tbody/tr[1]/td[3]/div/div[3]/a")
	private WebElement concilieMenuRollover;

	public void importExtractOfx(String arquivo) {
		driver.switchTo().frame( "idUpload" );
		try {
			getAssistants().getFileUploadAssistant( driver ).remoteUploadFile( upFile,
					"//src//test//resources//file_attachments//" + arquivo );
		} catch (IOException e) {
			Assert.fail( "AVISO: A importação do arquivo " + arquivo
					+ " falhou.", e );
		}
		sleep( VERY_LONG );
		driver.switchTo().defaultContent();
		sleep( VERY_LONG );
		btnContinued.click();
	}

	public void createBankOrigin(String accountOriginCredit) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndCreateNew( bankOrigin, accountOriginCredit );
	}

	public void createBankDestiny(String accountOriginDestiny) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndCreateNew( bankDestiny, accountOriginDestiny );
	}

	public void selectBankAccount(String conta) {
		getAssistants().getAutoCompleteAssistant( driver )
				.sendKeysAndSelectExisting( typeAccount, conta );
	}

	public void btnNewExtractOfx() {
		sleep( VERY_LONG );
		btnExtratc.click();
	}

	public void newtAccountBank(String conta) {
		getAssistants().getAutoCompleteAssistant( driver )
				.sendKeysAndCreateNew( typeAccount, conta );
	}

	public void rolloverAndClickMenuConciliation() {
		rolloverMenuConciliation();
		sleep( VERY_LONG );
		concilieMenuRollover.click();
	}

	public String getEntry(Integer lineNumber) {
		sleep( VERY_LONG );
		return driver.findElementByXPath(
				"//*[@id='statement-list-container']/table[3]/tbody/tr[" + lineNumber + "]/td[3]/span" ).getText();
	}

	public String getDate(Integer lineNumber) {
		sleep( VERY_LONG );
		return driver.findElementByXPath(
				"//*[@id='statement-list-container']/table[3]/tbody/tr[" + lineNumber + "]/td[2]" ).getText();
	}

	public String getValue(Integer lineNumber) {
		sleep( VERY_LONG );
		sleep( VERY_LONG );
		return driver.findElementByXPath(
				"//*[@id='statement-list-container']/table[3]/tbody/tr[" + lineNumber + "]/td[4]" ).getText();
	}

	public void clickAllCheckBox() {
		selectAllCheckBoxElement.click();
	}

	public void clickLaunchConcileButton() {
		launchConcileButton.click();
	}

	public void menuSendToTransfer() {
		clickAllCheckBox();
		btnAction.click();
		btnAddTransfer.click();
	}

	public void setValueTransfer(String valueTransferP) {
		valueTransfer.clear();
		valueTransfer.sendKeys( valueTransferP );
	}

	public void setDescriptionTransfer(String descriptionP) {
		description.clear();
		description.sendKeys( descriptionP );
	}

	public void selectAction(String action) {
		btnAction.click();

		if (action.equals( "Adicionar como lançamentos" )) {
			driver.findElementById( "selectedActions" )
					.findElement( By.linkText( "Adicionar como lançamentos" ) )
					.click();
		}
		if (action.equals( "Adicionar como transferências" )) {
			driver.findElementById( "selectedActions" )
					.findElement( By.linkText( "Adicionar como transferências" ) )
					.click();
		}
		if (action.equals( "Excluir" )) {
			driver.findElementById( "selectedActions" )
					.findElement( By.linkText( "Excluir" ) ).click();
			btnDeleteNow.click();
		}
	}

	public void addValueTransfer(String valueTransferP) {
		valueTransfer.clear();
		valueTransfer.sendKeys( valueTransferP );
	}

	public void addDescriptionTransfer(String descriptionP) {
		description.clear();
		description.sendKeys( descriptionP );
	}

	public void closeRegister() {
		btnCancel.click();
	}

	public void closeTransfer() {
		btnCloseTransfer.click();
	}

	public void selectBankOrigin(String accountOriginCredit) {
		sleep( VERY_LONG );
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( bankOrigin, accountOriginCredit );
	}

	public void selectBankDestiny(String accountOriginDestiny) {
		sleep( VERY_LONG );
		getAssistants().getAutoCompleteAssistant( driver )
				.sendKeysAndSelectExisting( bankDestiny, accountOriginDestiny );
	}

	public void btnSaveOfxFile() {
		btnContinued.click();
	}

	public void setDate(String Date) {
		dateTransfer.clear();
		dateTransfer.sendKeys( Date );
	}

	public void saveTransfer() {
		btnSaveTransfer.click();
		sleep( VERY_LONG );
	}

	public void actionsMenu() {
		Actions a = new Actions( driver );
		a.moveToElement(
				driver.findElement( By
						.xpath( "//div[contains(@data-original-title, 'Encontrar lan&amp;ccedil;amento já cadastrado e conciliar')]" ) ),
				-5, 5 );
		a.build();
		a.perform();
	}

	public void rolloverMenuConciliation() {
		javascript( "jQuery('.act-actions.act-actions-container.conciliation-actions-container').eq(0).attr('style', 'display: block')" );
		sleep( VERY_QUICKLY );
	}

	public void clickToConciliate() {
		sleep( VERY_QUICKLY );
		clickConciliate.click();
	}

	public String getMessageConcile() {
		waitForNotText( By.className( "container-message" ), "" );
		sleep( VERY_LONG );
		String message = this.driver.findElementByClassName( "container-message" ).getText();
		waitForElementNotPresent( By.className( "container-message" ) );
		return message;
	}

}
