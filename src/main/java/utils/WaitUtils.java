package utils;

import java.time.Duration;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
	
	static WebDriver driver;
	public static void elementToBeLocated(WebElement element,int timeout) {
		try {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.visibilityOf((element)));
		}catch(Exception e) {
			e.getMessage();
		}
	}
	
	

}
