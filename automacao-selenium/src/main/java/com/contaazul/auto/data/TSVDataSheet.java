package com.contaazul.auto.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.apache.tika.io.IOUtils;
import org.testng.Assert;
import org.testng.Reporter;

public class TSVDataSheet extends DataSheet {

	@Override
	public synchronized void importSheet(String filePathRelative,
			String sourceSheetName) {

		BufferedReader readbuffer = null;
		String strRead;

		try {
			readbuffer = new BufferedReader( new FileReader(
					System.getProperty( "user.dir" )
							+ (filePathRelative.isEmpty()
									|| filePathRelative == null ? this.filePath
									: filePathRelative) ) );
			headers = readbuffer.readLine().split( "\t" );
			while ((strRead = readbuffer.readLine()) != null) {
				String values[] = strRead.split( "\t" );
				HashMap<String, String> item = new HashMap<String, String>(
						headers.length );
				for (int i = 0; i < headers.length; i++) {
					item.put( headers[i], values[i] );
				}
				this.addRow( item );
			}
		} catch (FileNotFoundException e) {
			String m = "DataSheet Erro: Não pôde carregar o arquivo de texto. Arquivo não encontrado:"
					+ System.getProperty( "user.dir" ) + filePathRelative;
			Reporter.log( m, true );
			Assert.fail( m, e );
			// aborta <test>
		} catch (IOException e) {
			String m = "DataSheet Erro: Não pôde carregar o arquivo de texto. Erro no acesso ao arquivo";
			Reporter.log( m, true );
			Assert.fail( m, e );
			// aborta <test>
		} catch (Exception e) {
			String m = "DataSheet Erro: Não pôde carregar o arquivo de texto. Verifique a estrutura do arquivo. Message:"
					+ e.getMessage();
			Reporter.log( m, true );
			Assert.fail( m, e );
			// aborta <test>
		} finally {
			IOUtils.closeQuietly( readbuffer );
		}
	}

	@Override
	public void exportSheet(String filePath) {
		// TODO Auto-generated method stub
		// ainda não foi necessário
	}
}
