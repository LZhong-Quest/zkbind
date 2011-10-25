package test3.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;

public class NonNegativeValidator implements Validator {

	public void validate(ValidationContext ctx) {
		
		if (ctx.getProperty().getValue() instanceof Integer){
			Integer value = (Integer)ctx.getProperty().getValue();
			if (value < 0){
				ctx.setInvalid();
			}
		}else{
			ctx.setInvalid();
		}
	}

}
