package com.contaazul.auto.selenium.clientes.testcases;

import static com.contaazul.auto.selenium.assistants.AccountAssistant.createProductServiceNFe;
import static com.contaazul.auto.selenium.assistants.CompanyAccountAssistant.createNewMenuCompanyFeature;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.componentesweb.pages.NewMenuPage;

public class NewMenuProductServiceNfeTest extends SeleniumTest {

	@BeforeClass
	public void login() throws Exception {
		getWebDriver().manage().window().maximize();

		getAssistants().getLoginAssistant( getWebDriver() ).login(
				createProductServiceNFe( createNewMenuCompanyFeature() ), "12345" );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void newMenuProductServiceNfeTest(String topMenu, String subCategory, String link, String expectedBreadcrumb) {
		NewMenuPage newMenuPage = getPaginas().getNewMenuPage( getWebDriver() );
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( topMenu, subCategory, link );
		step( "Navega pelo novo menu" );
		checkPoint( "A página indicada não carregou", expectedBreadcrumb, newMenuPage.getBreadcrumbText() );
		step( "Valida que o menu carregou corretamente" );
	}

	@AfterClass
	public void logout() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout( Boolean.TRUE );
	}

}
