/* PropertyBindingImpl.java

	Purpose:
		
	Description:
		
	History:
		Thu Jul 28 11:59:20     2011, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.zkbind.impl;

import java.lang.reflect.Method;
import java.util.Map;

import org.zkoss.lang.Classes;
import org.zkoss.lang.Strings;
import org.zkoss.xel.ExpressionX;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.metainfo.Annotation;
import org.zkoss.zk.ui.sys.ComponentCtrl;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.PropertyBinding;

/**
 * A base implementation of {@link PropertyBinding}.
 * @author henri
 */
public abstract class PropertyBindingImpl extends BindingImpl implements PropertyBinding {
	protected final ExpressionX _fieldExpr;
	protected final AccessInfo _accessInfo;
	protected final ExpressionX _converter;

	protected PropertyBindingImpl(Binder binder, Component comp, String fieldScript, String accessScript, String converter, Map args) {
		super(binder,comp, args);
		final BindEvaluatorX eval = binder.getEvaluatorX();
		final Class returnType = Object.class;
		this._fieldExpr = eval.parseExpressionX(null, fieldScript, returnType);
		this._accessInfo = AccessInfo.create(this, accessScript, returnType);
		this._converter = converter == null ? 
				null : eval.parseExpressionX(null, BinderImpl.VM+ ".getConverter("+converter+")", Converter.class);
	}
	
	public Converter getConverter() {
		return (Converter) getBinder().getEvaluatorX().getValue(null, getComponent(), _converter);
	}
	
	public String getFieldName() {
		final String fieldScript = getPureExpressionString(this._fieldExpr);
		final int j = fieldScript.lastIndexOf(".");
		return j < 0 ? fieldScript : fieldScript.substring(j);
	}
	
	public String getCommandName() {
		return this._accessInfo.getCommandName();
	}
	
	public String getPropertyString() {
		return getPureExpressionString(this._accessInfo.getProperty());
	}
	
	public boolean isAfter() {
		return this._accessInfo.isAfter();
	}
	
	/*package*/ ExpressionX getProperty() {
		return this._accessInfo.getProperty();
	}
}
