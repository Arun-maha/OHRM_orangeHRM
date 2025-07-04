package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import utils.WaitUtils;


public class LoginPage extends BasePage{
	
	
	
	@FindBy(xpath="//input[@name='username']")
	private WebElement userName;
	
	@FindBy(xpath="//input[@name='password']")
	private WebElement passWord;
	
	@FindBy(xpath="//button[@type='submit']")
	private WebElement submitButton;
	
	@FindBy(xpath="//div[@class='oxd-sidepanel-header']")
	private WebElement header;
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public void usernameField() {
		WaitUtils.elementToBeLocated(userName,5);	
		userName.sendKeys("Admin");
	}
	public void passwordField() {
		WaitUtils.elementToBeLocated(passWord,2);
		passWord.sendKeys("admin123");
	}
	
	public void submitButtonClick() {
		WaitUtils.elementToBeLocated(passWord,2);
		submitButton.click();
	}
	
	public void verifyHeader() {
		
		Assert.assertTrue(header.isDisplayed());
		
	}
	
	

}
