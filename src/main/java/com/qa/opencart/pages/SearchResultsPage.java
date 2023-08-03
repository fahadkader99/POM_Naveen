package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class SearchResultsPage {
	private WebDriver driver;
	private ElementUtils elementUtils;
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		elementUtils = new ElementUtils(driver);
	}
	
	private By productResult = By.cssSelector("div.product-layout");
	
	
	public int getSearchResultsCount() {
		return elementUtils.waitForElementsVisible(productResult, AppConstants.MEDIUM_TIME_OUT).size();
	}
	
	
	public ProductInfoPage selectProduct(String productName) {
		elementUtils.waitAndClick(By.linkText(productName), AppConstants.MEDIUM_TIME_OUT);
		return new ProductInfoPage(driver);
	}
	
	
	
	
	
	
	
	
}
