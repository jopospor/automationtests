package com.contaazul.auto.selenium.componentesweb.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.contaazul.auto.selenium.WebPage;

public class NewMenuPage extends WebPage {

	public NewMenuPage(WebDriver driver) {
		super( driver );
	}

	@FindBy(id = "act-logo-topo-novo")
	protected WebElement logoDashboard;
	@FindBy(id = "file")
	private WebElement upFile;
	@FindBy(id = "upload-logo-popover-save-btn")
	protected WebElement saveLogo;
	@FindBy(id = "notification-number")
	protected WebElement userNotificationSupport;
	@FindBy(id = "liConfiguracoes")
	protected WebElement userConfigure;
	@FindBy(id = "liPerfil")
	protected WebElement perfilUser;
	@FindBy(id = "breadcrumb")
	private WebElement breadcrumb;
	@FindBy(id = "bankIntegrationCancelBtn")
	private WebElement buttonCancel;

	public void buttonCancel() {
		buttonCancel.click();
	}

	public void buttonSaveLogo() {
		saveLogo.click();
	}

	public void clickUserNotificationSupport() {
		userNotificationSupport.click();

	}

	public void clickConfigure() {
		userConfigure.click();

	}

	public void clickDataUser() {
		perfilUser.click();

	}

	public void uploadLogo(String arquivo) {
		driver.switchTo().frame( "idUpload" );
		try {
			getAssistants().getFileUploadAssistant( driver ).remoteUploadFile( upFile,
					"//src//test//resources//file_attachments//" + arquivo );
		} catch (IOException e) {
			Assert.fail( "AVISO: O Upload " + arquivo
					+ " falhou.", e );
		}
		sleep( VERY_LONG );
		driver.switchTo().defaultContent();

		if (saveLogo.isDisplayed())
			saveLogo.click();
		else
			buttonCancel.click();

	}

	public void clickToLogo() {
		logoDashboard.click();
	}

	public String getBreadcrumbText() {
		return breadcrumb.getText();
	}
}
