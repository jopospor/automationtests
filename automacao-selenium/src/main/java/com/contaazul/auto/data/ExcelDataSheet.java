package com.contaazul.auto.data;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.Reporter;
import org.testng.TestNGException;

public class ExcelDataSheet extends DataSheet {

	@Override
	public synchronized void importSheet(String filePathRelative,
			String sourceSheetName) throws Exception {

		HSSFWorkbook workbook;
		HSSFSheet sheet = null;

		int cols;
		try {
			workbook = loadWorkbook( filePathRelative );
			sheet = workbook.getSheet( sourceSheetName );
			cols = sheet.getRow( 0 ).getLastCellNum();
		} catch (FileNotFoundException e) {
			String m = "DataSheet Erro: Não pôde carregar a planilha Excel. Arquivo não encontrado. "
					+ (filePathRelative.isEmpty() || filePathRelative == null ? this.filePath
							: filePathRelative);
			Reporter.log( m, true );
			throw new TestNGException( m, e );
		} catch (IOException e) {
			String m = "DataSheet Erro: Não pôde carregar a planilha Excel. Erro no acesso ao arquivo.";
			Reporter.log( m, true );
			throw new TestNGException( m, e );
		} catch (NullPointerException e) {
			String m = "DataSheet Erro: Não pôde carregar a planilha Excel. Planilha (sheet) não encontrada no arquivo Excel? Nome da planilha: "
					+ sourceSheetName;
			Reporter.log( m, true );
			throw new TestNGException( m, e );
		}

		try {
			headers = new String[cols];
			// "introduzindo" um numero de linha para facilitar relatórios
			// headers[0] = "Row";
			for (int i = 0; i < cols; i++)
				headers[i] = sheet.getRow( 0 ).getCell( i ).getStringCellValue();
			Iterator<Row> rowIterator = sheet.rowIterator();
			Row r;
			Cell celula;
			HashMap<String, String> hash;
			rowIterator.next();// pula o header
			while (rowIterator.hasNext()) {
				hash = new HashMap<String, String>( headers.length );
				r = rowIterator.next();
				Iterator<Cell> cellIterator = r.cellIterator();
				int j = 0;
				while (cellIterator.hasNext()) {
					celula = cellIterator.next();
					try {
						hash.put( headers[j], celula.getStringCellValue() );
					} catch (IllegalStateException e) {
						hash.put( headers[j], String.valueOf( Math.round( celula
								.getNumericCellValue() ) ) );
					}
					j++;
				}
				this.addRow( hash );
			}

		} catch (Exception e) {
			String m = "Erro ao ler dados da planilha Excel. Verifique células em branco, com valores numéricos, ou colunas fora do alinhamento padrão. Mensagem da Exceção: "
					+ e.getMessage();
			Reporter.log( m, true );
			throw new TestNGException( m, e );
		}
	}

	private HSSFWorkbook loadWorkbook(String filePathRelative)
			throws FileNotFoundException, IOException {
		filePathRelative = (filePathRelative.isEmpty() || filePathRelative == null) ? this.filePath
				: filePathRelative;
		return new HSSFWorkbook( new FileInputStream(
				System.getProperty( "user.dir" ) + filePathRelative ) );
	}

	@Override
	public void exportSheet(String filePathRelative) {
		// TODO Auto-generated method stub
		// ainda não foi necessário
	}
}
