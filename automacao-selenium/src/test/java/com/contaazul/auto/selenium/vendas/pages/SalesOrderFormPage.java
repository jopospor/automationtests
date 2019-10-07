package com.contaazul.auto.selenium.vendas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class SalesOrderFormPage extends WebPage {

	public SalesOrderFormPage(WebDriver driver) {
		super( (RemoteWebDriver) driver );
	}

	private static final String ID_ELEMENT_CLIENT = "cliente";

	private static final String ID_ELEMENT_ITEM_0 = "Produto_0_";

	@FindBy(id = ID_ELEMENT_CLIENT)
	private WebElement client;

	@FindBy(id = ID_ELEMENT_ITEM_0)
	private WebElement item0;

	@FindBy(id = "item_0_itemQty")
	private WebElement itemQuantity0;

	@FindBy(id = "item_0_vlUnitario")
	private WebElement itemValue0;

	@FindBy(id = "formPedidoVenda_2")
	private WebElement buttonSave;

	public WebElement getClient() {
		return client;
	}

	public String getClientElementId() {
		return ID_ELEMENT_CLIENT;
	}

	public WebElement getItem0() {
		return item0;
	}

	public String getItem0ElementId() {
		return ID_ELEMENT_ITEM_0;
	}

	public void setItemQuantity0(String itemQuantity) {
		this.itemQuantity0.clear();
		this.itemQuantity0.sendKeys( itemQuantity );
	}

	public WebElement getItemQuantity0() {
		return itemQuantity0;
	}

	public WebElement getButtonSave() {
		return buttonSave;
	}

	public String getClientValue() {
		return getAssistants().getAutoCompleteAssistant( driver ).getText( client );
	}

	public String getItemValue() {
		return getAssistants().getAutoCompleteAssistant( driver ).getText( item0 );
	}

	public void setItem1Value(String value) {
		this.itemValue0.sendKeys( value );

	}
}
