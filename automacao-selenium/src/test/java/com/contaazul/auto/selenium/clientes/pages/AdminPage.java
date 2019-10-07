package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.WebPage;

public class AdminPage extends WebPage {

	@FindBy(id = "searchField")
	protected WebElement searchField;

	@FindBy(id = "searchButton")
	protected WebElement searchButton;

	@FindBy(xpath = "//*[contains(text(), 'Teste A/B')]")
	protected WebElement testABButton;

	@FindBy(id = "newListStep")
	protected WebElement selectNewListStep;

	@FindBy(id = "sendButton")
	protected WebElement saveFeaturesButton;

	public AdminPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void goToAdminpage() {
		driver.get( "https://"
				+ SeleniumSession.getSession().getProperties().getProperty( SeleniumProperties.APPLICATION_BASE_URL )
				+ "/contaazul-admin" );
	}

	public void setSearchField(String searchFieldText) {
		searchField.sendKeys( searchFieldText );
	}

	public void clickSearchButton() {
		searchButton.click();
	}

	public void clickTestABButtonOnFirstuser(final String email) {
		(new WebDriverWait( driver, 15 )).until( new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver d) {
				return driver.findElement( By.id( "searchResults" ) ).getText()
						.contains( email );
			}
		} );
		testABButton.click();
	}

	public void changeNewListStep() {
		new Select( selectNewListStep )
				.selectByVisibleText( "Com novos list steps" );
	}

	public void clickSaveFeatures() {
		saveFeaturesButton.click();
		sleep( VERY_LONG );
	}

	public void setABTest(String label, String value) {
		sleep( FOR_A_LONG_TIME );
		WebElement select = driver.findElementByXPath( "//label[contains(text(), '" + label + "')]/../div/select" );
		new Select( select ).selectByVisibleText( value );
	}

}
