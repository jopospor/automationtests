package com.contaazul.auto.selenium;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.log4j.Log4j;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.contaazul.auto.TestSession;
import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.selenium.assistants.ContaAzulAssistants;

@Log4j
public class WebPage {

	protected static final int VERY_QUICKLY = 100;
	protected static final int QUICKLY = 500;
	protected static final int FOR_A_LONG_TIME = 1000;
	protected static final int VERY_LONG = 3000;
	protected static final int VERY_LONGEST = 5000;
	protected static final int MUCH_LONGER = 20000;

	protected Select select;
	private ContaAzulAssistants assistentes;
	protected final RemoteWebDriver driver;

	public WebPage(final WebDriver driver) {
		this.driver = (RemoteWebDriver) driver;
	}

	public ContaAzulAssistants getAssistants() {
		return (assistentes == null ? new ContaAzulAssistants() : assistentes);
	}

	/*
	 * #Sobre WAITS, ASSERTS, e VERIFYS All Selenium Assertions can be used in 3
	 * modes: "assert", "verify", and "waitFor". For example, you can
	 * "assertText", "verifyText" and "waitForText".
	 * 
	 * When an "assert" fails, the test is aborted.
	 * 
	 * When a "verify" fails, the test will continue execution, logging the
	 * failure. This allows a single "assert" to ensure that the application is
	 * on the correct page, followed by a bunch of "verify" assertions to test
	 * form field values, labels, etc.
	 * 
	 * "waitFor" commands wait for some condition to become true (which can be
	 * useful for testing Ajax applications). They will succeed immediately if
	 * the condition is already true. However, they will fail and halt the test
	 * if the condition does not become true within the current timeout setting
	 * (see the setTimeout action below).
	 * 
	 * Documentacao Selenium
	 */

	protected boolean isElementPresent(By by) {
		try {
			driver.findElement( by );
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected boolean isElementPresent(By by, int timeout) {
		// 2DO: Já que o timeout padrão do Selenium é imutável (depois de
		// setado), aqui vamos precisar de uns WAITS. Implementar.
		try {
			driver.findElement( by );
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected void waitForTextNotPresent(final By by, final String value,
			int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						return !driver.findElement( by ).getText()
								.contains( value );
					}
				} );
	}

	protected void waitForTextNotPresent(final By by, final String value) {
		int timeout;
		try {
			timeout = Integer.parseInt( SeleniumSession.getSession()
					.getProperties()
					.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) );
		} catch (Exception e) {
			timeout = 0;
		}
		waitForTextNotPresent( by, value, timeout );
	}

