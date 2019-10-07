package com.contaazul.auto.selenium.fiscal.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.contaazul.auto.selenium.WebPage;

public class NfePage extends WebPage {

	public NfePage(WebDriver driver) {
		super( driver );
	}

	@FindBy(id = "cliente")
	private WebElement nameClient;
	@FindBy(id = "Produto_0_")
	private WebElement nameItem;
	@FindBy(id = "item_0_itemQty")
	private WebElement nrQuantity;
	@FindBy(id = "item_0_vlUnitario")
	private WebElement vlItem;
	@FindBy(id = "tpFrete")
	private WebElement collectFreight;
	@FindBy(id = "Transportadora")
	private WebElement transporter;
	@FindBy(id = "vlFrete")
	private WebElement vlFreight;
	@FindBy(id = "selectIcms0")
	private WebElement tributarySituationIcms;
	@FindBy(id = "modtICMS0")
	private WebElement bcICMS;
	@FindBy(id = "selectIpi0")
	private WebElement tributarySituationIpi;
	@FindBy(id = "selectPis0")
	private WebElement tributarySituationPis;
	@FindBy(id = "valorBCPIS0")
	private WebElement valuePis;
	@FindBy(id = "aliquotaPIS0")
	private WebElement aliquotPis;
	@FindBy(id = "valorBCIPI0")
	private WebElement valueCalculation;
	@FindBy(id = "aliquotaIPI0")
	private WebElement aliquotIpi;
	@FindBy(id = "selectCofins0")
	private WebElement tributarySituationCofins;
	@FindBy(id = "valorBCCOFINS0")
	private WebElement basedCalculationConfins;
	@FindBy(id = "aliquotaCOFINS0")
	private WebElement aliquotConfins;
	@FindBy(id = "dsCfop0")
	private WebElement cfop;
	@FindBy(id = "valorBCICMS0")
	private WebElement basedCalculationIcms;
	@FindBy(id = "aliquotaICMS0")
	private WebElement aliquotIcms;
	@FindBy(xpath = "//button[contains(text(),'Ações')]")
	protected WebElement pageActions;
	@FindBy(xpath = "//input[@id='formEmissaoNotaFiscal']")
	private WebElement save;
	@FindBy(id = "formEmissaoNotaFiscal_emissao_modalidadeTransporte")
	private WebElement modTransporter;
	@FindBy(id = "totalFrete")
	private WebElement vlFreightNf;
	@FindBy(id = "modtICMSST0")
	private WebElement modBcIcms;
	@FindBy(id = "valorBCICMSST0")
	private WebElement subBcIccms;
	@FindBy(id = "aliquotaICMSST0")
	private WebElement subAliquotIcms;
	@FindBy(id = "nrSerie")
	private WebElement nrSerie;
	@FindBy(id = "percentualMargemValorAdicionadoICMSST0")
	private WebElement mvaSt;
	@FindBy(id = "aliquotaCalculoCredito0")
	private WebElement aliquotApplyable;
	@FindBy(id = "formEmissaoNotaFiscal_emissao_volume_quantidadeVolumesTransportados")
	private WebElement qtyTransporter;
	@FindBy(id = "creditoICMSAproveitavel0")
	private WebElement creditIcms;
	@FindBy(id = "percentualReducaoBCICMS0")
	private WebElement percentReduceBcIcms101;
	@FindBy(id = "percentualReducaoBCICMSST0")
	private WebElement percentReduceBcIcms201;

	public void setPercentReduceBcIcms201(String percent) {
		percentReduceBcIcms201.sendKeys( percent );
	}

	public void setModBcIcms(String typeMod) {
		modBcIcms.sendKeys( typeMod );
	}

	public void setPercentReduceBcIms101(String percent) {
		percentReduceBcIcms101.sendKeys( percent );
	}

	public void setCreditIcms(String creditIcms) {
		this.creditIcms.sendKeys( creditIcms );
	}

	public void setQuantityTransporter(String qty) {
		qtyTransporter.sendKeys( qty );
	}

	public void setMvaSt(String mva) {
		mvaSt.sendKeys( mva );
	}

