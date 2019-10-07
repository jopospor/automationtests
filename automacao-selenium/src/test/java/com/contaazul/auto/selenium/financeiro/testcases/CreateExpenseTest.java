package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateExpenseTest extends CreateFinanceStatementTest {

	@BeforeClass
	public void setup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "CreateExpenseTest@contaazul.com", "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void createExpenseTest(String expenseName, String value, String bankAccount,
			String expirationDate,
			String incomeCategory, String paid, String repeat, String customer, String competenceDate,
			String costCenter, String observations, String attachment, String $result, String transaction) {

		createFinanceStatementTest( expenseName, value, bankAccount, expirationDate, incomeCategory,
				paid, repeat, customer, competenceDate, costCenter, observations, attachment, $result, transaction );
	}

	@AfterClass
	public void afterClass() {
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

	@Override
	protected void clickAddFinanceStatement() {
		financeFlowPage.navigateToNewExpense();
	}
}
