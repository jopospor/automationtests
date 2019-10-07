package com.contaazul.auto.selenium.clientes.testcases;

import java.util.Calendar;
import java.util.Random;

import org.testng.annotations.Test;

import br.com.informant.agil.ejb.entity.ContaStatus;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.LoginAssistant;
import com.contaazul.auto.selenium.assistants.MenuAssistant;
import com.contaazul.auto.selenium.assistants.RegisterAssistant;
import com.contaazul.auto.selenium.assistants.UserConfigurationAssistant;
import com.contaazul.auto.selenium.clientes.pages.PlansAndPricingPage;
import com.contaazul.auto.selenium.clientes.pages.RegisterWizardInvoicesPage;
import com.contaazul.auto.selenium.clientes.pages.RegisterWizardPage;

public class PlansAndPricingActivateTest extends SeleniumTest {

	private static final String PREFIX_USER_NAME = "User Plans and Pricing Selenium ";

	private static final String PREFIX_COMPANY_NAME = "Company Plans and Pricing Selenium ";

	private static final String PREFIX_EMAIL = "PlansAndPricingActivateTest";

	private static final String SUFIX_EMAIL = "@contaazul.com";

	private static final String PHONE = "1234697980";

	private static final String COMPANY_CNPJ = "29925726000117";

	private static final String COMPANY_CEP = "89227301";

	private static final int maximumExpirationYear = Calendar.getInstance().get( Calendar.YEAR ) + 8;

	private PlansAndPricingPage plansAndPricingPage;

	private LoginAssistant loginAssistant;

	private UserConfigurationAssistant userConfigurationAssistant;

	@Test(dataProvider = DATA_PROVIDER)
	public void activateByBankslip(String status, String plan, String paymentType, String period, String valueExpected,
			String creditCardNumber, String brandExpected)
			throws Exception {
		ContaStatus contaStatus = ContaStatus.valueOf( status );

		String randomNumber = String.valueOf( new Random().nextInt() );
		String email = PREFIX_EMAIL + randomNumber + SUFIX_EMAIL;
		String password = email;
		String userName = PREFIX_USER_NAME + randomNumber;
		String companyName = PREFIX_COMPANY_NAME + randomNumber;

		userConfigurationAssistant = getAssistants().getUserConfigurationAssistant( getWebDriver() );

		createUser( userName, companyName, email, password );
		if (!contaStatus.equals( ContaStatus.BLOQUEADA ))
			userConfigurationAssistant.updateStatus( email, password, contaStatus );

		boolean isAccountUpdated = false;
		for (int i = 0; i < 10; i++) {
			if (userConfigurationAssistant.isAccountUpdated( email, password )) {
				isAccountUpdated = true;
				break;
			}
			Thread.sleep( 2000 );
		}
		checkPoint( "Não atualizou a conta.", true, isAccountUpdated, true );

		loginAssistant = getAssistants().getLoginAssistant( getWebDriver() );
		plansAndPricingPage = getPaginas().getPlansAndPricingPage( getWebDriver() );

		loginAssistant.logout();
		loginAssistant.login( email, password );
		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();

		if (!contaStatus.equals( ContaStatus.END_TRIAL )) {
			MenuAssistant menu = getAssistants().getMenuAssistant( getWebDriver() );
			menu.navigateToMyAccount();
		}
		activateTrialOrEndTrial( plan, paymentType, period, valueExpected, companyName, userName, creditCardNumber,
				brandExpected );

		if (contaStatus.equals( ContaStatus.BLOQUEADA ))
			activateBlocked( email, password, plan, paymentType, period, valueExpected, userName, creditCardNumber,
					brandExpected );

		loginAssistant.logout();
	}

	private void createUser(String userName, String companyName, String email, String password) {
		RegisterAssistant register = getAssistants().getRegisterAssistant( getWebDriver() );
		register.registerUser( userName, companyName, PHONE, email, password, true );
		completeWizard();
	}

	private void completeWizard() {
		RegisterWizardPage wizzard = getPaginas().getRegisterWizardPage( getWebDriver() );
		wizzard.selectServicesAndProducts();
		wizzard.clickProximoButton();

		RegisterWizardInvoicesPage invoices = getPaginas().getRegisterWizardInvoicesPage( getWebDriver() );
		invoices.selectProductsAndServices();
		invoices.clickProximoButton();
		invoices.clickProximoButton();
		invoices.clickProximoButton();
	}

