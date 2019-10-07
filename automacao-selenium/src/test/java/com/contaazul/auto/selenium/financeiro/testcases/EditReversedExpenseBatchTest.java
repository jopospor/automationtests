package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EditReversedExpenseBatchTest extends EditReversedFinanceStatementBatchTest {

	@BeforeClass
	public void setup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "EditReversedExpenseBatchTest@contaazul.com",
				"12345" );
	}

	@Test
	public void editReversedRevenueBatchTest() {
		editReversedFinanceStatementBatchTest();
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	@Override
	protected String getStatementType() {
		return "#EXPENSE";
	}

	@Override
	protected String getModuleName() {
		return "Contas a Pagar";
	}

}
