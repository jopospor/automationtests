package com.contaazul.auto.selenium.assistants;

import br.com.informant.agil.ejb.entity.CompanyFeature;
import br.com.informant.agil.ejb.entity.UsoEmissaoNF;
import br.com.informant.agil.ejb.entity.UsoProdutoServico;
import br.com.informant.agil.ejb.entity.UsoVendedor;

import com.contaazul.accountconfiguration.AccountConfiguration;
import com.contaazul.base.BaseURL;
import com.contaazul.restclient.ClientSessionResource;
import com.contazul.client.admin.TenantClient;

public class AccountAssistant {

	public static String createProductNFe(CompanyFeature companyFeature) throws Exception {
		return create( companyFeature, UsoProdutoServico.SOMENTE_PRODUTO, UsoEmissaoNF.EMITIR_NF,
				UsoVendedor.NAO_POSSUI );
	}

	public static String createProductWithoutNFe(CompanyFeature companyFeature) throws Exception {
		return create( companyFeature, UsoProdutoServico.SOMENTE_PRODUTO, UsoEmissaoNF.NAO_EMITIR,
				UsoVendedor.POSSUI_VENDEDOR_COMISSAO );
	}

	public static String createServiceNFse(CompanyFeature companyFeature) throws Exception {
		return create( companyFeature, UsoProdutoServico.SOMENTE_SERVICO, UsoEmissaoNF.EMITIR_NFS,
				UsoVendedor.POSSUI_VENDEDOR_COMISSAO );
	}

	public static String createServiceWithoutNFse(CompanyFeature companyFeature) throws Exception {
		return create( companyFeature, UsoProdutoServico.SOMENTE_SERVICO, UsoEmissaoNF.NAO_EMITIR,
				UsoVendedor.NAO_POSSUI );
	}

	public static String createProductServiceNFse(CompanyFeature companyFeature) throws Exception {
		return create( companyFeature, UsoProdutoServico.PRODUTO_SERVICO, UsoEmissaoNF.EMITIR_NFS,
				UsoVendedor.NAO_POSSUI );
	}

	public static String createProductServiceNFe(CompanyFeature companyFeature) throws Exception {
		return create( companyFeature, UsoProdutoServico.PRODUTO_SERVICO, UsoEmissaoNF.EMITIR_NF,
				UsoVendedor.POSSUI_VENDEDOR_COMISSAO );
	}

	public static String createProductService(CompanyFeature companyFeature) throws Exception {
		return create( companyFeature, UsoProdutoServico.PRODUTO_SERVICO, UsoEmissaoNF.NAO_EMITIR,
				UsoVendedor.NAO_POSSUI );
	}

	public static String createFinanceControl(CompanyFeature companyFeature) throws Exception {
		return create( companyFeature, UsoProdutoServico.NAO_CONTROLA, UsoEmissaoNF.NAO_EMITIR,
				UsoVendedor.NAO_POSSUI );
	}

	public static String createProductServiceNFeNFse(CompanyFeature companyFeature) throws Exception {
		return create( companyFeature, UsoProdutoServico.PRODUTO_SERVICO, UsoEmissaoNF.EMITIR_NF_NFS,
				UsoVendedor.NAO_POSSUI );
	}

	public static String create(CompanyFeature companyFeature, UsoProdutoServico usoProdutoServico,
			UsoEmissaoNF usoEmissaoNF, UsoVendedor usoVendedor) throws Exception {
		AccountConfiguration accountConfiguration = new AccountConfiguration( null, usoProdutoServico,
				usoEmissaoNF, usoVendedor, null );
		ClientSessionResource clientResource = new ClientSessionResource( BaseURL.getBaseURL() );
		return new TenantClient( clientResource ).createTenantGuest( companyFeature, accountConfiguration );
	}

}
