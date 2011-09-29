package issues;

import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.ValidationContext;
import org.zkoss.zkbind.Validator;

public class PropertyValidator implements Validator{

	public void validate(ValidationContext ctx) {
		Property p = ctx.getProperty();
		Object obj = p.getValue();
		if(obj instanceof Integer){
			int i = (Integer)obj;
			if(i<100 && i>0){
				ctx.setMessage(p,"wrong value range");
			}else{
				ctx.setValid(true);
			}
		}else{
			ctx.setMessage(p,"not an integer");
		}
	}

}
