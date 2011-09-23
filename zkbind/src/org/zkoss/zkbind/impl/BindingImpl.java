/* BindingImpl.java

	Purpose:
		
	Description:
		
	History:
		Aug 5, 2011 11:02:23 AM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

import org.zkoss.xel.ExpressionX;
import org.zkoss.xel.XelContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.Binding;

/**
 * Base implementation for implementing a {@link Binding}
 * @author henri
 *
 */
public class BindingImpl implements Binding {
	private Object _comp;
	private final Binder _binder;
	private final Map<String, ExpressionX> _args;
	
	protected BindingImpl(Binder binder, Component comp, Map args) {
		_comp = new WeakReference<Component>(comp);
		_binder = binder;
		_args = args == null ? null : parsedArgs(args);
	}
	
	public Component getComponent() {
		Object comp = _comp == null ? null : ((WeakReference<Object>)_comp).get();
		if (comp == null && _comp != null) { //Help GC
			_comp = null;
		}
		return (Component) comp;
	}
	
	public Binder getBinder() {
		return _binder;
	}

	public Map getArgs() {
		return _args;
	}

	//utility class, remove ${ and }
	protected String getPureExpressionString(ExpressionX expr) {
		if (expr == null) {
			return null;
		}
		final String evalstr = expr.getExpressionString(); 
		return evalstr.substring(2, evalstr.length() - 1);
	}
	
	//to pars args of BindingImpl to expressions map
	//TODO DENNIS , check if args spec was changed.?
	private Map<String, ExpressionX> parsedArgs(Map args) {
		final BindEvaluatorX eval = getBinder().getEvaluatorX();
		final Map<String, ExpressionX> result = new LinkedHashMap<String, ExpressionX>(args.size()); 
		for(final Iterator<Entry<String, Object>> it = args.entrySet().iterator(); it.hasNext();) {
			final Entry<String, Object> entry = it.next(); 
			final String key = entry.getKey();
			final Object value = entry.getValue();
			if (value instanceof String[]) {
				//TODO DENNIS, BUG, the impl. of addArg always keep last value only. 
				//and, should we accept a expression in array in args? over kill?
				for(String v : (String[]) value) {
					addArg(eval, result, key, v);
				}
			} else {
				addArg(eval, result, key, value.toString());
			}
		}
		return result;
	}
	
	private void addArg(BindEvaluatorX eval, Map result, String key, String valueScript) {
		final ExpressionX parsedValue = valueScript == null ? null : eval.parseExpressionX(null, valueScript, String.class);
		result.put(key, parsedValue);
	}
	
	protected Object setAttribute(BindContext ctx, Object key, Object value) {
		Map<Object, Object> bindingBag = (Map<Object, Object>) ctx.getAttribute(this);
		if (bindingBag == null) {
			bindingBag = new HashMap<Object, Object>();
			ctx.setAttribute(this, bindingBag);
		}
		return bindingBag.put(key, value);
	}
	
	protected Object getAttribute(BindContext ctx, Object key) {
		Map<Object, Object> bindingBag = (Map<Object, Object>) ctx.getAttribute(this);
		return bindingBag != null ? bindingBag.get(key) : null;
	}
	
	protected boolean containsAttribute(BindContext ctx, Object key) {
		Map<Object, Object> bindingBag = (Map<Object, Object>) ctx.getAttribute(this);
		return bindingBag != null ? bindingBag.containsKey(key) : false;
	}
}
