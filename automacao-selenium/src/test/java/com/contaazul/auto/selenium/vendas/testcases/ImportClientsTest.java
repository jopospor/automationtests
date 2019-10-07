package com.contaazul.auto.selenium.vendas.testcases;

import java.awt.AWTException;
import java.io.File;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.vendas.pages.ClientListPage;
import com.contaazul.auto.selenium.vendas.pages.ImportClientsPage;

public class ImportClientsTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void importaClientes(String filePath, String $result)
			throws InterruptedException, AWTException {

		getAssistants().getLoginAssistant( getWebDriver() ).login( "ImportClientsTest@contaazul.com", "12345" );
		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Cadastros", "Clientes" );
		step( "Navega para clientes" );

		ClientListPage clients = getPaginas().getClientListPage( getWebDriver() );
		sleep( VERY_LONG );
		clients.clickImportClients();
		ImportClientsPage importPopUp = getPaginas().getImportClientsPage( getWebDriver() );
		step( "Abre popup de importação de clientes" );

		String workingDir = System.getProperty( "user.dir" );

		clients.clickUploadFile();
		getAssistants()
				.getRobotAssistant( getWebDriver() )
				.typeMessage(
						workingDir + "\\src\\test\\resources\\file_attachments\\planilha_importacao_clientes.xlsx" );

		getAssistants().getRobotAssistant( getWebDriver() ).pressKeyEnter();
		step( "Faz upload de arquivo" );

		importPopUp.clickImport();
		importPopUp.mapColumns();
		step( "Mapeia colunas" );

		long importTime = importPopUp.importFile();
		Reporter.log( "Tempo de Importação: " + importTime + " millisegundos." );

		checkPoint( "Não obteve a mensagem de sucesso esperada após a importação", $result,
				importPopUp.getResultMessage() );
		getWebDriver().findElement( By.id( "import-done" ) ).click();
		clients.selectAllCheckBox();
		clients.clickDeleteClient();
		clients.clickAlert();
		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}
}
