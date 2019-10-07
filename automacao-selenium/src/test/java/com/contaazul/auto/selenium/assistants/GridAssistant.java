package com.contaazul.auto.selenium.assistants;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.contaazul.auto.selenium.WebPage;

public class GridAssistant extends WebPage {

	public GridAssistant(WebDriver driver) {
		super( driver );
	}

	public void mouseOverRowAndClickEdit(Integer rowNumber, String idListCategory) {
		Actions actions = new Actions( driver );
		waitForElementPresent( By.cssSelector( idListCategory ) );
		List<WebElement> elements = this.driver.findElementsByCssSelector( idListCategory );
		actions.moveToElement( elements.get( rowNumber ) ).build().perform();
		waitForElementPresent( By.cssSelector( ".actions input[value='Editar']" ) );
		WebElement editButton = this.driver.findElementByCssSelector( ".actions input[value='Editar']" );
		actions.click( editButton ).build().perform();
	}

	public void mouseOverRowAndClickRemove(Integer rowNumber, String idListcategory) {
		Actions actions = new Actions( driver );
		waitForElementPresent( By.cssSelector( "#" + idListcategory + "" ) );
		List<WebElement> elements = this.driver.findElementsByCssSelector( "#" + idListcategory + "" );
		actions.moveToElement( elements.get( rowNumber ) ).perform();
		waitForElementPresent( By.cssSelector( ".actions input[value='Excluir']" ) );
		javascript( "jQuery('input[value=\"Excluir\"]:first').click();" );
		acceptAlert();
	}

	private void acceptAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
}
