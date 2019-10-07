package com.contaazul.auto.selenium.fiscal.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.fiscal.pages.InvoiceListPage;
import com.contaazul.auto.selenium.fiscal.pages.InvoicePage;

public class InvoiceCFOPTest extends SeleniumTest {

	@BeforeClass
	public void setUP() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "InvoiceCFOPTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Produtos", "Emitir NF-e" );
		step( "Navega para listagem emissão notas fiscais de vendas" );
		InvoiceListPage invoiceListPage = getPaginas().getInvoiceListPage( getWebDriver() );
		invoiceListPage.clickOnFirstInvoice();
		step( "Clica na primeira nota da lista" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void insertEntranceCFOPOnOutGoingInvoice(final String value, String $expected) {
		final InvoicePage invoice = getPaginas().getInvoicePage( getWebDriver() );
		invoice.typeOnCFOPField( value );
		String autoCompleteResult = invoice.getPopUpResult( value );
		checkPoint( "Encontrado CFOP de entrada na emissão de nota fiscal de venda", $expected, autoCompleteResult,
				true );
		invoice.clearCFOPField();
	}

	@AfterClass
	public void finishTest() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
