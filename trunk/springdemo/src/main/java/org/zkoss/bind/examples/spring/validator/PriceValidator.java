package org.zkoss.bind.examples.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;

@Component("priceValidator")
@Scope("prototype")
public class PriceValidator implements Validator{
	@Autowired
	MessagePool validationMessages;
	public void validate(ValidationContext ctx) {
		Double price = (Double)ctx.getProperty().getValue();
		if(price==null || price<=0){
			ctx.setInvalid(); // mark invalid
			validationMessages.put("price", "must large than 0");
		}else{
			validationMessages.remove("price");
		}
		//notify messages was changed.
		ctx.getBindContext().getBinder().notifyChange(validationMessages, "price");
	}
}
