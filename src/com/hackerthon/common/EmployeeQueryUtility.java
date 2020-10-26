package com.hackerthon.common;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class EmployeeQueryUtility extends BaseUtility {

	/**
	 * Get the employee query
	 * @param id of the Employee
	 * @return query
	 * @throws Exception null exception(in case of the XML loading failed)
	 */
	public static String getEmployeeQueryById(String id) throws Exception {
		NodeList nodeList;
		Element element = null;
		nodeList = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new File(Constants.SRC_COM_HACKERTHON_CONFIG_EMPLOYEE_QUERY_XML))
				.getElementsByTagName("query");
		for (int x = 0; x < nodeList.getLength(); x++) {
			element = (Element) nodeList.item(x);
			if (element.getAttribute("id").equals(id))
				break;
		}

		return element.getTextContent().trim();
	}
}
