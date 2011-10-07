/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package test2;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;

/**
 * @author Dennis Chen
 * 
 */
public class ArgsComposer extends GenericBindComposer {
	private String value1;
	private String value2;


	public ArgsComposer() {
		value1 = "A";
		value2 = "B";
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
	
	
	public String getMyArg1(){
		return "myarg1";
	}
	
	public String getMyArg2(){
		return "myarg2";
	}
	
	public String getParam2(){
		return "Chen";
	}
	
	public Converter getConverter1() {
		return new Converter() {
			public Object coerceToUi(Object val, Component component,
					BindContext ctx) {
				return val + "-"+ctx.getAttribute("arg1");
			}

			public Object coerceToBean(Object val, Component component,
					BindContext ctx) {
				return val + "-"+ctx.getAttribute("arg2");
			}
		};
	}

	@NotifyChange("*")
	public void cmd1(Map<String,Object> args){
		this.value1 += args.get("param1");
		this.value2 += args.get("param2");
	}
}
