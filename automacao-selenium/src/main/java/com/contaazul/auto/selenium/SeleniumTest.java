package com.contaazul.auto.selenium;

//Classe base para todos os Testes (Fixtures)

import java.io.File;

import lombok.extern.log4j.Log4j;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.TestNGException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.internal.thread.TestNGThread;

import com.contaazul.auto.DataDrivenTest;
import com.contaazul.auto.TestSession;
import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.report.RichReporter;
import com.contaazul.auto.selenium.assistants.ContaAzulAssistants;
import com.contaazul.auto.selenium.contaazulpages.ContaAzulPages;

/**
 * Extender esta classe para criar os Testes. Testes contém o fluxo de teste (ou
 * roteiro, ou script de teste). Invocam componentes (Páginas) para realizar as
 * ações de teste. Checam resultados esperados, passam, falham testes.
 * 
 * @author ahulse
 * 
 * @param <T>
 */

@Log4j
public abstract class SeleniumTest extends DataDrivenTest {

	protected static final int VERY_QUICKLY = 100;
	protected static final int QUICKLY = 500;
	protected static final int FOR_A_LONG_TIME = 1000;
	protected static final int VERY_LONG = 2000;
	protected static final int VERY_LONGEST = 5000;
	protected static final int MUCH_LONGER = 20000;
	protected RemoteWebDriver driver;
	private ContaAzulPages paginas;
	private ContaAzulAssistants assistentes = new ContaAzulAssistants();
	private long identifierFileCount = 0;
	private String takeScreenShots = SeleniumSession.getSession().getProperties()
			.getProperty( SeleniumProperties.TAKE_SCREENSHOT );
	public static final String DATA_PROVIDER = "data_provider";

	public SeleniumTest(RemoteWebDriver driver) {
		super();
		this.driver = driver;
	}

	public SeleniumTest() {
		super();
	}

	@BeforeClass
	public void setUpClassSelenium() throws Exception {
		driver = SeleniumSession.getSession().getInstance(
				TestNGThread.currentThread().getId() );
	}

	@AfterSuite
	public void quitAllTests() {
		SeleniumSession.tearDown();
	}

	public synchronized ContaAzulPages getPaginas() {
		return (paginas == null ? new ContaAzulPages() : paginas);
	}

	protected RemoteWebDriver getWebDriver() {
		return driver;
	}

	public void setDriver(RemoteWebDriver driver) {
		this.driver = driver;
	}

	protected String takeScreenShot() {
		String auxFilePath = "";
		String auxFileName = "";
		if (Boolean.valueOf( this.takeScreenShots ) || this.takeScreenShots.matches( "1" )) {
			try {
				auxFileName = generateUniqueImgName() + ".png";
				auxFilePath = System.getProperty( "user.dir" )
						+ SeleniumSession.getSession().getProperties()
								.getProperty( SeleniumProperties.REPORT_OUTPUT_DIR )
						+ "//"
						+ SeleniumSession.getSession().getProperties()
								.getProperty( SeleniumProperties.REPORT_IMAGE_OUTPUT_DIR ) + "//" + auxFileName;
				WebDriver augmentedDriver = new Augmenter().augment( getWebDriver() );
				File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs( OutputType.FILE );
				FileUtils.copyFile( scrFile, new File( auxFilePath ) );
			} catch (Exception e) {
				Reporter.log( "AVISO: Erro ao capturar screenshot: " + e.getMessage(), true );
				return "";
			}
			return SeleniumSession.getSession().getProperties()
					.getProperty( SeleniumProperties.REPORT_IMAGE_OUTPUT_DIR ) + auxFileName;
		} else {
			return "";
		}
	}

	public void fail(String message, boolean softFailure) {
		if (softFailure) {
			message = "TESTE FOI FALHADO - (Soft Failure) - " + message;
			if (getWebDriver() != null)
				logInfo( message, "Failed", takeScreenShot() );
			else
				logInfo( message, "Failed" );
			addVerificationFailure( new TestNGException( message ) );
		} else {
			fail( message );
		}
	}

	public void fail(String message) {
		if (getWebDriver() != null) {
			message = "TESTE FOI FALHADO - " + message;
			logInfo( message, "Failed", takeScreenShot() );
			throw new TestNGException( message );
		} else {
			message = "TESTE FOI FALHADO - " + message;
			logInfo( message, "Failed" );
			throw new TestNGException( message );
		}
	}

	public void step(String message) {
		if (getWebDriver() != null)
			logInfo( "INFO: " + message, "Step", takeScreenShot() );
		else
			Reporter.log( message, true );
	}

	protected void logInfo(String message, String status, String screenShotFilePath) {
		String step = "{";
		step += "\"info\":\"" + new RichReporter().cleanParams( message ) + "\",";
		step += "\"stepStatus\":\"" + status + "\"";
		if (!screenShotFilePath.isEmpty() && screenShotFilePath != null) {
			step += ",";
			step += "\"screenCapture\":\"" + new RichReporter().cleanParams( screenShotFilePath ) + "\"";
		}
		step += "}";
		Reporter.log( step );
	}

	protected void passCheckpoint(String expected, String actual) {
		String outMsg = "CHECKPOINT SUCESSO. Resultado Esperado: ";
		outMsg += (expected == null) ? "null" : expected;
		outMsg += "   Resultado Obtido: ";
		outMsg += (actual == null) ? "null" : actual;
		if (getWebDriver() != null) {
			logInfo( outMsg, "Passed", takeScreenShot() );
		} else {
			logInfo( outMsg, "Passed" );
		}
	}

