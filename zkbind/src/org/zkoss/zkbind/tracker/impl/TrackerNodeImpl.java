/* TrackerNodeImpl.java

	Purpose:
		
	Description:
		
	History:
		Aug 25, 2011 9:24:41 AM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.tracker.impl;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.zkoss.zkbind.impl.WeakHashSet;
import org.zkoss.zkbind.sys.Binding;
import org.zkoss.zkbind.sys.tracker.TrackerNode;
import org.zkoss.zkbind.xel.zel.BindELContext;

/**
 * @author henri
 *
 */
public class TrackerNodeImpl implements TrackerNode {
	private final Object _script; //script of this node (e.g. firstname or ['firstname'])
	private final Map<Object, TrackerNode> _dependents; //kid script -> kid TrackerNode
	private final Set<Binding> _bindings; //associated bindings
	private Object _bean; //associated bean value
	private final Map<Object, Object> _brackets; //property -> bracket script
	
	public TrackerNodeImpl(Object property) {
		_script = property;
		_dependents = new HashMap<Object, TrackerNode>(4);
		_bindings = new WeakHashSet<Binding>(4);
		_brackets = new HashMap<Object, Object>(4);
	}
	
	public TrackerNode getDependent(Object property) {
		TrackerNode kid = getDependent0(property);
		if (kid == null) { //try bracket
			final Object script = _brackets.get(property);
			if (script != null) {
				kid = getDependent0(script);
			}
		}
		return kid;
	}
	
	private TrackerNode getDependent0(Object script) {
		return _dependents.get(script); 
	}

	public void addDependent(Object script, TrackerNode dependent) {
		_dependents.put(script, dependent);
	}
	
	/*package*/ void tieProperty(Object property, Object script) {
		final Object oldscript = _brackets.get(property);
		if (script.equals(oldscript)) {
			return;
		}
		if (oldscript != null) {
			_brackets.remove(property);
		}
		for (final Iterator<Object> it = _brackets.values().iterator(); it.hasNext();) {
			final Object bracket = it.next();
			if (script.equals(bracket)) {
				it.remove();
				break;
			}
		}
		if (property != null) {
			_brackets.put(property, script);
		}
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zkbind.tracker.TrackerNode#removeDependent(java.lang.String)
	 */
	public TrackerNode removeDependent(Object script) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addBinding(Binding binding) {
		_bindings.add(binding);
	}
	
	public Set<Binding> getBindings() {
		return _bindings;
	}

	public Set<TrackerNode> getDependents() {
		final Set<TrackerNode> nodes = new HashSet<TrackerNode>();
		final Set<TrackerNode> kids = getDirectDependents();
		nodes.addAll(kids);
		for(TrackerNode kid : kids) {
			nodes.addAll(kid.getDependents());
		}
		return nodes;
	}

	public Set<TrackerNode> getDirectDependents() {
		return new HashSet<TrackerNode>(_dependents.values());
	}

	public Object getBean() {
		Object bean = _bean == null ? null : ((WeakReference<Object>)_bean).get();
		if (bean == null && _bean != null) { //Help GC
			setBean(null);
		}
		return bean;
	}

	public void setBean(Object bean) {
		_bean = bean == null ? null : new WeakReference<Object>(bean);
	}
	
	public Object getFieldScript() {
		return _script;
	}
	
	/*package*/ Map<Object, Object> getPropNameMapping() {
		return _brackets;
	}
}
