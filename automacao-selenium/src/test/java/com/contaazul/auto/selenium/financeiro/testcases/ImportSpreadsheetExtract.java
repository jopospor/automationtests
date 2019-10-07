package com.contaazul.auto.selenium.financeiro.testcases;

import java.io.File;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.FinanceSpreadsheetPage;

public class ImportSpreadsheetExtract extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void Import(String bankAccount, String fileName) {

		step( "Login" );
		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"ImportSpreadsheetExtractTest@contaazul.com", "12345" );

		step( "Abre o módulo 'Financeiro'" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Movimentações", "Extrato" );

		FinanceSpreadsheetPage finSS = getPaginas()
				.getPaginaImportacaoPlanilhaExtrato( getWebDriver() );

		step( "Verifica se está fazendo o download da planilha padrão" );
		finSS.downloadTemplate();
		verifyDownloadedTemplate();

		step( "Clica em 'Minha planilha já está no padrão'" );
		finSS.openImportWindow();

		step( "Faz os cadastro" );
		finSS.ImportSpreadsheetWindow( bankAccount, fileName );

		step( "Confirma a importação" );
		finSS.confirmImport();

		step( "Verifica os valores dos registros importados" );
		verifyImportedFields( finSS );

		step( "Limpa a base" );
		finSS.apagarTodosRegistrosDaLista();

		step( "Logout" );
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	public void verifyDownloadedTemplate() {
		checkPoint( "Planilha padrão não foi baixada", true, getDefaultDownloadedFileLocation().exists() );

		if (!getDefaultDownloadedFileLocation().exists())
			getDefaultDownloadedFileLocation().delete();
	}

	public File getDefaultDownloadedFileLocation() {
		if (!System.getProperty( "os.name" ).contains( "Windows" )) {
			return new File( "/home/usuario/" + System.getProperty( "user.name" )
					+ "/Downloads/Planilha_Modelo_ContaAzul.xls" );
		} else {
			return new File( "C:\\Users\\" + System.getProperty( "user.name" )
					+ "\\Downloads\\Planilha_Modelo_ContaAzul.xls" );
		}
	}

	public void verifyImportedFields(FinanceSpreadsheetPage page) {
		page.btnMostrarTodos.click();
		page.filtroMostrarTodos.click();

		verifyFirstRegister( page );
		verifySecondRegister( page );
		verifyThirdRegister( page );
		verifyFourthRegister( page );
		verifyTotal( page );
	}

	public void verifyFirstRegister(FinanceSpreadsheetPage page) {
		checkPoint( "Data incorreta no primeiro registro", "24/05/2012", page.getFirstRegisterDate() );
		checkPoint( "Categoria incorreta no primeiro registro", "Venda", page
				.getFirstRegisterCategory() );
		checkPoint( "Descrição incorreta no primeiro registro", "Venda do produto 2213",
				page.getFirstRegisterDescription() );
		checkPoint( "Valor incorreto no primeiro registro", "10.000,00", page
				.getFirstRegisterValue() );
		checkPoint( "Saldo incorreto no primeiro registro", "10.000,00", page
				.getFirstRegisterBalance() );
	}

	public void verifySecondRegister(FinanceSpreadsheetPage page) {
		checkPoint( "Data incorreta no segundo registro", "25/05/2012", page.getSecondRegisterDate() );
		checkPoint( "Categoria incorreta no segundo registro", "Aluguel", page
				.getSecondRegisterCategory() );
		checkPoint( "Descrição incorreta no segundo registro", "Aluguel de maio",
				page.getSecondgetRegisterDescription() );
		checkPoint( "Valor incorreto no segundo registro", "-500,00", page.getSecondRegisterValue() );
		checkPoint( "Saldo incorreto no segundo registro", "9.500,00", page
				.getSecondRegisterBalance() );
	}

	public void verifyThirdRegister(FinanceSpreadsheetPage page) {
		checkPoint( "Data incorreta no terceiro registro", "25/05/2012", page.getThirdRegisterDate() );
		checkPoint( "Categoria incorreta no terceiro registro", "Infraestrutura", page
				.getThirdRegisterCategory() );
		checkPoint( "Descrição incorreta no terceiro registro", "Custos de infraestrutura",
				page.getThirdRegisterDescription() );
		checkPoint( "Valor incorreto no terceiro registro", "3.000,00", page.getThirdRegisterValue() );
		checkPoint( "Saldo incorreto no terceiro registro", "12.500,00", page
				.getThirdRegisterBalance() );
	}

	public void verifyFourthRegister(FinanceSpreadsheetPage page) {
		checkPoint( "Data incorreta no terceiro registro", "25/05/2012", page
				.getFourthRegisterDate() );
		checkPoint( "Categoria incorreta no terceiro registro", "Notas Fiscais", page
				.getFourthRegisterCategory() );
		checkPoint( "Descrição incorreta no terceiro registro", "Referente a Nota-Fiscal 2",
				page.getFourthRegisterDescription() );
		checkPoint( "Valor incorreto no terceiro registro", "5.000,00", page
				.getFourthRegisterValue() );
		checkPoint( "Saldo incorreto no terceiro registro", "17.500,00", page
				.getFourthRegisterBalance() );
	}

	public void verifyTotal(FinanceSpreadsheetPage page) {
		checkPoint( "Valor incorreto para o Total de Receitas", "18.000,00", page.getTotalReceitas() );
		checkPoint( "Valor incorreto para o Total de Despesas", "-500,00", page.getTotalDespesas() );
		checkPoint( "Valor incorreto para o Total do Período", "17.500,00", page.getTotalPeriodo() );
		checkPoint( "Quantidade incorreta para o Número de lançamentos existentes", "4", page
				.getNumeroLancamentos() );
	}
}
