package TestNGXML;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Binder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testng.TestNG;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class RunTestNG {

	public static void main(String[] args) {

		// Create object of TestNG Class
		TestNG runner=new TestNG();

		// Create a list of String 
		List<String> suitefiles=new ArrayList<String>();

		//delete existing XML if exist
		try {
			Files.deleteIfExists(Paths.get("./testNG.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 



		// Add xml file which you have to execute
		generateTestNGFile();
		suitefiles.add("./testNG.xml");
		//suitefiles.add("C:\\Users\\XCGSZA6\\workspace\\TestNG\\testNgNew.xml");

		/*XLSReader suite = new XLSReader("C:\\Users\\XCGSZA6\\Desktop\\testDataForTestNG.xlsx");
		suite.getTests("select * from testcases where module='Order'");*/

		// now set xml file for execution
		runner.setTestSuites(suitefiles);

		// finally execute the runner using run method
		runner.run();
	}

	private static void generateTestNGFile() {

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document doc =dbBuilder.newDocument();
			Element rootElement = doc.createElement("suite");
			doc.appendChild((Node) rootElement);

			Attr rootNameAttribute = doc.createAttribute("name");
			rootNameAttribute.setValue("Suite");

			Attr rootParallelAttribute = doc.createAttribute("parallel");
			rootParallelAttribute.setValue("tests");

			Fillo fillo = new Fillo();
			//Connection con = fillo.getConnection("C:\\Users\\XCGSZA6\\Desktop\\testDataForTestNG.xlsx");
			Connection con = fillo.getConnection("C:/Users/Chandu Reddy/workspace/TestNG/TestCases/testCases.xlsx");
			//Connection con = fillo.getConnection("./TestCases/testCases.xlsx");
			Recordset recordSet;
			recordSet=con.executeQuery("select * from ENV");
			recordSet.next();

			Attr thrdCount = doc.createAttribute("thread-count");
			thrdCount.setValue(recordSet.getField("NoOfBrowsers"));

			((org.w3c.dom.Element) rootElement).setAttributeNode(rootNameAttribute);
			((org.w3c.dom.Element) rootElement).setAttributeNode(rootParallelAttribute);
			((org.w3c.dom.Element) rootElement).setAttributeNode(thrdCount);




			//Browser Parameter
			Element BrowserparameterElement = (Element) doc.createElement("parameter");
			rootElement.appendChild((Node) BrowserparameterElement);
			Attr browserparameterNameAttribute = doc.createAttribute("name");
			browserparameterNameAttribute.setValue("BrowserType");
			((org.w3c.dom.Element) BrowserparameterElement).setAttributeNode(browserparameterNameAttribute);

			Attr browserparametervalueAttribute = doc.createAttribute("value");
			browserparametervalueAttribute.setValue(recordSet.getField("Browser"));
			((org.w3c.dom.Element) BrowserparameterElement).setAttributeNode(browserparametervalueAttribute);

			//URL parameter
			Element URLparameterElement = (Element) doc.createElement("parameter");
			rootElement.appendChild((Node) URLparameterElement);
			Attr URLparameterNameAttribute = doc.createAttribute("name");
			URLparameterNameAttribute.setValue("envURL");
			((org.w3c.dom.Element) URLparameterElement).setAttributeNode(URLparameterNameAttribute);

			Attr URLparametervalueAttribute = doc.createAttribute("value");
			URLparametervalueAttribute.setValue(recordSet.getField("ExecutionURL"));
			((org.w3c.dom.Element) URLparameterElement).setAttributeNode(URLparametervalueAttribute);


			//Listerners
			Element listeners = doc.createElement("listeners");
			rootElement.appendChild(listeners);

			//firstListener
			Element firstListener = doc.createElement("listener");
			listeners.appendChild(firstListener);
			Attr firstListenerAttr = doc.createAttribute("class-name");
			firstListenerAttr.setValue("Scripts.TestListener");
			firstListener.setAttributeNode(firstListenerAttr);

			//SecondListener
			Element secondListener = doc.createElement("listener");
			listeners.appendChild(secondListener);
			Attr secondListenerAttr = doc.createAttribute("class-name");
			secondListenerAttr.setValue("listeners.AnnotationTransformer");
			secondListener.setAttributeNode(secondListenerAttr);



			String query = "Select * from testcases";
			recordSet = con.executeQuery(query);

			while (recordSet.next()) {
				if (recordSet.getField("Execute").equals("Y")) {
					/*Element classElement = (Element) doc.createElement("class");  
	                    Attr classNameAttribute = doc.createAttribute("name");
	                    classNameAttribute.setValue(recordSet.getField("TestCase"));
	                    ((org.w3c.dom.Element) classElement).setAttributeNode(classNameAttribute);
	                    ((Node) classesElement).appendChild((Node) classElement);*/

					Element testElement = doc.createElement("test");
					rootElement.appendChild(testElement);

					Attr testNameAttribute = doc.createAttribute("name");
					testNameAttribute.setValue(recordSet.getField("TestCase#"));
					testElement.setAttributeNode(testNameAttribute);


					//parameter for test case details
					Element parameterElement = (Element) doc.createElement("parameter");
					testElement.appendChild((Node) parameterElement);
					Attr parameterNameAttribute = doc.createAttribute("name");
					parameterNameAttribute.setValue("TestCaseID");
					((org.w3c.dom.Element) parameterElement).setAttributeNode(parameterNameAttribute);

					Attr parametervalueAttribute = doc.createAttribute("value");
					parametervalueAttribute.setValue(recordSet.getField("TestCaseID"));
					((org.w3c.dom.Element) parameterElement).setAttributeNode(parametervalueAttribute);

					//parameter for test data
					Element testdataparameterElement = (Element) doc.createElement("parameter");
					testElement.appendChild((Node) testdataparameterElement);
					Attr testdataparameterNameAttribute = doc.createAttribute("name");
					testdataparameterNameAttribute.setValue("TestDataFilePath");
					((org.w3c.dom.Element) testdataparameterElement).setAttributeNode(testdataparameterNameAttribute);

					Attr testdataparametervalueAttribute = doc.createAttribute("value");
					testdataparametervalueAttribute.setValue(recordSet.getField("Data"));
					((org.w3c.dom.Element) testdataparameterElement).setAttributeNode(testdataparametervalueAttribute);

					//classes
					Element classesElement = (Element) doc.createElement("classes");
					((Node) testElement).appendChild((Node) classesElement);
					Element classElement = (Element) doc.createElement("class");
					classesElement.appendChild((Node) classElement);

					Attr classNameAttribute = doc.createAttribute("name");
					classNameAttribute.setValue("Scripts.BusinessComponents");
					((org.w3c.dom.Element) classElement).setAttributeNode(classNameAttribute);

					//groups
					Element groupsElement = (Element) doc.createElement("groups");
					((Node) classesElement).appendChild((Node) groupsElement);

					//run
					Element runElement = (Element) doc.createElement("run");
					((Node) groupsElement).appendChild((Node) runElement);

					//include
					Element includeElement = (Element) doc.createElement("include");
					runElement.appendChild((Node) includeElement);

					Attr includeNameAttribute = doc.createAttribute("name");
					includeNameAttribute.setValue(recordSet.getField("Module"));
					includeElement.setAttributeNode(includeNameAttribute);


				}
			}
			recordSet.close();
			con.close();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			//StreamResult result = new StreamResult(new File("./testNgNew.xml"));
			StreamResult result = new StreamResult(new File("./testNG.xml"));
			transformer.transform(source, result);
			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FilloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}