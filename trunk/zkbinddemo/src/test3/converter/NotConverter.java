package test3.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

/*
 * reverse the boolean value
 */
public class NotConverter implements Converter{

	public Object coerceToUi(Object val, Component component, BindContext ctx) {
		return !(Boolean)val;
	}

	public Object coerceToBean(Object val, Component component, BindContext ctx) {
		
		return !(Boolean)val;
	}

}
