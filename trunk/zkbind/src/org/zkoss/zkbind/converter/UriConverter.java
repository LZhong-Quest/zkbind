/* UriConverter.java

	Purpose:
		
	Description:
		
	History:
		Jun 23, 2011 12:18:59 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.zkbind.converter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Converter;

/**
 * Convert String into Uri
 * @author henrichen
 *
 */
public class UriConverter implements Converter {

	public Object coerceToUi(Object val, Component component, BindContext ctx) {
		final String uri = (String) val;
		final String prefix = (String) ctx.getAttribute("prefix");
		final String postfix = (String) ctx.getAttribute("postfix");
		return prefix + uri + postfix;
	}

	public Object coerceToBean(Object val, Component component, BindContext ctx) {
		// Never called
		return null;
	}

}
