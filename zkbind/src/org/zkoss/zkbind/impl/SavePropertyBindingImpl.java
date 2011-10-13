/* SavePropertyBindingImpl.java

	Purpose:
		
	Description:
		
	History:
		Aug 1, 2011 2:45:43 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.zkoss.xel.ExpressionX;
import org.zkoss.xel.ValueReference;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.Validator;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.SavePropertyBinding;
import org.zkoss.zkbind.xel.zel.BindELContext;

/**
 * Implementation of {@link SavePropertyBinding}.
 * @author henrichen
 */
public class SavePropertyBindingImpl extends PropertyBindingImpl implements SavePropertyBinding {
	private final ExpressionX _validator;
	
	public SavePropertyBindingImpl(Binder binder, Component comp, String attr, String saveScript, String converter, String validator, Map<String, Object> args) {
		super(binder, comp, "self."+attr, saveScript, converter, args);
		final BindEvaluatorX eval = binder.getEvaluatorX();
		
		_validator = validator==null?null:parseValidator(eval,validator);
	}
	
	@Override
	protected boolean ignoreTracker(){
		return true;
	}
	
	private ExpressionX parseValidator(BindEvaluatorX eval, String validatorExpr) {
		
		final BindContext ctx = new BindContextImpl(getBinder(), this, false, null, getComponent(), null, null);
		//expression will/should not be tracked, (although, from the impl, tracker don't care savebinding)
		ctx.setAttribute(BinderImpl.IGNORE_TRACKER, Boolean.TRUE);//ignore tracker when doing el, we don't need to trace validator
		return eval.parseExpressionX(ctx, validatorExpr, Object.class);
	}

	public Validator getValidator() {
		if(_validator==null) return null;

		final BindContext ctx = new BindContextImpl(getBinder(), this, false, null, getComponent(), null, null);
		final BindEvaluatorX eval = getBinder().getEvaluatorX();
		ctx.setAttribute(BinderImpl.IGNORE_TRACKER, Boolean.TRUE);//ignore tracker when doing el, we don't need to trace validator
		Object obj = eval.getValue(ctx, getComponent(), _validator);
		
		if(obj instanceof Validator){
			return (Validator)obj;
		}else if(obj instanceof String){
			ExpressionX vmconverter = eval.parseExpressionX(null, 
					new StringBuilder().append(BinderImpl.VM).append(".getValidator('").append(obj).append("')").toString(),
					Validator.class);
			obj = eval.getValue(null, getComponent(), vmconverter);
			return (Validator)obj;
		}else{
			throw new ClassCastException("result of expression '"+_validator.getExpressionString()+"' is not a Validator, is "+obj);
		}
	}
	

	private static final String $COMPVALUE$ = "$COMPVALUE$";
	private static final String $VALUEREF$ = "$VALUEREF$";
	private Object getComponentValue(BindContext ctx) {
		if (!containsAttribute(ctx, $COMPVALUE$)) {
			final Component comp = ctx.getComponent();
			final BindEvaluatorX eval = getBinder().getEvaluatorX();
			
			//get data from component attribute
			Object value = eval.getValue(null, comp, _fieldExpr);
			
			//use converter to convert type if any
			final Converter conv = getConverter();
			if (conv != null) {
				value = conv.coerceToBean(value, comp, ctx);
				ValueReference ref = getValueReference(ctx);
					//collect Property for @NotifyChange, kept in BindContext
					//see BinderImpl$CommandEventListener#onEvent()
				BindELContext.addNotifys(getConverterMethod(conv.getClass()), ref.getBase(), null, value, ctx);
			}
			setAttribute(ctx, $COMPVALUE$, value);
		}
		return getAttribute(ctx, $COMPVALUE$);
	}
	
	public void save(BindContext ctx) {
		//get data from component attribute
		Object value = getComponentValue(ctx);
		
		//set data into bean property
		final Component comp = ctx.getComponent();
		final BindEvaluatorX eval = getBinder().getEvaluatorX();
		eval.setValue(ctx, comp, _accessInfo.getProperty(), value);
	}
	
	//get and cache value reference of this binding
	private ValueReference getValueReference(BindContext ctx){
		ValueReference ref = (ValueReference) getAttribute(ctx, $VALUEREF$);
		if (ref == null) {
			final Component comp = ctx.getComponent();
			final BindEvaluatorX eval = getBinder().getEvaluatorX();
			ref = eval.getValueReference(ctx, comp, _accessInfo.getProperty());
			setAttribute(ctx, $VALUEREF$, ref);
		}
		return ref;
	}

	//--SaveBinding--//
	public Set<Property> getValidates(BindContext ctx) {
		final Set<Property> properties = new HashSet<Property>(2);
		//validate if required
		if (isValidate()) {
			final Object value = getComponentValue(ctx);
			try {
				ValueReference ref = getValueReference(ctx);
				properties.add(new PropertyImpl(ref.getBase(), (String) ref.getProperty(), value));
			} catch (Exception e) {
				throw UiException.Aide.wrap(e);
			}
		}
		return properties;
	}
	
	public boolean isValidate() {
		return _validator == null ? false : true;
	}
	
	private Method getConverterMethod(Class<? extends Converter> cls) {
		try {
			return cls.getMethod("coerceToBean", new Class[] {Object.class, Component.class, BindContext.class});
		} catch (NoSuchMethodException e) {
			//ignore
		}
		return null; //shall never come here
	}
}
