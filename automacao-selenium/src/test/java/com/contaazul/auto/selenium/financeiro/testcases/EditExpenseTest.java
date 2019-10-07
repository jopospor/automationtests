package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EditExpenseTest extends EditFinanceStatementTest {
	@BeforeClass
	public void setup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "EditExpenseTest@contaazul.com", "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void editExpenseTest(String expenseName, String value, String bankAccount, String expirationDate,
			String incomeCategory, String paid, String repeat, String customer, String competenceDate,
			String costCenter, String observations, String attachment) {
		editFinanceStatementTest( expenseName, value, bankAccount, expirationDate, incomeCategory, paid, repeat,
				customer, competenceDate, costCenter, observations, attachment );
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
