package com.contaazul.auto.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.testng.TestNGException;

import com.contaazul.auto.TestSession;

public class DateFormatUtil {

	public static final int SHORTDATE = 0;
	public static final int FULLDATE = 1;
	public static final int FULLYQUALIFIEDDATE = 2;
	public static final int SHORTTIME = 4;
	public static final int FULLTIME = 5;
	public static final int DATETIME = 7;
	public static final int FULLDATETIME = 8;
	private static final HashMap<String, Integer> DATE_CALC_KEYWORDS = new HashMap<String, Integer>();
	static {
		DATE_CALC_KEYWORDS.put( "AGORA", Calendar.MINUTE );
		DATE_CALC_KEYWORDS.put( "HOJE", Calendar.DATE );
	}

	public static String format(String date, int pattern) {
		if (date.length() > 0) {
			Date dt = convertToDate( date );
			return format( dt, pattern );
		} else
			return "";
	}

	public static String format(Date date, int pattern) {
		// Converte uma Date para o formato definido pelo parâmetro pattern
		// Retorna a String com a data formatado no locale da sessão.
		SimpleDateFormat dtFormat = new SimpleDateFormat(
				getFormat( pattern ),
				new Locale( TestSession.getSession().getLocale().getLocaleCode() ) );
		return dtFormat.format( date );
	}

	public static Date convertToDate(String date) {
		// Converte uma String no formato : "hoje+1", "agora-2" etc. em uma Date
		Calendar c = Calendar.getInstance();
		String dt = date.toUpperCase().replace( " ", "" ).trim();
		// É possível usar keywords AGORA e HOJE
		// soma-se minutos a AGORA e dias ao HOJE
		String unit = "";
		int qtty = 0;
		// para todas as keywords
		for (Iterator<String> i = DATE_CALC_KEYWORDS.keySet().iterator(); i
				.hasNext();) {
			unit = i.next();
			// se o campo contém alguma das palavras chave ("HOJE", "AGORA",
			// etc.)
			if (dt.contains( unit ))
				// interrompe loop
				// determinada a unidade com que que soma
				break;
		}
		// se encontrou alguma palavra chave
		if (unit.length() > 0) {
			// determina a quantidade de unidades que soma (pode ser negativo)
			String unidades;// modulo
			dt = dt.replace( "<", "" ).replace( ">", "" );
			String[] components = dt.split( unit );
			if (components.length > 1)
				unidades = components[1];
			else
				unidades = "0";
			qtty = evalAdditions( unidades );
			c.add( DATE_CALC_KEYWORDS.get( unit ), qtty );
		}
		// retorna a Date acrescida/diminuida
		return c.getTime();
	}

	private static Integer evalAdditions(String expression) {
		if (expression.trim().matches( "^$" ))
			return 0;
		int valor = 0;
		String[] factors = expression.split( "\\+" );
		if (factors[0].length() == 0)
			// começava com um sinal de +
			factors[0] = "0";
		for (int i = 0; i < factors.length; i++)
			valor = valor + evalSubtractions( factors[i] );
		return valor;
	}

	private static int evalSubtractions(String expression) {
		if (expression == "")
			return 0;
		if (expression.indexOf( "-" ) == -1)
			return Integer.parseInt( expression );

		String[] factors = expression.split( "\\-" );
		int valor = (factors[0].length() == 0) ? 0 : Integer.parseInt( factors[0] );
		for (int i = 1; i < factors.length; i++)
			valor = valor - Integer.parseInt( factors[i] );
		return valor;
	}

	protected static String getFormat(int pattern) {
		// "Tabela" privada das possíveis formatações para todos os locales
		// suportados
		// Tem que ser atualizada manualmente quando um novo formato ou novo
		// locale passa a ser necessário
		String loc = TestSession.getSession().getLocale().getLocaleCode();
		if (loc.matches( "Pt_BR" )) {
			switch (pattern) {
			case SHORTDATE:
				return "dd/MM/yy";
			case FULLDATE:
				return "dd/MM/yyyy";
			case FULLYQUALIFIEDDATE:
				return "EEEE, dd de MMMM de yyyy";
			case SHORTTIME:
				return "H:mm";
			case FULLTIME:
				return "HH:mm z";
			case DATETIME:
				return "dd/MM/yyyy HH:mm";
			case FULLDATETIME:
				return "EEEE, dd de MMMM de yyyy �s HH:mm z";
			default:
				return "DD/MM/yyyy";
			}
		} else if (loc.matches( "En_US" )) {
			switch (pattern) {
			case SHORTDATE:
				return "MM/dd/yy";
			case FULLDATE:
				return "MM/dd/yyyy";
			case FULLYQUALIFIEDDATE:
				return "EEE, MMM dd, yyyy";
			case SHORTTIME:
				return "h:mm a";
			case FULLTIME:
				return "hh:mm a z";
			case DATETIME:
				return "MM/dd/yyyy hh:mm a z";
			case FULLDATETIME:
				return "EEEE, MMMM dd, yyyy at hh:mm a z";
			default:
				return "MM/dd/yyyy";
			}
		} else if (loc.matches( "En_UK" )) {
			switch (pattern) {
			case SHORTDATE:
				return "dd/MM/yy";
			case FULLDATE:
				return "dd/MM/yyyy";
			case FULLYQUALIFIEDDATE:
				return "EEE, MMM dd, yyyy";
			case SHORTTIME:
				return "H:mm";
			case FULLTIME:
				return "HH:mm z";
			case DATETIME:
				return "dd/MM/yyyy HH:mm z";
			case FULLDATETIME:
				return "EEEE, MMMM dd, yyyy at HH:mm z";
			default:
				return "MM/dd/yyyy";
			}
		} else {
			throw new TestNGException(
					"Não pôde formatar data, o locale informado é inválido. locale: \""
							+ loc + "\"." );
		}
	}
}
