package com.contaazul.auto.selenium.contaazulpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.contaazul.auto.selenium.assistants.FinancialAssistant;
import com.contaazul.auto.selenium.clientes.pages.AdminPage;
import com.contaazul.auto.selenium.clientes.pages.BillingFormPage;
import com.contaazul.auto.selenium.clientes.pages.CheckoutModalSelectYourPlanPage;
import com.contaazul.auto.selenium.clientes.pages.CheckoutPlansAndPricePage;
import com.contaazul.auto.selenium.clientes.pages.HeaderContaAzulPage;
import com.contaazul.auto.selenium.clientes.pages.LoginPage;
import com.contaazul.auto.selenium.clientes.pages.NewUserStepsPage;
import com.contaazul.auto.selenium.clientes.pages.PlansAndPricingPage;
import com.contaazul.auto.selenium.clientes.pages.RegisterPage;
import com.contaazul.auto.selenium.clientes.pages.RegisterWizardInvoicesPage;
import com.contaazul.auto.selenium.clientes.pages.RegisterWizardPage;
import com.contaazul.auto.selenium.clientes.pages.TourFlowControlPage;
import com.contaazul.auto.selenium.componentesweb.pages.NewMenuPage;
import com.contaazul.auto.selenium.dashboard.pages.DashboardBankAccountPage;
import com.contaazul.auto.selenium.dashboard.pages.DashboardPage;
import com.contaazul.auto.selenium.dashboard.pages.GraphicalDashboardPage;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.BankAccountsPage;
import com.contaazul.auto.selenium.financeiro.pages.ConfigureBankslipPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateBankAccountPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateCategoryPage;
import com.contaazul.auto.selenium.financeiro.pages.CreateIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.DeleteIncomePage;
import com.contaazul.auto.selenium.financeiro.pages.EditCategoryPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceFlowPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceHistoryPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceImportExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceSpreadsheetPage;
import com.contaazul.auto.selenium.financeiro.pages.ImportBankExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.InLineBankAccountPage;
import com.contaazul.auto.selenium.financeiro.pages.InLineFinantialStatementPage;
import com.contaazul.auto.selenium.financeiro.pages.IncomePage;
import com.contaazul.auto.selenium.financeiro.pages.NewImportExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.RegisterRevenueAndExpenseAttachedPage;
import com.contaazul.auto.selenium.financeiro.pages.VirtualKeyboardPage;
import com.contaazul.auto.selenium.fiscal.pages.EntranceInvoiceListPage;
import com.contaazul.auto.selenium.fiscal.pages.EntranceInvoicePage;
import com.contaazul.auto.selenium.fiscal.pages.InvoiceListPage;
import com.contaazul.auto.selenium.fiscal.pages.InvoicePage;
import com.contaazul.auto.selenium.fiscal.pages.NfePage;
import com.contaazul.auto.selenium.vendas.pages.ClientListPage;
import com.contaazul.auto.selenium.vendas.pages.ImportClientsPage;
import com.contaazul.auto.selenium.vendas.pages.ListSalesOrderFormPage;
import com.contaazul.auto.selenium.vendas.pages.NewProductSalePage;
import com.contaazul.auto.selenium.vendas.pages.NewSalePage;
import com.contaazul.auto.selenium.vendas.pages.PurchaseListPage;
import com.contaazul.auto.selenium.vendas.pages.SalesOrderFormPage;

public class ContaAzulPages {

