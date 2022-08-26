package test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import reusable.utils.DataUtils;


public class BaseTest {
	
		public static WebDriver driver=null;
		
		private static Logger logger = LogManager.getLogger(BaseTest.class.getName());
		public static ExtentReports extent=null;
		public static ExtentTest test ;
//		static ITestContext context;
//		static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
//		
//		public static ITestContext setContext(ITestContext iTestContext, WebDriver driver) {
//	        iTestContext.setAttribute("driver", driver);
//
//	        return iTestContext;
//	    }
//		
//		static WebDriver getDriver() {
//			return threadLocalDriver.get();
//		}
		
		@BeforeSuite
		public void setUp(ITestContext result) {
			String dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date());
			String reportPath = System.getProperty("user.dir") + "/src/main/resources/reports/" + dateFormat+ ".html";
					
			logger.info("************* Report configuration ********************");
			extent = new ExtentReports();
			
			logger.info(result.getName()+": Initialized extent object for the extent reports");
			ExtentHtmlReporter htmlReport = new ExtentHtmlReporter(reportPath);
			extent.attachReporter(htmlReport);
			
			logger.info(result.getName()+": Attached html report for the reporting");
		}
		
		@AfterSuite
		public void tearDown(ITestContext name) {
			extent.flush();
			logger.info(name.getName()+": Report flushed");
		}
		
	//	public WebDriver getBrowser(String browserName, boolean headless) {
		public WebDriver getBrowser(boolean headless) throws IOException {
			String browserName= DataUtils.readPropertiesFile("logintestdata", "browser");
			String browser = browserName.toLowerCase();
			switch (browser) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				if(headless) {
					ChromeOptions co = new ChromeOptions();
					co.addArguments("--head");
					logger.info("Chromebrowser with headless mode configured");
					driver = new ChromeDriver(co);
				} else {
					driver = new ChromeDriver();
					logger.info("Chromebrowser configured");
				}
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				logger.info("Firefox browser configured");
				break;
			case "safari":
				WebDriverManager.safaridriver().setup();
				driver = new SafariDriver();
				logger.info("Safar browser configured");
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				logger.info("Edge browser configured");
				break;

			default:
				driver = null;
				logger.error("Driver couldn't be initialized");
			}
			return driver;
		}

}
