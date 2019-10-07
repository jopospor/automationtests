package com.contaazul.auto.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.contaazul.auto.DataDrivenTest;
import com.contaazul.auto.TestSession;
import com.contaazul.auto.config.AutomationProperties;
import com.contaazul.auto.data.DateFormatUtil;

public class RichReporter implements IReporter {

	private static final int UNSTABLE_TEST_RESULT = 4;

	public void generateReport(List<XmlSuite> arquivosDeSuiteXML,
			List<ISuite> suitesExecutadas, String diretorioSaida) {

		StringBuffer out = new StringBuffer();
		Iterator<ISuite> allSuites = suitesExecutadas.iterator();

		int testsPassed = 0;
		int testsFailed = 0;
		long elapsedTimeMins = 0;
		String suiteName = "";
		String suiteStatus = "";
		Map<String, ISuiteResult> allSuiteResults = new HashMap<String, ISuiteResult>();
		Map<String, ISuiteResult> thisSuiteResults;
		long totalAssertsRan = 0;
		while (allSuites.hasNext()) {
			ISuite suite = allSuites.next();
			suiteName += assembleSuiteNameForReport( suiteName, suite );
			suiteStatus = determineSuiteStatus( allSuites, suiteStatus, suite );
			long finishTime = determineSuiteExecutionElapsedTime();
			elapsedTimeMins += (finishTime / 1000 / 60);
			totalAssertsRan += TestSession.getSession().getCheckPointsPerformed();
			totalAssertsRan += TestSession.getSession().getCheckPointsPerformed();
			thisSuiteResults = aggregateSuiteToGlobalTestResults( allSuiteResults, suite );
			for (ISuiteResult sr : thisSuiteResults.values()) {
				ITestContext tc = sr.getTestContext();
				testsPassed += tc.getPassedTests().getAllResults().size();
				testsFailed += tc.getFailedTests().getAllResults().size();
			}
		}
		String mins = (elapsedTimeMins > 1) ? " mins." : " min.";
		String elapsedTime = String.valueOf( elapsedTimeMins ) + mins;

		out.append( "{" );
		out.append( "\n" );
		out.append( "\"testSuiteName\":\"" + suiteName + "\"," );
		out.append( "\n" );
		out.append( "\"testSuiteStatus\":\"" + suiteStatus + "\"," );
		out.append( "\n" );
		out.append( "\"testSuiteExecutionDate\":\"" + DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE )
				+ "\"," );
		out.append( "\"testSuiteStatus\":\"" + suiteStatus + "\"," );
		out.append( "\n" );
		out.append( "\"testSuiteElapsedTime\":\"" + elapsedTime + "\"," );
		out.append( "\n" );
		out.append( "\"testSuiteAssertsRan\":\"" + totalAssertsRan + "\"," );
		out.append( "\n" );

		out.append( "\"totalTestRuns\":\"" + testsPassed + " testes passados e " + testsFailed + " falhados\"," );
		out.append( "\n" );
		out.append( "\"testCases\":[" );
		out.append( "\n" );

		Set<String> suiteResultsKeys = allSuiteResults.keySet();
		Iterator<String> iteraSuiteResultKeys = suiteResultsKeys.iterator();
		while (iteraSuiteResultKeys.hasNext()) {
			String chave = iteraSuiteResultKeys.next();
			ISuiteResult testResult = allSuiteResults.get( chave );
			ITestNGMethod[] n = testResult.getTestContext()
					.getAllTestMethods();
			String listOfTests = "";
			for (int i = 0; i < n.length; i++) {
				if (!listOfTests.contains( testResult.getTestContext().getName() )) {
					listOfTests += testResult.getTestContext().getName() + ";";
					buildTestReportElement( out, iteraSuiteResultKeys, testResult, n, i );
				}
			}
		}
		out.append( "]" );
		out.append( "\n" );
		if (allSuites.hasNext())
			out.append( "," );

		out.append( "}" );
		out.append( "\n" );

