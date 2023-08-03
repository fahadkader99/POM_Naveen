package com.qa.opencart.extentreport;

import java.io.File;
import java.net.InetAddress;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ReportManager {
	
private static ExtentReports extent;
public static String path;

	public static ExtentReports getInstance() {															// singleton class > will create 1 object of the class
		if (extent == null) {
			createInstance();
		}
		return extent;   
		
	}
	
	
	public static ExtentReports createInstance() {
		
        path = System.getProperty("user.dir")+"/reports/ExtentReport.html";		//src/test/java/
        
        File reportFilePath = new File(path);
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);	

        // Report config.
        ExtentSparkReporterConfig config = sparkReporter.config();
        config.setTheme(Theme.STANDARD);
        config.setReportName("Selenium Automation reports");
        config.setDocumentTitle("Azure Automation Report");
        config.setTimeStampFormat("dd-MM-yyyy hh:mm:ss");
        config.setCss(".badge-primary{background-color:#fd7e14}");                                                      // changing the color of the Time
        config.setCss(".badge-success{background-color:#6c757d}");
        config.setCss(".badge-danger{background-color:#6c757d}");
        config.setJs("document.getElementsByClassName('logo')[0].style.display='none';");                               // to hide the logo from top left corner - extent logo

        // For Tabs
        sparkReporter.viewConfigurer().viewOrder().as(new ViewName[]{                                                   // removing the Device tab only            
        		ViewName.DASHBOARD,
        		ViewName.TEST,
                ViewName.EXCEPTION,
                ViewName.CATEGORY,
                ViewName.AUTHOR
              
        }).apply();

        // Add System config info
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("System", systemName());
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        //extent.setSystemInfo("Browser", getBrowserInfo());
        extent.setSystemInfo("Country", System.getProperty("user.country"));
        extent.setSystemInfo("App URL", "Get it from the Framework");
        extent.setSystemInfo("Application ", "IAM | PAM");
        extent.setSystemInfo("Environment", System.getProperty("env"));

        // attaching the report instance       


        return extent;
	}
	
	
//    public static String getBrowserInfo(){
//        String browser = "";
//        String browserVersion = "";
//        String fullBrowser = "";
//        WebDriver driver = new ChromeDriver();
//        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
//        browser = capabilities.getBrowserName();
//        browserVersion = capabilities.getBrowserVersion();
//        fullBrowser = browser +" "+ browserVersion;
//        //System.out.println("\nBrowser: "+browser +"\nVersion: " + browserVersion);
//        driver.quit();
//
//        return fullBrowser;
//
//    }

    public static String systemName(){
        String systemName = "";
        try{
            systemName = InetAddress.getLocalHost().getHostName();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("System: " + systemName);
        return systemName;
    }

}
