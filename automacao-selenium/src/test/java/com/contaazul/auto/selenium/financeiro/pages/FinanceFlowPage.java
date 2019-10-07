package com.contaazul.auto.selenium.financeiro.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.contaazul.auto.selenium.WebPage;

public class FinanceFlowPage extends WebPage {

	public static final int NEW_REVENUE_OPTION = 0;

	public static final int NEW_EXPENSE_OPTION = 1;

	public static final int NEW_TRANSFER_OPTION = 2;

	public static final int FROM_EXTRACT_OPTION = 3;

	public static final int FROM_SPREADSHEET_OPTION = 4;

	@FindBy(className = "btn-editar-contas")
	private WebElement editCategoryButton;

	@FindBy(className = "ng-binding")
	private WebElement lastBreadcrumb;

	@FindBy(css = "button.btn.btn-primary.primary-action")
	private WebElement primaryButton;

	@FindBy(xpath = "//*[@id='conteudo']/div/div[2]/div[1]/a[4]/span/span")
	private WebElement btnExtract;

	@FindBy(xpath = "//*[@id='inlineBtnBarMenu_0']/div[2]/a")
	private WebElement isNotDisplayedPrintSlip;

	private String idRolloverMenuOpened;

	public FinanceFlowPage(WebDriver driver) {
		super( driver );
		PageFactory.initElements( driver, this );
	}

	private String getIdRolloverMenuOpened() {
		return idRolloverMenuOpened;
	}

	private void setIdRolloverMenuOpened(String idRolloverMenuOpened) {
		this.idRolloverMenuOpened = idRolloverMenuOpened;
	}

	private WebElement getRolloverMenuOpened() {
		try {
			return driver.findElementById( getIdRolloverMenuOpened() );
		} catch (Exception e) {
			Reporter.log( "AVISO: Não encontrou Menu Rollover com id = '" + getIdRolloverMenuOpened() + "'.", true );
			return null;
		}
	}

	public EditCategoryPage navigateToEditCategory() {
		editCategoryButton.click();
		return new EditCategoryPage( driver );
	}

	public IncomePage<FinanceFlowPage> navigateToNewIncome() {
		this.openFinanceDropdown();
		this.newRevenueDropdownItem().click();
		return new IncomePage<FinanceFlowPage>( this, driver );
	}

	private void openFinanceDropdown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript( "document.getElementById('addFinance').className = 'btn-group open'" );
	}

	private List<WebElement> financeDropdownElements() {
		return this.driver.findElementsByCssSelector( "#addFinance .dropdown-menu.addChildren li a" );
	}

	/**
	 * Método substituído por <code>newRevenueDropdownItem</code>
	 */
	@Deprecated
	private WebElement newInvoiceDropdownItem() {
		return this.financeDropdownElements().get( NEW_REVENUE_OPTION );
	}

	private WebElement newRevenueDropdownItem() {
		return newInvoiceDropdownItem();
	}

	public void clickAddIncome() {
		if (this.lastBreadcrumb.getText().equals( "Contas a Receber" ))
			clickPrimaryButton();
		else
			chooseOptionDropDownMenuPrimaryButton( NEW_REVENUE_OPTION );
	}

	public void navigateToNewExpense() {
		if (this.lastBreadcrumb.getText().equals( "Contas a Pagar" ))
			clickPrimaryButton();
		else
			chooseOptionDropDownMenuPrimaryButton( NEW_EXPENSE_OPTION );
	}

	public void navigateToImportExtract() {
		if (this.lastBreadcrumb.getText().equals( "Conciliações Pendentes" ))
			clickPrimaryButton();
		else
			chooseOptionDropDownMenuPrimaryButton( FROM_EXTRACT_OPTION );
	}

	public void chooseOptionDropDownMenuPrimaryButton(int option) {
		openFinanceDropdown();
		this.financeDropdownElements().get( option ).click();
	}

	public void clickPrimaryButton() {
		this.primaryButton.click();
	}

	public void showRolloverMenu(Integer rowNumber) {
		sleep( FOR_A_LONG_TIME );
		String stringId = "inlineBtnBarMenu_" + rowNumber.toString();
		sleep( VERY_LONG );
		javascript( "document.getElementById('" + stringId + "').style.display = 'block'" );
		setIdRolloverMenuOpened( stringId );
	}

	public void chooseOptionMenuRollover(RolloverMenuOptions option) {
		getRolloverMenuOpened().findElement( By.cssSelector( option.getSelector() ) ).click();
	}

	public void closeRolloverMenuOpened() {
		if (isOpenMenuRollover()) {
			javascript( "document.getElementById('" + getIdRolloverMenuOpened() + "').style.display = 'none'" );
			setIdRolloverMenuOpened( "" );
		}
	}

	private boolean isOpenMenuRollover() {
		if (!getIdRolloverMenuOpened().isEmpty())
			return isRolloverMenuBlocked();
		else
			return false;
	}

	private boolean isRolloverMenuBlocked() {
		return (Boolean) javascript( "return document.getElementById('" + getIdRolloverMenuOpened()
				+ "').style.display == 'block'" );
	}

	public enum RolloverMenuOptions {
		EDIT("div.inline-icon.statement-inline-edit"),
		PRINT_SLIP("div.inline-icon.statement-inline-bankslip"),
		SEND_SLIP("div.inline-icon.statement-inline-bankslip-send"),
		PAY("div.inline-icon.statement-inline-pay"),
		DELETE("div.inline-icon.statement-inline-delete"),
		UNDO_PAY("div.inline-icon.statement-inline-undo-pay"),
		FIND_CONCILE("div.inline-icon.min-margin-right.new_tooltip.act-focused-icon");

		private String selector;

		private RolloverMenuOptions(String selector) {
			this.selector = selector;
		}

		public String getSelector() {
			return selector;
		}
	}

	public void launchConcile(String nrLine) {
		driver.findElementByXPath( "//*[@id='statement-list-container']/table[3]/tbody/tr[" + nrLine + "]" ).click();
		driver.findElementByClassName( "act-conciliate-label" ).click();
	}

	public void clickExtract() {
		btnExtract.click();
		sleep( VERY_LONG );
	}

	public boolean isNotDisplayedPrintSlip() {
		return !isNotDisplayedPrintSlip.isDisplayed();
	}
}
