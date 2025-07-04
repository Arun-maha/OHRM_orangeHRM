package utils;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.*;
import com.aventstack.extentreports.reporter.configuration.Theme;

import commons.BaseClass;


public class ExtendReportUtility implements ITestListener {
	  public ExtentSparkReporter sparkReporter;
	  public ExtentReports extent;
	  public ExtentTest test;
	  String repName;
	public void onStart(ITestContext textContext) {
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.HH.mm.ss").format(new Date());
		
		repName="Test_Report"+timeStamp+".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);
		sparkReporter.config().setDocumentTitle("OrangeHRM Automation Report");
		sparkReporter.config().setReportName("OrangeHRM Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		
		extent= new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "OrangeHRM");
		extent.setSystemInfo("Module", "Login page");
		extent.setSystemInfo("User name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		textContext.getCurrentXmlTest().getParameter("os");
		textContext.getCurrentXmlTest().getParameter("browser");
		
		
		
	}
	
	public void onTestSuccess(ITestResult result) {
		
		test=extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName()+"got successfully executed");	
	}
	public void onTestFailure(ITestResult result) {
		
		test= extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName()+"got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		try {
			
			String imgpath = BaseClass.captureScreenshot(result.getName());
			 test.addScreenCaptureFromPath(imgpath);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+"get skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}
	
	

}
