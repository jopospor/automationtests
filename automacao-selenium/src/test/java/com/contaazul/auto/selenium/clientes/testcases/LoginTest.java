package com.contaazul.auto.selenium.clientes.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.clientes.pages.HeaderContaAzulPage;
import com.contaazul.auto.selenium.clientes.pages.LoginPage;

public class LoginTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void testLogin(String username, String password, String nome,
			String $result) {

		getAssistants().getLoginAssistant( getWebDriver() ).login( username,
				password );
		step( "Submeteu formulário de login" );

		if ($result.matches( SeleniumSession.getSession().getLocale()
				.translate( "#LOGIN_SUCCESSFUL" ) )) {

			// Se o resultado esperado é sucesso, checa que está na página
			// inicial:
			HeaderContaAzulPage barraDoUsuario = getPaginas().getPaginaInicial(
					getWebDriver() );
			checkPoint( "Não efetuou login com sucesso.", nome,
					barraDoUsuario.getNomeUsuarioLogado() );
			step( "Pega nome do usuário logado na tela inicial" );
			getAssistants().getLoginAssistant( getWebDriver() ).logout();

		} else {

			// Se o resultado esperado é um erro de login, checa o erro
			LoginPage logMe = getPaginas().getPaginaLogin( getWebDriver() );
			checkPoint( "Não encontrou a mensagem de falha no login esperada.",
					$result, logMe.getMensagemDeErroLogin() );
			step( "Pega mensagem de erro/validação na tela de Login" );
		}

		// Nota: checkpoints também tiram capturas de telas

	}

}
