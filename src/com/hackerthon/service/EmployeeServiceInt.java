package com.hackerthon.service;

import com.hackerthon.common.BaseUtility;
import com.hackerthon.common.EmployeeTransformationUtility;
import java.util.logging.Logger;
/**
 * Abstract of Employee service
 * @Author WD-04
 * @version 1.0
 */
public abstract class EmployeeServiceInt extends BaseUtility {

    final public void displayEmployeesTableTemplateMethod(){

        final Logger log = Logger.getLogger(EmployeeServiceInt.class.getName());
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
    public abstract  void employeesFromXML() ;
    public abstract  void createEmployeeTable() ;
    public abstract  void addEmployees() ;
    public abstract  void displayEmployees() ;
}
