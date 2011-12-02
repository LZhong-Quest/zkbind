package org.zkoss.bind.examples.spring.order.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.examples.spring.order.util.Messages;

@Component("quantityValidator")
@Scope("prototype")
public class QuantityValidator implements Validator{
	@Autowired
	Messages messages;
	public void validate(ValidationContext ctx) {
		Integer quantity = (Integer)ctx.getProperty().getValue();
		if(quantity==null || quantity<=0){
			ctx.setInvalid();// mark invalid
			messages.put("quantity", "must large than 0");
		}else{
			messages.remove("quantity");
		}
		//notify messages was changed.
		ctx.getBindContext().getBinder().notifyChange(messages, "quantity");
	}
}
