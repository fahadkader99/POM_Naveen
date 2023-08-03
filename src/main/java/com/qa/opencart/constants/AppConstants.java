package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	// ************************** default URL ******************* //
	
	public static final String ORANGE_HRM = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public static final String HEROKU_APP_ALERTS = "https://the-internet.herokuapp.com/javascript_alerts";
	public static final String CRM_PRO = "https://classic.crmpro.com/";
	public static final String NAVEEN_REGISTRATION = "https://naveenautomationlabs.com/opencart/index.php?route=account/register";
	public static final String NAVEEN_ACC_LOGIN = "https://naveenautomationlabs.com/opencart/index.php?route=account/login";
	
	
	// **********************************************
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_URL_FRACTION = "route=account/login";
	public static final String ACCOUNT_PAGE_URL_FRACTION = "route=account/account";
	public static final String ACCOUNT_PAGE_TITLE = "My Account";
	public static final int ACCOUNT_PAGE_HEADER_COUNT = 4;
	
	
	public static final List<String> EXPECTED_ACC_PAGE_HEADER_LIST = Arrays.asList("My Account", "My Orders", "My Affiliate Account", "Newsletter"); 
	
	public static final String USER_REGISTER_SUCCESS_MSG = "Your Account Has Been Created!";
	
	
	// ************************** Timeout values ******************* //
	
	public static final int SHORT_TIME_OUT = 5;
	public static final int MEDIUM_TIME_OUT = 10;
	public static final int LONG_TIME_OUT = 15;
	
	// ************************** Excel values ******************* //
	
	public static final String REGISTER_SHEET = "register";
}
