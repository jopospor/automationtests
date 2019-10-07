package com.contaazul.auto.selenium.fiscal.testcases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.fiscal.pages.EntranceInvoiceListPage;
import com.contaazul.auto.selenium.fiscal.pages.EntranceInvoicePage;

public class EntranceInvoiceCFOPTest extends SeleniumTest {

	@BeforeClass
	public void setUP() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "InvoiceCFOPTest@contaazul.com", "12345" );
		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Estoque", "Compras",
				"Emitir NF-e de Entrada" );
		step( "Navega para listagem emissão notas fiscais de vendas" );
		EntranceInvoiceListPage entranceInvoiceListPage = getPaginas().getEntranceInvoiceListPage( getWebDriver() );
		entranceInvoiceListPage.clickOnFirstInvoice();
		step( "Clica na primeira nota da lista" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void insertOutgoingCFOPOnEntranceInvoice(final String value, String $expected) {
		EntranceInvoicePage invoice = getPaginas().getEntranceInvoicePage( getWebDriver() );
		invoice.typeOnCFOPField( value );
		String autoCompleteResult = invoice.getPopUpResult( value );
		checkPoint(
				"Encontrado CFOP de venda na emissão de nota fiscal de entrada",
				$expected, autoCompleteResult, true );
		invoice.clearCFOPField();
	}

	@AfterTest
	public void finishTest() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

}
