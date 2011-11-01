package test3.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;

public class BooleanValidator implements Validator {

	public void validate(ValidationContext ctx) {
		
		if (!(ctx.getProperty().getValue() instanceof Boolean)){
				ctx.setInvalid();
		}
	}

}
