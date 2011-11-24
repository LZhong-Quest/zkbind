package org.zkoss.bind.examples.search.mvp;

import java.text.DecimalFormat;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class TotalPriceConverter implements TypeConverter{
	
	public Object coerceToBean(Object val, Component component) {
		return null;//never called in this example
	}

	public Object coerceToUi(Object val, Component component) {
		if(val==null) return null;
		String str = new DecimalFormat("$ ###,###,###,##0.00").format((Double)val);
		return str;
	}
}
