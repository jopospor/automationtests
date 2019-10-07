package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.FinancialAssistant;
import com.contaazul.auto.selenium.assistants.ListingAssistant;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.componentesweb.pages.Menu;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;

public class ImportTransferLotFinanceTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void importTransferLotFinance(String conta, String arquivo, String arquivoOFX, String accountOrigin,
			String valueTransfer, String accountDestiny) {

		ListingAssistant listingAssistant = getAssistants().getListingAssistant( getWebDriver() );
		NotificationAssistant notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );
		FinancialAssistant financialAssistant = getAssistants().getFinancialAssistant( getWebDriver() );
		ImportBankExtractPage importBankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );

		step( "Faz o login e navega para pagina de importar extrato." );
		getAssistants().getLoginAssistant( getWebDriver() ).login( "ImportTransferLotFinance@contaazul.com",
				"12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );

		step( "Seleciona o arquivo OFX, clica para enviar como uma transfêrencia, espera mensagem ser exebida e as deleta." );
		String randomId = conta + "_" + Double.toString( Math.random() );
		randomId = randomId.substring( 0, Math.min( 30, randomId.length() - 1 ) );
		importBankExtract.btnNewExtractOfx();
		importBankExtract.selectBankAccount( randomId );
		importBankExtract.importExtractOfx( arquivo );
		importBankExtract.clickLaunchConcileButton();
		notificationAssistant.waitMessageDismiss();

		step( "Seleciona as receitas e vai para a opcao de enviar para transferencia" );
		listingAssistant.checkAllItems();
		importBankExtract.selectAction( "Adicionar como transferências" );

		step( "Verifica a mensagem na tela, e deleta as receitas em seguida" );
		checkPoint( "Mensagem de validação não apareceu.",
				"Por favor selecione apenas recebimentos ou apenas pagamentos para conciliar em lote.",
				notificationAssistant.getAlertMessageText() );
		notificationAssistant.waitMessageDismiss();
		listingAssistant.checkAllItems();
		listingAssistant.checkAllItems();
		importBankExtract.selectAction( "Excluir" );

		step( "Navega para a tela de importar Extrato, seleciona um novo arquivo OFX, clica para enviar como uma transfêrencia e espera a tela." );
		importBankExtract.btnNewExtractOfx();
		String randomId2 = conta + "_" + Double.toString( Math.random() );
		randomId2 = randomId2.substring( 0, Math.min( 30, randomId2.length() - 1 ) );
		importBankExtract.selectBankAccount( randomId2 );
		importBankExtract.importExtractOfx( arquivoOFX );
		importBankExtract.clickLaunchConcileButton();

		step( "Tela de transferencia, insere os dados: Banco Origem, Banco Destino e Valor e clica em salvar." );

		step( "Seleciona as duas recieta e envia para transferencia em lote" );
		listingAssistant.checkAllItems();
		importBankExtract.selectAction( "Adicionar como transferências" );
		importBankExtract.selectBankDestiny( randomId );
		importBankExtract.selectBankOrigin( randomId2 );
		importBankExtract.saveTransfer();

		step( "Vai para tela de extrato e verifica se os valores na tela são corretos tipo transfêrencia CRÉDITO." );
		financialAssistant.navigateMenuFinance( "Extrato" );
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );

		if (financialAssistant.getNameTransaction( 1 ).equals( "CRÉDITO - CONTA CORRENTE" )) {
			checkPoint( "Os campos estão com valores incorretos", "Transferência de Entrada",
					financialAssistant.getTypeTransaction( "4", 2 ) );
			checkPoint( "Os campos estão com valores incorretos", "CRÉDITO - CONTA CORRENTE",
					financialAssistant.getNameTransaction( 2 ) );
			checkPoint( "Os campos estão com valores incorretos", "3.000,50",
					financialAssistant.getValue( 2 ) );
			checkPoint( "Os campos estão com valores incorretos", "0,00",
					financialAssistant.getBalance( 2 ) );
			checkPoint( "Os campos estão com valores incorretos", "Transferência de Saída",
					financialAssistant.getTypeTransaction( "4", 1 ) );
			checkPoint( "Os campos estão com valores incorretos", "CRÉDITO - CONTA CORRENTE",
					financialAssistant.getNameTransaction( 1 ) );
			checkPoint( "Os campos estão com valores incorretos", "-3.000,50",
					financialAssistant.getValue( 1 ) );
			checkPoint( "Os campos estão com valores incorretos", "-3.000,50",
					financialAssistant.getBalance( 1 ) );
			checkPoint( "Os campos estão com valores incorretos", "Transferência de Entrada",
					financialAssistant.getTypeTransaction( "4", 4 ) );
			checkPoint( "Os campos estão com valores incorretos", "DÉBITO - CONTA CORRENTE",
					financialAssistant.getNameTransaction( 4 ) );
			checkPoint( "Os campos estão com valores incorretos", "1.000,50",
					financialAssistant.getValue( 4 ) );
			checkPoint( "Os campos estão com valores incorretos", "0,00",
					financialAssistant.getBalance( 4 ) );
			checkPoint( "Os campos estão com valores incorretos", "Transferência de Saída",
					financialAssistant.getTypeTransaction( "4", 3 ) );
			checkPoint( "Os campos estão com valores incorretos", "DÉBITO - CONTA CORRENTE",
					financialAssistant.getNameTransaction( 3 ) );
			checkPoint( "Os campos estão com valores incorretos", "-1.000,50",
					financialAssistant.getValue( 3 ) );
			checkPoint( "Os campos estão com valores incorretos", "-1.000,50",
					financialAssistant.getBalance( 3 ) );
			step( "Verifica na tela os valores do tipo DÉBITO." );
		}
		else {
			checkPoint( "Os campos estão com valores incorretos", "Transferência de Entrada",
					financialAssistant.getTypeTransaction( "4", 2 ) );
			checkPoint( "Os campos estão com valores incorretos", "DÉBITO - CONTA CORRENTE",
					financialAssistant.getNameTransaction( 2 ) );
			checkPoint( "Os campos estão com valores incorretos", "1.000,50",
					financialAssistant.getValue( 2 ) );
			checkPoint( "Os campos estão com valores incorretos", "0,00",
					financialAssistant.getBalance( 2 ) );
			checkPoint( "Os campos estão com valores incorretos", "Transferência de Saída",
					financialAssistant.getTypeTransaction( "4", 1 ) );
			checkPoint( "Os campos estão com valores incorretos", "DÉBITO - CONTA CORRENTE",
					financialAssistant.getNameTransaction( 1 ) );
			checkPoint( "Os campos estão com valores incorretos", "-1.000,50",
					financialAssistant.getValue( 1 ) );
			checkPoint( "Os campos estão com valores incorretos", "-1.000,50",
					financialAssistant.getBalance( 1 ) );
			checkPoint( "Os campos estão com valores incorretos", "Transferência de Entrada",
					financialAssistant.getTypeTransaction( "4", 4 ) );
			checkPoint( "Os campos estão com valores incorretos", "CRÉDITO - CONTA CORRENTE",
					financialAssistant.getNameTransaction( 4 ) );
			checkPoint( "Os campos estão com valores incorretos", "3.000,50",
					financialAssistant.getValue( 4 ) );
			checkPoint( "Os campos estão com valores incorretos", "0,00",
					financialAssistant.getBalance( 4 ) );
			checkPoint( "Os campos estão com valores incorretos", "Transferência de Saída",
					financialAssistant.getTypeTransaction( "4", 3 ) );
			checkPoint( "Os campos estão com valores incorretos", "CRÉDITO - CONTA CORRENTE",
					financialAssistant.getNameTransaction( 3 ) );
			checkPoint( "Os campos estão com valores incorretos", "-3.000,50",
					financialAssistant.getValue( 3 ) );
			checkPoint( "Os campos estão com valores incorretos", "-3.000,50",
					financialAssistant.getBalance( 3 ) );
		}

		listingAssistant.checkAllItems();
		importBankExtract.selectAction( "Excluir" );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( Menu.CONTAS_BANCARIAS );
		BankAccountsPage bank = getPaginas().getPaginaContasBancarias( getWebDriver() );
		bank.clickCheckbox( randomId );
		bank.excluir();
		bank.clickCheckbox( randomId2 );
		bank.excluir();
		getAssistants().getLoginAssistant( getWebDriver() ).logout();

	}
}
