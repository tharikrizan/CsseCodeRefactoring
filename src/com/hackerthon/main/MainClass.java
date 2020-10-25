package com.hackerthon.main;


import com.hackerthon.service.EmployeeService;

public class MainClass {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		EmployeeService employeeService = EmployeeService.getInstance();

		employeeService.displayEmployeesTableTemplateMethod();


	}
}
