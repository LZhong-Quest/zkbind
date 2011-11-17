package org.zkoss.bind.examples.spring.validator;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.spring.SpringUtil;

@Component("creationDateValidator")
public class CreationDateValidator implements Validator{
	public void validate(ValidationContext ctx) {
		MessagePool validationMessages = (MessagePool)SpringUtil.getBean("messagePool");
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
