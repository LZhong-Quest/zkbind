/* ValidationContext.java

	Purpose:
		
	Description:
		
	History:
		2011/9/29 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.zkbind;

import java.util.Set;

/**
 * @author dennis
 * 
 */
public interface ValidationContext {

	/** 
	 * is valid 
	 * default true
	 */
	boolean isValid();

	/**
	 * set validation fail
	 * @param valid
	 */
	void setFail();

	/**
	 * get the command that trigger the validation
	 * @return the command, null if a prompt-save-binding
	 */
	String getCommand();

	/**
	 * get properties that need to be validated.
	 * you usually use this method to get value of other properties to do complex validation or
	 * form validation
	 * @return the properties.
	 */
	Set<Property> getProperties();

	/**
	 * get the main property that need to be validated. 
	 * @return the main property.
	 */
	Property getProperty();

	/**
	 * @return current bind context
	 */
	BindContext getBindContext();

	//TODO
//	/**
//	 * the error message to property
//	 * @param property the property that message will attached to
//	 * @param message the message
//	 */
//	void setMessage(Property property, String message);
}
