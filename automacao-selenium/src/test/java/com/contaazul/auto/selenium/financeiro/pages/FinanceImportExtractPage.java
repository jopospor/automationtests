package com.contaazul.auto.selenium.financeiro.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.Reporter;

import com.contaazul.auto.selenium.WebPage;

public class FinanceImportExtractPage extends WebPage {

	@FindBy(id = "file")
	protected WebElement selecionarArquivoOFX;
	@FindBy(id = "newIdConta")
	protected WebElement newConta;
	@FindBy(xpath = "//button[contains(text(),'Continuar')]")
	protected WebElement btnContinuar;
	@FindBy(className = "container-message")
	protected WebElement popUp;
	@FindBy(xpath = "//button[contains(text(), 'Importar extrato')]")
	protected WebElement btnImportarExtrato;
	@FindBy(className = "close")
	protected WebElement btnFecharTelaConciliacao;
	@FindBy(xpath = "//button[contains(text(),'Ações')]")
	protected WebElement pageActions;
	@FindBy(linkText = "Excluir Agora")
	protected WebElement btnExcluirAgora;
	@FindBy(xpath = "//a[contains(@class,'periodToday new-financ')]")
	protected WebElement dateConciliation;
	@FindBy(xpath = "//*[@id='popup-bank-filter']/button")
	protected WebElement bankConciliation;
	@FindBy(id = "conciliate")
	protected WebElement reconcileRelease;
	@FindBy(xpath = "//*[@id='result-found']/div/table/tbody/tr[1]/td[1]")
	protected WebElement checkBoxSuggestions;
	@FindBy(xpath = "//div[@id='newPopupManagerReplacement']/div[2]/div[2]/div[2]/div[2]/button[contains(@class, 'btn dropdown-toggle periodToggle')]")
	protected WebElement openDropDownDate;
	@FindBy(xpath = "//div[contains (@class,'summary-item qty')]/span")
	protected WebElement numberReleases;

	// Opção "IMPORTAR EXTRATO" presente no menu lateral do financeiro
	@FindBy(xpath = "//a[5]/span/span")
	// Descrição e valor para lançamento de crédito
	protected WebElement btnMenuImportarExtrado;
	@FindBy(xpath = "//div[@id='statement-list-container']/table[3]/tbody/tr/td[3]/span")
	protected WebElement memoDescription1;
	@FindBy(xpath = "//*[@id='statement-list-container']/table[3]/tbody/tr/td[4]")
	protected WebElement value1;

	// Descrição e valor para lançamento de débito
	@FindBy(xpath = "//div[@id='statement-list-container']/table[3]/tbody/tr[2]/td[3]/span")
	protected WebElement memoDescription2;
	@FindBy(xpath = "//*[@id='statement-list-container']/table[3]/tbody/tr[2]/td[4]")
	protected WebElement value2;

	// Totais (Receita + Despesa + Período)
	public String getTotalRevenue() {
		return driver.findElementByXPath( "//*[@id='conteudo']/div/div[2]/div[2]/div/div[5]/div[3]/div[1]/div[1]/span" )
				.getText();
	}

	public String getTotalExpense() {
		return driver.findElementByXPath( "//*[@id='conteudo']/div/div[2]/div[2]/div/div[5]/div[3]/div[1]/div[2]/span" )
				.getText();
	}

	public String getTotalPeriod() {
		sleep( VERY_LONG );
		return driver.findElementByXPath( "//*[@id='conteudo']/div/div[2]/div[2]/div/div[5]/div[3]/div[1]/div[3]/span" )
				.getText();
	}

	public String getNumberReleases() {
		return numberReleases.getText();
	}

	public FinanceImportExtractPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void selectBankAccountConciliation() {
		bankConciliation.click();
	}

	public void clickReconcileThisRelease() {
		sleep( VERY_LONGEST );
		reconcileRelease.click();
	}

	public void selectCheckBoxSuggestionsConciliation() {
		sleep( VERY_LONGEST );
		Reporter.log( "Prestes a clicar no radio após um sleepzinho" );
		checkBoxSuggestions.click();
	}

