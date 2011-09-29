/* SelectedTreeitemConverter.java

	Purpose:
		
	Description:
		
	History:
		Aug 17, 2011 6:10:20 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.converter.sys;

import java.util.Iterator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.impl.BinderImpl;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

/**
 * Convert tree selected treeitem to bean and vice versa.
 * @author henrichen
 *
 */
public class SelectedTreeitemConverter implements Converter, java.io.Serializable {
	private static final long serialVersionUID = 201109261823L;
	
	public Object coerceToUi(Object val, Component comp, BindContext ctx) {
		Tree tree = (Tree) comp;
	  	if (val != null) {
	  		final TreeModel model = tree.getModel();
	  		if (model != null) {
		  		final String varnm = (String) tree.getAttribute(BinderImpl.VAR);
		  		if (varnm != null) { //There is binding on template
		  			//TODO might be done with dependency tracker, bean -> listitem (implicit binding)
		  			//iterate to find the selected item
		  			for (final Iterator it = tree.getItems().iterator(); it.hasNext();) {
		  				final Treeitem li = (Treeitem) it.next();
		  				final Object bean = li.getAttribute(varnm);
		  				if (val.equals(bean)) {
		  					return li;
		  				}
		  			}
		  			//not in the item list
		  			return null;
		  		}
	  		}
	  		//no model or no binding, assume Treeitem.value to be used with selectedItem
  			//iterate to find the selected item
  			for (final Iterator it = tree.getItems().iterator(); it.hasNext();) {
  				final Treeitem li = (Treeitem) it.next();
  				if (val.equals(li.getValue())) {
  					return li;
  				}
  			}
	  	}
	  	return null;
	}

	public Object coerceToBean(Object val, Component comp, BindContext ctx) {
	  	if (val != null) {
		  	final Tree tree = (Tree) comp;
	  		final TreeModel model = tree.getModel();
	  		if (model != null) {
		  		final String varnm = (String) tree.getAttribute("$VAR$");
		  		if (varnm != null) { //There is binding on template
		  			return ((Treeitem) val).getAttribute(varnm);
		  		} else { //no template, assume Treeitem.value to be used with selectedItem
		  			return ((Treeitem) val).getValue();
		  		}
	  		} else { //no model case, assume Treeitem.value to be used with selectedItem
	  			return ((Treeitem) val).getValue();
	  		}
	  	}
	 	return null;
	}

}
