package com.contaazul.auto.selenium.clientes.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.clientes.pages.CheckoutModalSelectYourPlanPage;
import com.contaazul.auto.selenium.clientes.pages.CheckoutPlansAndPricePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceImportExtractPage;

public class CheckoutPlansAndPricesBilletTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void checkoutPlansAndPricesBilletTest(String periodoPlano, String nomeUsuarioBoleto, String email,
			String numTelefone, String cupom) {

		CheckoutPlansAndPricePage checkoutPlansAndPricePage = getPaginas()
				.getCheckoutPlansAndPricePage( getWebDriver() );
		CheckoutModalSelectYourPlanPage checkoutModalSelectYourPlanPage = getPaginas()
				.getCheckoutModalSelectYourPlanPage( getWebDriver() );
		FinanceImportExtractPage financeImportExtractPage = getPaginas().getPaginaImportacaoExtrato( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"CheckoutPlansAndPricesBilletTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver()
				).navigateToMyAccount();

		step( "seleciona o plano e periodo" );
		checkoutPlansAndPricePage.chooseOtherPlan();
		checkoutModalSelectYourPlanPage.planMicro();
		checkoutPlansAndPricePage.selectPeriodPlan( periodoPlano );

		step( "Digita o cupom e verifica se ele é valido" );
		checkoutPlansAndPricePage.validateCoupon( cupom );
		checkoutPlansAndPricePage.msgCouponValid();

		step( "muda forma de pagamento para boleto e preenche os campos verificando os campos obrigatórios" );
		checkoutPlansAndPricePage.boletPayment();
		checkoutPlansAndPricePage.billetPayment( nomeUsuarioBoleto, email, numTelefone );
		checkoutPlansAndPricePage.confirmAndPrintSlipPayment();

		checkPoint( "Mensagem invalida", "Preencha corretamente o(s) campo(s) obrigatório(s) em destaque",
				financeImportExtractPage.getPopUpResult() );

		step( "faz logout" );
		getAssistants().getLoginAssistant( getWebDriver() ).logout();

	}
}