	public void setSubAliquotIcms(String icms) {
		subAliquotIcms.sendKeys( icms );

	}

	public void setAliquotApplyable(String aliquot) {
		sleep( VERY_LONG );
		aliquotApplyable.sendKeys( aliquot );
	}

	public void clickOnOption(String option) {
		sleep( VERY_LONG );
		driver.findElementByLinkText( option ).click();
	}

	public void setSeries(String serie) {
		nrSerie.sendKeys( serie );
	}

	public void selectClient(String client) {
		waitForElementPresent( By.id( "cliente" ) );
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( nameClient, client );
	}

	public void selectItem(String item) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( nameItem, item );
	}

	public void setQuantity(String qty) {
		nrQuantity.clear();
		nrQuantity.sendKeys( qty );
	}

	public void setValue(String valueItem) {
		vlItem.clear();
		vlItem.sendKeys( valueItem );
	}

	public void selectOptionFreight(String option) {
		collectFreight.sendKeys( option );
	}

	public void selectTransporter(String transport) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( transporter, transport );
	}

	public void setFreight(String value) {
		vlFreight.clear();
		vlFreight.sendKeys( value );
	}

	public String getNrSale(Integer nrLine) {
		sleep( 5000 );
		return driver.findElementByXPath( "//*[@id='row_row" + nrLine + "']/td[2]" ).getText();
	}

	public String getSeries() {
		JavascriptExecutor getSerie = (JavascriptExecutor) driver;
		return (String) getSerie
				.executeScript( "return document.getElementById('formEmissaoNotaFiscal_emissao_nrSerie').value" );
	}

	public String getNrNfe() {
		return driver.findElementByXPath( "//*[@id='nrNotaFiscal']" ).getText();
	}

	public String getNatureOfTransaction() {
		return driver.findElementByXPath( "//*[@id='idNatureza']" ).getText();
	}

	public String getFormPayement() {
		return driver.findElementByXPath( "//*[@id='formEmissaoNotaFiscal_emissao_formaPagamento']" ).getText();
	}

	public String getNameClient() {
		return driver.findElementByXPath( "//*[@id='cliente']" ).getText();
	}

	public String getNameItem() {
		return driver.findElementByXPath( "//*[@id='itemLabel0']" ).getText();
	}

	public String getQuantidy() {
		JavascriptExecutor getQty = (JavascriptExecutor) driver;
		return (String) getQty.executeScript( "return document.getElementById('qtComercial0').value" );
	}

	public String getValueItem() {
		JavascriptExecutor valueI = (JavascriptExecutor) driver;
		return (String) valueI.executeScript( "return document.getElementById('vlUnitarioComercial0').value" );
	}

	public void selectCfop(String setCfop) {
		getAssistants().getAutoCompleteAssistant( driver ).sendKeysAndSelectExisting( cfop, setCfop );
		cfop.sendKeys( Keys.TAB );
	}

	public String getValueTotal() {
		sleep( VERY_LONG );
		JavascriptExecutor getValueT = (JavascriptExecutor) driver;
		return (String) getValueT.executeScript( "return document.getElementById('vlTotalBruto0').value" );
	}

	public void setTributarySituationCofins(String situationcofins) {
		tributarySituationCofins.sendKeys( situationcofins );
	}

	public void setAliquotCofins(String aliquotCofin) {
		aliquotConfins.sendKeys( aliquotCofin );
	}

	public String getCfop() {
		return driver.findElementByXPath( "//*[@id='dsCfop0]" ).getText();
	}

	public void setTributarySituationIcms(String situation) {
		Select select = new Select( tributarySituationIcms );
		select.selectByVisibleText( situation );
	}

	public String getOrigin() {
		return driver.findElementByXPath( "//*[@id='formEmissaoNotaFiscal_emissao_itens_0__tributosICMS_origem']" )
				.getText();
	}

	public void setModIcms(String bcIcms) {
		bcICMS.sendKeys( bcIcms );
		bcICMS.sendKeys( Keys.ENTER );
	}

	public String getValueItemIcms() {
		JavascriptExecutor getAliquotIcmsT = (JavascriptExecutor) driver;
		return (String) getAliquotIcmsT.executeScript( "return document.getElementById('valorBCICMS0').value" );

	}

	public String getAliquotIcms() {
		JavascriptExecutor getAliquotIcmsT = (JavascriptExecutor) driver;
		return (String) getAliquotIcmsT.executeScript( "return document.getElementById('aliquotaICMS0').value" );
	}

	public String getValueIcmsTotal() {
		JavascriptExecutor getValueIcmsT = (JavascriptExecutor) driver;
		return (String) getValueIcmsT.executeScript( "return document.getElementById('totalICMS').value" );
	}

	public void setTributarySituationIpi(String situation) {
		tributarySituationIpi.sendKeys( situation );
		tributarySituationIpi.sendKeys( Keys.TAB );
	}

	public void setBasedCalculationIpi(String value) {
		valueCalculation.sendKeys( value );
	}

	public void setAliquotIpiI(String aliquot) {
		aliquotIpi.sendKeys( aliquot );
	}

	public String getValueIpi() {
		return driver.findElementByXPath( "//*[@id='valorIPI0']" ).getText();
	}

	public String getCodEnq() {
		JavascriptExecutor CodEnq = (JavascriptExecutor) driver;
		return (String) CodEnq.executeScript( "return document.getElementById('cdEnqIPI0').value" );
	}

	public void setTributarySituationPis(String situation) {
		tributarySituationPis.sendKeys( situation );
	}

	public String getBasedCalculationPis() {
		JavascriptExecutor bcPis = (JavascriptExecutor) driver;
		return (String) bcPis.executeScript( "return document.getElementById('totalBCICMS').value" );
	}

	public String getAliquotPis() {
		JavascriptExecutor aliquotPis = (JavascriptExecutor) driver;
		return (String) aliquotPis.executeScript( "return document.getElementById('aliquotaPIS0').value" );
	}

	public String getValuePis() {
		sleep( VERY_LONG );
		JavascriptExecutor valuePis = (JavascriptExecutor) driver;
		return (String) valuePis.executeScript( "return document.getElementById('valorPIS0').value" );
	}

	public void setBasedCalculationPis(String value) {
		valuePis.sendKeys( value );
	}

	public void setAliquotPis(String aliquot) {
		aliquotPis.sendKeys( aliquot );
	}

	public void setBasedCalculation(String calculation) {
		basedCalculationConfins.sendKeys( calculation );
	}

	public String getBasedCalculationIcms() {
		JavascriptExecutor getBasedIcms = (JavascriptExecutor) driver;
		return (String) getBasedIcms.executeScript( "return document.getElementById('totalBCICMS').value" );
	}

	public String getValueConfins() {
		JavascriptExecutor valueConfins = (JavascriptExecutor) driver;
		return (String) valueConfins.executeScript( "return document.getElementById('valorCOFINS0').value" );
	}

	public String getTotalPis() {
		JavascriptExecutor totalPis = (JavascriptExecutor) driver;
		return (String) totalPis.executeScript( "return document.getElementById('totalBCPis').value" );
	}

	public String getBasedCalculationConfins() {
		JavascriptExecutor totalConfins = (JavascriptExecutor) driver;
		return (String) totalConfins.executeScript( "return document.getElementById('totalBCCofins').value" );
	}

	public String getTotalIpi() {
		JavascriptExecutor totalIpi = (JavascriptExecutor) driver;
		return (String) totalIpi.executeScript( "return document.getElementById('totalBCIpi').value" );
	}

	public String getTotalImport() {
		JavascriptExecutor totalImport = (JavascriptExecutor) driver;
		return (String) totalImport.executeScript( "return document.getElementById('totalBCImportacao').value" );
	}

	public String getTotalValueIcsm() {
		JavascriptExecutor totalValueIcsm = (JavascriptExecutor) driver;
		return (String) totalValueIcsm.executeScript( "return document.getElementById('totalICMS').value" );
	}

	public String getTotalValuePis() {
		JavascriptExecutor totalValuePis = (JavascriptExecutor) driver;
		return (String) totalValuePis.executeScript( "return document.getElementById('totalPis').value" );
	}

	public String getTotalValueConfins() {
		JavascriptExecutor totalValueConfins = (JavascriptExecutor) driver;
		return (String) totalValueConfins.executeScript( "return document.getElementById('totalCofins').value" );
	}

	public String getTotalValueIpi() {
		JavascriptExecutor totalValueIpi = (JavascriptExecutor) driver;
		return (String) totalValueIpi.executeScript( "return document.getElementById('totalIpi').value" );
	}

	public String getTotalValueImport() {
		return driver.findElementByXPath( "//*[@id='totalImportacao']" ).getText();
	}

	public String getTotalInvoice() {
		JavascriptExecutor getTotalinvoice = (JavascriptExecutor) driver;
		return (String) getTotalinvoice.executeScript( "return document.getElementById('totalNota').value" );
	}

	public String getStatusNf() {
		return driver.findElementByXPath( "//*[contains(@id,'statusNotaFiscal')]" ).getText();
	}

	public void selectCheckboxLine(Integer nrLine) {
		driver.findElementByXPath( "//*[@id='row_row" + nrLine + "']/td[1]/span" ).click();
	}

	public void setBasedCalculationIcms(String value) {
		basedCalculationIcms.clear();
		basedCalculationIcms.sendKeys( value );
	}

	public void setAliquotIcms(String value) {
		aliquotIcms.clear();
		aliquotIcms.sendKeys( value );
		aliquotIcms.sendKeys( Keys.TAB );
	}

	public String getTotalProduct() {
		JavascriptExecutor getTotalValueproduct = (JavascriptExecutor) driver;
		return (String) getTotalValueproduct.executeScript( "return document.getElementById('totalProdServ').value" );
	}

	public void clickAction() {
		pageActions.click();
	}

	public String getValueIcms() {
		JavascriptExecutor getValueicms = (JavascriptExecutor) driver;
		return (String) getValueicms.executeScript( "return document.getElementById('valorICMS0').value" );
	}

	public void saveNf() {
		sleep( VERY_LONG );
		save.click();
		sleep( VERY_LONG );
	}

	public void setModTransporter(String transporter) {
		modTransporter.sendKeys( transporter );
	}

	public void setValueFreightNf(String value) {
		vlFreightNf.sendKeys( value );
		vlFreightNf.sendKeys( Keys.ENTER );
	}

	public void setSubModBcIcms(String submod, String icms, String subaliquot) {
		if (modBcIcms.isDisplayed()) {
			modBcIcms.sendKeys( submod );
			subBcIccms.sendKeys( icms );
			subAliquotIcms.sendKeys( subaliquot );
		}
	}

	public void waitSendNfe() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( 120, TimeUnit.SECONDS ).pollingEvery(
				1, TimeUnit.SECONDS );
		wait.until( new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				try {
					driver.findElementByXPath( "//*[contains(@id,'statusNotaFiscal')]" );
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		} );
	}

	public void waitUpdateStatus() {
		sleep( VERY_LONGEST );// kkk ri alto aqui :D
	}

	public void waitForNFeUpdateStatus(final String statusInvoice) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( 120, TimeUnit.SECONDS ).pollingEvery(
				1, TimeUnit.SECONDS );
		wait.until( new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				try {
					driver.findElementByLinkText( "Resultado pesquisa notas fiscais emitidas" ).click();
					return driver.findElementByXPath( "//*[contains(@id,'statusNotaFiscal')]" ).getText()
							.matches( statusInvoice );
				} catch (Exception e) {
					return false;
				}
			}
		} );
	}

	public void clickOnCancelNfeOption(String justification) {
		WebElement btnCancelar = driver.findElementByXPath( "//span[contains(@id, 'btCancelar')]" );
		String idBtnCancelar = btnCancelar.getAttribute( "id" );
		javascript( "document.getElementById('" + idBtnCancelar + "').removeClassName('spanDestacavel')" );
		btnCancelar.click();
		driver.findElementByXPath( "//input[contains(@id, 'justification')]" ).sendKeys( justification );
		driver.findElementByXPath( "//button[contains(text(), 'Cancelar esta Nota Fiscal')]" ).click();
	}
}
