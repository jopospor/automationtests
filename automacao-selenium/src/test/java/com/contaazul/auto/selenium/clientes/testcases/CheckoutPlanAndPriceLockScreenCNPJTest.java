package com.contaazul.auto.selenium.clientes.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.clientes.pages.CheckoutPlansAndPricePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceImportExtractPage;

public class CheckoutPlanAndPriceLockScreenCNPJTest extends CheckoutPlanAndPriceLockScreenTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void ceckoutPlanAndPriceLockScreenTest(String cnpjNumber, String razaoSocial, String cepNumber,
			String numeroCasa, String telefone, String email) {

		FinanceImportExtractPage financeImportExtractPage = getPaginas().getPaginaImportacaoExtrato( getWebDriver() );
		CheckoutPlansAndPricePage checkoutPlansAndPricePage = getPaginas()
				.getCheckoutPlansAndPricePage( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"CheckoutPlanAndPriceLockScreenCNPJTest@contaazul.com", "12345" );

		step( "faz a configuração da NF validando o campo de CNPJ" );
		checkoutPlansAndPricePage.clickBtnCnpj();
		checkoutPlansAndPricePage.cnpjField( cnpjNumber );
		checkoutPlansAndPricePage.corporateName( razaoSocial );

		formNF( cepNumber, numeroCasa, telefone, email, checkoutPlansAndPricePage );
		checkoutPlansAndPricePage.clickSave();

		checkPoint( "Mensagem invalida", "Preencha corretamente o(s) campo(s) obrigatório(s) em destaque",
				financeImportExtractPage.getPopUpResult() );

	}
}
