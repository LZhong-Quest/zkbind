/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package test2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.ValidationContext;
import org.zkoss.zkbind.Validator;
import org.zkoss.zkbind.DependsOn;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;

/**
 * @author Dennis Chen
 * 
 */
public class F0011 extends GenericBindComposer {
	private Date bday1;
	private Date bday2;
	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private String message1;
	private String message2;
	private String message3;
	private String message4;
	private String message5;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private Validator validator2;
	public F0011() {
		bday1 = new Date();
		addValidator("validator1", new Validator() {
			public void validate(ValidationContext ctx) {
				Property p = ctx.getProperty();
				Date d = (Date)p.getValue();
				if(d==null){
					setMessage1("date "+p.getProperty()+" is empty");
					ctx.setInvalid();
					return;
				}
				String today = sdf.format(new Date());
				if(sdf.format(d).compareTo(today)>=0){
					setMessage1("date "+p.getProperty()+" must small than today");
					ctx.setInvalid();
					return;
				}
				setMessage1("");
			}
		});
		validator2 = new Validator() {
			public void validate(ValidationContext ctx) {
				Property p = ctx.getProperty();
				Date d = (Date)p.getValue();
				if(d==null){
					//allow empty
					return;
				}
				String today = sdf.format(new Date());
				if(sdf.format(d).compareTo(today)<=0){
					setMessage2("date "+p.getProperty()+" must large than today");
					ctx.setInvalid();
					return;
				}
				setMessage2("");
			}
		};
		addValidator("validator31", new Validator() {
			public void validate(ValidationContext ctx) {
				if(!ctx.isValid()){
					return;
				}
				Property p = ctx.getProperty();
				String v1 = (String)p.getValue();
				if(v1==null || "".equals(v1.trim())){
					setMessage3(p.getProperty()+" is empty");
					ctx.setInvalid();
					return;
				}
				setMessage3("");
			}
		});
		addValidator("validator32", new Validator() {
			public void validate(ValidationContext ctx) {
				if(!ctx.isValid()){
					return;
				}
				Property p = ctx.getProperty();
				String v2 = (String)p.getValue();
				String v1 = (String)ctx.getProperties().get("value1")[0].getValue();
				if(v1!=v2 && (v1!=null && !v1.equals(v2))){
					setMessage3("value2 must euqlas to value 1");
					ctx.setInvalid();
					return;
				}
				setMessage3("");
				
			}
		});
		addValidator("validator41", new Validator() {
			public void validate(ValidationContext ctx) {
				if(!ctx.isValid()){
					return;
				}
				Property p = ctx.getProperty();
				String val = (String)p.getValue();
				if(val==null || "".equals(val.trim())){
					setMessage4(p.getProperty()+" is empty");
					ctx.setInvalid();
					return;
				}
				setMessage4("");
			}
		});
		addValidator("validator4", new Validator() {
			public void validate(ValidationContext ctx) {;
				if(!ctx.isValid()){
					return;
				}
				String v3 = (String)ctx.getProperties().get("value3")[0].getValue();;
				String v4 = (String)ctx.getProperties().get("value4")[0].getValue();;
				
				//
				if(v3==null || "".equals(v3.trim())){
					setMessage4("value3 is empty");
					ctx.setInvalid();
					return;
				}
				
				if(v4==null || "".equals(v4.trim())){
					setMessage4("value4 is empty");
					ctx.setInvalid();
					return;
				}
				
				if(v3!=v4 && (v3!=null && !v3.equals(v4))){
					setMessage4("value4 must euqlas to value 3");
					ctx.setInvalid();
					return;
				}
				setMessage4("");
			}
		});
		addValidator("validator5", new Validator() {
			public void validate(ValidationContext ctx) {
				if(!ctx.isValid()){
					return;
				}
				Property p = ctx.getProperty();
				String v2 = (String)p.getValue();
				String v1 = (String)ctx.getProperties().get("value1")[0].getValue();
				if(v1!=v2 && (v1!=null && !v1.equals(v2))){
					setMessage5("value2 must euqlas to value 1");
					ctx.setInvalid();
					return;
				}
				setMessage5("");
				
			}
		});		
	}
	
	public String getMessage1() {
		return message1;
	}

	void setMessage1(String message1) {
		this.message1 = message1;
		getBinder().notifyChange(this, "message1");
	}
	
	

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
		getBinder().notifyChange(this, "message2");
	}

	public String getMessage3() {
		return message3;
	}

	public void setMessage3(String message3) {
		this.message3 = message3;
		getBinder().notifyChange(this, "message3");
	}
	
	public String getMessage4() {
		return message4;
	}

	public void setMessage4(String message4) {
		this.message4 = message4;
		getBinder().notifyChange(this, "message4");
	}
	public String getMessage5() {
		return message5;
	}

	public void setMessage5(String message5) {
		this.message5 = message5;
		getBinder().notifyChange(this, "message5");
	}	

	public Validator getValidator2(){
		return validator2;
	}


	public Date getBday1() {
		return bday1;
	}

	@NotifyChange
	public void setBday1(Date bday1) {
		this.bday1 = bday1;
	}
	
	public Date getBday2() {
		return bday2;
	}

	@NotifyChange
	public void setBday2(Date bday2) {
		this.bday2 = bday2;
	}

	public String getValue1() {
		return value1;
	}

	@NotifyChange
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	@NotifyChange
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	
	
	
	public String getValue3() {
		return value3;
	}
	@NotifyChange
	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}
	@NotifyChange
	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public void cmd1(){
		setMessage3("do Command1");
	}
	
	public void cmd2(){
		setMessage4("do Command2");
	}
	
	public void cmd3(){
		setMessage5("do Command3");
	}
	
}