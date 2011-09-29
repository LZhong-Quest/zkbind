/* ValidationContextImpl.java

	Purpose:
		
	Description:
		
	History:
		2011/9/29 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.zkbind.validator.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.ValidationContext;
/**
 * 
 * @author dennis
 *
 */
public class ValidationContextImpl implements ValidationContext{

	private boolean valid = true;
	private String command;
	private Set<Property> properties;
	private BindContext ctx;
	
	private Map<Property,String> messages;
	
	public ValidationContextImpl(String command, Set<Property> properties, BindContext ctx){
		this.command = command;
		this.properties = properties;
		this.ctx = ctx;
	}
	
	
	public BindContext getBindContext() {
		return ctx;
	}

	public String getCommand() {
		return command;
	}

	public Set<Property> getProperties() {
		return properties;
	}

	public Property getProperty() {
		return properties.iterator().next();
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public void setMessage(Property property, String message) {
		if(messages==null){
			messages = new HashMap<Property, String>();
		}
		messages.put(property, message);
	}
	
	public String getMessage(Property property){
		return messages==null?null:messages.get(property);
	}

}
