package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.ConfigureBankslipPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateBankAccountPage;

public class ConfigureBankAccountTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void configurarBoletoContaBancaria(
			String banco,
			String nomeConta,
			String saldoInicialConta,
			String dataInicial,
			String nrAgence,
			String nrAccount,
			String walletStandard,
			String walletStandardSelect,
			String billetValue,
			String nrConvenio,
			String nrWallet,
			String lineOne,
			String lineTwo,
			String lineThree,
			String lineFour,
			String paymentPlaceOne,
			String paymentPlaceTwo,
			String nrModality,
			String dsCategory,
			String codClient,
			String nrCEB,
			String autorizacaoGerente, String $result) {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "ConfigureBankAccountTest@contaazul.com", "12345" );
		step( "Fez logon" );
		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Organizar",
				"Contas bancárias" );
		step( "Navega para bancos" );
		BankAccountsPage banks = getPaginas().getPaginaContasBancarias( getWebDriver() );
		banks.clicarNovaConta();
		step( "Clica nova conta" );
		CreateBankAccountPage novaConta = getPaginas().getPaginaAdicionarContaBancaria( getWebDriver() );
		novaConta.setBanco( banco );
		String randomId = nomeConta + "_" + Double.toString( Math.random() );
		randomId = randomId.substring( 0, Math.min( 30, randomId.length() - 1 ) );
		novaConta.setNomeDaConta( randomId );
		novaConta.setSaldoInicial( saldoInicialConta );
		novaConta.setDataSaldoInicial( dataInicial );
		step( "Preenche form Nova Conta" );
		novaConta.clicarSalvar();
		step( "Submete form Nova Conta" );
		banks.pesquisar( randomId );
		step( "Pesquisa pela Conta criada" );
		banks.clickCheckbox( randomId );
		step( "Seleciona conta na listagem" );
		banks.clicConfigureBankslip();
		step( "Clica configurar Conta" );
		ConfigureBankslipPage configureBankslipPage = getPaginas().getConfigureBankslipPage( getWebDriver() );
		configureBankslipPage.setNrAgencia( nrAgence );
		configureBankslipPage.setNrConta( nrAccount );
		if (!walletStandard.isEmpty())
			configureBankslipPage.setCarteiras( walletStandard );
		if (!walletStandardSelect.isEmpty())
			configureBankslipPage.selectCarteiras( walletStandardSelect );
		configureBankslipPage.setValorDoBoleto( billetValue );
		configureBankslipPage.setDsInstrucao1( lineOne );
		configureBankslipPage.setDsInstrucao2( lineTwo );
		configureBankslipPage.setDsInstrucao3( lineThree );
		configureBankslipPage.setDsInstrucao4( lineFour );
		configureBankslipPage.setDsLocalPagamento1( paymentPlaceOne );
		configureBankslipPage.setDsLocalPagamento2( paymentPlaceTwo );
		step( "Preenche form básico de configuração da Conta" );
		if (!nrWallet.isEmpty())
			configureBankslipPage.setVarCarteira( nrWallet );
		if (!nrConvenio.isEmpty())
			configureBankslipPage.setNumeroConvenio( nrConvenio );
		if (!codClient.isEmpty())
			configureBankslipPage.setCodCliente( codClient );
		if (!nrModality.isEmpty())
			configureBankslipPage.setModalidade( nrModality );
		if (!dsCategory.isEmpty())
			configureBankslipPage.setCategoria( dsCategory );
		boolean auxAutorizacaoGerente = autorizacaoGerente.toLowerCase()
				.matches( "true" );
		configureBankslipPage.setAutorizacaoGerente( auxAutorizacaoGerente );
		step( "Preenche form variável de configuração da Conta" );
		configureBankslipPage.submitForm();
		step( "Submete form configuração da Conta" );

		if ($result.matches( SeleniumSession.getSession().getLocale()
				.translate( "#SUCCESS" ) )) {
			sleep( VERY_LONG );
			banks.pesquisar( randomId );
			step( "Pesquisa pela Conta criada" );
			banks.clickCheckbox( randomId );
			step( "Seleciona conta na listagem" );
			banks.clicConfigureBankslip();
			step( "Clica configurar Conta" );

			ConfigureBankslipPage configureBankslipPage2 = getPaginas()
					.getConfigureBankslipPage( getWebDriver() );

			step( "Visualizar configuração da Conta" );
			checkPoint( "Não encontrou valor esperado em banco", banco,
					configureBankslipPage2.getNameBanco(), true );
			checkPoint( "Não encontrou valor esperado agencia", nrAgence,
					configureBankslipPage2.getNrAgencia(), true );
			checkPoint( "Não encontrou valor esperado em contaBeneficiario",
					nrAccount, configureBankslipPage2.getNrConta(), true );
			if (!walletStandard.isEmpty())
				checkPoint( "Não encontrou valor esperado em carteiraPadrao",
						walletStandard, configureBankslipPage2.getCarteiras(), true );
			if (!walletStandardSelect.isEmpty())
				checkPoint( "Não encontrou valor esperado em carteiraPadrao",
						walletStandardSelect, configureBankslipPage2.getCarteiras(), true );
			checkPoint( "Não encontrou valor esperado em valorDoBoleto",
					billetValue, configureBankslipPage2.getValorDoBoleto(), true );
			checkPoint( "Não encontrou valor esperado em linha1", lineOne,
					configureBankslipPage2.getDsInstrucao1(), true );
			checkPoint( "Não encontrou valor esperado em linha2", lineTwo,
					configureBankslipPage2.getDsInstrucao2(), true );
			checkPoint( "Não encontrou valor esperado em linha3", lineThree,
					configureBankslipPage2.getDsInstrucao3(), true );
			checkPoint( "Não encontrou valor esperado em linha4", lineFour,
					configureBankslipPage2.getDsInstrucao4(), true );
			checkPoint( "Não encontrou valor esperado em localPagto1",
					paymentPlaceOne, configureBankslipPage2.getDsLocalPagamento1(), true );
			checkPoint( "Não encontrou valor esperado em localPagto2",
					paymentPlaceTwo, configureBankslipPage2.getDsLocalPagamento2(), true );
			checkPoint( "Não encontrou valor esperado em checkboxGerente",
					autorizacaoGerente.toLowerCase().matches( "false" ),
					configureBankslipPage2.getAutorizacaoGerente(), true );

			if (!nrWallet.isEmpty())
				checkPoint( "Não encontrou valor esperado em variacaoCarteira",
						nrWallet,
						configureBankslipPage2.getVarCarteira(), true );
			if (!nrConvenio.isEmpty())
				checkPoint( "Não encontrou valor esperado em numeroConvenio",
						nrConvenio,
						configureBankslipPage2.getNumeroConvenio(), true );
			if (!codClient.isEmpty())
				checkPoint( "Não encontrou valor esperado em codCliente",
						codClient, configureBankslipPage2.getCodCliente(), true );
			if (!nrModality.isEmpty())
				checkPoint( "Não encontrou valor esperado em modalidade",
						nrModality, configureBankslipPage2.getModalidade(), true );
			if (!dsCategory.isEmpty())
				checkPoint( "Não encontrou valor esperado em categoria",
						dsCategory, configureBankslipPage2.getCategoria(), true );

		} else {

			step( "Tratar resultados negativos" );
			checkPoint( "Não encontrou mensagem de erro esperada.", $result,
					configureBankslipPage.getMensagemDeValidacao() );

		}

		configureBankslipPage.clickCancelConfigure();
		BankAccountsPage bank = getPaginas().getPaginaContasBancarias(
				getWebDriver() );
		bank.clickCheckbox( randomId );
		bank.clickCheckbox( randomId );
		bank.excluir();
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
