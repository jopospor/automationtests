package com.contaazul.auto.selenium.assistants;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.contaazul.auto.selenium.WebPage;

public class DropdownAssistant extends WebPage {

	public DropdownAssistant(WebDriver driver) {
		super( driver );
	}

	public void applyFilter(String id) {
		this.driver.findElementByXPath( "//div[@id='" + id + "']//*/button[contains(text(), 'Aplicar')]" ).click();
		sleep( VERY_LONG );
	}

	public void selectDropdownOption(String id, String option) {
		this.driver.findElementByXPath( "//div[@id='" + id + "']//*[contains(text(), '" + option + "')]" )
				.click();
	}

	public void openDropdownOptions(String id) {
		this.driver.findElementByCssSelector(
				"#" + id + "[data-toggle='dropdown'],#" + id + " [data-toggle='dropdown']" ).click();
	}

	public void cleanDropdownCheckboxes(String id) {
		JavascriptExecutor executor = this.driver;
		executor.executeScript( "jQuery('#" + id + " .selected').click();" );
		executor.executeScript( "jQuery('#" + id + " .selected:first').click();" );
	}

}
