package Scripts;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.READER;

import org.apache.commons.lang3.StringUtils;
//import org.apache.pdfbox.contentstream.operator.Operator;
//import org.apache.pdfbox.cos.COSArray;
//import org.apache.pdfbox.cos.COSString;
//import org.apache.pdfbox.pdfparser.PDFStreamParser;
//import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageTree;
//import org.apache.pdfbox.pdmodel.common.PDStream;
//import org.apache.pdfbox.rendering.ImageType;
//import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
//import com.testautomationguru.utility.CompareMode;
//import com.testautomationguru.utility.PDFUtil;


import commonFunctionLibrary.Configuration;
import commonFunctionLibrary.Excel;
import commonFunctionLibrary.GetProperties;
//import commonFunctionLibrary.Configuration;
//import commonFunctionLibrary.Excel;
//import commonFunctionLibrary.GetProperties;
import commonFunctionLibrary.GlobalData;
import commonFunctionLibrary.TestReports;
import extentReports.ExtentTestManager;
//import formsValidation.PDFData;

public class BusinessComponents{


	public static TestReports TR=new TestReports();
	public static TestListener TL=new TestListener();
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();



	//Formatting dates to US date format		
	static DataFormatter formatter = new DataFormatter(Locale.US);
	public static synchronized WebDriver getDriver() {
		//public static synchronized WebDriver getDriver() {
		return driver.get();
		//silvernium = new Silvernium(selenium, OBJECTID, SCRIPTKEY);
		//return getDriver();
	}

	public static synchronized WebDriverWait getWait (ThreadLocal<WebDriver> driver) {
		return new WebDriverWait(getDriver(),30);

	}



	@BeforeSuite
	public void readSuiteProperties()
	{
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdfDate=new SimpleDateFormat("yyyyMMdd_HH-mm-ss");
		Date now =new Date();
		String date=sdfDate.format(now);
		GlobalData.date=date;

		TR.setreportfilepath(date);
		GlobalData.extent=new ExtentReports(GlobalData.extentReportFile,false);

	}


	//@BeforeClass
	@Test(priority=1,groups={"HRMS"})
	@Parameters({"TestCaseID","TestDataFilePath","BrowserType"})
	public void readProperties(String testCaseID,String DataFilePath,String BrowserType) throws MalformedURLException
	{

		try
		{
			//Documents("U-GL-851-B CW");
			System.out.println("At initializer="+testCaseID);
			System.out.println("At initializer="+DataFilePath);
			GlobalData.Data_File_Path=DataFilePath;
			GlobalData.TestcaseName=testCaseID;
			//GlobalData.extentTest=GlobalData.extent.startTest("Test Results for "+ LOBType +" Testcase:"+testCaseID).assignCategory(LOBType);
			Configuration configobj=new Configuration(".\\Configuration\\Config.Properties");
			configobj.ReadProperty();
			GetProperties obj=new GetProperties(".\\OR\\PolicyObject.Properties");
			obj.ReadProperty();



			//Set Browser to ThreadLocalMap
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");

			if (BrowserType.equals("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				options.addArguments("disable-infobars");
				options.setAcceptInsecureCerts(true);
				options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\ExternalJars\\chromedriver.exe");
				driver.set(new ChromeDriver(options));
			}
			else if(BrowserType.equals("IE"))
			{
				DesiredCapabilities capability=DesiredCapabilities.internetExplorer();
				capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

				//getDriver().set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability));
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\ExternalJars\\IEDriverServer.exe");
				//System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\ExternalJars\\IEDriver322.8\\IEDriverServer.exe");

				capability.setCapability("nativeEvents", true);
				InternetExplorerOptions options = new InternetExplorerOptions();
				options.merge(capability);
				driver.set(new InternetExplorerDriver(options));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@AfterClass
	public void close()
	{
		//getDriver().close();
		//getDriver().quit();
		//driver.remove();

	}

	public void captureScreenshot(String date,String desc)
	{
		String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot)getDriver()).
				getScreenshotAs(OutputType.BASE64);
		ExtentTestManager.getTest().log(LogStatus.INFO, desc+ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
		
	}

	@Test(priority=2,groups={"HRMS"})
	@Parameters({"BrowserType","envURL"})
	public void launchURL(String BrowserType,String envURL)
	{
		try
		{

			getDriver().get(envURL);
			captureScreenshot(GlobalData.date,"LaunchURL page is loaded");
			getDriver().manage().window().maximize();
		}catch(Exception e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL,e);
			e.printStackTrace();
			Assert.fail();
		}

	}

	@SuppressWarnings("deprecation")
	@Test(priority=3,groups={"HRMS"})
	public void login()
	{
		try
		{
			GlobalData.runTimeStatus=true;

			ReusableFunctions.inputById("txtUsername", "username", GlobalData.ConfigData.get("UN"));			
			ReusableFunctions.inputById("txtPassword", "password", GlobalData.ConfigData.get("PWD"));
			ReusableFunctions.ClickByname("Submit", "Login", "Nil");
			captureScreenshot(GlobalData.date,"Login successfully");
			if (GlobalData.runTimeStatus!=true) {
				GlobalData.testcaseStatus="false";
			}
			//////return "Pass";
		}catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,e);
			e.printStackTrace();
			Assert.fail();

			//////return "Fail";
		}
	}

