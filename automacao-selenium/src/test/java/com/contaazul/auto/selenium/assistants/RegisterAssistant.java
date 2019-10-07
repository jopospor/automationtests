package com.contaazul.auto.selenium.assistants;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.contaazul.auto.selenium.WebPage;
import com.contaazul.auto.selenium.clientes.pages.LoginPage;
import com.contaazul.auto.selenium.clientes.pages.RegisterPage;

public class RegisterAssistant extends WebPage {

	public RegisterAssistant(RemoteWebDriver driver) {
		super( driver );
	}

	public String registerUser(String name, String empresa, String telefone,
			String email, String senha, boolean termosDeUso) {
		LoginPage logMe = PageFactory.initElements( driver, LoginPage.class
				);
		logMe.clicaNovoCadastro();
		RegisterPage regForm = PageFactory.initElements( driver, RegisterPage.class );
		regForm.fillOutForm( name, empresa, telefone, email, senha, termosDeUso );
		regForm.submitRegisterForm();
		sleep( VERY_LONG );
		return email;
	}

}
