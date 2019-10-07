package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateBankAccountPage;

public class CreateBankAccountTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void criarContaBancaria(String banco, String nomeConta,
			String saldoInicialConta, String dataInicial, String $resultado, String $nomeBanco) {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "CreateBankAccountTest@contaazul.com", "12345" );
		step( "Fez logon" );
		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Organizar", "Contas bancárias" );

		BankAccountsPage banks = getPaginas().getPaginaContasBancarias(
				getWebDriver() );
		CreateBankAccountPage novaConta = getPaginas()
				.getPaginaAdicionarContaBancaria( getWebDriver() );

		step( "Adiciona dados nos campos, menos no campo do nome do Banco" );
		createBank( banco, saldoInicialConta, dataInicial, banks, novaConta );

		step( "Verifica a mensagem por ter deixado o campo em branco" );
		checkPoint( "Mensagem de erro não encontrada",
				"Preencha corretamente o(s) campo(s) obrigatório(s) em destaque", getAssistants()
						.getNotificationAssistant( getWebDriver() ).getAlertMessageText() );
		step( "Adiciona o nome do banco." );
		String randomId = createAccount( nomeConta, novaConta );

		step( "Apaga data e clica em salvar." );
		deleteDate( novaConta );

		step( "Seleciona um valor e deixa a data em branco." );
		randomId = checkFieldValue( banco, saldoInicialConta, dataInicial,
				banks, novaConta, randomId );

		step( "Submete form Nova Conta" );
		createNewAccount( banco, saldoInicialConta, dataInicial, $resultado, $nomeBanco,
				banks, novaConta, randomId );

		step( "Volta para tela inicial, seleciona o checkbox a ser deletado." );
		deleteAccount( novaConta, randomId );
	}

	private void deleteAccount(CreateBankAccountPage novaConta, String randomId) {
		novaConta.telaInicial();
		BankAccountsPage bank = getPaginas().getPaginaContasBancarias(
				getWebDriver() );
		bank.clickCheckbox( randomId );
		bank.excluir();
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void createNewAccount(String banco, String saldoInicialConta,
			String dataInicial, String $resultado, String $nomeBanco, BankAccountsPage banks,
			CreateBankAccountPage novaConta, String randomId) {
		if ($resultado.matches( SeleniumSession.getSession().getLocale()
				.translate( "#SUCCESS" ) )) {
			banks.pesquisar( randomId );
			checkPoint( "Não encontrou nome do banco esperado na conta bancária inline.", $nomeBanco,
					banks.getNomeBanco( randomId ) );
			checkPoint(
					"Não encontrou situacao boleto configurado na conta bancária inline.",
					SeleniumSession.getSession().getLocale().translate( "#NO" ),
					banks.getBankslipConfigured( randomId ) );
			banks.clickBankAccount( randomId );
			checkPoint(
					"Não encontrou nome do banco esperado na conta bancária.",
					banco, novaConta.getNomeBanco() );
			checkPoint(
					"Não encontrou nome da conta esperado na conta bancária.",
					randomId, novaConta.getNomeConta() );
			checkPoint(
					"Não encontrou saldo inicial esperado na conta bancária.",
					saldoInicialConta, novaConta.getSaldoInicial() );
			if (!saldoInicialConta.equals( "0" ))
				checkPoint(
						"Não encontrou data inicial esperada na conta bancária.",
						dataInicial.trim(), novaConta.getDataInicial().trim() );
		} else {
			checkPoint( "Mensagem de validação esperada mas não encontrada.",
					$resultado, novaConta.getMensagemValidacao() );
		}
	}

	private String checkFieldValue(String banco, String saldoInicialConta,
			String dataInicial, BankAccountsPage banks,
			CreateBankAccountPage novaConta, String randomId) {
		if (!saldoInicialConta.equals( "0" )) {
			randomId = randomId.substring( 0,
					Math.min( 30, randomId.length() - 1 ) );
			checkPoint(
					"Mensagem de erro não encontrada",
					"Para salvar um saldo inicial, a data do mesmo é obrigatória!",
					novaConta.msgAviso() );
			step( "Clica em OK e vai para tela inicial, para criar uma nova conta" );
			novaConta.btnMsgAvisoOk();
			novaConta.telaInicial();
			banks.clicarNovaConta();
			novaConta.setBanco( banco );
			novaConta.clearNomeDaConta();
			novaConta.setSaldoInicial( saldoInicialConta );
			novaConta.setDataSaldoInicial( dataInicial );
			novaConta.setNomeDaConta( randomId );
			novaConta.clicarSalvar();
		}
		return randomId;
	}

	private void deleteDate(CreateBankAccountPage novaConta) {
		novaConta.clearDataSaldoInicial();
		novaConta.clicarSalvar();
	}

	private String createAccount(String nomeConta,
			CreateBankAccountPage novaConta) {
		String randomId = nomeConta + "_" + Double.toString( Math.random() );
		randomId = randomId.substring( 0, Math.min( 30, randomId.length() - 1 ) );
		novaConta.setNomeDaConta( randomId );
		return randomId;
	}

	private void createBank(String banco, String saldoInicialConta,
			String dataInicial, BankAccountsPage banks,
			CreateBankAccountPage novaConta) {
		banks.clicarNovaConta();
		novaConta.setBanco( banco );
		novaConta.clearNomeDaConta();
		novaConta.setSaldoInicial( saldoInicialConta );
		novaConta.setDataSaldoInicial( dataInicial );
		novaConta.clicarSalvar();
	}
}
