package test;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import listeners.Listeners1;
import pages.LoginPage;
import pages.UserMenuPage;
import reusable.utils.DataUtils;
import reusable.utils.Utilities;


@Listeners(Listeners1.class)
public class LoginTest extends BaseTest {
	private LoginPage lp ;
	private UserMenuPage up;
	private static Logger logger = LogManager.getLogger(LoginTest.class.getName());
	@BeforeMethod
	public void driverSetup() throws IOException {
		driver = getBrowser(false);
		//driver = getBrowser("chrome", false);
	}
//	@BeforeMethod
//	public void driverSetup(ITestContext iTest) throws IOException {
//
//		this.context = setContext(iTest, driver);		
//	}

	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		driver.quit();
		logger.info("Browser is closed.");
	}
	@Test(dataProviderClass = LoginTest.class, groups = "Login")
	public void loginErrorMessage_TC01(Method name) throws IOException, InterruptedException {
		logger.info("chrome setup");
		lp = new LoginPage(driver);
		up = new UserMenuPage(driver);
		lp.launchApp(driver);	
		test.info("App launched");
		lp.enterUsername(DataUtils.readPropertiesFile("logintestdata", "prod.valid.username"));
		test.info("Username is entered");
		lp.clearPassword();
		test.info("Password cleared");
		lp.clickLogin();
		test.info("Login button clicked");
		Thread.sleep(3000);
		Assert.assertTrue(lp.isErrorMessageSeen(), "Error message should be visible");
		test.info("Login error message is seen");
		logger.info("Login error message is seen");	
		Assert.assertEquals(lp.getErrorMessage(), DataUtils.readPropertiesFile("logintestdata", "login.error.emptypasssword"));	
		test.info("Empty password error message verified");		 
	}

	@Test(groups = "Login", priority = 1)
	public void loginToSFDC_TC02() throws IOException, InterruptedException {
		lp = new LoginPage(driver);
		up = new UserMenuPage(driver);
		lp.launchApp(driver);
		test.info("App launched");
		lp.enterUsername(DataUtils.readPropertiesFile("logintestdata", "prod.valid.username"));
		test.info("Valid username is entered");
		lp.enterPassword(DataUtils.readPropertiesFile("logintestdata", "prod.valid.password"));
		test.info("Valid password is entered");
		Thread.sleep(3000);
		Assert.assertTrue(lp.isFreeTrailSeen(), "Free trial option should be available");		
	}

	@Test (groups = "Login")
	public void checkRememberMe_TC03() throws IOException, InterruptedException {
		lp = new LoginPage(driver);
		up = new UserMenuPage(driver);
		lp.launchApp(driver);
		test.info("App launched");
		lp.enterUsername(DataUtils.readPropertiesFile("logintestdata", "prod.valid.username"));
		test.info("Valid username is entered");
		Thread.sleep(3000);
		lp.enterPassword(DataUtils.readPropertiesFile("logintestdata", "prod.valid.password"));
		test.info("Valid password is entered");
		Assert.assertTrue(lp.selectRemeberMeCheckbox(),"Remember me checkbox should be selected");
		test.info("Remember me checkbox selected");
		lp.clickLogin();
		test.info("Clicked on login button");
		Utilities.waitForElement(driver, up.userMenu);
		test.info("Waiting for usermenu to appear");
		Thread.sleep(3000);
		Assert.assertTrue(up.isUserMenuSeen(),"User menu options should be seen");
		up.clickOnUserMenu();
		test.info("Clicked on user menu");
		Assert.assertTrue(up.selectOptionInUserMenuDropDown(driver, "Logout"), "logout option should be visible and clickable");
		Utilities.waitForElement(driver, lp.savedUsername);
		Assert.assertTrue(lp.isSavedUsernameSeen(), "username should be saved");
		Utilities.waitForElement(driver, lp.savedUsername);
		Assert.assertEquals(lp.getSavedUsername(), DataUtils.readPropertiesFile("logintestdata", "prod.valid.username"), "Saved username and entered username should be same" );
		test.info("User name is saved "+lp.getSavedUsername());
	}

	@Test(groups = "Login")
	public void forgotPassword_TC04A() throws IOException, InterruptedException {
		lp = new LoginPage(driver);
		up = new UserMenuPage(driver);
		lp.launchApp(driver);
		test.info("App launched");
		lp.clickForgotYourPassword();
		Utilities.waitForElement(driver, lp.forgotUsername);
		Assert.assertTrue(lp.isForgotPassowrdDisplayed(), "Forgot password screen should be displayed");
		lp.enterForgotUsername(DataUtils.readPropertiesFile("logintestdata", "prod.valid.actualusername"));
		lp.continueButton.click();
		Assert.assertTrue(lp.passwordResetScreen.isDisplayed(), "password reset message should be seen");
		
	}

	@Test(groups = "Login")
	public void forgotPassword_TC04B() throws IOException, InterruptedException {
		lp = new LoginPage(driver);
		up = new UserMenuPage(driver);
		lp.launchApp(driver);
		test.info("App launched");
		lp.enterUsername("123");
		lp.enterPassword("22131");
		lp.clickLogin();
		Thread.sleep(3000);
		Assert.assertEquals(lp.getErrorMessage(), DataUtils.readPropertiesFile("logintestdata", "login.error.up"));
	}

	@DataProvider(name = "UserNames")
	public Object[][] userCreds() {

		return new Object[][] { { "aashi_sangal123@ta.com", "1234" }, { "aashi_sangal123@ta.com", "password123" } };
	}


}
