package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;


public class ElementUtils {
	

	private WebDriver driver;
	private Actions act;
	JavaScriptUtils javaScriptUtils;

	public ElementUtils(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		javaScriptUtils = new JavaScriptUtils(driver);
	}
	

	public void doSendKeys(By locator, String value) {

		if (value == null) {
			System.out.println("value can not be null while using sendKeys method");
			//throw new FrameworkException("VALUECANNOTBENULL");
		}

		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String doElementGetText(By locator) {
		String eleText = getElement(locator).getText();
		System.out.println("Element text is ====>" + eleText);
		return eleText;
	}
	
	public WebElement getLinkEleByText(String linkText) {
		return driver.findElement(By.linkText(linkText));
	}

	public boolean checkElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String getElementAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public boolean checkElementIsDisabled(By locator) {
		String disabledValue = getElement(locator).getAttribute("disabled");
		if (disabledValue.equals("true")) {
			return true;
		}
		return false;
	}

	public WebElement getElement(By locator) {												// if thru then hight if false then not. since this is the entry point of all interaction
		WebElement element = driver.findElement(locator);
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			javaScriptUtils.flash(element);
		}
		return element;
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();// pc=0
		for (WebElement e : eleList) {
			String text = e.getText();
			if (!text.isEmpty()) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public void clickOnElement(By locator, String linkText) {
		List<WebElement> linksList = getElements(locator);
		System.out.println("total number of links = " + linksList.size());

		for (WebElement e : linksList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(linkText)) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(By searchLocator, By searchSuggLocator, String searchKey, String suggName)
			throws InterruptedException {
		doSendKeys(searchLocator, searchKey);
		Thread.sleep(4000);
		List<WebElement> suggList = getElements(searchSuggLocator);
		System.out.println(suggList.size());
		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggName)) {
				e.click();
				break;
			}
		}
	}

