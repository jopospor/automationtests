package com.contaazul.auto.selenium.assistants;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.contaazul.auto.selenium.WebPage;
import com.contaazul.auto.selenium.clientes.pages.AdminPage;

public class AdminAssistant extends WebPage {

	public AdminAssistant(WebDriver driver) {
		super( driver );
	}

	public void goToAdminPageAndAuthenticate() {

		AdminPage adminPage = PageFactory.initElements( driver, AdminPage.class );

		adminPage.goToAdminpage();

		Robot robot;
		try {
			robot = new Robot();

			robot.keyPress( KeyEvent.VK_M );
			robot.keyRelease( KeyEvent.VK_M );
			robot.keyPress( KeyEvent.VK_A );
			robot.keyRelease( KeyEvent.VK_A );
			robot.keyPress( KeyEvent.VK_R );
			robot.keyRelease( KeyEvent.VK_R );
			robot.keyPress( KeyEvent.VK_V );
			robot.keyRelease( KeyEvent.VK_V );
			robot.keyPress( KeyEvent.VK_I );
			robot.keyRelease( KeyEvent.VK_I );
			robot.keyPress( KeyEvent.VK_N );
			robot.keyRelease( KeyEvent.VK_N );
			sleep( FOR_A_LONG_TIME );
			robot.keyPress( KeyEvent.VK_TAB );
			robot.keyRelease( KeyEvent.VK_TAB );
			robot.keyPress( KeyEvent.VK_C );
			robot.keyRelease( KeyEvent.VK_C );
			robot.keyPress( KeyEvent.VK_0 );
			robot.keyRelease( KeyEvent.VK_0 );
			robot.keyPress( KeyEvent.VK_N );
			robot.keyRelease( KeyEvent.VK_N );
			robot.keyPress( KeyEvent.VK_T );
			robot.keyRelease( KeyEvent.VK_T );
			robot.keyPress( KeyEvent.VK_SHIFT );
			robot.keyPress( KeyEvent.VK_2 );
			robot.keyRelease( KeyEvent.VK_2 );
			robot.keyRelease( KeyEvent.VK_SHIFT );
			robot.keyPress( KeyEvent.VK_SHIFT );
			robot.keyPress( KeyEvent.VK_2 );
			robot.keyRelease( KeyEvent.VK_2 );
			robot.keyRelease( KeyEvent.VK_SHIFT );
			robot.keyPress( KeyEvent.VK_Z );
			robot.keyRelease( KeyEvent.VK_Z );
			robot.keyPress( KeyEvent.VK_U );
			robot.keyRelease( KeyEvent.VK_U );
			robot.keyPress( KeyEvent.VK_1 );
			robot.keyRelease( KeyEvent.VK_1 );
			robot.keyPress( KeyEvent.VK_ENTER );
			robot.keyRelease( KeyEvent.VK_ENTER );
			sleep( VERY_LONG );
			robot.keyPress( KeyEvent.VK_ESCAPE );
			robot.keyRelease( KeyEvent.VK_ESCAPE );

		} catch (AWTException e) {
			e.printStackTrace();
		}

	}
}
