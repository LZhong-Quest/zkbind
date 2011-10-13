/* AnnotateBinderHelper.java

	Purpose:
		
	Description:
		
	History:
		Sep 9, 2011 6:06:10 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.lang.Strings;
import org.zkoss.xel.ExpressionX;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.metainfo.Annotation;
import org.zkoss.zk.ui.sys.ComponentCtrl;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.sys.BindEvaluatorX;

/**
 * Helper class to parse binding annotations and create bindings. 
 * @author henrichen
 *
 */
public class AnnotateBinderHelper {
	final private Binder _binder;
	public AnnotateBinderHelper(Binder binder) {
		_binder = binder;
	}
	
	public void initComponentBindings(Component comp) {
		initBindingsByAnnotation(comp, "bind");
	}
	
	private void initBindingsByAnnotation(Component comp, String annotName) {
		initAllComponentsBindings(comp, annotName);
	}
	
	private void initAllComponentsBindings(Component comp, String annotName) {
		final Binder selfBinder = (Binder) comp.getAttribute(BinderImpl.BINDER);
		//check if a component was binded already(by any binder)
		if (selfBinder != null) //already binded !
			return;
		initFormBindings(comp);//initial form binding on comp, ex self="form(...)"
		initComponentPropertiesBindings(comp, annotName); //initial property binding on comp, ex value="bind(...)"
		for(final Iterator it = comp.getChildren().iterator(); it.hasNext();) {
			final Component kid = (Component) it.next();
			initAllComponentsBindings(kid, annotName); //recursive to each child
		}
	}
	
	private void initFormBindings(Component comp) {
		final ComponentCtrl compCtrl = (ComponentCtrl) comp;
		final Annotation annot = compCtrl.getAnnotation("form");
		if (annot != null) {
			Map attrs = annot.getAttributes();
			final List<String> saveExprs = new ArrayList<String>();
			final List<String> loadExprs = new ArrayList<String>();
			String id = null;
			Object value = null;
			String initExpr = null;
			String validatorExpr = null;
			Map<String, Object> args = null;
			for (final Iterator it = attrs.entrySet().iterator(); it.hasNext();) {
				final Map.Entry entry = (Map.Entry) it.next();
				final String tag = (String) entry.getKey();
				final Object tagExpr = entry.getValue();
				if ("id".equals(tag)) {
					id = (String) tagExpr;
				} else if ("init".equals(tag)) {
					initExpr = (String) tagExpr;
				} else if ("save".equals(tag)) {
					addTagExpr(saveExprs, tagExpr);
				} else if ("load".equals(tag)) {
					addTagExpr(loadExprs, tagExpr);
				} else if ("validator".equals(tag)) {
					validatorExpr = (String) tagExpr;
				} else if ("value".equals(tag)) {
					value = tagExpr;
				} else { //other unknown tag, keep as arguments
					if (args == null) {
						args = new HashMap<String, Object>();
					}
					args.put(tag, tagExpr);
				}
			}
			if (Strings.isBlank(id)) {
				throw new UiException("Must specify a form id!");
			}
			if (value != null) {
				//default value will apply to load or save if they were empty
				if (loadExprs.isEmpty()) { //might be both
//					loadExprs.add(value);
					addTagExpr(loadExprs, value);
				}
				if (saveExprs.isEmpty()) { //might be both
//					saveExprs.add(value);
					addTagExpr(saveExprs, value);
				}
			}
			
			args = args == null ? null : parsedArgs(args);
			
			_binder.addFormBindings(comp, id, initExpr,
					loadExprs.toArray(new String[loadExprs.size()]), 
					saveExprs.toArray(new String[saveExprs.size()]),  
					validatorExpr, args);
		}
	}
	
	private void initComponentPropertiesBindings(Component comp, String annotName) {
		final ComponentCtrl compCtrl = (ComponentCtrl) comp;
		final List props = compCtrl.getAnnotatedPropertiesBy(annotName);
		for (final Iterator it = props.iterator(); it.hasNext(); ) {
			final String propName = (String) it.next();
			if (isEventProperty(propName)) {
				initCommandBindings(comp, propName, annotName);
			} else {
				initPropertyBindings(comp, propName, annotName);
			}
			
		}
	}
	
	private boolean isEventProperty(String propName) {
		return propName.startsWith("on") && propName.length() >= 3 && Character.isUpperCase(propName.charAt(2));
	}
	
