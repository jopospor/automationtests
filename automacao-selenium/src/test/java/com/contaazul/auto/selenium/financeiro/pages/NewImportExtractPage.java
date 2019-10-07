package com.contaazul.auto.selenium.financeiro.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.contaazul.auto.selenium.WebPage;

public class NewImportExtractPage extends WebPage {
	public NewImportExtractPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	@FindBy(id = "newIdConta")
	protected WebElement extractAccount;
	@FindBy(id = "ofxImportDialogBtn")
	protected WebElement buttonImport;
	@FindBy(id = "OfxImportDialogCancel")
	protected WebElement buttonCancel;
	@FindBy(id = "ofxImportFileUploadField")
	protected WebElement selectImportOfx;

	public void setNameAccountExtract(String nomeBanco) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( extractAccount, nomeBanco );
	}

	public void clickImportExtract() {
		buttonImport.click();
		sleep( VERY_LONG );
	}

	public void btnCancel() {
		buttonCancel.click();
	}

	public void clickSelectImport() {
		selectImportOfx.click();
	}

	public void selectFileImport(String arquivo) {
		sleep( FOR_A_LONG_TIME );
		try {
			getAssistants().getFileUploadAssistant( driver ).remoteUploadFile( selectImportOfx,
					"//src//test//resources//file_attachments//" + arquivo );
		} catch (IOException e) {
			Assert.fail( "AVISO: A importação do arquivo " + arquivo
					+ " falhou.", e );
		}
		sleep( VERY_LONG );
		driver.switchTo().defaultContent();
	}

}
