package com.contaazul.auto.selenium.clientes.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.clientes.pages.AdminPage;

public class AdminTest extends SeleniumTest {

	@Test
	public void vaiProAdminEFazUmaAlteracaoDepoisVaiPraContaAzul() {
		AdminPage adminPage = getPaginas().getAdminPage( getWebDriver() );
		adminPage.goToAdminpage();
		adminPage.setSearchField( "h_nilton@saniplast.com.br" );
		adminPage.clickSearchButton();
		adminPage.clickTestABButtonOnFirstuser( "h_nilton@saniplast.com.br" );

		adminPage.setABTest( "Planos disponibilizados:",
				"Trimestral/Semestral: Autônomo, Startup, Micro, Pequeno, Médio" );
		// changeNewListStep();
		adminPage.clickSaveFeatures();

		getWebDriver().get(
				SeleniumSession.getSession().getProperties().getProperty( SeleniumProperties.APPLICATION_BASE_URL ) );

	}

}
