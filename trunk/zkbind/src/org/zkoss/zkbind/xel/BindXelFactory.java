/* BindXelFactory.java

	Purpose:
		
	Description:
		
	History:
		Aug 10, 2011 5:56:08 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.xel;

import org.zkoss.xel.Expression;
import org.zkoss.xel.XelContext;
import org.zkoss.xel.XelException;
import org.zkoss.xel.zel.ELXelExpression;
import org.zkoss.xel.zel.ELFactory;
import org.zkoss.zel.ELContext;
import org.zkoss.zkbind.xel.zel.BindELContext;

/**
 * Xel Factory for Binding.
 * @author henrichen
 */
public class BindXelFactory extends ELFactory {
	public Expression parseExpression(XelContext xelc, String expression, Class expectedType)
	throws XelException {
		return new BindXelExpression(
			_expf.createValueExpression(
				newELContext(xelc), expression, expectedType));
	}
	
	protected ELContext newELContext(XelContext xelc) {
		return new BindELContext(xelc);
	}
	
    //20110815, Henri Chen: allow overriding node visiting (see BindExpressionFactory#newExpressionBuilder)
	protected org.zkoss.zel.ExpressionFactory newExpressionFactory() {
		return new org.zkoss.zkbind.xel.zel.BindExpressionFactoryImpl();
	}
}
