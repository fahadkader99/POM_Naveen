package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class LoginPage  {
	
	private WebDriver driver;
	private ElementUtils elementUtils;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtils = new ElementUtils(driver);
	}
	
	/*
	 * Page class / Page library usually contains the followings:::
	 * 
	 * - maintain by PRIVATE locator here / page locator / page elements
	 * - public constructor - to get the driver
	 * - public page actions methods
	 * - All the assertion will go on test class & page method will always return so that test class can do the assertions
	 */
	
	private By email = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerPage = By.linkText("Register");
	
	
	
	public String getLoginPageTitle() {
		String title =  elementUtils.waitForTitleContains(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
		System.out.println("Login page title: " + title);
		return title;
	}
	
	public String getLoginPageURL() {
		String url = elementUtils.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION,  AppConstants.SHORT_TIME_OUT);		//driver.getCurrentUrl();
		System.out.println("Login page URL: " + url);
		return url;
	}
	
	public boolean isForgotPwdLisnkExist() {
		return elementUtils.waitForElementVisible(forgotPwdLink, AppConstants.SHORT_TIME_OUT).isDisplayed();
	}
	
	public AccountPage doLogin(String userName, String pwd) {
		System.out.println("App credentials: " + userName + " : " + pwd);
		
		elementUtils.waitForElementVisible(email, AppConstants.SHORT_TIME_OUT).sendKeys(userName);
		elementUtils.doSendKeys(password, pwd);
		elementUtils.doClick(loginBtn);
		
		//return elementUtils.waitForTitleContains(AppConstants.ACCOUNT_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);														// if the title that returns & match with expectation then test pass in test case			
		
		/*
		 * Page chaining model > 2 pages are connected::
		 * this is page chaining concept > right after clicking Login > this method is responsible for returning next page objet
		 */
		return new AccountPage(driver);
		
		
	}
	
	
	public RegisterPage navigateToRegisterPage() {
		elementUtils.waitForElementVisible(registerPage, AppConstants.SHORT_TIME_OUT).click();
		return new RegisterPage(driver);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
