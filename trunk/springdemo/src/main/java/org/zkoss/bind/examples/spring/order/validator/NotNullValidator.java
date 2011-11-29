package org.zkoss.bind.examples.spring.order.validator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.examples.spring.order.util.Messages;

@Component("notNullValidator")
@Scope("prototype")
public class NotNullValidator implements Validator{
	@Autowired
	Messages validationMessages;
	public void validate(ValidationContext ctx) {
		Date creation = (Date)ctx.getProperty().getValue();
		if(creation==null){
			ctx.setInvalid();// mark invalid
			validationMessages.put("creationDate", "must not null ");
		}else{
			validationMessages.remove("creationDate");
		}
		//notify messages was changed.
		ctx.getBindContext().getBinder().notifyChange(validationMessages, "creationDate");
	}
	
}