	private void activateTrialOrEndTrial(String plan, String paymentType, String period, String valueExpected,
			String companyName, String holderName, String creditCardNumber, String brandExpected)
			throws InterruptedException {
		clickButtonPlan( plan );
		populateBilling( companyName );

		if (paymentType.equals( "BANKSLIP" ))
			plansAndPricingPage.clickButtonBankslip();
		else {
			plansAndPricingPage.clickButtonCreditCard();
			populateCreditCard( holderName, creditCardNumber, brandExpected );
			checkPoint( "Não identificou bandeira do cartão.", true,
					plansAndPricingPage.isBrandSelected( brandExpected ), true );
		}

		clickPeriodOption( period );
		activeAccount( plan, period, valueExpected, paymentType );
	}

	private void activateBlocked(String login, String password, String plan, String paymentType, String period,
			String valueExpected, String holderName, String creditCardNumber, String brandExpected) throws Exception {
		userConfigurationAssistant.updateStatus( login, password, ContaStatus.BLOQUEADA );
		loginAssistant.logout();
		loginAssistant.login( login, password );
		if (paymentType.equals( "BANKSLIP" ))
			plansAndPricingPage.clickButtonPrintBankslipBlocked();
		else {
			plansAndPricingPage.clickButtonConfirmCreditCard();
			populateCreditCard( holderName, creditCardNumber, brandExpected );
			activeAccount( plan, period, valueExpected, paymentType );
		}
	}

	private void activeAccount(String plan, String period, String valueExpected, String paymentType)
			throws InterruptedException {
		plansAndPricingPage.clickButtonConfirmPayment();

		Thread.sleep( 3000 );

		checkPoint( "Não mostrou mensagem de sucesso.", true, plansAndPricingPage.isSuccessMessageDisplayed(), true );
		checkPoint( "Não mostrou corretamente o plano contratado.", true,
				plansAndPricingPage.isContractPlanMessagePresent( plan ), true );
		checkPoint( "Não mostrou corretamente o valor contratado.", true,
				plansAndPricingPage.isContractValueMessagePresent( valueExpected ), true );
		checkPoint( "Não mostrou corretamente o período contratado.", true,
				plansAndPricingPage.isContractPeriodMessagePresent( period.equals( "MONTHLY" ) ? "mensalmente"
						: "anualmente" ), true );
		checkPoint( "Não mostrou corretamente a forma de pagamento contratada.", true,
				plansAndPricingPage.isContractTypeMessagePresent( paymentType.equals( "BANKSLIP" ) ? "boleto bancário."
						: "cartão de crédito." ), true );
	}

	private void clickButtonPlan(String plan) {
		if (plan.equals( "PLUS" ))
			plansAndPricingPage.clickButtonPlanPlus();
		else if (plan.equals( "BASIC" ))
			plansAndPricingPage.clickButtonPlanBasic();
		else if (plan.equals( "SOLO" ))
			plansAndPricingPage.clickButtonPlanSolo();
		else
			plansAndPricingPage.clickButtonPlanInitial();
	}

	private void clickPeriodOption(String period) {
		if (period.equals( "ANNUAL" ))
			plansAndPricingPage.clickRadioAnnualOption();
	}

	private void populateBilling(String companyName) {
		plansAndPricingPage.setFieldDocumentNumber( COMPANY_CNPJ );
		plansAndPricingPage.setFieldName( companyName );
		plansAndPricingPage.setFieldCep( COMPANY_CEP );
		plansAndPricingPage.setFieldNumber( "0" );
	}

	private void populateCreditCard(String holderName, String creditCardNumber, String brandExpected) {
		plansAndPricingPage.setFieldHolderName( holderName );
		plansAndPricingPage.setFieldCreditCardNumber( creditCardNumber );
		plansAndPricingPage.setSelectExpirationYear( getExpirationYear() );
		plansAndPricingPage.setSelectExpirationMonth( getExpirationMonth() );
		plansAndPricingPage.setFieldSecurityCode( getSecurityCode( brandExpected ) );
	}

	private String getExpirationMonth() {
		int month = new Random().nextInt( 13 );
		if (month == 0)
			return "01";
		else if (month < 10)
			return "0" + month;
		else
			return String.valueOf( month );
	}

	private String getExpirationYear() {
		return String.valueOf( maximumExpirationYear - new Random().nextInt( 7 ) );
	}

	private String getSecurityCode(String brandExpected) {
		int lengthSecurityCode = 3;
		if (brandExpected.equals( "AMEX" ))
			lengthSecurityCode = 4;

		String code = String.valueOf( new Random().nextInt( 1000 ) );
		for (int count = code.length(); count < lengthSecurityCode; count++)
			code = code.concat( String.valueOf( new Random().nextInt( 10 ) ) );
		return code;
	}
}
