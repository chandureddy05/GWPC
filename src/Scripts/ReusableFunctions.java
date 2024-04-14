package Scripts;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.loading.MLet;

//import org.apache.pdfbox.pdfparser.PDFParser;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.common.PDStream;
//import org.apache.pdfbox.text.PDFTextStripper;
//import org.apache.pdfbox.text.PDFTextStripperByArea;
//import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Predicate;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.ScreenCapture;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import commonFunctionLibrary.GlobalData;
//import commonFunctionLibrary.TestReports;
import extentReports.ExtentTestManager;

@SuppressWarnings("unused")
public class ReusableFunctions {
	//public static TestReports TR=new TestReports();
	public static BusinessComponents bc=new BusinessComponents();
	public static String ClickById(String objects,String description,String testdata)
	{
		try{

			int count=bc.getDriver().findElements(By.id(objects)).size();
			if(!testdata.equalsIgnoreCase(""))
			{
				if (count==1) {
					bc.getDriver().findElement(By.id(objects)).click();
					//ExtentTestManager.getTest().log(LogStatus.PASS,description+" is successful");
					return "true";
				}else{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description+"is not successful and count of the objects:"+count);
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();

				}
			}
		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "Pass";
	}


	public static String ClickByCSSSelectorId(String objects,String description,String testdata)
	{
		try{
			waitForElementTobeClickableByCSSSelectorid(description,objects, 30);
			int count=bc.getDriver().findElements(By.cssSelector("[id$="+objects+"]")).size();
			if(!testdata.equalsIgnoreCase(""))
			{
				if (count==1) {
					bc.getDriver().findElement(By.cssSelector("[id$="+objects+"]")).click();
					Thread.sleep(1000);
					//ExtentTestManager.getTest().log(LogStatus.PASS,description+" is successful");
					return "true";
				}else{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description+"is not successful and count of the objects:"+count);
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();

				}
			}
		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "Pass";
	}


