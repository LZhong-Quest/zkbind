/* 

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package test2;

import org.zkoss.zkbind.BindComposer;
import org.zkoss.zkbind.NotifyChange;

/**
 * @author Dennis Chen
 * 
 */
public class F0015 extends BindComposer {
	private String value1;
	private String value2;
	private String value3;


	public F0015() {
		value1 = "A";
		value2 = "B";
		value3 = "C";
	}

	public String getValue1() {
		return value1;
	}

	public String getValue2() {
		return value2;
	}
	
	public String getValue3() {
		return value3;
	}


	//notify property, but not base object
	@NotifyChange("*")
	public void cmd1(){
		this.value1 = "doCommand1";
	}
	
	@NotifyChange("*")
	public void cmd2(){
		this.value2 = "doCommand2";
		getBinder().postCommand("cmd3", null);
	}
	
	@NotifyChange("*")
	public void cmd3(){
		this.value3 = "doCommand3";
	}
	
	
}
