/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henrichen

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

	@NotifyChange
	public void setValue1(int value1) {
		this.value1 = value1;
	}
	
	
	
	public String getValue2() {
		return value2;
	}

	@NotifyChange
	public void setValue2(String value2) {
		this.value2 = value2;
	}



	private String lastMessage1;
	private String lastMessage2;
	
	public String getLastMessage1() {
		return lastMessage1;
	}

	@NotifyChange
	public void setLastMessage1(String lastMessage1) {
		this.lastMessage1 = lastMessage1;
	}
	
	
	
	

	public String getLastMessage2() {
		return lastMessage2;
	}

	public void setLastMessage2(String lastMessage2) {
		this.lastMessage2 = lastMessage2;
	}

	public boolean validate(String cmd,Set<Property> ps, BindContext ctx){
		System.out.println(">>>validate "+cmd+",prop:"+ps);
		boolean r = true;
		for(Property p :ps){
			Object base = p.getBase();
			String prop = p.getProperty();
			Object val = p.getValue();
			if("value1".equals(prop)){
				if(val!=null && Integer.parseInt(val.toString())>10){
					setLastMessage1(null);
				}else{
					r &= false;
					setLastMessage1("value 1 have to large than 10");
				}
				
				//TODO notify change not work, it only work by call by EL, we have to notify it manually
				notifyChange(this, "lastMessage1");
			}else if("value2".equals(prop)){
				if(val!=null && Integer.parseInt(val.toString())>20){
					setLastMessage2(null);
				}else{
					r &= false;
					setLastMessage2("value 2 have to large than 20");
				}
				//TODO notify change not work, it only work by call by EL, we have to notify it manually
				notifyChange(this, "lastMessage2");
			}
		}
		return r;
	}

	public void cmd1(){
		
	}
	
	public void cmd2(){
		
	}
}
