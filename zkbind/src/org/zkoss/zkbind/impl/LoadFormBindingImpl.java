/* LoadFormBinding.java

	Purpose:
		
	Description:
		
	History:
		Aug 9, 2011 6:25:44 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.util.Map;

import org.zkoss.xel.ExpressionX;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.Form;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.LoadBinding;
import org.zkoss.zkbind.sys.LoadFormBinding;

/**
 * Implementation of {@link LoadFormBinding}
 * @author henri
 *
 */
public class LoadFormBindingImpl extends FormBindingImpl implements	LoadFormBinding {
	private int _len;
	
	protected LoadFormBindingImpl(Binder binder, Component comp, Form form, String loadExpr, Map args) {
		super(binder, comp, form, loadExpr, args);
	}

	public void load(BindContext ctx) {
		final Binder binder = getBinder();
		final BindEvaluatorX eval = binder.getEvaluatorX();
		final Component comp = ctx.getComponent();
		final Form form = getFormBean();
		for (String field : form.getLoadFieldNames()) {
			final ExpressionX expr = getFieldExpression(eval, field);
			if (expr != null) {
				final Object value = eval.getValue(ctx, comp, expr);
				form.setField(field, value);
			}
		}
		((FormImpl)form).initFields(); //initial loading, mark form as clean
		if (getCommandName() == null) { //prompt form loading, update UI
			binder.notifyChange(form, "*", null, null); //notify change of fx.*
		}
	}
	
	public void setSeriesLength(int len) {
		_len = len;
	}
	
	public int getSeriesLength() {
		return _len;
	}
}
