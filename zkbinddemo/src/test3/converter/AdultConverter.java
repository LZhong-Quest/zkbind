package test3.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

public class AdultConverter implements Converter{

	public Object coerceToUi(Object val, Component component, BindContext ctx) {
		return val;
	}

	public Object coerceToBean(Object val, Component component, BindContext ctx) {
		
		Integer age = (Integer)val;
		if (age >= 18){
			return new Boolean(true);
		}
		return new Boolean(false);
	}

}
