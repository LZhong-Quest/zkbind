/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package test2;

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

/**
 * @author henri
 * 
 */
public class ValidationComposer extends GenericBindComposer {
	private int value1;
	public ValidationComposer() {
	}
	
	public int getValue1() {
		return value1;
	}

	@NotifyChange
	public void setValue1(int value1) {
		this.value1 = value1;
	}
	
	private String lastMessage;
	
	public String getLastMessage() {
		return lastMessage;
	}

	public boolean validate(String cmd,Set<Property> ps, BindContext ctx){
		System.out.println(">>>validate "+cmd+",prop:"+ps);
		for(Property p :ps){
			Object base = p.getBase();
			String prop = p.getProperty();
			Object val = p.getValue();
			if("value1".equals(prop)){
				if(Integer.parseInt(val.toString())>10){
					return true;
				}
			}
		}
		
		//
		return false;
	}

	public void cmd1(){
		
	}
}
