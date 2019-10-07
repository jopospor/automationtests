package com.contaazul.auto;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.contaazul.auto.config.AutomationProperties;

public class DefaultRetryAnalyzer implements IRetryAnalyzer {

	private int maxCount;

	public DefaultRetryAnalyzer() {
		try {
			maxCount = Integer.parseInt( TestSession.getSession()
					.getProperties()
					.getProperty( AutomationProperties.RETRY_POLICY ) );
		} catch (Exception e) {
			Reporter.log(
					"Retry Analyzer não conseguiu obter valor do número de retries. Utilizando o default (0). ",
					true );
			maxCount = 0;
		}
	}

	public boolean retry(ITestResult tr) {
		if (willReturnTrueOnRetry( tr )) {
			return true;
		} else {
			TestSession.resetThreadFailureCount( tr.getMethod().getId() );
			return false;
		}
	}

	public boolean willReturnTrueOnRetry(ITestResult tr) {
		int p = TestSession.getThreadRetryFailureCount( tr.getMethod().getId() );
		return (p <= maxCount);
	}

}
