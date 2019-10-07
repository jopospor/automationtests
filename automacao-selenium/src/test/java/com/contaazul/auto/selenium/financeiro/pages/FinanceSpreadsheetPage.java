package com.contaazul.auto.selenium.financeiro.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.Reporter;

import com.contaazul.auto.selenium.WebPage;

public class FinanceSpreadsheetPage extends WebPage {

	@FindBy(xpath = "//div[@id='addExtract']/button")
	protected WebElement btnAdicionar;
	@FindBy(xpath = "//div[@id='addExtract']/ul/li[1]/a/span[1]")
	protected WebElement addNovaReceita;
	@FindBy(xpath = "//div[@id='addExtract']/ul/li[2]/a/span[1]")
	protected WebElement addNovaDespesa;
	@FindBy(xpath = "//div[@id='addExtract']/ul/li[3]/a/span[1]")
	protected WebElement addNovaTransferencia;
	@FindBy(xpath = "//div[@id='addExtract']/ul/li[4]/a/span[1]")
	protected WebElement addOfxExtrato;
	@FindBy(xpath = "//div[@id='addExtract']/ul/li[5]/a/span[1]")
	protected WebElement addPlanilhaExtrato;
	@FindBy(xpath = "/html/body/div[7]/h1/span")
	protected WebElement tituloTelaImportacao;
	@FindBy(xpath = "//button[contains(text(),'Baixar planilha padrão do ContaAzul')]")
	protected WebElement btnBaixarPlanilha;
	@FindBy(xpath = "//button[contains(text(),'Minha planilha já está no Padrão')]")
	protected WebElement btnPlanilhaJaNoPadrao;
	@FindBy(xpath = "//button[contains(text(),'Importar')]")
	protected WebElement btnImportar;
	@FindBy(id = "newIdConta")
	protected WebElement bankField;
	@FindBy(id = "file")
	protected WebElement fileField;
	@FindBy(xpath = "//html/body/div[7]/div[1]")
	protected WebElement warningPanel;
	@FindBy(xpath = "//div[@id='fileContentDetais']/div/div[1]/span/text()")
	protected WebElement sucessPanel;
	@FindBy(xpath = "//button[contains(text(),'Importar movimentações selecionadas')]")
	protected WebElement btnImportarMovSelecionadas;
	@FindBy(className = "container-message")
	protected WebElement popUp;
	@FindBy(xpath = "//th[@id='statementSelector']/span/span/img")
	private WebElement selectAll;
	@FindBy(linkText = "Excluir")
	private WebElement btnDelete;
	@FindBy(linkText = "Cancelar")
	private WebElement btnCancel;
	@FindBy(xpath = "//button[contains(text(),'Ações')]")
	private WebElement btnAction;
	@FindBy(linkText = "Excluir Agora")
	private WebElement btnDelete2;
	@FindBy(xpath = "//div[@id='conteudo']/div/div[2]/div/a[4]/span/span")
	private WebElement menuExtrato;
	@FindBy(xpath = "//div[@id='conteudo']/div/div/div[2]/button")
	public WebElement btnMostrarTodos;
	@FindBy(xpath = "//div[@id='conteudo']/div/div/div[2]/ul/li[5]/a/span")
	public WebElement filtroMostrarTodos;
	@FindBy(xpath = "/html/body/div[7]/div[1]")
	private WebElement wrongFileMessage;

	public final static String ATTACHMENTS_LOCATION = "//src//test//resources//file_attachments//";

	public FinanceSpreadsheetPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void addOptions(String option) {
		btnAdicionar.click();

		if (option.equals( "Nova Receita" ))
			addNovaReceita.click();
		if (option.equals( "Nova Despesa" ))
			addNovaDespesa.click();
		if (option.equals( "Nova Transferência" ))
			addNovaTransferencia.click();
		if (option.equals( "A partir de um extrato" ))
			addOfxExtrato.click();
		if (option.equals( "A partir de uma planilha" ))
			addPlanilhaExtrato.click();
	}

	public void downloadTemplate() {
		addOptions( "A partir de uma planilha" );
		btnBaixarPlanilha.click();

		sleep( VERY_LONG );

		downloadSpreadsheetDialogWindow();
	}

	public void downloadSpreadsheetDialogWindow() {
		try {
			sleep( VERY_LONG );

			Robot robot = new Robot();
			robot.keyPress( KeyEvent.VK_ALT );
			robot.keyPress( KeyEvent.VK_S );

			sleep( VERY_LONG );

			robot.keyRelease( KeyEvent.VK_ALT );
			robot.keyPress( KeyEvent.VK_ENTER );

			sleep( VERY_LONG );

			robot.keyRelease( KeyEvent.VK_ENTER );
		} catch (AWTException e) {
			Reporter.log( "Erro na tela de 'Save' da planilha padrão: " + e, true );
		}
	}

