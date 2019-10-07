package com.contaazul.auto.selenium.vendas.testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.FinancialAssistant;
import com.contaazul.auto.selenium.financeiro.pages.AccountReceivablePage;
import com.contaazul.auto.selenium.financeiro.pages.FinanceExtractPage;
import com.contaazul.auto.selenium.financeiro.pages.Period;
import com.contaazul.auto.selenium.vendas.pages.NewSalePage;

public class CreateNewSaleTest extends SeleniumTest {

	protected NewSalePage salePage;
	private Double calc = 0.0;
	private String saleNumber;
	private String totalLiquid;
	private Double valueTotal = 0.0;

	@BeforeClass
	public void startup() {
		getAssistants().getLoginAssistant( getWebDriver() ).login( "NewSaleTest@contaazul.com", "12345" );

		getAssistants().getModalAssistant( getWebDriver() ).closeAllModalWindowsAndPopups();

		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Vendas", "Lista" );
		salePage = getPaginas().getNewSalePage( getWebDriver() );
	}

	@Test(dataProvider = DATA_PROVIDER)
	public void CreateNew(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate, String quantityItens, String item, String itemDesc, String itemQuantity,
			String itemPrice, String discountType, String discountValue, String freightValue, String validateResume) {

		salePage = getPaginas().getNewSalePage( getWebDriver() );
		step( "Abrir o cadastro da Nova Venda" );
		salePage.addNewSale();

		step( "Verifica se o número da venda foi gerado sequencialmente" );
		if (!(salePage.getResumeTableRows() == 0))
			verifySequentialSaleNumber();

		step( "Preencher primeiros campos" );
		fillFirstFields( client, saleNumber, saleDate, paymentType, paymentMaturityDate );

		step( "Preencher lista de itens" );
		fillItemsList( quantityItens, item, itemDesc, itemQuantity,
				itemPrice, discountType, discountValue, freightValue );

		step( "Valida os valores" );
		verifySaleValues( quantityItens, discountType );

		step( "Salva o cadastro" );
		saveNegotiation();

		if (Boolean.parseBoolean( validateResume )) {
			step( "Valida os dados do Resumo da Venda" );
			verifySaleResume( client, saleDate, paymentMaturityDate, paymentType, quantityItens, discountType );

			step( "Valida se a venda está aparecendo na listagem" );
			verifySaleList();

			step( "Verifica o lançamento da venda no extrato do Financeiro" );
			verifyFinancialExtract();
		}
	}

	@AfterClass
	public void teardown() {
		getAssistants().getLoginAssistant( getWebDriver() ).logout( true );
	}

	public void verifyFinancialExtract() {
		getAssistants().getNewMenuAssistant( getWebDriver() ).navigateMenu(
				"Financeiro", "Movimentações", "Extrato" );

		AccountReceivablePage accountReceivablePage = getPaginas().getAccountReceivablePage( getWebDriver() );
		FinanceExtractPage fin = getPaginas().getFinanceExtractPage( getWebDriver() );
		FinancialAssistant financialAssistant = getAssistants().getFinancialAssistant( getWebDriver() );

		accountReceivablePage.filterPeriod( Period.MOSTRAR_TODOS );
		fin.search( saleNumber );
		checkPoint( "Valida se a Receita está gerada e com o valor correto.", totalLiquid,
				salePage.convertValues( financialAssistant.getValue( 1 ) ) );
	}

	public void verifySaleList() {
		salePage.goBackToSaleList();

		sleep( VERY_LONG );

		checkPoint( "Verifica se a venda gerada aparece na lista de vendas",
				saleNumber,
				String.valueOf( Integer.parseInt( driver.findElement(
						By.xpath( "//*[@id='conteudo']/div/div/table/tbody/tr[2]/td[2]" ) ).getText() ) ) );
	}

	public void verifySaleResume(String client, String saleDate, String paymentMaturityDate, String paymentType,
			String quantityItens, String discountType) {
		checkPoint( "Resumo da venda - número da venda no cabeçalho",
				client,
				salePage.getClient() );

		checkPoint( "Resumo da venda - campo 'Cliente'",
				saleNumber,
				String.valueOf( salePage.getHeaderSaleNumber() ) );

		checkPoint( "Resumo da venda - campo 'Data da Venda'",
				saleDate,
				String.valueOf( salePage.getSaleFirstDueDate() ) );

		checkPoint( "Resumo da venda - campo 'Data de Vencimento'",
				paymentMaturityDate,
				String.valueOf( salePage.getSaleDate() ) );

		checkPoint( "Resumo da venda - campo 'Condições de Pagamento'",
				paymentType,
				String.valueOf( salePage.getSaleInstallment() ) );

		checkPoint( "Resumo da venda - Quantidade de itens'",
				quantityItens,
				String.valueOf( salePage.getResumeTableRows() + 1 ) );

		verifySaleResumeValues( quantityItens, discountType );

	}

