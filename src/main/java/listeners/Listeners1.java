package listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import reusable.utils.Utilities;
import test.BaseTest;
import test.LoginTest;


public class Listeners1 extends BaseTest implements ITestListener{
	
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("test has started");
		test = extent.createTest(result.getName());	
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.pass(result.getName()+" PASSED");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver"); 
		 
		try {
			
		test.addScreenCaptureFromPath(Utilities.captureScreenshot(BaseTest.driver));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		LoginTest.test.log(Status.FAIL, result.getName()+" Failed");
		
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	
}
