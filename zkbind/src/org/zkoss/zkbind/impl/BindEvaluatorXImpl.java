/* SimpleEvaluatorX.java

	Purpose:
		
	Description:
		
	History:
		Jul 29, 2011 9:30:30 AM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import org.zkoss.xel.Expression;
import org.zkoss.xel.ExpressionX;
import org.zkoss.xel.FunctionMapper;
import org.zkoss.xel.VariableResolver;
import org.zkoss.xel.XelContext;
import org.zkoss.xel.XelException;
import org.zkoss.xel.util.SimpleXelContext;
import org.zkoss.xel.ValueReference;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.xel.impl.SimpleEvaluator;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.Binding;

/**
 * A simple implementation of {@link BindEvaluatorX}.
 * 
 * @author henri
 *
 */
public class BindEvaluatorXImpl extends SimpleEvaluator implements BindEvaluatorX {
	public BindEvaluatorXImpl(FunctionMapper mapper, Class expfcls) {
		super(mapper, expfcls);
	}

	public Object getValue(BindContext ctx, Component comp, ExpressionX expression)
	throws XelException {
		return expression.evaluate(newXelContext(ctx, comp));
	}

	public void setValue(BindContext ctx, Component comp, ExpressionX expression, Object value)
	throws XelException {
		expression.setValue(newXelContext(ctx, comp), value);
	}

	public ExpressionX parseExpressionX(BindContext ctx, String expression, Class expectedType)
	throws XelException {
		return (ExpressionX) getExpressionFactory()
			.parseExpression(newXelContext(ctx, null), "${"+expression+"}", expectedType);
	}
	
	public Class getType(BindContext ctx, Component comp, ExpressionX expression)
	throws XelException {
		return expression.getType(newXelContext(ctx, comp));
	}
	
	public ValueReference getValueReference(BindContext ctx, Component comp, ExpressionX expression)
	throws XelException {
		return expression.getValueReference(newXelContext(ctx, comp));
	}

	//utility to create an XelContext associated to the refrence (could be Component or Page)
	protected XelContext newXelContext(BindContext ctx, Component comp) {
		final XelContext xelc = super.newXelContext(comp);
		xelc.setAttribute(BinderImpl.BINDCTX, ctx);
		if (ctx != null) {
			xelc.setAttribute(BinderImpl.BINDING, ctx.getBinding());
		}
		return xelc;
	}
}
