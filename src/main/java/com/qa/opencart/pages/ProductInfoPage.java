package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class ProductInfoPage {
	/*
	 * No assertion is the page class, so all the method should return something so that I can use these methods to do assertions on Test class
	 * Num of pages should be 1 to 1 mapping with the application pages
	 */
	
	private WebDriver driver;
	private ElementUtils elementUtils;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		elementUtils = new ElementUtils(driver);
	}
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productQuantity = By.name("quantity");
	private By addToCart = By.id("button-cart");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	
	private Map<String, String> productMap;
 	
	
	public String getProductHeaderValue() {
		return elementUtils.doElementGetText(productHeader);
	}
	
	public int getProductImgCount() {
		int actProductImgCount = elementUtils.waitForElementsVisible(productImages, AppConstants.MEDIUM_TIME_OUT).size();
		System.out.println("Total product images for : " + getProductHeaderValue() + " ---> " + actProductImgCount);
		return actProductImgCount;
	}
	
	/**
	 * This will return the product meta details
	 * @return
	 */
	private void getProductMetaData() {
		List<WebElement> metaList = elementUtils.waitForElementsVisible(productMetaData, AppConstants.MEDIUM_TIME_OUT);
		//Map<String,  String> metaMap = new HashMap<String, String>();
		for(WebElement e: metaList) {
			String metaText = e.getText();
			String key = metaText.split(":")[0].trim();
			String value = metaText.split(":")[1].trim();
			productMap.put(key, value);
		}
		//return productMap;
	}
	
	
	/**
	 * This will return the product price details
	 * @return
	 */
	private void getProductPriceData() {
		List<WebElement> priceList = elementUtils.waitForElementsVisible(productPriceData, AppConstants.MEDIUM_TIME_OUT);
		//Map<String,  String> priceMap = new HashMap<String, String>();
		
		String actPrice =  priceList.get(0).getText();
		String exTax = priceList.get(1).getText().split(":")[0].trim();
		String exTaxValue = priceList.get(1).getText().split(":")[1].trim();
		
		productMap.put("price", actPrice);
		productMap.put(exTax, exTaxValue);
		
		//return priceMap;

	}
	
	
	
	public Map<String,  String> getProductData() {
		//productMap = new LinkedHashMap <String, String>();
		productMap = new TreeMap<String, String>();
		productMap.put("productHeader", getProductHeaderValue());
		productMap.put("productImages", String.valueOf(getProductImgCount())); 
		getProductMetaData();
		getProductPriceData();
		
		return productMap;
	}
	
	
	
	
	
	
	
	

}
