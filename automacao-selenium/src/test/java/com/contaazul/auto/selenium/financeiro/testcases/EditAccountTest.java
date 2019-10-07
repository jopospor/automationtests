package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateBankAccountPage;

public class EditAccountTest extends SeleniumTest {
	@Test(dataProvider = DATA_PROVIDER)
	public void criarContaBancaria(String banco, String nomeConta,
			String saldoInicialConta, String dataInicial, String saldoInicialContaA,
			String dataInicialA) {

		step( "Faz o login e navega para a tela de Contas bancárias" );
		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"EditAccountTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Organizar", "Contas bancárias" );

		BankAccountsPage banks = getPaginas().getPaginaContasBancarias(
				getWebDriver() );
		CreateBankAccountPage novaConta = getPaginas()
				.getPaginaAdicionarContaBancaria( getWebDriver() );

		step( "Cria uma nova conta para fazer alteracoes depois" );
		String randomId = newEditBankAccount( banco, nomeConta, saldoInicialConta, dataInicial, banks, novaConta );

		step( "Pesquisa a conta criada anteriormente e faz alteração do cadastro." );
		searchEditBankAccount( saldoInicialContaA, dataInicialA, banks, novaConta, randomId );
		checkPoint( "Mensagem não encontrada, nao foi alterado com sucesso", "Registro alterado com sucesso!",
				novaConta.notification() );

		step( "Seleciona a conta e faz exclusão e faz o logout em sequencia." );
		deleteEditbankAccount( banks, randomId );
	}

	private void deleteEditbankAccount(BankAccountsPage banks, String randomId) {
		banks.clickCheckbox( randomId );
		banks.excluir();
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void searchEditBankAccount(String saldoInicialContaA, String dataInicialA, BankAccountsPage banks,
			CreateBankAccountPage novaConta, String randomId) {
		banks.pesquisar( randomId );
		novaConta.telaAlterar();
		novaConta.setNomeDaConta( randomId );
		novaConta.setSaldoInicial( saldoInicialContaA );
		novaConta.setDataSaldoInicial( dataInicialA );
		novaConta.clicarSalvar();
	}

	private String newEditBankAccount(String banco, String nomeConta, String saldoInicialConta, String dataInicial,
			BankAccountsPage banks, CreateBankAccountPage novaConta) {
		banks.clicarNovaConta();
		novaConta.setBanco( banco );
		novaConta.setSaldoInicial( saldoInicialConta );
		novaConta.setDataSaldoInicial( dataInicial );
		String randomId = nomeConta + "_" + Double.toString( Math.random() );
		randomId = randomId.substring( 0, Math.min( 30, randomId.length() - 1 ) );
		novaConta.setNomeDaConta( randomId );
		novaConta.clicarSalvar();
		return randomId;
	}
}