	private void initCommandBindings(Component comp, String propName, String annotName) {
		final ComponentCtrl compCtrl = (ComponentCtrl) comp;
		final Annotation ann = compCtrl.getAnnotation(propName, annotName);
		if (ann != null) {
			final Map attrs = ann.getAttributes(); //(tag, tagExpr)
			Map<String, Object> args = null;
			final List<String> cmdExprs = new ArrayList<String>();
			for (final Iterator it = attrs.entrySet().iterator(); it.hasNext();) {
				final Map.Entry entry = (Map.Entry) it.next();
				final String tag = (String) entry.getKey();
				final Object tagExpr = entry.getValue();
				if ("value".equals(tag)) {
					if (tagExpr instanceof String[]) {
						throw new UiException("Allow only one Command for an event!");
					}
					cmdExprs.add((String)tagExpr);
				} else { //other unknown tag, keep as arguments
					if (args == null) {
						args = new HashMap<String, Object>();
					}
					args.put(tag, tagExpr);
				}
			}
			
			args = args == null ? null : parsedArgs(args);
			
			for(String cmd : cmdExprs) {
				_binder.addCommandBinding(comp, propName, cmd, args);
			}
		}
	}
	
	private void initPropertyBindings(Component comp, String propName, String annotName) {
		final ComponentCtrl compCtrl = (ComponentCtrl) comp;
		final Annotation ann = compCtrl.getAnnotation(propName, annotName);
		if (ann != null) {
			final Map attrs = ann.getAttributes(); //(tag, tagExpr)
			final List<String> saveExprs = new ArrayList<String>();
			final List<String> loadExprs = new ArrayList<String>();
			Object value = null;
			String initExpr = null;
			String converterExpr = null;
			String validatorExpr = null;
			Map<String, Object> args = null;
			for (final Iterator it = attrs.entrySet().iterator(); it.hasNext();) {
				final Map.Entry entry = (Map.Entry) it.next();
				final String tag = (String) entry.getKey();
				final Object tagExpr = entry.getValue();
				if ("init".equals(tag)) {
					initExpr = (String) tagExpr;
				} else if ("save".equals(tag)) {
					addTagExpr(saveExprs, tagExpr);
				} else if ("load".equals(tag)) {
					addTagExpr(loadExprs, tagExpr);
				} else if ("converter".equals(tag)) {
					converterExpr = (String) tagExpr;
				} else if ("validator".equals(tag)) {
					validatorExpr = (String) tagExpr;
				} else if ("value".equals(tag)) {
					value = tagExpr;
				} else { //other unknown tag, keep as arguments
					if (args == null) {
						args = new HashMap<String, Object>();
					}
					args.put(tag, tagExpr);
				}
			}
			if (value != null) {
				//default value will apply to load or save if they were empty
				if (loadExprs.isEmpty()) { //might be both
//					loadExprs.add(value);
					addTagExpr(loadExprs, value);
				}
				if (saveExprs.isEmpty()) { //might be both
//					saveExprs.add(value);
					addTagExpr(saveExprs, value);
				}
			}
			
			args = args==null?null:parsedArgs(args);
			
			_binder.addPropertyBinding(comp, propName, initExpr,
					loadExprs.toArray(new String[loadExprs.size()]), 
					saveExprs.toArray(new String[saveExprs.size()]), 
					converterExpr, validatorExpr, args);
		}
	}

	private void addTagExpr(List<String> exprs, Object tagExpr) {
		if (tagExpr instanceof String[]) {
			for (String expr : (String[])tagExpr) {
				exprs.add(expr);
			}
		} else {
			exprs.add((String)tagExpr);
		}
	}
	
	// parse args , if it is a string, than parse it to ExpressionX
	private Map<String, Object> parsedArgs(Map<String,Object> args) {
		final BindEvaluatorX eval = _binder.getEvaluatorX();
		final Map<String, Object> result = new LinkedHashMap<String, Object>(args.size()); 
		for(final Iterator<Entry<String, Object>> it = args.entrySet().iterator(); it.hasNext();) {
			final Entry<String, Object> entry = it.next(); 
			final String key = entry.getKey();
			final Object value = entry.getValue();
			if (value instanceof String){
				addArg(eval, result, key, (String)value);
			/*}else if (value instanceof String[]) {*/ // TODO, consider if it is a string array/collection
			}else{
				result.put(key, value);
			}
		}
		return result;
	}

	
	private void addArg(BindEvaluatorX eval, Map<String,Object> result, String key, String valueScript) {
		final ExpressionX parsedValue = valueScript == null ? null : eval.parseExpressionX(null, valueScript, String.class);
		result.put(key, parsedValue);
	}
}
