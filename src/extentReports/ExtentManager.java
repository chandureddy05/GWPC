package extentReports;
//package utils.ExtentReports;
 
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

import commonFunctionLibrary.GlobalData;
 
//OB: ExtentReports extent instance created here. That instance can be reachable by getReporter() method.
 
public class ExtentManager {
 
    private static ExtentReports extent;
 
    public synchronized static ExtentReports getReporter(){
        if(extent == null){
            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
           
            //extent = new ExtentReports(workingDir+"\\ExtentReports\\ExtentReportResults.html", true);
            //SimpleDateFormat sdfdate=new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat sdfdate=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date now=new Date();
			String date1=sdfdate.format(now);
            System.out.println(date1);
            GlobalData.timeStamp=date1;
            extent = new ExtentReports(workingDir+"\\ExtentReports\\"+date1+"\\ExtentReportResults.html", true);
            //extent = new ExtentReports(workingDir+"\\ExtentReports\\ExtentReportResults_"+date1+".html", true);
        }
        return extent;
    }
}