	@Test(priority=4,dataProvider="data",groups={"HRMS"})
	public void AddCandidates(Map<String,String> testData,ITestContext iTestContext) throws InterruptedException, IOException
	{
		
		ReusableFunctions.ClickByXpath("//b[text()='Recruitment']", "Recruitment", "Nil");
		ReusableFunctions.ClickByXpath("//a[text()='Candidates']", "Recruitment", "Nil");
		ReusableFunctions.ClickByXpath("//input[@id='btnAdd']", "Add Button Click", "Nil");
		((BusinessComponents) getDriver()).captureScreenshot(GlobalData.date,"Add Button Click");
		ReusableFunctions.inputByName("addCandidate[firstName]", "FirstName", testData.get("FirstName"));
		ReusableFunctions.inputByName("addCandidate[lastName]", "FirstName", testData.get("LastName"));
		ReusableFunctions.inputByName("addCandidate[email]", "FirstName", testData.get("Email"));
		ReusableFunctions.inputByName("addCandidate[contactNo]", "FirstName", testData.get("ContactNo"));
		ReusableFunctions.inputTextByActionusingXpath("//input[@name='addCandidate[appliedDate]']", "FirstName", testData.get("Date"));
		ReusableFunctions.ClickByXpath("//input[@id='btnSave']", "Add Button Click", "Nil");
		captureScreenshot(GlobalData.date,"Add Button values entered");
		
		
	}
	
