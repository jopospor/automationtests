package com.contaazul.auto.selenium.clientes.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.clientes.pages.CheckoutPlansAndPricePage;

public abstract class CheckoutPlanAndPriceLockScreenTest extends SeleniumTest {

	public CheckoutPlanAndPriceLockScreenTest() {
		super();
	}

	@BeforeClass
	public void startup() {

	}

	protected void formNF(String cepNumber, String numeroCasa, String telefone, String email,
			CheckoutPlansAndPricePage checkoutPlansAndPricePage) {
		checkoutPlansAndPricePage.cepField( cepNumber );
		checkoutPlansAndPricePage.nameStreet();
		checkoutPlansAndPricePage.numberHouse( numeroCasa );
		checkoutPlansAndPricePage.neighborhood();
		checkoutPlansAndPricePage.nameCity();
		checkoutPlansAndPricePage.numberPhoneNf( telefone );
		checkoutPlansAndPricePage.emailNF( email );
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

}
