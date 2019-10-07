package com.contaazul.auto.selenium.financeiro.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.financeiro.pages.FinanceHistoryPage;

public class FinanceSearchTest extends SeleniumTest {
	@Test(dataProvider = DATA_PROVIDER)
	public void findAndListRealeased(String Receita, String Despesa, String Transferencia) {

		step( "Faz login, navega pelo menu financeiro e vai para o histórico." );
		FinanceHistoryPage financeHistoryPage = loginAndGoToFinanceHistoryPage();

		step( "Pesquisa a receita, verifica os valores, salva e limpa o campo de busca." );
		searchRevenueValue( Receita, financeHistoryPage );

		step( "Pesquisa a despesa, verifica os valores, salva e limpa o campo de busca." );
		searchExpenseValue( Despesa, financeHistoryPage );

		financeHistoryPage.searchList( Receita );
		financeHistoryPage.seeRevenue();
		checkPoint( "Nome da Receita é inválido", "Receita", financeHistoryPage.getName() );
		checkPoint( "Valores não correspondem", "100,00", financeHistoryPage.getCompareValue() );
		checkPoint( "As contas bancárias não são iguais", "Bradesco", financeHistoryPage.getCompareAccount() );
		checkPoint( "As Datas não conferem", "17/12/2023", financeHistoryPage.getCompareDate() );
		checkPoint( "Categoria de despesa incorreta", "Ajuste Caixa", financeHistoryPage.getCategory() );
		financeHistoryPage.save();
		financeHistoryPage.cleanSearch();

		financeHistoryPage.btnHistoric();

		step( "Pesquisa a despesa, verifica os valores, salva e limpa o campo de busca." );

		financeHistoryPage.searchList( Despesa );
		financeHistoryPage.seeExpense();
		checkPoint( "Nome da Despesa é inválido", "Despesa", financeHistoryPage.getName() );
		checkPoint( "Valores não correspondem", "200,00", financeHistoryPage.getCompareValue() );
		checkPoint( "As contas bancárias não são iguais", "Banco do Brasil", financeHistoryPage.getCompareAccount() );
		checkPoint( "As Datas não conferem", "17/12/2023", financeHistoryPage.getCompareDate() );
		checkPoint( "Categoria de despesa incorreta", "Aluguel", financeHistoryPage.getCategory() );
		financeHistoryPage.save();
		financeHistoryPage.cleanSearch();

		financeHistoryPage.btnHistoric();

		step( "Pesquisa a tranferência, verifica os valores, salva e limpa o campo de busca." );
		searchTransferValue( Transferencia, financeHistoryPage );

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void searchTransferValue(String Transferencia, FinanceHistoryPage financeHistoryPage) {
		financeHistoryPage.searchList( Transferencia );
		financeHistoryPage.seeTransfer();
		checkPoint( "Dados da transferencia não batem com os cadastrados", "Banco do Brasil",
				financeHistoryPage.getAccountBankOrigin() );
		checkPoint( "Dados da transferencia não batem com os cadastrados", "Bradesco",
				financeHistoryPage.getAccountBankDestiny() );
		checkPoint( "Dados da transferencia não batem com os cadastrados", "transferência",
				financeHistoryPage.getDescriptionTransfer() );
		checkPoint( "Dados da transferencia não batem com os cadastrados", "300,00",
				financeHistoryPage.getValueTransfer() );
		checkPoint( "Dados da transferencia não batem com os cadastrados", "16/12/2013",
				financeHistoryPage.getTransferedDate() );
		financeHistoryPage.btnSaveTransfer();
		financeHistoryPage.cleanSearch();
	}

	private void searchExpenseValue(String Despesa, FinanceHistoryPage financeHistoryPage) {
		financeHistoryPage.searchList( Despesa );
		financeHistoryPage.seeExpense();
		checkPoint( "Nome da Despesa é inválido", "Despesa", financeHistoryPage.getName() );
		checkPoint( "Valores não correspondem", "200,00", financeHistoryPage.getCompareValue() );
		checkPoint( "As contas bancárias não são iguais", "Banco do Brasil", financeHistoryPage.getCompareAccount() );
		checkPoint( "As Datas não conferem", "17/12/2023", financeHistoryPage.getCompareDate() );
		checkPoint( "Categoria de despesa incorreta", "Aluguel", financeHistoryPage.getCategory() );
		financeHistoryPage.save();
		financeHistoryPage.cleanSearch();
		financeHistoryPage.btnHistoric();
	}

	private void searchRevenueValue(String Receita, FinanceHistoryPage financeHistoryPage) {
		financeHistoryPage.searchList( Receita );
		financeHistoryPage.seeRevenue();
		checkPoint( "Nome da Receita é inválido", "Receita", financeHistoryPage.getName() );
		checkPoint( "Valores não correspondem", "100,00", financeHistoryPage.getCompareValue() );
		checkPoint( "As contas bancárias não são iguais", "Bradesco", financeHistoryPage.getCompareAccount() );
		checkPoint( "As Datas não conferem", "17/12/2023", financeHistoryPage.getCompareDate() );
		checkPoint( "Categoria de despesa incorreta", "Ajuste Caixa", financeHistoryPage.getCategory() );
		financeHistoryPage.save();
		financeHistoryPage.cleanSearch();
		financeHistoryPage.btnHistoric();
	}

	private FinanceHistoryPage loginAndGoToFinanceHistoryPage() {
		step( "Faz login, entra no menu financeiro e navega para o Histórico." );
		FinanceHistoryPage financeHistoryPage = (getPaginas()).getFinanceHistoryPage( getWebDriver() );
		getAssistants().getLoginAssistant( getWebDriver() ).login(
				"FinanceSearchTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro" );

		financeHistoryPage.btnHistoric();
		return financeHistoryPage;
	}

}
