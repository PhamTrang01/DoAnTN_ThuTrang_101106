package config;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Log;
import utils.CaptureUtils;


public class ActionKeywords {
	public static WebDriver driver;
	private static final int timeoutWait = 10;
	private static final int timeoutWaitForPageLoaded = 20;
	private static Actions action;
	private static JavascriptExecutor js;
	private static WebDriverWait wait;
	 private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	public static Properties OR=new Properties(System.getProperties());
	

    private static WebElement GetElement(String locatorType, String locatorValue){	
    	WebElement element;
    	// nếu locatorType = className 
        if (locatorType.equalsIgnoreCase("className"))
        	element = driver.findElement(By.className(locatorValue));
        else if (locatorType.equalsIgnoreCase("cssSelector"))
        	element = driver.findElement(By.cssSelector(locatorValue));
        else if (locatorType.equalsIgnoreCase("id"))
        	element = driver.findElement(By.id(locatorValue));
        else if (locatorType.equalsIgnoreCase( "partialLinkText"))
        	element = driver.findElement(By.partialLinkText(locatorValue));
        else if (locatorType.equalsIgnoreCase("name"))
        	element = driver.findElement(By.name(locatorValue));
        else if (locatorType.equalsIgnoreCase("xpath"))
        	element = driver.findElement(By.xpath(locatorValue));
        else if (locatorType.equalsIgnoreCase("tagName"))
        	element = driver.findElement(By.tagName(locatorValue));
        else
        	element = driver.findElement(By.xpath(locatorValue));
     
        return element;
        }

	public static void openBrowser() {
		driver = new ChromeDriver();
	}
	
	//Khoi tao cau hinh cua cac Browser de dua vao Switch Case
	private static WebDriver initChromeDriver() {
		Log.info("Launching Chrome browser...");
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
	
	private static WebDriver initFirefoxDriver() {
		System.out.println("Launching Firefox browser...");
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
	
	private static WebDriver initOperaDriver() {
		System.out.println("Launching Opera browser...");
		WebDriverManager.operadriver().setup();
		driver=new OperaDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
	
	public static WebDriver openBrowser(String browserType) {
		switch(browserType.trim().toLowerCase()) {
			case "chrome":
				driver=initChromeDriver();
				Log.info("Open chrome");
				break;
			case "firefox":
				driver=initFirefoxDriver();
				break;
			case "opera":
				driver=initOperaDriver();
				break;
				
			default:
				Log.error("Browser: "+browserType+" is invalid, Launching Chrome as browser of choice...");
				driver=initChromeDriver();
		}
		wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		return driver;
	}


	public static void navigate(String url) {
		try {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Log.info("Access to url: "+url);
		driver.get(url);
		driver.manage().window().maximize();
		driver.navigate().refresh();
		}catch (Exception e) {
			Log.error("Error..."+e.getStackTrace());
		}
	}
	
	//đóng web
	public static void quitDriver() throws Exception{
		if(driver.toString().contains("null")) {
			Log.info("All Browser windows are closed ");
		}else {
			Log.info("All Browser windows are closed ");
			driver.manage().deleteAllCookies();
			driver.quit();
		}
	}

	//hàm 2 đầu vào
	public static void setTextRegister(String locatorType, String locatorValue , String Value) {
		try {
			WebElement element = GetElement(locatorType, locatorValue);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.clear();
			element.sendKeys(Value);
			Log.info("Set text: "+ Value + " on " + locatorType+" : " +locatorValue);
		}catch (Exception e) {
			e.getMessage();
			driver.quit();
		}
		
	}
	
	public static void setTextLogin(WebElement element, String Value) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.clear();
			element.sendKeys(Value);
			Log.info("Set text: "+ element);
		}catch (Exception e) {
			e.getMessage();
			driver.quit();
		}
		
	}
	//hàm setText có 3 đàu vào
	//lấy về đối tượng locatorType, 
	
	public static void setText(String locatorType, String locatorValue, String value) throws IOException {
		WebElement element=	GetElement(locatorType,locatorValue);
		waitForPageLoaded();
		//Chụp màn hình khi không bắt đc đối tượng
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(value);
			Log.info("Set text: "+ value + " on " + locatorType+" : " +locatorValue);
		}catch (Exception e) {
			e.getMessage();
		}
	}
	
	public static void clickElement(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	
	//thực hiện click, không có thì sẽ không thực hiện hành vi click 
	public static void clickElement(String locatorType, String locatorValue) {
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		WebElement element;
		element = GetElement(locatorType,locatorValue);

		waitForPageLoaded();
		wait.until(ExpectedConditions.visibilityOf(element));
		Log.info("Verify element visibility: "+  locatorType+" : " +locatorValue);
		Log.info("Click element: " + locatorType+" : " +locatorValue);
		element.click();
	}
	
	//
	public static void clickElement1(String locatorType, String locatorValue) {
		WebElement element;
		element = GetElement(locatorType,locatorValue);
		WebElement scanEle =new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(element));
		Actions action =new Actions(driver);
		action.moveToElement(scanEle).click().build().perform();
		Log.info("Click element: " + locatorType+" : " +locatorValue);
	}
//	public void clickElement(String element) {
//		waitForPageLoaded();
////		wait.until(ExpectedConditions.visibilityOf(element));
//		driver.findElement(By.xpath(OR.getProperty(element))).click();
//	}


	public static void clickElementWithJs(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true)", element);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		js.executeScript("arguments[0].click();", element);
	}
