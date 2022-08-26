package pages;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import reusable.utils.DataUtils;


public class BasePage {
	static Logger logger = LogManager.getLogger(BasePage.class.getName());

	public void launchApp(WebDriver driver) throws IOException {

		driver.get(DataUtils.readPropertiesFile("logintestdata", "prod.salesforce"));
		logger.info("Launched the app");
	}
	


}
