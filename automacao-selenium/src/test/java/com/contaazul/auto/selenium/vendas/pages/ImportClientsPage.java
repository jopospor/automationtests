package com.contaazul.auto.selenium.vendas.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class ImportClientsPage extends WebPage {

	@FindBy(id = "file")
	protected WebElement fileField;
	@FindBy(id = "doImport")
	protected WebElement importButton;
	@FindBy(id = "columnSelectionDone")
	private WebElement importReady;

	public ImportClientsPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void clickImport() {
		importButton.click();
	}

	public void setFileToImport(String filePath) {
		driver.switchTo().defaultContent();
		try {
			Thread.sleep( 500 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// iframe
		driver.switchTo().frame( "idUpload" );
		try {
			getAssistants().getFileUploadAssistant( driver ).remoteUploadFile( fileField, filePath );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().defaultContent();
		waitForElementPresent( By.xpath( "//div[contains(text(),'Arquivo enviado.')]" ), 120 );
	}

	public long uploadFile() {
		long startTime = System.currentTimeMillis();
		importButton.click();
		driver.findElement( By.xpath( "//div[@id='column-container']/div[2]/ul/li/div/button" ) ).click();
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

	public void mapColumns() {
		try {
			driver.findElement( By.xpath( "//*[@id='column-container']/div[2]/ul[1]/li[1]/div/button/span[2]" ) )
					.click();
			driver.findElement( By.xpath( "//a[contains(text(),'Nome do cliente / Nome Fantasia *')]" ) ).click();
			Thread.sleep( 500 );
			driver.findElement( By.xpath( "//div[@id='column-container']/div[2]/ul[4]/li/div/button" ) ).click();
			Thread.sleep( 500 );
			driver.findElement( By.xpath( "(//a[contains(text(),'Endereço')])[4]" ) ).click();
			Thread.sleep( 500 );
			driver.findElement( By.xpath( "//div[@id='column-container']/div[2]/ul[5]/li/div/button" ) ).click();
			Thread.sleep( 500 );
			driver.findElement( By.xpath( "(//a[contains(text(),'Número')])[5]" ) ).click();
			Thread.sleep( 500 );
			driver.findElement( By.xpath( "//div[@id='column-container']/div[2]/ul[8]/li/div/button" ) ).click();
			Thread.sleep( 500 );
			driver.findElement( By.xpath( "(//a[contains(text(),'Telefone')])[8]" ) ).click();
			Thread.sleep( 500 );
			driver.findElement( By.xpath( "//div[@id='column-container']/div[2]/ul[11]/li/div/button" ) ).click();
			Thread.sleep( 500 );
			driver.findElement( By.xpath( "(//a[contains(text(),'Inscrição Estadual')])[11]" ) ).click();
			Thread.sleep( 500 );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long importFile() {
		long startTime = System.currentTimeMillis();
		importReady.click();
		driver.findElement( By.id( "import-done" ) );
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

	public String getResultMessage() {
		waitForText( By.xpath( "//div[@class='import-description margin-top']" ), "Pronto" );
		return driver.findElement( By.className( "imported-counter-add" ) ).getText();
	}

}
