package com.contaazul.auto.selenium.financeiro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.contaazul.auto.selenium.WebPage;

public class DeleteIncomePage extends WebPage {

	@FindBy(xpath = "//*[@id='newPopupManagerReplacement']/div[3]/a[1]")
	private WebElement deleteNowButton;

	@FindBy(className = "container-message")
	private WebElement sucessMessage;

	@FindBy(xpath = "//*[@id='statementSelector']/span/span/img")
	private WebElement checkboxSelectAll;

	@FindBy(xpath = "//*[@id='newPopupManagerReplacement']/div[2]/p/span")
	private WebElement numberOfRecordsPhrase;

	public DeleteIncomePage(WebDriver driver) {
		super( (RemoteWebDriver) (driver) );
		PageFactory.initElements( driver, this );
	}

	public void checkTableRow(Integer row) {
		String xpathRow = "//*[@id='statement-list-container']/table[1]/tbody/tr[" + row + "]/td[2]/span";
		driver.findElement( By.xpath( xpathRow ) ).click();
	}

	public void clickDeleteNowButton() {
		this.deleteNowButton.click();
	}

	public String getSucessMessage() {
		return this.sucessMessage.getText();
	}

	public int getNumberOfRecordsWillBeDeleted() {
		if (isJustOneRecord())
			return 1;
		else
			return Integer.parseInt( getNumberOfRecordsPhrase() );
	}

	private boolean isJustOneRecord() {
		return (getNumberOfRecordsPhrase().equals( "uma" ) || getNumberOfRecordsPhrase().equals( "um" ));
	}

	private String getNumberOfRecordsPhrase() {
		return this.numberOfRecordsPhrase.getText().split( " " )[0];
	}

	public void clickCheckboxSelectAll() {
		this.checkboxSelectAll.click();
	}

	public boolean isSuccessMessageDisplayed() {
		try {
			waitForElementNotPresent( By.className( sucessMessage.getAttribute( "class" ) ) );
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
