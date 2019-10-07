package com.contaazul.auto.selenium.assistants;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.informant.agil.ejb.entity.ContaStatus;

import com.contaazul.auto.selenium.WebPage;
import com.contaazul.configuration.UserConfigurationRemote;

public class UserConfigurationAssistant extends WebPage {

	public UserConfigurationAssistant(RemoteWebDriver driver) {
		super( driver );
	}

	public void updateCompanyFeatures(String login, String password, Enum<?>... newValue) throws Exception {
		final UserConfigurationRemote ejb = lookupRemoteEJB( login, password );
		ejb.updateCompanyFeatures( login, newValue );
	}

	public void updateStatus(String login, String password, ContaStatus status) throws Exception {
		final UserConfigurationRemote ejb = lookupRemoteEJB( login, password );
		ejb.updateStatus( login, status );
	}

	public boolean isAccountUpdated(String login, String password) throws Exception {
		final UserConfigurationRemote ejb = lookupRemoteEJB( login, password );
		return ejb.isAccountUpdated( login );
	}

	private UserConfigurationRemote lookupRemoteEJB(String login, String password) throws NamingException {
		Properties env = new Properties();
		env.put( Context.SECURITY_PRINCIPAL, login );
		env.put( Context.SECURITY_CREDENTIALS, password );
		env.put( Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming" );

		final Context context = new InitialContext( env );

		return (UserConfigurationRemote) context
				.lookup( "ejb:contaazul-app/contaazul-app-business/UserConfigurationService!"
						+ UserConfigurationRemote.class.getName() );
	}
}
