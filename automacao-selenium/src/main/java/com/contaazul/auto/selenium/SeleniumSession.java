package com.contaazul.auto.selenium;

import java.io.File;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.utils.GridHubConfiguration;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.web.Hub;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.TestNGException;
import org.testng.internal.thread.TestNGThread;

import com.contaazul.auto.TestSession;
import com.contaazul.auto.config.SeleniumProperties;

public class SeleniumSession extends TestSession {

	private static HashMap<Integer, RemoteWebDriver> manySeleniums;
	private static SeleniumSession SELENIUM_SESSION;

	/**
	 * Retorna o singleton da sessao
	 * 
	 * @throws Exception
	 */
	protected SeleniumSession() {
		super();
	}

	/**
	 * Retorna o singleton da Sessao
	 * 
	 * @return TestSession
	 */
	public static synchronized SeleniumSession getSession() {
		if (SELENIUM_SESSION == null) {
			SELENIUM_SESSION = new SeleniumSession();
		}
		return SELENIUM_SESSION;
	}

	/**
	 * Retorna uma instância de Web Driver
	 * 
	 * @param threadKey
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public synchronized RemoteWebDriver getInstance(long threadKey)
			throws MalformedURLException, Exception {
		return getInstance( threadKey,
				getProperties().getProperty( SeleniumProperties.DEFAULT_BROWSER ) );
	}

	/**
	 * Retorna uma instância de Web Driver
	 * 
	 * @param threadKey
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public synchronized RemoteWebDriver getInstance(long threadKey, String browserName)
			// 2DO: permitir configurar os browsers e versões com um json
			throws MalformedURLException, Exception {

		if (manySeleniums == null) {
			initManySeleniums();
			if (getProperties().getProperty( SeleniumProperties.GRID_SERVER_BASE_URL ).matches( "localhost" )
					&& willUseGrid()) {
				try {
					startLocalGridHub();
					startLocalGridNode();
				} catch (Exception e) {
					Reporter.log( "Falhou ao inicial um Seleniun GRID no ambiente local. Mensagem: " + e.getMessage(),
							1, true );
				}
			}
		}
		DesiredCapabilities capability = generateDesiredCapabilities( browserName );
		if (!hasDriverOnGrid( threadKey )) {
			if (willUseGrid()) {
				URL u = generateServerURL();
				try {
					waitGridResponse();
					startLocalBrowserOnGrid( threadKey, browserName, capability, u );
				} catch (Exception e) {
					Reporter.log( "Seleniun GRID não respondeu. Mensagem: " + e.getMessage(), 1, true );
					startLocalBrowserWithoutGrid( threadKey, capability );
				}
			} else {
				startLocalBrowserWithoutGrid( threadKey, capability );
			}
		}
		return manySeleniums.get( (int) threadKey );
	}

	private void startLocalBrowserOnGrid(long threadKey, String browserName, DesiredCapabilities capability, URL u) {
		RemoteWebDriver seleniumInstance;
		seleniumInstance = initDriver( capability, u );
		configDriverTimeouts( browserName, seleniumInstance );
		seleniumInstance.setFileDetector( new LocalFileDetector() );
		manySeleniums.put( (int) threadKey, seleniumInstance );
	}

	private RemoteWebDriver startLocalBrowserWithoutGrid(long threadKey, DesiredCapabilities capability)
			throws Exception {
		return manySeleniums.put( (int) threadKey, (RemoteWebDriver) startLocalWebDriver( threadKey, capability ) );
	}

	private boolean hasDriverOnGrid(long threadKey) {
		return manySeleniums.containsKey( (int) threadKey );
	}

	private void configDriverTimeouts(String browserName, RemoteWebDriver seleniumInstance) {
		if (!browserName.equalsIgnoreCase( "ie" )) {
			seleniumInstance.manage()
					.timeouts()
					.pageLoadTimeout(
							Integer.parseInt( getProperties().getProperty(
									SeleniumProperties.PAGE_LOAD_TIMEOUT ) ),
							TimeUnit.SECONDS );
		}
		seleniumInstance.manage()
				.timeouts()
				.implicitlyWait(
						Integer.parseInt( getProperties().getProperty(
								SeleniumProperties.DEFAULT_TIMEOUT ) ),
						TimeUnit.SECONDS );
	}

	private LogEnabledRemoteWebDriver initDriver(DesiredCapabilities capability, URL u) {
		Reporter.log( "Requisitando browser no grid: " + capability.getBrowserName(), true );
		return new LogEnabledRemoteWebDriver( u, capability, isDriverLogEnabled() );
	}

	private boolean isDriverLogEnabled() {
		return getProperties().getProperty(
				SeleniumProperties.LOG_ENABLED ).matches( "1" )
				|| getProperties().getProperty( SeleniumProperties.LOG_ENABLED ).toLowerCase().matches( "true" );
	}

	private void waitGridResponse() throws NumberFormatException, Exception {
		waitSocketResponse(
				getProperties().getProperty(
						SeleniumProperties.GRID_SERVER_BASE_URL ),
				Integer.parseInt( getProperties().getProperty(
						SeleniumProperties.GRID_SERVER_PORT ) ), Integer.parseInt( getProperties().getProperty(
						SeleniumProperties.PAGE_LOAD_TIMEOUT ) ) );
	}

	private DesiredCapabilities generateDesiredCapabilities(String browserName) {
		DesiredCapabilities capability;
		if (browserName.equalsIgnoreCase( "ie" ))
			capability = DesiredCapabilities.internetExplorer();
		else if (browserName.equalsIgnoreCase( "chrome" ))
			capability = DesiredCapabilities.chrome();
		else if (browserName.toLowerCase().contains( "phantom" ))
			capability = DesiredCapabilities.phantomjs();
		else {
			capability = DesiredCapabilities.firefox();
			try {
				capability.setCapability( FirefoxDriver.PROFILE, generateFirefoxProfile() );
			} catch (Exception e) {
				Reporter.log( "Firefox será iniciado com profile padrão.", true );
			}
		}
		return capability;
	}

	private WebDriver startLocalWebDriver(long threadKey, DesiredCapabilities capability) {
		Reporter.log( "Abrindo browser local: " + capability.getBrowserName() );
		WebDriver seleniumInstance;
		if (capability.getBrowserName().toLowerCase().matches( "chrome" ))
			seleniumInstance = new ChromeDriver( capability );
		else if (capability.getBrowserName().toLowerCase().matches( "firefox" ))
			seleniumInstance = new FirefoxDriver( capability );
		else
			throw new TestNGException( "Nome do browser informado não suportado: " + capability.getBrowserName() );
		seleniumInstance.manage().timeouts().implicitlyWait(
				Integer.parseInt( getProperties().getProperty(
						SeleniumProperties.DEFAULT_TIMEOUT ) ), TimeUnit.SECONDS );
		return seleniumInstance;
	}

	private boolean willUseGrid() {
		return !Boolean.parseBoolean( getProperties().getProperty( SeleniumProperties.RUN_LOCALLY_WITHOUT_GRID ) );
	}

	private FirefoxProfile generateFirefoxProfile() {
		return new FirefoxProfile( new File(
				getProperties().getProperty(
						SeleniumProperties.FIREFOX_PROFILE_DIR ) ) );
	}

	private URL generateServerURL() throws MalformedURLException {
		return new URL( "http://"
				+ getProperties().getProperty( SeleniumProperties.GRID_SERVER_BASE_URL )
				+ ":"
				+ getProperties().getProperty( SeleniumProperties.GRID_SERVER_PORT )
				+ "/wd/hub" );
	}

	private void initManySeleniums() {
		if (manySeleniums == null) {
			manySeleniums = new HashMap<Integer, RemoteWebDriver>();
		}
	}

	private synchronized void startLocalGridHub() throws TestNGException {
		GridHubConfiguration gridHubConf = new GridHubConfiguration();
		gridHubConf.setPort( Integer.parseInt( getProperties().getProperty(
				SeleniumProperties.GRID_SERVER_PORT ) ) );
		gridHubConf.setHost( getProperties().getProperty(
				SeleniumProperties.GRID_SERVER_BASE_URL ) );
		Hub hub = new Hub( gridHubConf );
		try {
			hub.start();
		} catch (Exception e) {
			// hub.start throws java.lang.Exception mas Sonar não aprecia
			throw new TestNGException( e );
		}
	}

	private synchronized void startLocalGridNode() throws NumberFormatException, Exception {
		String base = getProperties().getProperty(
				SeleniumProperties.GRID_SERVER_BASE_URL );
		String port = getProperties().getProperty(
				SeleniumProperties.GRID_SERVER_PORT );
		String nodePort = getProperties().getProperty(
				SeleniumProperties.GRID_NODE_PORT );
		String[] args = new String[] { "-role", "node", "-hub",
				"http://" + base + ":" + port + "/grid/register", "-port",
				nodePort };
		RegistrationRequest rr = RegistrationRequest.build( args );
		SelfRegisteringRemote remote = null;
		remote = new SelfRegisteringRemote( rr );
		try {
			remote.startRemoteServer();
		} catch (Exception e) {
			// remote.startRemoteServer throws java.lang.Exception mas Sonar não
			// aprecia
			throw new TestNGException( e );
		}
		remote.startRegistrationProcess();
		waitSocketResponse( base, Integer.parseInt( port ), 5 );
	}

	private synchronized static void waitSocketResponse(String hostname,
			int port, int timeout) throws Exception {
		boolean down = true;
		int attemptsCount = 0;
		while (down) {
			if (attemptsCount >= timeout)
				throw new Exception( "Timeout " + String.valueOf( timeout )
						+ " segundos. Hostname:" + hostname + ". Port:"
						+ String.valueOf( port ) );
			try {
				attemptsCount++;
				Socket socket = new Socket( hostname, port );
				socket.close();
				down = false;
				break;
			} catch (Exception e) {
				Thread.sleep( 1000 );
			}
		}
	}

	/***
	 * Fecha todas as instâncias de WebDriver que estiverem abertas
	 */
	public synchronized static void tearDown() {
		if (manySeleniums != null) {
			Iterator<RemoteWebDriver> iterat = manySeleniums.values()
					.iterator();
			for (int i = 0; manySeleniums.values().size() > i; i++) {
				try {
					RemoteWebDriver w = iterat.next();
					w.close();
				} catch (Exception e) {
					Reporter.log(
							"Selenium Session Aviso: não foi possível encerrar browsers.",
							true );
				}
			}
		}
	}

	public synchronized RemoteWebDriver forceResetBrowser()
			throws MalformedURLException, Exception {
		manySeleniums.get( TestNGThread.currentThread().getId() ).close();
		return getInstance( TestNGThread.currentThread().getId() );
	}
}
