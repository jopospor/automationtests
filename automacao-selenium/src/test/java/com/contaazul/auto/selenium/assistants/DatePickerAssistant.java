package com.contaazul.auto.selenium.assistants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.WebPage;

public class DatePickerAssistant extends WebPage {

	public DatePickerAssistant(RemoteWebDriver driver) {
		super( driver );
	}

	public void setDate(WebElement datePicker, Calendar calendar) {
		this.setDate( datePicker, calendar, getDateFormat() );
	}

	public void setDate(WebElement datePicker, Calendar calendar, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat( format );
		String dateFormatted = formatter.format( calendar.getTime() );
		datePicker.clear();
		datePicker.sendKeys( dateFormatted );
	}

	private String getDateFormat() {
		return SeleniumSession.getSession().getProperties().getProperty( SeleniumProperties.DATE_FORMAT );
	}

}
