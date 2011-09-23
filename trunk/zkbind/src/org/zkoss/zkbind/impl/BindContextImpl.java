/* BindContextImpl.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:09:16 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.sys.Binding;

/**
 * Implementation of {@link BindContext}.
 * @author henri
 *
 */
public class BindContextImpl implements BindContext {
	private final Binder binder;
	private final Binding binding;
	private final boolean save;
	private final String command;
	private final Component component; //ZK context
	private final Event event; //ZK event
	private final Map<Object, Object> attrs;
	
	public BindContextImpl(Binder binder, Binding binding, boolean save, String command, Component comp, Event event, Map args) {
		this.binder = binder;
		this.binding = binding;
		this.save = save;
		this.command = command;
		this.component = comp;
		this.event = event;
		this.attrs = new HashMap<Object, Object>();
		if (args != null) {
			attrs.putAll(args);
		}
	}
	public Binder getBinder() {
		return this.binder;
	}

	public Binding getBinding() {
		return this.binding;
	}

	public Object getAttribute(Object key) {
		return this.attrs.get(key);
	}

	public Object setAttribute(Object key, Object value) {
		return value == null ? 
				this.attrs.remove(key) : this.attrs.put(key, value);
	}

	public Map<Object, Object> getAttributes() {
		return Collections.unmodifiableMap(attrs); 
	}

	public boolean isSave() {
		return this.save;
	}

	public String getCommandName() {
		return this.command;
	}

	public Component getComponent() {
		return this.component;
	}
	
	public Event getTriggerEvent() {
		return this.event;
	}
}
