package org.zkoss.bind.examples.spring.validator;

import org.springframework.stereotype.Component;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.spring.SpringUtil;

@Component("quantityValidator")
public class QuantityValidator implements Validator{
	public void validate(ValidationContext ctx) {
		MessagePool validationMessages = (MessagePool)SpringUtil.getBean("messagePool");
		Integer quantity = (Integer)ctx.getProperty().getValue();
		if(quantity==null || quantity<=0){
			ctx.setInvalid();// mark invalid
			validationMessages.put("quantity", "must large than 0");
		}else{
			validationMessages.remove("quantity");
		}
		//notify messages was changed.
		ctx.getBindContext().getBinder().notifyChange(validationMessages, "quantity");
	}
}
