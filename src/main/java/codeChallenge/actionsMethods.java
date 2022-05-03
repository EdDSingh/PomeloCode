package codeChallenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import repository.aspireRepository;

public class actionsMethods extends aspireRepository
{
	public static WebDriver driver;
	static ChromeOptions opt;
	
	static String ProjectPath = null;
	static String TestResultFolder = null;
	static String ScreenShotPath;
	
	//Extent Report Variables
	static ExtentReports extent;
	static ExtentSparkReporter spark; 
	static ExtentTest test;
	static String ReportPath;
	static String ReportName;
	
	//Properties File Variable
	static BufferedReader reader;
	static Properties properties;
	
	
	@BeforeTest
	public void launchBrowser() throws Exception
	{
		CreateExecutionFolderAndPathInitialization();
		rep_CreateExtentReport(ReportPath);
		
		
		String Browser = getPropertyValue("Browser");
		if(driver == null)
		{
			if(Browser.equals("Chrome"))
			{
				WebDriverManager.chromedriver().setup();
				opt = new ChromeOptions();
				opt.addArguments("--disable-notifications");
				driver = new ChromeDriver(opt);
			}
			else if(Browser.equals("Firefox"))
			{
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			else if(Browser.equals("Edge"))
			{
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
			else if(Browser.equals("Headless"))
			{
				WebDriverManager.chromedriver().setup();
				opt = new ChromeOptions();
				opt.addArguments("--headless");
				opt.addArguments("--window-size=1325x744");
				opt.addArguments("--disable-notifications");
				driver = new ChromeDriver(opt);
			}
			else
			{
				System.out.println("Please Enter Valid Browser Name.");
			}
		}
		
		driver.manage().window().maximize();
		wait(3);
		System.out.println("Launching : '["+Browser+"]' Browser.");
	}
	@AfterTest
	public void flush() 
	{
		//driver.close();
		driver.quit();
		extent.flush();
	}
	public static void CreateExecutionFolderAndPathInitialization()throws Exception
	{
		//Variable ini
		ProjectPath = System.getProperty("user.dir");
		ReportName = getDateTimeStamp();
		TestResultFolder = ProjectPath+"/Execution_Reports/"+getPropertyValue("ExecutionName")+"_"+getDateTimeStamp();
		ReportPath = TestResultFolder+"/";
		ScreenShotPath = ReportPath+"ScreenShots";
		Files.createDirectories(Paths.get(TestResultFolder));
		Files.createDirectories(Paths.get(ScreenShotPath));
		
	}
	public static void rep_CreateExtentReport(String ReportPath)
	{
		extent = new ExtentReports();
		spark = new ExtentSparkReporter(ReportPath+ReportName+".html");
		extent.attachReporter(spark);
	}
	public static void rep_CreateTest(String TestScriptName)
	{
		test = extent.createTest(TestScriptName);
	}
	public static void rep_AddLog(String Status_PassOrFail,String LogOrMessage)
	{
		System.out.println("Validation : "+Status_PassOrFail+" - "+LogOrMessage);
		if((Status_PassOrFail.toLowerCase()).equals("pass"))
		{
			test.pass(LogOrMessage);
		}
		else if((Status_PassOrFail.toLowerCase()).equals("fail"))
		{
			test.fail(LogOrMessage);
		}
	}
	public static void rep_close()
	{
		extent.flush();
	}
	public static void openURL(String URL)throws Exception
	{
		System.out.println("Opening "+URL+" URL.");
		driver.get(URL);
		wait(2);
	}
	public static void sendKeys(By wl,String value) throws Exception
	{
		try
		{
			System.out.println("SendKeys To '"+str_ElementDetails(wl.toString())+"' Element.");
			highlightElement(wl);
			driver.findElement(wl).clear();
			wait(1);
			driver.findElement(wl).sendKeys(value);
		}catch(Exception e)
		{
			System.out.println(""+e.getMessage());
			throw e;
		}	
	}
	public static void click(By wl)
	{
		try
		{
			System.out.println("Clicking On '"+str_ElementDetails(wl.toString())+"' Element.");
			highlightElement(wl);
			driver.findElement(wl).click();
			
		}catch(Exception e)
		{
			System.out.println(""+e.getMessage());
			throw e;
		}	
	}
	public static void moveToElement(By wl) throws Exception
	{
		Actions ac = new Actions(driver);
		System.out.println("Moving To '"+str_ElementDetails(wl.toString())+"' Element.");
		ac.moveToElement(driver.findElement(wl)).perform();
		highlightElement(wl);
		wait(1);
	}
	
	public static void wait(int seconds)throws Exception
	{
		Thread.sleep(seconds*1000);
	}
	public static void highlightElement(By wl)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: grey; border: 2px solid red;');", driver.findElement(wl));
	}
	public static boolean isElementVisible(By wl)
	{
		boolean rv = false;
		if(driver.findElements(wl).size()>0)
		{
			if(driver.findElement(wl).isDisplayed()==true)
			{
				rv=true;
			}
		}
		return rv;
	}
	public static boolean isElementAvailable(By wl)
	{
		boolean rv = false;
		if(driver.findElements(wl).size()>0)
		{
				rv=true;
		}
		return rv;
	}
	public static void waitForElementVisibility(By wl) throws Exception
	{
		int y=0;
		int timecountflag = 0;
		while(y==0)
		{
			if(driver.findElements(wl).size()!=0)
			{	
				
				if(driver.findElement(wl).isDisplayed()==true)
				{
					y=1;
				}
				else
				{
					y=0;
					wait(2);
					timecountflag=timecountflag+1;
					if(timecountflag>60)
					{
						throw new Exception("No Such Element Found.");
					}
				}
			}
			else
			{
				y=0;
				wait(1);
				timecountflag=timecountflag+1;
				if(timecountflag>60)
				{
					throw new Exception("Waited For More Than 60 Secs. No Such Element Visible. "+wl.toString());
				}
			}
		}
	}
	public static String web_TakeScreenShot() throws IOException
	{			
		 //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)driver);

        //Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination
        String Ssfilename = getDateTimeStamp()+".png";
        String ssfileref = ScreenShotPath+"/"+Ssfilename;
        File DestFile=new File(ssfileref);

        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
        
        return "./ScreenShots/"+Ssfilename;
	}
	public static void rep_AddLogWithScreenShot(String Status_PassOrFail,String LogOrMessage,String ScreenshotRef)
	{
		System.out.println("Validation : "+Status_PassOrFail+" - "+LogOrMessage);
		if((Status_PassOrFail.toLowerCase()).equals("pass"))
		{
			test.pass(LogOrMessage, MediaEntityBuilder.createScreenCaptureFromPath(ScreenshotRef).build());
		}
		else if((Status_PassOrFail.toLowerCase()).equals("fail"))
		{
			test.fail(LogOrMessage, MediaEntityBuilder.createScreenCaptureFromPath(ScreenshotRef).build());
		}
		
	}
	public static void rep_AddLogInfo(String LogOrMessage)
	{
		test.info(LogOrMessage);
	}
	public static void implicitwait(Duration secs)
	{
		driver.manage().timeouts().implicitlyWait(secs);
	}
	
	public static String getDateTimeStamp()
	{
		String datetamp = new SimpleDateFormat("dd_MMM_yyyy").format(Calendar.getInstance().getTime());
		String timetamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
		return ""+datetamp+"_Time_"+timetamp;
	}
	public static String getPropertyValue(String PropertyName) throws Exception
	{
		reader = new BufferedReader(new FileReader(ProjectPath+"\\src\\test\\resources\\Config.properties"));
		properties = new Properties();
		properties.load(reader);
		String returnvar = properties.getProperty(PropertyName);
		
		reader.close();
		return returnvar;
	}
	//START: STRING FUNCTIONS
			public static String str_ElementDetails(String FullText)
			{
				try {
					return str_TextSplit(str_Replace(FullText, "-> ", "->["),"->","After");	
				}catch(Exception e)
				{
					return FullText;
				}
				
			}
			
			public static String str_TextSplit(String FullText,String TextSplit,String RangeBeforeOrAfter)
			{
				String[] SplitArr = FullText.split(TextSplit);
				String strreturn;
				
				if(RangeBeforeOrAfter.equals("Before"))
				{
					strreturn = SplitArr[0];
				}
				else
				{
					strreturn = SplitArr[1];
				}
				return strreturn;
			}
			public static String str_Replace(String FullText,String ReplaceTo,String ReplaceWith)
			{
				return FullText.replace(ReplaceTo, ReplaceWith);
			}
			public static String str_getFirstLineFromStr(String str)
			{
				int newLineIndex;
				String first;
				if(str.contains("\n"))
				{
					newLineIndex = str.indexOf("\n");

					first = str.substring(0, newLineIndex);
				}
				else
				{
					first = str;
				}
				//String rest = str.substring(newLineIndex + 1);
				return first;
			}
		//END: STRING FUNCTIONS
}
