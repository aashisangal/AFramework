package reusable.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Utilities {
	public void click(WebElement element) {
		element.click();
	}
	
	public void enterText(WebElement element, String text) {
		element.sendKeys(text);
	}

	public boolean isElementDisplayed(WebElement element) {
		return element.isDisplayed();
	}

	public boolean assertEquals(String actual, String expected) {
		try {
			Assert.assertEquals(expected, actual);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static By xpath(String xpath) {
		return By.xpath(xpath);
	}

	public static By css(String css) {
		return By.cssSelector(css);
	}

	public static boolean waitForElement(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public static String captureScreenshot(WebDriver driver) throws IOException {
		String dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date());
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		String destinationpath = System.getProperty("user.dir")+"/src/main/resources/screenshots/"+dateFormat+"_sfdc.PNG";
		File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(destinationpath);
		FileUtils.copyFile(sourceFile, destFile);
		System.out.println("Screenshot captured");
		return destinationpath;
	}
}
