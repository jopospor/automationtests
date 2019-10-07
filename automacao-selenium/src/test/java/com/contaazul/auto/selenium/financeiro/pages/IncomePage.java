package com.contaazul.auto.selenium.financeiro.pages;

import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.contaazul.auto.selenium.WebPage;

/**
 * Deprecated, use CreateIncomePage
 * 
 * @param <T>
 */
@Deprecated
public class IncomePage<T extends WebPage> extends WebPage {

	private T parentWebPage;

	@FindBy(id = "dtVencimento")
	private WebElement expirationDate;

	@FindBy(id = "idCategoria")
	private WebElement categoryAutocomplete;

	@FindBy(css = "li.dataFieldName")
	public WebElement autoCompletePopUp;

	public IncomePage(T parentWebPage, WebDriver driver) {
		super( driver );
		this.parentWebPage = parentWebPage;
		PageFactory.initElements( driver, this );
		sleep( VERY_LONG );
	}

	public void setBankAccount(String bankAccountName) {
		WebElement bankAccountAutocomplete = driver.findElementById( "newIdConta" );
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting(
				bankAccountAutocomplete, bankAccountName );
	}

	public T save() {
		this.driver.findElementByXPath( "//button[contains(text(), 'Salvar Receita')]" ).click();
		getAssistants().getNotificationAssistant( driver ).waitLoadingDismiss();
		return this.parentWebPage;
	}

	public void createCategoryName(String name) {
		CreateCategoryPage.validateCategoryNameLength( name );
		this.categoryAutocomplete.clear();
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndCreateNew( this.categoryAutocomplete, name );
	}

	public boolean containsCategoryName(String categoryName) {
		return getAssistants().getAutoCompleteAssistant( driver ).containsOption( categoryAutocomplete, categoryName );
	}

	public void setExpirationDate(Calendar calendar) {
		getAssistants().getDatePickerAssistant( driver ).setDate( this.expirationDate, calendar );
	}

	public T close() {
		driver.findElementByClassName( "close" ).click();
		return this.parentWebPage;
	}

	public Boolean getSugestionCategoryAutoComplete(final String input) {
		try {
			(new WebDriverWait( driver, 10 )).until( new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.findElement( By.cssSelector( "li.dataFieldName" ) ).getText().contains( input );
				}
			} );
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void selectCategoryName(String name) {
		CreateCategoryPage.validateCategoryNameLength( name );
		this.categoryAutocomplete.clear();
		this.categoryAutocomplete.sendKeys( name );
	}

}
