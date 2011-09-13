/* PropertyImpl.java

	Purpose:
		
	Description:
		
	History:
		Aug 12, 2011 12:45:49 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.lang.reflect.Method;

import org.zkoss.zel.impl.parser.Node;
import org.zkoss.zkbind.Property;

/**
 * Implementation of a property.
 * @author henri
 *
 */
public class PropertyImpl implements Property {
	private final Object _base;
	private final String _property;
	private final Object _value;
	public PropertyImpl(Object base, String property, Object value) {
		_base = base;
		_property = property;
		_value = value;
	}

	public Object getBase() {
		return _base;
	}
	
	public Object getValue() {
		return _value;
	}

	public String getProperty() {
		return _property;
	}
}
