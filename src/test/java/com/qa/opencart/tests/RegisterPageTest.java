package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtils;

public class RegisterPageTest  extends BaseTest{
	
	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	
	
	public String getRandomEmailId() {
		return "opencart_" + System.currentTimeMillis() +"@gamil.com";														// this method will give time with mill sec > which is always unique
	}
	
	
	@DataProvider
	public Object[][] getUserRegisterData(){
		return new Object[][] {
			{"Fahad", "Kader", "1234567890", "test123", "yes"},
			{"ayesha", "nahar","223344556" , "abc22", "no"},
			{"esrat", "jahan", "343434343", "bbc23", "yes"}
		};
	}
	
	
	@DataProvider
	public Object[][] getUserRegSheetData(){
		return ExcelUtils.getTestData(AppConstants.REGISTER_SHEET);
	}
	
	
	
	
	@Test(dataProvider = "getUserRegSheetData")																									// DataProvider is repetative test so make the method is such a way so that it can support repetative test > should not break the flow 
	public void userRegisterTest(String firstName, String lastName, String phone, String pass, String subscribe) {
		Assert.assertTrue(registerPage.registerUser(firstName, lastName, getRandomEmailId(), phone, pass, subscribe));							// since the method will return boolean
	}
	
	
	

}
