package com.contaazul.auto.selenium.financeiro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import com.contaazul.auto.selenium.WebPage;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class FinanceExtractPage extends WebPage {

	public FinanceExtractPage(WebDriver driver) {
		super( driver );
	}

	@FindBy(xpath = "//div[@id='addExtract']/button")
	protected WebElement adicionarItem;
	@FindBy(xpath = "//a[contains(@class, 'menu-new-expense new-financ')]")
	protected WebElement addExpenseLink;
	@FindBy(xpath = "//div[@id='addExtract']/ul/li/a/span")
	protected WebElement addRevenueLink;
	@FindBy(xpath = "//*[contains(@class, ' textSearchIcon headerTextSearch')]")
	protected WebElement keywordSearchField;
	@FindBy(xpath = "//*[contains(@class, 'btn no-margin-top btn-gerenciar-contas')]")
	protected WebElement botaoGerenciarContas;
	@FindBy(xpath = "//div[@id='addExtract']/ul/li[1]/a/span")
	protected WebElement newRevenue;
	@FindBy(xpath = "//*[contains(@class, 'btn-editar-contas')]")
	protected WebElement editCategoryButton;
	@FindBy(xpath = "//div[@id='statement-list-container']/table/tbody/tr/td[3]")
	protected WebElement dateField;
	@FindBy(xpath = "//div[@id='statement-list-container']/table/tbody/tr/td[4]/div/span")
	protected WebElement btnEdit;
	@FindBy(xpath = "//label[4]/div/label/input")
	protected WebElement dateActual;
	@FindBy(xpath = "//*[contains(@class, 'btn save_form btn-primary addStatement')]")
	protected WebElement saveEdit;
	@FindBy(xpath = "//div[@id='addExtract']/button")
	protected WebElement btnAddRevenue;
	@FindBy(id = "valor")
	protected WebElement value;
	@FindBy(id = "dtVencimento")
	protected WebElement expirationDate;
	@FindBy(id = "idCategoria")
	protected WebElement incomeCategory;
	@FindBy(xpath = "//*[@id='formStatement']/div[1]/label[6]/div/span[1]")
	protected WebElement received;
	@FindBy(id = "idClienteFornecedor")
	protected WebElement customer;
	@FindBy(id = "dtEmissao")
	protected WebElement competenceDate;
	@FindBy(id = "centroCustoNewSelect")
	protected WebElement costCenter;
	@FindBy(id = "observacao")
	protected WebElement observations;
	@FindBy(linkText = "Adicionar Receita")
	protected WebElement btnCreateRevenue;
	@FindBy(xpath = "//div[@id='conteudo']/div/div[2]/div[2]/div/div/div[4]/div/button")
	protected WebElement clickOptionsSearch;
	@FindBy(id = "dsCategoria")
	protected WebElement category;
	@FindBy(id = "centroCusto")
	protected WebElement searchCostCenter;
	@FindBy(id = "clienteFornecedorSearch")
	protected WebElement customerSupplier;
	@FindBy(id = "descricao")
	protected WebElement searchDescription;
	@FindBy(id = "dropdown-btn-search")
	protected WebElement search;
	@FindBy(xpath = "//div[@id='statement-list-container']/table/tbody/tr/td[2]/span/span/img")
	protected WebElement firstLineCheckBox;
	@FindBy(id = "type-filter")
	protected WebElement viewFilter;
	@FindBy(xpath = "//*[contains(@class, 'btn more-options')]")
	private WebElement moreOptions;

	public void clickAdicionarReceita() {
		adicionarItem.click();
		addRevenueLink.click();
	}

	public void pesquisarPorTexto(String query) {
		keywordSearchField.sendKeys( query );
		keywordSearchField.sendKeys( Keys.RETURN );
	}

	public WebElement getLancamentoInlineElement(String nomeLancamento) {
		try {
			return driver.findElement( By
					.xpath( "//span[contains(@data-original-title, '"
							+ nomeLancamento + "')]" ) );
		} catch (ElementNotFoundException e) {
			Reporter.log(
					"AVISO: Não encontrou lançamento inline nesta página (Extrato) com o nome: "
							+ nomeLancamento + ".", true );
			return null;
		} catch (ElementNotVisibleException e) {
			Reporter.log( "AVISO: Elemento inline com o nome: " + nomeLancamento
					+ " encontado porém invisivel.", true );
			return null;
		}
	}

	public void clickAdicionarDespesas() {
		adicionarItem.click();
		addExpenseLink.click();
	}

	/**
	 * Deprecado, utilizar void clickEditCategoriesButton
	 * 
	 * @return Retorna uma instância da página EditCategoryPage
	 */
	@Deprecated
	public EditCategoryPage navigateToEditCategory() {
		editCategoryButton.click();
		sleep( VERY_LONG );
		return new EditCategoryPage( driver );
	}

	public void clickEditCategoriesButton() {
		editCategoryButton.click();
		sleep( VERY_LONG );
	}

	public IncomePage<FinanceExtractPage> navigateToNewIncome() {
		sleep( VERY_LONG );
		this.openFinanceDropdown();
		driver.findElementByXPath( "//div[@id='addExtract']/ul/li/a/span" ).click();
		return new IncomePage<FinanceExtractPage>( this, driver );
	}

	public IncomePage<FinanceExtractPage> navigateToNewExpense() {
		sleep( VERY_LONG );
		this.openFinanceDropdown();
		driver.findElementByXPath( "//*[@id='addExtract']/ul/li[2]/a/span" ).click();
		return new IncomePage<FinanceExtractPage>( this, driver );
	}

	private void openFinanceDropdown() {
		sleep( QUICKLY );
		driver.findElementByXPath( "//div[@id='addExtract']/button" ).click();
		javascript( "document.getElementById('addExtract').className = 'btn-group open'" );
	}

	public void btnEdit() {
		btnEdit.click();
	}

	public void saveEdit() {
		saveEdit.click();
		sleep( VERY_LONG );
	}

	public void search(String pesquisar) {
		sleep( VERY_LONG );
		keywordSearchField.click();
		keywordSearchField.sendKeys( pesquisar );
		keywordSearchField.sendKeys( Keys.RETURN );
		sleep( VERY_LONG );
	}

	public void searchClean() {
		keywordSearchField.clear();
		keywordSearchField.sendKeys( Keys.ENTER );
		sleep( VERY_LONG );
	}

	public void btnAddRevenue() {
		btnAddRevenue.click();
	}

	public void value(String valor) {
		value.sendKeys( valor );
	}

	public void expirationDate(String dtVencimento) {
		expirationDate.sendKeys( dtVencimento );
	}

	public void sendMoreOptions(String cliente, String dtEmissao, String centroDeCusto, String observacao) {
		customer.sendKeys( cliente );
		competenceDate.sendKeys( dtEmissao );
		costCenter.sendKeys( centroDeCusto );
		observations.sendKeys( observacao );
	}

	public void incomeCategory(String categoria) {
		incomeCategory.sendKeys( categoria );
	}

	public void advancedSearch(String categorias, String CentroDeCusto, String ClientesFornecedores, String Descricao) {
		clickOptionsSearch.click();
		category.sendKeys( categorias );
		searchCostCenter.sendKeys( CentroDeCusto );
		customerSupplier.sendKeys( ClientesFornecedores );
		searchDescription.sendKeys( Descricao );
		search.click();
		sleep( VERY_LONG );
	}

	public void selectFirstLineCheckbox() {
		sleep( VERY_LONG );
		firstLineCheckBox.click();
	}

	public void clickMoreOptions() {
		moreOptions.click();
	}

	public void filterStatus(String status) {
		openDropdownOptionsViews( "type-filter-controller" );
		cleanDropdownCheckboxes( "type-filter" );
		selectDropdownOption( "type-filter", status );
		applyFilter( "type-filter" );
	}

	public void filterStatusBank(String bank) {
		openDropdownOptionsAccountBank( "bank-filter" );
		cleanDropdownCheckboxesCleanBank();
		selectDropdownOptionBank( "bank-filter", bank );
		applyFilterBank( "type-filter" );
	}

	public void applyFilterBank(String id) {
		driver.findElementByXPath( "//*[contains(text(), 'Aplicar')]" ).click();
	}

	protected void applyFilter(String id) {
		driver.findElementByXPath(
				"//div[@id='" + id + "']//*/button[contains(text(), 'Aplicar')]" )
				.click();
		sleep( VERY_LONG );
	}

	public void selectDropdownOption(String id, String option) {
		driver.findElementByXPath(
				"//div[@id='" + id + "']//*[contains(text(), '" + option + "')]" )
				.click();
	}

	protected void selectDropdownOptionBank(String id, String option) {
		driver.findElementByXPath(
				"//div[@id='" + id + "']//*[contains(text(), '" + option + "')]" )
				.click();
	}

	public void selectDropdownOptionDate(String id) {
		driver.findElementByXPath( "//button[(contains(@class,'btn dropdown-toggle periodToggle'))]" ).click();
	}

	public void selectDropdownOptionToday(String today) {
		driver.findElementByXPath( "//*[@id='conteudo']/div/div[1]/div[2]/ul/li[1]/a" ).click();
	}

	public void selectDropdownOptionWeek(String week) {
		driver.findElementByXPath( "//*[@id='conteudo']/div/div[1]/div[2]/ul/li[2]/a" ).click();
	}

	public void selectDropdownOptionMonth(String month) {
		driver.findElementByXPath( "//*[@id='conteudo']/div/div[1]/div[2]/ul/li[3]/a" ).click();
	}

	public void selectDropdownOptionAll(String all) {
		driver.findElementByXPath( "//*[@id='conteudo']/div/div[1]/div[2]/ul/li[5]/a" ).click();
	}

	protected void openDropdownOptionsViews(String id) {
		driver.findElementByXPath(
				"//button[@id='type-filter-controller'] "
				)
				.click();
	}

	public void openDropdownOptionsAccountBank(String id) {
		driver.findElementByCssSelector(
				"#" + id + "[data-toggle='dropdown'],#" + id +
						" [data-toggle='dropdown']" ).click();
	}

	public void cleanDropdownCheckboxes(String id) {
		JavascriptExecutor executor = this.driver;
		executor.executeScript( "jQuery('#" + id + " .selected').click();" );
		executor.executeScript( "jQuery('#" + id + " .selected:first').click();" );
	}

	public void cleanDropdownCheckboxesCleanBank() {
		driver.findElementByXPath(
				"//*[@id='bank-filter']/ul/li[1]/a/span[1]" ).click();
	}

	public void newRevenue() {
		newRevenue.click();
		sleep( VERY_LONG );
	}

	public void deleteNow(String option) {
		driver.findElementByLinkText( option ).click();
	}

	public String getTotalValueRevenue() {
		return driver.findElementByXPath( "//*[@id='conteudo']/div/div[2]/div[2]/div/div[5]/div[3]/div[1]/div[1]/span" )
				.getText();
	}

	public String getTotalValueExpense() {
		return driver.findElementByXPath( "//*[@id='conteudo']/div/div[2]/div[2]/div/div[5]/div[3]/div[1]/div[2]/span" )
				.getText();
	}

	public String getTotalNumberTransaction() {
		return driver.findElementByXPath( "//*[@id='conteudo']/div/div[2]/div[2]/div/div[5]/div[3]/div[2]/div[1]/span" )
				.getText();
	}

	public void btnOpenMenuExtract(String option) {
		driver.findElementByXPath( "//*[@id='addExtract']/button" ).click();
		driver.findElementByLinkText( option ).click();
	}
}
