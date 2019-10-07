package com.contaazul.auto.selenium.financeiro.pages;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.TestNGException;

import com.contaazul.auto.selenium.WebPage;

public class CreateIncomePage extends WebPage {

	@FindBy(id = "financeiro_memo")
	private WebElement incomeName;

	@FindBy(id = "valor")
	private WebElement value;

	@FindBy(id = "newIdConta")
	private WebElement bankAccount;

	@FindBy(id = "dtVencimento")
	private WebElement expirationDate;

	@FindBy(id = "idCategoria")
	private WebElement incomeCategory;

	@FindBy(xpath = "//*[@id='formStatement']/div[1]/label[9]/div/span[1]")
	private WebElement received;

	@FindBy(xpath = "//*[@id='formStatement']/div[1]/label[7]/div/span[1]")
	private WebElement repeat;

	@FindBy(id = "idClienteFornecedor")
	private WebElement customer;

	@FindBy(id = "dtEmissao")
	private WebElement competenceDate;

	@FindBy(id = "centroCustoNewSelect")
	private WebElement costCenter;

	@FindBy(id = "observacao")
	private WebElement observations;

	@FindBy(id = "file")
	private WebElement attachment;

	@FindBy(css = "button.btn.more-options")
	private WebElement moreOptionsButton;

	@FindBy(css = "button.btn.save_form.btn-primary.addStatement")
	private WebElement addIncomeButton;

	@FindBy(xpath = "//*[@id='popupNotify']/div/div[1]")
	private WebElement messageTopModal;

	@FindBy(css = "button.close")
	private WebElement closeButton;

	@FindBy(css = "li a.save_new")
	private WebElement addDropDownContinue;

	@FindBy(css = "li a.btn-continue-adding")
	private WebElement addDropDownContinueKeepFields;

	@FindBy(id = "newModalTitle")
	private WebElement modalTitle;

	@FindBy(xpath = "//*[@id='newPopupManagerReplacement']/div[4]/div/button")
	private WebElement cancelButton;

	@FindBy(xpath = "//*[@id='addFinance']/button[1]")
	public WebElement btnCreateNew;

	@FindBy(id = "act-finance-buttons")
	private WebElement isDisplayedTransactions;

	@FindBy(id = "newIdConta")
	private WebElement nameAccountBank;

	public CreateIncomePage(WebDriver driver) {
		super( (RemoteWebDriver) (driver) );
		PageFactory.initElements( driver, this );
	}

	public void clickDate() {
		expirationDate.click();

	}

	public String getIncomeCategory() {
		return incomeCategory.getAttribute( "value" );
	}

	public Boolean isReceived() {
		return (Boolean) driver.executeScript( "return jQuery('span.checkbox.baixado').hasClass('selected')" );
	}

	public Boolean isRepeat() {
		return (Boolean) driver.executeScript( "return jQuery('span.checkbox.repetir').hasClass('selected')" );
	}

	public String getCustomer() {
		return customer.getAttribute( "value" );
	}

	public String getCompetenceDate() {
		return competenceDate.getAttribute( "value" );
	}

	public String getCostCenter() {
		return costCenter.getAttribute( "value" );
	}

	public String getObservations() {
		return observations.getAttribute( "value" );
	}

	public String getMessageTopModal() {
		waitForElementPresent( By.xpath( "//*[@id='popupNotify']/div/div[1]" ) );
		return messageTopModal.getText();
	}

	public String getExpirationDate() {
		return expirationDate.getAttribute( "value" );
	}

	public String getIncomeName() {
		return incomeName.getAttribute( "value" );
	}

	public String getValue() {
		return value.getAttribute( "value" );
	}

	public String getModalTitle() {
		return modalTitle.getText();
	}

	public String getBankAccount() {
		return bankAccount.getAttribute( "value" );
	}

	public void setObservations(String observations) {
		if (!observations.equals( getObservations() )) {
			openMoreOptions();
			clearField( this.observations );
			this.observations.sendKeys( observations );
		}
	}

	public void createCategoryName(String name) {
		CreateCategoryPage.validateCategoryNameLength( name );
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndCreateNew( incomeCategory, name );
	}

	public void setCostCenter(String costCenter) {
		if (!costCenter.isEmpty()) {
			openMoreOptions();
			getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( this.costCenter,
					costCenter );
		}
	}

	public void setCompetenceDate(String competenceDate) {
		if (!competenceDate.isEmpty()) {
			openMoreOptions();
			clearField( this.competenceDate );
			this.competenceDate.sendKeys( competenceDate );
		}
	}

	public void setIncomeName(String incomeNameValue) {
		waitForElementNotStale( By.id( "financeiro_memo" ) );
		incomeName.clear();
		sleep( VERY_LONG );
		incomeName.sendKeys( incomeNameValue );
		sleep( VERY_LONG );
	}

	public void setValue(String valueValue) {
		value.clear();
		sleep( VERY_LONG );
		value.sendKeys( valueValue );
		sleep( VERY_LONG );
	}

	public void setBankAccount(String bankAccount) {
		sleep( VERY_LONGEST );
		if (!bankAccount.isEmpty())
			getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting(
					driver.findElementById( "newIdConta" ),
					bankAccount );
	}

