package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceImportExtractPage;

public class ImportBankExtract extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void Import(String fileName, String bankAccount, String result, String newAccount) {

		FinanceImportExtractPage importExtractPage = getPaginas().getPaginaImportacaoExtrato( getWebDriver() );

		step( "Login e vai para Financeiro" );
		getAssistants().getLoginAssistant( getWebDriver() ).login( "ImportBankExtractTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );

		step( "Clica na opção 'Importar Extrato'" );
		importExtractPage.clickImportarExtrato();

		step( "Seleciona o arquivo" );
		importExtractPage.selecionarArquivoOFX( fileName );
		String contaId = bankAccount + "_" + Double.toString( Math.random() );
		if (newAccount.equals( "sim" )) {
			step( "Informa qual é a nova conta bancária" );
			importExtractPage.informarNovaContaBancaria( contaId );
		} else {
			step( "Informa qual é a conta bancária existente para ser selecionada" );
			importExtractPage.informarContaBancariaExistente( bankAccount );
		}

		step( "Clica em 'Continuar'" );
		importExtractPage.clickContinuar();

		step( "Verifica a mensagem 'Extrato bancário importado com sucesso!'" );
		checkPoint( "Extrato bancário importado com sucesso!", result, importExtractPage.getPopUpResult(), true );

		step( "Fechar a tela de conciliação" );
		importExtractPage.clickFecharTelaConciliacao();

		step( "Verifica os valores (conhecidos) que foram importados" );
		importExtractPage.clickMenuImportarExtrato();
		if (fileName.contains( "OFX_ContaBancaria" ))
			verificarValoresImportadosContaBancaria( importExtractPage );
		else
			verificarValoresImportadosCartaoCredito( importExtractPage );

		step( "Excluir todos os registros cadastrados" );
		importExtractPage.apagarTodosRegistrosDaLista();
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		checkPoint( "Registro não foram apagados da grid", "Importar meu extrato bancário",
				getAssistants()
						.getNotificationAssistant( getWebDriver() ).msgConcileValidation() );

		step( "Excluir Banco" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Organizar",
				"Contas bancárias" );

		step( "Se adicionou conta bancária, então necessita apagar ela.." );
		if (newAccount.equals( "sim" )) {
			getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Organizar",
					"Contas bancárias" );
			BankAccountsPage bank = getPaginas().getPaginaContasBancarias( getWebDriver() );
			bank.pesquisar( contaId );
			bank.clickCheckbox( contaId );
			bank.excluir();
		}

		step( "Logout" );
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void verificarValoresImportadosCartaoCredito(FinanceImportExtractPage importExtractPage) {
		// Verifica os lançamentos para Crédito (com base na posição da Table e
		// do arquivo)
		checkPoint( "Descrição esperada não encontrada", "CRÉDITO - CARTÃO CRÉDITO",
				importExtractPage.getMemoDescription1Text() );
		checkPoint( "Valor esperado não encontrado", "3.000,50", importExtractPage.getValue1Text() );

		// Verifica os lançamentos para Débito (com base na posição da Table e
		// do arquivo)
		checkPoint( "Descrição esperada não encontrada", "DÉBITO - CARTÃO CRÉDITO",
				importExtractPage.getMemoDescription2Text() );
		checkPoint( "Valor 2 esperado não encontrado", "-1.000,50", importExtractPage.getValue2Text() );
	}

	private void verificarValoresImportadosContaBancaria(FinanceImportExtractPage importExtractPage) {

		// Verifica os lançamentos para Crédito (com base na posição da Table e
		// do arquivo)
		checkPoint( "Descrição esperada não encontrada", "CRÉDITO - CONTA CORRENTE",
				importExtractPage.getMemoDescription1Text() );
		checkPoint( "Valor esperado não encontrado", "3.000,50", importExtractPage.getValue1Text() );

		// Verifica os lançamentos para Débito (com base na posição da Table e
		// do arquivo)
		checkPoint( "Descrição esperada não encontrada", "DÉBITO - CONTA CORRENTE",
				importExtractPage.getMemoDescription2Text() );
		checkPoint( "Valor 2 esperado não encontrado", "-1.000,50", importExtractPage.getValue2Text() );
	}

}
