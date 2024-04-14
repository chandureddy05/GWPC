package commonFunctionLibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.relevantcodes.extentreports.LogStatus;

import extentReports.ExtentTestManager;

public class Excel {



	public static int Exceldata(String DataFilePath,String ColumnName,String DataSheetName,int callcount,String TestCaseName,int datacounter)
	{
		String key="";
		String Value="";
		String tcid="";
		int reqrow=0,matchcount=0;

		try
		{
			FileInputStream inputworkbook=new FileInputStream(DataFilePath);
			XSSFWorkbook Dataworkbook;
			GlobalData.DataFilePath=DataFilePath;
			Dataworkbook=new XSSFWorkbook(inputworkbook);

			XSSFSheet DataSheet=Dataworkbook.getSheet(DataSheetName);
			int Datarowcount=DataSheet.getLastRowNum();
			XSSFRow r=DataSheet.getRow(1);
			int DataColCount=r.getLastCellNum();
			if (datacounter==0) {
				for(int h=0;h<=Datarowcount;h++)
				{
					tcid=DataSheet.getRow(h).getCell(1).getStringCellValue();
					if(tcid.equalsIgnoreCase(TestCaseName))
					{
						datacounter++;
					}
				}
			}
			for(int y=0;y<=Datarowcount;y++)
			{
				tcid=DataSheet.getRow(y).getCell(1).getStringCellValue();
				if(tcid.equalsIgnoreCase(TestCaseName)&&matchcount==callcount)
				{
					reqrow=y;
				}
				if(tcid.equalsIgnoreCase(TestCaseName))
					matchcount++;
			}
			for(int u=0;u<DataColCount;u++)
			{
				key=DataSheet.getRow(0).getCell(u).getStringCellValue();
				Value=DataSheet.getRow(reqrow).getCell(u).getStringCellValue();
				GlobalData.DataElements.put("TCRowNo", Integer.toString(reqrow));
				GlobalData.DataElements.put(key, Value);
			}

		}catch(InvalidFormatException e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception in Exceladd"+e);
			System.out.println("Exception is="+e);
			e.printStackTrace();
		}catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception in Exceladd"+e);
			System.out.println("Exception is="+e);
			e.printStackTrace();
		}
		return datacounter;

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
					GlobalData.extentTest.log(LogStatus.INFO,"Excel Formula Evaluator Exception"+e);
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
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception in Exceladd "+e);
			System.out.println("Exception is="+e);
			return "fail";

		}

	}


	public static synchronized int ExceldataWrite(String DataFilePath,String ColumnName,String DataSheetName,int callcount,String TestCaseName,String Quote_policyNumber)
	{
		String key="";
		String Value="";
		String tcid="";
		String columnHeader="";
		int reqrow=0,matchcount=0,reqColumn=0;;

		try
		{
			//FileInputStream inputworkbook=new FileInputStream(".\\TestData\\"+DataFilePath);
			FileInputStream inputworkbook=new FileInputStream(DataFilePath);
			/*XSSFWorkbook Dataworkbook;*/
			GlobalData.DataFilePath=DataFilePath;
			GlobalData.Dataworkbook=new XSSFWorkbook(inputworkbook);

			XSSFSheet DataSheet=GlobalData.Dataworkbook.getSheet(DataSheetName);
			int Datarowcount=DataSheet.getLastRowNum();
			XSSFRow r=DataSheet.getRow(1);
			int DataColCount=r.getLastCellNum();
			/*if (datacounter==0) {
				for(int h=0;h<=Datarowcount;h++)
				{
					tcid=DataSheet.getRow(h).getCell(1).getStringCellValue();
					if(tcid.equalsIgnoreCase(TestCaseName))
					{
						datacounter++;
					}
				}
			}*/
			for(int y=0;y<=Datarowcount;y++)
			{
				tcid=DataSheet.getRow(y).getCell(1).getStringCellValue();
				if(tcid.equalsIgnoreCase(TestCaseName)&&matchcount==callcount)
				{
					reqrow=y;
				}

			}
			for(int u=0;u<DataColCount;u++)
			{
				columnHeader=DataSheet.getRow(0).getCell(u).getStringCellValue();
				if(columnHeader.equalsIgnoreCase(ColumnName)&&matchcount==callcount)
				{
					reqColumn=u;
				}
			}

			if (reqrow>=1 && reqColumn>=1)
			{
				DataSheet.getRow(reqrow).getCell(reqColumn).setCellValue(Quote_policyNumber);
				inputworkbook.close();
				try {
					FileOutputStream out =new FileOutputStream(GlobalData.DataFilePath);
					GlobalData.Dataworkbook.write(out);
					out.close();
					System.out.println("Excel written successfully..");

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}catch(InvalidFormatException e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception in Exceladd"+e);
			System.out.println("Exception is="+e);
			e.printStackTrace();
		}catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception in Exceladd"+e);
			System.out.println("Exception is="+e);
			e.printStackTrace();
		}
		return 1;

	}


}
