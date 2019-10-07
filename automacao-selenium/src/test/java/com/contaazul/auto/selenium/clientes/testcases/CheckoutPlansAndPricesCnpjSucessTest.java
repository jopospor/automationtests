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

public class CheckoutPlansAndPricesCnpjSucessTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void checkoutPlansAndPricesCnpjSucessTest(
			String periodoPlano,
			String cnpjNumber,
			String razaoSocial,
			String cepNumber,
			String numeroCasa,
			String telefone,
			String email,
			String tipoEmpresa,
			String emiteNota,
			String nomeUsuarioBoleto,
			String numTelefone
			) {

		CheckoutModalSelectYourPlanPage checkoutModalSelectYourPlanPage = getPaginas()
				.getCheckoutModalSelectYourPlanPage( getWebDriver() );
		CheckoutPlansAndPricePage checkoutPlansAndPricePage = getPaginas()
				.getCheckoutPlansAndPricePage( getWebDriver() );

		AdminPage adminPage = getPaginas().getAdminPage( getWebDriver() );
		RegisterAssistant registerAssistant = getAssistants().getRegisterAssistant( getWebDriver() );
		LoginAssistant loginAssistant = getAssistants().getLoginAssistant( getWebDriver() );

		step( "cadastra novo usuário no sistema e seta testes AB" );
		CreateNewUserAndSetABTest( email, adminPage, registerAssistant, loginAssistant );

		step( "Seleciona um plano e periodo e verifica o resumo " );
		checkoutPlansAndPricePage.chooseOtherPlan();
		checkoutModalSelectYourPlanPage.planStartup();
		checkoutPlansAndPricePage.selectPeriodPlan( periodoPlano );

		step( "muda forma de pagamento para boleto e preenche os campos verificando os campos obrigatórios" );
		checkoutPlansAndPricePage.boletPayment();
		checkoutPlansAndPricePage.billetPayment( nomeUsuarioBoleto, email, numTelefone );
		checkPoint( "valores incorretos", "246", checkoutPlansAndPricePage.valuePlan() );
		checkPoint( "valores incorretos", "1x ", checkoutPlansAndPricePage.summarynumberOfInstallments() );
		checkPoint( "valores incorretos", "STARTUP", checkoutPlansAndPricePage.ResumePlan() );

		checkoutPlansAndPricePage.confirmAndPrintSlipPayment();

		step( "faz a configuração da NF validando o campo de CNPJ" );
		checkoutPlansAndPricePage.clickBtnCnpj();
		checkoutPlansAndPricePage.cnpjField( cnpjNumber );
		checkoutPlansAndPricePage.corporateName( razaoSocial );
		formNF( cepNumber, numeroCasa, telefone, email, checkoutPlansAndPricePage );

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

	protected void registerUser(String tipoEmpresa, String emiteNota) {
		RegisterWizardPage wizz = getPaginas().getRegisterWizardPage(
				getWebDriver() );
		if (tipoEmpresa.matches( "Produtos e Servicos" )) {
			wizz.selectServicesAndProducts();
			wizz.clickProximoButton();
			RegisterWizardInvoicesPage regInvoices = getPaginas().getRegisterWizardInvoicesPage( getWebDriver() );

			if (emiteNota.matches( "NF-e e NFS-e" )) {
				regInvoices.selectProductsAndServices();
				regInvoices.clickProximoButton();
			}

			regInvoices.clickProximoButton();
			regInvoices.clickProximoButton();
		}
	}

	private void formNF(String cepNumber, String numeroCasa, String telefone, String email,
			CheckoutPlansAndPricePage checkoutPlansAndPricePage) {
		checkoutPlansAndPricePage.cepField( cepNumber );
		checkoutPlansAndPricePage.nameStreet();
		checkoutPlansAndPricePage.numberHouse( numeroCasa );
		checkoutPlansAndPricePage.neighborhood();
		checkoutPlansAndPricePage.nameCity();
		checkoutPlansAndPricePage.numberPhoneNf( telefone );
		checkoutPlansAndPricePage.emailNF( email );
	}
}
