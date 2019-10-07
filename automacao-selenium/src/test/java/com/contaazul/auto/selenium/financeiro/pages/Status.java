package com.contaazul.auto.selenium.financeiro.pages;

public enum Status {

	TODOS("Todos"),
	VENCIDOS("Vencidos"),
	CONTRATO_DE_VENDA("Contrato de Venda"),
	CONTAS_A_RECEBER("Contas a Receber"),
	RECEBIDO("Recebido");

	private String description;

	private Status(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return this.description;
	}

}
