package test3.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

public class AgeLimitIndicator implements Converter {

	public Object coerceToUi(Object val, Component component, BindContext ctx) {

		Number limit = (Number)ctx.getConverterArg("limit");
		Integer age = (Integer)val;
		if (age >= limit.longValue()){
			return "Over age "+limit;
		}
		return "Under Age "+limit;
	}
	public Object coerceToBean(Object val, Component component, BindContext ctx) {
		// TODO Auto-generadted method stub
		return null;
	}
}