	public void verifySaleResumeValues(String qtdeItens, String discountType) {
		for (int i = 0; i != Integer.parseInt( qtdeItens ); i++) {
			calc = salePage.getResumePriceItem( i ) * salePage.getResumeQttyItem( i );

			checkPoint( "[Resumo da venda] Validação de cálculos de 'Subtotal' da lista de itens",
					String.valueOf( calc ),
					String.valueOf( salePage.getResumeSubTotal( i ) ) );
		}

		checkPoint( "[Resumo da venda] Validação do 'Valor total' da Nova Venda",
				String.valueOf( calc ),
				String.valueOf( salePage.getSomaVlrTotal() ) );

		calc = salePage.getSomaVlrTotal() - salePage.getResumeDiscountValue() + salePage.getResumeFreightValue();

		checkPoint( "[Resumo da venda] Validação do 'Total líquido' da Nova Venda",
				String.valueOf( calc ),
				String.valueOf( salePage.getSaleTotalLiquid() ) );

		checkPoint( "[Resumo da venda] Validação do 'Total líquido do cabeçalho' da Nova Venda",
				String.valueOf( calc ),
				String.valueOf( salePage.getHeaderSaleTotalLiquid() ) );
	}

	public void verifySaleValues(String qtdeItens, String discountType) {
		valueTotal = 0.0;
		for (int i = 0; i != Integer.parseInt( qtdeItens ); i++) {
			calc = salePage.getPriceItem( i ) * salePage.getQttyItem( i );

			checkPoint( "Validação de cálculos de 'Subtotal' da lista de itens",
					String.valueOf( calc ),
					String.valueOf( salePage.getSubTotal( i ) ) );
			valueTotal += calc;
		}

		checkPoint( "Validação do 'Valor total' da Nova Venda",
				String.valueOf( valueTotal ),
				String.valueOf( salePage.getSomaVlrTotal() ) );

		if (!discountType.equals( "%" ))
			calc = salePage.getSomaVlrTotal() - salePage.getDiscountValue();
		else
			calc = salePage.getSomaVlrTotal() - (salePage.getSomaVlrTotal() * (salePage.getDiscountValue() * 0.01));

		calc += salePage.getFreightValue();

		checkPoint( "Validação do 'Total líquido' da Nova Venda",
				String.valueOf( calc ),
				String.valueOf( salePage.getSaleTotalLiquid() ) );

		checkPoint( "Validação do 'Total líquido do cabeçalho' da Nova Venda",
				String.valueOf( calc ),
				String.valueOf( salePage.getHeaderSaleTotalLiquid() ) );

		totalLiquid = String.valueOf( calc );
	}

	public void verifySequentialSaleNumber() {
		checkPoint( "Validação para o número sequencial da venda no campo 'Nº da Venda'.",
				String.valueOf( salePage.getLastSaleNumber() + 1 ),
				salePage.getSaleNumber() );

		checkPoint( "Validação para o número sequencial da venda no cabeçalho.",
				String.valueOf( salePage.getLastSaleNumber() + 1 ),
				salePage.getHeaderSaleNumber() );
	}

	public void fillFirstFields(String client, String saleNumber, String saleDate, String paymentType,
			String paymentMaturityDate) {
		salePage.setSaleClient( client );
		salePage.setSaleNumber( saleNumber );
		salePage.setSaleDate( saleDate );
		salePage.setSalePaymentType( paymentType );
		salePage.setPaymentMaturityDate( paymentMaturityDate );
	}

	public void fillItemsList(String quantityItens, String item, String itemDesc, String itemQuantity,
			String itemPrice, String discountType, String discountValue, String freightValue) {
		if (Integer.parseInt( quantityItens ) != 1)
			salePage.fillRandomItemsList( quantityItens, discountType, discountValue, freightValue );
		else
			salePage.addOneItemOnList( item, itemDesc, itemQuantity, itemPrice, discountType, discountValue,
					freightValue );
	}

	public void saveNegotiation() {
		saleNumber = salePage.getSaleNumber();

		salePage.saveNewSale();

		checkPoint( "Validação para a mensagem de venda salva com sucesso.", "A venda nº " + saleNumber
				+ " foi criada com sucesso.", salePage.getMessageTopModal() );
	}
}
