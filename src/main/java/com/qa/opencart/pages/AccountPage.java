package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class AccountPage {
	
	private WebDriver driver;
	private ElementUtils elementUtils;
	
	public AccountPage(WebDriver driver) {
		this.driver = driver;
		elementUtils = new ElementUtils(driver);
	}
	

	private By logOutLink = By.partialLinkText("Logout");
	private By accountHeader = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	private By myAccBtn = By.xpath("//span[contains(text(),'My Account')]");
	private By logoutBtn1 = By.xpath("//a[contains(text(),'Logout')]");
	private By logoutBtn2 = By.xpath("(//a[contains(text(),'Logout')])[2]");

	
	
	
	public String getAccTitle() {
		return elementUtils.waitForTitleContains(AppConstants.ACCOUNT_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
	}
	
	public boolean isLogoutLinkExist() {
		return elementUtils.waitForElementPresence(logOutLink, AppConstants.MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountsPageHeader() {
		List<WebElement> headerList =  elementUtils.waitForElementsVisible(accountHeader, AppConstants.MEDIUM_TIME_OUT);
		List<String> headersValueList = new ArrayList<String>();
		for(WebElement e: headerList) {
			String header = e.getText();
			headersValueList.add(header);
		}
		System.out.println("Actual headers are ==> " + headersValueList);
		return headersValueList;		
		
	}
	
	public int getAccountPageHeaderCount(){
		return elementUtils.waitForElementsVisible(accountHeader, AppConstants.MEDIUM_TIME_OUT).size();
	}
	
	public SearchResultsPage doSearch(String searchKey) {
		WebElement searchField =  elementUtils.waitForElementVisible(search, AppConstants.MEDIUM_TIME_OUT);
		searchField.clear();
		searchField.sendKeys(searchKey);
		elementUtils.doClick(searchIcon);
		return new SearchResultsPage(driver);			// TDD
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void doLogOut() {
		
		try {
			if (elementUtils.checkElementIsDisplayed(myAccBtn)) {
				elementUtils.doActionsClick(myAccBtn);
				elementUtils.doActionsClick(logoutBtn1);
			}
			else if (elementUtils.checkElementIsDisplayed(logoutBtn2)) {
				elementUtils.doActionsClick(logoutBtn2);
			}
		} catch (Exception e) {
			throw new SkipException("Unable to locate Logout button / unable to logout from the app");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
