package Sample;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Working_With_Extent_Report {
	WebDriver driver;

	@Test
	public void ReportDemo() throws IOException
	{
		//create a HTML report template

		ExtentHtmlReporter reporter =new ExtentHtmlReporter("./Report/ExecutionReport.html");
		//ExecutionReport is just a file name
		
		//Attach the report to the template
		ExtentReports reports=new ExtentReports();
		reports.attachReporter(reporter);
		
		//create a test with test cases
		ExtentTest test=reports.createTest("Demo web shop Regression");
		
		//Test Steps
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver_win32/chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("https://demowebshop.tricentis.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
		test.log(Status.PASS,"Application Launched Successfully");
		test.pass("Application launch").addScreenCaptureFromPath(Capture_Screen_shot("Launch DWS"));
		
		//Capture_Screen_shot("Launch_DWS");
		
		driver.findElement(By.id("small-searchterms")).sendKeys("books");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		test.log(Status.PASS, "Product search is successful");
		
		Capture_Screen_shot("Search");
		
		test.log(Status.INFO,"Search is completed");
		
		driver.close();
		
		reports.flush();//it is used to flush the report
		

	}
	
	//Methods to capture screen shot
	public String Capture_Screen_shot(String stepname) throws IOException{
		TakesScreenshot ts=(TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		String destpath="./ScreenShot"+stepname+".png";
		//File dest=new File("./ScreenShot/"+stepname+".png");
		FileHandler.copy(src,new File(destpath));
		
		return "."+destpath;
	}
}


