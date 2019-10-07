package com.contaazul.auto.selenium.dashboard.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.dashboard.pages.DashboardPage;

public class ManualmportExtractTest extends SeleniumTest {

	/**
	 * Configuração de importação automática já cadastrada teste apenas irá
	 * atualizar o extrato
	 */

	// TODO: Teste em implementação ainda

	@Test
	public void manualmportExtractTest() {

		DashboardPage dashboardPage = getPaginas().getDashboardPage( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"ManualmportExtractTest@contaazul.com", "12345" );

		dashboardPage.atualizarExtract();
		checkPoint( "Atualizando extrato", "Atualizando extrato, isso poderá levar vários minutos...",
				dashboardPage.msgErrorImportExtract() );
		validateMsg();
		step( "clica em atualizar extrato,aguarda atualizar e faz a validação" );

		step( "Verifica as atualizações e clica em Conciliar " );
		vericationConciliation();

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
		step( "faz logout" );
	}

	private void validateMsg() {
		for (int i = 1; i <= 10; i++) {
			sleep( i * 5000 );
			String yes = driver.findElementById( "boxBankAccountNotificationText_1" ).getText();
			if (yes == "Extrato atualizado") {
				return;
			}
		}
	}

	private void vericationConciliation() {
		String conciliation = driver.findElementById( "pendingTransactionLink_0" ).getText();
		if (conciliation == "Conciliar") {

			DashboardPage dashboardPage = getPaginas().getDashboardPage( getWebDriver() );
			dashboardPage.btnConciliate();

		}

	}

}
