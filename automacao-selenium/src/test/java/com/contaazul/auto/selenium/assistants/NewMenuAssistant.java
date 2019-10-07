package com.contaazul.auto.selenium.assistants;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.contaazul.auto.selenium.WebPage;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;

public class NewMenuAssistant extends WebPage {

	public NewMenuAssistant(WebDriver driver) {
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
		sleep( FOR_A_LONG_TIME );
	}

	private void clickOnTopGroup(String menuItem) {
		driver.findElementById( "liInicio" ).click();
	}

	public void navigateToHomePage() {
		driver.findElementByClassName( "logo" ).click();
	}

	private void navigateSequenceMenu(String topMenu, String subCategory,
			String link) {
		javascript( "$j('#" + findTopGroupId( topMenu ) + "').mouseenter()" );
		sleep( QUICKLY );
		driver.findElement(
				By.xpath( "//a[@id='" + findTopGroupId( topMenu ) + "']/parent::div/ul/li/ul/li[contains(text(), '"
						+ subCategory + "')]/parent::ul/li/a[starts-with(text(),'"
						+ link + "')]" ) ).click();
	}

	private String findTopGroupId(String topMenu) {
		WebElement menuLi = driver.findElement( By.linkText( topMenu ) );
		return menuLi.getAttribute( "id" );
	}

	public void userProfile(String optionUser) {
		javascript( "$j('#liPerfil').mouseenter()" );
		driver.findElementByLinkText( optionUser ).click();

	}

	public void userConfigure(String optionConfigure) {
		javascript( "$j('#liConfiguracoes').parent().find('a:contains(\"" + optionConfigure + "\")').click()" );

	}

}
