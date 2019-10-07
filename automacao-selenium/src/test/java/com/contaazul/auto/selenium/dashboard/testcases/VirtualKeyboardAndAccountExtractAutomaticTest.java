package com.contaazul.auto.selenium.dashboard.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.dashboard.pages.DashboardPage;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateBankAccountPage;
import com.contaazul.auto.selenium.financeiro.pages.VirtualKeyboardPage;

public class VirtualKeyboardAndAccountExtractAutomaticTest extends SeleniumTest {

	@BeforeClass
	public void preparForTesting() {
		BankAccountsPage bankAccountsPage = getPaginas().getPaginaContasBancarias( getWebDriver() );
		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"VirtualKeyboardAndAccountExtractAutomaticTest@contaazul.com", "12345" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Organizar", "Contas bancárias" );

		if (bankAccountsPage.hasItemsListed()) {
			bankAccountsPage.selecAllCheckBoxAccounts();
			bankAccountsPage.excluir();
		}
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void virtualKeyboardAndAccountExtractAutomaticTest(String numberKey0, String numberKey1, String numberKey2,
			String numberKey3,
			String numberKey4, String numberKey5, String numberKey6, String numberKey7, String numberKey8,
			String numberKey9) {

		CreateBankAccountPage createBankAccountPage = getPaginas().getPaginaAdicionarContaBancaria( getWebDriver() );
		DashboardPage dashboardPage = getPaginas().getDashboardPage( getWebDriver() );
		VirtualKeyboardPage virtualKeyboardPage = getPaginas().getVirtualKeyboardPage( getWebDriver() );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		dashboardPage.btnAddAccount();
		createBankAccountPage.setBanco( "Itaú" );
		createBankAccountPage.setNomeDaConta( "Itaú" );
		createBankAccountPage.doNotConfigure();
		createBankAccountPage.inputAccountSynchInformation( "3858", "0101",
				"1" );
		createBankAccountPage.save();
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		checkPoint( "Conta nao criada", "Muito bem!",
				dashboardPage.msgSuscessCreateAccount(), true );
		step( "Salvar configurações e rotarna ao dashboard" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		dashboardPage.btnConfigureExtractAutomatic();
		createBankAccountPage.cpfUser( "07922134916" );
		createBankAccountPage.password();
		virtualKeyboard( numberKey0, numberKey1, numberKey2, numberKey3, numberKey4, numberKey5, numberKey6,
				numberKey7, numberKey8, numberKey9, virtualKeyboardPage );
		createBankAccountPage.confirmPassword();
		virtualKeyboard( numberKey0, numberKey1, numberKey2, numberKey3, numberKey4, numberKey5, numberKey6,
				numberKey7, numberKey8, numberKey9, virtualKeyboardPage );
		createBankAccountPage.save();
		checkPoint( "Conta nao criada", "Muito bem!", dashboardPage.msgSuscessCreateAccount(), true );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		step( "Faz a configuração de extrato automático,volta para o dashboard e valida" );

		createBankAccountPage.telaInicial();
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
		step( " faz logout" );
	}

	private void virtualKeyboard(String numberKey0, String numberKey1, String numberKey2,
			String numberKey3, String numberKey4, String numberKey5, String numberKey6, String numberKey7,
			String numberKey8,
			String numberKey9, VirtualKeyboardPage virtualKeyboardPage) {
		virtualKeyboardPage.click( numberKey0 );
		virtualKeyboardPage.click( numberKey1 );
		virtualKeyboardPage.click( numberKey2 );
		virtualKeyboardPage.click( numberKey3 );
		virtualKeyboardPage.click( numberKey4 );
		virtualKeyboardPage.click( numberKey5 );
		virtualKeyboardPage.click( numberKey6 );
		virtualKeyboardPage.click( numberKey7 );
		virtualKeyboardPage.click( numberKey8 );
		virtualKeyboardPage.click( numberKey9 );
	}

}
