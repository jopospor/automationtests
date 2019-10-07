package com.contaazul.auto.selenium.clientes.testcases;

import java.util.UUID;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.AdminAssistant;
import com.contaazul.auto.selenium.assistants.LoginAssistant;
import com.contaazul.auto.selenium.assistants.ModalAssistant;
import com.contaazul.auto.selenium.assistants.RegisterAssistant;
import com.contaazul.auto.selenium.clientes.pages.AdminPage;
import com.contaazul.auto.selenium.clientes.pages.CheckoutModalSelectYourPlanPage;
import com.contaazul.auto.selenium.clientes.pages.CheckoutPlansAndPricePage;
import com.contaazul.auto.selenium.clientes.pages.RegisterWizardInvoicesPage;
import com.contaazul.auto.selenium.clientes.pages.RegisterWizardPage;

public class CheckoutPlansAndPricesCpfSucessTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void checkoutPlansAndPricesCpfSucessTest(String periodoPlano, String nomeUsuario, String numeroCartao,
			String codSeguranca, String mesValidade, String anoValidade, String cpfNumber, String nomeCompleto,
			String cepNumber, String numeroCasa, String telefone, String email) {

		CheckoutPlansAndPricePage checkoutPlansAndPricePage = getPaginas()
				.getCheckoutPlansAndPricePage( getWebDriver() );
		CheckoutModalSelectYourPlanPage checkoutModalSelectYourPlanPage = getPaginas()
				.getCheckoutModalSelectYourPlanPage( getWebDriver() );
		AdminPage adminPage = getPaginas().getAdminPage( getWebDriver() );

		RegisterAssistant registerAssistant = getAssistants().getRegisterAssistant( getWebDriver() );
		LoginAssistant loginAssistant = getAssistants().getLoginAssistant( getWebDriver() );

		step( "cadastra novo usuário no sistema e seta testes AB" );
		CreateNewUserAndSetABTest( email, adminPage, registerAssistant, loginAssistant );

		step( "Seleciona um plano e periodo e verifica o resumo da compra" );
		checkoutPlansAndPricePage.chooseOtherPlan();
		checkoutModalSelectYourPlanPage.planMicro();
		checkoutPlansAndPricePage.selectPeriodPlan( periodoPlano );

		step( "Faz pagamento via cartão de crédito" );
		checkoutPlansAndPricePage.nameUserCard( nomeUsuario );
		checkoutPlansAndPricePage.creditCardNumber( numeroCartao );
		checkoutPlansAndPricePage.codeCardSecurity( codSeguranca );
		checkoutPlansAndPricePage.expirateDateCard( mesValidade, anoValidade );

		checkPoint( "valores incorretos", "99", checkoutPlansAndPricePage.valuePlan() );
		checkPoint( "valores incorretos", "1x ", checkoutPlansAndPricePage.summarynumberOfInstallments() );
		checkPoint( "valores incorretos", "MICRO", checkoutPlansAndPricePage.ResumePlan() );
		checkoutPlansAndPricePage.paymentCard();

		step( "Preenche os dados para cadastro para nota fiscal" );
		checkoutPlansAndPricePage.clickBtnCpf();
		checkoutPlansAndPricePage.cpfField( cpfNumber );
		checkoutPlansAndPricePage.name( nomeCompleto );
		checkoutPlansAndPricePage.cepField( cepNumber );
		checkoutPlansAndPricePage.nameStreet();
		checkoutPlansAndPricePage.numberHouse( numeroCasa );
		checkoutPlansAndPricePage.neighborhood();
		checkoutPlansAndPricePage.nameCity();
		checkoutPlansAndPricePage.numberPhoneNf( telefone );
		checkoutPlansAndPricePage.emailNF( email );

		checkoutPlansAndPricePage.clickSave();

		getAssistants().getLoginAssistant( getWebDriver() ).logout();

	}

	private void CreateNewUserAndSetABTest(String email, AdminPage adminPage, RegisterAssistant registerAssistant,
			LoginAssistant loginAssistant) {
		String emailRandom = UUID.randomUUID() + "." + email;
		loginAssistant.vaiParaPaginaDeLogin();
		registerAssistant.registerUser( "CheckoutTest@contaazul.com", "ContaAzul", "1234567890", emailRandom, "12345",
				true );

		AdminAssistant adminAssistant = getAssistants().getAdminAssistant( getWebDriver() );
		adminAssistant.goToAdminPageAndAuthenticate();
		adminPage.setSearchField( emailRandom );
		adminPage.clickSearchButton();
		adminPage.clickTestABButtonOnFirstuser( emailRandom );
		adminPage.setABTest( "Checkout de contratação simplificado", "ENABLED - Habilitado" );
		adminPage.setABTest( "Planos disponibilizados",
				"TRIMESTRAL_SEMESTRAL - Trimestral/Semestral: Autônomo, Startup, Micro, Pequeno, Médio" );
		adminPage.clickSaveFeatures();

		loginAssistant.vaiParaPaginaDeLogin();

		RegisterWizardPage registerPage = getPaginas().getRegisterWizardPage( getWebDriver() );
		registerPage.selectServicesAndProducts();
		registerPage.clickProximoButton();

		RegisterWizardInvoicesPage registerWizardInvoicesPage = getPaginas().getRegisterWizardInvoicesPage(
				getWebDriver() );
		registerWizardInvoicesPage.selectProductsAndServices();
		registerWizardInvoicesPage.clickProximoButton();
		registerWizardInvoicesPage.clickProximoButton();
		registerWizardInvoicesPage.clickProximoButton();

		ModalAssistant modalAssistant = getAssistants().getModalAssistant( getWebDriver() );
		registerWizardInvoicesPage.waitForVideo();
		modalAssistant.closeIntroductionVideoPopUp();

		getAssistants().getMenuAssistant( getWebDriver() ).navigateToMyAccount();
	}
}
