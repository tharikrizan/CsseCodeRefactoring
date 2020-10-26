package com.hackerthon.service;

import com.hackerthon.common.BaseUtility;
import com.hackerthon.common.EmployeeTransformationUtility;
import java.util.logging.Logger;
/**
 * Abstract of Employee service base
 * @Author WD-04
 * @version 1.0
 */
public abstract class EmployeeServiceBase extends BaseUtility {
    public abstract  void employeesFromXML();
    public abstract  void createEmployeeTable() ;
    public abstract  void addEmployees() ;
    public abstract  void displayEmployees() ;
    /**
     * This method is used for initialization of data and printing the employees table template
     * */
    final public void displayEmployeesTableTemplateMethod(){

        final Logger log = Logger.getLogger(EmployeeServiceBase.class.getName());
        try {
            EmployeeTransformationUtility.requestTransform();
            employeesFromXML();
            createEmployeeTable();
            addEmployees();
            displayEmployees();
        } catch (Exception e) {
            log.info("Error occured when executing the Template method: "+e.getMessage()+"\n");
        }

    }
}
