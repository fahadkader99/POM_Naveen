package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * this is use to initialize the driver
	 * Creating thread local copy of local driver > for better performance in multi-threaded env
	 * We are distribution driver to each thread more efficiency & we are optimizing driver factory in multi threading env
	 * So all the thread in xml file will get individual copy of driver = so more efficient + also good when running 300-500 tc + also improves reporting with ExtentReport
	 * Also thread local helps to take better ScreenShot while running couple hundreds | thousands test
	 * Also perfect for Extent reporting properly
	 * 
	 */
	public WebDriver initializeDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		System.out.println("\nBrowser name: " + browserName);
		
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		
		switch (browserName.toLowerCase()) {
		case "chrome":
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			threadLocalDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));											// this is for creating & maintaining better chrome driver in thread running condition
			break;

		case "firefox":
			//driver = new FirefoxDriver(optionsManager.getFireFoxOptions());
			threadLocalDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
			break;

		case "safari":
			driver = new SafariDriver();
			threadLocalDriver.set(new SafariDriver());
			break;

		case "edge":
			driver = new EdgeDriver(optionsManager.getEdgeOptions());
			threadLocalDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		default:
			System.out.println("Please pass right browser: " + browserName);
			break;
		}
		
		
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	
	public static WebDriver getDriver() {
		// returning the local thread driver
		return threadLocalDriver.get();																				
	}
	
	
	
	
	/*
	 * Old way of initializing driver
	 */
	/*
	public WebDriver initializeDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		System.out.println("\nBrowser name: " + browserName);
		
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		
		switch (browserName.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver(optionsManager.getChromeOptions());
			//threadLocalDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));											// this is for creating & maintaining better chrome driver in thread running condition
			break;

		case "firefox":
			driver = new FirefoxDriver(optionsManager.getFireFoxOptions());
			//threadLocalDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
			break;

		case "safari":
			driver = new SafariDriver();
			//threadLocalDriver.set(new SafariDriver());
			break;

		case "edge":
			driver = new EdgeDriver(optionsManager.getEdgeOptions());
			//threadLocalDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		default:
			System.out.println("Please pass right browser: " + browserName);
			break;
		}
				
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));
		return driver;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	*/

	
	
	/**
	 * not taking param, use to init properties
	 *
	 */
	public Properties initProperty() {

		/* 				-- Ways of running maven test --
		 * 
		 * project folder > cmd > mvn clean install -Denv="qa"
		 * project folder > cmd > mvn clean install -Denv="stage"
		 * 
		 * Eclipse > Run as > Maven Build > clean install -Denv="stage"
		 * 
		 * Sequence::: Maven -> pom.xml -> dependency -> plugins ->
		 * 				i. compiler plugins
		 * 				ii. surefire plugin (test.xml)
		 * 
		 * Jenkins::: Maven -> pom.xml -> dependency -> plugins ->
		 * 				i. compiler plugin
		 * 				ii. surefire plugin (test.xml)
		 */
		
		FileInputStream ip = null;
		Properties prop = new Properties();
		
		String envName = "qa"; //System.getProperty("env");
		System.out.println("\nSelected Environment : " + envName);
		
		try {
			if (envName == null) {
				System.out.println("No env is given. . . default env is QA");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");			
			}
			else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
					
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
					
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
					
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
					
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("Please provide correct env. . . " + envName);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	
	
	/**
	 * take screenshot
	 */
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshot/"+methodName+"_"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
