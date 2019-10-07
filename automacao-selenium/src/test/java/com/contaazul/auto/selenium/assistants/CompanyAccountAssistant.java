package com.contaazul.auto.selenium.assistants;

import br.com.informant.agil.ejb.entity.CompanyFeature;
import br.com.informant.agil.ejb.entity.CompanyFeature.BankAccountDashboard;
import br.com.informant.agil.ejb.entity.CompanyFeature.Minimalistic;

public class CompanyAccountAssistant {

	public static CompanyFeature createNewMenuCompanyFeature() {
		CompanyFeature companyFeature = new CompanyFeature();
		companyFeature.setMinimalistic( Minimalistic.ENABLED_WITH_GRAPH );
		companyFeature.setBankAccountDashboard( BankAccountDashboard.ENABLED );
		return companyFeature;
	}

	public static CompanyFeature createOldMenuCompanyFeature() {
		CompanyFeature companyFeature = new CompanyFeature();
		companyFeature.setMinimalistic( Minimalistic.DISABLED_BY_CHANCE );
		companyFeature.setBankAccountDashboard( BankAccountDashboard.DISABLED );
		return companyFeature;

	}

}
