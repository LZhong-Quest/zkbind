/* GenericBindComposer.java

	Purpose:
		
	Description:
		
	History:
		Jun 22, 2011 10:09:50 AM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.zkbind;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.metainfo.Annotation;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.sys.ComponentCtrl;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.ComposerExt;
import org.zkoss.zkbind.impl.AnnotateBinderImpl;
import org.zkoss.zkbind.impl.BinderImpl;

/**
 * Base composer to apply ZK Bind.
 * @author henrichen
 *
 */
public class GenericBindComposer implements Composer, ComposerExt {
	private Object _viewModel;
	private Binder _binder;
	private final Map<String, Converter> _converters;
	private final Map<String, Validator> _validators;
	
//	private static final String ON_BINDER_POST_COMMAND = "onBinderPostCommand";
	
	public GenericBindComposer() {
		setViewModel(this);
		_converters = new HashMap<String, Converter>(8);
		_validators = new HashMap<String, Validator>(8);
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
	
	public Validator getValidator(String name) {
		Validator validator = _validators.get(name);
		if (validator == null) {
			validator = _binder.getValidator(name);
		}
		return validator;
	}
	
	public void addConverter(String name, Converter converter) {
		_converters.put(name, converter);
	}
	
	public void addValidator(String name, Validator validator) {
		_validators.put(name, validator);
	}

	//--Composer--//
	public void doAfterCompose(Component comp) throws Exception {
		//TODO, Dennis, need to confirm with henrichen about annotation or attribute
		//name this composer
		final String cname = getAnnotationOrAttribute(comp,"composerName");
		comp.setAttribute(cname != null ? cname : comp.getId()+"$composer", this);
		
		//init the binder
		final String qname = getAnnotationOrAttribute(comp,"queueName");
		final String qscope = getAnnotationOrAttribute(comp,"queueScope");
		_binder = new AnnotateBinderImpl(comp, _viewModel, qname, qscope);
		
		//assign binder name
		final String bname = getAnnotationOrAttribute(comp,"binderName");
		comp.setAttribute(BinderImpl.BINDER, _binder);
		comp.setAttribute(bname != null ? bname : "binder", _binder);
		
		//load data
		((BinderImpl)_binder).loadComponent(comp); //load all bindings
	}
	
	private static String getAnnotationOrAttribute(Component comp,String attr){
		final ComponentCtrl compCtrl = (ComponentCtrl) comp;
		final Annotation cnameann = compCtrl.getAnnotation(attr);
		final String value;
		if(cnameann!=null){
			value = cnameann.getAttribute("value");
		}else{
			value = (String) comp.getAttribute(attr);
		}
		return value;
	}
	
	//--ComposerExt//
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) throws Exception {
		return compInfo;
	}

	public void doBeforeComposeChildren(Component comp) throws Exception {
	}

	public boolean doCatch(Throwable ex) throws Exception {
		return false;
	}

	public void doFinally() throws Exception {
		// ignore
	}
	
	//--notifyChange--//
	public void notifyChange(Object bean, String property) {
		getBinder().notifyChange(bean, property);
	}
	
}
