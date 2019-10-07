package com.contaazul.auto.selenium.vendas.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.contaazul.auto.selenium.WebPage;

public class RegisterProductPage extends WebPage {

	public RegisterProductPage(RemoteWebDriver driver) {
		super( driver );
	}

	@FindBy(className = "notValidated", id = "formProduto_produto_descricao")
	protected WebElement productName;

	@FindBy(className = "theme-hidefields-labe")
	protected WebElement showMoreOptions;

	@FindBy(className = "label")
	protected WebElement showProviderOptions;

	@FindBy(className = "save", id = "formProduto_0")
	protected WebElement buttonSave;

	@FindBy(className = "theme-button-big-gray", id = "formProduto_2")
	protected WebElement buttonSaveAndAddNew;

	@FindBy(className = "toggleLetter")
	protected WebElement returnList;

	@FindBy(id = "codProduto")
	protected WebElement idProduct;

	@FindBy(id = "produtoCategoria")
	protected WebElement categoryProduct;

	@FindBy(id = "formProduto_produto_vlVenda")
	protected WebElement valueSaleProduct;

	@FindBy(id = "formProduto_produto_vlCusto")
	protected WebElement valueCostProduct;

	@FindBy(id = "avaiable-quantity-field-product-form")
	protected WebElement quantityAvaiableProduct;

	@FindBy(id = "fornecedor_0_")
	protected WebElement idProvider;

}
