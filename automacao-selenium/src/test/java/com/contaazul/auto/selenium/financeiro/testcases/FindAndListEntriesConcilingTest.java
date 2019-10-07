package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;

public class FindAndListEntriesConcilingTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void findAndListEntriesConcilingTest(String conta, String arquivo) {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "FindAndListEntriesConcilingTest@contaazul.com",
				"12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Financeiro", "Movimentações",
				"Importar Extrato" );

		ImportBankExtractPage bankExtract = getPaginas().getImportBankExtractPage( getWebDriver() );

		step( "Acessa o menu de importar extrato e faz upload dele." );
		bankExtract.btnNewExtractOfx();
		String contaId = conta + "_" + Double.toString( Math.random() );
		bankExtract.selectBankAccount( contaId );
		bankExtract.importExtractOfx( arquivo );
		bankExtract.clickLaunchConcileButton();

		step( "Clica no filtro de despesa e tira o checked dele filtrando apenas por receitas" );
		getAssistants().getDropdownAssistant( getWebDriver() ).openDropdownOptions( "type-filter" );
		getAssistants().getDropdownAssistant( getWebDriver() ).selectDropdownOption( "type-filter", "Pagamentos" );
		getAssistants().getListingAssistant( getWebDriver() ).clickOnApplyDisplayCriteria();
		checkPoint( "Valor da grid incorreto, data ERRADA!", "15/10/2013", bankExtract.getDate( 1 ) );
		checkPoint( "valor da grid incorreto, lançamento ERRADO!", "CRÉDITO - CONTA CORRENTE",
				bankExtract.getEntry( 1 ) );
		checkPoint( "Valor da grid Incorreto, valor ERRADO!", "3.000,50", bankExtract.getValue( 1 ) );

		step( "Clica no filtro de despesas seleciona todos os filtros." );
		getAssistants().getDropdownAssistant( getWebDriver() ).openDropdownOptions( "type-filter" );
		getAssistants().getDropdownAssistant( getWebDriver() ).selectDropdownOption( "type-filter", "Pagamentos" );
		getAssistants().getListingAssistant( getWebDriver() ).clickOnApplyDisplayCriteria();

		step( "Clica no filtro de receitas e deixa apenas, filtrando por Despesas" );
		getAssistants().getDropdownAssistant( getWebDriver() ).openDropdownOptions( "type-filter" );
		getAssistants().getDropdownAssistant( getWebDriver() ).selectDropdownOption( "type-filter", "Recebimentos" );
		getAssistants().getListingAssistant( getWebDriver() ).clickOnApplyDisplayCriteria();
		checkPoint( "Valor da grid incorreto, data ERRADA!", "15/10/2013", bankExtract.getDate( 1 ) );
		checkPoint( "valor da grid incorreto, lançamento ERRADO!", "DÉBITO - CONTA CORRENTE",
				bankExtract.getEntry( 1 ) );
		checkPoint( "Valor da grid Incorreto, valor ERRADO!", "-1.000,50", bankExtract.getValue( 1 ) );

		step( "Marca todos os filtros novamente e faz validação dos valores na tela" );
		getAssistants().getDropdownAssistant( getWebDriver() ).openDropdownOptions( "type-filter" );
		getAssistants().getDropdownAssistant( getWebDriver() ).selectDropdownOption( "type-filter", "Recebimentos" );
		getAssistants().getListingAssistant( getWebDriver() ).clickOnApplyDisplayCriteria();
		checkPoint( "Valor da grid incorreto, data ERRADA!", "15/10/2013", bankExtract.getDate( 1 ) );
		checkPoint( "valor da grind incorreto, lançamento ERRADO!", "CRÉDITO - CONTA CORRENTE",
				bankExtract.getEntry( 1 ) );
		checkPoint( "Valor da grid Incorreto, valor ERRADO!", "3.000,50", bankExtract.getValue( 1 ) );
		checkPoint( "Valor da grid incorreto, data ERRADA!", "15/10/2013", bankExtract.getDate( 2 ) );
		checkPoint( "valor da grind incorreto, lançamento ERRADO!", "DÉBITO - CONTA CORRENTE",
				bankExtract.getEntry( 2 ) );
		checkPoint( "Valor da grid Incorreto, valor ERRADO!", "-1.000,50", bankExtract.getValue( 2 ) );

		step( "Seleciona todas as conciliação e as deleta e faz em sequencia a verificação se foram deletados e em seguioda logout!" );
		bankExtract.clickAllCheckBox();
		bankExtract.selectAction( "Excluir" );
		sleep( VERY_LONG );
		checkPoint( "Registros não foram deletados", "Importar meu extrato bancário",
				getAssistants()
						.getNotificationAssistant( getWebDriver() ).msgConcileValidation() );
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
