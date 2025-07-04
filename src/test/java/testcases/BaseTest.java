package testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import commons.BaseClass;


public class BaseTest extends BaseClass {
	
	
	@BeforeMethod
	@Parameters({"Browser"})
	public void browserLaunch(String browser) {
		
		BaseClass.browsers(browser);
		
	}
	@AfterMethod
	public void tearDown() {
		// getDriver().close();
		//logger.info("browser closed");
	}

	
	

}
