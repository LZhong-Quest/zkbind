package org.zkoss.bind.examples.spring.order.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.examples.spring.order.util.Messages;

@Component("positiveNumberValidator")
@Scope("prototype")
public class PositiveNumberValidator implements Validator{
	@Autowired
	Messages validationMessages;
	public void validate(ValidationContext ctx) {
		Number price = (Number)ctx.getProperty().getValue();
		if(price==null || price.doubleValue()<=0){
			ctx.setInvalid(); // mark invalid
			validationMessages.put("price", "must large than 0");
		}else{
			validationMessages.remove("price");
		}
		//notify messages was changed.
		ctx.getBindContext().getBinder().notifyChange(validationMessages, "price");
	}
}