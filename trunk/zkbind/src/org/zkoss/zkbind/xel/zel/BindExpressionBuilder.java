/* BindExpressionBuilder.java

	Purpose:
		
	Description:
		
	History:
		Aug 15, 2011 11:04:37 AM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.xel.zel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.zkoss.zel.ELContext;
import org.zkoss.zel.ELException;
import org.zkoss.zel.impl.lang.EvaluationContext;
import org.zkoss.zel.impl.lang.ExpressionBuilder;
import org.zkoss.zel.impl.parser.AstBracketSuffix;
import org.zkoss.zel.impl.parser.AstDotSuffix;
import org.zkoss.zel.impl.parser.AstIdentifier;
import org.zkoss.zel.impl.parser.AstInteger;
import org.zkoss.zel.impl.parser.AstString;
import org.zkoss.zel.impl.parser.AstValue;
import org.zkoss.zel.impl.parser.Node;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.Form;
import org.zkoss.zkbind.impl.BinderImpl;
import org.zkoss.zkbind.impl.LoadFormBindingImpl;
import org.zkoss.zkbind.impl.LogUtil;
import org.zkoss.zkbind.sys.Binding;
import org.zkoss.zkbind.sys.LoadFormBinding;
import org.zkoss.zkbind.sys.LoadPropertyBinding;
import org.zkoss.zkbind.sys.SavePropertyBinding;
import org.zkoss.zkbind.tracker.impl.TrackerImpl;

/**
 * Handle value dot series script for Binding.
 * @author henrichen
 *
 */
public class BindExpressionBuilder extends ExpressionBuilder {
	
	private static final LogUtil log = new LogUtil(BindExpressionBuilder.class.getName());
			
	private final BindELContext _ctx;
    public BindExpressionBuilder(String expression, ELContext ctx) throws ELException {
		super(expression, ctx);
		_ctx = (BindELContext) ctx;
	}

    //20110815, allow handle value dot series script (e.g. x.y.z ? a.b.c : d.e.f)
    //will call back for each token node.
	public void visit(Node node) throws ELException {
    	super.visit(node);
    	visitNode(node);
    }
	private void addTracking(List<String> series) {
		final Binding binding = _ctx.getBinding();
		if (series != null && !series.isEmpty()) {
			final Iterator<String> it = series.iterator();
			final String prop = (String) it.next();
			final Binder binder = binding.getBinder();
			final TrackerImpl tracker = (TrackerImpl) binder.getTracker();
			
			final BindContext bctx = (BindContext) _ctx.getAttribute(BinderImpl.BINDCTX);
			final List<String> srcpath = bctx != null ? 
					(List<String>) bctx.getAttribute(BinderImpl.SRCPATH) : null;
			final String[] srcprops = srcpath != null ? properties(srcpath) : null;
			//check if a PropertyBinding inside a Form
			final Object base = binding.getComponent().getAttribute(prop, true);
			if (base instanceof Form) {
				final Form formBean = (Form) base;
				final String fieldName = fieldName(it);
				if (fieldName != null) {
					if (binding instanceof SavePropertyBinding) {
						log.debug("add save-filed %s to form %s", fieldName,formBean);
						formBean.addSaveFieldName(fieldName);
					} else if (binding instanceof LoadPropertyBinding) {
						log.debug("add load-filed %s to form %s", fieldName,formBean);
						formBean.addLoadFieldName(fieldName);
					}
					//initialize Tracker per the series (in special Form way)
					tracker.addTracking(binding.getComponent(), new String[] {prop, fieldName}, srcprops, binding);
				} else {
					tracker.addTracking(binding.getComponent(), new String[] {prop}, srcprops, binding);
				}
			
			} else {
				//initialize Tracker per the series
				String[] props = properties(series);
				tracker.addTracking(binding.getComponent(), props, srcprops, binding);
				
				if (binding instanceof LoadFormBindingImpl) {
					((LoadFormBindingImpl)binding).setSeriesLength(props.length);
				}
			}
		}
	}
	
	private void visitNode(Node node) {
		final Binding binding = _ctx.getBinding();
		if(binding==null) return; //no need to build tracker, we are not in binding expression
		
		final List<String> path = new ArrayList<String>();
		//find the path from AST value node or AST identifier node
    	if (node instanceof AstValue) {
    		for(int j = 0, len = node.jjtGetNumChildren(); j < len; ++j) {
    			final Node kid = node.jjtGetChild(j);
    			path.add(BindELContext.toNodeString(kid, new StringBuffer()));
    		}
    		//path example, [vm,.p1,.firstName]
    		addTracking(path);
    	} else if (node instanceof AstIdentifier) {
    		if (!(node.jjtGetParent() instanceof AstValue)) { //one variable series
    			path.add(BindELContext.toNodeString(node, new StringBuffer()));
    			addTracking(path);
    		}
    	}
	}
	
	//remove prefix '.' for properties
	private String[] properties(List<String> series) {
		final String[] props = new String[series.size()];
		int j = 0;
		for (String prop : series) {
			if (prop.charAt(0) == '.') {
				prop = prop.substring(1);
			}
			props[j++] = prop;
		}
		return props;
	}
	
	//append rest field into form field name
	private String fieldName(Iterator<String> it) {
		final StringBuffer sb = new StringBuffer();
		while(it.hasNext()) {
			sb.append(it.next());
		}
		return sb.length() == 0 ? null : sb.charAt(0) == '.' ? sb.substring(1) : sb.toString();
	}
}
