/* SaveFormBindingImpl.java

	Purpose:
		
	Description:
		
	History:
		Aug 9, 2011 6:30:34 PM, Created by henrichen

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
import org.zkoss.zkbind.Validator;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.SaveFormBinding;

/**
 * Implementation of {@link SaveFormBinding}.
 * @author henrichen
 *
 */
public class SaveFormBindingImpl extends FormBindingImpl implements	SaveFormBinding {
	private final ExpressionX _validator;
	private final Map<String, Object> _validatorArgs;
	
	public SaveFormBindingImpl(Binder binder, Component comp, Form form, String access, String validator, Map<String,Object> args, Map<String,Object> validatorArgs) {
		super(binder, comp, form, access, args);
		final BindEvaluatorX eval = binder.getEvaluatorX();
		_validator = validator==null?null:parseValidator(eval,validator);
		_validatorArgs = validatorArgs;
	}
	
	public Map<String, Object> getValidatorArgs() {
		return _validatorArgs;
	}
	
	@Override
	protected boolean ignoreTracker(){
		return true;
	}
	
	private ExpressionX parseValidator(BindEvaluatorX eval, String validatorExpr) {
//		final BindContext ctx = BindContextUtil.newBindContext(getBinder(), this, false, null, getComponent(), null);
//		ctx.setAttribute(BinderImpl.IGNORE_TRACKER, Boolean.TRUE);//ignore tracker when doing el, we don't need to trace validator
		//don't provide a bindcontext when pare expression of validator with this binding,
		//do so, the tracker will not also tracking the validator dependence with this binding.
		return eval.parseExpressionX(null, validatorExpr, Object.class);
	}

	public Validator getValidator() {
		if(_validator==null) return null;

//		final BindContext ctx = BindContextUtil.newBindContext(getBinder(), this, false, null, getComponent(), null);
//		ctx.setAttribute(BinderImpl.IGNORE_TRACKER, Boolean.TRUE);//ignore tracker when doing el, we don't need to trace validator		
		final BindEvaluatorX eval = getBinder().getEvaluatorX();
		Object obj = eval.getValue(/*ctx*/null, getComponent(), _validator);
		
		if(obj instanceof Validator){
			return (Validator)obj;
		}else if(obj instanceof String){
			ExpressionX vmconverter = eval.parseExpressionX(null, 
					new StringBuilder().append(BinderImpl.BINDER).append(".viewModel.getValidator('").append(obj).append("')").toString(),
					Validator.class);
			obj = eval.getValue(null, getComponent(), vmconverter);
			if(obj==null){ // try to get it from binder's system level validator
				obj = getBinder().getValidator((String)obj);
			}
			return (Validator)obj;
		}else{
			throw new ClassCastException("result of expression '"+_validator.getExpressionString()+"' is not a Validator, is "+obj);
		}
	}
	
	public void save(BindContext ctx) {
		final Binder binder = getBinder();
		final BindEvaluatorX eval = binder.getEvaluatorX();
		final Component comp = ctx.getComponent();
		final Form form = getFormBean();

		//update form field into backing bean
//		final boolean vali = isValidate();
		for (String field : form.getSaveFieldNames()) {
			final ExpressionX expr = getFieldExpression(eval, field);
			if (expr != null) {
				final Object value = form.getField(field);
				eval.setValue(ctx, comp, expr, value);
			}
		}
	}

	//--SaveBinding--//
	public Property getValidate(BindContext ctx) {
		final Set<Property> properties = new HashSet<Property>(2);
		//we should not check this binding need to validate or not here, 
		//since other validator may want to know the value of porperty of this binding, so just provide it 
		final Binder binder = getBinder();
		final BindEvaluatorX eval = binder.getEvaluatorX();
		final Component comp = ctx.getComponent();
		final Form form = getFormBean();
			
		final ExpressionX expr = getBaseExpression(eval);
		if (expr != null) {
			final Object base = eval.getValue(ctx, comp, expr);
			return new PropertyImpl(base, ".", form);
		}
		return null;
	}
	
	//--SaveFormBinding--//
	public Set<Property> getValidates(BindContext ctx) {
		final Set<Property> properties = new HashSet<Property>(2);
		//we should not check this binding need to validate or not here, 
		//since other validator may want to know the value of porperty of this binding, so just provide it 
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
		return properties;
	}
	
	public boolean hasValidator() {
		return _validator == null ? false : true;
	}
}
