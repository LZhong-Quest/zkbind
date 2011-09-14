/* SaveFormBindingImpl.java

	Purpose:
		
	Description:
		
	History:
		Aug 9, 2011 6:30:34 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.zkoss.xel.ExpressionX;
import org.zkoss.xel.ValueReference;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.Form;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.SaveFormBinding;

/**
 * Implementation of {@link SaveFormBinding}.
 * @author henri
 *
 */
public class SaveFormBindingImpl extends FormBindingImpl implements	SaveFormBinding {
	private final ExpressionX validate;
	public SaveFormBindingImpl(Binder binder, Component comp, Form form, String access, String validate, Map args) {
		super(binder, comp, form, access, args);
		final BindEvaluatorX eval = binder.getEvaluatorX();
		final BindContext ctx = new BindContextImpl(binder, this, true, null, comp, null);
		this.validate = validate != null ? eval.parseExpressionX(ctx, validate, Boolean.class) : null;
	}
	
	public void save(BindContext ctx) {
		final Binder binder = getBinder();
		final BindEvaluatorX eval = binder.getEvaluatorX();
		final Component comp = ctx.getComponent();
		final Form form = getFormBean();

		//update form field into backing bean
		final boolean vali = isValidate();
		for (String field : form.getSaveFieldNames()) {
			final ExpressionX expr = getFieldExpression(eval, field);
			if (expr != null) {
				final Object value = form.getField(field);
				eval.setValue(ctx, comp, expr, value);
			}
		}
	}

	//--SaveBinding--//
	public Set<Property> getValidates(BindContext ctx) {
		final Set<Property> properties = new HashSet<Property>(2);
		//validate if required
		if (isValidate()) {
			final Binder binder = getBinder();
			final BindEvaluatorX eval = binder.getEvaluatorX();
			final Component comp = ctx.getComponent();
			final Form form = getFormBean();
	
			//remember base and form field
			for (String field : form.getSaveFieldNames()) {
				final ExpressionX expr = getFieldExpression(eval, field);
				if (expr != null) {
					final ValueReference ref = eval.getValueReference(ctx, comp, expr);
					final Object value = form.getField(field);
					properties.add(new PropertyImpl(ref.getBase(), (String) ref.getProperty(), value));
				}
			}
		}
		return properties;
	}
	
	public boolean isValidate() {
		return validate == null ? false : //default is no validation 
			((Boolean) getBinder().getEvaluatorX().getValue(null, getComponent(), validate)).booleanValue();
	}
}
