package com.contaazul.auto.selenium.financeiro.pages;

import java.io.IOException;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.contaazul.auto.selenium.WebPage;

public class RegisterRevenueAndExpenseAttachedPage extends WebPage {

	@FindBy(xpath = "//div[@id='statement-list-container']/table/tbody/tr/td[4]/div/span/i")
	private WebElement getElementPaperClip;
	@FindBy(xpath = "//div[@id='finance-image-placeholder']/div/div/div/div/span/i")
	private WebElement btnRemoveAttachment;
	@FindBy(xpath = "//*[@id='finance-image-placeholder']/div[1]/div")
	private WebElement getAttachmentField1;
	@FindBy(xpath = "//*[@id='finance-image-placeholder']/div[2]/div")
	private WebElement getAttachmentField2;
	@FindBy(id = "idClienteFornecedor")
	private WebElement customer;
	@FindBy(id = "file")
	private WebElement attachment;

	public RegisterRevenueAndExpenseAttachedPage(WebDriver driver) {
		super( driver );
	}

	public void setClientProvider(String clientProvider) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( customer, clientProvider );
	}

	public void revenueUpload(String arquivo) throws IOException {
		sleep( VERY_LONG );
		driver.switchTo().frame( "idUpload" );
		getAssistants().getFileUploadAssistant( driver ).remoteUploadFile(
				attachment, "//src//test//resources//file_attachments//" + arquivo );

		driver.switchTo().defaultContent();
		sleep( VERY_LONG );
	}

	public String getMessageValidation() {
		sleep( VERY_LONG );
		return driver.findElementByClassName( "container-message" ).getText();
	}

	public String getMessageVerification() {
		return driver.findElementByXPath( "//*[@id='popupNotify']/div/div[1]" ).getText();
	}

	public boolean isPaperclipDisplayed() {
		waitForElementNotPresent( By.className( "container-message" ) );
		return this.getElementPaperClip.isEnabled();
	}

	public void removeAttachment() {
		sleep( VERY_LONG );

		javascript( "jQuery('.finance-thumb-delete-container').eq(0).attr('style', 'display: block')" );

		sleep( VERY_LONG );
		driver.findElement( By.xpath( "//div[@id='finance-image-placeholder']/div/div/div/div/span" ) ).click();

		new WebDriverWait( driver, 7000 ).until( ExpectedConditions.alertIsPresent() );
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
	}

	public boolean isAttachmentDisplayed1() {
		sleep( VERY_LONG );
		return this.getAttachmentField1.isDisplayed();
	}

	public boolean isAttachmentDisplayed2() {
		sleep( VERY_LONG );
		return this.getAttachmentField2.isDisplayed();
	}

	public void clickRemoveAttachment(Integer nrLine) {
		getAssistants().getListingAssistant( driver ).getListingRowAsElement( nrLine )
				.findElement( By.xpath( "td[4]/span" ) ).click();
	}
}
