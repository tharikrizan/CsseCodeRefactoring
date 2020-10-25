package com.hackerthon.common;

import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.w3c.dom.Document;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

public class EmployeeTransformationUtility extends BaseUtility {

	private static final ArrayList<Map<String, String>> employeesParsedInfo = new ArrayList<Map<String, String>>();
	public static final String COUNT_EMPLOYEES_EMPLOYEE_EXPRESSION = "count(//Employees/Employee)";

	private static Map<String, String> employeeInfo = null;

	public static void requestTransform() throws Exception {

		Source employeeRequestSource = new StreamSource(new File(Constants.SRC_COM_HACKERTHON_CONFIG_EMPLOYEE_REQUEST_XML));
		Source employeeModifiedSource = new StreamSource(new File(Constants.SRC_COM_HACKERTHON_CONFIG_EMPLOYEE_MODIFIED_XSL));
		Result employeeStreamResult = new StreamResult(new File(Constants.SRC_COM_HACKERTHON_CONFIG_EMPLOYEE_RESPONSE_XML));
		TransformerFactory.newInstance().newTransformer(employeeModifiedSource).transform(employeeRequestSource, employeeStreamResult);
	}

	public static ArrayList<Map<String, String>> getAllXMLPaths() throws Exception {

		Document employeeResponseDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(Constants.SRC_COM_HACKERTHON_CONFIG_EMPLOYEE_RESPONSE_XML1);
		XPath xPath = XPathFactory.newInstance().newXPath();
		int n = Integer.parseInt((String) xPath.compile(COUNT_EMPLOYEES_EMPLOYEE_EXPRESSION).evaluate(employeeResponseDocument, XPathConstants.STRING));
		for (int i = 1; i <= n; i++) {
			employeeInfo = new HashMap<String, String>();
			employeeInfo.put(Constants.XPATH_EMPLOYEE_ID_KEY, (String) xPath.compile(Constants.EMPLOYEES_EMPLOYEE_ROOT + i + "]/EmployeeID/text()")
					.evaluate(employeeResponseDocument, XPathConstants.STRING));
			employeeInfo.put(Constants.XPATH_EMPLOYEE_NAME_KEY, (String) xPath.compile(Constants.EMPLOYEES_EMPLOYEE_ROOT + i + "]/EmployeeFullName/text()")
					.evaluate(employeeResponseDocument, XPathConstants.STRING));
			employeeInfo.put(Constants.XPATH_EMPLOYEE_ADDRESS_KEY,
					(String) xPath.compile(Constants.EMPLOYEES_EMPLOYEE_ROOT + i + "]/EmployeeFullAddress/text()").evaluate(employeeResponseDocument,
							XPathConstants.STRING));
			employeeInfo.put(Constants.XPATH_FACULTY_NAME_KEY, (String) xPath.compile(Constants.EMPLOYEES_EMPLOYEE_ROOT + i + "]/FacultyName/text()")
					.evaluate(employeeResponseDocument, XPathConstants.STRING));
			employeeInfo.put(Constants.XPATH_DEPARTMENT_KEY, (String) xPath.compile(Constants.EMPLOYEES_EMPLOYEE_ROOT + i + "]/Department/text()")
					.evaluate(employeeResponseDocument, XPathConstants.STRING));
			employeeInfo.put(Constants.XPATH_DESIGNATION_KEY, (String) xPath.compile(Constants.EMPLOYEES_EMPLOYEE_ROOT + i + "]/Designation/text()")
					.evaluate(employeeResponseDocument, XPathConstants.STRING));
			employeesParsedInfo.add(employeeInfo);
		}
		return employeesParsedInfo;
	}
}
