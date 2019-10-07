package com.contaazul.auto.selenium.assistants;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.TestNGException;

import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.WebPage;
import com.contaazul.auto.selenium.clientes.pages.HeaderContaAzulPage;
import com.contaazul.auto.selenium.clientes.pages.LoginPage;

public class LoginAssistant extends WebPage {

	public LoginAssistant(RemoteWebDriver driver) {
		super( driver );
	}

	// Login e o logout são fundamentais pois são os estados inciais e finais de
	// todos os testes

	// Antes de cada teste
	// Está deslogado?
	// Está na tela de login?
	// -> Se loga
	// Não está na tela de login?
	// -> Vai pra tela de login
	// -> Se loga
	// Ainda está logado?
	// -> Desloga
	// -> Se loga

	public void login(String username, String password) {
		vaiParaPaginaDeLogin();
		LoginPage logMe = PageFactory.initElements( driver, LoginPage.class );
		try {
			logMe.preencherCampoUsuario( username );
			logMe.preencherCampoSenha( password );
			logMe.submit();
		} catch (NoSuchElementException e) {
			logout();
			logMe.preencherCampoUsuario( username );
			logMe.preencherCampoSenha( password );
			logMe.submit();
		}
		try {
			getAssistants().getModalAssistant( driver ).closeAllModalWindowsAndPopups();
		} catch (Exception e) {
			Reporter.log( "ERRO: Exceção ao tentar fechar todos os modais (popups): " + e.getMessage(), true );
		}
	}

	public void vaiParaPaginaDeLogin() {
		String scheme = SeleniumSession.getSession().getProperties()
				.getProperty( SeleniumProperties.AUT_BASE_SCHEME );
		String baseUrl = SeleniumSession.getSession().getProperties()
				.getProperty( SeleniumProperties.APPLICATION_BASE_URL );
		String loginUrl = SeleniumSession.getSession().getProperties()
				.getProperty( SeleniumProperties.AUT_BASE_URL );
		driver.get( scheme + "://" + baseUrl + loginUrl );
	}

	public void logout() {
		logout( false );
	}

	public void logout(Boolean isNewMenu) {
		HeaderContaAzulPage barraUsuario = PageFactory.initElements( driver,
				HeaderContaAzulPage.class );
		try {
			if (isNewMenu)
				barraUsuario.logoutNewMenu();
			else
				barraUsuario.logout();
		} catch (ElementNotVisibleException eInv) {
			new ModalAssistant( driver ).closeAllModalWindowsAndPopups();
			barraUsuario.logout();
		} catch (NoSuchElementException e) {
			try {
				SeleniumSession.getSession().forceResetBrowser();
			} catch (Exception e2) {
				// desiste
				throw new TestNGException(
						"Não conseguiu reiniciar browser. Exceção:"
								+ e2.getClass().getName() + ". Mensagem:"
								+ e.getMessage() + "." );
			}
		}
	}

}
