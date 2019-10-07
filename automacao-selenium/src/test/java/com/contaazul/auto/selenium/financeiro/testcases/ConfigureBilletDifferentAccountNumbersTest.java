package com.contaazul.auto.selenium.financeiro.testcases;

import java.util.Date;

import org.testng.annotations.Test;

import com.contaazul.auto.data.DateFormatUtil;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.dashboard.pages.DashboardBankAccountPage;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.ConfigureBankslipPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateBankAccountPage;

public class ConfigureBilletDifferentAccountNumbersTest extends SeleniumTest {

	private static final String ITAU_BANK_NAME = "Itaú";

	@Test(dataProvider = DATA_PROVIDER)
	public void configureBilletDifferentAccountNumbersTest(String banco, String nome, String numberA, String numberB,
			String digitV, String valor, String accountName, String nrAgencia, String nrConta, String valorDoBoleto,
			String carteiras, String dsInstrucao1, String dsInstrucao2, String dsInstrucao3, String dsInstrucao4,
			String dsLocalPagamento1, String dsLocalPagamento2, String isSelected, String accountNumber, String $result) {

		CreateBankAccountPage createBankAccountPage = getPaginas().getPaginaAdicionarContaBancaria( getWebDriver() );
		BankAccountsPage bankAccountsPage = getPaginas().getPaginaContasBancarias( getWebDriver() );
		ConfigureBankslipPage configureBankslipPage = getPaginas().getConfigureBankslipPage( getWebDriver() );
		step( "Faz login e navega para as contas bancárias" );

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"configureBilletDifferentAccountNumbers@contaazul.com", "12345" );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Organizar", "Contas bancárias" );

		if (bankAccountsPage.hasItemsListed()) {
			bankAccountsPage.selecAllCheckBoxAccounts();
			bankAccountsPage.excluir();
		}
		step( "Cria uma nova conta e clica em salvar" );

		bankAccountsPage.clicarNovaConta();
		createBankAccountPage.setBanco( banco );
		createBankAccountPage.setNomeDaConta( nome );

		if (nome.equals( ITAU_BANK_NAME ) && createBankAccountPage.hasItemsImport())
			createBankAccountPage.withoutAutomaticImport();

		createBankAccountPage.setNumberAgency( numberA );
		createBankAccountPage.numberAccount( numberB );
		createBankAccountPage.verificationDigit( digitV );
		createBankAccountPage.setSaldoInicial( valor );
		createBankAccountPage.setDataSaldoInicial( DateFormatUtil.format( new Date(), DateFormatUtil.FULLDATE ) );
		createBankAccountPage.clicarSalvar();
		step( "Seleciona a conta e configura o boleto com um numero da agência novo" );

		bankAccountsPage.clickCheckbox( accountName );
		bankAccountsPage.clicConfigureBankslip();
		configureBankslipPage.setNrAgencia( nrAgencia );
		configureBankslipPage.setNrConta( nrConta );
		configureBankslipPage.setValorDoBoleto( valorDoBoleto );
		configureBankslipPage.setCarteiras( carteiras );
		configureBankslipPage.setDsInstrucao1( dsInstrucao1 );
		configureBankslipPage.setDsInstrucao2( dsInstrucao2 );
		configureBankslipPage.setDsInstrucao3( dsInstrucao3 );
		configureBankslipPage.setDsInstrucao4( dsInstrucao4 );
		configureBankslipPage.setDsLocalPagamento1( dsLocalPagamento1 );
		configureBankslipPage.setDsLocalPagamento2( dsLocalPagamento2 );
		configureBankslipPage.setAutorizacaoGerente( Boolean.valueOf( isSelected ) );
		configureBankslipPage.submitForm();
		step( "Vai para o Dashboard e verifica que o numero da agência é o mesmo do boleto" );

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Visão Geral" );
		DashboardBankAccountPage dashboardBankAccountPage = getPaginas().getDashboardBankAccountPage( getWebDriver() );

		Boolean isShowAccountNumber = dashboardBankAccountPage.isAccountNumberDisplayed( accountNumber );

		checkPoint( "Não encontrou conta no dashboard", Boolean.TRUE, isShowAccountNumber );
		step( "Faz logout" );

		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );

	}

}
