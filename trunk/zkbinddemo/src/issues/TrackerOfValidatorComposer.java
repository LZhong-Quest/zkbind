/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package issues;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.BindComposer;
import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.ValidationContext;
import org.zkoss.zkbind.Validator;

/**
 * @author Dennis Chen
 * 
 */
public class TrackerOfValidatorComposer extends BindComposer {
	private String value1;


	public TrackerOfValidatorComposer() {
		value1 = "A";
	}

	public String getValue1() {
		return value1;
	}

	@NotifyChange
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	
	public Validator getValidator1(){
		return new Validator(){

			public void validate(ValidationContext ctx) {
				
			}};
	}
	
	public Converter getConverter1(){
		return new Converter(){
			public Object coerceToBean(Object val, Component component,
					BindContext ctx) {
				return val;
			}

			public Object coerceToUi(Object val, Component component,
					BindContext ctx) {
				return val;
			}
		};
	}
}
