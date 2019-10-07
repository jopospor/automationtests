package com.contaazul.auto.selenium.financeiro.pages;

public enum Period {

	HOJE("Hoje"),
	ESTA_SEMANA("Esta semana"),
	ESTE_MES("Este mês"),
	ULTIMOS_30_DIAS("Útimos 30 dias"),
	MOSTRAR_TODOS("Mostrar todos");

	private String description;

	private Period(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return this.description;
	}

}
