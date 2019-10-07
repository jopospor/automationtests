package com.contaazul.auto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;
import org.testng.Assert;

import com.contaazul.auto.config.AutomationProperties;
import com.contaazul.auto.config.LineReader;
import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.locale.SessionLocale;

public class TestSession {

	protected static TestSession SESSION = null;

	protected Properties sessionProperties = null;

	protected SessionLocale locale;

	private String propEnvironment;

	private long checkPointsPerformed = 0;

	private long startTime = 0;

	private static Map<String, List<Throwable>> verificationFailuresMap = new HashMap<String, List<Throwable>>();

	private static Map<String, List<Throwable>> retryFailedTestsMap = new HashMap<String, List<Throwable>>();

	/**
	 * Carregamento das properties e locale
	 */
	protected TestSession() {
		startTime = System.currentTimeMillis();
		loadTestFrameworkProperties();
		locale = new SessionLocale( sessionProperties.getProperty( AutomationProperties.SESSION_LOCALE ) );
	}

	/**
	 * Retorna o singleton da Sessao
	 * 
	 * @return TestSession
	 */
	public static synchronized TestSession getSession() {
		if (SESSION == null)
			SESSION = new TestSession();
		return SESSION;
	}

	/**
	 * Insere uma falha 'soft' e continua a execução da Run
	 * 
	 * @param methodId
	 *            : Identificador único da Run
	 * @param e
	 *            : Thowable
	 */

	public static synchronized void addRunVerificationFailure(String threadId,
			Throwable e) {

		List<Throwable> aux = verificationFailuresMap.get( threadId );
		if (aux == null)
			aux = new ArrayList<Throwable>();
		aux.add( e );
		verificationFailuresMap.put( threadId, aux );

	}

	/**
	 * Retorna uma lista com todas as falhas soft identificadas pela Run até o
	 * momento
	 * 
	 * @param threadId
	 *            : Identificador único da Run
	 * @return: Lista de exceções
	 */
	public static List<Throwable> getRunVerificationFailures(String threadId) {

		List<Throwable> verificationFailures = verificationFailuresMap
				.get( threadId );
		return verificationFailures == null ? new ArrayList<Throwable>()
				: verificationFailures;

	}

	/**
	 * Em re-execução (re-tentativa), limpa as exceções da tentativa
	 * 
	 * @param threadId
	 *            : Identificador único da Run
	 */

	public static void resetThreadVerificationFailures(String threadId) {

		verificationFailuresMap.remove( threadId );

	}

	/**
	 * Em re-execução (re-tentativa), limpa as Runs anteriores
	 * 
	 * @param threadId
	 */
	public static void resetThreadFailureCount(String threadId) {

		retryFailedTestsMap.remove( threadId );

	}

	/**
	 * Marca o método como falhado para efeito de retentativa
	 * 
	 * @param threadId
	 *            Identificador único da Run
	 * @param e
	 */
	public static void addThreadRetryFailure(String threadId, Throwable e) {
		List<Throwable> aux = retryFailedTestsMap.get( threadId );
		if (aux == null)
			aux = new ArrayList<Throwable>();
		aux.add( e );
		retryFailedTestsMap.put( threadId, aux );
	}

	/**
	 * Retorna a quantidade de tentativas falhadas
	 * 
	 * @param threadId
	 *            Identificador único da Run
	 * @return int
	 */

	public static int getThreadRetryFailureCount(String threadId) {

		List<Throwable> retries = retryFailedTestsMap.get( threadId );
		return (retries != null) ? retries.size() : 0;

	}

	/**
	 * Retorna ref ao objeto Locale da Sessao
	 */

	public SessionLocale getLocale() {
		return locale;
	}

	/**
	 * Retorna propriedades da sessão em um objeto Properties.
	 */
	public synchronized Properties getProperties() {
		if (sessionProperties == null)
			loadTestFrameworkProperties();
		return sessionProperties;
	}

	/**
	 * Carrega props de arquivo
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected synchronized void loadTestFrameworkProperties() {
		if (SystemUtils.USER_NAME.contains( "jenkins" ) || SystemUtils.USER_NAME.contains( "ubuntu" ))
			propEnvironment = "prod.";
		else
			propEnvironment = "local.";

		sessionProperties = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream( System.getProperty( "user.dir" ) + "/src/main/resources/propriedades.properties" );
			load( in );
			in.close();
		} catch (FileNotFoundException e) {
			Assert.fail(
					"Não conseguiu carregar o arquivo de propriedades. Arquivo não encontrado.",
					e );
		} catch (IOException e) {
			Assert.fail(
					"Não conseguiu ler o arquivo de propriedades. Erro de IO.",
					e );
		}
	}

	private void load(InputStream inStream) throws IOException {
		LineReader lr = new LineReader( inStream );
		char[] convtBuf = new char[1024];
		int limit;
		int keyLen;
		int valueStart;
		char firstChar;
		boolean hasSeparator;
		boolean precedingBackslash;

		while ((limit = lr.getLineLen()) >= 0) {
			firstChar = 0;
			keyLen = 0;
			valueStart = limit;
			hasSeparator = false;
			precedingBackslash = false;
			while (keyLen < limit) {
				firstChar = lr.lineBuffer[keyLen];
				if (lr.isCharSeparator( firstChar, precedingBackslash )) {
					valueStart = keyLen + 1;
					hasSeparator = true;
					break;
				} else if (lr.isCharLeadingWhiteSpace( firstChar, precedingBackslash )) {
					valueStart = keyLen + 1;
					break;
				}
				if (firstChar == '\\')
					precedingBackslash = !precedingBackslash;
				else
					precedingBackslash = false;
				keyLen++;
			}
			while (valueStart < limit) {
				if (lr.hasSeparator( valueStart, hasSeparator ))
					hasSeparator = true;
				else
					break;
				valueStart++;
			}
			cleanAndAddProperty( lr, convtBuf, limit, keyLen, valueStart );
		}
		String targetEnvironment = System.getProperty( "application_base_url" );
		if (targetEnvironment != null)
			sessionProperties.put( SeleniumProperties.APPLICATION_BASE_URL, targetEnvironment );
	}

	private void cleanAndAddProperty(LineReader lr, char[] convtBuf, int limit, int keyLen, int valueStart) {
		String key = LineReader.loadConvert( lr.lineBuffer, 0, keyLen, convtBuf );
		String value = LineReader.loadConvert( lr.lineBuffer, valueStart, limit - valueStart, convtBuf );
		if (key.contains( propEnvironment )) {
			sessionProperties.put( key.replace( propEnvironment, "" ), value );
		}
	}

	public synchronized void increaseCheckpoints() {
		checkPointsPerformed++;
	}

	public long getCheckPointsPerformed() {
		return checkPointsPerformed;
	}

	public long getSessionStartTimeMillis() {
		return startTime;
	}

}
