package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class CheckoutPlansAndPricePage extends WebPage {

	@FindBy(id = "paymentPeriodOptions")
	protected WebElement dropDownPeriodPlan;

	@FindBy(id = "changePlanLink")
	protected WebElement chooseOtherPlan;

	@FindBy(id = "customerName")
	protected WebElement nameUserBillet;

	@FindBy(id = "customerEmail")
	protected WebElement emailUser;

	@FindBy(id = "customerPhoneNumber")
	protected WebElement phoneNumberUser;

	@FindBy(id = "confirmBakslipPayment")
	protected WebElement confirmAndPrintSlip;

	@FindBy(id = "showBoxCouponLink")
	protected WebElement openShowBoxCoupon;

	@FindBy(id = "changetoCreditCardPaymentMethodLink")
	public WebElement paymentCreditCard;

	@FindBy(id = "promoCodeInput")
	protected WebElement inputCoupon;

	@FindBy(id = "promoCodeBtn")
	protected WebElement validateCoupon;

	@FindBy(id = "creditCardHolderName")
	protected WebElement nameUserCreditCard;

	@FindBy(id = "creditCardNumber")
	protected WebElement numberCreditCard;

	@FindBy(id = "creditCardSecurityCode")
	protected WebElement creditCardSecurityCode;

	@FindBy(id = "creditCardExpirationMonth")
	protected WebElement expirationCreditCardMonth;

	@FindBy(id = "creditCardExpirationYear")
	protected WebElement expirationCreditCardYear;

	@FindBy(id = "changeToBankslipPaymentMethodLink")
	protected WebElement paymentBillet;

	@FindBy(id = "summaryPlanLabel")
	protected WebElement summaryPlanResume;

	@FindBy(id = "creditCardHolderName")
	protected WebElement nameUserCard;

	@FindBy(id = "option1")
	protected WebElement optionCnpj;

	@FindBy(id = "option2")
	protected WebElement optionCpf;

	@FindBy(className = "tooltip-inner")
	protected WebElement msgCoupon;

	@FindBy(id = "confirmCreditCardPayment")
	protected WebElement creditCardPayment;

	@FindBy(id = "cnpj")
	protected WebElement cnpj;

	@FindBy(id = "companyName")
	protected WebElement company;

	@FindBy(id = "cpf")
	protected WebElement cpf;

	@FindBy(id = "addressCep")
	protected WebElement cep;

	@FindBy(id = "addressStreet")
	protected WebElement street;

	@FindBy(id = "addressNumber")
	protected WebElement numberHouse;

	@FindBy(id = "addressComplement")
	protected WebElement complement;

	@FindBy(id = "addressNeighborhood")
	protected WebElement neighborhood;

	@FindBy(id = "addressCity")
	protected WebElement city;

	@FindBy(id = "phoneNumber")
	protected WebElement phoneNumberNF;

	@FindBy(id = "email")
	protected WebElement emailNF;

	@FindBy(id = "sendInvoiceDataButton")
	protected WebElement save;

	@FindBy(id = "usingCpf")
	protected WebElement buttonCpf;

	@FindBy(id = "usingCnpj")
	protected WebElement buttonCnpj;

	@FindBy(id = "bankslipDownloadLink")
	protected WebElement secondViaBolet;

	@FindBy(id = "summaryPlanInstallment")
	protected WebElement numberOfInstallments;

	@FindBy(id = "summaryPlanTotalValue")
	protected WebElement valuePlanTotal;

	public String valuePlan() {
		return valuePlanTotal.getText();
	}

	public String summarynumberOfInstallments() {
		return numberOfInstallments.getText();
	}

	public void selectPeriodPlan(String plano) {
		dropDownPeriodPlan.sendKeys( plano );
		sleep( VERY_QUICKLY );
	}

	public String ResumePlan() {
		return summaryPlanResume.getText();
	}

	public void chooseOtherPlan() {
		chooseOtherPlan.click();
		sleep( VERY_LONG );
	}

	public void billetPayment(String nomeUsuarioBoleto, String email, String numTelefone) {
		nameUserBillet.clear();
		nameUserBillet.sendKeys( nomeUsuarioBoleto );
		emailUser.clear();
		emailUser.sendKeys( email );
		phoneNumberUser.clear();
		phoneNumberUser.sendKeys( numTelefone );
		sleep( FOR_A_LONG_TIME );
	}

	public void nameUserCard(String nomeUsuario) {
		nameUserCard.clear();
		nameUserCard.sendKeys( nomeUsuario );
	}

	public void creditCardNumber(String numeroCartao) {
		numberCreditCard.clear();
		numberCreditCard.sendKeys( numeroCartao );
	}

	public void codeCardSecurity(String codSeguranca) {
		creditCardSecurityCode.clear();
		creditCardSecurityCode.sendKeys( codSeguranca );
	}

	public void boletPayment() {
		paymentBillet.click();
	}

	public void expirateDateCard(String mesValidade, String anoValidade) {
		expirationCreditCardMonth.sendKeys( mesValidade );
		expirationCreditCardYear.sendKeys( anoValidade );
		sleep( FOR_A_LONG_TIME );
	}

	public void validateCoupon(String cupom) {
		openShowBoxCoupon.click();
		inputCoupon.sendKeys( cupom );
		validateCoupon.click();
	}

	public void iHaveCreditCard() {
		paymentCreditCard.click();
	}

	public void confirmAndPrintSlipPayment() {
		confirmAndPrintSlip.click();
		sleep( VERY_LONG );
	}

	public String msgCouponInvalid() {
		return msgCoupon.getText();
	}

	public void paymentCard() {
		creditCardPayment.click();
		sleep( FOR_A_LONG_TIME );
	}

	public void clickBtnCnpj() {
		buttonCnpj.click();
	}

	public void clickBtnCpf() {
		buttonCpf.click();
	}

	public void cpfField(String cpfNumber) {
		cpf.clear();
		cpf.sendKeys( cpfNumber );
	}

	public void cnpjField(String cnpjNumber) {
		cnpj.clear();
		sleep( FOR_A_LONG_TIME );
		cnpj.sendKeys( cnpjNumber );
	}

	public void cepField(String cepNumber) {
		cep.clear();
		cep.sendKeys( cepNumber );
	}

	public void nameStreet() {
		street.sendKeys( Keys.TAB );
	}

	public void numberHouse(String numeroCasa) {
		numberHouse.clear();
		numberHouse.sendKeys( numeroCasa );
	}

	public void complement(String complemento) {
		complement.clear();
		complement.sendKeys( complemento );
	}

	public void neighborhood() {
		neighborhood.sendKeys( Keys.TAB );
	}

	public void nameCity() {
		city.sendKeys( Keys.TAB );
	}

	public void numberPhoneNf(String telefone) {
		phoneNumberNF.clear();
		phoneNumberNF.sendKeys( telefone );
	}

	public void emailNF(String email) {
		emailNF.clear();
		emailNF.sendKeys( email );
	}

	public void corporateName(String razaoSocial) {
		company.clear();
		company.sendKeys( razaoSocial );
	}

	public void clickSave() {
		save.click();
		sleep( VERY_LONG );
	}

	public void name(String nomeCompleto) {
		nameUserBillet.clear();
		nameUserBillet.sendKeys( nomeCompleto );
	}

	public String msgCouponValid() {
		return driver.findElementByXPath( "//*[@id='boxCoupon']/div[5]" ).getText();

	}

	public CheckoutPlansAndPricePage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	@FindBy(id = "contaazul-name")
	protected WebElement name;
	@FindBy(id = "contaazul-company")
	protected WebElement companymodal;
	@FindBy(id = "contaazul-phone")
	protected WebElement phone;
	@FindBy(id = "contaazul-email")
	protected WebElement email;
	@FindBy(id = "contaazul-pass")
	protected WebElement pass;
	@FindBy(id = "contaazul-terms")
	protected WebElement terms;// checkbox
	@FindBy(id = "createUser")
	protected WebElement cadastrar;

	public void cadastro(String email, boolean termosDeUso) {
		name.sendKeys( "Testes" );
		companymodal.sendKeys( "Conta Azul" );
		phone.sendKeys( "4796217647" );
		this.email.sendKeys( email );
		pass.sendKeys( "12345" );
		if (termosDeUso) {
			if (!terms.isSelected())
				terms.click();
		} else {
			if (terms.isSelected())
				terms.click();
		}
		cadastrar.click();
	}

}
