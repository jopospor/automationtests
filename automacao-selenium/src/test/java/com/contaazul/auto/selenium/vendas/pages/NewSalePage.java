package com.contaazul.auto.selenium.vendas.pages;

import java.util.List;
import java.util.Random;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.contaazul.auto.selenium.WebPage;
import com.contaazul.auto.selenium.assistants.AutoCompleteAssistant;

public class NewSalePage extends WebPage {

	public NewSalePage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	@FindBy(id = "negotiationNumberLabel")
	private WebElement headerSaleNumber;
	@FindBy(id = "negotiationTotalValueLabel")
	private WebElement headerSaleTotalValue;
	@FindBy(id = "negotiationNegotiator")
	private WebElement saleClient;
	@FindBy(id = "negotiationNegotiatiorName")
	private WebElement saleResumeClient;
	@FindBy(id = "negotiationNumber")
	private WebElement saleNumber;
	@FindBy(id = "negotiationEmission")
	private WebElement saleEmissionDate;
	@FindBy(id = "negotiationInstallmentQuantity")
	private WebElement salePaymentType;
	@FindBy(id = "negotiationInstallmentFirstDueDate")
	private WebElement salePaymentMaturityDate;
	@FindBy(id = "negotiationTotalItemsLabel")
	private WebElement somaVlrTotal;
	@FindBy(id = "negotiationDate")
	private WebElement saleDate;
	@FindBy(id = "negotiationFirstDueDate")
	private WebElement saleFirstDueDate;
	@FindBy(id = "negotiationDiscountMeasureUnit")
	private WebElement discountType;
	@FindBy(id = "negotiationDiscountRate")
	private WebElement discountValue;
	@FindBy(id = "negotiationShipping")
	private WebElement salefreightValue;
	@FindBy(id = "negotiationTotalValueLabel")
	private WebElement headerSaleTotalLiquid;
	@FindBy(id = "negotiationTotalLiqueidLabel")
	private WebElement saleTotalLiquid;
	@FindBy(id = "negotiationInstallment")
	private WebElement saleInstallment;
	@FindBy(id = "newNegotiation")
	private WebElement buttonCreateNewSale;
	@FindBy(xpath = "//*[@id='conteudo']/div/div[2]/div/div/div/div/a")
	private WebElement buttonNewSaleBlankSlate;
	@FindBy(linkText = "Adicionar mais uma linha")
	private WebElement buttonNewItem;
	@FindBy(id = "removeNegotiationItem0")
	private WebElement removeFirstItem;
	@FindBy(id = "saveNegotiation")
	private WebElement buttonSave;
	@FindBy(id = "cancelNegotiation")
	private WebElement btnCancelNewSale;
	@FindBy(linkText = "Salvar Item")
	private WebElement btnSalvarQuiackAdd;
	@FindBy(xpath = "//*[@id='breadcrumb']/li[2]/a")
	private WebElement breadbrumbVenda;
	@FindBy(id = "editNegotiationBottom")
	private WebElement btnEditSaleBottom;
	@FindBy(id = "negotiationNote")
	private WebElement observationSale;
	@FindBy(id = "textualSearchNegotiation")
	private WebElement searchField;
	@FindBy(id = "editNegotiationUpper")
	private WebElement btnEditSaleUpper;
	@FindBy(id = "btnOk")
	private WebElement btnWarningDelete;
	@FindBy(id = "searchNegotiation")
	private WebElement btnSearchList;

	Random randomNumber = new Random();
	AutoCompleteAssistant ac = new AutoCompleteAssistant( driver );
	private int lastSaleNumber;
	public static final int TYPE_PRODUCT = 0;
	public static final int TYPE_SERVICE = 1;

	public void goBackToSaleList() {
		this.breadbrumbVenda.click();
		sleep( VERY_LONG );
	}

	public String getDiscountType() {
		return this.discountType.getText();
	}

	public Double getSaleTotalLiquid() {
		return Double.parseDouble( convertValues( this.saleTotalLiquid.getText() ) );
	}

	public Double getHeaderSaleTotalLiquid() {
		String text = driver.findElementById( "negotiationTotalValueLabel" ).getText();
		text = text.replace( "R$", "" ).trim();
		return Double.parseDouble( convertValues( text ) );
	}

