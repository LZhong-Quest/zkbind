/* StringDateConverter.java

	Purpose:
		
	Description:
		
	History:
		Jun 22, 2011 11:22:20 AM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.zkbind.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Converter;

/**
 * Converter to convert String to Date.
 * @author henri
 *
 */
public class FormatedDateConverter implements Converter {
	/**
	 * Convert Date to String.
	 * @param val date to be converted
	 * @param comp associated component
	 * @param ctx bind context for associate {@link Binding} and extra parameter (e.g. format)
	 * @return the converted String
	 */
	public Object coerceToUi(Object val, Component comp, BindContext ctx) {
		final String format = (String) ctx.getAttribute("format");
		final Date date = (Date) val;
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * Convert String to Date.
	 * @param val date in string form
	 * @param comp associated component
	 * @param ctx bind context for associate {@link Binding} and extra parameter (e.g. format)
	 * @return the converted Date
	 */
	public Object coerceToBean(Object val, Component comp, BindContext ctx) {
		final String format = (String) ctx.getAttribute("format");
		final String date = (String) val;
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			throw UiException.Aide.wrap(e);
		}
	}
}
