package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.ConfigureBankslipPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage.RolloverMenuOptions;

public class PrintBankSlipInLineTest extends SeleniumTest {

	@Test
	public void printBankSlipInLine() {

		loginAndNavigate();

		FinanceFlowPage financeFlow = getPaginas().getFinanceFlowPage( getWebDriver() );
		ConfigureBankslipPage configureBank = getPaginas().getConfigureBankslipPage( getWebDriver() );

		step( "Abre o menu rollover e clica para imprimir um boleto não configurado para imprimir" );
		clickPrintSlip( financeFlow );
		checkPoint( "Erro banco está configurado para emitir boleto",
				"Sua conta ainda não está configurada para emitir boleto.",
				getAssistants().getNotificationAssistant( getWebDriver() ).getAlertMessageText() );

		step( "Fecha a janela de configurar banco" );
		closeBankConfigure( configureBank );

		step( "Define a receita como paga" );
		setRevenuePay( financeFlow );

		step( "Verifica se a receita tem a opção de imprimir com a receita definida como paga" );
		financeFlow.showRolloverMenu( 0 );
		checkPoint( "Boleto está como não pago ou ocorreu outro erro", true, financeFlow.isNotDisplayedPrintSlip() );
		waitMessegeDismissAndCloseRollOver( financeFlow );

		step( "Define a receita como não paga" );
		setrevenueUndoPay( financeFlow );

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void loginAndNavigate() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "PrintBankSlipTestInLine@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitLoadingDismiss();
		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
	}

	private void setrevenueUndoPay(FinanceFlowPage financeFlow) {
		financeFlow.showRolloverMenu( 0 );
		financeFlow.chooseOptionMenuRollover( RolloverMenuOptions.UNDO_PAY );
		financeFlow.closeRolloverMenuOpened();
	}

	private void waitMessegeDismissAndCloseRollOver(FinanceFlowPage financeFlow) {
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		financeFlow.closeRolloverMenuOpened();
	}

	private void setRevenuePay(FinanceFlowPage financeFlow) {
		financeFlow.showRolloverMenu( 0 );
		financeFlow.chooseOptionMenuRollover( RolloverMenuOptions.PAY );
		financeFlow.closeRolloverMenuOpened();
	}

	private void closeBankConfigure(ConfigureBankslipPage configureBank) {
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageDismiss();
		configureBank.btnCancelConfigureBank();
	}

	private void clickPrintSlip(FinanceFlowPage financeFlow) {
		financeFlow.showRolloverMenu( 0 );
		financeFlow.chooseOptionMenuRollover( RolloverMenuOptions.PRINT_SLIP );
		financeFlow.closeRolloverMenuOpened();
	}

}