//đơi page lóad
	public static void waitForPageLoaded() {
		try {
			wait.until(new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver driver) {
					return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
							.equals("complete");
				}
			});
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load request.");
		}
	}

	public static boolean verifyPageLoaded(String pageLoadedText) {
		waitForPageLoaded();
		Boolean res = false;

		List<WebElement> elementList = driver.findElements(By.xpath("//*contains(text(),'" + pageLoadedText + "')]"));
		if (elementList.size() > 0) {
			res = true;
			Log.info("Page loaded(" + res + "): " + pageLoadedText);
		} else {
			res = false;
			Log.info("Page loaded(" + res + "): " + pageLoadedText);
		}
		return res;
	}

	public static boolean verifyUrl(String url) {
//		System.out.println(driver.getCurrentUrl());
//		System.out.println(url);
		Log.info("Verify Url: "+ url);
		return driver.getCurrentUrl().contains(url);
	}

	public static String getPageTitle() {
		waitForPageLoaded();
		Log.info("Get Page Title: "+ driver.getTitle());
		return driver.getTitle();
	}
//so sánh kết quả 
	public static boolean verifyPageTitle(String pageTitle) {
		Log.info("Verify Page Title: "+ pageTitle);
		return getPageTitle().equals(pageTitle);
	}
//chuột phải
	public static void rightClickElement(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		action.contextClick().build().perform();
	}

	// Chon du lieu tu combobox
	public static void selectOptionByText(WebElement element, String text) {
		Select select = new Select(element);
		select.selectByVisibleText(text);
	}
	//chọn theo vị trí trong cobobox
	public static void selectOptionByText(String locatorType, String locatorValue, String text) {
		WebElement element=	GetElement(locatorType,locatorValue);
		Select select=new Select(element);
		select.selectByVisibleText(text);
		Log.info("Select Option By Text: "+ text +" on " + locatorType+" : " +locatorValue);
	}
	//Chọn giá trị theo tùy chọn
	public static void selectOptionByValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}
	
	public static void selectOptionByValue(String locatorType, String locatorValue, String value) {
		WebElement element=	GetElement(locatorType,locatorValue);
		Select select=new Select(element);
		select.selectByValue(value);
		Log.info("Select Option By Text: "+ value +" on " + locatorType+" : " +locatorValue);
	}

	public static void selectOptionByIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
	}
	
	public static void selectOptionByIndex(String locatorType, String locatorValue, int index) {
		WebElement element=	GetElement(locatorType,locatorValue);
		Select select=new Select(element);
		select.selectByIndex(index);
		Log.info("Select Option By Text: "+ index +" on " + locatorType+" : " +locatorValue);
	}

	public static boolean verifyElementText(WebElement element, String textValue) {
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.getText().equals(textValue);
	}
	
	public static boolean verifyElementText(String locatorType, String locatorValue, String text) {
		WebElement element=	GetElement(locatorType,locatorValue);
		wait.until(ExpectedConditions.visibilityOf(element));
		Log.info("Verify Element Text: "+ text +" on " + locatorType+" : " +locatorValue);
		return element.getText().equals(text);
	}

	public static boolean verifyLabel(WebElement element, String textValue) {
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.getText().equals(textValue);
	}
	
	//
	public static boolean verifyResult(String expected) {
		WebElement error = driver.findElement(By.xpath("//li[contains(text(),'"+ expected+"')]"));
		if (error.getText().equals(expected)) {
			return true;
		}
		else return false;
	}
	
	public static boolean verifyElementValue(String locatorType, String locatorValue, String value) {
		WebElement element=	GetElement(locatorType,locatorValue);
		wait.until(ExpectedConditions.visibilityOf(element));
		Log.info("Verify Element Text: "+ value +" on " + locatorType+" : " +locatorValue);
		return element.getAttribute("value").equals(value);
	}

	public static boolean verifyElementExist(By elementBy) {
		// Tạo list lưu tất cả các đối tượng WebElement
		List<WebElement> listElement = driver.findElements(elementBy);

		int total = listElement.size();
		if (total > 0) {
			return true;
		}
		return false;
	}
	
	public static void clickAndsetTextElement(String locatorType, String locatorValue, String value) {
		WebElement element;
		element = GetElement(locatorType,locatorValue);
		WebElement scanEle =new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(element));
		Actions action =new Actions(driver);
		action.moveToElement(scanEle).click().build().perform();
	
		element.clear();	
		element.sendKeys(value);
		Log.info("set text: " + value + " on " + locatorType + " : " + locatorValue);
	}
	//@Step ("Verify HTML5 message of element {0} valid")
    public static Boolean verifyHTML5ValidValueField(String locatorType, String locatorValue, String text) {
    	WebElement element=	GetElement(locatorType,locatorValue);
    	JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean verifyValid = (Boolean) js.executeScript("return arguments[0].validity.valid;", element);
        Log.info("Verify HTML5 Valid Value Field: "+ text +" on " + locatorType+" : " +locatorValue);
        return verifyValid;
    	
    }

//     * Lấy ra câu thông báo từ HTML5 của field
//     *
//     * @param by là element thuộc kiểu By
//     * @return giá trị chuỗi Text của câu thông báo (String)
     
//    @Step("Get HTML5 message of element {0}")
//    public static String getHTML5MessageField(By by) {
//        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
//        String message = (String) js.executeScript("return arguments[0].validationMessage;", getWebElement(by));
//        return message;
//    }
	// wait for Javascript to Loaded
	public static void waitForJQueryLoaded() {
		ExpectedCondition<Boolean> jQueryload = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(jQueryload);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for JQuery.");
		}
	}

	// wait for Javascript to Loaded
	public static void waitForJSLoaded() {

		ExpectedCondition<Boolean> jsload = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			wait = new WebDriverWait(driver,  Duration.ofSeconds(10));
			wait.until(jsload);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Javascript request.");
		}
	}
}