	public void openImportWindow() {
		btnPlanilhaJaNoPadrao.click();
	}

	public void ImportSpreadsheetWindow(String bank, String file) {
		getAssistants().getAutoCompleteAssistant( driver )
				.sendKeysAndSelectExisting( bankField, bank );

		sleep( VERY_LONG );

		selecionarPlanilha( file );
		btnImportar.click();
	}

	public void selecionarPlanilha(String arquivo) {
		driver.switchTo().frame( "idUpload" );
		try {
			getAssistants().getFileUploadAssistant( driver ).remoteUploadFile(
					fileField, ATTACHMENTS_LOCATION + arquivo );
		} catch (IOException e) {
			Assert.fail( "AVISO: A importação do arquivo " + arquivo
					+ " falhou.", e );
		} catch (ElementNotVisibleException e) {
			Assert.fail( "O campo para a seleção do arquivo de importação não está visível!.", e );
		}
		driver.switchTo().defaultContent();
	}

	public void confirmImport() {
		btnImportarMovSelecionadas.click();

		waitForText( By.className( "container-message" ),
				"Importação realizada com sucesso!" );
	}

	public String getFirstRegisterDate() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr/td[3]" ) )
				.getText();
	}

	public String getFirstRegisterCategory() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr/td[4]/span" ) )
				.getText();
	}

	public String getFirstRegisterDescription() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr/td[4]/div/span" ) )
				.getText();
	}

	public String getFirstRegisterValue() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr/td[5]" ) )
				.getText();
	}

	public String getFirstRegisterBalance() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr/td[6]" ) )
				.getText();
	}

	public String getSecondRegisterDate() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[2]/td[3]" ) )
				.getText();
	}

	public String getSecondRegisterCategory() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[2]/td[4]/span" ) )
				.getText();
	}

	public String getSecondgetRegisterDescription() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[2]/td[4]/div/span" ) )
				.getText();
	}

	public String getSecondRegisterValue() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[2]/td[5]" ) )
				.getText();
	}

	public String getSecondRegisterBalance() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[2]/td[6]" ) )
				.getText();
	}

	public String getThirdRegisterDate() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[3]/td[3]" ) )
				.getText();
	}

	public String getThirdRegisterCategory() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[3]/td[4]/span" ) )
				.getText();
	}

	public String getThirdRegisterDescription() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[3]/td[4]/div/span" ) )
				.getText();
	}

	public String getThirdRegisterValue() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[3]/td[5]" ) )
				.getText();
	}

	public String getThirdRegisterBalance() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[3]/td[6]" ) )
				.getText();
	}

	public String getFourthRegisterDate() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[4]/td[3]" ) )
				.getText();
	}

	public String getFourthRegisterCategory() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[4]/td[4]/span" ) )
				.getText();
	}

	public String getFourthRegisterDescription() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[4]/td[4]/div/span" ) )
				.getText();
	}

	public String getFourthRegisterValue() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[4]/td[5]" ) )
				.getText();
	}

	public String getFourthRegisterBalance() {
		return driver.findElement(
				By.xpath( "//div[@id='statement-list-container']/table/tbody/tr[4]/td[6]" ) )
				.getText();
	}

	public String getTotalReceitas() {
		return driver.findElement(
				By.xpath( "//div[@id='conteudo']/div/div[2]/div[2]/div/div[5]/div[3]/div/div/span" ) )
				.getText();
	}

	public String getTotalDespesas() {
		return driver.findElement(
				By.xpath( "//div[@id='conteudo']/div/div[2]/div[2]/div/div[5]/div[3]/div/div[2]/span" ) )
				.getText();
	}

	public String getTotalPeriodo() {
		return driver.findElement(
				By.xpath( "//div[@id='conteudo']/div/div[2]/div[2]/div/div[5]/div[3]/div/div[3]/span" ) )
				.getText();
	}

	public String getNumeroLancamentos() {
		return driver.findElement(
				By.cssSelector( "div.summary-item.qty > span" ) )
				.getText();
	}

	public void apagarTodosRegistrosDaLista() {
		menuExtrato.click();
		btnMostrarTodos.click();
		filtroMostrarTodos.click();

		sleep( VERY_LONG );

		selectAll.click();
		btnAction.click();
		btnDelete.click();

		sleep( VERY_LONG );

		btnDelete2.click();

		waitForText( By.className( "container-message" ),
				"Lançamento(s) removido(s) com sucesso" );
	}
}
