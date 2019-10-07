package com.contaazul.auto.selenium.financeiro.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class CreateCategoryPage extends WebPage {

	private static final int CATEGORY_NAME_MAX_LENGTH = 50;

	@FindBy(css = ".contaazul-components-buttonbar.saveGroup .save_edit")
	private WebElement saveButton;
	@FindBy(css = ".contaazul-components-buttonbar.saveGroup .cancel_edit")
	private WebElement cancelButton;
	@FindBy(id = "dsNaturezaFinanceira")
	private WebElement categoryNameField;
	@FindBy(id = "idNaturezaFinanceiraParent")
	private WebElement appearInsideOfSelect;
	@FindBy(id = "habilitarDro")
	private WebElement considerLaunchCheckbox;
	@FindBy(id = "popupNotify")
	private WebElement validationMessageBox;

	public CreateCategoryPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void setCategoryText(String categoryName) {
		validateCategoryNameLength( categoryName );
		sleep( VERY_LONG );
		this.categoryNameField.clear();
		this.categoryNameField.sendKeys( categoryName );
	}

	public String getCategoryText() {
		return this.categoryNameField.getText();
	}

	public void setAppearInsideOfText(String insideOfText) {
		List<WebElement> options = appearInsideOfSelect.findElements( By.tagName( "option" ) );
		for (WebElement option : options) {
			if (option.getText().equals( insideOfText )) {
				option.click();
				break;
			}
		}
	}

	public void setConsiderLaunchCheckbox(Boolean selected) {
		if (selected && !this.considerLaunchCheckbox.isSelected())
			this.considerLaunchCheckbox.click();
		else if (this.considerLaunchCheckbox.isSelected())
			this.considerLaunchCheckbox.click();
	}

	public void save() {
		saveButton.click();
		getAssistants().getModalAssistant( this.driver ).waitAppear();
	}

	public void cancel() {
		cancelButton.click();
		getAssistants().getModalAssistant( this.driver ).dismissAndWait();
	}

	public static void validateCategoryNameLength(String name) {
		if (name.length() > CATEGORY_NAME_MAX_LENGTH)
			throw new RuntimeException( "Category name cannot be greater than 50 characters" );
	}

	public String getValidationMessage() {
		return validationMessageBox.getText();
	}

}
