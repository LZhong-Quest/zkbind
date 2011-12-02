package org.zkoss.bind.examples.spring.order.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.examples.spring.order.util.Messages;

@Component("priceValidator")
@Scope("prototype")
public class PriceValidator implements Validator{
	@Autowired
	Messages messages;
	public void validate(ValidationContext ctx) {
		Number price = (Number)ctx.getProperty().getValue();
		if(price==null || price.doubleValue()<=0){
			ctx.setInvalid(); // mark invalid
			messages.put("price", "must large than 0");
		}else{
			messages.remove("price");
		}
		//notify messages was changed.
		ctx.getBindContext().getBinder().notifyChange(messages, "price");
	}
}