	// ****************************Drop Down Utils************************//
	public void doSelectDropDownByIndex(By locator, int index) {
		if (index < 0) {
			System.out.println("please pass the right (+ve) index");
			return;
		}
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
		if (visibleText == null) {
			System.out.println("please pass the right visible text and it can not be null");
			return;
		}
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	public void doSelectDropDownByValue(By locator, String value) {
		if (value == null) {
			System.out.println("please pass the right visible text and it can not be null");
			return;
		}
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}

	public List<String> getDropDownTextList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsTextList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	public void doSelectDropDownValue(By locator, String dropDownValue) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}
		}
	}

	public void doSelectDropDownValueUsingLocator(By locator, String dropDownValue) {
		List<WebElement> optionsList = getElements(locator);
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}
		}
	}

	// ****************Actions utils********************//
	
	
	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();
	}
	
	public void doActionsSendKeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).perform();
	}
	
	

	public void selectRightClickOption(By contextMenuLocator, String optionValue) {
		act.contextClick(getElement(contextMenuLocator)).perform();
		By optionLocator = By.xpath("//*[text()='" + optionValue + "']");
		doClick(optionLocator);
	}

	/**
	 * this method will handle the menu upto two levels
	 * @param level1MenuLocator
	 * @param level2MenuLocator
	 * @throws InterruptedException
	 */
	public void multiLevelMenuHandling(By level1MenuLocator, By level2MenuLocator) throws InterruptedException {
		act.moveToElement(getElement(level1MenuLocator)).perform();
		Thread.sleep(1500);
		doClick(level2MenuLocator);
	}
	
	public void multiLevelMenuHandling(By level1Locator, String level2, String level3, String level4) throws InterruptedException {
		act.moveToElement(getElement(level1Locator)).perform();
		Thread.sleep(1500);
		act.moveToElement(getLinkEleByText(level2)).perform();
		Thread.sleep(1500);
		act.moveToElement(getLinkEleByText(level3)).perform();
		Thread.sleep(1500);
		getLinkEleByText(level4).click();

	}
	
	public void multiLevelMenuHandling(By level1Locator, String level2, String level3) throws InterruptedException {
		act.moveToElement(getElement(level1Locator)).perform();
		Thread.sleep(1500);
		act.moveToElement(getLinkEleByText(level2)).perform();
		Thread.sleep(1500);
		getLinkEleByText(level3).click();
	}
	
	public boolean checkPseduElementFieldRequired(String field) {
		String script = "return window.getComputedStyle(document.querySelector(\"label[for='" + field + "']\"), '::before').getPropertyValue('content')";
		boolean flag = false;
		//driver.exe
		
		JavascriptExecutor js = (JavascriptExecutor)driver;													// converting driver into JS executor
		String required_text = js.executeScript(script).toString();
		System.out.println(required_text);
		
		// Required field validation
		if(required_text.contains("*")) {
			System.out.println("This field is required");
			flag = true;
		}else {
			System.out.println("Not required field");
			flag = false;
		}
		return flag;
	}
	
	
	/*
	 * 
	 * Wait utils
	 * 
	 */
	
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator)); 
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			javaScriptUtils.flash(element);
		}
		return element;
	}
	
	
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));	
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			javaScriptUtils.flash(element);
		}
		return element;
	}
	
	/*
	 * Pooling wait
	 */
	
	public WebElement waitForElementPresence(By locator, int timeOut, int pooling) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pooling));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator)); 
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			javaScriptUtils.flash(element);
		}
		return element;
	}
	
	
	public WebElement waitForElementVisible(By locator, int timeOut, int pooling) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pooling));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			javaScriptUtils.flash(element);
		}
		return element;
	}

	
	/*
	 * wait for url
	 */

	
	public String waitForURLContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			} else {
				System.out.println(urlFraction + " url value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(urlFraction + " url value is not present...");
			return null;
		}
	}
	
	
	
	
	public String waitForURLToBE(String URLValue, int timeOut) {								// exact url
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlContains(URLValue))) {
				return driver.getCurrentUrl();
			}else {
				System.out.println(URLValue +" not available");
				return null;
			}
		} catch (Exception e) {
			System.out.println(URLValue +" not available");
			return null;
		}
	}	
	
	/*
	 * wait for title
	 */
	public String waitForTitleContains(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}else {
				System.out.println(titleFraction +" not available");
				return null;
			}
		} catch (Exception e) {
			System.out.println(titleFraction +" not available");
			return null;
		}
	}
	
	
	public String waitForTitleIs(String titleValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleIs(titleValue))) {
				return driver.getTitle();
			}else {
				System.out.println(titleValue +" not available");
				return null;
			}
		} catch (Exception e) {
			System.out.println(titleValue +" not available");
			return null;
		}
	}
	
	
	/*
	 * Dynamic Wait for number of windows to be.
	 */
	
	public Boolean waitForNumberOfBrowserWindows(int timeOut, int numberOfWindowsToBe) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindowsToBe));
	}
	
	/*
	 * Dynamic Wait for iFrame by locator
	 */
	
	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameByIndex(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public void waitForFrameByIdOrName(String frameNameOrID, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
	}
	
	public void waitForFrameByFrameElement(String frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	
	/*
	 * Wait for WebElements
	 * 
	 */
	
	// it will go & check if only 1 element is available or not. 
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));									// now the return type of the wait.until is LIST
	}
	
	
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	/*
	 * wait & click when ready
	 */
	
	public void waitAndClick(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();											// 1st it check visibility 2nd it checks enable & then it click
	}
	
	/*
	 * 
	 * 
	 * Fluent waits
	 * 
	 */
	
	public WebElement waitForElementVisibleFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)														// means ignore NoSuchExpection until the 10 sec polling, I can pass any other exception
				.withMessage("---- Time out is done. . . element is not found ---- " + locator);			// message will be displayed after the pooling time when it fails to find the element
		
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	
	public WebElement waitForElementPresenceFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)														// means ignore NoSuchExpection until the 10 sec polling, I can pass any other exception
				.withMessage("---- Time out is done. . . element is not found ---- " + locator);			// message will be displayed after the pooling time when it fails to find the element
		
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	
	public Alert waitForJSAlertFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoAlertPresentException.class)														// means ignore NoSuchExpection until the 10 sec polling, I can pass any other exception
				.withMessage("---- Time out is done. . . JS Alert is not found ---- ");							// message will be displayed after the pooling time when it fails to find the element
				
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	
	public void waitForFrameWithFluentWait(String frameNameOrID, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchFrameException.class)																			// means ignore NoSuchExpection until the 10 sec polling, I can pass any other exception
				.withMessage("---- Time out is done. . . Frame is not found ---- " + frameNameOrID);							// message will be displayed after the pooling time when it fails to find the element
				
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
	}
	
	
	public void waitForElementAndSendKeys(By locator, int timeOut, int pollingTime, String value) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		wait.pollingEvery(Duration.ofSeconds(pollingTime))
			.ignoring(NoSuchElementException.class)
			.withMessage("--- Time Out. . . element not found --- " + locator)
			.until(ExpectedConditions.presenceOfElementLocated(locator))
			.sendKeys(value);
	}
	
	
	public void waitForElementAndClieck(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		wait.pollingEvery(Duration.ofSeconds(pollingTime))
			.ignoring(NoSuchElementException.class)
			.withMessage("--- Time Out. . . element not found --- " + locator)
			.until(ExpectedConditions.presenceOfElementLocated(locator))
			.click();
	}
	
	
	/*
	 * 
	 * Wait without selenium, only java wait
	 * 
	 */
	
	public WebElement retryingElement(By locator, int timeOut) {
		WebElement element = null;													// non-primitive default value is always null, just like string default is always null
		int attemps = 0;
		
		while (attemps < timeOut) {
			try {
				
				element = getElement(locator);
				System.out.println("Element is found . . . " + locator + " in attemp " + attemps);
				break;
				
			} catch (NoSuchElementException e) {
				System.out.println("Element not found . . . " + locator + " in attemp " + attemps);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attemps++;																						// if element not found > attemps will keep increasing & loop will going until the timeOut | loop condition
			
		}
		
		if(element == null) {
			System.out.println("Element not found . . . " + locator + " in attemp " + attemps);
		}
		
		return element;	
		
	}
	
	
	public WebElement retryingElement(By locator, int timeOut, int pollingTime) {
		WebElement element = null;																				// non-primitive default value is always null, just like string default is always null
		int attemps = 0;
		
		while (attemps < timeOut) {
			try {
				
				element = getElement(locator);
				System.out.println("Element is found . . . " + locator + " in attemp " + attemps);
				break;
				
			} catch (NoSuchElementException e) {
				System.out.println("Element not found . . . " + locator + " in attemp " + attemps);
				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attemps++;																							// if element not found > attemps will keep increasing & loop will going until the timeOut | loop condition

		}
		
		if(element == null) {
			System.out.println("Element not found . . . " + locator + " in attemp " + attemps + " with the interval of " + pollingTime +" mill seconds");
		}
		
		return element;	
		
	}
	
	
	public boolean isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'")).toString();
		return Boolean.parseBoolean(flag);
	}
	

}
