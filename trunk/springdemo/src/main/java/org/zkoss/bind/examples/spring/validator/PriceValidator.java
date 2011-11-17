package org.zkoss.bind.examples.spring.validator;

import org.springframework.stereotype.Component;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.spring.SpringUtil;

@Component("priceValidator")
public class PriceValidator implements Validator{
	public void validate(ValidationContext ctx) {
		MessagePool validationMessages = (MessagePool)SpringUtil.getBean("messagePool");
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
