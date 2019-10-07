package com.contaazul.auto.selenium.assistants;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.WebPage;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;

public class MenuAssistant extends WebPage {

	public MenuAssistant(RemoteWebDriver driver) {
		super( driver );
	}

	/**
	 * 
	 * Navega para o {@link Menu} do conta azul
	 * 
	 * @param menu
	 */

	public void navigateMenu(Menu menu) {
		this.navigateMenu( menu.toMenuSequence().toArray( new String[0] ) );
	}

	/**
	 * Navega para o menu da contaazul utilizando uma sequencia de string
	 * 
	 * @param menuSequence
	 */

	public void navigateMenu(String... menuSequence) {
		if (menuSequence != null && menuSequence.length > 0)
			if (menuSequence.length == 1)
				clickOnTopGroup( menuSequence[0] );
			else
				navigateSequenceMenu( menuSequence[0], menuSequence[1],
						menuSequence[2] );
		sleep( VERY_LONG );
	}

	public void navigateToHomePage() {
		this.clickOnLogo();
	}

	private void navigateSequenceMenu(String topMenu, String subCategory,
			String link) {
		showTopMenu( topMenu );
		driver.findElement(
				By.xpath( "//li[@id='"
						+ findTopGroupId( topMenu )
						+ "']/ul/li/a/h3[contains(text(),'" + subCategory
						+ "')]/parent::a/parent::li/ul/li/a/h3[contains(text(),'" + link + "')]" ) )
				.click();
		hideTopMenu( topMenu );
	}

	private void showTopMenu(String topGroupName) {
		String topMenuId = findTopGroupId( topGroupName );
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( "document.getElementById('" + topMenuId + "').addClassName('simulatedHover')" );
	}

	private void hideTopMenu(String topGroupName) {
		String topMenuId = findTopGroupId( topGroupName );
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript( "document.getElementById('" + topMenuId + "').removeClassName('simulatedHover')" );
	}

	private String findTopGroupId(String topGroupName) {
		WebElement menuLi = driver.findElement( By.xpath( "//span[contains(text(),'" + topGroupName
				+ "')]/parent::a/parent::li" ) );
		return menuLi.getAttribute( "id" );
	}

	private void clickOnTopGroup(String menuItem) {
		driver.findElement( By.className( "big-menu" ) )
				.findElement( By.xpath( "//span[contains(text(), '" + menuItem + "')]" ) ).click();
	}

	private void clickOnUserGroup() {
		driver.findElement(
				By.cssSelector( ".pull-right > li > a.accountUserLogged" ) ).click();
	}

	private void clickOnLogo() {
		driver.findElementByClassName( "logo" ).click();
	}

	private void clickOnConfigurationTool() {
		driver.findElement( By.className( "config-icon" ) ).click();
	}

	public void navigateToMyData() {
		clickOnUserGroup();
		driver.findElement(
				By.linkText( SeleniumSession.getSession().getLocale().translate( "#MEUS_DADOS" ) ) ).click();// Meus
																												// Dados
	}

	public void navigateToMyAccount() {
		clickOnUserGroup();
		driver.findElement(
				By.linkText( SeleniumSession.getSession().getLocale().translate( "#MEU_PLANO" ) ) ).click();// Meu
																											// Plano
	}

	public void navigateToBilling() {
		clickOnUserGroup();
		driver.findElement(
				By.linkText( SeleniumSession.getSession().getLocale().translate( "#MEUS_DADOS_DE_COBRANCA" ) ) )
				.click();// Meus Dados de Cobrança
	}

	public void navigateToExternalApplicationForm() {
		clickOnConfigurationTool();
		driver.findElement(
				By.linkText( SeleniumSession.getSession().getLocale().translate( "#INTEGRACOES_VIA_API" ) ) ).click();// Integrações
																														// via
																														// API
	}

	protected void navigateByDirectRequest(String link) {
		// 2DO: criar um enum com os links e deixar publico
		driver.get( link );
	}

}
