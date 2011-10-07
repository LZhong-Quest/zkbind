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
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.SavePropertyBinding;
import org.zkoss.zkbind.xel.zel.BindELContext;

/**
 * Implementation of {@link SavePropertyBinding}.
 * @author henrichen
 */
public class SavePropertyBindingImpl extends PropertyBindingImpl implements SavePropertyBinding {
	private final ExpressionX _validate;
	public SavePropertyBindingImpl(Binder binder, Component comp, String attr, String saveScript, String converter, String validate, Map<String, Object> args) {
		super(binder, comp, "self."+attr, saveScript, converter, args);
		final BindEvaluatorX eval = binder.getEvaluatorX();
		this._validate = validate != null ? eval.parseExpressionX(null, validate, Boolean.class) : null;
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
		return _validate == null ? false : //default is no validation 
				((Boolean) getBinder().getEvaluatorX().getValue(null, getComponent(), _validate)).booleanValue();
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
