package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EditExpenseInlineTest extends EditFinanceStatementInlineTest {

	@BeforeClass
	public void setup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "EditExpenseInlineTest@contaazul.com", "12345" );
	}

	@Test
	public void editExpenseInlineTest() {
		editFinanceStatementInlineTest();
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
