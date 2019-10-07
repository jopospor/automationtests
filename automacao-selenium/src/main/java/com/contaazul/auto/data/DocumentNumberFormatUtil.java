package com.contaazul.auto.data;

import org.testng.TestNGException;

public class DocumentNumberFormatUtil {

	public static String formatCPF(String cpfNumbers) {
		try {
			Long.valueOf( cpfNumbers );
		} catch (NumberFormatException e) {
			throw new TestNGException(
					"CPF precisa ter somente caracteres numéricos.", e );
		}
		if (cpfNumbers.length() != 11)
			throw new TestNGException(
					"CPF precisa ter 11 caracteres, todos numéricos." );
		else {
			return cpfNumbers.substring( 0, 3 ) + "."
					+ cpfNumbers.substring( 3, 6 ) + "."
					+ cpfNumbers.substring( 6, 9 ) + "-"
					+ cpfNumbers.substring( 9, 11 );
		}
	}

	public static String formatCNPJ(String documentNumber) {
		try {
			Long.valueOf( documentNumber );
		} catch (NumberFormatException e) {
			throw new TestNGException(
					"CNPJ precisa ter somente caracteres numéricos.", e );
		}
		if (documentNumber.length() != 14)
			throw new TestNGException(
					"CNPJ precisa ter 14 caracteres, todos numéricos." );
		else {
			return documentNumber.substring( 0, 2 ) + "."
					+ documentNumber.substring( 2, 5 ) + "."
					+ documentNumber.substring( 5, 8 ) + "/"
					+ documentNumber.substring( 8, 12 ) + "-"
					+ documentNumber.substring( 12, 14 );
		}
	}
}
