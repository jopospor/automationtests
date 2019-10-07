package com.contaazul.auto.report;

import java.util.Comparator;

public class TestResultComparator<S> implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		if (o1.toLowerCase().contains( "linha" ) && o2.toLowerCase().contains( "linha" ))
			return Integer.valueOf( o1.substring( o1.indexOf( " " ) + 1, o1.indexOf( "\"", o1.indexOf( " " ) + 1 ) ) )
					.compareTo(
							Integer.valueOf( o2.substring( o2.indexOf( " " ) + 1,
									o2.indexOf( "\"", o2.indexOf( " " ) + 1 ) ) ) );
		else
			return o1.compareTo( o2 );
	}

}
