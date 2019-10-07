package com.contaazul.auto.selenium.assistants;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import com.contaazul.auto.selenium.SeleniumSession;
import com.contaazul.auto.selenium.WebPage;

public class ModalAssistant extends WebPage {

	@FindBy(id = "closeVideo")
	protected WebElement closeVideo;

	private int duration = 500;
	public static final int POPUP_VIDEO = 1000;
	public static final int POPUP_DEGUSTACAO_QUASE_ENCERRADA = 2000;
	public static final int POPUP_DEGUSTACAO_ENCERRADA = 3000;
	public static final int NEW_POPUP_DADOS_NOTA_FISCAL = 4000;
	public static final int POPUP_IMPORTACAO_CLIENTES = 5000;
	public static final int POPUP_BANNER_PROMOCIONAL = 6000;

	public ModalAssistant(RemoteWebDriver driver) {
		super( driver );
	}

	public void closeAllModalWindowsAndPopups() {
		closeIntroductionVideoPopUp();// introductionVideo
		closeAlmostBlockedUserPopUp();// almostBlockedUserWarningDialog
		closeBankslipDataFormPopUp();// newPopupManagerReplacement
		closeBlockedUserPopUp();
		closeImportCustomersPopUp();//
		closePopupPromotion();// Banner promocional
	}

	public void closePopUp(int popUpId) {
		switch (popUpId) {
		case POPUP_VIDEO:
			closeIntroductionVideoPopUp();
			break;
		case POPUP_DEGUSTACAO_QUASE_ENCERRADA:
			closeAlmostBlockedUserPopUp();
			break;
		case POPUP_DEGUSTACAO_ENCERRADA:
			closeBlockedUserPopUp();
			break;
		case NEW_POPUP_DADOS_NOTA_FISCAL:
			closeBankslipDataFormPopUp();
			break;
		case POPUP_IMPORTACAO_CLIENTES:
			closeImportCustomersPopUp();
			break;
		case POPUP_BANNER_PROMOCIONAL:
			closePopupPromotion();
			break;
		}
	}

	public void closeVideo() {
		closeVideo.click();
	}

	private void closeBlockedUserPopUp() {
		findAndCloseVisiblePopUps( "tastingBlockedUserWarningDialog" );
	}

	public void closeIntroductionVideoPopUp() {
		findAndCloseVisiblePopUps( "introductionVideo" );
	}

	public void closeAlmostBlockedUserPopUp() {
		findAndCloseVisiblePopUps( "almostBlockedUserWarningDialog" );
	}

	public void closeBankslipDataFormPopUp() {
		findAndCloseVisiblePopUps( "newPopupManagerReplacement" );
	}

	public void closeImportCustomersPopUp() {
		findAndCloseVisiblePopUps( "file-import-popup" );
	}

	public void closePopupPromotion() {
		javascript( "jQuery('.modal-promo-container').eq(0).attr('style', 'display: none')" );
	}

	private void findAndCloseVisiblePopUps(String popUpId) {
		JavascriptExecutor jay = (JavascriptExecutor) driver;
		for (int i = 0; i < duration; i += 250) {
			try {
				Thread.sleep( 250 );
			} catch (InterruptedException e) {
				Logger.getRootLogger().info( e.getMessage() );
			}
			Object popUpJSReference = jay.executeScript( "return document.getElementById('" + popUpId + "')" );
			if (popUpJSReference == null)
				break;
			else if (isPopUpVisible( popUpId )) {
				clickClosePopUp( popUpId );
				jay.executeScript( "return document.getElementById('" + popUpId + "').setStyle(\"display:none;\")" );
				break;
			}
		}
	}

	public boolean isPopUpVisible(String popUpId) {
		JavascriptExecutor jay = (JavascriptExecutor) driver;
		try {
			return (((String) jay.executeScript(
					"return document.getElementById('" + popUpId + "').getStyle('visibility')" ))
					.matches( "visible" )
			&& !((String) jay
					.executeScript( "return document.getElementById('" + popUpId + "').getStyle('display')" ))
					.matches( "none" ));
		} catch (Exception e) {
			return false;
		}
	}

	private void clickClosePopUp(String popUpId) {
		if (popUpId.matches( "almostBlockedUserWarningDialog" ) || popUpId.matches( "tastingBlockedUserWarningDialog" ))
			driver.findElement( By.xpath( "//*[contains(text(), '" + SeleniumSession
					.getSession()
					.getLocale()
					.translate(
							"#DESCONSIDERAR_AVISO_DEGUSTACAO" )
					+ "')]" ) ).click();
		else if (popUpId.matches( "newPopupManagerReplacement" ))
			// driver.findElement( By.xpath(
			// "//*[contains(text(), 'Enviar Dados')]" ) ).click();
			driver.findElement( By.xpath( "//button[contains(text(), '×')]" ) );
		else if (popUpId.matches( "file-import-popup" ))
			driver.findElement( By.xpath( "//button[@class='close']" ) ).click();
		else if (popUpId.matches( "introductionVideo" ))
			driver.findElementById( "skipVideo" ).click();

	}

	public void clickInCloseButton() {
		this.driver.findElementByCssSelector( ".modal.in .close" ).click();
	}

	public void waitAppear() {
		sleep( 1000 );
	}

	public void dismissAndWait() {
		this.closeAllModalWindowsAndPopups();
		sleep( 1000 );
	}

	public void confirmAlert() {
		try {
			sleep( FOR_A_LONG_TIME );
			driver.switchTo().alert().accept();
			sleep( FOR_A_LONG_TIME );
			driver.switchTo().defaultContent();
			sleep( FOR_A_LONG_TIME );
		} catch (UnhandledAlertException e) {
			Reporter.log( "Não conseguiu fechar um dialogue", 2, true );
			driver.switchTo().defaultContent();
		}

	}
}
