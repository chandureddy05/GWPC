package commonFunctionLibrary;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.LogStatus;

import extentReports.ExtentTestManager;
//import listeners.TestListener;
import Scripts.*;

public class TestReports extends TestListener {
	

	/*public static ExtentReports extent=null;
	public static ExtentTest extentTest=null;
	public static String extentReportFile="";
	public static String extentReportImage="";*/
	public void setreportfilepath(String date)
	{
		GlobalData.extentReportFile=".\\Reports\\"+date+"\\extentReportFile.html";
		GlobalData.extentReportImage=".\\extentReportImage.jpg";
	}
	
	/*public void captureScreenshot(String date,String Desc)
	{
		try{
			//int flag=0;
			SimpleDateFormat sdfdate=new SimpleDateFormat("yyyyMMddHHmmss");
			Date now=new Date();
			String date1=sdfdate.format(now);
			BufferedImage image=new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image,"jpg",new File(".\\Reports\\"+date+"\\screenshot"+date1+".jpg"));
			GlobalData.extentTest.log(LogStatus.INFO,"Image",Desc+GlobalData.extentTest.addScreenCapture(".\\screenshot"+date1+".jpg"));
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("error while capturing the screenshot");
		}
	}*/
	

	
	
	

}
