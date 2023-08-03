package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.extentreport.TestListener;

@Listeners(TestListener.class)


public class LoginPageTest extends BaseTest{
	
	/*
	 * <> Test class should never contain any webDriver method or I am doing it wrong -> By Selenium creator.
	 * <> All webelEment actions & driver methods goes on page class && assertions goes on Test class				[design principle]
	 * <> Test will call the page class & page class will do all the actions & test class do the assertions
	 * 
	 * 
	 * - also target is to access the page class properties
	 * - note: we should have multiple test methods but each test should do specific test & don't do multiple complex tests under 1 test method only, have multiple test methods for multiple assertios
	 */
	
	
	@Test(priority = 1)
	public void loginPageTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);				
	}
	
	@Test(priority = 2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));				
	}
	
	@Test(priority = 3)
	public void isFOrgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLisnkExist());
	}
	
	@Test(priority = 4)
	public void loginTest() {
		accountPage =  loginPage.doLogin("janautomation@gmail.com", "Selenium@12345");
		Assert.assertEquals(accountPage.isLogoutLinkExist(), true);
	}
	
	
	
	
	
	
	
	/*
	 * //	Given an int n, return true if it is within 10 of 100 or 200. Note: Math.abs(num) computes the absolute value of a number.	
	 * 
	 * public boolean nearHundred(int n) {
  			return ((Math.abs(100 - n) <= 10) ||
    		(Math.abs(200 - n) <= 10));
		}
		 
		
	 */
	
	
	
	
	
	
	

}
