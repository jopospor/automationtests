package com.contaazul.auto.selenium.dashboard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class GraphicalDashboardPage extends WebPage {

	@FindBy(xpath = "//*[@id='inOutComesContent']/div[3]")
	protected WebElement graphical;

	public void tooltip(String numeroBarra) {
		sleep( VERY_LONG );
		String script = "var evObj = document.createEvent( 'Events' );" +
				"evObj.initEvent( 'mouseover', true, false );" +
				"document.getElementById( 'highcharts_drilldown_bar_" + numeroBarra
				+ "' ).dispatchEvent( evObj );";
		javascript( script );

	}

	public boolean isTooltipTextDisplayed(String value) {
		try {
			driver.findElement( By.xpath( "//div[contains(text(),'" + value + "')]" ) );
			return true;
		} catch (ElementNotFoundException e) {
			return false;
		}
	}

	public String getTooltipText() {
		sleep( VERY_LONG );
		return driver.findElementById( "highchartInOutComesChartTooltipPoint" ).getText();

	}

	public void drillDown(String numeroBarra) {
		driver.findElementById( "highcharts_drilldown_bar_" + numeroBarra + "" ).click();
		sleep( VERY_QUICKLY );
	}

	public boolean getGraphicalText() {
		return graphical.getText().contains( "Exemplo" );

	}

	public GraphicalDashboardPage(WebDriver driver) {
		super( driver );
	}

}
