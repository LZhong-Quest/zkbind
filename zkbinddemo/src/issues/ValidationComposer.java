/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package issues;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.DependsOn;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.ValidationContext;
import org.zkoss.zkbind.validator.impl.ValidationContextImpl;

/**
 * @author henrichen
 * 
 */
public class ValidationComposer extends GenericBindComposer {
	private int value1;
	private String value2;
	public ValidationComposer() {
	}
	
	public int getValue1() {
		return value1;
	}
	

	public boolean validate(String cmd,Set<Property> ps, BindContext ctx){
		ValidationContext vctx = new ValidationContextImpl(cmd, ps.size()>0?ps.iterator().next():null,ps, ctx,true);
		new PropertyValidator().validate(vctx);
		
		return vctx.isValid();
	}

	public void cmd1(){
		
	}
	
	public void cmd2(){
		
	}
}
