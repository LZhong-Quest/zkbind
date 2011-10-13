/* 

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package test2;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.Form;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.SimpleForm;

/**
 * @author Dennis Chen
 * 
 */
public class F0002 extends GenericBindComposer {
	private String value1;
	private String value2;
	private Form form1;

	public F0002() {	
		value1 = "A";
		value2 = "B";
		form1 = new SimpleForm();
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

	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	
	
	public Form getForm1() {
		return form1;
	}


	public void cmd1(){
		
	}
	
	@NotifyChange({"value2","formValue2"})
	public void cmd2(){
		
	}
	
	public String getFormValue2(){
		return (String)form1.getField("value2");
	}
}
