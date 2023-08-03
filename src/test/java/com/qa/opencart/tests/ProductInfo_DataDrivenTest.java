package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfo_DataDrivenTest extends BaseTest{
	
	
	@BeforeClass																		// this will run after BeforeTest method in BaseTest
	public void productInfoSetup() {
		accountPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));		
	}
	
	@DataProvider
	public Object[][] productTestData(){
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"macbook", "MacBook Air"},
			{"iMac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"}
		};
	}
	
	
	/*
	 * This is a zic zac patter of method chaining
	 */
	@Test(priority = 0, dataProvider = "productTestData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultPage = accountPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		String actProductInfoHeader = productInfoPage.getProductHeaderValue();
		Assert.assertEquals(actProductInfoHeader, productName);
	}
	
	/*
	 * 
	 * Maintain the dataProvider in separate class or same class && also modern approach > use less excel & keep data provider data in test class > it is easy to manage if the data are less then why use excel un-necessary? 
	 * Now a days we can maintain the data in the script & it has less dependency on excel library
	 * DataProvider is helpful for repetetive search field validation
	 * 
	 * If We have repetative test & need to pass lots of data > DataProvider is excellent choice. 
	 */
	
	@DataProvider
	public Object[][] productData(){
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"macbook", "MacBook Air", 4},
			{"iMac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"samsung", "Samsung Galaxy Tab 10.1", 7}
		};
	}
	
	
	@Test(priority = 1, dataProvider = "productData")
	public void productImgCountTest(String searchKey, String productName, int expProductImgCount) {
		searchResultPage = accountPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		int actProdImgCount = productInfoPage.getProductImgCount();
		Assert.assertEquals(actProdImgCount, expProductImgCount);
	}
	
	
	//AAA
	@Test
	public void productInfoTest() {
		searchResultPage = accountPage.doSearch("macbook");
		productInfoPage = searchResultPage.selectProduct("MacBook Pro");
		Map<String, String> productActualData = productInfoPage.getProductData();
		System.out.println(productActualData);
		
		// It is ok to have multiple assertions when needed.
		softAssert.assertEquals(productActualData.get("Brand"), "Apple");
		softAssert.assertEquals(productActualData.get("productheader"), "MacBook Pro");
		softAssert.assertEquals(productActualData.get("price"), "$2,000.00");
		softAssert.assertAll(); 																								// This line will give the assertion results at the end of the run
	}
	
	

}
