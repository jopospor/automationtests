package com.contaazul.auto.selenium;

import java.net.URL;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.Reporter;

public class LogEnabledRemoteWebDriver extends RemoteWebDriver implements WebDriver {

	private final long EXECUTION_INTERVAL = 200;
	private boolean verbose = true;

	public LogEnabledRemoteWebDriver() {
		super();
	}

	public LogEnabledRemoteWebDriver(boolean verbose) {
		super();
		this.verbose = verbose;
	}

	public LogEnabledRemoteWebDriver(Capabilities desiredCapabilities, boolean verbose) {
		super( desiredCapabilities );
		this.verbose = verbose;
	}

	public LogEnabledRemoteWebDriver(CommandExecutor executor, Capabilities desiredCapabilities, boolean verbose) {
		super( executor, desiredCapabilities );
		this.verbose = verbose;
	}

	public LogEnabledRemoteWebDriver(URL remoteAddress, Capabilities desiredCapabilities, boolean verbose) {
		super( remoteAddress, desiredCapabilities );
		this.verbose = verbose;
	}

	@Override
	protected void log(SessionId sessionId, String commandName, Object toLog, When when) {
		if (verbose)
			Reporter.log( "LOG NOTIFICADO - Comando: " + commandName + ", " + "Objeto: " + toLog.toString(), true );
	}

	@Override
	public WebElement findElement(By by) {
		try {
			Thread.sleep( EXECUTION_INTERVAL );
		} catch (InterruptedException e) {
			Reporter.log( "Warning: erro ao esperar intervalo entre execuções.", true );
		}
		return by.findElement( (SearchContext) this );
	}

	@Override
	public void get(String url) {
		super.get( url );
	}

	@Override
	public String getCurrentUrl() {
		return super.getCurrentUrl();
	}

	@Override
	public String getTitle() {
		return super.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {
		return super.findElements( by );
	}

	@Override
	public String getPageSource() {
		return super.getPageSource();
	}

	@Override
	public void close() {
		super.close();
	}

	@Override
	public void quit() {
		super.quit();
	}

	@Override
	public Set<String> getWindowHandles() {
		return super.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {
		return super.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		return super.switchTo();
	}

	@Override
	public Navigation navigate() {
		return super.navigate();
	}

	@Override
	public Options manage() {
		return super.manage();
	}

}
