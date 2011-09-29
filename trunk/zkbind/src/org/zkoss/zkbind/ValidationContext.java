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
	 * is valid of current validation, 
	 * default false
	 */
	boolean isValid();

	/**
	 * set valid result of this context
	 * @param valid
	 */
	void setValid(boolean valid);

	/**
	 * get the command that trigger the validation
	 * @return the command, null if a prompt-save-binding
	 */
	String getCommand();

	/**
	 * get the properties that need to be validated.
	 * @return the properties.
	 */
	Set<Property> getProperties();

	/**
	 * get the first property that need to be validated. 
	 * @return the first property.
	 */
	Property getProperty();

	/**
	 * @return current bind context
	 */
	BindContext getBindContext();

	/**
	 * the error message to property
	 * @param property the property that message will attached to
	 * @param message the message
	 */
	void setMessage(Property property, String message);
}