	private LoginPage loginPage;
	private HeaderContaAzulPage headerContaAzulPage;
	private FinanceExtractPage financeExtractPage;
	private FinanceImportExtractPage financeImportExtractPage;
	private FinanceSpreadsheetPage financeSpreadsheetPage;
	private InLineFinantialStatementPage inLineFinantialStatementPage;
	private BankAccountsPage bankAccountsPage;
	private CreateBankAccountPage createBankAccountPage;
	private CreateIncomePage createIncomePage;
	private InLineBankAccountPage inLineBankAccountPage;
	private BillingFormPage billingFormPage;
	private ConfigureBankslipPage configureBankslipPage;
	private RegisterPage registerPage;
	private RegisterWizardPage registerWizardPage;
	private RegisterWizardInvoicesPage registerWizardInvoicesPage;
	private NewUserStepsPage newUserStepsPage;
	private AdminPage adminPage;
	private TourFlowControlPage tourFlowControlPage;
	private PurchaseListPage purchaseListPage;
	private EntranceInvoiceListPage entranceInvoiceListPage;
	private EntranceInvoicePage entranceInvoicePage;
	private InvoiceListPage invoiceListPage;
	private InvoicePage invoicePage;
	private ClientListPage clientListPage;
	private ImportClientsPage importClientsPage;
	private ListSalesOrderFormPage listSalesOrderFormPage;
	private SalesOrderFormPage salesOrderFormPage;
	private PlansAndPricingPage plansAndPricingPage;
	private NewProductSalePage newProductSalePage;
	private FinanceHistoryPage financeHistoryPage;
	private FinancialAssistant financialAssistant;
	private FinanceFlowPage financeFlowPage;
	private RegisterRevenueAndExpenseAttachedPage registerExpenseAttachedPage;
	private ImportBankExtractPage importBankExtractPage;
	private IncomePage incomePage;
	private NfePage nfePage;
	private DashboardPage dashboardPage;
	private DeleteIncomePage deleteIncomePage;
	private DashboardBankAccountPage dashboardBankAccountPage;
	private CheckoutPlansAndPricePage checkoutPlansAndPricePage;
	private CheckoutModalSelectYourPlanPage checkoutModalSelectYourPlanPage;
	private VirtualKeyboardPage virtualKeyboardPage;
	private NewImportExtractPage newImportExtractPage;
	private NewSalePage newSalePage;
	private GraphicalDashboardPage graphicalDashboardPage;
	private NewMenuPage newMenuPage;

	public NewSalePage getNewSalePage(WebDriver driver) {
		if (newSalePage == null)
			newSalePage = PageFactory.initElements( driver,
					NewSalePage.class );
		return newSalePage;
	}

	public NewMenuPage getNewMenuPage(WebDriver driver) {
		if (newMenuPage == null)
			newMenuPage = PageFactory.initElements( driver,
					NewMenuPage.class );
		return newMenuPage;
	}

	public GraphicalDashboardPage getGraphicalDashboardPage(WebDriver driver) {
		if (graphicalDashboardPage == null)
			graphicalDashboardPage = PageFactory.initElements( driver,
					GraphicalDashboardPage.class );
		return graphicalDashboardPage;
	}

	public VirtualKeyboardPage getVirtualKeyboardPage(WebDriver driver) {
		if (virtualKeyboardPage == null)
			virtualKeyboardPage = PageFactory.initElements( driver,
					VirtualKeyboardPage.class );
		return virtualKeyboardPage;
	}

	public NfePage getNfePage(WebDriver driver) {
		if (nfePage == null)
			nfePage = PageFactory.initElements( driver, NfePage.class );
		return nfePage;
	}

	public NewImportExtractPage getNewImportExtractPage(WebDriver driver) {
		if (newImportExtractPage == null)
			newImportExtractPage = PageFactory.initElements( driver,
					NewImportExtractPage.class );
		return newImportExtractPage;
	}

	public DashboardBankAccountPage getDashboardBankAccountPage(WebDriver driver) {
		if (dashboardBankAccountPage == null)
			dashboardBankAccountPage = PageFactory.initElements( driver,
					DashboardBankAccountPage.class );
		return dashboardBankAccountPage;
	}

	public DeleteIncomePage getDeleteIncomePage(WebDriver driver) {
		if (deleteIncomePage == null)
			deleteIncomePage = PageFactory.initElements( driver,
					DeleteIncomePage.class );
		return deleteIncomePage;
	}

	public DashboardPage getDashboardPage(WebDriver driver) {
		if (dashboardPage == null)
			dashboardPage = PageFactory.initElements( driver,
					DashboardPage.class );
		return dashboardPage;
	}

	public CheckoutModalSelectYourPlanPage getCheckoutModalSelectYourPlanPage(WebDriver driver) {
		if (checkoutModalSelectYourPlanPage == null)
			checkoutModalSelectYourPlanPage = PageFactory.initElements( driver,
					CheckoutModalSelectYourPlanPage.class );
		return checkoutModalSelectYourPlanPage;
	}

	public CheckoutPlansAndPricePage getCheckoutPlansAndPricePage(WebDriver driver) {
		if (checkoutPlansAndPricePage == null)
			checkoutPlansAndPricePage = PageFactory.initElements( driver,
					CheckoutPlansAndPricePage.class );
		return checkoutPlansAndPricePage;
	}

	public IncomePage getIncomePage(WebDriver driver) {
		this.incomePage = new IncomePage( new AccountReceivablePage( driver ), driver );
		return incomePage;
	}

	public RegisterRevenueAndExpenseAttachedPage getregisterExpenseAttachedPage(WebDriver driver) {
		if (registerExpenseAttachedPage == null)
			registerExpenseAttachedPage = PageFactory.initElements( driver,
					RegisterRevenueAndExpenseAttachedPage.class );
		return registerExpenseAttachedPage;
	}