	public void clearBankAccount() {
		this.bankAccount.clear();
	}

	public void editBank(String name) {
		sleep( VERY_LONG );
		bankAccount.clear();
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( nameAccountBank, name );
		clickAddIncomeButton();
		sleep( VERY_QUICKLY );
	}

	public void setExpirationDate(String expirationDate) {
		if (!expirationDate.equals( getExpirationDate() )) {
			clearField( this.expirationDate );
			this.expirationDate.sendKeys( expirationDate );
		}
	}

	public void setIncomeCategory(String incomeCategory) {
		if (!incomeCategory.isEmpty())
			getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( this.incomeCategory,
					incomeCategory );
	}

	public void setReceived(Boolean received) {
		if (!received.equals( isReceived() ))
			this.received.click();
	}

	public void setRepeat(Boolean repeat) {
		if (!repeat.equals( isRepeat() ))
			this.repeat.click();
	}

	public void setCustomer(String customer) {
		if (!customer.isEmpty()) {
			openMoreOptions();
			sleep( VERY_LONG );
			getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( this.customer,
					customer );
		}
	}

	public void clearField(WebElement field) {
		field.clear();
	}

	public void openMoreOptions() {
		if (!isOpenMoreOptions())
			this.moreOptionsButton.click();
	}

	public Boolean isOpenMoreOptions() {
		return this.moreOptionsButton.getText().contains( "menos" );
	}

	public void closeMoreOptions() {
		if (isOpenMoreOptions())
			this.moreOptionsButton.click();
	}

	public void clickAddIncomeButton() {
		sleep( VERY_LONG );
		driver.findElement( By.id( "newModalTitle" ) ).click();
		this.addIncomeButton.click();
		sleep( VERY_LONG );
	}

	public void waitTopMessageNotPresent() {
		waitForElementNotPresent( By.xpath( "//*[@id='popupNotify']/div/div[1]" ) );
	}

	public void waitTopNotificationNotPresent() {
		waitForElementNotPresent( By.xpath( "//*[@id='notification']/div/div[1]" ) );
	}

	public void closePage() {
		this.closeButton.click();
	}

	public String getTopNotificationText() {
		String xpathElement = "//*[@id='notification']/div/div[1]";
		return this.driver.findElementByXPath( xpathElement ).getText();
	}

	public String getSucessMessageExpected(String incomeName, String value, String expirationDate, Boolean received,
			Boolean transaction) {
		String msgDayPart = received ? ", no dia " : ", agendado para o dia ";
		String msgTypeTransaction = transaction ? "Receita '" : "Despesa '";
		return msgTypeTransaction + incomeName.trim() + "', de R$ " + value.trim() + msgDayPart
				+ expirationDate.trim();
	}

	public void clickAddIncomeAndContinue(boolean keepFields) {
		openAddDropDown();
		if (keepFields)
			this.addDropDownContinueKeepFields.click();
		else
			this.addDropDownContinue.click();

	}

	private void openAddDropDown() {
		if (!isOpenAddDropDown())
			javascript( "jQuery('div.btn-group.act-save').addClass('open')" );
	}

	private boolean isOpenAddDropDown() {
		return (Boolean) javascript( "return jQuery('div.btn-group.act-save').hasClass('open')" );
	}

	public void clickCancelButton() {
		this.cancelButton.click();
	}

	public boolean isPdfDownloaded(String clientName) {

		File diretorioDownload = null;
		diretorioDownload = new File( "/home/" + System.getProperty( "user.name" ) + "/Downloads" );
		if (!diretorioDownload.canRead())
			diretorioDownload = new File( "C:/Users/" + System.getProperty( "user.name" ) + "/Downloads" );
		if (!diretorioDownload.canRead())
			throw new TestNGException(
					"Não conseguiu acesso de leitura para o diretório de Downloads default. Testou usar: "
							+ diretorioDownload.getPath() );

		for (final File arquivoDoDiretorio : diretorioDownload.listFiles()) {

			String nomeDoArquivo = arquivoDoDiretorio.getName();

			String dataDeHoje = new SimpleDateFormat( "dd-MM-yyyy" ).format( new Date() );

			if (nomeDoArquivo.matches( clientName + "_" + dataDeHoje + ".+" + ".pdf" )) {
				Date ultimaModificacaoArquivo = new Date( arquivoDoDiretorio.lastModified() );
				long diferenca = (new Date().getTime() - ultimaModificacaoArquivo.getTime());
				if ((diferenca / (60 * 1000) % 60) < 5) {
					arquivoDoDiretorio.delete();

					return true;
				}
			}
		}
		return false;
	}

	public void newExpense() {
		sleep( VERY_QUICKLY );
		btnCreateNew.click();
	}

	public void selectAllTransactionAndDelete(ImportBankExtractPage importBankExtractPage) {
		sleep( VERY_LONG );
		if (!isDisplayedTransactions.isDisplayed()) {
			getAssistants().getListingAssistant( driver ).checkAllItems();
			importBankExtractPage.selectAction( "Excluir" );
		}
	}
}
