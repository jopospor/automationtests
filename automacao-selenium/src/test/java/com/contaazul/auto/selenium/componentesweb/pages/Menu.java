package com.contaazul.auto.selenium.componentesweb.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Constantes que representam o menu superior do conta azul
 * 
 */

public enum Menu {

	MENU(null, null),
	VENDAS(MENU, "Vendas"),
	PRODUTOS(VENDAS, "Produtos"),
	VENDAS_DE_PRODUTOS(PRODUTOS, "Vendas de Produtos"),
	FINANCEIRO(MENU, "Financeiro"),
	ORGANIZAR(FINANCEIRO, "Organizar"),
	CONTAS_BANCARIAS(ORGANIZAR, "Contas bancárias"),
	MOVIMENTACOES(FINANCEIRO, "Movimentações"),
	CONTAS_A_RECEBER(MOVIMENTACOES, "Contas a Receber"),
	IMPORTAR_EXTRATO(MOVIMENTACOES, "Importar Extrato");

	private Menu parent;
	private String description;

	private Menu(Menu parent, String description) {
		this.parent = parent;
		this.description = description;
	}

	public List<String> toMenuSequence() {
		List<String> menuSequence = new ArrayList<String>();
		Menu current = this;

		while (true) {
			if (current == MENU)
				break;
			menuSequence.add( current.description );
			current = current.parent;
		}

		Collections.reverse( menuSequence );
		return menuSequence;
	}

	@Override
	public String toString() {
		return StringUtils.join( this.toMenuSequence() );
	}
}
