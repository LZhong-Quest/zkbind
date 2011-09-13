/* BindComboitemRenderer.java

	Purpose:
		
	Description:
		
	History:
		Aug 17, 2011 3:47:56 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import org.zkoss.lang.Objects;
import org.zkoss.xel.VariableResolverX;
import org.zkoss.xel.XelContext;
import org.zkoss.xel.XelException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zkbind.Binder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 * comboitem renderer for binding.
 * @author henri
 *
 */
public class BindComboitemRenderer implements ComboitemRenderer {
	public void render(final Comboitem item, final Object data) throws Exception {
		final Combobox cb = (Combobox)item.getParent();
		final Template tm = cb.getTemplate("model");
		if (tm == null) {
			item.setLabel(Objects.toString(data));
			item.setValue(data);
		} else {
			final String varnm = (String) cb.getAttribute(BinderImpl.VAR); //see BinderImpl#initRendererIfAny
			final Component[] items = tm.create(cb, item,
				new VariableResolverX() {
					public Object resolveVariable(String name) {
						//shall never call here
						return null;
					}

					public Object resolveVariable(XelContext ctx, Object base, Object name) throws XelException {
						if (base == null) {
							return varnm.equals(name) ? data : null;
						} else if (base.equals(data)) {
							return "index".equals(name) ? Integer.valueOf(item.getIndex()) : null;
						}
						return null;
					}
				});
			if (items.length != 1)
				throw new UiException("The model template must have exactly one item, not "+items.length);

			final Comboitem nci = (Comboitem)items[0];
			
			//apply binding
			nci.setAttribute(varnm, data); //kept the value
			final Binder binder = (Binder) cb.getAttribute(BinderImpl.BINDER, true);
			new AnnotateBinderHelper(binder).initComponentBindings(nci); //parse binding meta-info of this comboitem
			((BinderImpl)binder).loadComponent(nci); //evaluate binding of this comboitem

			if (nci.getValue() == null) //template might set it
				nci.setValue(data);
			item.setAttribute("org.zkoss.zul.model.renderAs", nci);
				//indicate a new item is created to replace the existent one
			item.detach();
		}
	}
}
