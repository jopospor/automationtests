package com.contaazul.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.TestNGException;

public class DefaultListener extends TestListenerAdapter {

	static private Map<String, Integer> retryFailulesMap = new HashMap<String, Integer>();

	@Override
	public void onStart(ITestContext testContext) {
		ITestNGMethod[] methods = testContext.getAllTestMethods();
		List<String> classes_chamadas = new ArrayList<String>();

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isTest()) {
				if (!classes_chamadas.contains( methods[i].getClass()
						.getCanonicalName() + "#" + methods[i].getMethodName() ))
					classes_chamadas.add( methods[i].getClass()
							.getCanonicalName()
							+ "#"
							+ methods[i].getMethodName() );
				else {
					String msgErroTestesPorClasse = "Para utilizar o FrameWork Data Driven, uma Classe (Caso de Teste) não pode ter mais que uma anotação @Test, nem mais que uma anotação @DataProvider. Erro ocorreu na Classe:";
					Reporter.log(
							msgErroTestesPorClasse
									+ classes_chamadas.add( methods[i]
											.getClass().getCanonicalName() ),
							true );
					throw new TestNGException( msgErroTestesPorClasse
							+ classes_chamadas.add( methods[i].getClass()
									.getCanonicalName() ) );
				}
			}
		}
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		tr.getMethod().setRetryAnalyzer( new DefaultRetryAnalyzer() );
		Boolean retry = ((DefaultRetryAnalyzer) tr.getMethod().getRetryAnalyzer()).willReturnTrueOnRetry( tr );
		if (retry) {
			increaseSkippedCount( tr.getMethod().getId() + tr.getMethod().toString() );
		} else {
			// Testes vêm aqui para morrer.
			if (tr.getThrowable() != null && !tr.getThrowable().getClass().equals( TestNGException.class ))
				for (int h = 0; h < tr.getMethod().getInstances().length; h++) {
					DataDrivenTest cast = (DataDrivenTest) tr.getMethod().getInstances()[h];
					// Chama função de callback, pode ou não estar implementada
					cast.disasterRecovery( cast, tr.getThrowable() );
				}
		}
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		TestSession.resetThreadFailureCount( tr.getMethod().getId() );
	}

	private void increaseSkippedCount(String string) {
		if (!retryFailulesMap.containsKey( string ))
			retryFailulesMap.put( string, 1 );
		else
			retryFailulesMap.put( string, retryFailulesMap.get( string ) + 1 );
	}

	public static int getFailedSkippedCount(String string) {
		if (retryFailulesMap.containsKey( string ))
			return retryFailulesMap.get( string );
		else
			return 0;
	}
}
