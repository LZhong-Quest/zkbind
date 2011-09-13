/* BindELContext.java

	Purpose:
		
	Description:
		
	History:
		Aug 10, 2011 4:52:27 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.xel.zel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.lang.Strings;
import org.zkoss.xel.ExpressionX;
import org.zkoss.xel.XelContext;
import org.zkoss.xel.zel.XelELContext;
import org.zkoss.zel.ELResolver;
import org.zkoss.zel.VariableMapper;
import org.zkoss.zel.impl.parser.AstBracketSuffix;
import org.zkoss.zel.impl.parser.AstDotSuffix;
import org.zkoss.zel.impl.parser.AstValue;
import org.zkoss.zel.impl.parser.Node;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.DependsOn;
import org.zkoss.zkbind.Form;
import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.impl.BindContextImpl;
import org.zkoss.zkbind.impl.BinderImpl;
import org.zkoss.zkbind.impl.FormImpl;
import org.zkoss.zkbind.impl.Path;
import org.zkoss.zkbind.impl.PropertyImpl;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.Binding;
import org.zkoss.zkbind.sys.FormBinding;
import org.zkoss.zkbind.sys.LoadBinding;
import org.zkoss.zkbind.sys.LoadPropertyBinding;
import org.zkoss.zkbind.sys.PropertyBinding;
import org.zkoss.zkbind.sys.SaveBinding;
import org.zkoss.zkbind.sys.SavePropertyBinding;
import org.zkoss.zkbind.tracker.impl.TrackerImpl;

/**
 * ELContext for Binding.
 * @author henri
 *
 */
public class BindELContext extends XelELContext {
	private List<Object> _path = null;
	private List<Property> _properties;
	
	public BindELContext(XelContext xelc) {
		super(xelc);
	}
	
	protected ELResolver newELResolver(XelContext xelc) {
		return new BindELResolver(xelc);
	}

	//can be used to pass info to ELResolver
	public VariableMapper getVariableMapper() {
		return super.getVariableMapper();
	}

	public Binding getBinding() {
		return (Binding) getXelContext().getAttribute(BinderImpl.BINDING); //see BindEvaluatorXImpl#newXelContext()
	}
	
	public Object getAttribute(String name) {
		return getXelContext().getAttribute(name); //see BindEvaluatorXImpl#newXelContext()
	}
	
	public Object setAttribute(String name, Object value) {
		return getXelContext().setAttribute(name, value);
	}
	
	//check method annotation and collect NotifyChange annotation
	public static Set<Property> getNotifys(Method m, Object base, String prop, Object value) {
		final Set<Property> notifys = new HashSet<Property>();
		final NotifyChange annt = m == null ? 
			null : m.getAnnotation(NotifyChange.class);
		if (annt != null) {
			String[] notifies = annt.value();
			if (notifies.length > 0) {
				for(String notify : notifies) {
					final Property propx = new PropertyImpl(base, notify, value);
					notifys.add(propx);
				}
			} else if (prop != null) {
				notifys.add(new PropertyImpl(base, prop, value));
			}
		}
		return notifys;
	}
	
	public static void addNotifys(Method m, Object base, String prop, Object value, BindContext ctx) {
		final Set<Property> props = getNotifys(m, base, prop, value);
		addNotifys(props, ctx);
	}
	
	private static void addNotifys(Set<Property> props, BindContext ctx) {
		if (ctx == null) {
			return;
		}
		Set<Property> notifys = (Set<Property>) ctx.getAttribute(BinderImpl.NOTIFYS);
		if (notifys == null) {
			notifys = new HashSet<Property>();
			ctx.setAttribute(BinderImpl.NOTIFYS, notifys);
		}
		notifys.addAll(props);
	}
	
	public static void addValidates(Set<Property> props, BindContext ctx) {
		if (ctx == null) {
			return;
		}
		Set<Property> validates = (Set<Property>) ctx.getAttribute(BinderImpl.VALIDATES);
		if (validates == null) {
			validates = new HashSet<Property>();
			ctx.setAttribute(BinderImpl.VALIDATES, validates);
		}
		validates.addAll(props);
	}

	/*package*/ static String toNodeString(Node next, StringBuffer path) {
		if (next instanceof AstBracketSuffix) {
			final String bracketString = toNodeString(next.jjtGetChild(0), new StringBuffer()); //recursive
			path.append("[").append(bracketString).append("]");
		} else if (next instanceof AstValue) {
    		for(int j = 0, len = next.jjtGetNumChildren(); j < len; ++j) {
    			final Node kid = next.jjtGetChild(j);
    			toNodeString(kid, path); //recursive
    		}
		} else if (next instanceof AstDotSuffix) {
			path.append(".").append(next.getImage());
		} else {
			path.append(next.getImage());
		}
		return path.toString();
	}

	public static boolean isBracket(String script) {
		return script.startsWith("[") && script.endsWith("]");
	}
	
	public static String appendFields(String prefix, String field) {
		return prefix + (isBracket(field) ? "" : '.') + field; 
	}
	
	private static final String TRACKING_DEPENDS_ON = "$TRACKING_DEPENDS_ON$";
	//check method annotation and collect NotifyChange annotation
	public static void addDependsOnTrackings(Method m, String basepath, Binding binding, BindContext ctx) {
		final DependsOn annt = m.getAnnotation(DependsOn.class);
		if (annt != null) {
			String[] props = annt.value();
			if (props.length > 0) {
				if (ctx == null || !m.equals(ctx.getAttribute(TRACKING_DEPENDS_ON))) { //see addDependsOnTracking
					for(String prop : props) {
						addDependsOnTracking(m, basepath, prop, binding);
					}
				}
			}
		}
	}
	
	//prepare the dependsOn nodes
	private static void addDependsOnTracking(Method m, String basepath, String prop, Binding binding) {
		final Component comp = binding.getComponent();
		final Binder binder = binding.getBinder();
		final BindEvaluatorX eval = binder.getEvaluatorX();
		final String path = BindELContext.appendFields(basepath, prop);
		
		//parse depends on series
		BindContext ctxparse = new BindContextImpl(binder, binding, false, null, comp, null);
		ExpressionX expr = eval.parseExpressionX(ctxparse, path, Object.class); //prepare the tracking
		
		//bean association
		BindContext ctx = new BindContextImpl(binder, binding, false, null, comp, null);
		ctx.setAttribute(TRACKING_DEPENDS_ON, m); //avoid endless loop
		eval.getValue(ctx, comp, expr); //will call tieValue() and recursive back via BindELResolver
	}
}
