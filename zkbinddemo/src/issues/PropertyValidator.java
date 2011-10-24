package issues;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;

public class PropertyValidator implements Validator{

	public void validate(ValidationContext ctx) {
		Property p = ctx.getProperty();
		Object obj = p.getValue();
		if(obj instanceof Integer){
			int i = (Integer)obj;
			if(i<100 && i>0){
//				ctx.setMessage(p,"wrong value range");
				ctx.setInvalid();
			}
		}else{
//			ctx.setMessage(p,"not an integer");
			ctx.setInvalid();
		}
	}

}
