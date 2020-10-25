package com.hackerthon.common;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public  class BaseUtility {

	public static final Properties properties = new Properties();
	private static final Logger log = Logger.getLogger(BaseUtility.class.getName());

	public static final String CONFIG_CONFIG_PROPERTIES = "../config/config.properties";

	static {
		try {
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
