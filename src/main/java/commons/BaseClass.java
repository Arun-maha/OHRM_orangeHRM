package commons;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
    public static Logger logger = LogManager.getLogger(BaseClass.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static Properties prop;

    public static WebDriver getDriver() {
        return driver.get(); 
    }

    public static String browsers(String browser) {
        try {
            // Load config.properties once
            if (prop == null) {
                FileReader file = new FileReader(System.getProperty("user.dir") + "/src/test/resources/config.properties");
                prop = new Properties();
                prop.load(file);
            }
        } catch (IOException e) {
            logger.error("Error loading config.properties", e);
        }
        
        if(prop.getProperty("execution-env").equalsIgnoreCase("remote")) {
        	DesiredCapabilities cap=new DesiredCapabilities();
        	cap.setPlatform(Platform.WINDOWS);
        	if(browser.equalsIgnoreCase("chrome")) {
        		cap.setBrowserName("chrome");
        	}else if(browser.equalsIgnoreCase("edge")) {
        		cap.setBrowserName("edge");
        	}else {
        		return browser;
        	}
        	try {
				WebDriver remoteDriver= new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
				driver.set(remoteDriver);
				getDriver().manage().window().maximize();
				getDriver().get(prop.getProperty("url"));
			} catch (MalformedURLException e) {
				logger.error("Invalid Selenium Grid URL", e);
		        return "Remote setup failed";
				
			}
        }else if(prop.getProperty("execution-env").equalsIgnoreCase("local")){

        WebDriver localDriver = null;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                localDriver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                localDriver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                localDriver = new EdgeDriver();
                break;

            default:
                logger.error("Invalid browser name: " + browser);
                return "Invalid browser";
        }

        
        driver.set(localDriver);

        getDriver().manage().window().maximize();
        getDriver().get(prop.getProperty("url"));

        logger.info(browser + " browser has been launched successfully.");
        
    }
		return browser;
    }

    public static String captureScreenshot(String tname) {
        if (driver == null) {
            throw new RuntimeException("WebDriver is not initialized.");
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        File sourceFile = ( (TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

        String targetFilepath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "-" + timeStamp + ".png";

        try {
            FileUtils.copyFile(sourceFile, new File(targetFilepath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return targetFilepath;
    }


}
