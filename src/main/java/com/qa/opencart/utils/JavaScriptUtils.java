package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {
	
	private WebDriver driver;
	private JavascriptExecutor js;
	
	/**
	 * When any calss is going to use JS util methods then that clss will need to create an object of this class & then that class will pass the driver, which will come in this constructor.
	 * @param driver
	 */
	public JavaScriptUtils(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor)this.driver;
	}

	
	/* Reusable methods */
	
	/**
	 * 
	 * @ JSE navigation methods
	 * 
	 */
	public String getTitleByJS() {
		return js.executeScript("return document.title;").toString();
	}
	
	
	public void goBackWithJS() {
		js.executeScript("history.go(-1);");
	}
	
	public void goForwardWithJS() {
		js.executeScript("history.go(1);");
	}
	
	public void pageRefreshWithJS() {
		js.executeScript("history.go(0);");
	}
	

	/**
	 * 
	 * @param JSE generate alert methods
	 * 
	 */
	public void generateJSAlert(String message){
		js.executeScript("alert('"+message+"');");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
	}
	
	
	public void generateConfirmAlertWithJS(String message){
		js.executeScript("confirm('"+message+"');");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
	}
	
	public void generateConfirmAlertWithJS(String message, String value) {
		js.executeScript("confirm('"+message+"');");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(value);
		alert.accept();
	}
	
	// returns all text contents from the page. 
	public String getPageInnerText() {														// Can grab all the text contents from the page & we can do so many validation, like if the specific text is available in the page or not !
		return js.executeScript("return document.documentElement.innerText;").toString();
	}
	
	
	/**
	 * static scrolling methods -> [when page site is fixed, not for LinkedIN, FB, IG - they are infinite scrolling]
	 */
	
	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, '"+height+"')");
	}
	
	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollToHeight, 0)");
	}
	
	public void scrollPageDownMiddlePage() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
	}
	
	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);								// here element is the [0] argument. If i have more parameter passed, then argument[1]. so arguments increases with the number of parameter passed. But always use argument[0]
	}
	
	/**
	 * 
	 * @param zoomPercent
	 */
	
	public void zoomChromeEdgeSafari(String zoomPercent) {
		String zoom = "document.body.style.zoom = '" + zoomPercent + "%'";
		js.executeScript(zoom);
	}
	
	public void zoomFireFox(String zoomPercent) {
		String zoom = "document.body.style.MozTransform = 'scale(" + zoomPercent + ")'";
		js.executeScript(zoom);
	}
	
	/**
	 * Draw Border around Element
	 */	
	public void drawBorderAroundElement(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	/**
	 * Flash the element
	 */
	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" +color + "'", element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// Do noting.
		}
	}
	
	public void flash(WebElement element) {
		String bgColor = element.getCssValue("backgroundColor");
		for(int i = 0; i < 10; i++) {												// increase loop will increase the flashing. 
			changeColor("rgb(0,200,0)", element);									// the loop is calling the same method twise but passing 2 colors very fast & it will look like flashing until the loops
			changeColor(bgColor, element);		
		}
	}
	

	
	

}
