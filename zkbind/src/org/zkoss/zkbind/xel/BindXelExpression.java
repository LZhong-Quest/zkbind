/* BindXelExpression.java

	Purpose:
		
	Description:
		
	History:
		Aug 10, 2011 5:42:27 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.xel;

import org.zkoss.xel.XelContext;
import org.zkoss.xel.zel.ELXelExpression;
import org.zkoss.zel.ELContext;
import org.zkoss.zel.ValueExpression;
import org.zkoss.zkbind.xel.zel.BindELContext;

/**
 * @author henri
 *
 */
public class BindXelExpression extends ELXelExpression {

	BindXelExpression(ValueExpression expr) {
		super(expr);
	}
	protected ELContext newELContext(XelContext xelc) {
		return new BindELContext(xelc);
	}
}