	@Test(priority=5,dataProvider="data",groups={"HRMS"})
	public void PIM_Employees(Map<String,String> testData,ITestContext iTestContext) throws InterruptedException, IOException
	{
		
		ReusableFunctions.ClickByXpath("//b[text()='PIM']", "PIM","Nil");
		ReusableFunctions.ClickById("menu_pim_viewEmployeeList", "EmployeeList", "Nil");
		ReusableFunctions.inputByName("empsearch[employee_name][empName]", "Employeename", testData.get("FirstName"));
		ReusableFunctions.inputByName("empsearch[id]", "ID", testData.get("ID"));
		ReusableFunctions.dropboxSelectByName("empsearch[employee_status]", "EMPStatus", testData.get("EmpStatus"));
		ReusableFunctions.dropboxSelectByName("empsearch[job_title]", "JobTitle", testData.get("JobTitle"));
		ReusableFunctions.inputByName("empsearch[supervisor_name]", "SupervisorName",testData.get("LastName"));
		captureScreenshot(GlobalData.date,"Employee detais entered successfully");
		
	}
	
	
	@Test(priority=6,dataProvider="data",groups={"HRMS"})
	public void Leave_AssignLeave(Map<String,String> testData,ITestContext iTestContext) throws InterruptedException, IOException
	{
		ReusableFunctions.ClickByXpath("//b[text()='Leave']", "Leave", "Nil");
		ReusableFunctions.ClickByXpath("//a[text()='Assign Leave']", "Assign leave", "Nil");
		ReusableFunctions.inputByName("assignleave[txtEmployee][empName]", "EmpNmae",testData.get("FirstName"));
		ReusableFunctions.dropboxSelectByName("assignleave[txtLeaveType]", "LeaveType", testData.get("LeaveType"));
		ReusableFunctions.inputTextByActionusingXpath("//input[@name='assignleave[txtFromDate]']", "Fromdate", testData.get("FromDate"));
		ReusableFunctions.inputTextByActionusingXpath("//input[@name='assignleave[txtToDate]']","Todate",testData.get("ToDate"));	
		Thread.sleep(2000);
		ReusableFunctions.dropboxSelectByName("assignleave[duration][duration]", "Duration", testData.get("Duration"));
		ReusableFunctions.inputByName("assignleave[txtComment]", "comment", testData.get("Comment"));
		ReusableFunctions.ClickById("assignBtn","Assign","Nil");
		captureScreenshot(GlobalData.date,"Leaves assigned sucessfully");
		//Canceling Leave
		Thread.sleep(2000);
		ReusableFunctions.ClickById("menu_leave_viewLeaveList", "Leavelist", "Nil");	
		ReusableFunctions.inputTextByActionusingXpath("//input[@name='leaveList[calFromDate]']", "Fromdate", testData.get("FromDate"));
		ReusableFunctions.inputTextByActionusingXpath("//input[@name='leaveList[calToDate]']","Todate",testData.get("ToDate"));	
		
		int checkbox=getDriver().findElements(By.xpath("//input[@checked='checked']")).size();
		for(int i=0;i<checkbox;i++)
		{
			getDriver().findElements(By.xpath("//input[@checked='checked']")).get(i).click();
		}
		//ReusableFunctions.ClickByXpath("//label[text()='Scheduled']/following-sibling::input[1]", "Scheduled", "Nil");
		ReusableFunctions.inputByName("leaveList[txtEmployee][empName]", "EmpNmae",testData.get("FirstName"));
		ReusableFunctions.ClickById("btnSearch", "search", "Nil");
		Thread.sleep(3000);
//		WebElement mEle=getDriver().findElement(By.xpath("//select[@class='select_action quotaSelect']"));
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mEle);
//		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//select[@class='select_action quotaSelect']")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//select[@class='select_action quotaSelect']/option[2]")).click();
		//ReusableFunctions.dropboxSelectByXpath("//select[@class='select_action quotaSelect']", "selectAction", testData.get("Action"));
		ReusableFunctions.ClickByname("btnSave", "save", "Nil");
		captureScreenshot(GlobalData.date,"Leaves Cancelled sucessfully");
	}
	
