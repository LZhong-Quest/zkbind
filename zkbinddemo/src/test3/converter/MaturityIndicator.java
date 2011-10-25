package test3.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

public class MaturityIndicator implements Converter {

	public Object coerceToUi(Object val, Component component, BindContext ctx) {

		Integer age = (Integer)val;
		if (age >= 18){
			return "Adult";
		}
		return "Under Age";
	}
	public Object coerceToBean(Object val, Component component, BindContext ctx) {
		// TODO Auto-generadted method stub
		return null;
	}
}
