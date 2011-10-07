/* BindingImpl.java

	Purpose:
		
	Description:
		
	History:
		Aug 5, 2011 11:02:23 AM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.xel.ExpressionX;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.sys.Binding;

/**
 * Base implementation for implementing a {@link Binding}
 * @author henrichen
 *
 */
public class BindingImpl implements Binding {
	private Object _comp;
	private final Binder _binder;
	private final Map<String, Object> _args;
	
	protected BindingImpl(Binder binder, Component comp, Map<String, Object> args) {
		_comp = new WeakReference<Component>(comp);
		_binder = binder;
		_args = args;
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

	public Map<String, Object> getArgs() {
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