	public void selectOptionBank(String Banco) {
		driver.findElementByXPath(
				"//li[@class= 'modal-filter-dropdown-item']/a[contains(text(), '" + Banco + "')]/span" ).click();
		driver.findElementByXPath( "//*[@id='popup-bank-filter']/ul/li[2]/button" ).click();
	}

	public void selectDateConciliation(Period period) {
		openDropDownDate.click();
		sleep( VERY_QUICKLY );
		driver.findElementByLinkText( "" + period + "" ).click();
	}

	public void clickImportarExtrato() {
		sleep( 5000 );
		btnImportarExtrato.click();
	}

	public void selecionarArquivoOFX(String arquivo) {
		driver.switchTo().frame( "idUpload" );

		try {
			getAssistants().getFileUploadAssistant( driver ).remoteUploadFile(
					selecionarArquivoOFX,
					"//src//test//resources//file_attachments//" + arquivo );
		} catch (IOException e) {
			Assert.fail( "AVISO: A importação do arquivo " + arquivo
					+ " falhou.", e );
		}

		driver.switchTo().defaultContent();
		sleep( VERY_QUICKLY );
	}

	public void informarNovaContaBancaria(String conta) {
		getAssistants().getAutoCompleteAssistant( driver )
				.sendKeysAndCreateNew( newConta, conta );
	}

	public void informarContaBancariaExistente(String conta) {
		getAssistants().getAutoCompleteAssistant( driver )
				.sendKeysAndSelectExisting( newConta, conta );
		sleep( VERY_QUICKLY );
	}

	public void clickContinuar() {
		sleep( VERY_LONG );
		waitForElementPresent( By
				.xpath( "//button[contains(text(),'Continuar')]" ) );
		btnContinuar.click();
	}

	public String getPopUpResult() {
		return popUp.getText();
	}

	public void clickFecharTelaConciliacao() {
		sleep( VERY_LONG );
		waitForElementNotStale( By.xpath( "//button[contains(text(),'×')]" ) );
		btnFecharTelaConciliacao.click();
	}

	public void apagarTodosRegistrosDaLista() {
		if (!isElementVisible( "importBlankslate" )) {
			driver.findElement( By.xpath( "//th[@id='importSelector']/span/span/img" ) )
					.click();
			selecionarAcao( "Excluir" );
			sleep( 5000 );
		}
	}

	private boolean isElementVisible(String id) {
		JavascriptExecutor jay = (JavascriptExecutor) driver;
		return (((String) jay.executeScript(
				"return document.getElementById('" + id + "').getStyle('visibility')" ))
				.matches( "visible" )
		&& !((String) jay
				.executeScript( "return document.getElementById('" + id + "').getStyle('display')" ))
				.matches( "none" ));
	}

	public void selecionarAcao(String action) {
		pageActions.click();

		if (action.equals( "Adicionar como lançamentos" )) {
			driver.findElementById( "selectedActions" )
					.findElement( By.linkText( "Adicionar como lançamentos" ) )
					.click();
		}
		if (action.equals( "Adicionar como transferências" )) {
			driver.findElementById( "selectedActions" )
					.findElement( By.linkText( "Adicionar como transferências" ) )
					.click();
		}
		if (action.equals( "Excluir" )) {
			driver.findElementById( "selectedActions" )
					.findElement( By.linkText( "Excluir" ) ).click();
			btnExcluirAgora.click();
		}
	}

	public void clickMenuImportarExtrato() {
		btnImportarExtrato.click();
	}

	public String getMemoDescription1Text() {
		return memoDescription1.getText();
	}

	public String getValue1Text() {
		return value1.getText();
	}

	public String getMemoDescription2Text() {
		return memoDescription2.getText();
	}

	public void waitForImportMessageToClose() {
		waitForNotText( By.className( "container-message" ), "Extrato bancário importado com sucesso!" );
	}

	public String getValue2Text() {
		return value2.getText();
	}

}
