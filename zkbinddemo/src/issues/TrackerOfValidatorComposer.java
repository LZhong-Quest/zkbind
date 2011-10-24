/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package issues;

import org.zkoss.bind.BindComposer;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.zk.ui.Component;

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
