package com.contaazul.auto.selenium.clientes.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.data.DocumentNumberFormatUtil;
import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.clientes.pages.BillingFormPage;

public class BillingDataTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void criarDadosCobranca(String tipoDocumento, String documentNumber,
			String name, String cep, String address, String number,
			String complement, String neighborhood, String nameCity,
			String phoneNumber, String email, String $resultado) {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "BillingDataTest@contaazul.com", "12345" );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateToBilling();
		step( "Navega para Billing" );

		BillingFormPage dados = getPaginas().getPaginaDadosBilling(
				getWebDriver() );
		dados.setTipoDocumento( tipoDocumento );
		dados.setAddress( address );
		dados.setCEP( cep );
		dados.setComplement( complement );
		dados.setDocumentNumber( documentNumber );
		dados.setEmail( email );
		dados.setName( name );
		dados.setNameCity( nameCity );
		dados.setNeighborhood( neighborhood );
		dados.setNumber( number );
		dados.setPhoneNumber( phoneNumber );
		step( "Preenche formulário" );

		dados.clicarEnviarDados();
		step( "Envia formulário" );

		if ($resultado.matches( SeleniumSession.getSession().getLocale().translate( "#SUCCESS" ) )) {

			getAssistants().getMenuAssistant( getWebDriver() ).navigateToBilling();
			step( "Reabre Billing" );
			sleep( QUICKLY );// carregamento dos dados por JS após o load do
								// browser confunde o Selenium.
			checkPoint( "Não encontrou tipoDocumento esperado.", tipoDocumento, dados.getTipoDocumento(), true );
			if (tipoDocumento.matches( "CPF" ))
				checkPoint( "Não encontrou documentNumber esperado.",
						DocumentNumberFormatUtil.formatCPF( documentNumber ), dados.getDocumentNumber(), true );
			else
				checkPoint( "Não encontrou documentNumber esperado.",
						DocumentNumberFormatUtil.formatCNPJ( documentNumber ), dados.getDocumentNumber(), true );
			checkPoint( "Não encontrou name esperado.", name, dados.getName(), true );
			checkPoint( "Não encontrou CEP esperado.", cep, dados.getCEP(), true );
			checkPoint( "Não encontrou address esperado.", address, dados.getAddress(), true );
			checkPoint( "Não encontrou number esperado.", number, dados.getNumber(), true );
			checkPoint( "Não encontrou complement esperado.", complement, dados.getComplement(), true );
			checkPoint( "Não encontrou neighborhood esperado.", neighborhood, dados.getNeighborhood(), true );
			checkPoint( "Não encontrou nameCity esperado.", nameCity, dados.getNameCity(), true );
			checkPoint( "Não encontrou phoneNumber esperado.", phoneNumber, dados.getPhoneNumber(), true );
			checkPoint( "Não encontrou email esperado.", email, dados.getEmail(), true );

		} else {

			checkPoint( "Mensagem de validação esperada mas não encontrada.", $resultado, dados.getMensagemValidacao() );

		}

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
