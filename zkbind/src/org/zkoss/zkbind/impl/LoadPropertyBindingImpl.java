/* LoadPropertyBindingImpl.java

	Purpose:
		
	Description:
		
	History:
		Aug 1, 2011 2:43:33 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.zkoss.lang.Classes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.LoadPropertyBinding;
import org.zkoss.zkbind.xel.zel.BindELContext;

/**
 * Implementation of {@link LoadPropertyBinding}.
 * @author henri
 */
public class LoadPropertyBindingImpl extends PropertyBindingImpl implements
		LoadPropertyBinding {
	private Set<Method> _doneDependsOn = new WeakHashSet<Method>(4);
	private Set<Class> _doneConverterDependsOn = new WeakHashSet<Class>(4);
	
	public LoadPropertyBindingImpl(Binder binder, Component comp,
		String attr, String loadScript, String converter, Map args) {
		super(binder, comp, "self."+attr, loadScript, converter, args);
	}
	
	public void load(BindContext ctx) {
		final Component comp = ctx.getComponent();
		final BindEvaluatorX eval = getBinder().getEvaluatorX();
		//get data from property
		Object value = eval.getValue(ctx, comp, _accessInfo.getProperty());
		
		//use _converter to convert type if any
		if (_converter != null) {
			final Converter conv = (Converter) eval.getValue(null, comp, _converter);
			if (conv != null) {
				addConverterDependsOnTrackings(conv, ctx);
				value = conv.coerceToUi(value, comp, ctx);
			}
		}
		
		//set data into component attribute
		eval.setValue(null, comp, _fieldExpr, value);
	}
	
	private void addConverterDependsOnTrackings(Converter conv, BindContext ctx) {
		final Class convClz = conv.getClass();
		if (_doneConverterDependsOn.contains(convClz)) { //avoid to eval converter @DependsOn again if not exists
			return;
		}
		_doneConverterDependsOn.add(convClz);
		final Method m = getConverterMethod(convClz);
		BindELContext.addDependsOnTrackings(m, getPropertyString(), this, ctx);
	}
	
	private Method getConverterMethod(Class<? extends Converter> cls) {
		try {
			return cls.getMethod("coerceToUi", new Class[] {Object.class, Component.class, BindContext.class});
		} catch (NoSuchMethodException e) {
			//ignore
		}
		return null; //shall never come here
	}
	
	/**
	 * Internal Use Only.
	 */
	public void addDependsOnTrackings(Method m, String basepath, String[] props) {
		if (_doneDependsOn.contains(m)) { //this method has already done @DependsOn in this binding
			return;
		}
		_doneDependsOn.add(m); //mark method as done @DependsOn
		for(String prop : props) {
			BindELContext.addDependsOnTracking(m, basepath, prop, this);
		}
	}
}
