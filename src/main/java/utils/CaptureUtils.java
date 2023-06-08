package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import utils.Log;

import config.ActionKeywords;

public class CaptureUtils {
	public static WebDriver driver;
	
	//Lấy đường dẫn đến project hiện tại
     static String projectPath = System.getProperty("user.dir") + "/";
    //Tạo format ngày giờ để xíu gắn vào cái name của screenshot hoặc record video
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public static void captureScreenshot(ITestResult result) throws InterruptedException {
    	//passed = SUCCESS và failed = FAILURE
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                // Tạo tham chiếu của TakesScreenshot với driver hiện tại, TakesScreenshot là interface trong thư viện
                TakesScreenshot ts = (TakesScreenshot) driver;
                // Gọi hàm capture screenshot - getScreenshotAs, chụp mh và chuyển vào file
                File source = ts.getScreenshotAs(OutputType.FILE);
                
                //Kiểm tra folder tồn tại. Nêu không thì tạo mới folder
                File theDir = new File("./Screenshots/");
                if (!theDir.exists()) {
                    theDir.mkdirs();
                }
                // result.getName() lấy tên của test case xong gán cho tên File chụp màn hình
                //Lưu hình vào folder mà mk đã chỉ định là source
                FileHandler.copy(source, new File("./Screenshots/" + result.getName() + ".png"));
                Log.info("Đã chụp màn hình: " + result.getName());
            } catch (Exception e) {
                Log.warn("Exception while taking screenshot " + e.getMessage());
            }
        }
    } 
    public static void takeScreenshotCapture(String screenName) throws IOException {
		// Tạo tham chiếu của TakesScreenshot
		TakesScreenshot ts = (TakesScreenshot) ActionKeywords.driver;
		// Gọi hàm capture screenshot - getScreenshotAs
		File source = ts.getScreenshotAs(OutputType.FILE);
		// Kiểm tra folder tồn tại. Nêu không thì tạo mới folder
		File theDir = new File("./Screenshots/");
		if (!theDir.exists()) {
			theDir.mkdirs();
		}
		// result.getName() lấy tên của test case xong gán cho tên File chụp màn hình
		FileHandler.copy(source,new File("./Screenshots/" + screenName + "Fail_" + dateFormat.format(new Date()) + ".png"));
		Log.info("Da chup man hinh: " + screenName);
	}
}
