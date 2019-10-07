package com.contaazul.auto.selenium.assistants;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import com.contaazul.auto.selenium.WebPage;

public class ListingAssistant extends WebPage {

	@FindBy(xpath = "//span[contains(@class, 'jquery-checkbox')]/span/img")
	private WebElement checkAll;
	@FindBy(xpath = "//div[@id='selectedActions']/button")
	private WebElement actionsButton;
	@FindBy(xpath = "//div[@id='type-filter']/button")
	private WebElement displayButton;
	@FindBy(xpath = "//i[@class='rightArrow']/parent::a")
	private WebElement nextPeriodButton;
	@FindBy(xpath = "//i[@class='leftArrow']/parent::a")
	private WebElement previousPeriodButton;
	@FindBy(id = "textSearch")
	private WebElement textSearch;
	@FindBy(xpath = "//button[contains(@class, 'periodToggle')]")
	private WebElement filterByPeriodButton;
	@FindBy(css = "tbody.list-body.act-extract-body")
	private WebElement listBody;
	@FindBy(id = "deleteStmt")
	private WebElement deleteIncomeButton;

	/**
	 * Construtor default, deve passar RemoteWebDriver para o construtor super.
	 * 
	 * @param driver
	 *            uma referência para o RemoteWebDriver do teste que chama o
	 *            ListingAssistant
	 */
	public ListingAssistant(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	/**
	 * Seleciona todos os checkboxes que estão sendo listados, clicando no
	 * checkbox de selecionar todos do cabeçalho
	 */
	public void checkAllItems() {
		sleep( VERY_LONG );
		if (!isFinanceBlankslateVisible()) {
			if (checkAll.isDisplayed()) {
				waitForElementEnabled( By.xpath( "//span[contains(@class, 'jquery-checkbox')]/span/img" ) );
				checkAll.click();
			}
			else
				driver.findElement( By.xpath( "//th[@id='importSelector']/span/span/img" ) )
						.click();
		}
	}

	public boolean isFinanceBlankslateVisible() {
		sleep( VERY_LONG );
		JavascriptExecutor jay = (JavascriptExecutor) driver;
		return (((String) jay.executeScript(
				"return document.getElementById('financeiroListBlankSlateContainer').getStyle('visibility')" ))
				.matches( "visible" )
		&& !((String) jay
				.executeScript( "return document.getElementById('financeiroListBlankSlateContainer').getStyle('display')" ))
				.matches( "none" ));
	}

	/**
	 * Desmarca todos os checkboxes que estão sendo listados, clicando no
	 * checkbox de selecionar todos do cabeçalho. Caso o checkbox de marcar
	 * todos esteja desmarcado, ele o marca e em seguida desmarca, para que
	 * todos sejam desmarcados.
	 */
	public void uncheckAllItems() {
		if (!isCheckAllSelected())
			checkAll.click();
		checkAll.click();
	}

	private boolean isCheckAllSelected() {
		WebElement checkAllCheckbox = driver.findElement( By
				.xpath( "//th[span[contains(@class, 'jquery-checkbox')]]/input" ) );
		return (checkAllCheckbox.getAttribute( "checked" ) != null && checkAllCheckbox.getAttribute( "checked" )
				.matches( "true" ));
	}

	/**
	 * Marca o checkbox dentro da linha desejada.
	 * 
	 * @param rowNumber
	 *            Linha da tabela que se deseja selecionar. A primeira linha é a
	 *            linha 1 e o cabeçalho é ignorado.
	 */
	public void checkRow(int rowNumber) {
		if (!isRowSelected( rowNumber ))
			driver.findElementsByClassName( "mark" ).get( rowNumber ).findElement( By.tagName( "img" ) ).click();
	}

	private boolean isRowSelected(int rowNumber) {
		WebElement checkBox = driver.findElementsByXPath( "//td[@class='checkbox-colunm']/input" ).get( rowNumber - 1 );
		return (checkBox.getAttribute( "checked" ) != null && checkBox.getAttribute( "checked" ).matches( "true" ));
	}

	/**
	 * Desmarca o checkbox dentro da linha desejada.
	 * 
	 * @param rowNumber
	 *            Linha da tabela que se deseja desmarcar. A primeira linha é a
	 *            linha 1 e o cabeçalho é ignorado.
	 */
	public void uncheckRow(int rowNumber) {
		if (isRowSelected( rowNumber ))
			driver.findElementsByClassName( "mark" ).get( rowNumber ).click();
	}

	/**
	 * Busca os títulos das colunas das tabelas.
	 * 
	 * @return Retorna um array de Strings contendo os títulos das colunas das
	 *         tabelas
	 */
	public String[] getListingHeaders() {
		List<WebElement> tableHeaders = driver.findElementsByXPath( "//thead[@class='table-header']/tr/th" );
		String results = "";
		Iterator<WebElement> headerElementsIterator = tableHeaders.iterator();
		while (headerElementsIterator.hasNext()) {
			WebElement header = headerElementsIterator.next();
			if (!header.getAttribute( "id" ).matches( "statementSelector" ))// checkbox
				if (header.isDisplayed() && !header.getText().isEmpty())
					results += header.getText() + ";";
		}
		if (results.endsWith( ";" ))
			results = results.substring( 0, results.length() - 1 );
		return results.split( ";" );
	}

	/**
	 * Retorna a linha inteira como um WebElement, normalmente para que nele
	 * possam ser executadas operações como findElement, getText, etc.
	 * 
	 * @param rowNumber
	 *            Linha da tabela que se deseja selecionar. A primeira linha é a
	 *            linha 1 e o cabeçalho é ignorado.
	 * @return Retorna um WebElement que representa a linha inteira.
	 */
	public WebElement getListingRowAsElement(int rowNumber) {
		waitForElementNotStale( By.xpath( "//div[@id='statement-list-container']/table" ) );
		WebElement listing = driver.findElement( By.xpath( "//div[@id='statement-list-container']/table" ) );
		return listing.findElements( By.tagName( "tr" ) ).get( rowNumber );
	}

	/**
	 * Retorna o texto de uma linha sem tratamento algum.
	 * 
	 * @param rowNumber
	 *            Linha da tabela que se deseja selecionar. A primeira linha é a
	 *            linha 1 e o cabeçalho é ignorado.
	 * @return String contento todo o texto que extiver sendo exposto naquela
	 *         linha, agrupando todas as células.
	 */
	public String getRowText(int rowNumber) {
		return getListingRowAsElement( rowNumber ).getText();
	}

	/**
	 * Exibe (ou esconde) o menu de Ações.
	 */
	public void clickActionsButton() {
		actionsButton.click();
	}

	/**
	 * Exibe (ou esconde) o menu de Exibir.
	 */
	public void clickDisplayButton() {
		displayButton.click();
	}

	/**
	 * Exibe o menu de Ações e clica no item correspondente.
	 * 
	 * @param command
	 *            Label do menu de Ações que se deseja ativar.
	 */
	public void performAction(String option) {
		if (option == "Excluir") {
			if (!isFinanceBlankslateVisible()) {
				clickActionsButton();
				driver.findElement( By.linkText( "Excluir" ) ).click();
				driver.findElement( By.linkText( "Excluir Agora" ) ).click();
			}
		}
		else
			clickActionsButton();
		driver.findElement( By.linkText( option ) ).click();

	}

	private boolean isActionsMenuDisplayed() {
		return driver.findElement( By.xpath( "//ul[contains(@class, 'fin-actions-dropdown')]" ) ).isDisplayed();
	}

	/**
	 * Abre o menu de Exibir e clica no item desejado, marcando ou desmarcando o
	 * item.
	 * 
	 * @param criteria
	 *            Label do item que se deseja marcar ou desmarcar no menu
	 *            Exibir.
	 */
	public void clickOnDisplayCriteria(String criteria) {
		if (!isDisplayMenuDisplayed())
			clickDisplayButton();
		driver.findElementByLinkText( criteria ).click();
	}

	private boolean isDisplayMenuDisplayed() {
		return driver.findElement( By.xpath( "//ul[contains(@class, 'filter-finance-flow')]" ) ).isDisplayed();
	}

	/**
	 * Independente da marcação no menu de Exibir, abre o menu e clica em
	 * Aplicar.
	 */
	public void clickOnApplyDisplayCriteria() {
		if (!isDisplayMenuDisplayed())
			clickDisplayButton();
		driver.findElementByXPath( "//div[@id='type-filter']/ul/li[2]/button" ).click();
	}

	/**
	 * Digita uma string no campo de pesquisa e tecla Enter.
	 * 
	 * @param keyword
	 */
	public void searchByKeyword(String keyword) {
		textSearch.clear();
		textSearch.sendKeys( keyword, Keys.RETURN );
	}

	/**
	 * Seleciona um período dentre os períodos pré-definidos nas telas de
	 * listagem.
	 * 
	 * @param keyword
	 *            O período que se deseja selecionar. Ex. Este mês.
	 */
	public void filterByPeriod(String keyword) {
		if (!isPeriodFilterMenuDisplayed())
			clickFilterByPeriodButton();
		driver.findElement( By.linkText( keyword ) ).click();
	}

	private boolean isPeriodFilterMenuDisplayed() {
		return driver.findElement( By.className( "dropdown-menu" ) ).isDisplayed();
	}

	/**
	 * Exibe (ou esconde) o menu de seleção de período da busca.
	 */
	public void clickFilterByPeriodButton() {
		filterByPeriodButton.click();
	}

	/**
	 * Expande o menu de seleção ao lado do checkbox de selecionar todos e
	 * escolhe um item no menu.
	 * 
	 * @param criteria
	 *            Label do item que se deseja selecionar. Ex. A receber.
	 */
	public void selectItemsByCriteria(String criteria) {
		if (!isSelectByCriteriaMenuDisplayed())
			clickSelectByCriteriaArrow();
		driver.findElement( By.linkText( criteria ) ).click();
	}

	/**
	 * Exibe (ou esconde) o menu de seleção ao lado do checkbox de selecionar
	 * todos.
	 */
	public void clickSelectByCriteriaArrow() {
		driver.findElement( By.cssSelector( "a.select-all.dropdown-toggle" ) ).click();
	}

	private boolean isSelectByCriteriaMenuDisplayed() {
		return driver.findElement( By.xpath( "//th[@id='statementSelector']/div/ul" ) ).isDisplayed();
	}

	public void clickPreviousPeriod() {
		previousPeriodButton.click();
	}

	public void clickNextPeriod() {
		nextPeriodButton.click();
	}

	/**
	 * Verifica se existe algum item na grid que o nome do lançamento contenha a
	 * string passada como parâmetro
	 * 
	 * @param financeStatement
	 *            Nome do lançamento
	 */
	public boolean hasItemInGrid(String financeStatement) {
		return getListingRowsByFinanceStatement( financeStatement ).size() > 0;
	}

	/**
	 * Retorna uma lista de <code>WebElement</code> com o nome o lançamento
	 * informado como parâmetro.
	 * 
	 * @param financeStatement
	 *            Nome do lançamento
	 * @return List<WebElement> Lista de de linhas que contenham na coluna
	 *         "Lançamento" o nome do lançamento.
	 */
	public List<WebElement> getListingRowsByFinanceStatement(String financeStatement) {
		try {
			String xpathFindByFinanceStatement = "//div[@id='statement-list-container']/table/tbody//*/div[@class='statement-table']/span[1][contains(text(), '"
					+ financeStatement + "')]/parent::div/parent::td/parent::tr";
			List<WebElement> listElements = this.driver.findElementsByXPath( xpathFindByFinanceStatement );
			return listElements;
		} catch (Exception e) {
			Reporter.log( "AVISO: Não conseguiu encontrar Lançamento na grid com: '" + financeStatement + "'", true );
			return null;
		}
	}

	/**
	 * Verifica se existe algum item na grid que contenha o Nome do Lançamento e
	 * seja igual a flag Baixado
	 * 
	 * @param financeStatement
	 *            Nome do Lançamento
	 * @param down
	 *            Flag Valor Baixado
	 * @return Boolean Indica se existe algum item na grid com as restrições
	 *         passadas como parâmetro
	 */
	public boolean hasItemInGrid(String financeStatement, Boolean down) {
		WebElement tdReceived;

		for (WebElement element : getListingRowsByFinanceStatement( financeStatement )) {

			tdReceived = element.findElement( By.xpath( "td[5]" ) );
			if (down.equals( tdReceived.getAttribute( "class" ).contains( "baixado-icon" ) ))
				return true;
		}

		return false;
	}

	public boolean isReceivedOnRow(Integer rowNumber) {
		String selectorReceived = "#statement-list-container > table:eq(0) > tbody > tr:eq(" + (rowNumber - 1)
				+ ") > td:eq(4)";
		return (Boolean) driver.executeScript( "return jQuery('" + selectorReceived + "').hasClass('baixado-icon')" );

	}

	public String getFinanceStatementOnRow(Integer rowNumber) {
		if (isRowNumberValid( rowNumber )) {
			String xpathFinanceStatement = "//*[@id='statement-list-container']/table[1]/tbody/tr[" + rowNumber
					+ "]/td[4]/div[1]/span";
			return driver.findElement( By.xpath( xpathFinanceStatement ) ).getText().trim();
		}
		return "";
	}

	public String getDateOnRow(Integer rowNumber) {
		if (isRowNumberValid( rowNumber ))
			return getListingRowAsElement( rowNumber ).findElements( By.tagName( "td" ) ).get( 2 ).getText().trim();

		return "";
	}

	public String getValueOnRow(Integer rowNumber) {
		if (isRowNumberValid( rowNumber ))
			return getListingRowAsElement( rowNumber ).findElement( By.xpath( "td[5]" ) ).getText().trim();

		return "";
	}

	public String getCategoryOnRow(Integer rowNumber) {
		if (isRowNumberValid( rowNumber )) {
			String xpathCategory = "//*[@id='statement-list-container']/table[1]/tbody/tr[" + rowNumber
					+ "]/td[4]/span[1]";
			return driver.findElement( By.xpath( xpathCategory ) ).getText().trim();
		}
		return "";
	}

	public String getCustomerVendorOnRow(Integer rowNumber) {
		if (isRowNumberValid( rowNumber ))
			return getListingRowAsElement( rowNumber )
					.findElement( By.xpath( "td[@class='statement']/div[@class='statement-table']/span[2]'" ) )
					.getText().trim();
		return "";
	}

	public Integer getTotalRows() {
		List<WebElement> listRows = this.listBody.findElements( By.cssSelector( "tr" ) );
		return listRows.size();
	}

	private Boolean isRowNumberValid(Integer rowNumber) {
		return rowNumber > 0 && rowNumber <= getTotalRows();
	}

	public void clickDeleteIncomeButton() {
		this.deleteIncomeButton.click();
	}

	public int getTotalSelectedRows() {
		return getSelectedRows().size();
	}

	private List<WebElement> getSelectedRows() {
		try {
			return driver
					.findElementsByXPath( "//*[@id='statement-list-container']/table[1]/tbody/tr[contains(@class, 'selected')]" );
		} catch (Exception e) {
			Reporter.log( "AVISO: Não encontrou linhas selecionadas na grid", true );
			return null;
		}
	}

	public void confirmExclusion() {
		sleep( 3000 );
		if (!getAssistants().getModalAssistant( driver ).isPopUpVisible( "newPopupManagerReplacement" ))
			driver.findElement( By.linkText( "Excluir Agora" ) ).click();
	}

	public boolean hasItemsBeingListed() {
		try {
			return driver.findElementById( "statementSelector" ).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void deleteAllItems() {
		if (hasItemsBeingListed()) {
			selectAllItems();
			performAction( "Excluir" );
		}
	}

	private void selectAllItems() {
		if (hasItemsBeingListed())
			if (!driver.findElement( By.xpath( "//th[@id='statementSelector']/span" ) ).getAttribute( "class" )
					.contains( "checked" ))
				driver.findElement( By.xpath( "//th[@id='statementSelector']/span/span/img" ) ).click();
	}

}
