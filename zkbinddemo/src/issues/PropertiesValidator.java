package issues;

import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.ValidationContext;
import org.zkoss.zkbind.Validator;

public class PropertiesValidator implements Validator{

	public void validate(ValidationContext ctx) {
		
		for(Property p:ctx.getProperties()){
			Object obj = p.getValue();
			if(obj instanceof Integer){
				int i = (Integer)obj;
				if(i<100 && i>0){
//					ctx.setMessage(p,"wrong value range");
					ctx.setFail();
				}
			}else{
//				ctx.setMessage(p,"not an integer");
				ctx.setFail();
			}
		}
	}

}
