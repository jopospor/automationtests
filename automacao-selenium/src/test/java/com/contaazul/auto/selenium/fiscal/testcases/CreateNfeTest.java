package com.contaazul.auto.selenium.fiscal.testcases;

import org.testng.annotations.Test;

import com.contaazul.auto.selenium.SeleniumTest;
import com.contaazul.auto.selenium.assistants.NotificationAssistant;
import com.contaazul.auto.selenium.fiscal.pages.NfePage;

public class CreateNfeTest extends SeleniumTest {

	@Test(dataProvider = DATA_PROVIDER)
	public void createNfeTest(
			String login, String password,
			String optionMenu,
			String nameClient,
			String serie,
			String item,
			String qty,
			String valueItem,
			String optionFreight,
			String transport,
			String valueFreight,
			String ehsimplesnacional,
			String Cfop,
			String situationIcms,
			String bcIcms,
			String valueTotalSale,
			String aliquotIcmsNaoSimples,
			String creditIcms,
			String valueIcms,
			String valueTotalInvoice,
			String statusInvoice,
			String origin,
			String transporterNf,
			String transportNf,
			String valueFreightNf,
			String submod,
			String subicms,
			String subBc,
			String subAliquot,
			String codEnq,
			String situationIpi,
			String bcIpi,
			String aliquotIpi,
			String situationPis,
			String bcPis,
			String aliquotPis,
			String valuePis,
			String situationCofins,
			String bcCofins,
			String aliquotCofins,
			String valueTotalPis,
			String valueTotalCofins,
			String valueTotalIpi,
			String aliquot101,
			String percentReduceBcIcms,
			String typeMod201,
			String aliquot201,
			String aliquotIcms201,
			String mva201,
			String subAliquot201,
			String typeMod202,
			String mva202,
			String aliquot202,
			String subAliquotIcms202,
			String qtyTransporter) {

		NfePage nfePage = getPaginas().getNfePage( getWebDriver() );
		NotificationAssistant notificationAssistant = getAssistants().getNotificationAssistant( getWebDriver() );

		getAssistants().getLoginAssistant( getWebDriver() ).login( login, password );
		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Produtos", optionMenu );

		nfePage.clickOnOption( "Nova Venda" );
		nfePage.selectClient( nameClient );
		nfePage.setSeries( serie );
		nfePage.selectItem( item );
		nfePage.setQuantity( qty );
		nfePage.setValue( valueItem );
		step( "Cria uma venda para emissão da nota" );

		nfePage.clickOnOption( "Informar frete" );
		nfePage.selectOptionFreight( optionFreight );
		if (!(optionFreight == "Não")) {
			nfePage.selectTransporter( transport );
			nfePage.setFreight( valueFreight );
		}
		step( "Expande a opção de frete e seleciona sim ou não" );

		nfePage.clickOnOption( "Salvar" );
		getAssistants().getNotificationAssistant( getWebDriver() ).waitMessageAppear();
		checkPoint( "Venda não foi gerada com sucesso", "Venda de Produto, nº " + nfePage.getNrSale( 0 )
				+ ", emitida com sucesso.", notificationAssistant.getAlertMessageText() );
		nfePage.selectCheckboxLine( 0 );
		notificationAssistant.waitMessageDismiss();
		nfePage.clickAction();
		nfePage.clickOnOption( "Emitir Nota Fiscal" );
		step( "Seleciona primeira linha e clica na opção de emitir nota fiscal" );

		notificationAssistant.waitLoadingDismiss();
		nfePage.selectCfop( Cfop );
		nfePage.clickOnOption( "ICMS" );
		step( "Preenchimento - ICMS" );

		if (situationIcms.matches( "101" )) {
			nfePage.setTributarySituationIcms( "101 - Tributada pelo Simples Nacional com permissão de crédito" );
			nfePage.setAliquotApplyable( aliquot101 );
			nfePage.setCreditIcms( creditIcms );
			nfePage.setPercentReduceBcIms101( percentReduceBcIcms );
		}

		if (situationIcms.matches( "201" )) {
			nfePage.setTributarySituationIcms( "201 - Tributada pelo Simples Nacional com permissão de crédito e com cobrança do ICMS por substituição tributária" );
			nfePage.setModBcIcms( typeMod201 );
			nfePage.setAliquotApplyable( aliquot201 );
			nfePage.setAliquotIcms( aliquotIcms201 );
			nfePage.setMvaSt( mva201 );
			nfePage.setSubAliquotIcms( subAliquot201 );
		}

		if (situationIcms.matches( "202" )) {
			nfePage.setTributarySituationIcms( "202 - Tributada pelo Simples Nacional sem permissão de crédito e com cobrança do ICMS por substituição tributária" );
			nfePage.setModBcIcms( typeMod202 );
			nfePage.setMvaSt( mva202 );
			nfePage.setAliquotIcms( aliquot202 );
			nfePage.setSubAliquotIcms( subAliquotIcms202 );
		}
		step( "Preenchimento - Situação tributária ICMS" );
		checkPoint( "Origem errado", origin, nfePage.getOrigin() );

		if (ehsimplesnacional.toLowerCase().matches( "[n[ã|a]o|0]" )) {
			step( "Não é SIMPLES nacional" );

			nfePage.setModIcms( bcIcms );
			nfePage.setSubModBcIcms( submod, subicms, subAliquot );
			nfePage.setAliquotIcms( aliquotIcmsNaoSimples );
			checkPoint( "Valor icms incorreto", valueIcms, nfePage.getValueIcms() );
			checkPoint( "Base de Calculo icms incorreta", valueTotalSale, nfePage.getBasedCalculationIcms() );
			checkPoint( "Valor total icms incorreto", valueIcms, nfePage.getValueIcmsTotal() );
			checkPoint( "Valor total do produto incorreto", valueTotalSale, nfePage.getTotalProduct() );
			step( "Valida os valores totais do ICMS" );

			populateIPIfields( codEnq, situationIpi, bcIpi, aliquotIpi, nfePage );
			populatePISfields( situationPis, aliquotPis, valuePis, nfePage );
			populateCOFINSfields( situationCofins, aliquotCofins, valueTotalCofins, nfePage );
			step( "Preenchimento - IPI, PIS e COFINS" );

			checkPoint( "Valor total da base de calculo do PIS incorreto", bcPis, nfePage.getBasedCalculationPis() );
			checkPoint( "Valor total da base de calculo COFINS incorreto", bcCofins,
					nfePage.getBasedCalculationConfins() );
			checkPoint( "Valor total  do PIS incorreto", valueTotalPis, nfePage.getTotalValuePis() );
			checkPoint( "Valor total do COFINS incorreto", valueTotalCofins, nfePage.getTotalValueConfins() );
		}

		nfePage.setModTransporter( transporterNf );
		nfePage.selectTransporter( transportNf );
		nfePage.setValueFreightNf( valueFreightNf );
		nfePage.setQuantityTransporter( qtyTransporter );
		step( "Se o frete não for efetuado pela tela de venda, será efetuado por aqui" );

		checkPoint( "Valor total da nota incorreto", valueTotalInvoice, nfePage.getTotalInvoice() );

		nfePage.saveNf();
		step( "Salva NF-e" );

		getAssistants().getMenuAssistant( getWebDriver() ).navigateMenu( "Vendas", "Produtos", "Emitir NF-e" );
		notificationAssistant.waitMessageDismiss();
		nfePage.clickOnOption( "Enviar Nota Fiscal Eletrônica" );
		nfePage.waitSendNfe();
		step( "Clica em enviar nota fiscal e espera o status dela como:'" + statusInvoice + "'" );

		nfePage.waitForNFeUpdateStatus( statusInvoice );
		checkPoint( "Status da nota fiscal incorreto", statusInvoice, nfePage.getStatusNf() );

		nfePage.clickOnCancelNfeOption( "Teste Selenium cancelamento" );

		nfePage.waitForNFeUpdateStatus( "Cancelada" );

		getAssistants().getLoginAssistant( getWebDriver() ).logout();
	}

	private void populateCOFINSfields(String situationCofins, String aliquotCofins, String valueTotalCofins, NfePage nfe) {
		nfe.clickOnOption( "COFINS" );
		nfe.setAliquotCofins( aliquotCofins );
		nfe.setTributarySituationCofins( situationCofins );
		checkPoint( "Valor do COFINS está incorreto", valueTotalCofins, nfe.getValueConfins() );
	}

	private void populatePISfields(String situationPis, String aliquotPis, String valuePis, NfePage nfe) {
		nfe.clickOnOption( "PIS" );
		nfe.setAliquotPis( aliquotPis );
		nfe.setTributarySituationPis( situationPis );
		checkPoint( "Valor do PIS incorreto", valuePis, nfe.getValuePis() );
	}

	private void populateIPIfields(String codEnq, String situationIpi, String bcIpi, String aliquotIpi, NfePage nfe) {
		nfe.clickOnOption( "IPI" );
		nfe.setTributarySituationIpi( situationIpi );
		nfe.setBasedCalculationIpi( bcIpi );
		nfe.setAliquotIpiI( aliquotIpi );
		checkPoint( "Valor cod Enq incorreto", codEnq, nfe.getCodEnq() );
	}
}
