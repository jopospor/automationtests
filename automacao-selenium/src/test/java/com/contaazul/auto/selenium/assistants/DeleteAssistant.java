package com.contaazul.auto.selenium.assistants;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

import com.contaazul.auto.selenium.WebPage;

public class DeleteAssistant extends WebPage {

	public DeleteAssistant(WebDriver driver) {
		super( driver );
	}

	public void deleteAllFinancialStatements() {
		/**
		 * Exclui lançamentos no extrato, depesas e receitas.
		 */
		if (getNumberOfRecords() > 0) {
			ListingAssistant listingAssistant = getAssistants().getListingAssistant( driver );
			listingAssistant.checkAllItems();
			driver.findElement( By.xpath( "//button[contains(text(),'Ações')]" ) ).click();
			sleep( FOR_A_LONG_TIME );
			driver.findElementById( "selectedActions" ).findElement( By.linkText( "Excluir" ) ).click();
			driver.findElement( By.linkText( "Excluir Agora" ) ).click();
		}
	}

	private int getNumberOfRecords() {
		try {
			return new Integer(
					driver.findElement( By.xpath( "//div[contains (@class,'summary-item qty')]/span" ) ).getText() );
		} catch (Exception e) {
			return 0;
		}
	}

	public void deleteAllBankAccounts(String accountName) {
		/**
		 * Exclui contas bancárias
		 */
		if (hasBanksListed()) {
			waitForElementPresent(
					By.xpath( "//tr[td[contains(text(), '" + accountName
							+ "')]]/td[1]/span" ), 30 );
			driver.findElement(
					By.xpath( "//tr[td[contains(text(), '" + accountName
							+ "')]]/td[1]/span" ) ).click();

			driver.findElementByLinkText( "Excluir" ).click();
			sleep( VERY_QUICKLY );
			driver.switchTo().alert().accept();

		}
	}

	public boolean hasBanksListed() {
		try {
			new FluentWait<WebDriver>( driver ).pollingEvery( getSessionDefaultTimeout(),
					TimeUnit.MILLISECONDS )
					.pollingEvery( 500, TimeUnit.MILLISECONDS ).until( new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver driver) {
							try {
								return driver.findElement( By.id( "formCliente_" ) ).getText()
										.contains( "Nenhum registro cadastrado." );
							} catch (Exception e) {
								return false;
							}
						}
					} );
			return false;
		} catch (Exception e) {
			return true;
		}
	}
}
