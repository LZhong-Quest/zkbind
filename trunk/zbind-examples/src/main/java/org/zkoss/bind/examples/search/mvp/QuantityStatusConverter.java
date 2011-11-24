package org.zkoss.bind.examples.search.mvp;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class QuantityStatusConverter implements TypeConverter{
	
	public Object coerceToBean(Object val, Component component) {
		return null;//never called in this example
	}

	public Object coerceToUi(Object val, Component component) {
		if(val instanceof Integer){
			Integer quantity = (Integer)val;
			if (quantity < 3){
				return "red";
			}else{
				return "";
			}
		}else{
			return val;
		}
	}
}
