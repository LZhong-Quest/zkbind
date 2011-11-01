package test3.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;

public class UpperBoundValidator implements Validator {

	public void validate(ValidationContext ctx) {
		Number upperBound = (Number)ctx.getBindContext().getValidatorArg("max");
		if (ctx.getProperty().getValue() instanceof Number){
			Number value = (Number)ctx.getProperty().getValue();
			if (value.longValue() > upperBound.longValue()){
				ctx.setInvalid();
			}
		}else{
			ctx.setInvalid();
		}
	}
}
