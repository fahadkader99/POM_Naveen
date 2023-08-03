package com.qa.opencart.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ElementUtils;

public class DemoTest extends BaseTest{
	
	ElementUtils elementUtils;
	
	@BeforeMethod
	public void demoSetUp() {
		driver.get("https://classic.crmpro.com/");
		elementUtils = new ElementUtils(driver);
	}
	
	
	
	
	@Test
	public void test1() {

		elementUtils.doSendKeys(By.name("username"), "testautomation");
		elementUtils.doSendKeys(By.name("password"), "testautomation");
		elementUtils.doClick(By.xpath("//input[@value='Login']"));
	}

}
