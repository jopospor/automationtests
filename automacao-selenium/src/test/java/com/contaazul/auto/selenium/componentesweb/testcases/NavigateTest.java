package com.contaazul.auto.selenium.componentesweb.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;

public class NavigateTest extends SeleniumTest {
	@Test
	public void navigateTest() {
		// financeiro
		getAssistants().getLoginAssistant( getWebDriver() ).login( "NavigateTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Compras",
				"Compras" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Organizar", "Contas bancárias" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Contas a Receber" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Organizar", "Contas bancárias" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações", "Extrato" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Organizar", "Contas bancárias" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Organizar", "Contas bancárias" );

		// Menu Compras>Compras para cadastros*
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Compras", "Compras" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Cadastros", "Produtos" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Compras", "Compras" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Cadastros", "Fornecedores" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Compras", "Compras" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Cadastros", "Estoque" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Compras", "Compras" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Cadastros", "Transportadora" );

		// Menu Compras>Pedidos de Compra para cadastros*
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Compras", "Pedidos de Compra" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Cadastros", "Produtos" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Compras", "Pedidos de Compra" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Cadastros", "Fornecedores" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Compras", "Compras" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Cadastros", "Transportadora" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Compras", "Compras" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Cadastros", "Estoque" );
	}

}
