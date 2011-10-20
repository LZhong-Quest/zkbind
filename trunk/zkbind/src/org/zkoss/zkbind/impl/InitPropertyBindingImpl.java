/* InitPropertyBindingImpl

	Purpose:
		
	Description:
		
	History:
		Aug 1, 2011 2:43:33 PM, Created by dennis

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.LoadPropertyBinding;

/**
 * Implementation of {@link LoadPropertyBinding}.
 * @author Dennis
 */
public class InitPropertyBindingImpl extends PropertyBindingImpl implements
		LoadPropertyBinding {
	
	public InitPropertyBindingImpl(Binder binder, Component comp,
		String attr, String loadScript, String converter, 
		Map<String, Object> args,Map<String, Object> converterArgs) {
		
		super(binder, comp, "self."+attr, loadScript, converter, args, converterArgs);
	}
	
	@Override
	protected boolean ignoreTracker(){
		//init only loaded once, so it don't need to add to tracker.
		return true;
	}
	
	public void load(BindContext ctx) {
		final Component comp = ctx.getComponent();
		final BindEvaluatorX eval = getBinder().getEvaluatorX();
		
		//get data from property
		Object value = eval.getValue(ctx, comp, _accessInfo.getProperty());
		
		//use _converter to convert type if any
		final Converter conv = getConverter();
		if (conv != null) {
			value = conv.coerceToUi(value, comp, ctx);
		}
		//set data into component attribute
		eval.setValue(null, comp, _fieldExpr, value);
	}
}
