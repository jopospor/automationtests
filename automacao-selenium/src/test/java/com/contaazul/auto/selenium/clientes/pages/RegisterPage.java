package com.contaazul.auto.selenium.clientes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class RegisterPage extends WebPage {

	@FindBy(id = "contaazul-name")
	protected WebElement name;
	@FindBy(id = "contaazul-company")
	protected WebElement company;
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
	@FindBy(className = "alert-heading")
	protected WebElement validationArea;

	public RegisterPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	public void fillOutForm(String name, String empresa, String telefone,
			String email, String senha, boolean termosDeUso) {

		setName( name );
		company.clear();
		company.sendKeys( empresa );
		phone.clear();
		phone.sendKeys( telefone );
		this.email.sendKeys( email );
		pass.clear();
		pass.sendKeys( senha );
		if (termosDeUso) {
			if (!terms.isSelected())
				terms.click();
		} else {
			if (terms.isSelected())
				terms.click();
		}
	}

	public void setName(String name) {
		this.name.clear();
		this.name.sendKeys( name );
	}

	public String getName() {
		return this.name.getText();
	}

	public void submitRegisterForm() {
		this.cadastrar.click();
	}

	public String getValidationMessage() {
		return validationArea.getText();
	}

}