	private synchronized String generateUniqueImgName() {
		final long count = identifierFileCount++;
		if (identifierFileCount == Long.MAX_VALUE)
			identifierFileCount = 0;
		final String stringCount = 'T' + String.valueOf( System.currentTimeMillis() ) + count;
		return format( stringCount, 12, '0', 'E' );
	}

	private String format(final String value, final int length, final char padd, final char direction) {
		String str = value;
		for (int i = value.length(); i < length; i++) {
			if (direction == 'D')
				str = str + padd;
			else
				str = padd + str;
		}
		return str.substring( 0, length );
	}

	public ContaAzulAssistants getAssistants() {
		return assistentes;
	}

	protected void sleep(int millis) {
		try {
			Thread.sleep( millis );
		} catch (InterruptedException e) {
			Reporter.log( "Erro ao suspender Thread" );
		}
	}

	protected void checkPoint(String message, boolean expected, boolean actual,
			boolean softAssert) {
		checkPoint( message, String.valueOf( expected ), String.valueOf( actual ),
				softAssert );
	}

	protected void checkPoint(String message, boolean expected, boolean actual) {
		checkPoint( message, String.valueOf( expected ), String.valueOf( actual ),
				false );
	}

	protected void checkPoint(String message, String expected, String actual) {
		checkPoint( message, expected, actual, false );
	}

	/**
	 * CheckPoint faz um assert com os valores esperado e obtido, mas também
	 * permite fazer asserções 'soft' (continua execução), escreve no relatório,
	 * e tira screenshot (se houver browser)
	 * 
	 * @param message
	 *            Mensagem utilizada quando o check point falha
	 * @param expected
	 *            Resultado esperado
	 * @param actual
	 *            Resultado obtido
	 * @param softAssert
	 *            Quando true, se falhar marca o teste como falhado mas continua
	 *            a execução
	 */
	protected void checkPoint(String message, String expected, String actual,
			boolean softAssert) {
		TestSession.getSession().increaseCheckpoints();
		// 2DO Chekpoints menos criteriosos, match "inteligente" ao invés do
		// absoluto
		// 2DO CheckPoint com RegEx
		if (expected != null && actual != null) {
			// Se nenhum dos parametros é nulo, compara diretamente
			expected = expected.trim();
			actual = actual.trim();
			if (expected.isEmpty() ? actual.matches( expected ) : actual
					.contains( expected ))
				// São iguais ou real contém esperado
				passCheckpoint( expected, actual );
			else
				// Não são iguais
				failCheckpoint( expected, actual, message, softAssert );
		} else {
			// Ou expected ou actual são nulos
			if (expected == actual)
				passCheckpoint( expected, actual );
			else
				failCheckpoint( expected, actual, message, softAssert );
		}
	}

	protected void failCheckpoint(String expected, String actual, String message,
			boolean softAssert) {
		String outMsg = "CHECKPOINT FALHOU: ";
		outMsg += message;
		outMsg += " Resultado Esperado: ";
		outMsg += (expected == null) ? "null" : expected;
		outMsg += "   Resultado Obtido: ";
		outMsg += (actual == null) ? "null" : actual;
		if (expected != null && actual != null)
			outMsg += "   Diferença: \"" + diff( expected, actual ) + "\"";
		outMsg += ". ";
		fail( outMsg, softAssert );
	}

	/**
	 * Função que compara o texto esperado e o obtido para facilitar leitura dos
	 * resultados do teste
	 * 
	 * @param expected
	 * @param actual
	 * @return diff
	 */

	protected String diff(String expected, String actual) {
		int actualStart = expected.indexOf( actual );
		if (actualStart > -1)
			if (actualStart == 0) {
				return "(...)" + expected.substring( actual.length() );
			} else if (actualStart + actual.length() == expected.length()) {
				return expected.substring( 0, actualStart ) + "(...)";
			} else {
				String matchAntes = expected.substring( 0, actualStart );
				String matchDepois = expected.substring( actualStart
						+ actual.length() );
				return matchAntes + "(...)" + matchDepois;
			}
		else
			return actual;
	}

	/**
	 * Adiciona uma falha na Run corrente e continua o teste
	 * 
	 * @param e
	 */
	protected void addVerificationFailure(Throwable e) {
		TestSession.addRunVerificationFailure( Reporter.getCurrentTestResult()
				.getMethod().getId(), e );
	}

	/**
	 * Falha o teste em caso de haver falhas soft associadas a ele
	 * 
	 * @throws TestNGException
	 */
	protected void assertVerificationErrors() throws TestNGException {
		if (!TestSession.getRunVerificationFailures(
				Reporter.getCurrentTestResult().getMethod().getId() ).isEmpty())
			throw new TestNGException(
					"Falhas encontradas durante a execução do Teste." );
	}

	// Existem 5 tipos de entradas no relatório:
	// 1 - INFO (REporter.log ou step())
	// 2 - CHECKPOINT SUCESSO
	// 3 - CHECKPOINT FALHA
	// 4 - CHECKPOINT FALHA (SOFT)
	// 5 - EXCEÇÃO

	// 2DO: passar o Log Info pra outra classe

	protected void logInfo(String message, String status) {
		String step = "{";
		step += "\"info\":\"" + new RichReporter().cleanParams( message ) + "\",";
		step += "\"stepStatus\":\"" + status + "\"";
		step += "}";
		Reporter.log( step );
	}
}
