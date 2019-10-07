package com.contaazul.auto.selenium.clientes.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.clientes.pages.CheckoutPlansAndPricePage;

public class CheckoutPlanAndPriceLockScreenCPFTest extends CheckoutPlanAndPriceLockScreenTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void ceckoutPlanAndPriceLockScreenTest(String cepNumber, String numeroCasa, String telefone, String email,
			String cpfNumber, String nomeCompleto) {

		CheckoutPlansAndPricePage checkoutPlansAndPricePage = getPaginas()
				.getCheckoutPlansAndPricePage( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"CheckoutPlanAndPriceLockScreenCPFTest@contaazul.com", "12345" );

		step( "faz a configuração da NF validando o campo de CPF" );
		checkoutPlansAndPricePage.clickBtnCpf();
		checkoutPlansAndPricePage.cpfField( cpfNumber );
		checkoutPlansAndPricePage.name( nomeCompleto );

		checkoutPlansAndPricePage.clickSave();

		formNF( cepNumber, numeroCasa, telefone, email, checkoutPlansAndPricePage );

	}

}
