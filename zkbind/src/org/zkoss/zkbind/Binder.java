/* Binder.java

	Purpose:
		
	Description:
		
	History:
		Jun 22, 2011 9:54:23 AM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.zkbind;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.tracker.Tracker;

/**
 * The Binder that do the data binding things.
 * @author henrichen
 *
 */
public interface Binder {
	/**
	 * Returns the {@link BindEvaluatorX} used by this Binder. 
	 * @return the EvaluatorX.
	 */
	public BindEvaluatorX getEvaluatorX();

	/**
	 * Add a new command bindins.
	 * @param comp the associated component
	 * @param evtnm the associated component event name
	 * @param commandExpr the command expression
	 * @param args other key-value pairs.
	 */
	public void addCommandBinding(Component comp, String evtnm, String commandExpr, Map<String, Object> args);
	
	/**
	 * Add new form Bindings. 
	 * @param comp the associated component
	 * @param _fieldExpr the associated attribute of the component
	 * @param loadExprs load expressions
	 * @param saveExprs save expressions
	 * @param confirmExprs confirm expressions
	 * @param validate the provided  validate expression; true to do validation.
	 * @param args other key-value pairs. 
	 */
	public void addFormBindings(Component comp, String id, 
			String[] loadExprs, String[] saveExprs, String[] confirmExprs, String validateExprs, Map<String, Object> args);

	/**
	 * Add new property Bindings.
	 * @param comp the associated component
	 * @param _fieldExpr the associated attribute of the component
	 * @param loadExprs load expressions
	 * @param saveExprs save expressions
	 * @param _converter the provided _converter expression; null to ignore it.
	 * @param validate the provided  validate expression; true to do validation.
	 * @param args other key-value pairs. 
	 */
	public void addPropertyBinding(Component comp, String attr, 
			String[] loadExprs, String[] saveExprs, String converterExprs, String validateExprs, Map<String, Object> args);

	/**
	 * Remove all managed bindings that associated with the specified component.
	 * @param comp
	 */
	public void removeBindings(Component comp);
	
	/**
	 * Remove all managed Binding that associated with the specified 
	 * component and attribute name, event name, or form id. 
	 * @param comp the associated component
	 * @param key the associated attribute name, event name, or form id
	 */
	public void removeBindings(Component comp, String key);
	
	/**
	 * Returns the _converter of the given _converter name.
	 * @param name _converter name
	 * @return the _converter of the given _converter name.
	 */
	public Converter getConverter(String name);
	
	/**
	 * Returns the _validator of the given _validator name.
	 * @param name _validator name
	 * @return the _validator of the given _validator name.
	 */
	public Validator getValidator(String name);
	
	/**
	 * Notify change of the property.
	 * @param bean the backing bean object.
	 * @param property the property of the bean that change the value 
	 */
	public void notifyChange(Object bean, String property);
	
	
	/**
	 * Notify command fired to this binder.
	 * @param command command name
	 * @param args , arguments when notifing this command, it will be passed as a arguments of execution method of vm
	 */
	public void notifyCommand(String command, Map<String, Object> args);
	
	/**
	 * Returns associated ViewModel of this binder.
	 * @return associated ViewModel of this binder.
	 */
	public Object getViewModel();
	
	/**
	 * Sets associated ViewModel of this binder.
	 * @param viewModel the associated view model of this binder.
	 */
	public void setViewModel(Object viewModel);

	/**
	 * Sets the associated phase listener to intervene the binding life cycle.
	 * @param listener the associated phase listener.
	 */
	public void setPhaseListener(PhaseListener listener);
	
	/**
	 * Returns associated dependency tracker of this binder.
	 * @return associated dependency tracker of this binder.
	 */
	public Tracker getTracker();
}
