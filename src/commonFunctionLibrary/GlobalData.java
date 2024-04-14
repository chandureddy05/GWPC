package commonFunctionLibrary;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class GlobalData {

	public static String Browser;
	//public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
	//public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	//public static WebDriver driver,autoitDriver;
	//public static RemoteWebDriver driver2;
	public static String Data_File_Path;
	public static String TestcaseName;
	public static int datacounter=0;
	public static int keywordIterator=0;
	public static boolean runTimeStatus;
	public static String date;
	//public static ExtentTest extentTest=null;
	//public static TestReports TR=new TestReports();
	public static HashMap<String,String> ConfigData=new HashMap<String,String>();
	public static HashMap<String,String> ObjectRepo=new HashMap<String,String>();
	public static HashMap<String, String> DataElements=new HashMap<String,String>();
	
	
	public static ExtentReports extent=null;
	public static ExtentTest extentTest=null;
	public static String extentReportFile="";
	public static String extentReportImage="";
	public static String DataFilePath;
	public static String TestCaseFilePath;
	public static int onTestStart=0;
	public static int onTestSuccess=0;
	public static int totalTestCases=0;
	public static String TIVLimitAmount;
	public static int counter;
	public static String testcaseStatus="true";
	public static Map<String, String> testresultdata=new HashMap<String, String>();
	public static XSSFWorkbook Dataworkbook;
	public static String quoteNumber;
	public static String timeStamp;
	

}
