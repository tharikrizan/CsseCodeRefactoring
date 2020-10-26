package com.hackerthon.common;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the base utility class
 * access to the config and other external settings files happen here.
 * */
public  class BaseUtility {

	public static final Properties properties = new Properties();
	private static final Logger log = Logger.getLogger(BaseUtility.class.getName());
	public static final String CONFIG_CONFIG_PROPERTIES = "../config/config.properties";

	static {
		try {
			// loading the properties file into the system memory
			properties.load(EmployeeQueryUtility.class.getResourceAsStream(CONFIG_CONFIG_PROPERTIES));
		} catch (NumberFormatException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (NullPointerException e) {
			log.log(Level.SEVERE, e.getMessage());
		}catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

}
