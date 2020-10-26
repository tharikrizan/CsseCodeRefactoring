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

	/**
	 * Local xPath constants
	 */
	public static final String COUNT_EMPLOYEES_EMPLOYEE_EXPRESSION = "count(//Employees/Employee)";
	public static final String EMPLOYEE_ID = "/EmployeeID";
	public static final String EMPLOYEE_FULL_NAME = "/EmployeeFullName";
	public static final String EMPLOYEE_FACULTY_NAME = "/FacultyName";
	public static final String EMPLOYEE_DEPARTMENT = "/Department";
	public static final String EMPLOYEE_DESIGNATION = "/Designation";
	public static final String EMPLOYEE_FULL_ADDRESS = "/EmployeeFullAddress";

	private static final ArrayList<Map<String, String>> employeesParsedInfo = new ArrayList<Map<String, String>>();
	private static Map<String, String> employeeInfo = null;

	/**
	 * Generate new instance of the Transformer with Employee as the source
	 * @throws Exception Null Exception if the file paths are wrong or not found
	 */
	public static void requestTransform() throws Exception {

		Source employeeRequestSource = new StreamSource(new File(Constants.SRC_COM_HACKERTHON_CONFIG_EMPLOYEE_REQUEST_XML));
		Source employeeModifiedSource = new StreamSource(new File(Constants.SRC_COM_HACKERTHON_CONFIG_EMPLOYEE_MODIFIED_XSL));
		Result employeeStreamResult = new StreamResult(new File(Constants.SRC_COM_HACKERTHON_CONFIG_EMPLOYEE_RESPONSE_XML));
		TransformerFactory.newInstance().newTransformer(employeeModifiedSource).transform(employeeRequestSource, employeeStreamResult);
	}

	/**
	 * Create an array of Map<key, value> of all the XML Paths for employees
	 * @return ArrayList<Map<String, String>> where each Map's key referring to employee field name and value referring to its values
	 * @throws Exception NullException if the files not found or wrong paths are given
	 */
	public static ArrayList<Map<String, String>> getAllXMLPaths() throws Exception {

		Document employeeResponseDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(Constants.SRC_COM_HACKERTHON_CONFIG_EMPLOYEE_RESPONSE_XML1);

		XPath xPath = XPathFactory.newInstance().newXPath();
		int totalEmployeesCount = Integer.parseInt((String) xPath.compile(COUNT_EMPLOYEES_EMPLOYEE_EXPRESSION).evaluate(employeeResponseDocument, XPathConstants.STRING));

		for (int i = 1; i <= totalEmployeesCount; i++) {
			employeeInfo = new HashMap<String, String>();
			employeeInfo.put(Constants.XPATH_EMPLOYEE_ID_KEY, (String) xPath.compile(concatXPathExpression(i, EMPLOYEE_ID))
					.evaluate(employeeResponseDocument, XPathConstants.STRING));
			employeeInfo.put(Constants.XPATH_EMPLOYEE_NAME_KEY, (String) xPath.compile(concatXPathExpression(i, EMPLOYEE_FULL_NAME))
					.evaluate(employeeResponseDocument, XPathConstants.STRING));
			employeeInfo.put(Constants.XPATH_EMPLOYEE_ADDRESS_KEY,
					(String) xPath.compile(concatXPathExpression(i, EMPLOYEE_FULL_ADDRESS)).evaluate(employeeResponseDocument,
							XPathConstants.STRING));
			employeeInfo.put(Constants.XPATH_FACULTY_NAME_KEY, (String) xPath.compile(concatXPathExpression(i, EMPLOYEE_FACULTY_NAME))
					.evaluate(employeeResponseDocument, XPathConstants.STRING));
			employeeInfo.put(Constants.XPATH_DEPARTMENT_KEY, (String) xPath.compile(concatXPathExpression(i, EMPLOYEE_DEPARTMENT))
					.evaluate(employeeResponseDocument, XPathConstants.STRING));
			employeeInfo.put(Constants.XPATH_DESIGNATION_KEY, (String) xPath.compile(concatXPathExpression(i, EMPLOYEE_DESIGNATION))
					.evaluate(employeeResponseDocument, XPathConstants.STRING));
			employeesParsedInfo.add(employeeInfo);
		}
		return employeesParsedInfo;
	}

	/**
	 * Generate a xPath query by concatenating each employee's property xPath with the Employee Root path
	 * @param i index of the employee
	 * @param employeeXPathField X Path referring to the employee's field in question
	 * @return the concatenated xPath string
	 */
	private static String concatXPathExpression(int i, String employeeXPathField) {
		return Constants.EMPLOYEES_EMPLOYEE_ROOT + i + "]" + employeeXPathField + "/text()";
	}
}
