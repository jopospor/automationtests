package com.contaazul.auto.report;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestNGException;

import com.contaazul.auto.DefaultListener;
import com.contaazul.auto.TestSession;

public class DefaultReportListener implements IInvokedMethodListener {

	protected static List<String> alreadyLoaded = new ArrayList<String>();

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			synchronized (method) {
				if (alreadyLoaded.contains( method.getTestMethod().getId() )) {
					alreadyLoaded.remove( method.getTestMethod().getId() );
				}
			}
			;
		}
	}

	/**
	 * Após a execução de um teste, inclui as falhas soft que tenha encontrado
	 * atenção porque se um mesmo TESTE é rodado múltiplas vezes, ele vai passar
	 * aqui várias vezes (uma vez cada invocação)
	 */

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

		Reporter.setCurrentTestResult( testResult );

		if (method.isTestMethod()) {

			List<Throwable> verificationFailures = TestSession
					.getRunVerificationFailures( method.getTestMethod().getId() );
			boolean existeSoft = verificationFailures.size() > 0;
			boolean existeHard = testResult.getThrowable() != null;

			if (existeSoft) {
				String reportMessage;
				Throwable mainFailure;
				// se há softs, teste falha
				testResult.setStatus( ITestResult.FAILURE );

				if (existeHard) {
					mainFailure = testResult.getThrowable();

					reportMessage = multipleFailuresMessages( mainFailure,
							verificationFailures );
					// existe soft E hard: multiplas
				} else {
					// existem somente uma ou mais soft

					// joga a primeira pro Throwable
					testResult.setThrowable( verificationFailures.get( 0 ) );
					verificationFailures.remove( 0 );
					// e reporta
					mainFailure = testResult.getThrowable();
					reportMessage = multipleFailuresMessages( mainFailure,
							verificationFailures );
				}

				Throwable merged = new TestNGException( reportMessage );
				testResult.setThrowable( merged );

				// limpa as falhas do método para a próxima execução dele
				TestSession.resetThreadVerificationFailures( method
						.getTestMethod().getId() );
			}
		}

		// altera o resultado do teste para incluir a importante infomação da
		// Linha executada
		if (method.isTestMethod()
				&& !alreadyLoaded.contains( method.getTestMethod().getId() )) {

			alreadyLoaded.add( method.getTestMethod().getId() );

			if (testResult.getStatus() == ITestResult.FAILURE) {
				TestSession.addThreadRetryFailure( method.getTestMethod()
						.getId(), testResult.getThrowable() );
			}

			int times = method.getTestMethod().getCurrentInvocationCount()
					- DefaultListener.getFailedSkippedCount( method
							.getTestMethod().getId()
							+ method.getTestMethod().toString() );

			Object[] realParams = testResult.getParameters();
			if (realParams.length > 0)
				realParams[0] = "Linha " + times + "\": \"" + realParams[0];
			testResult.setParameters( realParams );
		}
	}

	private String multipleFailuresMessages(Throwable mainFailure,
			List<Throwable> verificationFailures) {

		String failureMessage = "";
		if (verificationFailures.size() > 0) {
			// multiplos resultados
			// constrói uma mensagem de falha que reporte todas as falhas e
			// stack traces

			int totalFalhas = mainFailure == null ? verificationFailures.size()
					: verificationFailures.size() + 1;
			int temPrincipal = mainFailure == null ? 0 : 1;

			failureMessage = new String( "Múltiplas falhas ocorreram ("
					+ totalFalhas + ")\n" );

			if (mainFailure != null) {
				failureMessage += "Falha 1 de " + totalFalhas + ":"
						+ mainFailure.getMessage() + ".\n";
				failureMessage += mainFailure.getStackTrace()[1].toString()
						+ "\n";
			}
			ListIterator<Throwable> it = verificationFailures.listIterator();
			int i = 0;
			while (it.hasNext()) {
				i++;
				Throwable t = it.next();
				int aux = i + temPrincipal;
				failureMessage += "Falha " + aux + " de " + totalFalhas + ":"
						+ t.getMessage() + ".\n";
				failureMessage += t.getStackTrace()[1].toString() + "\n";
			}

			failureMessage += "Fim das falhas.\n";
		} else {

			failureMessage += mainFailure.getMessage();
			failureMessage += mainFailure.getStackTrace()[1].toString() + "\n";

		}

		return failureMessage;
	}
}
