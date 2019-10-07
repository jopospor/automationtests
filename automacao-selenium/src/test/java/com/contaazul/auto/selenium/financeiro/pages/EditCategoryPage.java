package com.contaazul.auto.selenium.financeiro.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.contaazul.auto.selenium.WebPage;

public class EditCategoryPage extends WebPage {

	@FindBy(className = "btn-add-category-revenue")
	private WebElement addCategoryButton;
	@FindBy(className = "btn-add-category-expense")
	private WebElement addExpenseCategoryButton;

	public EditCategoryPage(WebDriver driver) {
		super( driver );
		PageFactory.initElements( driver, this );
	}

	public void clickAddCategoryButton() {
		addCategoryButton.click();
	}

	/**
	 * Deprecado, usar clickAddCategoryButton
	 * 
	 * @return Retorna instância da página CreateCategorypage
	 */
	@Deprecated
	public CreateCategoryPage navigateToNewIncomeCategoryPage() {
		addCategoryButton.click();
		return new CreateCategoryPage( driver );
	}

	public void clickAddExpenseCategoryButton() {
		addExpenseCategoryButton.click();
	}

	/**
	 * Deprecado, usar clickAddExpenseCategoryButton
	 * 
	 * @return Retorna instância da página CreateCategorypage
	 */
	@Deprecated
	public CreateCategoryPage navigateToNewExpenseCategoryPage() {
		addExpenseCategoryButton.click();
		return new CreateCategoryPage( driver );
	}

	public void editCategory(Integer rowNumber, String idList) {
		getAssistants().getGridAssistant( this.driver ).mouseOverRowAndClickEdit( rowNumber, idList );
	}

	/**
	 * Deprecado, usar editCategory
	 * 
	 * @return Retorna instância da página CreateCategoryPage
	 */
	@Deprecated
	public CreateCategoryPage navigateToEditIncomeCategoryPage(Integer rowNumber, String idList) {
		getAssistants().getGridAssistant( this.driver ).mouseOverRowAndClickEdit( rowNumber, idList );
		return new CreateCategoryPage( driver );
	}

	public void removeIncomeCategory(String name) {
		if (isIncomeCategoryListed( name )) {
			WebElement categ = driver.findElement( By.xpath( "//a[contains(text(), '" + name + "')]" ) );
			categ.click();
			WebElement excluir = categ.findElement( By.xpath( "//*[@value='Excluir']" ) );
			excluir.click();
			try {
				getAssistants().getModalAssistant( driver ).confirmAlert();
				getAssistants().getNotificationAssistant( driver ).getAlertMessageText();
			} catch (UnhandledAlertException e) {
				Reporter.log( "workaround", true );
			}
		}
	}

	public Boolean containsIncomeItem(String name) {
		sleep( 1000 );
		waitForElementPresent( By.cssSelector( "#receitas li a" ) );
		List<WebElement> incomesList = this.driver.findElementsByCssSelector( "#receitas li a" );
		for (WebElement incomeItem : incomesList) {
			if (incomeItem.getText().equals( name ))
				return true;
		}
		return false;
	}

	public Boolean isExpenseCategoryListed(String name) {
		sleep( FOR_A_LONG_TIME );
		waitForElementPresent( By.cssSelector( "#despesas li a" ) );
		List<WebElement> incomesList = this.driver.findElementsByCssSelector( "#despesas li a" );
		for (WebElement incomeItem : incomesList) {
			if (incomeItem.getText().equals( name ))
				return true;
		}
		return false;
	}

	public Boolean isIncomeCategoryListed(String name) {
		sleep( FOR_A_LONG_TIME );
		waitForElementPresent( By.cssSelector( "#receitas li a" ) );
		List<WebElement> incomesList = this.driver.findElementsByCssSelector( "#receitas li a" );
		for (WebElement incomeItem : incomesList) {
			if (incomeItem.getText().equals( name ))
				return true;
		}
		return false;
	}

	public void removeExpenseCategory(String name) {
		if (isExpenseCategoryListed( name )) {
			WebElement categ = driver.findElement( By.xpath( "//a[contains(text(), '" + name + "')]" ) );
			categ.click();
			WebElement excluir = categ.findElement( By.xpath( "//*[@value='Excluir']" ) );
			excluir.click();
			try {
				getAssistants().getModalAssistant( driver ).confirmAlert();
				getAssistants().getNotificationAssistant( driver ).getAlertMessageText();
			} catch (UnhandledAlertException e) {
				Reporter.log( "workaround", true );
			}
		}
	}

	public void editExpenseCategory(String name) {
		if (isExpenseCategoryListed( name )) {
			WebElement categ = driver.findElement( By.xpath( "//a[contains(text(), '" + name + "')]" ) );
			categ.click();
			WebElement editar = categ.findElement( By.xpath( "//*[@value='Editar']" ) );
			editar.click();
		}
	}

	public void editIncomeCategory(String name) {
		if (isIncomeCategoryListed( name )) {
			WebElement categ = driver.findElement( By.xpath( "//a[contains(text(), '" + name + "')]" ) );
			categ.click();
			WebElement editar = categ.findElement( By.xpath( "//*[@value='Editar']" ) );
			editar.click();
		}
	}
}