		FileOutputStream ff;
		String path = null;
		try {
			path = System.getProperty( "user.dir" )
					+ TestSession.getSession().getProperties().getProperty( AutomationProperties.REPORT_OUTPUT_DIR )
					+ "relatorio_de_testes.json";
			File file = new File( path );
			if (!file.exists())
				file.createNewFile();
			ff = new FileOutputStream( file );
			ff.write( out.toString().getBytes() );
			ff.flush();
			ff.close();
		} catch (FileNotFoundException e) {
			Reporter.log( "Não foi possível produzir o relatório de testes. Destino do arquivo json era:"
					+ path
					+ ". "
					+ e.getMessage()
					+ e.getStackTrace().toString(), true );
		} catch (IOException e) {
			Reporter.log( "Não foi possível produzir o relatório de testes. Erro ao escrever arquivo json para:"
					+ path + e.getMessage() + e.getStackTrace().toString(), true );
		}
	}

	private Map<String, ISuiteResult> aggregateSuiteToGlobalTestResults(Map<String, ISuiteResult> allSuiteResults,
			ISuite suite) {
		Map<String, ISuiteResult> thisSuiteResults;
		thisSuiteResults = suite.getResults();
		Iterator<ISuiteResult> resultsItems = thisSuiteResults.values().iterator();
		while (resultsItems.hasNext()) {
			allSuiteResults.put( UUID.randomUUID().toString(), (ISuiteResult) resultsItems.next() );
		}
		return thisSuiteResults;
	}

	private long determineSuiteExecutionElapsedTime() {
		return System.currentTimeMillis() - TestSession.getSession().getSessionStartTimeMillis();
	}

	private String assembleSuiteNameForReport(String suiteName, ISuite suite) {
		return ((suiteName.isEmpty()) ? "" : " e ") + suite.getName();
	}

	private String determineSuiteStatus(Iterator<ISuite> allSuites, String suiteStatus, ISuite suite) {
		suiteStatus = (suite.getSuiteState().isFailed() ? "Failed" : suiteStatus.matches( "Failed" ) ? "Failed"
				: "Passed");
		suiteStatus += allSuites.hasNext() ? " | " : "";
		return suiteStatus;
	}

	private void buildTestReportElement(StringBuffer out, Iterator<String> iteraSuiteResultKeys,
			ISuiteResult testResult, ITestNGMethod[] n, int i) {

		ITestNGMethod met = n[i];
		String path = "No external file data loaded.";
		String sheet = "";
		if (((DataDrivenTest) met.getInstances()[0]).getTestDataFilePathAsPassedByTestSuite() != null)
			path = ((DataDrivenTest) met.getInstances()[0]).getTestDataFilePath();
		if (((DataDrivenTest) met.getInstances()[0]).getTestDataSheetName() != null)
			sheet = ((DataDrivenTest) met.getInstances()[0]).getTestDataSheetName();
		out.append( "{" );
		out.append( "\n" );
		out.append( "\"testCaseName\":\"" + testResult.getTestContext().getName() + "\"," );
		out.append( "\n" );
		out.append( "\"testCaseDataProvider\":\"Test data file: " + path );
		out.append( "\"," );
		out.append( "\n" );
		out.append( "\"testCaseParameters\":\"" );
		if (!sheet.isEmpty() && !path.toLowerCase().endsWith( ".txt" )) {
			out.append( "Data sheet name:" + sheet );
		}
		out.append( "\"," );
		out.append( "\n" );

		IResultMap passed = testResult.getTestContext().getPassedTests();
		IResultMap failed = testResult.getTestContext().getFailedTests();

		int j = 1;

		List<String> orderedRuns = new ArrayList<String>();

		Iterator<ITestResult> iteraPassados = passed.getAllResults().iterator();
		while (iteraPassados.hasNext()) {
			ITestResult r = iteraPassados.next();
			StringBuffer testRunResult = writeTestResult( r, String.valueOf( j++ ) );
			orderedRuns.add( testRunResult.toString() );
		}

		boolean realStatus = true;
		if (testResult.getTestContext().getFailedTests().size() > 0) {
			realStatus = isFailedTestUnstable( failed.getAllResults().iterator(), passed
					.getAllResults().iterator(),
					realStatus );
		}

		String testCaseStatus = testResult.getTestContext()
				.getFailedTests().size() > 0 ?
				realStatus ? "Unstable" : "Failed"
				: "Passed";

		out.append( "\"testCaseStatus\":\"" + testCaseStatus + "\"," );
		out.append( "\n" );
		out.append( "\"run\":[" );
		out.append( "\n" );

		Collections.sort( orderedRuns, new TestResultComparator<String>() );

		Iterator<ITestResult> iteraFalhados = failed.getAllResults().iterator();
		iteraPassados = passed.getAllResults().iterator();
		Iterator<String> iteraPassadosOrdenados = orderedRuns.iterator();
		while (iteraPassadosOrdenados.hasNext()) {
			out.append( iteraPassadosOrdenados.next() );
			if (iteraPassadosOrdenados.hasNext())
				out.append( "," );
			else if (iteraFalhados.hasNext())
				out.append( "," );
		}

		orderedRuns = new ArrayList<String>();
		while (iteraFalhados.hasNext()) {
			StringBuffer testRunResult = writeTestResult( iteraFalhados.next(), String.valueOf( j++ ) );
			orderedRuns.add( testRunResult.toString() );
		}

		Collections.sort( orderedRuns, new TestResultComparator<String>() );

		Iterator<String> iteraFalhadosOrdenados = orderedRuns.iterator();
		while (iteraFalhadosOrdenados.hasNext()) {
			out.append( iteraFalhadosOrdenados.next() );
			if (iteraFalhadosOrdenados.hasNext())
				out.append( "," );
		}

		out.append( "]}" );
		out.append( "\n" );
		if (iteraSuiteResultKeys.hasNext())
			out.append( "," );
	}

	private boolean isFailedTestUnstable(Iterator<ITestResult> iteraPassados,
			Iterator<ITestResult> iteraFalhados, boolean realTestStatus) {
		while (iteraFalhados.hasNext()) {
			ITestResult failedRunInstance = iteraFalhados.next();
			String testDataFailed = assembleRunExecutionResult( failedRunInstance );
			realTestStatus = false;
			while (iteraPassados.hasNext()) {
				ITestResult passedRunInstance = iteraPassados.next();
				if (testDataFailed.isEmpty()) {
					if (failedRunInstance.getMethod().equals( passedRunInstance.getMethod() )) {
						realTestStatus = true;
						failedRunInstance.setStatus( UNSTABLE_TEST_RESULT );
						break;
					}
				} else {
					String testDataPassed = assembleRunExecutionResult( passedRunInstance );
					if (testDataFailed.matches( testDataPassed )) {
						realTestStatus = true;
						failedRunInstance.setStatus( UNSTABLE_TEST_RESULT );
						break;
					}
				}
			}
		}
		return realTestStatus;
	}

	protected StringBuffer writeTestResult(ITestResult resTest, String row) {
		StringBuffer resultOut = new StringBuffer( "" );
		String runStatus = resTest.isSuccess() ? "Passed" : resTest.getStatus() == UNSTABLE_TEST_RESULT ? "Unstable"
				: "Failed";

		String testData = "";
		for (int j = 0; j < resTest.getParameters().length; j++) {
			testData += cleanParams( (String) resTest.getParameters()[j] );
			testData += ((j < resTest.getParameters().length - 1) ? ", " : ".");
		}
		resultOut.append( "{" );
		resultOut.append( "\n" );

		testData = cleanParams( testData );
		Boolean temLinha = testData.matches( "Linha [0-9]+.*" );
		String substrDaLinha;
		if (temLinha)
			substrDaLinha = testData.substring( testData.indexOf( "Linha" ),
					testData.indexOf( ":", testData.indexOf( "Linha" ) ) );
		else
			substrDaLinha = resTest.getMethod().getMethodName();// row;

		resultOut.append( "\"rowNumber\":\"" + substrDaLinha + "\"," );
		resultOut.append( "\n" );
		resultOut.append( "\"testData\":\"" + testData + "\"," );
		resultOut.append( "\n" );
		resultOut.append( "\"runStatus\":\"" + runStatus + "\"," );
		resultOut.append( "\n" );
		resultOut.append( "\"steps\":[" );
		resultOut.append( "\n" );

		List<String> stepList = Reporter.getOutput( resTest );
		Iterator<String> stepIt = stepList.iterator();
		while (stepIt.hasNext()) {
			String stepMsg = stepIt.next();
			if (stepMsg.matches( "^\\{{1}.+\\}{1}$" ))
				resultOut.append( stepMsg );
			else {
				// Entrada texto simples usando Reporter.log
				String step = "{";
				step += "\"info\":\"" + cleanParams( stepMsg ) + "\",";
				step += "\"stepStatus\":\"Step\"";
				step += "}";
				resultOut.append( step );
			}
			if (stepIt.hasNext()) {
				resultOut.append( "," );
			}
		}

		// FOR dos steps
		// 2DO = Criar uma coleção chaveada na instância de teste com entradas
		// para cada vez que se deu STEP

		Throwable ex = resTest.getThrowable();
		if (ex != null) {
			// se tiver mais steps
			if (!stepList.isEmpty())
				resultOut.append( "," );
			resultOut.append( "{" );
			resultOut.append( "\n" );
			resultOut.append( "\"info\":\"" + cleanParams( ex.getMessage() ) + "\"," );
			resultOut.append( "\n" );
			resultOut.append( "\"stepStatus\":\"Failed\"" );
			resultOut.append( "}" );
		}
		resultOut.append( "\n" );
		resultOut.append( "]" );
		resultOut.append( "\n" );
		resultOut.append( "}" );
		return resultOut;
	}

	private String assembleRunExecutionResult(ITestResult resTest) {
		String testData = "";
		for (int j = 0; j < resTest.getParameters().length; j++) {
			testData += cleanParams( (String) resTest.getParameters()[j] );
			testData += ((j < resTest.getParameters().length - 1) ? ", " : ".");
		}
		return cleanParams( testData );
	}

	public String cleanParams(String in) {
		if (in != null)
			return in.replace( "\n", "" ).replace( "\"", "" ).replace( "\\", "\\\\" )
					.replace( "&", "" ).replace( "<", "" ).replace( ">", "" );
		else
			return in;
	}
}
