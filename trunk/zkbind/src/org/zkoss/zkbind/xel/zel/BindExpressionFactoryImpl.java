/* BindExpressionFactoryImpl.java

	Purpose:
		
	Description:
		
	History:
		Aug 15, 2011 11:07:43 AM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.xel.zel;

import org.zkoss.zel.ELContext;
import org.zkoss.zel.impl.ExpressionFactoryImpl;
import org.zkoss.zel.impl.lang.ExpressionBuilder;
import org.zkoss.zkbind.xel.BindXelFactory;
/**
 * Handle dot series script.
 * @author henri
 * @see BindExpressionBuilder
 * @see BindXelFactory
 */
public class BindExpressionFactoryImpl extends ExpressionFactoryImpl {
    //20110815, Henri Chen: allow override node visiting (see BindExpressionBuilder#visit)
    protected ExpressionBuilder newExpressionBuilder(String expression, ELContext context) {
    	return new BindExpressionBuilder(expression, context);
    }
}
