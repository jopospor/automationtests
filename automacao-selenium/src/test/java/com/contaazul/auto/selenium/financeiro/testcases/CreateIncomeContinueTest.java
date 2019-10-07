package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;

public class CreateIncomeContinueTest extends SeleniumTest {

	private CreateIncomePage createIncomePage;
	private FinanceFlowPage financeFlowPage;

	@BeforeClass
	public void setup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "createIncomeContinueTest@contaazul.com", "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void createIncomeContinueTest(String incomeName, String value, String bankAccount, String expirationDate,
			String incomeCategory, String received, String repeat, String customer, String competenceDate,
			String costCenter, String observations, String attachment, String keepFields) {
		instantiatePages();

		navigateToAccountsReceivable();

		financeFlowPage.clickAddIncome();

		step( "Preenche formulário Adicionar Receita" );
		createIncomePage.setIncomeName( incomeName );
		createIncomePage.setValue( value );
		createIncomePage.setBankAccount( bankAccount );
		createIncomePage.setExpirationDate( expirationDate );
		createIncomePage.setIncomeCategory( incomeCategory );
		createIncomePage.setReceived( Boolean.parseBoolean( received ) );
		createIncomePage.setRepeat( Boolean.parseBoolean( repeat ) );
		createIncomePage.setCustomer( customer );
		createIncomePage.setCompetenceDate( competenceDate );
		createIncomePage.setCostCenter( costCenter );
		createIncomePage.setObservations( observations );

		bankAccount = createIncomePage.getBankAccount();
		incomeCategory = createIncomePage.getIncomeCategory();
		customer = createIncomePage.getCustomer();
		costCenter = createIncomePage.getCostCenter();

		createIncomePage.clickAddIncomeAndContinue( Boolean.parseBoolean( keepFields ) );

		checkPoint(
				"Não foi apresentada a mensagem de sucesso",
				getSucessMessageExpected( incomeName, value, expirationDate, Boolean.parseBoolean( received ) ),
				createIncomePage.getMessageTopModal(), true );

		createIncomePage.waitTopMessageNotPresent();

		checkPoint( "Título da modal não foi alterado", "Adicionar mais uma Receita", createIncomePage.getModalTitle(),
				true );

		if (Boolean.parseBoolean( keepFields )) {
			Assert.assertTrue( createIncomePage.getIncomeName().equals( incomeName ),
					"Campo Nome da Receita não manteve-se igual." );

			Assert.assertTrue( createIncomePage.getValue().equals( value ), "Campo Valor não manteve-se igual" );

			Assert.assertTrue( createIncomePage.getBankAccount().equals( bankAccount ),
					"Campo Conta Bancária não manteve-se igual." );

			Assert.assertTrue( createIncomePage.getExpirationDate().equals( expirationDate ),
					"Campo Data de Vencimento não manteve-se igual." );

			Assert.assertTrue( createIncomePage.getIncomeCategory().equals( incomeCategory ),
					"Campo Categoria da Receita não manteve-se igual." );

			Assert.assertTrue( createIncomePage.isReceived().equals( Boolean.parseBoolean( received ) ),
					"Campo Situação não manteve-se igual." );

			Assert.assertTrue( createIncomePage.isRepeat().equals( Boolean.parseBoolean( repeat ) ),
					"Campo Recorrência não manteve-se igual." );

			Assert.assertTrue( createIncomePage.getCustomer().equals( customer ), "Campo Cliente não manteve-se igual." );

			Assert.assertTrue( createIncomePage.getCompetenceDate().equals( competenceDate ),
					"Campo Data de competência não manteve-se igual" );

			Assert.assertTrue( createIncomePage.getCostCenter().equals( costCenter ),
					"Campo Centro de Custo não manteve-se igual" );

			Assert.assertTrue( createIncomePage.getObservations().equals( observations ),
					"Campo Observações não manteve-se igual." );
		}

		createIncomePage.clickCancelButton();
	}

	private void navigateToAccountsReceivable() {
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );
	}

	private void instantiatePages() {
		createIncomePage = new CreateIncomePage( getWebDriver() );
		financeFlowPage = new FinanceFlowPage( getWebDriver() );
	}

	public String getSucessMessageExpected(String incomeName, String value, String expirationDate, Boolean received) {
		String msgDayPart = received ? ", no dia " : ", agendado para o dia ";
		String msgTypeTransaction = "Receita '";
		return msgTypeTransaction + incomeName.trim() + "', de R$ " + value.trim() + msgDayPart
				+ expirationDate.trim();
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
