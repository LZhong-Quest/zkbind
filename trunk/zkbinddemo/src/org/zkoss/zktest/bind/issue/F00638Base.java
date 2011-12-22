package org.zkoss.zktest.bind.issue;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class F00638Base {

	String value1;
	
	public String getValue1() {
		return value1;
	}

	@NotifyChange
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	@Init
	public void init(){
		this.value1 = "X";
	}
}
