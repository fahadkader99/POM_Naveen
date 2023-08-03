package com.qa.opencart.base;

import java.util.Properties;

import javax.naming.directory.SearchResult;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	
	protected WebDriver driver;
	public Properties prop;
	protected LoginPage loginPage;
	protected AccountPage accountPage;
	protected SearchResultsPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	DriverFactory factory;
	
	protected SoftAssert softAssert;																			// only the child of BasTest class will have the access > protected
	
	
	
	@BeforeTest
	public void setUp() {
		factory = new DriverFactory();
		prop = factory.initProperty();
		
		driver = factory.initializeDriver(prop);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		// Initializing class objects at the BaseTest
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
		
		
	}
	
	
	
	
	
	
	
	
	@AfterTest
	public void tearDown() throws InterruptedException {
		
		accountPage.doLogOut();
		Thread.sleep(1000);
		driver.close();
		//driver.quit();
	}

}
