/* GenericBindComposer.java

	Purpose:
		
	Description:
		
	History:
		Jun 22, 2011 10:09:50 AM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.zkbind;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.ComposerExt;
import org.zkoss.zkbind.impl.AnnotateBinderImpl;
import org.zkoss.zkbind.impl.BinderImpl;

/**
 * Base composer to apply ZK Bind.
 * @author henri
 *
 */
public class GenericBindComposer implements Composer, ComposerExt {
	private Object _viewModel;
	private Binder _binder;
	private Component _ownerComp;
	private final Map<String, Converter> _converters;
	
	public GenericBindComposer() {
		setViewModel(this);
		_converters = new HashMap<String, Converter>(8);
	}
	
	public Binder getBinder() {
		return _binder;
	}
	
	//can assign a separate view model, default to this
	public void setViewModel(Object viewModel) {
		_viewModel = viewModel;
		if (this._binder != null) {
			this._binder.setViewModel(_viewModel);
		}
	}
	
	public Object getViewModel() {
		return _viewModel;
	}
	
	public Converter getConverter(String name) {
		Converter conv = _converters.get(name);
		if (conv == null) {
			conv = _binder.getConverter(name);
		}
		return conv;
	}
	
	public void addConverter(String name, Converter converter) {
		_converters.put(name, converter);
	}

	//--Composer--//
	public void doAfterCompose(Component comp) throws Exception {
		//name this composer
		final String cname = (String) comp.getAttribute("composerName");
		comp.setAttribute(cname != null ? cname : comp.getId()+"$composer", this);
		
		//init the binder
		final String qname = (String) comp.getAttribute("queueName");
		final String qscope = (String) comp.getAttribute("queueScope");
		_binder = new AnnotateBinderImpl(comp, _viewModel, qname, qscope);
		
		//assign binder name
		final String bname = (String) comp.getAttribute("binderName");
		comp.setAttribute(BinderImpl.BINDER, _binder);
		comp.setAttribute(bname != null ? bname : "binder", _binder);
		
		//load data
		((BinderImpl)_binder).loadComponent(comp); //load all bindings
	}
	
	//--ComposerExt//
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) throws Exception {
		return compInfo;
	}

	public void doBeforeComposeChildren(Component comp) throws Exception {
		this._ownerComp = comp;
	}

	public boolean doCatch(Throwable ex) throws Exception {
		return false;
	}

	public void doFinally() throws Exception {
		// ignore
	}
	
	//--notifyChange--//
	public void notifyChange(Object bean, String field, Object oldValue, Object newValue) {
		//TODO delegate to Binder.notifyChange()...
	}
}
