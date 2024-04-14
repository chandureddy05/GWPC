package Scripts;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;



import commonFunctionLibrary.GlobalData;

public class Check {
	@Test(priority=0)
	public void launchBrowser() {
		System.out.println("test");
		try
		{
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
			String command = "cmd.exe /c start "+"chrome.exe --remote-debugging-port=5555 --use-data-dir=\"c:\\AutomationProfile\"";
			Runtime.getRuntime().exec(command);

		}catch(Exception e)
		{
			e.printStackTrace();
			//return "Fail";
		}
		//return "Pass";
	}


	@Test(priority=1)
	public void initializeDriver()
	{
		try
		{
			//System.setProperty("webdriver.chrome.driver", "C:\\Users\\XCGPXB2\\workspace\\Zurich\\ExternalJars\\chromedriver.exe");
			//String cwd = System.getProperty("user.dir");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\ExternalJars\\chromedriver.exe");

			ChromeOptions chrOption=new ChromeOptions();
			chrOption.setExperimentalOption("debuggerAddress", "127.0.0.1:5555");

			//GlobalData.driver=new ChromeDriver(chrOption);
			/*DesiredCapabilities autoItCapabilities = new DesiredCapabilities();
			autoItCapabilities.setCapability("browserName", "AutoIt");
			GlobalData.autoitDriver=new RemoteWebDriver(new URL("http://localhost:4723/wd/hub" ),autoItCapabilities);
			System.out.println(GlobalData.autoitDriver.getCurrentUrl());*/
			//GlobalData.driver.manage().window().maximize();
			//Thread.sleep(3000);
			//GlobalData.driver.navigate().refresh();


		}catch(Exception e)
		{
			e.printStackTrace();
			//return "Fail";
		}
		//return "Pass";

	}


	
}
