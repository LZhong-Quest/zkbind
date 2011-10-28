package test3.validator;

import java.util.Date;
import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;

public class DurationValidator implements Validator {

	public void validate(ValidationContext ctx) {
		String startProperty = (String)ctx.getBindContext().getValidatorArg("start");
		String endProperty = (String)ctx.getBindContext().getValidatorArg("end");
		Map<String, Property[]> properties = ctx.getProperties();
		Date startDate = (Date)properties.get(startProperty)[0].getValue();
		Date endDate = (Date)properties.get(endProperty)[0].getValue();
		if (startDate==null || endDate==null || endDate.before(startDate)){
			ctx.setInvalid();
		}
	}
}
