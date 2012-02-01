package org.zkoss.zktest.bind.issue;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public class B00757OnChange {
	String value1;
	String value2;
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	
	@Command @NotifyChange("value2")
	public void cmd1(){
		this.value2 = value1+"-X";
	}
}
