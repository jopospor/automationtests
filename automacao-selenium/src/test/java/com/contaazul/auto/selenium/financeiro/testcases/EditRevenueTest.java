package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EditRevenueTest extends EditFinanceStatementTest {

	@BeforeClass
	public void setup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "EditRevenueTest@contaazul.com", "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void editRevenueTest(String incomeName, String value, String bankAccount, String expirationDate,
			String incomeCategory, String received, String repeat, String customer, String competenceDate,
			String costCenter, String observations, String attachment) {

		editFinanceStatementTest( incomeName, value, bankAccount, expirationDate, incomeCategory, received,
				repeat, customer, competenceDate, costCenter, observations, attachment );
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