	public ImportBankExtractPage getImportBankExtractPage(WebDriver driver) {
		if (importBankExtractPage == null)
			importBankExtractPage = PageFactory.initElements( driver,
					ImportBankExtractPage.class );
		return importBankExtractPage;
	}

	public FinanceFlowPage getFinanceFlowPage(WebDriver driver) {
		if (financeFlowPage == null)
			financeFlowPage = PageFactory.initElements( driver,
					FinanceFlowPage.class );
		return financeFlowPage;
	}

	public CreateIncomePage getCreateIncomePage(WebDriver driver) {
		if (createIncomePage == null)
			createIncomePage = PageFactory.initElements( driver,
					CreateIncomePage.class );
		return createIncomePage;
	}

	public FinancialAssistant getFinancialTransferPage(WebDriver driver) {
		if (financialAssistant == null)
			financialAssistant = PageFactory.initElements( driver,
					FinancialAssistant.class );
		return financialAssistant;
	}

	public TourFlowControlPage getTourFlowControlPage(WebDriver driver) {
		if (tourFlowControlPage == null)
			tourFlowControlPage = PageFactory.initElements( driver,
					TourFlowControlPage.class );
		return tourFlowControlPage;
	}

	public AdminPage getAdminPage(WebDriver driver) {
		if (adminPage == null)
			adminPage = PageFactory.initElements( driver, AdminPage.class );
		return adminPage;
	}

	public NewUserStepsPage getNewUserStepsPage(WebDriver driver) {
		if (newUserStepsPage == null)
			newUserStepsPage = PageFactory.initElements( driver,
					NewUserStepsPage.class );
		return newUserStepsPage;
	}

	public LoginPage getPaginaLogin(WebDriver driver) {
		if (loginPage == null)
			loginPage = PageFactory.initElements( driver, LoginPage.class );
		return loginPage;
	}

	public HeaderContaAzulPage getPaginaInicial(WebDriver driver) {
		if (headerContaAzulPage == null)
			headerContaAzulPage = PageFactory.initElements( driver,
					HeaderContaAzulPage.class );
		return headerContaAzulPage;
	}

	public FinanceExtractPage getFinanceExtractPage(WebDriver driver) {
		if (financeExtractPage == null)
			financeExtractPage = PageFactory.initElements( driver,
					FinanceExtractPage.class );
		return financeExtractPage;
	}

	public FinanceImportExtractPage getPaginaImportacaoExtrato(WebDriver driver) {
		if (financeImportExtractPage == null)
			financeImportExtractPage = PageFactory.initElements( driver,
					FinanceImportExtractPage.class );
		return financeImportExtractPage;
	}

	public FinanceSpreadsheetPage getPaginaImportacaoPlanilhaExtrato(WebDriver driver) {
		if (financeSpreadsheetPage == null)
			financeSpreadsheetPage = PageFactory.initElements( driver, FinanceSpreadsheetPage.class );
		return financeSpreadsheetPage;
	}

	public InLineFinantialStatementPage getPaginaLancamentoExtratoInline(
			WebDriver driver) {
		if (inLineFinantialStatementPage == null)
			inLineFinantialStatementPage = PageFactory.initElements( driver,
					InLineFinantialStatementPage.class );
		return inLineFinantialStatementPage;
	}

	public BankAccountsPage getPaginaContasBancarias(WebDriver driver) {
		if (bankAccountsPage == null)
			bankAccountsPage = PageFactory.initElements( driver,
					BankAccountsPage.class );
		return bankAccountsPage;
	}

	public CreateBankAccountPage getPaginaAdicionarContaBancaria(
			WebDriver driver) {
		if (createBankAccountPage == null)
			createBankAccountPage = PageFactory.initElements( driver,
					CreateBankAccountPage.class );
		return createBankAccountPage;
	}

	public InLineBankAccountPage getPaginaContaBancariaInline(WebDriver driver,
			WebElement tr) {
		inLineBankAccountPage = PageFactory.initElements( driver,
				InLineBankAccountPage.class );
		inLineBankAccountPage.setReferenciaNaTela( tr );
		return inLineBankAccountPage;
	}

	public BillingFormPage getPaginaDadosBilling(RemoteWebDriver driver) {
		if (billingFormPage == null)
			billingFormPage = PageFactory.initElements( driver,
					BillingFormPage.class );
		return billingFormPage;
	}

	public ConfigureBankslipPage getConfigureBankslipPage(RemoteWebDriver driver) {
		if (configureBankslipPage == null)
			configureBankslipPage = PageFactory.initElements( driver,
					ConfigureBankslipPage.class );
		return configureBankslipPage;
	}

