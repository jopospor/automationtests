package com.contaazul.auto.selenium.clientes.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.RegisterAssistant;
import com.contaazul.auto.selenium.clientes.pages.NewUserStepsPage;
import com.contaazul.auto.selenium.clientes.pages.RegisterWizardInvoicesPage;
import com.contaazul.auto.selenium.clientes.pages.RegisterWizardPage;
import com.contaazul.auto.selenium.clientes.pages.TourFlowControlPage;

public class NewUserStepsTest extends SeleniumTest {

	public NewUserStepsTest(RemoteWebDriver webDriver) {
		super();
		this.driver = webDriver;
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void testNewUserSteps(String tipoEmpresa, String emiteNota) {

		String randomEmail = "TesteSteps_" + Double.toString( Math.random() );
		randomEmail = randomEmail.substring( 0, Math.min( 20, randomEmail.length() - 1 ) );
		RegisterAssistant register = getAssistants().getRegisterAssistant( getWebDriver() );
		register.registerUser( "TesteSteps", "ContaAzul", "1234697980",
				randomEmail + "@contaazul.com", "12345", true );

		RegisterWizardPage wizz = getPaginas().getRegisterWizardPage(
				getWebDriver() );
		if (tipoEmpresa.matches( "Produtos e Servicos" )) {
			wizz.selectServicesAndProducts();
			wizz.clickProximoButton();
			RegisterWizardInvoicesPage regInvoices = getPaginas().getRegisterWizardInvoicesPage( getWebDriver() );

			if (emiteNota.matches( "NF-e e NFS-e" )) {
				regInvoices.selectProductsAndServices();
				regInvoices.clickProximoButton();
			}

			regInvoices.clickProximoButton();
			regInvoices.clickProximoButton();
		}

	}

	protected void testSeeVideo() {
		NewUserStepsPage newUserStepsPage = getPaginas().getNewUserStepsPage( getWebDriver() );

		newUserStepsPage.clicarFecharPopupVideo();
		newUserStepsPage.clicarVerVideo();
		checkPoint( "Não conseguiu encontrar Popup de vídeo", "Assistir o Vídeo",
				getWebDriver().findElement( By.id( "showVideo" ) ).getText() );
		newUserStepsPage.clicarFecharPopupVideo();

	}

	protected void testInsertExpenses() {
		NewUserStepsPage newUserStepsPage = getPaginas().getNewUserStepsPage( getWebDriver() );
		newUserStepsPage.clicarCadastroDeDesepesas();

		TourFlowControlPage tourFlowControlPage = getPaginas().getTourFlowControlPage( getWebDriver() );
		checkPoint( "Não encontrou Jtour Fluxo de Caixa.", "Saldo Inicial do seu fluxo de caixa",
				tourFlowControlPage.getTitleInitialCashFlow() );

	}

}