	public Double getDiscountValue() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String text = (String) js.executeScript( "return document.getElementById('negotiationDiscountRate').value" );
		return Double.parseDouble( convertValues( text ) );
	}

	public Double getResumeDiscountValue() {
		return Double
				.parseDouble( convertValues( driver.findElementById( "negotiationTotalDiscountLabel" ).getText() ) );
	}

	public int getLastSaleNumber() {
		return this.lastSaleNumber;
	}

	public String getSaleDate() {
		return this.saleDate.getText();
	}

	public String getSaleFirstDueDate() {
		return this.saleFirstDueDate.getText();
	}

	public String getSaleInstallment() {
		return this.saleInstallment.getText();
	}

	public int getTableRows() {
		List<WebElement> rows = driver
				.findElements( By
						.xpath( "//*[@id='negotiation']/div[1]/div[5]/div/div[1]/table/tbody/tr" ) );
		return rows.size();
	}

	public int getResumeTableRows() {
		List<WebElement> rows = driver
				.findElements( By
						.xpath( "//*[@id='conteudo']/div[1]/div[8]/table/tr" ) );
		return rows.size();
	}

	public int getTableColumns() {
		List<WebElement> columns = driver
				.findElements( By
						.xpath( "//*[@id='negotiation']/div[1]/div[5]/div/div[1]/table/tbody/td" ) );
		return columns.size();
	}

	public double getQttyItem(int i) {
		sleep( VERY_LONG );
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String text = (String) js.executeScript( "return document.getElementById('negotiationItemQuantity" + i
				+ "').value" );

		return Double.parseDouble( convertValues( text ) );
	}

	public double getResumeQttyItem(int i) {
		sleep( VERY_LONG );
		return Double.parseDouble( convertValues( driver.findElementById( "negotiationItemQuantity_" + i ).getText() ) );
	}

	public Double getPriceItem(int i) {
		sleep( VERY_LONG );
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String text = (String) js
				.executeScript( "return document.getElementById('negotiationItemPrice" + i + "').value" );

		return Double.parseDouble( convertValues( text ) );
	}

	public Double getResumePriceItem(int i) {
		sleep( VERY_LONG );
		return Double.parseDouble( convertValues( driver.findElementById( "negotiationItemPrice_" + i ).getText() ) );
	}

	public Double getSubTotal(int i) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String text = (String) js
				.executeScript( "return document.getElementById('negotiationItemTotal" + i + "').value" );

		return Double.parseDouble( convertValues( text ) );
	}

	public Double getResumeSubTotal(int i) {
		return Double.parseDouble( convertValues( driver.findElementById( "negotiationItemTotal_" + i ).getText() ) );
	}

	public Double getFreightValue() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String text = (String) js
				.executeScript( "return document.getElementById('negotiationShipping').value" );

		return Double.parseDouble( convertValues( text ) );
	}

	public Double getResumeFreightValue() {
		return Double.parseDouble( convertValues( driver.findElementById( "negotiationShipping" ).getText() ) );
	}

	public Double getSomaVlrTotal() {
		return Double.parseDouble( convertValues( this.somaVlrTotal.getText() ) );
	}

	public String convertValues(String text) {
		text = text.replace( ".", "" ).trim();
		text = text.replace( ",", "." ).trim();
		return text;
	}

	public String getItemName(int i) {
		return driver.findElementById( "negotiationItem0" + i ).getText();
	}

	public String getHeaderSaleNumber() {
		return this.headerSaleNumber.getText();
	}

	public String getClient() {
		return this.saleResumeClient.getText();
	}

	public String getSaleNumber() {
		sleep( VERY_LONG );
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (String) js.executeScript( "return document.getElementById('negotiationNumber').value" );
	}

	public void fillRandomItemsList(String qtd, String discountType, String discountValue, String freightValue) {
		for (int i = 0; i < Integer.parseInt( qtd ); i++) {
			sleep( VERY_LONG );
			createProductAndService( i );

			driver.findElementById( "negotiationItemDescription" + i ).sendKeys(
					"Descrição" + randomNumber.nextInt( 1000 ) );
			driver.findElementById( "negotiationItemDescription" + i ).clear();
			driver.findElementById( "negotiationItemQuantity" + i ).sendKeys(
					Integer.toString( randomNumber.nextInt( 1000 ) ) );
			driver.findElementById( "negotiationItemPrice" + i ).sendKeys(
					Integer.toString( randomNumber.nextInt( 1000 ) ) );

			if ((i >= 4) && (i != Integer.parseInt( qtd ) - 1))
				buttonNewItem.click();
		}

		if (discountType.equals( "%" ))
			new Select( driver.findElement( By.id( "negotiationDiscountMeasureUnit" ) ) ).selectByVisibleText( "%" );
		else
			new Select( driver.findElement( By.id( "negotiationDiscountMeasureUnit" ) ) ).selectByVisibleText( "R$" );
		this.discountValue.clear();
		this.discountValue.sendKeys( discountValue );
		if (this.salefreightValue.isDisplayed()) {
			this.salefreightValue.clear();
			this.salefreightValue.sendKeys( freightValue );
		}
	}

	private void createProductAndService(int i) {
		if (i % 2 == 0)
			createItemByQuickAdd( driver.findElementById( "negotiationItem" + i ),
					"Item" + randomNumber.nextInt( 10000 ),
					NewSalePage.TYPE_PRODUCT, i );
		else
			createItemByQuickAdd( driver.findElementById( "negotiationItem" + i ),
					"Item" + randomNumber.nextInt( 10000 ),
					NewSalePage.TYPE_SERVICE, i );
	}

	public void addOneItemOnList(String item, String itemDesc, String itemQuantity, String itemPrice,
			String discountType, String discountValue, String freightValue) {
		ac.newSendKeysAndSelectExisting( driver.findElementById( "negotiationItem0" ), item );

		driver.findElementById( "negotiationItemDescription0" ).click();

		driver.findElementById( "negotiationItemDescription0" ).sendKeys( itemDesc );

		if (!itemQuantity.equals( "" )) {
			driver.findElementById( "negotiationItemQuantity0" ).clear();
			driver.findElementById( "negotiationItemQuantity0" ).sendKeys( itemQuantity );
		}

		if (!itemPrice.equals( "" )) {
			driver.findElementById( "negotiationItemPrice0" ).clear();
			driver.findElementById( "negotiationItemPrice0" ).sendKeys( itemPrice );
		}

		if (discountType.equals( "%" ))
			new Select( driver.findElement( By.id( "negotiationDiscountMeasureUnit" ) ) ).selectByVisibleText( "%" );
		else
			new Select( driver.findElement( By.id( "negotiationDiscountMeasureUnit" ) ) ).selectByVisibleText( "R$" );
		this.discountValue.clear();
		this.discountValue.sendKeys( discountValue );
		if (this.salefreightValue.isDisplayed()) {
			this.salefreightValue.clear();
			this.salefreightValue.sendKeys( freightValue );
		}
	}

	public void deleteAllSales() {
		driver.findElement(
				By.xpath( "//*[@id='conteudo']/div/div/table/thead/tr/th[1]/input" ) )
				.click();
		driver.findElement(
				By.id( "actionsNegotiation" ) )
				.click();
		driver.findElement( By.id( "removeSelectedNegotiations" ) ).click();
		sleep( VERY_LONG );
		getAssistants().getModalAssistant( driver ).confirmAlert();
	}

	public void deleteLastSale(int nrLine) {
		driver.findElement( By.id( "negotiationSelected" + nrLine + "" ) ).click();

		driver.findElement(
				By.id( "actionsNegotiation" ) )
				.click();
		driver.findElement( By.id( "removeSelectedNegotiations" ) ).click();
		getAssistants().getModalAssistant( driver ).confirmAlert();
	}

	public void apagaLinhaItens(int linha) {
		linha = linha - 1;
		driver.findElementByXPath(
				"//*[@id='removeNegotiationItem" + linha + "']/i" ).click();

		if (driver.findElement( By.id( "negotiationItemTotal0" ) ).getText().equals( null )) {
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
		}
	}

	public void removeAllBlankLines() {
		for (int i = getTableRows(); i != 1; i--)
			driver.findElement(
					By.xpath( "//*[@id='negotiation']/div[1]/div[4]/div/table/tbody/tr["
							+ i + "]/td[6]" ) ).click();
	}

	public void setSaleClient(String client) {
		if (!client.isEmpty())
			getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( this.saleClient, client );
	}

	public void setSaleNumber(String number) {
		if (!number.isEmpty()) {
			this.saleNumber.clear();
			this.saleNumber.sendKeys( number );
		}
	}

	public void setSaleDate(String date) {
		if (!date.isEmpty()) {
			this.saleEmissionDate.clear();
			this.saleEmissionDate.sendKeys( date );
		}
	}

	public void setSalePaymentType(String type) {
		Select select = new Select( driver.findElement( By.id( "negotiationInstallmentQuantity" ) ) );
		select.selectByVisibleText( type );
	}

	public void setPaymentMaturityDate(String date) {
		if (!date.isEmpty()) {
			this.salePaymentMaturityDate.clear();
			this.salePaymentMaturityDate.sendKeys( date );
		}
	}

	public String getMessageTopModal() {
		waitForElementPresent( By.cssSelector( ".container .container-message" ) );
		return this.driver.findElementByCssSelector( ".container .container-message" ).getText();
	}

	public void addNewSale() {
		sleep( VERY_LONG );

		if (!(getTableRows() == 0))
			lastSaleNumber = Integer.parseInt( driver.findElement(
					By.xpath( "//*[@id='conteudo']/div/div/table/tbody/tr[1]/td[2]" ) ).getText() );

		waitForElementPresent( By.id( "newNegotiation" ) );
		this.buttonCreateNewSale.click();
	}

	public void saveNewSale() {
		buttonSave.click();
		sleep( VERY_LONG );
	}

	public void cancelSale() {
		this.btnCancelNewSale.click();
	}

	public void clickBtnEditBottom() {
		this.btnEditSaleBottom.click();
	}

	public void setObservationSale(String observation) {
		this.observationSale.sendKeys( observation );
	}

	public void setSearchField(String text) {
		this.searchField.clear();
		this.searchField.sendKeys( text );
	}

	public void clickOnRow(String line) {
		driver.findElementByXPath( "//div[@id='conteudo']/div/div/table/tbody/tr/td[contains(text(),'" + line + "')]" )
				.click();
	}

	public String getTooltypeMessage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (String) js
				.executeScript( "return document.getElementById('editNegotiationUpper').getAttribute('data-original-title')" );
	}

	public void clickBtnEditUpper() {
		this.btnEditSaleUpper.click();
	}

	public void setTypeItem(int nrLine, Integer typeItem) {
		driver.findElement( By.id( "negotiationItem" + nrLine ) ).clear();
		createItemByQuickAdd( driver.findElementById( "negotiationItem" + nrLine ),
				"Item" + randomNumber.nextInt( 10000 ),
				typeItem, 0 );
	}

	public void createItemByQuickAdd(WebElement autoCompleteField, String keysToSend, Integer type, Integer index) {
		String autoCompleteFieldId = getAssistants().getAutoCompleteAssistant( driver ).extractAutoCompleteId(
				autoCompleteField );
		autoCompleteField = getAssistants().getAutoCompleteAssistant( driver ).waitForAutoCompleteNotStale(
				autoCompleteFieldId );
		autoCompleteField.clear();

		sleep( VERY_LONG );
		autoCompleteField.sendKeys( keysToSend );

		sleep( VERY_LONG );
		driver.findElement( By.className( "addItem" ) ).click();
		sleep( VERY_LONG );
		driver.findElementsById( "itemType" ).get( index ).findElements( By.tagName( "option" ) ).get( type ).click();

		sleep( VERY_LONG );
		List<WebElement> list = driver.findElements( By.id( "saveItem" ) );
		list.get( index ).click();
	}

	public String getValuesGrid(String getText) {
		return driver.findElementByXPath( "//td[contains(text(),'" + getText + "')]" ).getText();
	}

	public void clearClient() {
		sleep( VERY_LONG );
		this.saleClient.clear();
		sleep( VERY_LONG );
		this.saleClient.sendKeys( Keys.ENTER );
	}

	public void clearNrSale() {
		this.saleNumber.clear();
	}

	public void clearDateSale() {
		this.saleEmissionDate.clear();
	}

	public void clearFirstDueDate() {
		this.salePaymentMaturityDate.clear();
	}

	public void clearObservation() {
		this.observationSale.clear();
	}

	public String ClearItemAndSave(int nrLine, String item, Integer type) {
		sleep( VERY_LONG );
		driver.findElementById( "negotiationItem" + nrLine ).clear();
		sleep( VERY_LONG );
		driver.findElementById( "negotiationItem" + nrLine ).sendKeys( Keys.ENTER );
		sleep( VERY_LONG );
		saveNewSale();
		String getAlertMsg = getAssistants().getNotificationAssistant( driver ).getAlertMessageText();
		return getAlertMsg;
	}

	public String ClearQtdAndSave(int nrLine, String item) {
		if (!driver.findElementByCssSelector( ".container .container-message" ).isDisplayed()) {
			driver.findElementById( "negotiationItemQuantity" + nrLine ).clear();
			saveNewSale();
			String getAlertMsg = getAssistants().getNotificationAssistant( driver ).getAlertMessageText();
			driver.findElementById( "negotiationItemQuantity" + nrLine ).sendKeys( item );
			return getAlertMsg;
		} else {
			String getAlertMsg = getAssistants().getNotificationAssistant( driver ).getAlertMessageText();
			return getAlertMsg;
		}
	}

	public String ClearValueItemAndSave(int nrLine, String item) {
		if (!driver.findElementByCssSelector( ".container .container-message" ).isDisplayed()) {
			driver.findElementById( "negotiationItemPrice" + nrLine ).clear();
			saveNewSale();
			String getAlertMsg = getAssistants().getNotificationAssistant( driver ).getAlertMessageText();
			driver.findElementById( "negotiationItemPrice" + nrLine ).sendKeys( item );
			return getAlertMsg;
		} else {
			String getAlertMsg = getAssistants().getNotificationAssistant( driver ).getAlertMessageText();
			return getAlertMsg;
		}

	}

	public void setClient(String client) {
		this.saleClient.sendKeys( client );
		sleep( VERY_LONG );
	}

	public void setNrSale(String nrSale) {
		this.saleNumber.sendKeys( nrSale );
	}

	public void setDateSale(String date) {
		this.saleEmissionDate.sendKeys( date );
	}

	public void setDateFirstDueDate(String dateForstDue) {
		this.salePaymentMaturityDate.sendKeys( dateForstDue );
	}

	public void removeLineWithDates(String line) {
		driver.findElementById( "removeNegotiationItem" + line ).click();
		getAssistants().getModalAssistant( driver ).confirmAlert();
	}

	public Long getNumberRowResume() {
		sleep( VERY_LONG );
		return (Long) this.javascript( "return jQuery('table.table-flat tbody tr:visible').size();" );
	}

	public Long getNumberRowGridList() {
		return (Long) this.javascript( "return jQuery('table.table-default tbody tr:visible').size();" );
	}

	public String getValueLiquid() {
		return driver.findElementByXPath( "//*[@id='conteudo']/div/div/table/tbody/tr[1]/td[5]/div" ).getText();
	}

	public Boolean isDisplayerFreight() {
		return salefreightValue.isDisplayed();
	}

	public String getWarningDeleteMoreThanOneFirstLine() {
		sleep( VERY_LONG );
		return driver.findElementByXPath( "//*[@id='moreThanOneSelectedSale']/p[1]" ).getText();
	}

	public String getWarningDeleteFirstLine() {
		return driver.findElementByXPath( "//*[@id='selectedSale']/p[1]" ).getText();
	}

	public String getWarningDeleteMoreThanOneSecondLine() {
		return driver.findElementByXPath( "//*[@id='selectedSale']/p[2]" ).getText();
	}

	public String getWarningDeleteSecondLine() {
		return driver.findElementByXPath( "//*[@id='moreThanOneSelectedSale']/p[2]" ).getText();
	}

	public void clickBtnWarningDelete() {
		btnWarningDelete.click();
	}

	public String msgDeleteMoreThanOneFirstLine() {
		return "Não é possível excluir a(s) venda(s) selecionada(s) pois existem um ou mais recebimentos associados.";
	}

	public String msgDeleteOneFirstLine() {
		return "Não é possível editar/excluir a venda selecionada pois existem um ou mais recebimentos associados.";
	}

	public String msgDeleteMoreThanOneSecondtLine() {
		return "Por favor, desfaça o(s) recebimento(s) no financeiro para poder excluir esta(s) venda(s).";
	}

	public String msgDeleteSecondLine() {
		return "Por favor, desfaça o(s) recebimento(s) no financeiro para poder excluir esta venda.";
	}

	public void clickBtnSearch() {
		this.btnSearchList.click();
		sleep( VERY_LONG );
	}
}
