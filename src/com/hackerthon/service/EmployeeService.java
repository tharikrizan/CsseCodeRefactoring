package com.hackerthon.service;

import com.hackerthon.common.Constants;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

import com.hackerthon.common.EmployeeTransformationUtility;

import java.util.logging.Level;

import com.hackerthon.common.EmployeeQueryUtility;
import com.hackerthon.model.Employee;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.Map;


/**
 * Implementation of Employee service
 * @Author WD-04
 * @version 1.0
 */
public class EmployeeService extends EmployeeServiceBase {

	public static final int PARAMETER_INDEX_1 = 1;
	public static final int PARAMETER_INDEX_2 = 2;
	public static final int PARAMETER_INDEX_3 = 3;
	public static final int PARAMETER_INDEX_4 = 4;
	public static final int PARAMETER_INDEX_5 = 5;
	public static final int PARAMETER_INDEX_6 = 6;

	private final ArrayList<Employee> employeeList = new ArrayList<Employee>();
	private static Connection connection;
	private static Statement statement;
	private PreparedStatement preparedStatement;
	private static final  Logger log = Logger.getLogger(EmployeeService.class.getName());

	private static  EmployeeService employeeServiceInstance = null ;

	private EmployeeService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		} 
	}
	public static EmployeeService getInstance(){
		if(employeeServiceInstance==null){
			synchronized (EmployeeService.class){
				if(employeeServiceInstance==null){
					employeeServiceInstance = new EmployeeService();
				}
			}

		}
		return employeeServiceInstance;
	}

/*
 * The method employeesFromXML() mutates the employeeList ArrayList that has
 * the details of employees as map elements. Read each map element and
 * assign to Employee Object
 */
	public void employeesFromXML() {

		try {



			for (Map<String, String> xmlParser : EmployeeTransformationUtility.getAllXMLPaths()) {

				Employee employee = new Employee();
				employee.setEmployeeId(xmlParser.get(Constants.XPATH_EMPLOYEE_ID_KEY));
				employee.setFullName(xmlParser.get(Constants.XPATH_EMPLOYEE_NAME_KEY));
				employee.setAddress(xmlParser.get(Constants.XPATH_EMPLOYEE_ADDRESS_KEY));
				employee.setFacultyName(xmlParser.get(Constants.XPATH_FACULTY_NAME_KEY));
				employee.setDepartment(xmlParser.get(Constants.XPATH_DEPARTMENT_KEY));
				employee.setDesignation(xmlParser.get(Constants.XPATH_DESIGNATION_KEY));
				employeeList.add(employee);
				System.out.println(employee.toString() + "\n");
			}
		} catch (NumberFormatException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (XPathExpressionException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		catch (SAXException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (NullPointerException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}


	/*
	 * The method createEmployeeTable() Give the path to create the employee details table
	 */
	public void createEmployeeTable() {
		try {
			statement = connection.createStatement();
			statement.executeUpdate(EmployeeQueryUtility.getEmployeeQueryById("q2"));
			statement.executeUpdate(EmployeeQueryUtility.getEmployeeQueryById("q1"));
		}catch (NumberFormatException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (XPathExpressionException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		catch (SAXException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (NullPointerException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/*
	 * The method addEmployees() Adds the employee details to the table created in createEmployeeTable
	 * Read each map employee element from employee list and
	 * assign to Employee Object
	 */
	public void addEmployees() {
		try {
			preparedStatement = connection.prepareStatement(EmployeeQueryUtility.getEmployeeQueryById("q3"));
			connection.setAutoCommit(false);

			for(Employee employee : employeeList){

				preparedStatement.setString(PARAMETER_INDEX_1, employee.getEmployeeId());
				preparedStatement.setString(PARAMETER_INDEX_2, employee.getFullName());
				preparedStatement.setString(PARAMETER_INDEX_3, employee.getAddress());
				preparedStatement.setString(PARAMETER_INDEX_4, employee.getFacultyName());
				preparedStatement.setString(PARAMETER_INDEX_5, employee.getDepartment());
				preparedStatement.setString(PARAMETER_INDEX_6, employee.getDesignation());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			connection.commit();
		}catch (NumberFormatException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (XPathExpressionException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		catch (SAXException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (NullPointerException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/*
	 * The method getEmployeeById() gets the employee by its ID
	 */
	public void getEmployeeById(String eid) {

		Employee employee = new Employee();
		try {
			preparedStatement = connection.prepareStatement(EmployeeQueryUtility.getEmployeeQueryById("q4"));
			preparedStatement.setString(1, eid);
			ResultSet resultOfQuery = preparedStatement.executeQuery();
			while (resultOfQuery.next()) {
				employee.setEmployeeId(resultOfQuery.getString(1));
				employee.setFullName(resultOfQuery.getString(2));
				employee.setAddress(resultOfQuery.getString(3));
				employee.setFacultyName(resultOfQuery.getString(4));
				employee.setDepartment(resultOfQuery.getString(5));
				employee.setDesignation(resultOfQuery.getString(6));
			}
			ArrayList<Employee> employeeList = new ArrayList<Employee>();
			employeeList.add(employee);
			printEmployeesOutPut(employeeList);
		} catch (NumberFormatException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (XPathExpressionException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		catch (SAXException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (NullPointerException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/*
	 * The method deleteEmployee() deletes the employee by its ID
	 */
	public void deleteEmployee(String eid) {

		try {
			preparedStatement = connection.prepareStatement(EmployeeQueryUtility.getEmployeeQueryById("q6"));
			preparedStatement.setString(1, eid);
			preparedStatement.executeUpdate();
		}catch (NumberFormatException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (XPathExpressionException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		catch (SAXException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (NullPointerException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}


	/*
	 * The method displayEmployees() displays the employees in the emplyeeList arraylist
	 */
	public void displayEmployees() {

		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		try {
			preparedStatement = connection.prepareStatement(EmployeeQueryUtility.getEmployeeQueryById("q5"));
			ResultSet r = preparedStatement.executeQuery();
			while (r.next()) {
				Employee employee = new Employee();
				employee.setEmployeeId(r.getString(1));
				employee.setFullName(r.getString(2));
				employee.setAddress(r.getString(3));
				employee.setFacultyName(r.getString(4));
				employee.setDepartment(r.getString(5));
				employee.setDesignation(r.getString(6));
				employeeList.add(employee);
			}
		}catch (NumberFormatException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (XPathExpressionException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		catch (SAXException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (NullPointerException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		printEmployeesOutPut(employeeList);
	}

	/*
	 * The method printEmployeesOutPut() is used by displayEmployees()  to display the employees in a specific format
	 */
	public void printEmployeesOutPut(ArrayList<Employee> l){

		log.info("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		log.info("================================================================================================================");
		for(int i = 0; i < l.size(); i++){
			Employee employee = l.get(i);
			log.info(employee.getEmployeeId() + "\t" + employee.getFullName() + "\t\t"
					+ employee.getAddress() + "\t" + employee.getFacultyName() + "\t" + employee.getDepartment() + "\t"
					+ employee.getDesignation() + "\n");
			log.info("----------------------------------------------------------------------------------------------------------------");
		}

	}
}
