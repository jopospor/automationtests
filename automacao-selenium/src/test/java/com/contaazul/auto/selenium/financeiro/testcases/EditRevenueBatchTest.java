package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EditRevenueBatchTest extends EditFinanceStatementBatchTest {

	@BeforeClass
	public void setup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "EditRevenueBatchTest@contaazul.com", "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void editRevenueBatchTest(String incomeName, String value, String bankAccount, String expirationDate,
			String incomeCategory, String received, String repeat, String customer, String competenceDate,
			String costCenter, String observations, String attachment) {

		editFinanceStatementBatchTest( incomeName, value, bankAccount, expirationDate, incomeCategory, received,
				repeat, customer, competenceDate, costCenter, observations, attachment );
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	@Override
	protected String getModuleName() {
		return "Contas a Receber";
	}

	@Override
	protected String getStatementType() {
		return "#REVENUE";
	}
}
