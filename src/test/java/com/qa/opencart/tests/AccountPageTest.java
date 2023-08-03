package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;


public class AccountPageTest extends BaseTest{
	
	/**
	 * This method is only applicable for this test class only, since this test required to login to the app first
	 * AAA - arrange, act, assert > every test must have a compolsery assert.
	 */
	
	@BeforeClass
	public void accPageSetUp() {
		accountPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	
	@Test(priority = 1)
	public void accPageTitleTest() {
		String actAccPAgeTitle =  accountPage.getAccTitle();
		Assert.assertEquals(actAccPAgeTitle, AppConstants.ACCOUNT_PAGE_TITLE); 
		
	}
	
	
	@Test(priority = 2)
	public void logoutLinkExist() {
		 Assert.assertTrue(accountPage.isLogoutLinkExist());
	}
	
	
	@Test(priority = 3)
	public void accPageHeaderCountTest() {
		int actAccPageHeaderCount = accountPage.getAccountPageHeaderCount();
		System.out.println("Actiual Acc page Header count = " + actAccPageHeaderCount);
		Assert.assertEquals(actAccPageHeaderCount, AppConstants.ACCOUNT_PAGE_HEADER_COUNT); 																			// based on page structure > usually headers remain fixed 
	}
	
	
	@Test(priority = 4)
	public void accPageHeaderTest() {
		List<String> accPAgeHeadersList =  accountPage.getAccountsPageHeader();
		Assert.assertEquals(accPAgeHeadersList, AppConstants.EXPECTED_ACC_PAGE_HEADER_LIST);
		
	}
	
	
	
	@DataProvider
	public Object[][] getSearchKey() {
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2},
			{"canon", 1}
		};
	}
	
	
	
	@Test(priority = 5, dataProvider = "getSearchKey")
	public void doSearchTest(String searchKey, int productCount) {
		searchResultPage = accountPage.doSearch(searchKey);
		int actResultCount = searchResultPage.getSearchResultsCount();
		Assert.assertEquals(actResultCount, productCount);
	}

}
