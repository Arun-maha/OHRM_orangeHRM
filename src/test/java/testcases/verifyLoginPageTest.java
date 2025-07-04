package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.LoginPage;

public class verifyLoginPageTest extends BaseTest {
	
	
	
	@Test
	public void T001_VerifyLoginPage() {
	    WebDriver driver = getDriver(); 

	    LoginPage loginpage = new LoginPage(driver);
	    logger.info("Navigated to Login page");

	    loginpage.usernameField();
	    loginpage.passwordField();
	    loginpage.submitButtonClick();
	    // loginpage.verifyHeader();
	}

}
