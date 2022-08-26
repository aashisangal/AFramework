package pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import reusable.utils.DataUtils;


public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "username")
	public WebElement username;

	@FindBy(name = "pw")
	public WebElement password;

	@FindBy(id = "Login")
	public WebElement loginButton;

	@FindBy(xpath = "//*[@id='rememberUn']")
	public WebElement rememberMe;

	@FindBy(partialLinkText = "Forgot Your")
	public WebElement forgotYourPassword;

	@FindBy(xpath = "/html/body/div[2]/div/div[6]/div[1]/div/div[8]/div/div/div/a/span")
	public WebElement startFreeTrial;

	@FindBy(css = "#error")
	public WebElement loginError;

	@FindBy(id = "idcard-identity")
	public WebElement savedUsername;

	@FindBy(id = "un")
	public WebElement forgotUsername;

	@FindBy(id = "continue")
	public WebElement continueButton;

	@FindBy(id = "forgotPassForm")
	public WebElement passwordResetScreen;

	@FindBy(xpath = "//a[text()='Return to Login']")
	public WebElement returnToLoginButton;

	public void enterUsername(String text) {
		username.sendKeys(text);
		logger.info("username entered on login page");
	}

	public boolean isResetPasswordScreen() {
		if (passwordResetScreen.isDisplayed()) {
			return true;
		} else {
			return false;
		}

	}

	public void enterForgotUsername(String text) {
		forgotUsername.sendKeys(text);
		logger.info("forgot username entered on login page");
	}

	public boolean isForgotPassowrdDisplayed() {
		if (forgotUsername.isDisplayed()) {
			return true;
		} else {
			return false;
		}

	}

	public void clickForgotYourPassword() {
		forgotYourPassword.click();
		logger.info("clicked on forgot your password");
	}

	public void enterPassword(String text) {
		password.sendKeys(text);
		logger.info("password entered on login page");
	}

	public void clickLogin() {
		loginButton.click();
		logger.info("login button clicked");
	}

	/**
	 * This function can be called to fetch the login error message
	 * 
	 * @return string with error message
	 */
	public String getErrorMessage() {
		String errorMessage = loginError.getText();
		if (errorMessage != null) {
			logger.info("error message returned");
		} else {
			logger.info("failed to fetch error message");
		}
		return errorMessage;
	}

	/**
	 * This function logs in to the app. Call this function when on login page
	 * 
	 * @throws IOException
	 */
	public void loginToApp() throws IOException {
		logger.info("initiating logging into app");
		username.sendKeys(DataUtils.readPropertiesFile("logintestdata", "prod.valid.username"));
		logger.info("username entered on login page");
		password.sendKeys(DataUtils.readPropertiesFile("logintestdata", "prod.valid.password"));
		logger.info("password entered on login page");
		loginButton.click();
		logger.info("login button clicked");
	}

	public void clearPassword() {
		password.clear();
		logger.info("password cleared");
	}

	public boolean isErrorMessageSeen() {
		if (loginError.isDisplayed()) {
			logger.info("Error message is displayed");
			return true;
		} else {
			logger.info("Error message is not displayed");
			return false;
		}
	}

	public boolean isFreeTrailSeen() {
		return true;
	}

	public boolean isSavedUsernameSeen() {
		if (savedUsername.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public String getSavedUsername() {
		return savedUsername.getText();
	}

	public boolean selectRemeberMeCheckbox() {
		boolean checkboxStatus = false;
		if (rememberMe.isSelected()) {
			checkboxStatus = true;
			logger.info("remember me checkbox was already selected");
		} else {
			rememberMe.click();
			checkboxStatus = true;
			logger.info("remember me checkbox is selected");
		}
		return checkboxStatus;
	}}
