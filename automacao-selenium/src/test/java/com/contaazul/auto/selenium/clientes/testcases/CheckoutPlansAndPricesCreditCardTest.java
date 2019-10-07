package com.contaazul.auto.selenium.clientes.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.clientes.pages.CheckoutPlansAndPricePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceImportExtractPage;

public class CheckoutPlansAndPricesCreditCardTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void checkoutPlansAndPricesCreditCardTest(String periodoPlano, String nomeUsuario, String numeroCartao,
			String codSeguranca, String mesValidade, String anoValidade, String cupom) {

		FinanceImportExtractPage financeImportExtractPage = getPaginas().getPaginaImportacaoExtrato( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"CheckoutPlansAndPricesCreditCardTest2@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver()
				).navigateToMyAccount();
		CheckoutPlansAndPricePage checkoutPlansAndPricePage = getPaginas()
				.getCheckoutPlansAndPricePage( getWebDriver() );

		step( "seleciona o periodo" );
		checkoutPlansAndPricePage.selectPeriodPlan( periodoPlano );

		step( "Digita o cupom e verifica se ele é invalido" );
		checkoutPlansAndPricePage.validateCoupon( cupom );
		checkoutPlansAndPricePage.msgCouponInvalid();

		step( "pagamento via cartão de crédito e faz veriricação de campos obrigatorios " );
		checkoutPlansAndPricePage.nameUserCard( nomeUsuario );
		checkoutPlansAndPricePage.creditCardNumber( numeroCartao );
		checkoutPlansAndPricePage.codeCardSecurity( codSeguranca );

		checkoutPlansAndPricePage.expirateDateCard( mesValidade, anoValidade );
		checkoutPlansAndPricePage.paymentCard();
		checkPoint( "Mensagem invalida", "Preencha corretamente o(s) campo(s) obrigatório(s) em destaque",
				financeImportExtractPage.getPopUpResult() );

		step( "faz logout" );
		getAssistants().getLoginAssistant( getWebDriver() ).logout();

	}
}
