package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;

public class LinkBankNewImporTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void linkBankNewImporTest() {

		// TODO: Teste em implementação

		getAssistants().getLoginAssistant( getWebDriver() ).login( "fernando.m@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );

		step( "Vai para a pagina de importação" );

		step( "Valida os links dos bancos" );

		step( "Faz logout" );

	}

}
