package com.contaazul.auto.selenium.assistants;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.contaazul.auto.selenium.WebPage;

public class TableAssistant extends WebPage {

	public TableAssistant(WebDriver driver) {
		super( driver );
	}

	/**
	 * 
	 * Clica sobre uma linha na tabela. Esta acao fara com que surja uma tela de
	 * edicao daquele registro
	 * 
	 * @param rootElementId
	 *            O id da div acima da tabela
	 * @param rowNumber
	 *            O numerto da linha, baseado em zero
	 */

	public void editRow(String rootElementId, Integer rowNumber) {
		if (rowNumber < 0) {
			throw new RuntimeException( "The row number cannot be negative" );
		}
		Integer onebasedRowNumber = rowNumber + 1;
		driver.findElementByXPath( "//*[@id='" + rootElementId + "']/table/tbody/tr[" + onebasedRowNumber + "]" )
				.click();
		waitForElementPresent( By.id( "newPopupManagerReplacement" ) );
	}

}
