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
import org.zkoss.zkbind.BindComposer;
import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.ValidationContext;
import org.zkoss.zkbind.Validator;

/**
 * @author dennis
 * 
 */
public class ValidationComposer extends BindComposer {
	private int value1;
	private String value2;
	
	public ValidationComposer() {
		addValidator("validator1",new Validator(){
			@NotifyChange("lastMessage1")
			public void validate(ValidationContext ctx) {
				if(!ctx.isValid()) return;
				Property p = ctx.getProperty();
				Object val = p.getValue();
				if(val!=null && Integer.parseInt(val.toString())>10){
					setLastMessage1(null);
				}else{
					ctx.setInvalid();
					setLastMessage1("value 1 have to large than 10");
				}
			}			
		});
		addValidator("validator2",new Validator(){
			@NotifyChange("lastMessage2")
			public void validate(ValidationContext ctx) {
				if(!ctx.isValid()) return;
				Property p = ctx.getProperty();
				Object val = p.getValue();
				if(val!=null && Integer.parseInt(val.toString())>20){
					setLastMessage2(null);
				}else{
					ctx.setInvalid();
					setLastMessage2("value 2 have to large than 20");
				}
			}			
		});
		addValidator("validator3",new Validator(){
			@NotifyChange({"lastMessage1","lastMessage2"})
			public void validate(ValidationContext ctx) {
				if(!ctx.isValid()) return;
				Object val1 = ctx.getProperties().get("value1")[0].getValue();//ctx.getPropertyValue("value1");
				Object val2 = ctx.getProperties().get("value2")[0].getValue();//ctx.getPropertyValue("value2");//null;
				Object form = ctx.getProperties().get(".")[0].getValue();//ctx.getPropertyValue(".");
				
				if(val1 == null){
					ctx.setInvalid();
					setLastMessage1("value1 must not empty");
				}else if(Integer.parseInt(val1.toString())>10){
					setLastMessage1(null);
				}else{
					ctx.setInvalid();
					setLastMessage1("value1 have to large than 10");
					
				}
				
				if(val2 == null){
					ctx.setInvalid();
					setLastMessage2("value2 must not empty");
				}else if(Integer.parseInt(val2.toString())>20){
					setLastMessage2(null);
				}else{
					ctx.setInvalid();
					setLastMessage2("value2 have to large than 20");
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

	public void setLastMessage1(String lastMessage1) {
		this.lastMessage1 = lastMessage1;
	}

	public String getLastMessage2() {
		return lastMessage2;
	}

	public void setLastMessage2(String lastMessage2) {
		this.lastMessage2 = lastMessage2;
	}

	public void cmd1(){
		
	}
	
	public void cmd2(){
		
	}
	
	
}
