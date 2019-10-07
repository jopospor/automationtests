package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.contaazul.auto.selenium.WebPage;

public class PlansAndPricingPage extends WebPage {

	public PlansAndPricingPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	@FindBy(xpath = "//a[contains(@class, 'btn-select-plan-initial')]")
	private WebElement buttonPlanInitial;

	@FindBy(xpath = "//a[contains(@class, 'btn-select-plan-solo')]")
	private WebElement buttonPlanSolo;

	@FindBy(xpath = "//a[contains(@class, 'btn-select-plan-basic')]")
	private WebElement buttonPlanBasic;

	@FindBy(xpath = "//a[contains(@class, 'btn-select-plan-plus')]")
	private WebElement buttonPlanPlus;

	@FindBy(id = "documentNumber")
	private WebElement fieldDocumentNumber;

	@FindBy(id = "name")
	private WebElement fieldName;

	@FindBy(id = "cep")
	private WebElement fieldCep;

	@FindBy(id = "address")
	private WebElement fieldAddress;

	@FindBy(id = "number")
	private WebElement fieldNumber;

	@FindBy(id = "neighborhood")
	private WebElement fieldNeighborhood;

	@FindBy(id = "city")
	private WebElement fieldCity;

	@FindBy(id = "email")
	private WebElement fieldEmail;

	@FindBy(xpath = "//a[contains(@class, 'act-step-subscription-billing-item')]")
	private WebElement buttonBankslip;

	@FindBy(xpath = "//a[contains(@class, 'act-step-subscription-card-item')]")
	private WebElement buttonCreditCard;

	@FindBy(id = "holderName")
	private WebElement fieldHolderName;

	@FindBy(id = "creditCardNumber")
	private WebElement fieldCreditCardNumber;

	@FindBy(id = "expirationMonth")
	private WebElement selectExpirationMonth;

	@FindBy(id = "expirationYear")
	private WebElement selectExpirationYear;

	@FindBy(id = "securityCode")
	private WebElement fieldSecurityCode;

	@FindBy(id = "monthlyOption")
	private WebElement radioMonthlyOption;

	@FindBy(id = "annualOption")
	private WebElement radioAnnualOption;

	@FindBy(xpath = "//a[contains(text(),'Confirmar Pagamento')]")
	private WebElement buttonConfirmPayment;

	@FindBy(xpath = "//a[contains(text(),'Imprimir 2ª via do boleto')]")
	private WebElement buttonPrintBankslipBlocked;

	@FindBy(xpath = "//a[contains(@class,'act-auto-credit-card')]")
	private WebElement buttonConfirmCreditCard;

	public void clickButtonPlanInitial() {
		buttonPlanInitial.click();
	}

	public void clickButtonPlanSolo() {
		buttonPlanSolo.click();
	}

	public void clickButtonPlanBasic() {
		buttonPlanBasic.click();
	}

	public void clickButtonPlanPlus() {
		buttonPlanPlus.click();
	}

	public void setFieldDocumentNumber(String documentNumber) {
		this.fieldDocumentNumber.clear();
		this.fieldDocumentNumber.sendKeys( documentNumber );
	}

	public void setFieldName(String name) {
		this.fieldName.clear();
		this.fieldName.sendKeys( name );
	}

	public void setFieldCep(String cep) {
		this.fieldCep.clear();
		this.fieldCep.sendKeys( cep );
	}

	public void setFieldNumber(String number) {
		this.fieldNumber.clear();
		this.fieldNumber.sendKeys( number );
	}

	public void setFieldEmail(String email) {
		this.fieldEmail.clear();
		this.fieldEmail.sendKeys( email );
	}

	public void clickButtonBankslip() {
		buttonBankslip.click();
	}

	public void clickButtonCreditCard() {
		buttonCreditCard.click();
	}

	public void setFieldHolderName(String holderName) {
		this.fieldHolderName.clear();
		this.fieldHolderName.sendKeys( holderName );
	}

	public void setFieldCreditCardNumber(String creditCardNumber) {
		this.fieldCreditCardNumber.clear();
		this.fieldCreditCardNumber.sendKeys( creditCardNumber );
	}

	public void setSelectExpirationMonth(String expirationMonth) {
		Select lis = new Select( this.selectExpirationMonth );
		lis.selectByVisibleText( expirationMonth );
	}

	public void setSelectExpirationYear(String expirationYear) {
		Select lis = new Select( this.selectExpirationYear );
		lis.selectByVisibleText( expirationYear );
	}

	public void setFieldSecurityCode(String securityCode) {
		this.fieldSecurityCode.clear();
		this.fieldSecurityCode.sendKeys( securityCode );
	}

	public void clickRadioAnnualOption() {
		radioAnnualOption.click();
	}

	public void clickButtonConfirmPayment() {
		buttonConfirmPayment.click();
	}

	public void clickButtonPrintBankslipBlocked() {
		buttonPrintBankslipBlocked.click();
	}

	public void clickButtonConfirmCreditCard() {
		buttonConfirmCreditCard.click();
	}

	public boolean isContractPlanMessagePresent(String planExpected) {
		try {
			waitForText( By.id( "activePlanAccount" ), planExpected );
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isContractValueMessagePresent(String valueExpected) {
		try {
			waitForText( By.id( "activeValuePlan" ), valueExpected );
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isContractPeriodMessagePresent(String periodExpected) {
		try {
			waitForText( By.className( "activePeriodType" ), periodExpected );
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isContractTypeMessagePresent(String typeExpected) {
		try {
			waitForText( By.className( "activePaymentType" ), typeExpected );
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSuccessMessageDisplayed() {
		try {
			driver.findElement( By.xpath( "//h4[contains(text(), 'Parabéns, pagamento realizado com sucesso.')]" ) );
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean isBrandSelected(String brand) {
		try {
			if (brand.equals( "VISA" ))
				driver.findElement( By
						.xpath( "//a[@class='subscription-card subscription-card-visa subscription-card-selected']/img[@alt='Cartão Visa']" ) );
			else if (brand.equals( "MASTERCARD" ))
				driver.findElement( By
						.xpath( "//a[@class='subscription-card subscription-card-master subscription-card-selected']/img[@alt='Cartão Master Card']" ) );
			else if (brand.equals( "AMEX" ))
				driver.findElement( By
						.xpath( "//a[@class='subscription-card subscription-card-amex subscription-card-selected']/img[@alt='Cartão American Express']" ) );
			else
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
