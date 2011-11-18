package org.zkoss.bind.examples.spring.validator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;

@Component("creationDateValidator")
@Scope("prototype")
public class CreationDateValidator implements Validator{
	@Autowired
	MessagePool validationMessages;
	public void validate(ValidationContext ctx) {
		Date creation = (Date)ctx.getProperty().getValue();
		if(creation==null){
			ctx.setInvalid();// mark invalid
			validationMessages.put("creationDate", "must not null");
		}else{
			validationMessages.remove("creationDate");
		}
		//notify messages was changed.
		ctx.getBindContext().getBinder().notifyChange(validationMessages, "creationDate");
	}
	
}
