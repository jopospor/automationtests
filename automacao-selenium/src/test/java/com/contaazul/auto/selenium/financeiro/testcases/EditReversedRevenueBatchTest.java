package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EditReversedRevenueBatchTest extends EditReversedFinanceStatementBatchTest {

	@BeforeClass
	public void setup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "EditReversedRevenueBatchTest@contaazul.com",
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
		return "#REVENUE";
	}

	@Override
	protected String getModuleName() {
		return "Contas a Receber";
	}

}