	public RegisterPage getRegisterPage(RemoteWebDriver driver) {
		if (registerPage == null)
			registerPage = PageFactory.initElements( driver, RegisterPage.class );
		return registerPage;
	}

	public RegisterWizardPage getRegisterWizardPage(RemoteWebDriver driver) {
		if (registerWizardPage == null)
			registerWizardPage = PageFactory.initElements( driver,
					RegisterWizardPage.class );
		return registerWizardPage;
	}

	public RegisterWizardInvoicesPage getRegisterWizardInvoicesPage(
			RemoteWebDriver driver) {
		if (registerWizardInvoicesPage == null)
			registerWizardInvoicesPage = PageFactory.initElements( driver,
					RegisterWizardInvoicesPage.class );
		return registerWizardInvoicesPage;
	}

	public PurchaseListPage getPurchaseListPage(RemoteWebDriver driver) {
		if (purchaseListPage == null)
			purchaseListPage = PageFactory.initElements( driver,
					PurchaseListPage.class );
		return purchaseListPage;
	}

	public EntranceInvoiceListPage getEntranceInvoiceListPage(
			RemoteWebDriver driver) {
		if (entranceInvoiceListPage == null)
			entranceInvoiceListPage = PageFactory.initElements( driver,
					EntranceInvoiceListPage.class );
		return entranceInvoiceListPage;
	}

	public EntranceInvoicePage getEntranceInvoicePage(RemoteWebDriver driver) {
		if (entranceInvoicePage == null)
			entranceInvoicePage = PageFactory.initElements( driver,
					EntranceInvoicePage.class );
		return entranceInvoicePage;
	}

	public InvoiceListPage getInvoiceListPage(RemoteWebDriver driver) {
		if (invoiceListPage == null)
			invoiceListPage = PageFactory.initElements( driver,
					InvoiceListPage.class );
		return invoiceListPage;
	}

	public InvoicePage getInvoicePage(RemoteWebDriver driver) {
		if (invoicePage == null)
			invoicePage = PageFactory.initElements( driver, InvoicePage.class );
		return invoicePage;
	}

	public ClientListPage getClientListPage(RemoteWebDriver driver) {
		if (clientListPage == null)
			clientListPage = PageFactory.initElements( driver, ClientListPage.class );
		return clientListPage;
	}

	public ImportClientsPage getImportClientsPage(RemoteWebDriver driver) {
		if (importClientsPage == null)
			importClientsPage = PageFactory.initElements( driver, ImportClientsPage.class );
		return importClientsPage;
	}

	public ListSalesOrderFormPage getListSalesOrderPage(RemoteWebDriver driver) {
		if (listSalesOrderFormPage == null)
			listSalesOrderFormPage = PageFactory.initElements( driver,
					ListSalesOrderFormPage.class );
		return listSalesOrderFormPage;
	}

	public SalesOrderFormPage getSalesOrderPage(RemoteWebDriver driver) {
		if (salesOrderFormPage == null)
			salesOrderFormPage = PageFactory.initElements( driver,
					SalesOrderFormPage.class );
		return salesOrderFormPage;
	}

	public PlansAndPricingPage getPlansAndPricingPage(RemoteWebDriver driver) {
		if (plansAndPricingPage == null)
			plansAndPricingPage = PageFactory.initElements( driver,
					PlansAndPricingPage.class );
		return plansAndPricingPage;
	}

	public NewProductSalePage getNewProductSalePage(RemoteWebDriver driver) {
		if (newProductSalePage == null)
			newProductSalePage = PageFactory.initElements( driver,
					NewProductSalePage.class );
		return newProductSalePage;
	}

	public FinanceHistoryPage getFinanceHistoryPage(RemoteWebDriver driver) {
		if (financeHistoryPage == null)
			financeHistoryPage = PageFactory.initElements( driver,
					FinanceHistoryPage.class );
		return financeHistoryPage;
	}

	public AccountReceivablePage getAccountReceivablePage(RemoteWebDriver driver) {
		return PageFactory.initElements( driver, AccountReceivablePage.class );
	}

	public FinancialAssistant getCreateTransferFromOfxFilePage(RemoteWebDriver driver) {
		return PageFactory.initElements( driver, FinancialAssistant.class );
	}

	public EditCategoryPage getEditCategoryPage(RemoteWebDriver driver) {
		return PageFactory.initElements( driver, EditCategoryPage.class );
	}

	public CreateCategoryPage getCreateCategoryPage(RemoteWebDriver driver) {
		return PageFactory.initElements( driver, CreateCategoryPage.class );
	}

}