//*******************************************************
	//siri code start
	@Test(priority=7,dataProvider="data",groups={"HRMS"})
	public void AddEmployee(Map<String,String> testData,ITestContext iTestContext) throws InterruptedException, IOException
	{
		ReusableFunctions.ClickById("menu_pim_viewPimModule", "PIM", "Nil");
		ReusableFunctions.ClickById("menu_pim_addEmployee", "Add Employee", "Nil");
		ReusableFunctions.inputById("firstName", "firstname",testData.get("FirstName"));
		ReusableFunctions.inputById("lastName", "lastname", testData.get("LastName"));
		captureScreenshot(GlobalData.date,"Employee detais");
		ReusableFunctions.ClickByXpath("//input[@id='chkLogin']", "create login detalies", "Nil");
		ReusableFunctions.inputById("user_name", "user name", testData.get("UserName"));
		ReusableFunctions.inputByName("user_password", "password", testData.get("Password"));
		Thread.sleep(3000);
		ReusableFunctions.inputByName("re_password", "confirm password", testData.get("confirmpassword"));
		ReusableFunctions.ClickByXpath("//input[@value='Save']", "button", "Nil");
		captureScreenshot(GlobalData.date,"Employee detais entered successfully");
		//Employee List
		ReusableFunctions.ClickById("menu_pim_viewPimModule", "PIM", "Nil");
		ReusableFunctions.ClickById("menu_pim_viewEmployeeList", "Employee List", "Nil");
		ReusableFunctions.inputByName("empsearch[employee_name][empName]", "Employee Name", testData.get("FirstName"));
		ReusableFunctions.ClickById("searchBtn", "Search", "Nil");
		ReusableFunctions.ClickByXpath("//a[text()='Chandu']", "link", "Nil");
		//ReusableFunctions.ClickByLinkText("Chandu", testData.get("FirstName"));
		ReusableFunctions.ClickById("btnSave", "Button", "Nil");
		ReusableFunctions.ClickById("personal_optGender_1", "Gender", "Nil");
		ReusableFunctions.inputById("personal_txtEmpNickName", "NickName", testData.get("NickName"));
		ReusableFunctions.inputById("personal_cmbMarital", "Military Status", testData.get("MilitaryStatus"));
	    ReusableFunctions.inputTextByActionusingXpath("//a[@class='ui-state-default'] ", "Todate", testData.get("TodayDtae"));
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//*************************************************************************************
	
	@DataProvider(name = "data")
	public Iterator<Object[]> getTestDataFromDataProvider(Method m,ITestContext iTestContext) throws Exception
	{
		Collection<Object[]> dp = null;
		try
		{
			
			String TestCaseID=iTestContext.getCurrentXmlTest().getParameter("TestCaseID");
			String TestDataFilePath=iTestContext.getCurrentXmlTest().getParameter("TestDataFilePath");
			//String TestDataFilePath="C:\\Users\\XCGPZG3\\TestNG\\TestData\\SmokeTestData\\SmokeTestData.xlsx";
			System.out.println("Testcase:"+TestCaseID);
			System.out.println("keyword="+m.getName());
			//System.out.println(TestDataFilePath);

			//File file = new File(GlobalData.Data_File_Path);
			File file = new File(TestDataFilePath);
			FileInputStream fis = new FileInputStream(file);

			XSSFWorkbook wb = new XSSFWorkbook(fis);



			XSSFSheet sheet = wb.getSheet(m.getName());
			System.out.println(sheet);
			wb.close();
			int lastRowNum = sheet.getLastRowNum();
			int lastCellNum = sheet.getRow(0).getLastCellNum();

			List<Object> liMaps=new ArrayList<Object>();
			//Object[][] obj = new Object[row][lastCellNum];

			Map<Object, Object> datamap = new HashMap<>();
			//To find total testdata rows for a test case
			int testadatTCCont=0;
			for (int i = 0; i < lastRowNum; i++) {
				//for (int j = 0; j < lastCellNum; j++) {
				System.out.println(sheet.getRow(i+1).getCell(1));
				
				if (sheet.getRow(i+1).getCell(1).toString().equals(TestCaseID)) {
					//System.out.println("true");
					testadatTCCont=testadatTCCont+1;

				}
				//}
			}
			//System.out.println("total testcases"+testadatTCCont);
			List<Map<String,String>> list=new ArrayList<Map<String,String>>();


			int row = 0,col,p=0;
			for (int i = 0; i < lastRowNum; i++) {
				//	for (int j = 0; j < lastCellNum; j++) {

				if (sheet.getRow(i+1).getCell(1).toString().equals(TestCaseID)) {
					System.out.println(sheet.getRow(i+1).getCell(1));
					Map<String,String> map=new HashMap<String,String>();
					//row = i+1;
					//int j=1;
					row = i+1;
					int j=0;
					int objRow=0;
					for (; j < lastCellNum; j++) {
						System.out.println("Key:"+sheet.getRow(0).getCell(j).toString()+" value:"+ sheet.getRow(row).getCell(j).toString());
						map.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(row).getCell(j).toString());
					}
					list.add(map);
				}

				//}

			}

			//System.out.println(list.size());
	
			GlobalData.datacounter=list.size();
			Map[] maps=list.toArray(new HashMap[list.size()]);

			//Collection<Object[]> dp = new ArrayList<Object[]>();
			dp = new ArrayList<Object[]>();
			for(Map<String,String> map:list){
				dp.add(new Object[]{map});
			}

			fis.close();

			//return dp.iterator();
		}
		catch(Exception e)
		{
			System.out.println("Failed to test data for sheet"+m.getName());
			System.out.println(e.getMessage());
			e.getStackTrace();
			//ExtentTestManager.getTest().log(LogStatus.FAIL,e.getMessage());
		}
		return dp.iterator();

	}

	public static String Exceldata_new(String TestcasesFilePath,String ColumnName,String DataSheetName,int callcount,String TestCaseName,String DataVal)
	{
		String key="";
		String Value="";
		String tcid="";
		int reqrow=0,matchcount=0,reqcol=0;

		try
		{
			//FileInputStream inputworkbook=new FileInputStream(".\\TestData\\"+DataFilePath);
			FileInputStream inputworkbook=new FileInputStream(TestcasesFilePath);
			XSSFWorkbook Dataworkbook;
			//GlobalData.DataFilePath=DataFilePath;
			Dataworkbook=new XSSFWorkbook(inputworkbook);

			XSSFSheet DataSheet=Dataworkbook.getSheet(DataSheetName);
			if(DataSheet!=null)
			{
				int DataRowcount=DataSheet.getLastRowNum();
				for (int j = 0; j<=DataRowcount; j++) {
					String tcname=DataSheet.getRow(j).getCell(1).getStringCellValue();
					if(tcname.equalsIgnoreCase(TestCaseName))
					{
						reqrow=j;
						break;
					}

				}


				//int DataRowcount=DataSheet.getLastRowNum();
				XSSFRow r=DataSheet.getRow(1);
				Cell cell=null;
				int DataColCount=r.getLastCellNum();

				for (int i = 0; i <=DataColCount; i++) {
					String ColName=DataSheet.getRow(0).getCell(i).getStringCellValue();
					if(ColName.equalsIgnoreCase(ColumnName))
					{
						reqcol=i;
						break;
					}

				}


				cell=DataSheet.getRow(reqrow).getCell(reqcol);
				cell.setCellValue(DataVal.trim());
				try{
					XSSFFormulaEvaluator.evaluateAllFormulaCells(Dataworkbook);
				}catch(RuntimeException e)
				{
					//ExtentTestManager.getTest().log(LogStatus.INFO,"Excel Formula Evaluator Exception"+e);
					//TR.extentTest.log(LogStatus.INFO,"Excel Formula Evaluator Exception"+e);
				}

				inputworkbook.close();
				//FileOutputStream outFile=new FileOutputStream(".\\TestData\\"+DataFilePath);
				FileOutputStream outFile=new FileOutputStream(TestcasesFilePath);

				Dataworkbook.write(outFile);
				Dataworkbook.close();
				outFile.close();
				return "Pass";
			}
			else
			{
				System.out.println(DataSheetName+"Data sheet not found");
				return "Pass";
			}
		}
		catch(Exception e)
		{
			//ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception in Exceladd"+e);
			System.out.println("Exception is="+e);
			return "fail";

		}

	}

	/*public void captureScreenshot(String date,String desc) throws IOException
	{
		
		TakesScreenshot ts=(TakesScreenshot)driver;

	    File source=ts.getScreenshotAs(OutputType.FILE);    
	    FileHandler.copy(source,new File("C:\\Users\\Chandu Reddy\\practise\\HRMS\\HRMS.png"));
		
		String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
		ExtentTestManager.getTest().log(LogStatus.INFO, desc+ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
		
//				addBase64ScreenShot(base64Screenshot));
		


	}*/
	/*public void closeBrowser() throws IOException
	{
		//driver.close();
		Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
		
	}*/


	
}
