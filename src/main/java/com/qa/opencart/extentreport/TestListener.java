package com.qa.opencart.extentreport;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.qa.opencart.factory.DriverFactory;



public class TestListener implements ITestListener{
	
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	// Before starting all test
	public void onStart(ITestContext context) {
		System.out.println("On Start : " + context.getName());
	}
	
	// Runs before every test starts | runs multiple times = number of test methods

	public void onTestStart(ITestResult iTestResult) {
		String description = iTestResult.getMethod().getDescription();
		System.out.println("Test started : " + getTestMethodName(iTestResult) + " successfully.");
		
		if (description != null) {
			ReportTestManager.startTest(iTestResult.getMethod().getMethodName() + " ( " + description + " ) ", iTestResult.getInstance().getClass().getCanonicalName());
		}
		else if (iTestResult.getTestName() != null) {
			ReportTestManager.startTest(iTestResult.getTestName(), iTestResult.getInstance().getClass().getCanonicalName());
		}
		else {
			ReportTestManager.startTest(iTestResult.getMethod().getMethodName(), iTestResult.getInstance().getClass().getCanonicalName());
		}
		System.out.println("On Test Start : " + getTestMethodName(iTestResult) + " start");
		
	}


	public void onTestSuccess(ITestResult iTestResult) {
		ReportTestManager.getTest().log(Status.PASS, MarkupHelper.createLabel(getTestMethodName(iTestResult) + "  | PASSED | ", ExtentColor.GREEN));
	}


	public void onTestFailure(ITestResult iTestResult) {
		
		Object testClass = iTestResult.getInstance();
		WebDriver webDriver = DriverFactory.getDriver();		//DriverFactory.getDriver();           		//((DriverFactory) testClass).getDriver();
		
		/*
		 * Take Base64 ScreenShot + attach to report
		 */
		
		String base64ScreenShot = "data:image/png;base64," + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);										//  ScreenShot will be hold along with the html & html can be moved anywhere & it will have the screen shot, although html size will increase
				
		ReportTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel(getTestMethodName(iTestResult) + "   -X FAILED X-   ", ExtentColor.RED));
		ReportTestManager.getTest().log(Status.FAIL, iTestResult.getThrowable());
		ReportTestManager.getTest().fail("Details", MediaEntityBuilder.createScreenCaptureFromBase64String(base64ScreenShot).build());
		
		
	}


	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("On Test Skipped : " + getTestMethodName(iTestResult) + " skipped. ");
		ReportTestManager.getTest().log(Status.SKIP, MarkupHelper.createLabel(getTestMethodName(iTestResult) + " --> Test SKIPPED ", ExtentColor.ORANGE));
	}
	


	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed with success ratio : " + getTestMethodName(iTestResult));
		ReportTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel("Test failed with success ratio : " + getTestMethodName(iTestResult), ExtentColor.RED));

	}

	// after ending all test

	public void onFinish(ITestContext context) {
		System.out.println("On finish method: " + context.getName());
		ReportManager.getInstance().flush();
		
		try {
			Desktop.getDesktop().browse(new File(ReportManager.path).toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
