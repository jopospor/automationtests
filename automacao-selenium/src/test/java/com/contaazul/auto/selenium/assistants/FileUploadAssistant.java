package com.contaazul.auto.selenium.assistants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import com.contaazul.auto.config.SeleniumProperties;
import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.WebPage;

public class FileUploadAssistant extends WebPage {

	public FileUploadAssistant(RemoteWebDriver driver) {
		super( driver );
	}

	public void remoteUploadFile(WebElement fileUploadField, String localFilePath) throws IOException {
		File localTestDataFile = new File( System.getProperty( "user.dir" ) + localFilePath );
		File tempFile = createTempRemoteFile( localTestDataFile.getName() );
		String tempAbsPath = tempFile.getAbsolutePath();
		streamLocalFileToRemote( localTestDataFile, tempFile );
		setFileUploadField( fileUploadField, tempAbsPath );
	}

	private void streamLocalFileToRemote(File localTestDataFile, File tempFile) throws IOException {
		InputStream in = new FileInputStream( localTestDataFile );
		OutputStream out = new FileOutputStream( tempFile );
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read( buf )) > 0)
			out.write( buf, 0, len );
		in.close();
		out.close();
		tempFile.deleteOnExit();
	}

	private File createTempRemoteFile(String fileName) throws IOException {
		String tempFileName = String.valueOf( System.currentTimeMillis() );
		return File.createTempFile( tempFileName, fileName );
	}

	private void setFileUploadField(WebElement fileUploadField, String tempAbsPath) {

		if (SeleniumSession.getSession().getProperties().getProperty( SeleniumProperties.GRID_SERVER_BASE_URL )
				.toLowerCase().matches( "localhost" )
				|| SeleniumSession.getSession().getProperties().getProperty( SeleniumProperties.GRID_SERVER_BASE_URL )
						.toLowerCase().matches( "127.0.0.1" )) {
			fileUploadField.sendKeys( tempAbsPath );
		} else {
			RemoteWebElement remoteFileField = (RemoteWebElement) driver.findElement( By.id( fileUploadField
					.getAttribute( "id" ) ) );
			((RemoteWebElement) remoteFileField).setFileDetector( new LocalFileDetector() );
			remoteFileField.sendKeys( tempAbsPath );
		}
	}

}
