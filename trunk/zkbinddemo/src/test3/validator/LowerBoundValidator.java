package test3.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;

public class LowerBoundValidator implements Validator {

	public void validate(ValidationContext ctx) {
		Number lowerBound = (Number)ctx.getBindContext().getValidatorArg("min");
		if (ctx.getProperty().getValue() instanceof Number){
			Number value = (Number)ctx.getProperty().getValue();
			if (value.longValue() < lowerBound.longValue()){
				ctx.setInvalid();
			}
		}else{
			ctx.setInvalid();
		}
	}
}
