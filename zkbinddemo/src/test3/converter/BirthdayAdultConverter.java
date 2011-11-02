package test3.converter;

import java.util.Calendar;
import java.util.Date;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

public class BirthdayAdultConverter implements Converter{

	public Object coerceToUi(Object val, Component component, BindContext ctx) {
		return val;
	}

	public Object coerceToBean(Object val, Component component, BindContext ctx) {
		
		Calendar adultDay = Calendar.getInstance();
		adultDay.setTime((Date)val);
		adultDay.add(Calendar.YEAR, 18);
		Calendar now = Calendar.getInstance();
		
		if (now.after(adultDay)){
			return true;
		}
		return false;
	}

}
