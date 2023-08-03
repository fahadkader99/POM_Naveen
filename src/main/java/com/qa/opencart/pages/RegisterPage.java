package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtils elementUtils;
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		elementUtils = new ElementUtils(driver);
	}
	
	
	private By email = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerPage = By.linkText("Register");
	private By yesDot = By.xpath("//label[normalize-space()='Yes']");
	private By noDot = By.xpath("//label[normalize-space()='Yes']");
	private By agreeCheckbox = By.xpath("//input[@type='checkbox']");
	private By firstName = By.name("firstname");
	private By lastName = By.name("lastname");
	private By phone = By.name("telephone");
	private By pass = By.name("password");
	private By continueBtn = By.xpath("//input[@value='Continue']");
	private By logout = By.name("Logout");
	private By successMessage = By.xpath("");
	
	
	
	public boolean registerUser(String firstName, String lastName, String email, String phone, String pass, String subscribe) {
		elementUtils.waitForElementVisible(this.firstName, AppConstants.MEDIUM_TIME_OUT).sendKeys(firstName);
		elementUtils.waitForElementVisible(this.lastName, AppConstants.MEDIUM_TIME_OUT).sendKeys(lastName);
		elementUtils.waitForElementVisible(this.email, AppConstants.MEDIUM_TIME_OUT).sendKeys(email);
		elementUtils.waitForElementVisible(this.phone, AppConstants.MEDIUM_TIME_OUT).sendKeys(phone);
		elementUtils.waitForElementVisible(this.pass, AppConstants.MEDIUM_TIME_OUT).sendKeys(pass);
		if (subscribe.equalsIgnoreCase("yes")) {
			elementUtils.doClick(yesDot);
		}else {
			elementUtils.doClick(noDot);
		}
		elementUtils.doClick(agreeCheckbox);
		elementUtils.doClick(continueBtn);
		String successMsg = elementUtils.waitForElementVisible(this.successMessage, AppConstants.LONG_TIME_OUT).getText();
		System.out.println(successMsg);
		if (successMsg.contains(AppConstants.USER_REGISTER_SUCCESS_MSG)) {
			elementUtils.doClick(this.logout);
			elementUtils.doClick(this.registerPage);
			return true;
		}
		return false;


	}

}
