/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package test2;

import java.util.Set;

import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.ValidationContext;
import org.zkoss.zkbind.Validator;

/**
 * @author dennis
 * 
 */
public class ValidationComposer extends GenericBindComposer {
	private int value1;
	private String value2;
	
	public ValidationComposer() {
		addValidator("validator1",new Validator(){
			public void validate(ValidationContext ctx) {
				if(!ctx.isValid()) return;
				Property p = ctx.getProperty();
				Object val = p.getValue();
				if(val!=null && Integer.parseInt(val.toString())>10){
					setLastMessage1(null);
				}else{
					ctx.setFail();
					setLastMessage1("value 1 have to large than 10");
				}
			}			
		});
		addValidator("validator2",new Validator(){
			public void validate(ValidationContext ctx) {
				if(!ctx.isValid()) return;
				Property p = ctx.getProperty();
				Object val = p.getValue();
				if(val!=null && Integer.parseInt(val.toString())>20){
					setLastMessage2(null);
				}else{
					ctx.setFail();
					setLastMessage2("value 2 have to large than 20");
				}
			}			
		});
		addValidator("validator3",new Validator(){
			public void validate(ValidationContext ctx) {
				if(!ctx.isValid()) return;
				Object val1=null;
				Object val2=null;
				for(Property p :ctx.getProperties()){
					String prop = p.getProperty();
					if("value1".equals(prop)){
						val1 = p.getValue();
					}else if("value2".equals(prop)){
						val2 = p.getValue();
					}
				}
				if(val1 == null){
					setLastMessage1("value1 must not empty");
				}else if(Integer.parseInt(val1.toString())>10){
					setLastMessage1("value1 have to large than 10");
				}else{
					setLastMessage1(null);
				}
				
				if(val2 == null){
					setLastMessage2("value2 must not empty");
				}else if(Integer.parseInt(val2.toString())>20){
					setLastMessage2("value2 have to large than 20");
				}else{
					setLastMessage2(null);
				}
			}			
		});
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
		notifyChange(this, "lastMessage1");
	}
	
	
	
	

	public String getLastMessage2() {
		return lastMessage2;
	}

	public void setLastMessage2(String lastMessage2) {
		this.lastMessage2 = lastMessage2;
		notifyChange(this, "lastMessage2");
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
			}else if("value2".equals(prop)){
				if(val!=null && Integer.parseInt(val.toString())>20){
					setLastMessage2(null);
				}else{
					r &= false;
					setLastMessage2("value 2 have to large than 20");
				}
			}
		}
		return r;
	}

	public void cmd1(){
		
	}
	
	public void cmd2(){
		
	}
}
