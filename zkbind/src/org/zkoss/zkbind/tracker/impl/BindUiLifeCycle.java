/* BindUiLifeCycle.java

	Purpose:
		
	Description:
		
	History:
		Sep 2, 2011 1:19:14 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.tracker.impl;

import java.util.Collection;
import java.util.Iterator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.UiLifeCycle;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.impl.AnnotateBinderHelper;
import org.zkoss.zkbind.impl.BinderImpl;
import org.zkoss.zkbind.sys.tracker.Tracker;

/**
 * Track Binding CRUD and dependent tracking management. 
 * @author henrichen
 */
public class BindUiLifeCycle implements UiLifeCycle {
	public void afterComponentAttached(Component comp, Page page) {
		if (comp.getDesktop() != null) {
			//check if this component already binded
			final Binder selfBinder = (Binder) comp.getAttribute(BinderImpl.BINDER);
			if (selfBinder == null) {
				//check if parent exists any binder
				final Binder binder = (Binder) comp.getAttribute(BinderImpl.BINDER, true);
				if (binder != null) {
					//try to load and init
					new AnnotateBinderHelper(binder).initComponentBindings(comp);
					((BinderImpl)binder).loadComponent(comp);
				}
			}
		}
	}

	public void afterComponentDetached(Component comp, Page prevpage) {
		removeBindings(comp);
	}

	public void afterComponentMoved(Component parent, Component child,
			Component prevparent) {
		//do nothing
	}

	public void afterPageAttached(Page page, Desktop desktop) {
		//do nothing
	}

	public void afterPageDetached(Page page, Desktop prevdesktop) {
		if (prevdesktop != null) {
			final Collection comps = prevdesktop.getComponents();
			for(final Iterator<Component> it = comps.iterator(); it.hasNext();) {
				final Component comp = it.next();
				removeBindings0(comp); 
			}
		}
	}
	private void removeBindings(Component comp) {
		removeBindings0(comp);
		for(final Iterator<Component> it = comp.getChildren().iterator(); it.hasNext();) {
			final Component kid = it.next();
			if (kid != null) {
				removeBindings(kid); //recursive
			}
		}
	}
	
	private void removeBindings0(Component comp) {
		final Binder binder = (Binder) comp.getAttribute(BinderImpl.BINDER);
		if (binder != null) {
			binder.removeBindings(comp);
		}
	}
	
}
