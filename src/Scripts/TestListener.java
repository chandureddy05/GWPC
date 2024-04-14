package Scripts;


import com.relevantcodes.extentreports.LogStatus;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlTest;

import Scripts.BusinessComponents;
import commonFunctionLibrary.Excel;
import commonFunctionLibrary.GlobalData;
import extentReports.ExtentManager;
import extentReports.ExtentTestManager;


//public class TestListener extends BusinessComponents implements ITestListener {
public class TestListener implements ITestListener {
BusinessComponents bc=new BusinessComponents();

	

	//Before starting all tests, below method runs.
	@Override
	public void onStart(ITestContext iTestContext) {
		//System.out.println("I am in onStart method " + iTestContext.getName());
		//iTestContext.setAttribute("WebDriver", this.driver);
		//iTestContext.setAttribute("WebDriver", driver);
		//iTestContext.setAttribute("WebDriver", bc.getDriver());

		/*try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		GlobalData.totalTestCases=iTestContext.getCurrentXmlTest().getSuite().getTests().size();
		String testDataFielPath=iTestContext.getCurrentXmlTest().getParameter("TestDataFilePath");
		System.out.println("Data file path:"+testDataFielPath);
		
		
		/*FileInputStream inputworkbook;
		
		try {
			inputworkbook = new FileInputStream(testDataFielPath);
			Dataworkbook=new XSSFWorkbook(inputworkbook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		DataSheet=Dataworkbook.getSheet("TestCaseResults");
		//add test result excel file column header
	    //write the header in the first row
	    GlobalData.testresultdata.put("1", new Object[] {"LOB", "TestCaseName", "QuoteNumber","PolicyNumber"});*/
	    
	    
	    
		
		System.out.println("total test cases="+GlobalData.totalTestCases);
		System.out.println("thread id:"+Thread.currentThread().getId());
		ExtentTestManager.startTest(iTestContext.getCurrentXmlTest().getParameter("TestCaseID").toString(),"");
		/*try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			  Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
      
		
	}

	//After ending all tests, below method runs.
	@Override
	public void onFinish(ITestContext iTestContext) {
		//System.out.println("I am in onFinish method " + iTestContext.getName());
		//Do tier down operations for extentreports reporting!
		//ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
		/*if(GlobalData.testcaseStatus.contains("false"))
			Excel.Exceldata_new(GlobalData.Data_File_Path,"Status","TestCaseResults",0,iTestContext.getCurrentXmlTest().getParameter("TestCaseID").toString(),"Fail");
		else
			Excel.Exceldata_new(GlobalData.Data_File_Path,"Status","TestCaseResults",0,iTestContext.getCurrentXmlTest().getParameter("TestCaseID").toString(),"Pass");
		*///bc.getDriver();
		//Excel.Exceldata_new(GlobalData.Data_File_Path,"QuoteNumber","TestCaseResults",0,iTestContext.getCurrentXmlTest().getParameter("TestCaseID").toString(),GlobalData.quoteNumber);
		//Excel.Exceldata_new(GlobalData.Data_File_Path,"PolicyNumber","TestCaseResults",0,iTestContext.getCurrentXmlTest().getParameter("TestCaseID").toString(),GlobalData.policyNumber);
		
		/* Set<String> keyset = GlobalData.testresultdata.keySet();
		    int rownum = 0;
		    for (String key : keyset) {
		        Row row = DataSheet.createRow(rownum++);
		        Object [] objArr = GlobalData.testresultdata.get(key);
		        int cellnum = 0;
		        for (Object obj : objArr) {
		            Cell cell = row.createCell(cellnum++);
		            if(obj instanceof Date) 
		                cell.setCellValue((Date)obj);
		            else if(obj instanceof Boolean)
		                cell.setCellValue((Boolean)obj);
		            else if(obj instanceof String)
		                cell.setCellValue((String)obj);
		            else if(obj instanceof Double)
		                cell.setCellValue((Double)obj);
		        }
		    }*/
		   /* try {
		        FileOutputStream out =new FileOutputStream(new File(iTestContext.getCurrentXmlTest().getParameter("TestDataFilePath")));
		        GlobalData.Dataworkbook.write(out);
		        out.close();
		        System.out.println("Excel written successfully..");
		         
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }*/
		
		//Excel.ExceldataWrite(GlobalData.Data_File_Path,"QuoteNumber","TestCaseResults",0,iTestContext.getCurrentXmlTest().getParameter("TestCaseID").toString(),GlobalData.quoteNumber);
		ExtentTestManager.endTest();
		ExtentManager.getReporter().flush();
		bc.getDriver().close();
		bc.getDriver().quit();
	}

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {

		
		//System.out.println("I am in onTestStart method " +  getTestMethodName(iTestResult) + " start");
		//Start operation for extentreports.
		//ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(),"");
		//ExtentTestManager.startTest(iTestResult.getTestContext().getCurrentXmlTest().getParameter("TestCaseID").toString(),"");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		
		//System.out.println("I am in onTestSuccess method " +  getTestMethodName(iTestResult) + " succeed");
		//Extentreports log operation for passed tests.
		//ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
		
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		GlobalData.testcaseStatus="false";
		System.out.println("I am in onTestFailure method " +  getTestMethodName(iTestResult) + " failed");
		ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed:"+getTestMethodName(iTestResult));

		//Get driver from BaseTest and assign to local webdriver variable.
		/*Object testClass = iTestResult.getInstance();
		WebDriver webDriver = ((BusinessComponents) testClass).getDriver();*/

		//Take base64Screenshot screenshot.
		//String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot)webDriver).
		String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot)bc.getDriver()).getScreenshotAs(OutputType.BASE64);

		//Extentreports log and screenshot operations for failed tests.
		//ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed"+ExtentTestManager.getTest().getDescription(),ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));

		/*ExtentTestManager.getTest().log(LogStatus.INFO,"Action class");
		Actions ans=new Actions(webDriver);
		ans.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String base64Screenshot1 = "data:image/png;base64,"+((TakesScreenshot)webDriver).
				getScreenshotAs(OutputType.BASE64);
		ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed",
				ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot1));*/

		ExtentTestManager.getTest().log(LogStatus.INFO,"Robot class");

		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(3000);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(3000);
		} catch (AWTException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		String base64Screenshot2 = "data:image/png;base64,"+((TakesScreenshot)bc.getDriver()).
				getScreenshotAs(OutputType.BASE64);
		ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed",ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot2));
		
		bc.getDriver().quit();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		//System.out.println("I am in onTestSkipped method "+  getTestMethodName(iTestResult) + " skipped");
		//Extentreports log operation for skipped tests.
		//ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}


}