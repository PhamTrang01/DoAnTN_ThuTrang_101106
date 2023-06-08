package executionEngine;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Listeners.TestListeners;
import config.ActionKeywords;
import report.ExtentManager;
import report.ExtentTestManager;

import utils.ExcelUtils;
import utils.Log;
import utils.CaptureUtils;

import Mail.EmailAttachments;
import Mail.EmailConfig;
import Mail.SendMail;

@Listeners(TestListeners.class)
public class TestScript_Keyword {
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String description;
	public static String locatorType;
	public static String locatorValue;
	public static String testData;

	public static WebDriver driver;
	public static String testcaseName;


	public static String sPath = System.getProperty("user.dir") + "\\Data\\DoAnTN_Data.xlsx";
	int casePass = 0;
	int caseFail = 0;
	int caseSkip = 0;

	String stestCaseID;

//	Chức năng đăng ký
	//@Ignore

	@Test(priority = 1)
	public void excute_Register() throws Exception {
		ExcelUtils.setExcelFile(sPath, "RegisterSteps");
		ExcelUtils.getSheet("RegisterSteps");
		excute_TestCase();
	}

//	//Chức năng đăng nhập
	//@Ignore
	@Test(priority = 2, description = "")
	public void excute_Login() throws Exception {
		ExcelUtils.setExcelFile(sPath, "LoginSteps");
		ExcelUtils.getSheet("LoginSteps");
		excute_TestCase();
	}

	// Chức năng tìm kiếm
	//@Ignore
	@Test(priority = 3)
	public void excute_Search() throws Exception {
		ExcelUtils.setExcelFile(sPath, "SearchSteps");
		ExcelUtils.getSheet("SearchSteps");
		excute_TestCase();
	}

	// Chức năng giỏ hàng
	//@Ignore
	@Test (priority = 4)
	public void excute_Cart() throws Exception {
		ExcelUtils.setExcelFile(sPath, "CartSteps");
		ExcelUtils.getSheet("CartSteps");
		excute_TestCase();
	
	}

	// Chức năng đặt hàng
	//@Ignore
	@Test(priority = 5)
	public void excute_Order() throws Exception {
		ExcelUtils.setExcelFile(sPath, "OderSteps1");
		ExcelUtils.getSheet("OderSteps1");
		excute_TestCase();
	}

	@Ignore
	@Test
	public void excute_Loginn() throws Exception {
		ExcelUtils.setExcelFile(sPath, "FailSteps");
		ExcelUtils.getSheet("FailSteps");
		excute_TestCase();
	}

//	
	public void excute_TestCase() throws Exception {

		// Không lấy hàng tiêu đề đầu tiên
		for (int iRow = 1; iRow < ExcelUtils.totalRow(); iRow++) {
//			sheetName=ExcelUtils.getCellData(iRow, 1);
//			System.out.println(sheetName);
			if (ExcelUtils.getCellData(iRow, 0) != "") {
				testcaseName = ExcelUtils.getCellData(iRow, 0);
				System.out.println(testcaseName);
			} else {
				sActionKeyword = ExcelUtils.getCellData(iRow, 3);
				// System.out.println(sActionKeyword);
				locatorType = ExcelUtils.getCellData(iRow, 4);
//				System.out.println(locatorType);
				locatorValue = ExcelUtils.getCellData(iRow, 5);
//				System.out.println(locatorValue);
				testData = ExcelUtils.getCellData(iRow, 6);
				System.out.println(testData);
				execute_Actions();
			}
		}
		SendMail.sendEmail();
	}

	static void execute_Actions() throws Exception {
		boolean result;
		
		try {
		switch (sActionKeyword) {
		case "openBrowser":
			Log.info("Execute Test Case");
			ExtentTestManager.saveToReport(testcaseName, "");
			try {
				ActionKeywords.openBrowser(testData);
				ExtentTestManager.logMessage(Status.PASS, description);
			} catch (Exception e) {
				ExtentTestManager.logMessage(Status.FAIL, description);
			}
			break;
//		case "openBrowser":
//			ActionKeywords.openBrowser(testData);
//			ExtentTestManager.logMessage(Status.PASS,"Open browser");
//			break;
		case "navigate":
			ActionKeywords.navigate(testData);
			ExtentTestManager.logMessage(Status.PASS,"Navigate to "+testData);
			break;
		case "setText":
			ActionKeywords.setText(locatorType, locatorValue, testData);
			ExtentTestManager.logMessage(Status.PASS,"Set Text to "+testData);
			break;
		case "clickAndsetTextElement":
			ActionKeywords.clickAndsetTextElement(locatorType, locatorValue, testData);
			ExtentTestManager.logMessage(Status.PASS,"Click And Set Text Element");
			break;
		case "selectOptionByText":
			ActionKeywords.selectOptionByText(locatorType, locatorValue, testData);
			ExtentTestManager.logMessage(Status.PASS,"selectOptionByText to "+testData);
			break;
		case "clickElement":
			ActionKeywords.clickElement(locatorType, locatorValue);
			ExtentTestManager.logMessage(Status.PASS,"Click element");
			break;
		case "clickElement1":
			ActionKeywords.clickElement1(locatorType, locatorValue);
			ExtentTestManager.logMessage(Status.PASS,"Click element1");
			break;
		case "verifyLabel":
			result = ActionKeywords.verifyElementText(locatorType, locatorValue, testData);
			if (result == true) {
				System.out.println(ActionKeywords.verifyElementText(locatorType, locatorValue, testData));
			} else {
				System.out.println(ActionKeywords.verifyElementText(locatorType, locatorValue, testData));
				CaptureUtils.takeScreenshotCapture("Login");
			}
			ExtentTestManager.logMessage(Status.PASS,"Verify Element Text");
			break;
//		case "captrueScreen":
//			ActionKeywords.captureScreenshot("Login");
//			break;
		case "verifyHTML":
			ActionKeywords.verifyHTML5ValidValueField(locatorType, locatorValue, testData);
			ExtentTestManager.logMessage(Status.PASS,"Verify HTML");
			break;
		case "closeBrower":
			ActionKeywords.quitDriver();
			ExtentTestManager.logMessage(Status.PASS,"Close Browser");
			ActionKeywords.quitDriver();
			break;
//		default:
//			Log.error("[>>ERROR<<]: |Keyword Not Found " + sActionKeyword);
//			ExtentTestManager.logMessage(Status.INFO,"Keyword Not Found :" + sActionKeyword);
		}
		}catch (Exception e) {
			e.getMessage();

			ExtentTestManager.logMessage(Status.FAIL,e.getMessage());
		}		
	}	
	
	public void reportInConsole() {

		java.util.Date date = new java.util.Date();
		System.out.println("-----------" + date + "--------------");
		System.out.println("Total number of Testcases run: " + (casePass + caseFail + caseSkip));
		System.out.println("Total number of passed Testcases: " + casePass);
		System.out.println("Total number of failed Testcases: " + caseFail);
		System.out.println("Total number of skiped Testcases: " + caseSkip);
		System.out.println("==========================================================");
	}		
}