	protected void waitForTextPresent(final By by, final String value,
			int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						return driver.findElement( by ).getText().contains( value );
					}
				} );
	}

	protected void waitForTextPresent(final By by, final String value) {
		int timeout;
		try {
			timeout = Integer.parseInt( SeleniumSession.getSession()
					.getProperties()
					.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) );
		} catch (Exception e) {
			timeout = 0;
		}
		waitForTextPresent( by, value, timeout );
	}

	protected void verifyTextNotPresent(final By by, final String value,
			int timeout) {
		try {
			waitForTextNotPresent( by, value, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );
		}
	}

	protected void verifyTextNotPresent(final By by, final String value) {
		verifyTextNotPresent(
				by,
				value,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifyTextPresent(final By by, final String value,
			int timeout) {
		try {
			waitForTextPresent( by, value, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );
		}
	}

	protected void verifyTextPresent(final By by, final String value) {
		verifyTextPresent(
				by,
				value,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifyText(final By by, final String value, int timeout) {
		try {
			waitForText( by, value, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );

		}
	}

	protected void verifyText(final By by, final String value) {
		verifyText(
				by,
				value,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifyNotText(final By by, final String value, int timeout) {
		try {
			waitForNotText( by, value, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );

		}
	}

	protected void verifyNotText(final By by, final String value) {
		verifyNotText(
				by,
				value,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifyElementPresent(final By by, int timeout) {
		try {
			waitForElementPresent( by, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );
		}
	}

	protected void verifyElementPresent(final By by) {
		verifyElementPresent(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifyElementNotPresent(final By by, int timeout) {
		try {
			waitForElementNotPresent( by, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );
		}
	}

	protected void verifyElementNotPresent(final By by) {
		verifyElementNotPresent(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifySelectedLabels(final By by, final String[] labels,
			int timeout) {
		try {
			waitForSelectedLabels( by, labels, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );

		}
	}

	protected void verifySelectedLabels(final By by, final String[] labels) {
		verifySelectedLabels(
				by,
				labels,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifyNotSelectedLabels(final By by, final String[] labels,
			int timeout) {
		try {
			waitForNotSelectedLabels( by, labels, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );

		}
	}

	protected void verifyNotSelectedLabels(final By by, final String[] labels) {
		verifyNotSelectedLabels(
				by,
				labels,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifyChecked(final By by, int timeout) {
		try {
			waitForChecked( by, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );

		}
	}

	protected void verifyChecked(final By by) {
		verifyChecked(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifyNotChecked(final By by, int timeout) {
		try {
			waitForNotChecked( by, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );
		}
	}

	protected void verifyNotChecked(final By by) {
		verifyNotChecked(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifyElementEnabled(final By by, int timeout) {
		try {
			waitForElementNotPresent( by, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );
		}
	}

	protected void verifyElementEnabled(final By by) {
		verifyElementEnabled(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void verifyNotElementEnabled(final By by, int timeout) {
		try {
			waitForNotElementEnabled( by, timeout );
		} catch (Throwable e) {
			TestSession.addRunVerificationFailure( Reporter
					.getCurrentTestResult().getMethod().getId(), e );
		}
	}

	protected void verifyNotElementEnabled(final By by) {
		verifyNotElementEnabled(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForChecked(final By by) {
		int timeout;
		try {
			timeout = Integer.parseInt( SeleniumSession.getSession()
					.getProperties()
					.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) );
		} catch (Exception e) {
			timeout = 0;
		}
		waitForChecked( by, timeout );
	}

	protected void waitForChecked(final By by, int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver d) {
						// return
						// isElementPresent(By.id(element.getAttribute("id")));
						try {
							return driver.findElement( by )
									.getAttribute( "checked" ).equals( "true" );
						} catch (NoSuchElementException e) {
							return false;
						} catch (StaleElementReferenceException ex) {
							return false;
						}
					}
				} );
	}

	protected void waitForElementEnabled(final By by) {
		waitForElementEnabled(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForElementEnabled(final By by, int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver d) {
						try {
							return driver.findElement( by ).isEnabled();
						} catch (NoSuchElementException e) {
							return false;
						} catch (StaleElementReferenceException ex) {
							return false;
						}
					}
				} );
	}

	protected void waitForElementNotPresent(final By by) {
		waitForElementNotPresent(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForElementNotPresent(final By by, int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver d) {
						try {
							return !driver.findElement( by ).isDisplayed();
						} catch (NoSuchElementException e) {
							return true;
						} catch (StaleElementReferenceException ex) {
							return true;
						}
					}
				} );
	}

	protected void waitForElementNotPresent(final String elementId) {
		waitForElementNotPresent(
				elementId,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForElementNotPresent(final String elementId, int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver d) {
						try {
							JavascriptExecutor jay = (JavascriptExecutor) d;
							String scriptLocalizadorJS = "return document.getElementById('" + elementId + "')";
							if (jay.executeScript( scriptLocalizadorJS ) != null)
								if ((((String) jay.executeScript( scriptLocalizadorJS + ".getStyle('visibility')" ))
										.matches( "visible" ) && !((String) jay.executeScript( scriptLocalizadorJS
										+ ".getStyle('display')" )).matches( "none" )))
									return false;
						} catch (Exception ex) {
							return true;
						}
						return true;
					}
				} );
	}

	// 2DO: Método que espera pela mensagem de "carregando" sumir
	// usar elementId = loading-message
	// style="display: none;"
	// (lembrar que às vezes ela sequer aparece)

	protected void waitForElementPresent(final By by) {
		waitForElementPresent(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForElementPresent(final By by, final int timeout) {
		(new WebDriverWait( driver, timeout )).until( new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				try {
					return d.findElement( by ).isDisplayed();
				} catch (NoSuchElementException e) {
					return false;
				} catch (StaleElementReferenceException ex) {
					return false;
				}
			}
		} );
	}

	protected void waitForNotChecked(final By by) {
		waitForNotChecked(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForNotChecked(final By by, int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver d) {
						try {
							return driver.findElement( by )
									.getAttribute( "checked" ).equals( "false" );
						} catch (NoSuchElementException e) {
							return false;
						} catch (StaleElementReferenceException ex) {
							return false;
						}
					}
				} );
	}

	protected void waitForNotElementEnabled(final By by) {
		waitForNotElementEnabled(
				by,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForNotElementEnabled(final By by, int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver d) {
						try {
							return !driver.findElement( by ).isEnabled();
						} catch (NoSuchElementException e) {
							return false;
						} catch (StaleElementReferenceException ex) {
							return false;
						}
					}
				} );
	}

	protected void waitForNotSelectedLabels(final By by, final String[] labels) {
		waitForNotSelectedLabels(
				by,
				labels,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForNotSelectedLabels(final By by, final String[] labels,
			int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						try {
							select = new Select( driver.findElement( by ) );
							List<WebElement> leb = select
									.getAllSelectedOptions();
							Iterator<WebElement> it = leb.iterator();
							int matches = 0;
							while (it.hasNext())
								for (int i = 0; i < labels.length; i++)
									if (it.next().getText().matches( labels[i] ))
										matches++;
							return matches == 0;
						} catch (NoSuchElementException e) {
							return false;
						} catch (StaleElementReferenceException ex) {
							return false;
						} catch (Exception e) {
							return false;
						}
					}
				} );
	}

	protected void waitForNotText(final By by, final String value) {
		waitForNotText(
				by,
				value,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForNotText(final By by, final String value, int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						return !driver.findElement( by ).getText().matches( value );
					}
				} );
	}

	protected void waitForSelectedLabels(final By by, final String[] labels) {
		waitForSelectedLabels(
				by,
				labels,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForSelectedLabels(final By by, final String[] labels,
			int timeout) {
		(new WebDriverWait( driver, timeout ))
				.until( new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						try {
							select = new Select( driver.findElement( by ) );
							List<WebElement> leb = select
									.getAllSelectedOptions();
							Iterator<WebElement> it = leb.iterator();
							int matches = 0;
							while (it.hasNext()) {
								for (int i = 0; i < labels.length; i++) {
									if (it.next().getText().matches( labels[i] )) {
										matches++;
									}
								}
							}
							return matches == labels.length;
						} catch (NoSuchElementException e) {
							return false;
						} catch (StaleElementReferenceException ex) {
							return false;
						} catch (Exception e) {
							return false;
						}
					}
				} );
	}

	protected void waitForText(final By by, final String value) {
		waitForText(
				by,
				value,
				Integer.parseInt( SeleniumSession.getSession().getProperties()
						.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) );
	}

	protected void waitForText(final By by, final String value, int timeout) {
		(new WebDriverWait( driver, timeout )).until( ExpectedConditions.textToBePresentInElement( by, value ) );
	}

	public int getSessionDefaultTimeout() {
		return Integer.valueOf( SeleniumSession.getSession().getProperties()
				.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) );
	}

	protected void sleep(long millis) {
		try {
			Thread.sleep( millis );
		} catch (InterruptedException e) {
			log.info( "Erro ao suspender Thread" );
		}
	}

	/**
	 * 
	 * Metodo para auxiliar o uso de
	 * {@link JavascriptExecutor#executeScript(String, Object...)}
	 * 
	 * @param js
	 * @return
	 */

	protected Object javascript(String js) {
		try {
			JavascriptExecutor j = (JavascriptExecutor) driver;
			return j.executeScript( js );
		} catch (Exception e) {
			Assert.fail( "Erro ao executar o script: \n\n" + js + "\n", e );
			throw new RuntimeException( e );
		}
	}

	public WebElement waitForElementNotStale(final By by) {
		int timeoutMillis = Integer.valueOf( SeleniumSession.getSession().getProperties()
				.getProperty( SeleniumProperties.DEFAULT_TIMEOUT ) ) * 1000;
		Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( timeoutMillis, TimeUnit.MILLISECONDS )
				.pollingEvery( 500, TimeUnit.MILLISECONDS );
		wait.until( new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				try {
					return webDriver.findElement( by ).isDisplayed();
				} catch (StaleElementReferenceException e) {
					return false;
				}
			}
		} );
		return driver.findElement( by );
	}

}