	public static String ClickByname(String objects,String description,String testdata)
	{
		try{
			waitForElementTobeClickableByName(description,objects, 30);
			int count=bc.getDriver().findElements(By.name(objects)).size();
			if(!testdata.equalsIgnoreCase(""))
			{
				if (count==1) {
					bc.getDriver().findElement(By.name(objects)).click();
					ExtentTestManager.getTest().log(LogStatus.PASS,description+"is successful");
					return "true";
				}else{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description+"is not successful and count of the objects:"+count);
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();
					////bc.tearDown();
				}
			}
		}catch(Exception e)
		{
			Assert.fail();
		}
		return "true";
	}



	public static String ClickByLinkText(String description,String testdata)
	{
		try{
			waitForElementTobeClickableByText(description,testdata, 30);
			int count=bc.getDriver().findElements(By.linkText(testdata)).size();
			if(!testdata.equalsIgnoreCase(""))
			{
				if (count==1) {
					bc.getDriver().findElement(By.linkText(testdata.trim())).click();
					ExtentTestManager.getTest().log(LogStatus.PASS,description+"clicking is successful");
					return "true";
				}else{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description+" clicking is not successful and count of the objects:"+count);
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();
					////bc.tearDown();
				}
			}
		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}



	public static String ClickByXpath(String objects,String description,String testdata)
	{
		try{
			waitForElementTobeClickableByXpath(description,objects, 30);
			int index=0;
			int count=bc.getDriver().findElements(By.xpath(objects)).size();
			//if(!testdata.isEmpty())
			if(!testdata.isEmpty() && count>=1)
			{
				if (count>=2) {
					index = 1;
					count=1;
				}
				if (count==1) {
					if (index==1) 
					{
						WebElement mEle1=bc.getDriver().findElement(By.xpath(objects));
						((JavascriptExecutor) bc.getDriver()).executeScript("arguments[0].scrollIntoView(true);", mEle1);
						Thread.sleep(2000);
						bc.getDriver().findElement(By.xpath(objects+"[1]")).click();
					}
					else
					{
						WebElement mEle1=bc.getDriver().findElement(By.xpath(objects));
						((JavascriptExecutor) bc.getDriver()).executeScript("arguments[0].scrollIntoView(true);", mEle1);
						Thread.sleep(2000);
						//((JavascriptExecutor) bc.getDriver()).executeScript("arguments[0].click();", mEle1);
						//Thread.sleep(2000);
						bc.getDriver().findElement(By.xpath(objects)).click();
					}
					Thread.sleep(1000);


					ExtentTestManager.getTest().log(LogStatus.PASS,description+" click is successful");
					return "true";
				}else{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description+" click is not successful and count of the objects:"+count);
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();
					////bc.tearDown();
				}
			}
		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}


	public static String ClickByXpathUsingActionClass(String objects,String description,String testdata)
	{
		try{
			waitForElementTobeClickableByXpath(description,objects, 30);
			int index=0;
			int count=bc.getDriver().findElements(By.xpath(objects)).size();
			//if(!testdata.isEmpty())
			if(!testdata.isEmpty() && count>=1)
			{
				if (count>=2) {
					index = 1;
					count=1;
				}
				if (count==1) {
					if (index==1) 
					{
						WebElement mEle1=bc.getDriver().findElement(By.xpath(objects));
						((JavascriptExecutor) bc.getDriver()).executeScript("arguments[0].scrollIntoView(true);", mEle1);
						Thread.sleep(2000);
						bc.getDriver().findElement(By.xpath(objects+"[1]")).click();
					}
					else
					{
						WebElement mEle1=bc.getDriver().findElement(By.xpath(objects));
						((JavascriptExecutor) bc.getDriver()).executeScript("arguments[0].scrollIntoView(true);", mEle1);
						Thread.sleep(2000);
						((JavascriptExecutor) bc.getDriver()).executeScript("arguments[0].click();", mEle1);
						Thread.sleep(2000);
						//bc.getDriver().findElement(By.xpath(objects)).click();
					}
					Thread.sleep(1000);


					ExtentTestManager.getTest().log(LogStatus.PASS,description+" click is successful");
					return "true";
				}else{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description+" click is not successful and count of the objects:"+count);
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();
					////bc.tearDown();
				}
			}
		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}


	public static String inputByXpath(String objects,String description,String testdata)
	{
		try
		{
			if (testdata!="") 
			{
				waitForElementTobeClickableByXpath(description,objects, 30);
				int count=bc.getDriver().findElements(By.xpath(objects)).size();
				if (count==1)
				{
					try{ 
						WebElement mEle1=bc.getDriver().findElement(By.xpath(objects));
						((JavascriptExecutor) bc.getDriver()).executeScript("arguments[0].scrollIntoView(true);", mEle1);
						Thread.sleep(1000);
						bc.getDriver().findElement(By.xpath(objects)).sendKeys(testdata);
						Thread.sleep(1000);

					}catch(UnhandledAlertException e){
						//acceptAlertPopup();neeed dto enable
						bc.getDriver().findElement(By.xpath(objects)).sendKeys(testdata);
					}
					catch (InvalidElementStateException e) {
						bc.getDriver().findElement(By.xpath(objects)).sendKeys(testdata);
					}
					ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is entered successfully in "+description+"field");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not entered successfully in "+description+"field");
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();
				}

			}


		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}


	public static String inputByName(String objects,String description,String testdata)
	{
		try
		{
			waitForElementTobeClickableByName(description,objects, 30);
			int count=bc.getDriver().findElements(By.name(objects)).size();


			if (testdata!="") 
			{
				if (count==1)
				{

					try{ 
						byte[] bytes = new byte[10];
						String str = new String(bytes, Charset.forName("UTF-8"));
						//bc.getDriver().findElement(By.name(objects)).clear();
						//bc.getDriver().findElement(By.name(objects)).sendKeys(testdata.trim().toString());

						bc.getDriver().findElement(By.name(objects)).sendKeys(testdata);
					}catch(UnhandledAlertException e){
						//acceptAlertPopup();
						bc.getDriver().findElement(By.name(objects)).sendKeys(testdata);
					}
					catch (InvalidElementStateException e) {
						bc.getDriver().findElement(By.name(objects)).sendKeys(testdata);
					}
					ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is entered successfully in "+description+"field");
					return "true";
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not entered successfully in "+description+"field");
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,description);
				bc.captureScreenshot(GlobalData.date,description);
				Assert.fail();
			}

		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}


	public static String inputById(String objects,String description,String testdata)
	{
		try
		{
			int count=bc.getDriver().findElements(By.id(objects)).size();


			if (testdata!="") 
			{
				if (count==1)
				{
					try{ 
						//byte[] bytes = new byte[10];
						//String str = new String(bytes, Charset.forName("UTF-8"));
						//bc.getDriver().findElement(By.name(objects)).clear();
						//bc.getDriver().findElement(By.name(objects)).sendKeys(testdata.trim().toString());
						bc.getDriver().findElement(By.id(objects)).sendKeys(testdata);
					}catch(UnhandledAlertException e){
						//acceptAlertPopup();
						bc.getDriver().findElement(By.name(objects)).sendKeys(testdata);
					}
					catch (InvalidElementStateException e) {
						bc.getDriver().findElement(By.name(objects)).sendKeys(testdata);
					}
					ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is entered successfully in "+description+"field");
					return "true";
				}
				return "true";
			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not entered successfully in "+description+"field");
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,description);
				////bc.tearDown();
			}

		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";

	}




	public static String inputByCSSSelecorId(String objects,String description,String testdata)
	{
		try
		{
			if (testdata!="") 
			{

				int count=bc.getDriver().findElements(By.cssSelector("[id$="+objects+"]")).size();

				if (count==1)
				{
					try{ 
						waitForElementTobeClickableByCSSSelectorid(description,objects, 30);
						byte[] bytes = new byte[10];
						//String str = new String(bytes, Charset.forName("UTF-8"));
						//bc.getDriver().findElement(By.name(objects)).clear();
						//bc.getDriver().findElement(By.name(objects)).sendKeys(testdata.trim().toString());
						//bc.getDriver().findElement(By.cssSelector("[id$="+objects+"]")).clear();
						bc.getDriver().findElement(By.cssSelector("[id$="+objects+"]")).sendKeys(testdata);
						bc.getDriver().findElement(By.cssSelector("[id$="+objects+"]")).sendKeys(Keys.TAB);
						Thread.sleep(1000);
					}catch(UnhandledAlertException e){
						//acceptAlertPopup();
						bc.getDriver().findElement(By.cssSelector("[id$="+objects+"]")).sendKeys(testdata);
					}
					catch (InvalidElementStateException e) {
						bc.getDriver().findElement(By.cssSelector("[id$="+objects+"]")).sendKeys(testdata);
					}
					ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is entered successfully in "+description+"field");
					return "true";
				}

			}
			/*else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not entered successfully in "+description+"field");
				ExtentTestManager.getTest().log(LogStatus.FAIL,description+" click is not successful and count of the objects:"+count);
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,description);
				////bc.tearDown();
			}*/

		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "Pass";
	}


	public static String dropboxSelectByCSSSelectorID(String objects,String description,String testdata)
	{
		try
		{
			String runTimeText="";
			int count=bc.getDriver().findElements(By.cssSelector("[id$="+objects+"]")).size();
			if (testdata!="") 
			{
				if (count==1)
				{
					new Select(bc.getDriver().findElement(By.cssSelector("[id$="+objects+"]"))).selectByVisibleText(testdata);
					ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is selected successfully in "+description+"field");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not selected successfully in "+description+"field");
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();
				}

			}
			return "true";
		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}

	public static String dropboxSelectByName(String objects,String description,String testdata)
	{
		try
		{
			String runTimeText="";
			int count=bc.getDriver().findElements(By.name(objects)).size();
			if (testdata!="") 
			{
				if (count==1)
				{
					new Select(bc.getDriver().findElement(By.name(objects))).selectByVisibleText(testdata);
					ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is selected successfully in "+description+"field");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not selected successfully in "+description+"field");
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();
				}

			}
			return "true";
		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}

	public static String dropboxSelectByXpath(String objects,String description,String testdata)
	{
		try
		{

			int count=bc.getDriver().findElements(By.xpath(objects)).size();
			if (testdata!="") 
			{
				if (count==1)
				{
					String dropText=bc.getDriver().findElement(By.xpath(objects)).getText();
					String[] drop=objects.split("/option");
					new Select(bc.getDriver().findElement(By.xpath(drop[0]))).selectByVisibleText(dropText);
					ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is selected successfully in "+description+"field");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not selected successfully in "+description+"field");
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();
				}

			}
			return "true";
		}catch(NoSuchElementException e)
		{
			String dropText=bc.getDriver().findElement(By.xpath(objects)).getText();
			String[] drop=objects.split("/option");
			new Select(bc.getDriver().findElement(By.xpath(drop[0]))).selectByVisibleText(dropText);
			ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is selected successfully in "+description+"field");
			return "true";
		}catch (Exception e) {
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}


	public static String dropdownSelectionByActionusingXpath(String objects,String description,String testdata)
	{
		try
		{
			if (testdata!="") 
			{
				waitForElementTobeClickableByXpath(description,objects, 60);
				int index=0;
				int count=bc.getDriver().findElements(By.xpath(objects)).size();

				if (count==1)
				{
					ReusableFunctions.inputTextByActionusingXpath(objects, description,testdata);
					Thread.sleep(1000);
					ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is selected successfully in "+description+"field");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not selected successfully in "+description+"field");
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();

				}

			}
			return "true";
		}catch(NoSuchElementException e)
		{
			String dropText=bc.getDriver().findElement(By.xpath(objects)).getText();
			String[] drop=objects.split("/option");
			new Select(bc.getDriver().findElement(By.xpath(drop[0]))).selectByVisibleText(dropText);
			ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is selected successfully in "+description+"field");
			return "true";
		}catch (Exception e) {
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}


	public static String dropdownTextSelectionByXpath(String objects,String description,String testdata)
	{
		try
		{
			if (testdata!="") 
			{
				waitForElementTobeClickableByXpath(description,objects, 60);
				int index=0;
				int count=bc.getDriver().findElements(By.xpath(objects)).size();

				if (count==1)
				{
					ReusableFunctions.ClickByXpath(objects, description, "Nil");
					ReusableFunctions.ClickByXpath("//LI[contains(text(),'"+testdata+"')]", ""	, "Nil");
					bc.captureScreenshot(GlobalData.date,description);
					Thread.sleep(1000);
					ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is selected successfully in "+description+"field");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not selected successfully in "+description+"field");
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();

				}

			}
			return "true";
		}catch(NoSuchElementException e)
		{
			String dropText=bc.getDriver().findElement(By.xpath(objects)).getText();
			String[] drop=objects.split("/option");
			new Select(bc.getDriver().findElement(By.xpath(drop[0]))).selectByVisibleText(dropText);
			ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is selected successfully in "+description+"field");
			return "true";
		}catch (Exception e) {
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}

	public static String MouseMovebyXpath(String objects,String description,String testdata)
	{
		try
		{

			int count=bc.getDriver().findElements(By.xpath(objects)).size();

			if(count==1)
			{
				WebElement MainMenu;
				MainMenu =bc.getDriver().findElement(By.xpath(objects));
				Actions builder=new Actions(bc.getDriver());
				builder.moveToElement(MainMenu).perform();
				ExtentTestManager.getTest().log(LogStatus.PASS,description +" Mouse moving success");
				return "true";
			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Mouse moving is not success");
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,description);
				Assert.fail();
			}
		}catch (Exception e) {
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}

	public static String MouseMovebyId(String objects,String description,String testdata)
	{
		try
		{

			int count=bc.getDriver().findElements(By.id(objects)).size();

			if(count==1)
			{
				WebElement MainMenu;
				MainMenu =bc.getDriver().findElement(By.id(objects));
				Actions builder=new Actions(bc.getDriver());
				builder.moveToElement(MainMenu).perform();
				ExtentTestManager.getTest().log(LogStatus.PASS,description +" Mouse moving success");
				return "true";
			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Mouse moving is not success");
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";
	}


	public static String MouseMovebyName(String objects,String description,String testdata)
	{
		try
		{

			int count=bc.getDriver().findElements(By.name(objects)).size();

			if(count==1)
			{
				WebElement MainMenu;
				MainMenu =bc.getDriver().findElement(By.name(objects));
				Actions builder=new Actions(bc.getDriver());
				builder.moveToElement(MainMenu).perform();
				ExtentTestManager.getTest().log(LogStatus.PASS,description +" Mouse moving success");
				return "true";
			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Mouse moving is not success");
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";
	}


	public static String naviagteToWindow(String objects,String description,String testdata)
	{
		try
		{
			int noOfWindows=bc.getDriver().getWindowHandles().size();
			int flag=0;
			for (String handle : bc.getDriver().getWindowHandles()) 
			{
				bc.getDriver().switchTo().window(handle);
				String strCurrentUrl=bc.getDriver().getCurrentUrl();
				String strDecodeUrl=URLDecoder.decode(strCurrentUrl,"US-ASCII");
				if (strDecodeUrl.toUpperCase().indexOf(testdata.toUpperCase())!=-1) {
					try
					{
						((JavascriptExecutor) bc.getDriver()).executeScript("window.focus()");
						ExtentTestManager.getTest().log(LogStatus.PASS,"Navigation to "+testdata +" child window focus is success");
						flag=1;
						return "true";
					}catch (Exception e) {
						ExtentTestManager.getTest().log(LogStatus.FAIL,"child window is not available "+testdata);
						GlobalData.runTimeStatus=false;
						//bc.tearDown();
					}

				}

			}
			if (flag==0) {

				ExtentTestManager.getTest().log(LogStatus.FAIL,"child window does not exist "+testdata);
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,description);
				scrollDownEnd("", "", "");
				bc.captureScreenshot(GlobalData.date,description);
				//bc.tearDown();
			}
			Thread.sleep(3000);

		}catch(Exception e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL,objects +" object does not Exist");
			bc.captureScreenshot(GlobalData.date,objects);
			Assert.fail();
		}
		return "Pass";
	}

	public static String objectXpathExist(String objects,String description,String testdata)
	{
		try
		{
			if (!testdata.equalsIgnoreCase("")) 
			{
				int count=bc.getDriver().findElements(By.xpath(objects)).size(); 
				if (count==1) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" object Exist");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" object does not Exist");
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					//bc.tearDown();
				}

			}
			return "true";
		}catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,objects +" object does not Exist");
			bc.captureScreenshot(GlobalData.date,objects);
			Assert.fail();
		}
		return "true";

	}

	public static String objectIdExist(String objects,String description,String testdata)
	{
		try
		{
			if (!testdata.equalsIgnoreCase("")) 
			{
				int count=bc.getDriver().findElements(By.id(objects)).size(); 
				if (count==1) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" object Exist");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" object does not Exist");
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					//bc.tearDown();
				}

			}
			return "true";
		}catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,objects +" object does not Exist");
			bc.captureScreenshot(GlobalData.date,objects);
			Assert.fail();
		}
		return "true";

	}


	public static String objectNameExist(String objects,String description,String testdata)
	{
		try
		{
			if (!testdata.equalsIgnoreCase("")) 
			{
				int count=bc.getDriver().findElements(By.name(objects)).size(); 
				if (count==1) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" object Exist");
					return "true";
				}
				else
				{

					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" object does not Exist");
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					//bc.tearDown();
				}

			}
			return "true";
		}catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,objects +" object does not Exist");
			bc.captureScreenshot(GlobalData.date,objects);
			Assert.fail();
		}
		return "true";

	}

	public static String scrollDownEnd(String objects,String description,String testdata)
	{
		try
		{
			/*Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);*/

			/*Actions actions = new Actions(bc.getDriver());
			actions.pause(2000);
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
			Thread.sleep(2000);*/

			ExtentTestManager.getTest().log(LogStatus.PASS,"complete scrolldown of the page");
			return "true";
		}catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,objects +" object does not Exist");
			bc.captureScreenshot(GlobalData.date,objects);
			Assert.fail();
		}
		return "true";

	}

	public static String scrollUpEnd(String objects,String description,String testdata)
	{
		try
		{
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_UP);
			robot.keyRelease(KeyEvent.VK_UP);
			ExtentTestManager.getTest().log(LogStatus.PASS,"complete scrollUp of the page");
			return "true";
		}catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,objects +" object does not Exist");
			bc.captureScreenshot(GlobalData.date,objects);
			Assert.fail();
		}
		return "true";

	}


	public static String waitById(String desc,String objects,int waittime)
	{
		for (int i = 0; i < 4; i++) {
			try
			{
				WebDriverWait wait=new WebDriverWait(bc.getDriver(), waittime);
				wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.id(objects)));
				//Thread.sleep(1000);
				//return "true";
			}catch (Exception e) {
				GlobalData.runTimeStatus=false;
				ExtentTestManager.getTest().log(LogStatus.FAIL,desc +"  is not available");
				bc.captureScreenshot(GlobalData.date,objects);
				
				Assert.fail();
			}}
		return "true";
	}

	public static String waitByCSSselectorId(String desc,String objects,int waittime)
	{
		for (int i = 0; i < 4; i++) {
			try
			{
				WebDriverWait wait=new WebDriverWait(bc.getDriver(), waittime);
				wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id$="+objects+"]")));
				//Thread.sleep(1000);
				//return "true";
			}catch (Exception e) {
				GlobalData.runTimeStatus=false;
				ExtentTestManager.getTest().log(LogStatus.FAIL,desc +"  is not available");
				bc.captureScreenshot(GlobalData.date,objects);
				Assert.fail();
			}
		}
		return "true";
	}

	public static String waitByXpath(String desc,String objects,int waittime)
	{
		for (int i = 0; i < 4; i++) {
			try
			{
				int count=bc.getDriver().findElements(By.xpath(objects)).size();

				WebDriverWait wait=new WebDriverWait(bc.getDriver(), waittime);
				wait.ignoring(StaleElementReferenceException.class,NotActiveException.class).until(ExpectedConditions.elementToBeClickable(By.xpath(objects)));
				//Thread.sleep(2000);


			}
			catch (StaleElementReferenceException b)
			{

			}catch (Exception e) {
				GlobalData.runTimeStatus=false;
				ExtentTestManager.getTest().log(LogStatus.FAIL,desc +"  is not available");
				bc.captureScreenshot(GlobalData.date,objects);
				Assert.fail();
			}
		}
		return "true";
	}
	public static String waitByLinkText(String desc,String objects,int waittime)
	{
		for (int i = 0; i < 4; i++) {
			try
			{
				WebDriverWait wait=new WebDriverWait(bc.getDriver(), waittime);
				wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.linkText(objects)));
				//Thread.sleep(1000);
				//return "true";
			}catch (StaleElementReferenceException b)
			{

			}
			catch (Exception e) {
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,objects);
				ExtentTestManager.getTest().log(LogStatus.FAIL,desc +"  is not available");
				Assert.fail();
			}
		}
		return "true";
	}

	public static String waitByName(String desc,String objects,int waittime)
	{
		for (int i = 0; i < 4; i++) {
			try
			{
				WebDriverWait wait=new WebDriverWait(bc.getDriver(), waittime);
				wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.name(objects)));
				//Thread.sleep(1000);
				//return "true";
			}
			catch (StaleElementReferenceException b)
			{

			}catch (Exception e) {
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,objects);
				ExtentTestManager.getTest().log(LogStatus.FAIL,desc +"  is not available");
				Assert.fail();
			}
		}
		return "true";
	}

	public static boolean isAlertPresent()
	{
		try
		{
			bc.getDriver().switchTo().alert(); 
			ExtentTestManager.getTest().log(LogStatus.PASS,"Alert is present");
			return true;
		}catch(NoAlertPresentException Ex)
		{
			GlobalData.runTimeStatus=false;
			bc.captureScreenshot(GlobalData.date,"NotPresent");
			return false;
		}
		catch(UnhandledAlertException Ex)
		{
			GlobalData.runTimeStatus=false;
			bc.captureScreenshot(GlobalData.date,"NotPresent");
			return false;
		}

	}

	public static boolean accessAlertPresent()
	{
		try
		{
			Alert simpleAlert=bc.getDriver().switchTo().alert();
			simpleAlert.accept();
			ExtentTestManager.getTest().log(LogStatus.PASS,"Accepting alert ");
			return true;
		}catch(NoAlertPresentException Ex)
		{
			GlobalData.runTimeStatus=false;
			bc.captureScreenshot(GlobalData.date,"NotPresent");
			return false;
		}
		catch(UnhandledAlertException Ex)
		{
			GlobalData.runTimeStatus=false;
			bc.captureScreenshot(GlobalData.date,"NotPresent");
			return false;
		}

	}


	public static boolean dismissAlert()
	{
		try
		{
			Alert simpleAlert=bc.getDriver().switchTo().alert();
			simpleAlert.dismiss();
			ExtentTestManager.getTest().log(LogStatus.PASS,"Dismissing alert ");
			return true;
		}catch(NoAlertPresentException Ex)
		{
			GlobalData.runTimeStatus=false;
			bc.captureScreenshot(GlobalData.date,"NotPresent");
			return false;
		}
		catch(UnhandledAlertException Ex)
		{
			GlobalData.runTimeStatus=false;
			bc.captureScreenshot(GlobalData.date,"NotPresent");
			return false;
		}
	}
	public static String waitForFrame(String frameName,int waittime)
	{
		try
		{
			WebDriverWait wait=new WebDriverWait(bc.getDriver(), waittime);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
			return "true";
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";
	}

	public static void hardWait(long timeInSec)
	{
		try
		{
			Thread.sleep(timeInSec);
		}catch (InterruptedException e) {
			// TODO: handle exception
		}

	}

	public void domRefresh()
	{
		Actions action=new Actions(bc.getDriver());
		action.sendKeys(Keys.F12).build().perform();
		hardWait(2000);
		action.sendKeys(Keys.TAB).build().perform();
		hardWait(2000);
		action.sendKeys(Keys.F5).build().perform();
		hardWait(2000);
		action.sendKeys(Keys.F12).build().perform();
		hardWait(2000);

	}

	public static String getTextAlertPopUp()
	{
		try
		{
			if (isAlertPresent()) {
				Alert simpleAlert=bc.getDriver().switchTo().alert();
				String text=simpleAlert.getText();
				return text;
			}
			GlobalData.runTimeStatus=false;
			//bc.tearDown();
		}catch (UnhandledAlertException e) {
			Assert.fail();
		}catch (NoAlertPresentException e) {
			Assert.fail();
		}
		return "true";
	}

	public static String validateTextByXpath(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.xpath(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.xpath(objects)).getText();
				if (runtimeText.trim().equalsIgnoreCase(testdata)) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}

	public static String validateTextById(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.id(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.id(objects)).getText();
				if (runtimeText.trim().equalsIgnoreCase(testdata)) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}



	public static String validateTextByName(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.name(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.name(objects)).getText();
				if (runtimeText.trim().equalsIgnoreCase(testdata)) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}

	public static String CheckRadioButtonSelectionByName(String objects,String description,String testdata)
	{
		try
		{
			int count=bc.getDriver().findElements(By.name(objects)).size(); 
			if (count==1) 
			{
				boolean radiButtonSelected=bc.getDriver().findElement(By.name(objects)).isSelected();
				if (testdata.equalsIgnoreCase("Y")&&radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is chosen");
					return "true";
				}
				else if (testdata.equalsIgnoreCase("N")||(testdata.equalsIgnoreCase(""))&&radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is not chosen");
					return "true";
				}
				else if (testdata.equalsIgnoreCase("Y")&&!radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" is not chosen");
					return "true";

				}else if (testdata.equalsIgnoreCase("N")||(testdata.equalsIgnoreCase(""))&&!radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" is chosen");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" combination does not exist to validate");
					return "true";
				}
			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";

	}



	public static String CheckRadioButtonSelectionById(String objects,String description,String testdata)
	{
		try
		{
			int count=bc.getDriver().findElements(By.id(objects)).size(); 
			if (count==1) 
			{
				boolean radiButtonSelected=bc.getDriver().findElement(By.id(objects)).isSelected();
				if (testdata.equalsIgnoreCase("Y")&&radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is chosen");
					return "true";
				}
				else if (testdata.equalsIgnoreCase("N")||(testdata.equalsIgnoreCase(""))&&radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is not chosen");
					return "true";
				}
				else if (testdata.equalsIgnoreCase("Y")&&!radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" is not chosen");
					return "true";

				}else if (testdata.equalsIgnoreCase("N")||(testdata.equalsIgnoreCase(""))&&!radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" is chosen");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" combination does not exist to validate");
					return "true";
				}
			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}


	public static String CheckRadioButtonSelectionByXpath(String objects,String description,String testdata)
	{
		try
		{
			int count=bc.getDriver().findElements(By.id(objects)).size(); 
			String runtimeText=bc.getDriver().findElement(By.id(objects)).getAttribute("value");
			if (count==1) 
			{
				boolean radiButtonSelected=bc.getDriver().findElement(By.xpath(objects)).isSelected();
				if (radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					return "true";
				}
				else
				{
					Pattern p=Pattern.compile(Pattern.quote("@name='")+"(.*?)"+Pattern.quote("'"));
					Matcher m=p.matcher(objects);
					if (m.find()) {
						String runTime=bc.getDriver().findElement(By.name(m.group(1))).getAttribute("value");
						int count1=bc.getDriver().findElements(By.name(m.group(1))).size();
						if (count1==1) {
							ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Expected value:"+testdata+"Actual value :"+runTime);
						}
						else
						{
							ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not chosen"+description);
						}
					}else{
						ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not chosen"+description);
					}
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					//bc.tearDown();		
				}

			}

			else if (count==0) {
				Pattern p=Pattern.compile(Pattern.quote("@name='")+"(.*?)"+Pattern.quote("'"));
				Matcher m=p.matcher(objects);
				if (m.find()) {
					List<WebElement> name=bc.getDriver().findElements(By.name(m.group(1)));
					for (int i = 0; i < name.size(); i++) {
						String value=name.get(i).getAttribute("value");
						if (name.get(i).isSelected()) {
							if (i+1==name.size()) {
								ExtentTestManager.getTest().log(LogStatus.PASS,description +" Expected value: Actual value :");
							}
						}
						else
						{
							ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Expected value: Actual value :"+value);
						}

					}
				}else {
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" object not found");
				}
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,description);
				//bc.tearDown();
			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"count of objects:"+count);
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,description);
				//bc.tearDown();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";

	}



	public static String getTextById(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.id(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.id(objects)).getText();
				if (!runtimeText.trim().isEmpty()) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is found and avalue is :"+runtimeText);
					return runtimeText;
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" is not available");
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected is not avialable And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";

	}



	public static String getTextByName(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.name(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.name(objects)).getText();
				if (!runtimeText.trim().isEmpty()) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is found and avalue is :"+runtimeText);
					return runtimeText;
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" is not available");
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected is not avialable And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,description);
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}

	public static String getTextByXpath(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.xpath(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.xpath(objects)).getText();
				if (!runtimeText.trim().isEmpty()) 
				{
					//ExtentTestManager.getTest().log(LogStatus.PASS,description +" is found and avalue is :"+runtimeText);
					return runtimeText;
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" is not available");
					GlobalData.runTimeStatus=false;
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected is not avialable And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,description);
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";

	}



	public static String objectGetAttribute(String objects,String description,String testdata)
	{
		try
		{
			String attributeValue="";
			int count=bc.getDriver().findElements(By.xpath(objects)).size(); 
			if (count==1) 
			{
				//runtimeText=bc.getDriver().findElement(By.xpath(objects)).getText();
				if(!bc.getDriver().findElement(By.xpath(objects)).getAttribute(testdata).isEmpty())
				{
					attributeValue=bc.getDriver().findElement(By.xpath(objects)).getAttribute(testdata);
					if(!attributeValue.trim().isEmpty())
					{
						ExtentTestManager.getTest().log(LogStatus.PASS,description +" contains attribute :"+testdata+"with value:"+attributeValue);
						return attributeValue;
					}else{
						ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected attribute is available and the value is empty");
						GlobalData.runTimeStatus=false;
						Assert.fail();
					}
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected attribute is not available ");
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}
			}else{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected is not avialable And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}

		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}


	public static String validateValueByName(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.name(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.name(objects)).getAttribute("value");
				if (runtimeText.trim().equalsIgnoreCase(testdata)) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}


	public static String validateValueById(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.id(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.id(objects)).getAttribute("value");
				if (runtimeText.trim().equalsIgnoreCase(testdata)) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";

	}

	public static String validateValueByXpath(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.xpath(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.xpath(objects)).getAttribute("value");
				if (runtimeText.trim().equalsIgnoreCase(testdata)) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" Expected value:"+testdata+"Actual value :"+runtimeText);
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";

	}



	public static String validateCheckBoxSelectionById(String objects,String description,String testdata)
	{
		try
		{
			int count=bc.getDriver().findElements(By.id(objects)).size(); 
			if (count==1) 
			{
				boolean radiButtonSelected=bc.getDriver().findElement(By.id(objects)).isSelected();
				if (testdata.equalsIgnoreCase("ON")&&!radiButtonSelected) {
					bc.getDriver().findElement(By.id(objects)).click();
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is selected successfully");
					return "true";
				}
				else if (testdata.equalsIgnoreCase("OFF")||(testdata.equalsIgnoreCase(""))&&!radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is deselected successfully");
					return "true";
				}
				else if (testdata.equalsIgnoreCase("ON")&&radiButtonSelected) {
					bc.getDriver().findElement(By.id(objects)).click();
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is deselected successfully");
					return "true";

				}else if (testdata.equalsIgnoreCase("OFF")||(testdata.equalsIgnoreCase(""))&&radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is selected");
					return "true";
				}
				else if(testdata.equalsIgnoreCase(""))
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" is blank and no action is implemented");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +"selecting and deselecting of checkboxes is not working ");
					return "FAIL";
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,description +" object is not selected and the count of objects:"+count);
				return "true";
			}

		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}


	public static String validateCheckBoxSelectionByXpath(String objects,String description,String testdata)
	{
		try
		{
			int count=bc.getDriver().findElements(By.xpath(objects)).size(); 
			if (count==1) 
			{
				boolean radiButtonSelected=bc.getDriver().findElement(By.xpath(objects)).isSelected();
				if (testdata.equalsIgnoreCase("ON")&&!radiButtonSelected) {
					bc.getDriver().findElement(By.xpath(objects)).click();
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is selected successfully");
					return "true";
				}
				else if (testdata.equalsIgnoreCase("OFF")||(testdata.equalsIgnoreCase(""))&&!radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is deselected successfully");
					return "true";
				}
				else if (testdata.equalsIgnoreCase("ON")&&radiButtonSelected) {
					bc.getDriver().findElement(By.xpath(objects)).click();
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is deselected successfully");
					return "true";

				}else if (testdata.equalsIgnoreCase("OFF")||(testdata.equalsIgnoreCase(""))&&radiButtonSelected) {
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is selected");
					return "true";
				}
				else if(testdata.equalsIgnoreCase(""))
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +" is blank and no action is implemented");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,description +"selecting and deselecting of checkboxes is not working ");
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,description +" object is not selected and the count of objects:"+count);
				return "true";
			}

		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}


	public static String getValueById(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.id(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.id(objects)).getAttribute("value");
				if (runtimeText.trim().isEmpty()) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is found and value is :"+runtimeText);
					return runtimeText;
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,"value is not avialable");
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";

	}

	public static String getValueByName(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.name(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.name(objects)).getAttribute("value");
				if (runtimeText.trim().isEmpty()) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is found and value is :"+runtimeText);
					return runtimeText;
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,"value is not avialable");
					GlobalData.runTimeStatus=false;
					Assert.fail();


				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";

	}


	public static String getValueByXpath(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.xpath(objects)).size(); 
			if (count==1) 
			{
				runtimeText=bc.getDriver().findElement(By.xpath(objects)).getAttribute("value");
				if (runtimeText.trim().isEmpty()) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,description +" is found and value is :"+runtimeText);
					return runtimeText;
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,"value is not avialable");
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}

	public static String validateRadioTextById(String objects,String description,String testdata)
	{
		try
		{
			String runtimeText="";
			int count=bc.getDriver().findElements(By.id(objects)).size(); 
			if (count>=1) 
			{
				for (WebElement element : bc.getDriver().findElements(By.id(objects))) {
					Boolean selectedRadio=element.isSelected();
					if (selectedRadio) {
						runtimeText=element.getAttribute("value");
						break;
					}

				}
				if (runtimeText.trim().equalsIgnoreCase(testdata)) 
				{
					ExtentTestManager.getTest().log(LogStatus.PASS,"Expected"+testdata+"value is selected under "+description+"Actual value:"+runtimeText);
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected"+testdata+"value is selected under "+description+"Actual value:"+runtimeText);
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}

			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}


	public static String windowScrollDown(String objects,String description,String testdata)
	{
		try
		{
			JavascriptExecutor jse=(JavascriptExecutor)bc.getDriver();
			jse.executeScript("windows.scrollBy(0,500)", "");
			return "pass";
		}catch (Exception e) {
			Assert.fail();
		}

		return "true";
	}

	public static String windowScrollUp(String objects,String description,String testdata)
	{
		try
		{
			JavascriptExecutor jse=(JavascriptExecutor)bc.getDriver();
			jse.executeScript("windows.scrollTo(0,500)", "");
			return "pass";
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";

	}


	public static String checkObjectById(String objects,String description,String testdata)
	{
		try
		{
			int count=bc.getDriver().findElements(By.id(objects)).size(); 
			if (count==1) 
			{
				if((!bc.getDriver().findElement(By.id(objects)).isSelected())&&testdata.equalsIgnoreCase("Y")) {
					bc.getDriver().findElement(By.id(objects)).click();
					ExtentTestManager.getTest().log(LogStatus.PASS, description+"is selected successfully");
					return "true";
				}else if((!bc.getDriver().findElement(By.id(objects)).isSelected())&&testdata.equalsIgnoreCase("N")) {
					bc.getDriver().findElement(By.id(objects)).click();
					ExtentTestManager.getTest().log(LogStatus.PASS, description+"is deselected successfully");
					return "true";
				}else if((bc.getDriver().findElement(By.id(objects)).isSelected())&&testdata.equalsIgnoreCase("N")) {
					bc.getDriver().findElement(By.id(objects)).click();
					ExtentTestManager.getTest().log(LogStatus.PASS, description+"is deselected successfully");
					return "true";
				}else if((bc.getDriver().findElement(By.id(objects)).isSelected())&&testdata.equalsIgnoreCase("Y")) {
					bc.getDriver().findElement(By.id(objects)).click();
					ExtentTestManager.getTest().log(LogStatus.PASS, description+"is selected successfully");
					return "true";
				}else if(testdata.equalsIgnoreCase(""))
				{
					ExtentTestManager.getTest().log(LogStatus.PASS, description+"is blank no action is implemented");
					return "true";
				}
				else
				{
					ExtentTestManager.getTest().log(LogStatus.FAIL, description+"selecting/deselecting the checkbox is not implemented");
					GlobalData.runTimeStatus=false;
					Assert.fail();
				}
			}else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Expected:"+description +" is not avialable"+testdata+"And count of objects:"+count);
				GlobalData.runTimeStatus=false;
				Assert.fail();
			}
		}catch (Exception e) {
			Assert.fail();
		}
		return "true";
	}


	public static String ObjectRepoAndScriptGenerator(String Menu,String identifier)
	{
		try
		{
			//List<WebElement> allElements=bc.getDriver().findElements(By.xpath("table[@class='innertable']//select|//table[@class='innertable']//input"));
			List<WebElement> allElements=bc.getDriver().findElements(By.xpath("table[@class='style2']/input|//table[@class='innertable']//input"));

			String object="",duplicateObject="";
			FileWriter scriptWriter=new FileWriter(".//output Files//Script.txt");
			FileWriter objRepowriter=new FileWriter(".//output Files//ObjectRepository.txt");
			FileWriter dataElementsWriter=new FileWriter(".//output Files//DataElements.txt");
			for (int i = 0; i < allElements.size(); i++) {
				WebElement webElement=allElements.get(i);
				String xpath=webElement.toString();
				String tag=webElement.getTagName();
				String property=webElement.getAttribute(identifier);
				if (!webElement.getAttribute("type").equals("checkbox")) {
					String objectPath="//input[@"+identifier+"='"+property+"']/parent::td/preceding::sibling::td[1]"
							+"|//select[@"+identifier+"='"+property+"']/parent::td/preceding::sibling::td";
					object=ReusableFunctions.getTextByXpath(objectPath, property, "").replaceAll("\\W+", "");
				}
				else
				{
					object=webElement.getAttribute("title").replaceAll("\\W+", "");
				}
				if(!property.substring(property.length()-4).equals("Desc")){
					if(webElement.getTagName().equals("input")){
						if(webElement.getAttribute("type").equals("radio")){
							if(!duplicateObject.equals(object)){
								dataElementsWriter.append(object).append("\n");
								objRepowriter.append(identifier.toUpperCase()+"_"+Menu+"_"+object+"="+property).append("\n");
								scriptWriter.append("ReusableFunctions.ClickByXpath(\"//input[@id='\"GlobalData.ObjectRepo.get(\""+identifier.toUpperCase()+"_"+Menu+""
										+"_"+object+"\")+\"' and @value='\"+GlobalData.DataElements.get(\""+object+"\")+\"']\",\""+object+"\",GlobalData.DataElements.get(\""
										+""+object+"\"));").append("\n");
								duplicateObject=object;
							}
						}else if(webElement.getAttribute("type").equals("checkbox")){
							dataElementsWriter.append(object).append("\n");
							objRepowriter.append(identifier.toUpperCase()+"_"+Menu+"_"+object+"="+property).append("\n");
							scriptWriter.append("ReusableFunctions.checkObjectById(GlobalData.ObjectRepo.get(\""+identifier.toUpperCase()+"_"+Menu+""
									+"_"+object+"\"),\""+object+"\",GlobalData.DataElements.get(\""+""+object+"\"));").append("\n");
						}

						else if(webElement.getAttribute("class").equals("button")){
							dataElementsWriter.append(object).append("\n");
							objRepowriter.append(identifier.toUpperCase()+"_"+Menu+"_"+object+"="+property).append("\n");
							scriptWriter.append("ReusableFunctions.clickById(GlobalData.ObjectRepo.get(\""+identifier.toUpperCase()+"_"+Menu+""
									+"_"+object+"\"),\""+object+"\",GlobalData.DataElements.get(\""+""+object+"\"));").append("\n");
						}else{
							dataElementsWriter.append(object).append("\n");
							objRepowriter.append(identifier.toUpperCase()+"_"+Menu+"_"+object+"="+property).append("\n");
							scriptWriter.append("ReusableFunctions.inputById(GlobalData.ObjectRepo.get(\""+identifier.toUpperCase()+"_"+Menu+""
									+"_"+object+"\"),\""+object+"\",GlobalData.DataElements.get(\""+""+object+"\"));").append("\n");
						}
					}else if(webElement.getTagName().equals("select")){
						dataElementsWriter.append(object).append("\n");
						objRepowriter.append(identifier.toUpperCase()+"_"+Menu+"_"+object+"="+property).append("\n");
						scriptWriter.append("ReusableFunctions.dropboxSelectById(GlobalData.ObjectRepo.get(\""+identifier.toUpperCase()+"_"+Menu+""
								+"_"+object+"\"),\""+object+"\",GlobalData.DataElements.get(\""+""+object+"\"));").append("\n");
					}
				}
			}

			scriptWriter.close();
			objRepowriter.close();
			dataElementsWriter.close();
			return "pass";

		}catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return "Fail";
		}catch (IOException e) {
			e.printStackTrace();
			return "Fail";
		}catch (Exception e) {
			e.printStackTrace();
			return "Fail";
		}
	}
	/*public static Reader getPDFContent(String f){
		Reader reader=null;
		PDDocument pdfDocument=null;
		FileInputStream fis=null;
		String Contents=null;
		String text = null;
		try{
			fis=new FileInputStream(f);
			PDFParser parser=new PDFParser(fis);
			parser.parse();
			pdfDocument=parser.getPDDocument();
			PDFTextStripper stripper=new PDFTextStripper();
			Contents=stripper.getText(pdfDocument);
			reader=new StringReader(Contents);

			
			PDDocument doc = PDDocument.load(f);
	        org.apache.pdfbox.text.PDFTextStripper pdfStripper = new org.apache.pdfbox.text.PDFTextStripper();
	        text = pdfStripper.getText(doc);
	        reader=new StringReader(text);
	        doc.close();

			PdfReader pdfReader=new PdfReader(f);
			//PDDocument doc = PDDocument.load(f);
			int s=pdfReader.getNumberOfPages();
			for (int i = 1; i <= s; i++) {
				System.out.println(PdfTextExtractor.getTextFromPage(pdfReader,i));

			}

		}catch(IOException e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Error while reading the file");
		}
		return reader;

	}


	public static String gettextPDFContent(final File f)
	{
		Reader reader=null;
		PDDocument pdfDocument=null;
		FileInputStream fis=null;
		String Contents=null;
		String text = null;
		try{
			fis=new FileInputStream(f);
			PDFParser parser=new PDFParser(fis);
			parser.parse();
			pdfDocument=parser.getPDDocument();
			PDFTextStripper stripper=new PDFTextStripper();
			Contents=stripper.getText(pdfDocument);
			reader=new StringReader(Contents);


			PDDocument doc = PDDocument.load(f);
			org.apache.pdfbox.text.PDFTextStripper pdfStripper = new org.apache.pdfbox.text.PDFTextStripper();
			text = pdfStripper.getText(doc);
			//reader=new StringReader(text);
			doc.close();

			 PDDocument document = PDDocument.load(f); 

	            document.getClass();

	            if (!document.isEncrypted()) {

	                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
	                stripper.setSortByPosition(true);

	                PDFTextStripper tStripper = new PDFTextStripper();

	                String pdfFileInText = tStripper.getText(document);
	                //System.out.println("Text:" + st);

					// split by whitespace
	                String lines[] = pdfFileInText.split("\\r?\\n");
	                for (String line : lines) {
	                    System.out.println(line);
	                }
	            }
			//System.out.println(text);
		}catch(IOException e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Error while reading the file");
		}
		return text;

	}

	public static String readPDF(String Path,String fileName)
	{
		try
		{
			///PDFValidation box=new PDFValidation();need to endable
			File f=new File(Path+"//"+fileName);
			//String str=box.getContents(f);need to enable
			//return str;need to enable
			return "";
		}catch(Exception e1)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Error is"+e1);
			GlobalData.runTimeStatus=false;
			bc.captureScreenshot(GlobalData.date,fileName);
			return "fail";
		}
	}
*/

	public static String getValueFromGlassButton(String objects,String partiaIDCodeTextBox,String description,String testdata)
	{
		try
		{

			int count=bc.getDriver().findElements(By.xpath(objects)).size();
			if (testdata!="") 
			{
				if (count==1)
				{
					//ReusableFunctions.ClickByXpath(objects,"UPCCodeGlassBtn","NIl");
					ReusableFunctions.ClickByXpathUsingActionClass(objects,"UPCCodeGlassBtn","NIl");
					ReusableFunctions.waitByXpath("Search Btn","//a[contains(@id,'SearchLinksInputSet:Search')]", 30);
					ReusableFunctions.inputTextByActionusingXpath(partiaIDCodeTextBox, "CodeTxtBox", testdata);
					Thread.sleep(2000);
					//ReusableFunctions.inputByCSSSelecorId(partiaIDCodeTextBox, "CodeTxtBox", testdata);
					/*ReusableFunctions.ClickByXpath("//a[@id='IndustryCodeSearchPopup:IndustryCodeSearchScreen:IndustryCodeSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search']",description, "Nil");
					ReusableFunctions.waitByXpath("//a[@id='IndustryCodeSearchPopup:IndustryCodeSearchScreen:IndustryCodeSearchResultsLV:0:_Select']", 30);
					ReusableFunctions.ClickByXpath("//a[@id='IndustryCodeSearchPopup:IndustryCodeSearchScreen:IndustryCodeSearchResultsLV:0:_Select']","Search Result Select ", "Nil");
					 */
					ReusableFunctions.ClickByXpath("//a[contains(@id,'SearchLinksInputSet:Search')]",description, "Nil");
					Thread.sleep(2000);
					ReusableFunctions.waitByXpath("Select Btn","//a[contains(@id,'Select')]", 30);
					ReusableFunctions.ClickByXpath("//a[contains(@id,'Select')]", "Select","Nil");

					ReusableFunctions.waitByXpath(description,objects, 30);
					ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is selected successfully in "+description+"field");
					return "true";
				}
				else
				{
					//ExtentTestManager.getTest().log(LogStatus.FAIL,description+"is not successful and count of the objects:"+count);
					//ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is not selected successfully in "+description+"field");
					ExtentTestManager.getTest().log(LogStatus.FAIL,description+"is not successful and count of the objects:"+count);
					bc.captureScreenshot(GlobalData.date,description);
					Assert.fail();

					//bc.tearDown();
				}

			}
			// }
			return "true";
		}catch(NoSuchElementException e)
		{
			String dropText=bc.getDriver().findElement(By.xpath(objects)).getText();
			String[] drop=objects.split("/option");
			new Select(bc.getDriver().findElement(By.xpath(drop[0]))).selectByVisibleText(dropText);
			ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is selected successfully in "+description+"field");
			return "true";
		}catch (Exception e) {
			bc.captureScreenshot(GlobalData.date,description);
			Assert.fail();
		}
		return "true";
	}


	public static String waitForElementTobeClickableByCSSSelectorid(String desc,String objects,int waittime)
	{
		for (int i = 0; i < 4; i++) {
			try
			{
				WebDriverWait wait=new WebDriverWait(bc.getDriver(), waittime);
				wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id$="+objects+"]")));
				//Thread.sleep(1000);

			}
			catch (StaleElementReferenceException b)
			{
				GlobalData.runTimeStatus=false;
				/*ExtentTestManager.getTest().log(LogStatus.FAIL,"Error:"+e);
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,objects);
				//bc.tearDown();*/
			}catch (Exception e) {
				GlobalData.runTimeStatus=false;
				ExtentTestManager.getTest().log(LogStatus.FAIL,desc +"  is not available");
				//Assert.fail();
			}
		}
		return "true";
	}

	public static String waitForElementTobeClickableByXpath(String desc,String objects,int waittime)
	{

		for (int i = 0; i < 4; i++) {
			try
			{

				WebDriverWait wait=new WebDriverWait(bc.getDriver(), waittime);
				wait.ignoring(StaleElementReferenceException.class,NotActiveException.class).until(ExpectedConditions.elementToBeClickable(By.xpath(objects)));
				//Thread.sleep(1000);

			}

			catch (StaleElementReferenceException b)
			{
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,objects);
				/*ExtentTestManager.getTest().log(LogStatus.FAIL,"Error:"+e);
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,objects);
				//bc.tearDown();*/
			}
			catch (Exception e)
			{
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,objects);
				ExtentTestManager.getTest().log(LogStatus.FAIL,desc +"  is not available");
				//Assert.fail();
			}
		}
		return "true";
	}

	public static String waitForElementTobeClickableByName(String desc,String objects,int waittime)
	{
		for (int i = 0; i < 4; i++) {
			try
			{
				WebDriverWait wait=new WebDriverWait(bc.getDriver(), waittime);
				//wait.until(ExpectedConditions.elementToBeClickable(By.name(objects)));
				wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.name(objects)));
				//Thread.sleep(1000);

			}
			catch (StaleElementReferenceException b)
			{
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,objects);
				/*ExtentTestManager.getTest().log(LogStatus.FAIL,"Error:"+e);
			GlobalData.runTimeStatus=false;
			bc.captureScreenshot(GlobalData.date,objects);
			//bc.tearDown();*/
			}catch (Exception e) {
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,objects);
				ExtentTestManager.getTest().log(LogStatus.FAIL,desc +"  is not available");
				//Assert.fail();
			}
		}
		return "true";
	}

	public static String waitForElementTobeClickableByText(String desc,String objects,int waittime)
	{
		for (int i = 0; i < 4; i++) {
			try	
			{
				WebDriverWait wait=new WebDriverWait(bc.getDriver(), waittime);
				wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.linkText(objects)));
				//Thread.sleep(1000);

			}
			catch (StaleElementReferenceException b)
			{
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,objects);
				/*ExtentTestManager.getTest().log(LogStatus.FAIL,"Error:"+e);
			GlobalData.runTimeStatus=false;
			bc.captureScreenshot(GlobalData.date,objects);
			//bc.tearDown();*/
			}catch (Exception e) {
				GlobalData.runTimeStatus=false;
				bc.captureScreenshot(GlobalData.date,objects);
				ExtentTestManager.getTest().log(LogStatus.FAIL,desc +"  is not available");
				//Assert.fail();
			}
		}
		return "true";
	}

	public static String inputTextByActionusingXpath(String objects,String description,String testdata)
	{
		try
		{

			//Thread.sleep(2000);
			int count=bc.getDriver().findElements(By.xpath(objects)).size();

			if (testdata!="") 
			{
				if (!testdata.contains("Nil"))
				{
					if (count==1)
					{
						try{
							waitByXpath(description, objects, 30);
							//Thread.sleep(2000);
							Actions builder=new Actions(bc.getDriver());
							WebElement mEle=bc.getDriver().findElement(By.xpath(objects));
							((JavascriptExecutor) bc.getDriver()).executeScript("arguments[0].scrollIntoView(true);", mEle);
							Thread.sleep(1000);
							/*builder.doubleClick(mEle).perform();
							Thread.sleep(3000);*/
							builder.keyDown(mEle, Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
							Thread.sleep(1000);
							String numkeys=testdata;
							builder.sendKeys(Keys.DELETE).build().perform();
							Thread.sleep(1000);
							builder.sendKeys(mEle,numkeys).build().perform();
							Thread.sleep(1000);
							builder.sendKeys(Keys.TAB).build().perform();
							Thread.sleep(2000);

						}catch(UnhandledAlertException e){
							ExtentTestManager.getTest().log(LogStatus.FAIL,"Error:"+e);
							GlobalData.runTimeStatus=false;
							//captureScreenshot(GlobalData.date,objects);
							//bc.tearDown();
						}

						ExtentTestManager.getTest().log(LogStatus.PASS,testdata +" is entered successfully in "+description+"field");
						return "true";
					}
					else
					{
						//GlobalData.extentTest.log(LogStatus.FAIL,testdata +" is not entered successfully in "+description+"field");
						ExtentTestManager.getTest().log(LogStatus.FAIL,testdata +" is not entered successfully in "+description+"field");
						bc.captureScreenshot(GlobalData.date,description);
						Assert.fail();
					}

				}
			}

		}catch(Exception e)
		{
			bc.captureScreenshot(GlobalData.date,description);
			ExtentTestManager.getTest().log(LogStatus.FAIL,e.getMessage());
			Assert.fail();
		}
		return "true";
	}



	public static String responseLoadingByXpath(String objects,String classCode,int loopTimes)
	{
		try
		{
			//waitForElementTobeClickableByXpath(objects, 30);
			Thread.sleep(2000);
			int count=bc.getDriver().findElements(By.xpath(objects)).size();


			if (count==1)
			{
				try{ 
					//bc.getDriver().findElement(By.xpath(objects)).click();
					Actions ans=new Actions(bc.getDriver());
					WebElement ele=bc.getDriver().findElement(By.xpath(objects));
					ans.click(ele).build().perform();
					Thread.sleep(1000);
					ans.sendKeys(ele, Keys.SPACE).build().perform();
					Thread.sleep(2000);
					for (int i = 0; i < 20; i++) {
						try
						{
							System.out.println(i);
							String AfterloadingClassText=bc.getDriver().findElement(By.xpath(objects)).getAttribute("class");
							System.out.println("After"+AfterloadingClassText);
							//bc.getDriver().findElement(By.xpath(objects)).sendKeys(Keys.TAB);
							if (AfterloadingClassText.contains(classCode)) 
							{
								System.out.println("inside");
								Thread.sleep(2000);
							}
							else
							{

								break;
							}
						}catch(StaleElementReferenceException e)
						{

						}

					}

				}catch(UnhandledAlertException e){
					bc.captureScreenshot(GlobalData.date,objects);
					Assert.fail();
				}


			}
			else
			{
				ExtentTestManager.getTest().log(LogStatus.FAIL,objects +" is not avaialble ");
				bc.captureScreenshot(GlobalData.date,"");
				Assert.fail();
			}




		}catch(Exception e)
		{
			Assert.fail();
		}
		return "true";
	}



}


