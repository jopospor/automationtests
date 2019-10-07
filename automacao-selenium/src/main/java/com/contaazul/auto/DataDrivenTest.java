package com.contaazul.auto;

//Classe base para todos os Testes

import lombok.extern.log4j.Log4j;

import org.testng.TestNGException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlTest;

import com.contaazul.auto.config.AutomationProperties;
import com.contaazul.auto.data.DataSheet;
import com.contaazul.auto.data.ExcelDataSheet;
import com.contaazul.auto.data.TSVDataSheet;
import com.contaazul.auto.selenium.SeleniumTest;

@Log4j
@Listeners({ com.contaazul.auto.DefaultListener.class,
		com.contaazul.auto.report.DefaultReportListener.class,
		com.contaazul.auto.report.RichReporter.class })
public class DataDrivenTest {

	private String testDataFilePath;

	private String testDataSheetName;

	protected DataSheet dataSheet;

	/*
	 * TestNG ordem de execução
	 * 
	 * Constructor
	 * 
	 * @BeforeTest
	 * 
	 * @BeforeClass
	 * 
	 * @BeforeMethod
	 * 
	 * @Test method
	 * 
	 * @AfterMethod
	 * 
	 * @AfterClass
	 * 
	 * @AfterTest
	 */

	/**
	 * Método que carrega dados de alguma fonte, por exemplo arquivos de excel
	 * ou texto tsv e retorna um array de array de objetos próprio para ser
	 * fonte de dados para o TestNG
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = SeleniumTest.DATA_PROVIDER)
	public Object[][] loadTestData() throws Exception {
		DataSheet dataSheet = null;
		if (getTestDataFilePath().toLowerCase().endsWith( "txt" ))
			dataSheet = new TSVDataSheet();
		else if (getTestDataFilePath().toLowerCase().endsWith( "xls" ))
			dataSheet = new ExcelDataSheet();
		else
			throw new TestNGException(
					"Erro carregando dados: Extensão não suportada. Arquivo:"
							+ getTestDataFilePath() );
		dataSheet.importSheet(
				TestSession.getSession().sessionProperties
						.getProperty( AutomationProperties.TEST_DATA_INPUT_DIR )
						+ getTestDataFilePath(), getTestDataSheetName() );
		return dataSheet.getTestData();
	}

	@Parameters({ "filePath", "sourceSheetName" })
	@BeforeTest(alwaysRun = true)
	public final void setUpTest(@Optional String filePath,
			@Optional String sheetName) {
		// parâmetros são carregados com valores entrados no XML Test NG
		setTestDataFilePath( filePath );
		setTestDataSheetName( sheetName );
		// 2DO IMPLEMENTAR POLITICAS DE RETRY TAMBÉM CUSTOMIZAVEIS POR <class>
	}

	@BeforeClass
	public void setUpClass() throws Exception {
	}

	@BeforeMethod
	public void setUpMethod(XmlTest teste) {
	}

	@AfterMethod(alwaysRun = true)
	public void tearDownMethod() throws Exception {
	}

	@AfterTest
	public void tearDownTest() throws Exception {
	}

	@AfterSuite
	public void tearDownSuite() {
	}

	// 2DO - fazer de CheckPoint uma classe a parte

	public String getTestDataFilePath() {
		if (testDataFilePath != null)
			return testDataFilePath;
		else {
			String[] nomes = this.getClass().getName().split( "\\." );
			return nomes[nomes.length - 1] + "_data.txt";
		}
	}

	public String getTestDataFilePathAsPassedByTestSuite() {
		return testDataFilePath;
	}

	public void setTestDataFilePath(String testDataFilePath) {
		this.testDataFilePath = testDataFilePath;
	}

	public String getTestDataSheetName() {
		return testDataSheetName;
	}

	public void setTestDataSheetName(String testDataSheetName) {
		this.testDataSheetName = testDataSheetName;
	}

	/**
	 * Função de callback invocada sempre que um teste falha definitivamente.
	 * Baseado na exceção
	 * 
	 * @param test
	 *            contexto do teste
	 * @param throwable
	 *            exceção que ocorreu
	 */
	public void disasterRecovery(DataDrivenTest test, Throwable throwable) {
		// 2DO implementar aqui o que fazer com o teste para cada tipo de
		// exceção.
		// Ex. org.openqa.selenium.NoSuchElementException
	}

}
