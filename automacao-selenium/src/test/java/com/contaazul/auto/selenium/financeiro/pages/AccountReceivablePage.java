package com.contaazul.auto.selenium.financeiro.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

/**
 * 
 * Page object representanto a pagina de Contas a Receber
 * 
 */

public class AccountReceivablePage extends WebPage {

	@FindBy(xpath = "//div[@id = 'addFinance']/button")
	private WebElement createExpenseButton;

	public AccountReceivablePage(WebDriver driver) {
		super( driver );
	}

	/**
	 * 
	 * Filtra dados pelo periodo. Os items atuais sao: Hoje, Esta semana, Este
	 * mes, Ultimos 30 dias entre outros
	 * 
	 * @param period
	 */

	public void filterPeriod(Period period) {
		sleep( VERY_LONG );
		WebElement button = this.driver.findElementByXPath( "//div[@id='conteudo']/div/div/div[2]/button" );
		button.click();
		WebElement listItem = this.driver
				.findElementByXPath( "//div[contains(@class, 'periodSelector')]/ul/*/a/*[contains(text(),'" + period
						+ "')]" );
		listItem.click();
		sleep( VERY_LONG );
	}

	/**
	 * 
	 * Faz a pesquisa pelo campo de texto
	 * 
	 * @param text
	 */

	public void search(String text) {
		WebElement searchInput = this.driver.findElementById( "textSearch" );
		searchInput.clear();
		searchInput.sendKeys( text );
		searchInput.sendKeys( Keys.RETURN );
		sleep( FOR_A_LONG_TIME );
	}

	/**
	 * 
	 * Navega para a pagina de edicao de receita
	 * 
	 * @param rowNumber
	 * @return
	 */

	public IncomePage<AccountReceivablePage> navigateToEditIncomePage(Integer rowNumber) {
		getAssistants().getTableAssistant( driver ).editRow( "statement-list-container", rowNumber );
		return new IncomePage<AccountReceivablePage>( this, driver );
	}

	/**
	 * 
	 * Filtra os dados pelo status. Os itens atuais sao: Todos, Vencidos,
	 * Contrato de Venda, entre outros.
	 * 
	 * @param status
	 */

	public void filterStatus(Status status) {
		getAssistants().getDropdownAssistant( driver ).openDropdownOptions( "type-filter-controller" );
		getAssistants().getDropdownAssistant( driver ).cleanDropdownCheckboxes( "type-filter" );
		getAssistants().getDropdownAssistant( driver ).selectDropdownOption( "type-filter", status.toString() );
		getAssistants().getDropdownAssistant( driver ).applyFilter( "type-filter" );
	}

	/**
	 * 
	 * Filtra pelas contas pre cadastradas, como por exemploe: Tesouraria
	 * (Caixinha), Banco do Brasil etc
	 * 
	 * @param accountName
	 */

	public void filterBankAccount(String accountName) {
		getAssistants().getDropdownAssistant( driver ).openDropdownOptions( "bank-filter" );
		getAssistants().getDropdownAssistant( driver ).cleanDropdownCheckboxes( "bank-filter" );
		getAssistants().getDropdownAssistant( driver ).selectDropdownOption( "bank-filter", accountName );
		getAssistants().getDropdownAssistant( driver ).applyFilter( "bank-filter" );
		sleep( VERY_QUICKLY );
	}

	/**
	 * 
	 * Verifica se algum item do grid contem o texto
	 * 
	 * @param text
	 * @return
	 */
	public boolean hasItemInGrid(String text) {
		WebElement element = this.driver
				.findElementByXPath( "//*/div[@class='statement-table']/span[1][contains(text(), '"
						+ text + "')]" );
		return element != null;
	}

	/**
	 * 
	 * Pega o numero de itens visiveis na grid. Util para saber quantos
	 * registros apareceram depois de um filtro.
	 * 
	 * @return
	 */

	public long getGridItemsCount() {
		return (Long) this.javascript( "return jQuery('#statement-list-container tbody tr:visible').size();" );
	}

	public void advancedSearch(AdvancedSearchOptions options) {
		openAdvancedSearchDropdown();
		setInputField( "dsCategoria", options.getCategory() );
		setInputField( "centroCusto", options.getCostCenter() );
		setInputField( "clienteFornecedorSearch", options.getCustomerOrProvider() );
		setInputField( "descricao", options.getDescription() );
		this.driver.findElementById( "dropdown-btn-search" ).click();
		sleep( FOR_A_LONG_TIME );
	}

	private void setInputField(String id, String value) {
		this.driver.findElementById( id ).sendKeys( value );
	}

	private void openAdvancedSearchDropdown() {
		javascript( "jQuery(\".finance-search-btn-container [data-toggle='dropdown']\").click()" );
	}

	public static class AdvancedSearchOptions {

		private String category;
		private String costCenter;
		private String customerOrProvider;
		private String description;

		public AdvancedSearchOptions() {
		}

		public AdvancedSearchOptions(String category, String costCenter, String customerOrProvider, String description) {
			this.category = category;
			this.costCenter = costCenter;
			this.customerOrProvider = customerOrProvider;
			this.description = description;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getCostCenter() {
			return costCenter;
		}

		public void setCostCenter(String costCenter) {
			this.costCenter = costCenter;
		}

		public String getCustomerOrProvider() {
			return customerOrProvider;
		}

		public void setCustomerOrProvider(String customerOrProvider) {
			this.customerOrProvider = customerOrProvider;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}

	public void clickAddExpense() {
		createExpenseButton.click();
	}

}
