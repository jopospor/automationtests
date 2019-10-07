package com.contaazul.auto.selenium.clientes.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.componentesweb.pages.NewMenuPage;

public class LogoMenuTest extends SeleniumTest {

	@BeforeClass
	public void login() {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "LogoMenuTest@contaazul.com", "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void logoMenuTest(String arquivo, String mensagem) {
		NotificationAssistant notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );
		NewMenuPage newMenuPage = getPaginas().getNewMenuPage( getWebDriver() );

		getAssistants().getNewMenuAssistant( getWebDriver() ).userConfigure( "Marca da Empresa" );
		step( "Faz login e navega pelo menu" );

		newMenuPage.uploadLogo( arquivo );
		step( "clica em marca da empresa e abre o popup adiciona um arquivo e faz upload" );

		checkPoint( "Erro no upload do arquivo para logo da empresa", "" + mensagem + "",
				notificationAssistant.getAlertMessageText() );
		step( "valida mensagem" );
	}

	@AfterClass
	public void logout() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
		step( "faz logout" );
	}
}
