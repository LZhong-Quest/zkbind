package test3.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;

public class MaxLengthValidator implements Validator {

	public void validate(ValidationContext ctx) {
		Number maxLength = (Number)ctx.getBindContext().getValidatorArg("length");
//		String maxLength = (String)ctx.getBindContext().getValidatorArg("length");
		if (ctx.getProperty().getValue() instanceof String){
			String value = (String)ctx.getProperty().getValue();
			if (value.length() > maxLength.longValue()){
				ctx.setInvalid();
			}
		}else{
			ctx.setInvalid();
		}
	}
}
