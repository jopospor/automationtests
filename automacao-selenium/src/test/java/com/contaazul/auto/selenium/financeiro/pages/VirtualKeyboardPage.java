package com.contaazul.auto.selenium.financeiro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.contaazul.auto.selenium.WebPage;

public class VirtualKeyboardPage extends WebPage {

	public VirtualKeyboardPage(WebDriver driver) {
		super( driver );
	}

	public void click(String digit) {
		driver.findElement( By.xpath( "//div[contains(text(), '" + digit + "']" ) ).click();
	}

}
