package org.zkoss.bind.examples.spring.order.validator;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.examples.spring.order.util.Messages;

@Component("shippingDateValidator")
@Scope("prototype")
public class ShippingDateValidator implements Validator{
	@Autowired
	Messages messages;
	public void validate(ValidationContext ctx) {
		Date shipping = (Date)ctx.getProperty().getValue();//the main property
		Date creation = (Date)ctx.getProperties("creationDate")[0].getValue();//the collected
		//do mixed validation, shipping date have to large than creation more than 3 days.
		if(!isDayAfter(creation,shipping,3)){
			ctx.setInvalid();
			messages.put("shippingDate", "must large than creation date at least 3 days");
		}else{
			messages.remove("shippingDate");
		}
		//notify the 'price' message in messages was changed.
		ctx.getBindContext().getBinder().notifyChange(messages, "shippingDate");
	}
	
	static public boolean isDayAfter(Date date, Date laterDay , int day) {
		if(date==null) return false;
		if(laterDay==null) return false;
		
		Calendar cal = Calendar.getInstance();
		Calendar lc = Calendar.getInstance();
		
		cal.setTime(date);
		lc.setTime(laterDay);
		
		int cy = cal.get(Calendar.YEAR);
		int ly = lc.get(Calendar.YEAR);
		
		int cd = cal.get(Calendar.DAY_OF_YEAR);
		int ld = lc.get(Calendar.DAY_OF_YEAR);
		
		return (ly*365+ld)-(cy*365+cd) >= day; 
	}
}
