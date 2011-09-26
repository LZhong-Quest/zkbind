/* BindELResolver.java

	Purpose:
		
	Description:
		
	History:
		Aug 10, 2011 4:31:51 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.xel.zel;

import java.lang.reflect.Method;
import java.util.List;

import org.zkoss.xel.XelContext;
import org.zkoss.xel.zel.XelELResolver;
import org.zkoss.zel.CompositeELResolver;
import org.zkoss.zel.ELContext;
import org.zkoss.zel.ELException;
import org.zkoss.zel.ELResolver;
import org.zkoss.zel.PropertyNotFoundException;
import org.zkoss.zel.PropertyNotWritableException;
import org.zkoss.zel.impl.lang.EvaluationContext;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.Form;
import org.zkoss.zkbind.impl.BinderImpl;
import org.zkoss.zkbind.impl.LoadFormBindingImpl;
import org.zkoss.zkbind.impl.Path;
import org.zkoss.zkbind.sys.Binding;
import org.zkoss.zkbind.sys.LoadBinding;
import org.zkoss.zkbind.sys.SaveBinding;

/**
 * ELResolver for Binding; handle Form bean.
 * @author henri
 *
 */
public class BindELResolver extends XelELResolver {
	private final CompositeELResolver _resolver;
	public BindELResolver(XelContext ctx) {
		super(ctx);
		_resolver = new CompositeELResolver();
		_resolver.add(new PathResolver()); //must be the first
		_resolver.add(new FormELResolver());
		_resolver.add(super.getELResolver());
	}
	protected ELResolver getELResolver() {
		return _resolver;
	}
	//ELResolver//
	public Object getValue(ELContext ctx, Object base, Object property)
	throws PropertyNotFoundException, ELException {
		final Object value = super.getValue(ctx, base, property);
		tieValue((BindELContext)((EvaluationContext)ctx).getELContext(), base, property, value);
		return value;
	}
	
	public void setValue(ELContext ctx, Object base, Object property, Object value)
	throws PropertyNotFoundException, PropertyNotWritableException, ELException {
		super.setValue(ctx, base, property, value);
		tieValue((BindELContext)((EvaluationContext)ctx).getELContext(), base, property, value);
	}
	
	//update dependency and notify changed
	private void tieValue(BindELContext ctx, Object base, Object propName, Object value) {
		final Binding binding = ctx.getBinding();
		
		//only there is a binding that needs tie tracking to value
		if (binding != null) {
        	final int nums = ((Integer) ctx.getContext(Integer.class)).intValue(); //get numOfKids, see #PathResolver
        	final List<String> path = (List<String>) ctx.getContext(Path.class); //get path, see #PathResolver
        	
        	String script = null;
			if (base instanceof Form) {
				if (nums > 0) { //still in resolving the form field
					return;
				} else { //done resolving the form field
					script = FormELResolver.fieldName(path);
				}
			} else {
				script = propertyName(path.listIterator(path.size()).previous());
			}
			final Binder binder = binding.getBinder();
			binder.getTracker().tieValue(binding.getComponent(), base, script, propName, value);
			
			if (base != null && !(base instanceof Form)) { //no @DependsOn and @NotifyChange in Form
				final Method m = (Method) ctx.getContext(Method.class);
				//parse @DependsOn and add into dependency tracking
				final BindContext bctx = (BindContext) ctx.getAttribute(BinderImpl.BINDCTX);
				final boolean prompt = bctx != null && bctx.getCommandName() == null; 
				if (prompt && binding instanceof LoadBinding && m != null) {
					//FormBinding shall not check @DependsOn() for dependent nodes
					if (!(binding instanceof LoadFormBindingImpl) || ((LoadFormBindingImpl)binding).getSeriesLength() <= path.size()) {
						BindELContext.addDependsOnTrackings(m, basePath(path), binding, bctx);
					}
				}
				
				//parse @NotifyChange and collect Property to publish PropertyChangeEvent
				if (nums == 0 && binding instanceof SaveBinding) { //a done save operation
					//collect Property for @NotifyChange, kept in BindContext
					//see BinderImpl$CommandEventListener#onEvent()
					BindELContext.addNotifys(m, base, (String) propName, value, bctx);
				}
			}
		}
	}

	//get the path before the last dot, to be a basepath 
	//ex, base path of 'vm.person.address.fullstr' will become 'vm.person.address'
	//so, a depends-on(city) on fullstr in address will add a depends to 'vm.person.address'.'city'
	private String basePath(List<String> path) {
    	final StringBuffer sb = new StringBuffer();
		for(String prop : path.subList(0, path.size()-1)) { //remove the last one
			sb.append(prop);
		}
    	return sb.charAt(0) == '.' ? sb.substring(1) : sb.toString();
	}
	
	private String propertyName(String script) {
    	return script.charAt(0) == '.' ? script.substring(1) : script;
	}
	
}
