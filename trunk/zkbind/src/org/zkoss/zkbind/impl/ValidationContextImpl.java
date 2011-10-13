/* ValidationContextImpl.java

	Purpose:
		
	Description:
		
	History:
		2011/9/29 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.zkbind.impl;

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

	private boolean _valid = true;//default validation result is true
	private String _command;
	private Property _property; //main property
	private Set<Property> _properties; //related properties
	private BindContext _ctx;
	
	private Map<Property,String> messages;
	
	public ValidationContextImpl(String command, Property property,Set<Property> properties, BindContext ctx,boolean valid){
		this._command = command;
		this._property = property;
		this._properties = properties;
		this._ctx = ctx;
		this._valid = valid;
	}
	
	
	public BindContext getBindContext() {
		return _ctx;
	}

	public String getCommand() {
		return _command;
	}

	public Set<Property> getProperties() {
		return _properties;
	}

	public Property getProperty() {
		return _property;
	}
	

	public Object getPropertyValue() {
		return _property==null?null:_property.getValue();
	}

	public Object getPropertyValue(String name) {
		if(_property!=null && _property.getProperty().equals(name)){
			return _property.getValue();
		}
		for(Property p:getProperties()){
			if(p.getProperty().equals(name)){
				return p.getValue();
			}	
		}
		return null;
	}

	public boolean isValid() {
		return _valid;
	}

//	public void setValid(boolean valid) {
//		this.valid = valid;
//	}
	
	public void setFail(){
		this._valid = false;
	}

	//TODO
	public void setMessage(Property property, String message) {
		if(messages==null){
			messages = new HashMap<Property, String>();
		}
		messages.put(property, message);
	}
	
	//TODO
	public String getMessage(Property property){
		return messages==null?null:messages.get(property);
	}


}